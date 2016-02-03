/*
Ross Bryan & Nate Lentz
CIS 457
1/20/16
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;


/***************************************
	class to run on a thread for each client
	****************************************/
	class ClientHandler implements Runnable {
   private Socket client;

    //Constructor
    ClientHandler(Socket client) {
	this.client = client;
    }
    
    public void run(){
    
	String file = "";
	
	    try{
	   
		BufferedReader inFromClient = 
		    new BufferedReader(new InputStreamReader(client.getInputStream()));
		String fileName = "";
		//Reads in client's desired file
		 
		fileName = inFromClient.readLine();
		if (fileName == null){
			System.out.println("Client has disconnected");
			client.close();
			return;
		}
		file = fileName;
		System.out.println("Client wants " + fileName);
		
		if(fileName.equals("exit")){
		    System.out.println("Client has exited.");
		    client.close();
		    return;
		}
	    } catch(IOException e){
		System.out.println("in or out failed");
		System.exit(-1);
	    }
	
	    //Attempts to convert file to bytes and transfer to client
	    try {
	    
		File outFile = new File(file);


		 if (!outFile.exists()){
		 	System.out.println("File does not exist");
		 	client.close();
		 }
		 else {

		byte[] byteArray = new byte[(int)outFile.length()];
		FileInputStream fis = new FileInputStream(outFile);

		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(byteArray, 0, byteArray.length);

		DataOutputStream outToClient = 
		new DataOutputStream(client.getOutputStream());
		System.out.println(
		    "Sending " + file + " (" + byteArray.length + " bytes)");
		outToClient.write(byteArray, 0, byteArray.length);
		outToClient.flush();
		System.out.println("Done.");
		
		bis.close();
		client.close();
		}
	    } catch(FileNotFoundException e) {
		System.out.println("Client requested a file that does not exist.");

		return;
	    }
	    catch (IOException e) {
		System.out.println("IO failed");
		System.exit(1);
	    }
	
    }
}
/****************************************
*
*
*
*****************************************/
public class server {


/****************************************
*
*
*
*****************************************/
public static void main(String[] args) throws Exception {
	//maintains the number of connections
	ArrayList<Socket> connections = new ArrayList<Socket>();

	String sSocket = "";

	System.out.println("Knockin up the FTP server like a mad woman's breakfast");
	System.out.print("Oi, we need a socket to listen to m8: ");

	Scanner sc = new Scanner(System.in);
	String uSocket = sc.next();
	int num = -1;
	try {
	num = Integer.parseInt(uSocket);
	}
	catch (Exception e){
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
		ClientHandler r = new ClientHandler(connectionSocket);
		Thread t = new Thread(r);
		t.start();


		}



	}//end main method
}//end server class
