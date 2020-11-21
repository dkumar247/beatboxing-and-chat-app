package socket.sendandreceiveapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SimpleChatServer {

	ArrayList<PrintWriter> clientOutputStreams;
	
	class ClientHandler implements Runnable{

		BufferedReader reader;
		
		public ClientHandler(Socket clientSocket) {
			// TODO Auto-generated constructor stub
			try {
				reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String message;
			try {
				while((message = reader.readLine())!=null) {
					System.out.println("read: " + message);
					tellEveryone(message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SimpleChatServer().go();

	}

	public void go() {
		clientOutputStreams = new ArrayList<PrintWriter>();
		try {
			ServerSocket serverSock = new ServerSocket(5000);
			while(true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("Got a connection!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tellEveryone(String message) {
		for(PrintWriter pw: clientOutputStreams) {
			pw.println(message);
			pw.flush();
		}
	}
}
