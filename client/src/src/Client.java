package src;

import java.io.*;
import java.net.*;

public class Client {

	public static final int PORT = 4321;
	public static final String HOST = "157.253.219.219";

	private static Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private static String[] files;

	public void test() throws Exception {
		// Initialize socket
		String status = startConnection();
		System.out.println(status);

		if (!status.startsWith("SUCCESS")) {
			System.out.println("Bye");
		} else {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			files = input.readUTF().split(",");
		}
		socket.close();
	}

	public String startConnection() {
		try {
			socket = new Socket(HOST, PORT);
			return "SUCCESSFULLY CONNECTED TO " + HOST + ":" + PORT + ".\n";
		} catch (IllegalArgumentException | NullPointerException | UnknownHostException e) {
			return "INVALID PARAMETERS. ABORTING CONNECTION";
		} catch (IOException e) {
			return "COULD NOT ESTABLISH CONNECITON:\n" + e.getMessage();
		}
	}
	
	public String getFiles(String status) throws IOException {
		if (!status.startsWith("SUCCESS")) {
			return "Bye!";
		} else {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			files = input.readUTF().split(",");
			return "GOT FILES";
		}
	}
	
	public String endConnection() {
		try {
			socket.close();
			return "Connection terminated. Bye!";
		} catch (IOException e) {
			return "Error terminating connection! " + e.getMessage();
		}
	}

	public boolean getFile(String fname) throws FileNotFoundException {
		return false;
	}

	public String[] getDownloads() {
		String files = "";
		File folder = new File("./downloads");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile())
				files += listOfFiles[i].getName() + ",";
		}
		return files.split(",");
	}
}
