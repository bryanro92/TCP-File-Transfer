/*
Ross Bryan & Nate Lentz
CIS 457
1/20/16
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;


public class s{
public static void main(String[] args) throws Exception {
	//this is for part 2 - maintains the number of connections
	ArrayList<Socket> connections = new ArrayList<Socket>();

	String sSocket = "";

	System.out.println("Knockin up the FTP server like a mad woman's breakfast");
	System.out.print("Oi, we need a socket to listen to m8: ");

	Scanner sc = new Scanner(System.in);
	String uSocket = sc.next();
	int num = -1;
	try {
	num = Integer.parseInt(uSocket);
	} catch (Exception e){
		System.out.println("Invalid port");
		System.exit(1);
	}
	if (num < 1024 || num > 65535){
		System.out.println("Invalid port m8");
		System.exit(1);
	}

	System.out.println();
		ServerSocket listenSocket = null;
	try {
	//listenSocket = new ServerSocket(9876);
    listenSocket = new ServerSocket(Integer.parseInt(uSocket));
	} catch (IOException ioe){
		System.out.println("Invalid socket m8!");
	}
	System.out.println("Server listening...\nfrom the land down under \n" +
			"on port: \n" + listenSocket + "\n");
	while (true){
		Socket connectionSocket = listenSocket.accept();
		connections.add(connectionSocket);
		System.out.println("Ready the barbie a m8 has connected.");
		System.out.println("There is " + connections.size() + " m8(s) connected.");
		//returns a new socket object that represents server to a specific client
		BufferedReader inFromClient = new BufferedReader(
			new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(
			connectionSocket.getOutputStream());
		String clientFile = inFromClient.readLine();
		System.out.println("The client requested: " + clientFile);

		//for now lets assume the file is there and begin the transfer
		System.out.println("This is what the client entered: " + clientFile);
		 File transferFile = new File (clientFile);

		 if (transferFile.exists() == false){
		 	outToClient.writeBytes("m8 the file doesn't exist!");
		 	System.out.println("File does not exist");
		 	connectionSocket.close(); 
		 	connections.remove(connectionSocket);

		 }
else {
   byte [] bytearray = new byte [(int)transferFile.length()];
    FileInputStream fin = new FileInputStream(transferFile); 
    BufferedInputStream bin = new BufferedInputStream(fin); 
    bin.read(bytearray,0,bytearray.length); 
    OutputStream os = connectionSocket.getOutputStream();
     System.out.println("Sending Files...");
      os.write(bytearray,0,bytearray.length); 
      os.flush(); 
      connectionSocket.close(); 
      System.out.println("File transfer complete"); 
		
		outToClient.flush();
		System.out.println("File has been sent!");
		//outToClient.writeBytes(clientFile+"\n");

		connections.remove(connectionSocket);
		}
}

	}



}