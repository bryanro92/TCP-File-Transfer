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
public static void main(String[] args) throws Exception{
	
	Scanner sc = new Scanner(System.in);
	System.out.print("Ay m8: Enter in a port: ");
	String uSocket = sc.next();
	System.out.println();
	System.out.print("Crikey m8, we need an IP Address to connect to: ");
	String uIP = sc.next();

 	Socket clientSocket = new Socket("127.0.0.1", 9876);
	// Socket clientSocket = new Socket(uIP, Integer.parseInt(uSocket));

 	System.out.println("Enter in a file to download m8: ");
 	String file = sc.next();
 	System.out.println("You want to download " + file
 		+ " m8? Crikey");
 	












}


}
