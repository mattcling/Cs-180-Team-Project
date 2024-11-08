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

public class UserClient{

    public static void main(String[] args) {
				try (Socket socket = new Socket("localhost", 4343);){
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						System.out.println("Hello, Server!");

						
				} catch (IOException e) {
						e.printStackTrace();
				}
		}
    
}