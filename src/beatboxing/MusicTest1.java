package beatboxing;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MusicTest1 {

	public void play(int instrument, int note) {
		try {
			Sequencer player = MidiSystem.getSequencer();
			player.open();
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();

			ShortMessage first = new ShortMessage();
			first.setMessage(192, 1, instrument, 0);
			MidiEvent changeInstrument = new MidiEvent(first, 1);
			track.add(changeInstrument);
			
			ShortMessage second = new ShortMessage();
			second.setMessage(144, 1, note, 100);
			MidiEvent noteOn = new MidiEvent(second, 1);
			track.add(noteOn);
			
			ShortMessage third = new ShortMessage();
			third.setMessage(128, 1, note, 100);
			MidiEvent noteOff = new MidiEvent(third, 16);
			track.add(noteOff);
			
			player.setSequence(seq);
			player.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MusicTest1 mt = new MusicTest1();
		if(args.length<2) {
			System.out.println("Don't forget the instrument and note args");
		}
		else {
			int instrument = Integer.parseInt(args[0]);
			int note = Integer.parseInt(args[1]);
			mt.play(instrument, note);
		}
	}
}
