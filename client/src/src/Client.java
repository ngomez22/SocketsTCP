package src;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Client {

	public static final String HOST = "127.0.0.1";
	public static final int PORT = 4321;
	private static final int BUFFER_SIZE = 8192;
	public static final int MSG_SIZE = 5216;
	private static final int TIMEOUT = 60000;

	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private Download download;
	private Timeout timeout;

	public String startConnection() {
		try {
			socket = new Socket(HOST, PORT);
			socket.setReceiveBufferSize(BUFFER_SIZE);
			socket.setSendBufferSize(BUFFER_SIZE);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			timeout = new Timeout();
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
				return "Error terminating connection!";
			}
		}
		return "No connection to terminate";
	}
	
	public boolean download(String fname) throws IOException {
		if(!timeout.timeout()) {
			download = new Download(socket.getReceiveBufferSize(), fname, output, input);
			download.start();
			timeout.reset();
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean cancelDownload() {
		if(download!=null && download.isAlive()) {
			download.exit();
			return true;
		}
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
	
	public boolean isClosed() {
		return socket.isClosed();
	}
	
	public class Timeout {
		
		private long start;
		
		public Timeout() {
			start = System.currentTimeMillis();
		}
		
		public void reset() {
			start = System.currentTimeMillis();
		}
		
		public boolean timeout() {
			return System.currentTimeMillis() - start > TIMEOUT;
		}
	}
}
