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
* Client class. Connects to server
* and requests a file. Downloads a copy
* of the file with 'new' prepended.
* Runs until invalid file, or user exits
*****************************************/
public class client {

/****************************************
* Gets an IP address from the user with 
* error checking
* @returns IP as a string
*****************************************/
private static String askForIP(){
	//Determines what IP & port the user will connect to
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	System.out.print("Crikey m8, we need an IP Address to connect to: ");
	String ip = "";
	try {
	ip = inFromUser.readLine();
	//if it is anything other than local host, for our purposes
	//it is invalid
	if (!ip.equals("127.0.0.1")) {
	    System.out.println("Invalid IP address.");
	    System.exit(1);
		}
	} catch (Exception e) {
		System.out.println("Error getting input");
	}
	return ip;
}

/****************************************
* Gets port number from the user with 
* error checking
* @returns port as int
*****************************************/
private static int askForPort(){
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	System.out.print("Ay m8: Enter in a port: ");
	int uSocket = -1;
	try {
		uSocket = Integer.parseInt(inFromUser.readLine());
		//checks for valid port number
		if (uSocket < 1024 || uSocket > 65000){
			System.out.println("Bloody hell m8 dat port ain't rite");
			System.exit(1);
		}
	}
	catch (IOException ioe){
		//If there is a string -> int parse error then
		//the dingus did not enter in a valid port
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
		
	//gets start up info from user	
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
		//if we are here, we succesfully connected
	    System.out.println("Connecting...");
	    
	    //Asks user for desired file and sends info to server
	    byte[] bArray = new byte[1000000];
	    DataInputStream is = new DataInputStream(clientSocket.getInputStream());
	    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	    System.out.println("What file would you like?");
	    String fileName = inFromUser.readLine();
	    outToServer.writeBytes(fileName + '\n');
	    
	    //Did the user request to exit?
	    if(fileName.equals("exit"))
			System.exit(0);


	    System.out.println("Requesting...");
	   
	    //this is going to be a copy of the file
	    saveFile = "new"+fileName;

	    //Recieves server output and saves to new file
	    try {
		FileOutputStream fos = new FileOutputStream(saveFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int bytesRead = is.read(bArray, 0, bArray.length);
		int current = bytesRead;
		
		//prints the size of the file in bytes
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
		System.exit(1);
	    }
	}
	}


}