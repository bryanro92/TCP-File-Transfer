/*
Ross Bryan
Project 1
CIS 457
1/20/16
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {

  public final static int FILE_SIZE = 1022386;

public static void main(String[] args) throws Exception{
	String uSocket = "";
	String uIP = "";
	String file = "";
	System.out.println("M8! Surfs up");
	Socket clientSocket = null;

	//grabs port and ip from client. error checks. if pass this then we
	//have connected to server
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	System.out.print("Ay m8: Enter in a port: ");


	//Requests PORT and IP from CLIENT
	try {
		uSocket = br.readLine();
	}
	catch (IOException ioe){
		System.out.println("Bloody hell m8 dat port ain't rite");
		System.exit(1);
	}
	System.out.print("Crikey m8, we need an IP Address to connect to: ");
	try {
		uIP = br.readLine();
	} catch (IOException ioe) {
		System.out.println("no no no. IP Address failed");
		System.exit(1);
	}
	System.out.println("We'll get right on connecting you to: " + uIP +
		"\nOn socket: " + uSocket);


	try {
		clientSocket = new Socket("127.0.0.1", 9876);
		// Socket clientSocket = new Socket(uIP, Integer.parseInt(uSocket));
	}
	catch (Exception e){
		System.out.println("Hm m8 dat didn't work our quite well. no connection");
		System.exit(1);
	}

	
	System.out.println("Crikey! Succesfully connected to server");

	//lets set up our buffered readers now shall we
	DataOutputStream outToServer =
		new DataOutputStream(clientSocket.getOutputStream());
	BufferedReader inFromServer =
		new BufferedReader(
			new InputStreamReader(clientSocket.getInputStream()));
	BufferedReader inFromUser =
		new BufferedReader(new InputStreamReader(System.in));



 	System.out.print("Enter in a file to download m8: ");
 	System.out.println();
	String fileName = inFromUser.readLine();
	//sends over socket
	//hard coded for testing
	
	outToServer.writeBytes(fileName);
	//outToServer.writeBytes("/Users/WhiteOwl/Desktop/test.txt"+"\n");
	//fileName = "/Users/WhiteOwl/Desktop/test.txt";

	System.out.println("Server will fetch: " + fileName);
	//System.out.println("jk. currently hardcoded to: /Users/WhiteOwl/Desktop/test.txt");


	//receive file	
   int bytesRead; 
   int currentTot = 0; 
   byte [] bytearray = new byte [FILE_SIZE]; 
   InputStream is = clientSocket.getInputStream(); 
   FileOutputStream fos = new FileOutputStream(fileName); 
   BufferedOutputStream bos = new BufferedOutputStream(fos); 
   bytesRead = is.read(bytearray,0,bytearray.length); 
   currentTot = bytesRead;
    do { bytesRead = is.read(bytearray, currentTot, (bytearray.length-currentTot)); 
      if(bytesRead >= 0) currentTot += bytesRead;
       } while(bytesRead > -1); 
       bos.write(bytearray, 0 , currentTot); 
       bos.flush(); 
       bos.close(); 
       clientSocket.close(); 


 	



}


}
