/*
Ross Bryan & Nate Lentz
Project 1
CIS 457
1/20/16
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

/****************************************
*
*
*
*****************************************/
public class client {

private static String askForIP(){
	//Determines what IP & port the user will connect to
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	System.out.print("Crikey m8, we need an IP Address to connect to: ");
	String ip = "";
	try {
	ip = inFromUser.readLine();
	if (!ip.equals("127.0.0.1")) {
	    System.out.println("Invalid IP address.");
	    System.exit(1);
		}
	} catch (Exception e) {
		System.out.println("Error getting input");
	}
	return ip;
}

private static int askForPort(){
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	System.out.print("Ay m8: Enter in a port: ");
	int uSocket = -1;
	try {
		uSocket = Integer.parseInt(inFromUser.readLine());
	}
	catch (IOException ioe){
		System.out.println("Bloody hell m8 dat port ain't rite");
		System.exit(1);
	}
	return uSocket;
}


/****************************************
*
*
*
*****************************************/
	public static void main(String[] args) throws Exception{
		
	String saveFile = "";
	String ip = askForIP();
	int port = askForPort();

	//Determines what IP & port the user will connect to
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

	while(true){
	    //Creates socket to connect with server
	    Socket clientSocket = null;	
	    try {
	    clientSocket = new Socket(ip, port);
		} catch (ConnectException ce){
			System.out.println("Crikey, unable to connect to server!");
			System.exit(1);
		}
	    System.out.println("Connecting...");
	    
	    //Asks user for desired file and sends info to server
	    byte[] bArray = new byte[1000000];
	    DataInputStream is = new DataInputStream(clientSocket.getInputStream());
	    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	    System.out.println("What file would you like?");
	    String fileName = inFromUser.readLine();
	    outToServer.writeBytes(fileName + '\n');
	    
	    if(fileName.equals("exit"))
		System.exit(-1);
	    System.out.println("Requesting...");
	    
	    saveFile = "new"+fileName;

	    //Recieves server output and saves to new file
	    try {
		FileOutputStream fos = new FileOutputStream(saveFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int bytesRead = is.read(bArray, 0, bArray.length);
		int current = bytesRead;
		
		System.out.println(bytesRead);
		
		do {
		    System.out.println(1);
		    bytesRead = is.read(bArray, current, (bArray.length - current));
		    System.out.println(2);
		    if (bytesRead >= 0)
			current += bytesRead;
		    System.out.println(3);
		} while(bytesRead > -1);

		bos.write(bArray, 0, current);
		bos.flush();
		System.out.println(
		    "File " + saveFile + " downloaded (" + current + " bytes read)");

		bos.close();
		clientSocket.close();
	    } catch (ArrayIndexOutOfBoundsException aie) {
		System.out.println("File not found.");
		System.exit(0);
	    }
	}
	}


}