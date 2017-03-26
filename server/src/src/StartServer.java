package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer
{
	private static final int PORT = 4321;
	private static final int MAX_CLIENTS = 100;
	private int clients = 0;
	public StartServer()
	{

		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while(true){
				if(acceptNewClient())
				{
					Socket clientSocket = null;
		            clientSocket = serverSocket.accept();
		            System.out.println("Nuevo Cliente");
		            addClient();
		            new Server(clientSocket, this).start();
				} 
				else
				{}
	        }
		} catch (IOException e) {
			System.out.println("Failed to set server socket on port: "+PORT);
			e.printStackTrace();
		}
	}
	public synchronized  void addClient()
	{
		clients++;
	}
	public synchronized  void removeClient()
	{
		clients--;
	}
	public synchronized boolean acceptNewClient()
	{
		if(clients < MAX_CLIENTS)
		{
			return true;
		}
		else{ return false;}
	}
}