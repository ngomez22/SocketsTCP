package src;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Download extends Thread {
	
	private volatile boolean downloading = true;
	private String fname;
	private DataOutputStream output;
	private DataInputStream input;
	
	public Download(String fname, DataOutputStream output, DataInputStream input) {
		this.fname = fname;
		this.output = output;
		this.input = input;
	}
	
	public void exit() {
		downloading = false;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Downloading " + fname);
			FileOutputStream fos = new FileOutputStream(new File("./downloads/" + fname));
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			output.writeUTF("DOWNLOAD");
			output.writeUTF(fname);
			
			long fileLength = input.readLong();
			
			int total = 0;
			int bytesRead = 0;
			int i = 1;
			byte[] chunk = new byte[Client.BUFFER_SIZE];
			while ( total < fileLength && downloading ){
				bytesRead = input.read(chunk);
				printChunk(chunk, i);
				bos.write(chunk, 0, bytesRead);
				total += bytesRead;
				i++;
			}
			if(downloading) {
				System.out.println("Transfer completed! File saved successfully");
			} else {
				System.out.println("Download cancelled");
				output.writeUTF("END");
			}
			bos.flush();
		} catch(IOException e) {
			System.out.println("Error");
		}
	}
	
	public void printChunk(byte[] chunk, int num) {
		System.out.println("Receving packet #" + num + ": " + Arrays.toString(chunk));
	}

}
