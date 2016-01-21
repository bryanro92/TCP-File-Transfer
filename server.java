/*
Ross Bryan & Nate Lentz
CIS 457
1/20/16
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;


public class tcpserver{
	public static void main(String[] args) throws Exception {
		System.out.print("Oi, we need a socket to listen to m8: ");
		Scanner sc = new Scanner(System.in);
		String uSocket = sc.next();
		ServerSocket listenSocket = new ServerSocket(9876);
		//ServerSocket listenSocket = new ServerSocket(Integer.parseInt(uSocket));
		while (true){
			Socket connectionSocket = listenSocket.accept();
			//returns a new socket object that represents server to a specific client
			BufferedReader inFromClient = new BufferedReader(
				new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
				connectionSocket.getOutputStream());
			String clientMessage = inFromClient.readLine();
			System.out.println("The client said: " + clientMessage);



			outToClient.writeBytes(clientMessage+"\n");

			connectionSocket.close();

		}


	}



}
