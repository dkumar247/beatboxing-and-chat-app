package socket.simpleprogram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DailyAdviceClient {

	public void go() {
		try {
			Socket socket = new Socket("127.0.0.1", 4242);
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String advice = br.readLine();
			System.out.println("Today's advice is: "+advice);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DailyAdviceClient dac = new DailyAdviceClient();
		dac.go();
	}
}
