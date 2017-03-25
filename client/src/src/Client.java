package src;

import java.io.*;
import java.net.*;

public class Client {

	public static final int PORT = 4321;
	public static final String HOST = "127.0.0.1";
	private static final int BUFFER_SIZE = 5000;

	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private String[] files;

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
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			return "SUCCESSFULLY CONNECTED TO " + HOST + ":" + PORT + ".\n";
		} catch (IllegalArgumentException | NullPointerException | UnknownHostException e) {
			return "INVALID PARAMETERS. ABORTING CONNECTION";
		} catch (IOException e) {
			return "COULD NOT ESTABLISH CONNECTION";
		}
	}

	public String[] getFiles() {
		if (socket != null) {
			try {
				output.writeUTF("GET FILES");
				return input.readUTF().split(",");
			} catch (IOException e) {
				e.printStackTrace();
				return new String[0];
			}
		}
		return new String[0];
	}

	public String endConnection() {
		if (socket != null) {
			try {
				output.writeUTF("END");
				socket.close();
				return "Connection terminated. Bye!";
			} catch (IOException e) {
				return "Error terminating connection! " + e.getMessage();
			}
		}
		return "No connection to terminate";
	}

	public void downloadFile(String fname) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("./downloads/" + fname));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		output.writeUTF("DOWNLOAD");
		output.writeUTF(fname);
		int bytesRead = 0;
		byte[] chunk = new byte[BUFFER_SIZE]; 
		while ((bytesRead = input.read(chunk)) != -1){
			System.out.println(chunk);
			bos.write(chunk, 0, bytesRead);
		}
		bos.flush();
		socket.close();

		System.out.println("File saved successfully!");
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
