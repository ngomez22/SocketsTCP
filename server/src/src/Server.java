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

public class Server extends Thread {

	private boolean active;
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

	public void sendFile() {
		FileInputStream fileInputStream;
		try {
			String pName = input.readUTF();
			File file = new File("./files/" + pName);
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

	public void sendFile(String fName) throws IOException {
		File file = new File("./files/");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis); 
                
        //Read File Contents into contents array 
        byte[] contents;
        long fileLength = file.length(); 
        long current = 0;
         
        long start = System.nanoTime();
        while(current!=fileLength){ 
            int size = 10000;
            if(fileLength - current >= size)
                current += size;    
            else{ 
                size = (int)(fileLength - current); 
                current = fileLength;
            } 
            contents = new byte[size]; 
            bis.read(contents, 0, size); 
            output.write(contents);
            System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
        }   
        
        output.flush();
        System.out.println("File sent succesfully!");
	}

	public void done() {
		System.out.println("OK");
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
			try {
				String request = input.readUTF();
				handleRequest(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
