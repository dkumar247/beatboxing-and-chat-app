package socket.simpleprogram;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DailyAdviceServer {
	String[] adviceList = {"Take smaller bites", "Go for the tight jeans. No they do NOT make you look fat.", "One word: inappropriate", "Just for today, be honest. Tell your boss what you *really* think", "You might want to rethink that haircut."};
	public void go() {
		try {
			ServerSocket ss = new ServerSocket(4242);
			while(true) {
				Socket socket = ss.accept();
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				String messg = getRandomMessage();
				pw.println(messg);
				pw.close();
				System.out.println(messg);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getRandomMessage() {
		return adviceList[(int)(Math.random()*(adviceList.length))];
	}
	
	public static void main(String args[]) {
		DailyAdviceServer das = new DailyAdviceServer();
		das.go();
	}
}
