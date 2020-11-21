package beatboxing;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimpleGui2WithColor2WithAnimation {

	JLabel label;
	JFrame frame;
	MyDrawPanel2 drawPanel;
	private int x;
	private int y;
	public static void main(String[] args) {
		SimpleGui2WithColor2WithAnimation sg = new SimpleGui2WithColor2WithAnimation();
		sg.go();
	}
	
	public void go() {
		frame = new JFrame();
		
		JButton button = new JButton("Change Color");
		button.addActionListener(new ColorListener());
		JButton labelButton = new JButton("Change Label");
		labelButton.addActionListener(new LabelListener());
		
		drawPanel = new MyDrawPanel2();
		label = new JLabel("I am a label");
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
		frame.getContentPane().add(BorderLayout.SOUTH ,button);
		frame.getContentPane().add(BorderLayout.EAST, label);
		frame.getContentPane().add(BorderLayout.WEST, labelButton);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}

	class LabelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			label.setText("Ouch!");
		}
		
	}
	
	class ColorListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			frame.repaint();
			for(int i=0;i<130;i++) {
				x++;
				y++;
				drawPanel.repaint();
				try {
					Thread.sleep(50);
				} catch (Exception e1) {
					// TODO: handle exception
				}
			}
		}
		
	}
	
	class MyDrawPanel2 extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1458814334384765288L;

		public void paintComponent(Graphics g) {
//			Graphics2D g2d = (Graphics2D)g;
//			int red = (int)(Math.random()*255);
//			int blue = (int)(Math.random()*255);
//			int green = (int)(Math.random()*255);
//			Color startColor = new Color(red, green, blue);
//			
//			red = (int)(Math.random()*255);
//			green = (int)(Math.random()*255);
//			blue = (int)(Math.random()*255);
//			Color endColor = new Color(red, green, blue);
//			
//			GradientPaint gradient = new GradientPaint(70, 70, startColor, 150, 150, endColor);
//			g2d.setPaint(gradient);
//			g2d.fillOval(x, y, 100, 100);
			g.fillOval(x, y, 100, 100);
		}
	}

}

