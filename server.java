/*
Ross Bryan & Nate Lentz
CIS 457
1/20/16
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;


public class server{
public static void main(String[] args) throws Exception {
	//this is for part 2 - maintains the number of connections
	ArrayList<Socket> connections = new ArrayList<Socket>();

	String sSocket = "";

	System.out.println("Knockin up the FTP server like a mad woman's breakfast");
	System.out.print("Oi, we need a socket to listen to m8: ");

	Scanner sc = new Scanner(System.in);
	String uSocket = sc.next();
	System.out.println();
	ServerSocket listenSocket = new ServerSocket(9876);
	//ServerSocket listenSocket = new ServerSocket(Integer.parseInt(uSocket));
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
		File fileToSend = new File(clientFile);
		byte [] mybytearray = new byte [(int)clientFile.length()];
		FileInputStream fis = new FileInputStream(clientFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(mybytearray,0,mybytearray.length);
		OutputStream os = connectionSocket.getOutputStream();
		os.write(mybytearray,0,mybytearray.length);
		
		outToClient.flush();
		System.out.println("File has been sent!");
		//outToClient.writeBytes(clientFile+"\n");
		//hm
		connectionSocket.close();
		connections.remove(connectionSocket);
		}


	}



}
