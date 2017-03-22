package src;

import java.io.*;
import java.net.*;


public class Client {
	
	public static final int PORT = 4321;
	public static final String HOST = "127.0.0.1";
	
	private static Socket socket;
	
	public static void main(String args[]) throws Exception {

        // Initialize socket
		String status = startConnection();
		System.out.println(status);
		
		if(!status.startsWith("SUCCESS")) {
			System.out.println("Bye");
		}
		else {
			
		}
		
        File file = new File("./files/"); //TODO cómo sé qué nombre ponerle?
        
        // Get the size of the file
        long length = file.length();
        
        byte[] bytes = new byte[16 * 1024]; //TODO tamaño de los paquetes
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();
        socket.close();
	}
	
	public static String startConnection() {
		try {
			socket = new Socket(HOST, PORT);
			return "SUCCESSFULLY CONNECTED TO " + HOST + ":" + PORT + ".\n";
		} catch (IllegalArgumentException | NullPointerException | UnknownHostException e) {
			return "INVALID PARAMETERS. ABORTING CONNECTION";
		} catch (IOException e) {
			return "COULD NOT ESTABLISH CONNECITON:\n" + e.getMessage();
		}
	}
	
	public static boolean getFile(String fname) throws FileNotFoundException {
		try {
			FileOutputStream fos = new FileOutputStream(new File("/downloads/testfile.jpg"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
