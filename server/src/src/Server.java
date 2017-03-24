package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
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
			output = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
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
		System.out.println("Se enviaron los nombres: "+resp);
		return resp;
	}
	


	public void run() {
		
		try {
			output.writeBytes(getFileNames());
			input.re
			
//			 FileInputStream fileInputStream = new FileInputStream(largeFile);
//             OutputStream socketOutputStream = socket.getOutputStream();
//             long startTime = System.currentTimeMillis();
//             byte[] buffer = new byte[BUFFER_SIZE];
//             int read;
//             int readTotal = 0;
//             while ((read = fileInputStream.read(buffer)) != -1) {
//                     socketOutputStream.write(buffer, 0, read);
//                     readTotal += read;
//             }
//			
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
//		try { 
//			
//			
//			
//			
//			FileWriter out = new FileWriter("test.txt");
//			BufferedWriter bufWriter = new BufferedWriter(out);
//
//			// Step 1 read length
//			int nb = input.readInt();
//			System.out.println("Read Length" + nb);
//			byte[] digit = new byte[nb];
//			// Step 2 read byte
//			System.out.println("Writing.......");
//			for (int i = 0; i < nb; i++)
//				digit[i] = input.readByte();
//
//			String st = new String(digit);
//			bufWriter.append(st);
//			bufWriter.close();
//			System.out.println("receive from : "
//					+ clientSocket.getInetAddress() + ":"
//					+ clientSocket.getPort() + " message - " + st);
//
//			// Step 1 send length
//			output.writeInt(st.length());
//			// Step 2 send length
//			output.writeBytes(st); // UTF is a string encoding
//			// output.writeUTF(data);
//		} catch (EOFException e) {
//			System.out.println("EOF:" + e.getMessage());
//		} catch (IOException e) {
//			System.out.println("IO:" + e.getMessage());
//		}
//
//		finally {
//			try {
//				clientSocket.close();
//			} catch (IOException e) {/* close failed */
//			}
//		}
//
	}

}
