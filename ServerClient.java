import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 * A framework to run public test cases for the User class.
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Purdue CS
 * @author Matthew Clingerman
 * @author Charlotte Falus
 * @author Luke Guiboux
 * @author Kimaya Deshpande
 * @author Sid Songirkar
 * @version November 3, 2024
 */

public class ServerClient implements Runnable {


	public void run(){
    
		
		try (ServerSocket socket = new ServerSocket(8080);){
			Socket clientSocket = socket.accept();
	    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.println("Hello, Server!");
			String response = in.readLine();
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Thread t = new Thread(new ServerClient());
		t.start();
	}
}