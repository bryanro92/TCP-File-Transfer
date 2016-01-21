/*
Ross Bryan and Nate Lentz
CIS 457
Project 1
Client side 
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;

class client{
public static void main(String args[]) throws Exception{
	Scanner sc = new Scanner(System.in);
	System.out.print("Ay m8: Enter in a port: ");
	String uSocket = sc.next();
	System.out.println();
	System.out.print("Crikey m8, we need an IP Address to connect to: ");
	String uIP = sc.next();

	Socket clientSocket = new Socket("127.0.0.1", 9876);
	// Socket clientSocket = new Socket(uIP, Integer.parseInt(uSocket));
	DataOutputStream outToServer =
		new DataOutputStream(clientSocket.getOutputStream());
	BufferedReader inFromServer =
		new BufferedReader(
			new InputStreamReader(clientSocket.getInputStream()));
	BufferedReader inFromUser =
		new BufferedReader(new InputStreamReader(System.in));

		//gets data form user
	System.out.println("Enter a message: ");
	String message = inFromUser.readLine();
	//sends over socket
	outToServer.writeBytes(message+"\n");

	System.out.println("Ok, I sent it");

	String serverMessage = inFromServer.readLine();
	System.out.println("Message: " + serverMessage);
	clientSocket.close();
}





}
