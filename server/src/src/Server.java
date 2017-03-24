package src;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Server extends Thread {

	private Socket clientSocket;
	private DataInputStream input;
	private DataOutputStream output;
	private byte[] buffer;
	private static final int BUFFER_SIZE = 5000;


	public Server(Socket cSocket) {
		buffer = new byte[BUFFER_SIZE];
		clientSocket = cSocket;
		try {
			input = new DataInputStream(clientSocket.getInputStream());
			//input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			output = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void handleRequest(String request) {
		switch(request) {
			case "GET FILES": sendAvailableFiles();
			case "DOWNLOAD": sendFile();
			case "OK": done();
			default: error();
		}
	}

	public String getFileNames() {
		String resp = "";
		File folder = new File("./files");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				resp+=listOfFiles[i].getName();
				if(i!=listOfFiles.length-1)
				{
					resp+=",";
				}
			} 
		}
		return resp;
	}
	
	public void sendAvailableFiles() {
		try {
			output.writeUTF(getFileNames());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendFile() {
		FileInputStream fileInputStream;
		try {
			String pName = input.readUTF();
			File file = new File("./files/"+pName);
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[BUFFER_SIZE];
			int read;
			int readTotal = 0;
			while ((read = fileInputStream.read(buffer)) != -1) {
				output.write(buffer, 0, read);
				readTotal += read;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find file");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendFile(String pName) {
		FileInputStream fileInputStream;
		try {
			File file = new File("./files/"+pName);
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[BUFFER_SIZE];
			int read;
			int readTotal = 0;
			while ((read = fileInputStream.read(buffer)) != -1) {
				output.write(buffer, 0, read);
				readTotal += read;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find file: "+pName);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void done() {
		System.out.println("OK");
	}
	
	public void error() {
		System.out.println("ERROOOOORRRRRRRR");
	}


	public void run() {
		while(true) {
			try {
				String request = input.readUTF();
				handleRequest(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//No olvidar cerrar socket
	}

}
