package src;

import java.net.*; 
import java.io.*; 

public class ClientePrueba { 
	private static final int BUFFER_SIZE = 5000;
	public static void main (String args[]) 
	{// arguments supply message and hostname of destination  
		Socket s = null; 
		try{ 
			  int serverPort = 4321;
				  String ip = "localhost";
				  
			  s = new Socket(ip, serverPort); 
			  DataInputStream input = new DataInputStream( s.getInputStream()); 
			  DataOutputStream output = new DataOutputStream( s.getOutputStream()); 
			  
			  
			  String names = input.readUTF();
			  System.out.println("Received: "+ names); 
			  
			  output.writeUTF("small.jpg");
			  
			 
		
			 /* byte[] buffer = new byte[BUFFER_SIZE];
              int read;
              int readTotal = 0;
              while ((read = input.read(buffer)) != -1) {
                      socketOutputStream.write(buffer, 0, read);
                      readTotal += read;
              }*/
              System.out.println("OK");
		  
			  
		 
		}
		catch (UnknownHostException e){ 
			System.out.println("Sock:"+e.getMessage());}
		catch (EOFException e){
			System.out.println("EOF:"+e.getMessage()); }
		catch (IOException e){
			System.out.println("IO:"+e.getMessage());} 
		finally {
			  if(s!=null) 
				  try {s.close();
				  } 
				  catch (IOException e) {/*close failed*/}
	}
  }
}
