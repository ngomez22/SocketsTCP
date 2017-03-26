package src;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerJMeterTest extends Thread {

	private static final int BUFFER_SIZE = 4096;
	private static final int MSG_SIZE = 3072;
	private static final int TIMEOUT = 60000;
	
	private StartServer startServer;
	private boolean active;
	private Socket clientSocket;
	private DataInputStream input;
	private DataOutputStream output;
	private byte[] buffer;
	private String reqTEST;

	public ServerJMeterTest(Socket cSocket, StartServer st) {
		startServer = st;
		buffer = new byte[MSG_SIZE];
		clientSocket = cSocket;
		reqTEST = "DOWNLOAD";
		try {
			clientSocket.setSoTimeout(TIMEOUT);
			clientSocket.setReceiveBufferSize(BUFFER_SIZE);
			clientSocket.setSendBufferSize(BUFFER_SIZE);
			input = new DataInputStream(clientSocket.getInputStream());
			output = new DataOutputStream(clientSocket.getOutputStream());
			active = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleRequest(String request) {
		System.out.println("REQUEST: " + request);
		switch (request) {
		case "GET FILES":
			sendAvailableFiles();
			break;
		case "DOWNLOAD":
			sendFile();
			break;
		case "OK":
			done();
			break;
		case "END":
			end();
			break;
		default:
			error();
			break;
		}
	}

	public String getFileNames() {
		String resp = "";
		File folder = new File("./files");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				resp += listOfFiles[i].getName();
				if (i != listOfFiles.length - 1) {
					resp += ",";
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

	public boolean sendFile() {
		try {
			String fName = "small.jpg";
			System.out.println("Starting download of " + fName);
			File file = new File("./files/" + fName);
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);

			long fileLength = file.length();
			output.writeLong(fileLength);

			clientSocket.setReceiveBufferSize(MSG_SIZE);

			byte[] contents = new byte[MSG_SIZE];
			long current = 0;
			int count;
			while ((count = fis.read(contents)) > -1) {
				output.write(contents, 0, count);
				current += count;
				System.out.println("Sending file ... " + (current * 100) / fileLength + "% complete!" + count);
			}
			System.out.println("File sent succesfully!");
			reqTEST = "OK";
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void done() {
		System.out.println("OK");
		reqTEST = "END";
	}

	public void error() {
		System.out.println("ERROOOOORRRRRRRR");
	}

	public void end() {
		System.out.println("Ending");
		active = false;
	}

	public void run() {
		while (active) {
			
				String request = reqTEST;
				handleRequest(request);
			
		}
		try {
			startServer.removeClient();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
