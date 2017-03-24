package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	
	private static final int PORT = 4321;
	public static void main(String[] args) {
		
	
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while(true){
	            Socket clientSocket = null;
	            clientSocket = serverSocket.accept();
	            new Server(clientSocket).start();
	        }
		} catch (IOException e) {
			System.out.println("Failed to set server socket on port: "+PORT);
			e.printStackTrace();
		}

	}

}
