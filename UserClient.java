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
						ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
						ObjectInputStream receive = new ObjectInputStream(socket.getInputStream());
						Scanner sc = new Scanner(System.in);

						String fromServer;
						fromServer = (String) receive.readObject();
						System.out.println(fromServer);
						fromServer = (String) receive.readObject();
						System.out.println(fromServer);
						send.writeObject(sc.nextLine());
						send.flush();
						fromServer = (String) receive.readObject();
						System.out.println(fromServer);
						send.writeObject(sc.nextLine());
						send.flush();
						fromServer = (String) receive.readObject();
						System.out.println(fromServer);


						


				} catch (IOException  | ClassNotFoundException e) {
						e.printStackTrace();
				}
		}
    
}