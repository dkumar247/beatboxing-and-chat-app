package socket.sendandreceiveapp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


/**
 * @author deepanshu
 * 
 *
 */
public class SimpleChatClient {

	JTextArea incoming;
	JTextField outgoing;
	PrintWriter writer;
	BufferedReader reader;
	Socket sock;
	
	public void go() {
		JFrame frame = new JFrame("Ludicrously simple chat client");
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(15, 50);
		incoming.setLineWrap(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing  = new JTextField(20);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		setUpNetworking();
		
		/*
		 * We’re starting a new thread, using a new inner class as the Runnable (job)
		 * for the thread. The thread’s job is to read from the server’s socket stream,
		 * displaying any incoming messages in the scrolling text area.
		 */
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
		frame.setSize(400, 500);
		frame.setVisible(true);
	}
	
	/*
	 * We’re using the socket to get the input and output streams. We were already
	 * using the output stream to send to the server, but now we’re using the input
	 * stream so that the new ‘reader’ thread can get messages from the server.
	 */
	public void setUpNetworking() {
		try {
			sock = new Socket("127.0.0.1", 5000);
			InputStreamReader isr = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isr);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("network established");
		}catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	class SendButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				writer.println(outgoing.getText());
				writer.flush();
			}catch (Exception ex) {
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
		
	}
	
	/*
	 * This is what the thread does!! In the run() method, it stays in a loop (as
	 * long as what it gets from the server is not null), reading a line at a time
	 * and adding each line to the scrolling text area (along with a new line
	 * character).
	 */
	class IncomingReader implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String message;
			try {
				while((message = reader.readLine())!=null) {
					System.out.println("read " + message);
					incoming.append(message + "\n");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		SimpleChatClient sChat = new SimpleChatClient();
		sChat.go();
	}
}
