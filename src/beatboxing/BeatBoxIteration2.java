package beatboxing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author deepanku
 *	Music with word 'la' printed whenever a beat is played 'note on'
 *
 */
public class BeatBoxIteration2 {

	class MyDrawPanel2 extends JPanel implements ControllerEventListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3765073446627197392L;
		boolean msg = false;
		
		@Override
		public void controlChange(ShortMessage event) {
			// TODO Auto-generated method stub
			msg = true;
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			if(msg) {
				Graphics2D g2 = (Graphics2D)g;
				
				int r = (int)(Math.random()*255);
				int gr = (int)(Math.random()*255);
				int b = (int)(Math.random()*255);
				g2.setColor(new Color(r, gr, b));
				
				int ht = (int)((Math.random()*120)+10);
				int wd = (int)((Math.random()*120)+10);
				
				int x = (int)((Math.random()*40)+10);
				int y = (int)((Math.random()*40)+10);
				
				g2.fillRect(x, y, ht, wd);
				msg = false;
			}
		}
		
	}
	
	static  JFrame f = new JFrame("My first music video");
	static MyDrawPanel2 m1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BeatBoxIteration2 bt = new BeatBoxIteration2();
		bt.go();
	}
	
	public void setUpGui() {
		m1 = new MyDrawPanel2();
		f.setContentPane(m1);
		f.setBounds(30, 30, 300, 300);
		f.setVisible(true);
	}
	
	public void go() {

		setUpGui();
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			
			int[] eventsWanted = {127};
			sequencer.addControllerEventListener(m1, eventsWanted);
			
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();
			
			for(int i=5;i<61;i+=4) {
				int r = (int)((Math.random()*50)+1);
//				144 is for note on
				track.add(makeEvent(144, 1, r, 100, i));
//				176 says the type is controller event
				track.add(makeEvent(176, 1, 127, 0, i));
//				128 is for note off
				track.add(makeEvent(128, 1, r, 100, i+2));
			}
			
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(220);
			sequencer.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return event;
	}
}
