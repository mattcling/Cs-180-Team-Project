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

public class ServerClient implements Runnable, ServerClientInterface {
	Socket socket;
	private final String userDataFile = "userTable.ser";
    private final String chatDataFile = "chatTable.ser";
    private final String messageDataFile = "messageTable.ser";

	public ServerClient(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		System.out.printf("connection received from %s\n", socket);
		
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			Scanner in = new Scanner(socket.getInputStream());
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				System.out.printf("%s says: %s\n", socket, line);
				pw.printf("echo: %s\n", line);
				pw.flush();
			}
			
			pw.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Database d = new Database();
		d.initializeDatabase();
		

		// First User!
		// User user = new User("john", "80085");
		// d.writeData(user,"user");
		// d.deleteData("user","john");
		//System.out.println( ((User) d.getData("user", "john")).getPassword());

		// First Chat!
		// Chat chat = new Chat("chat1", "john", "admin");
		// d.writeData(chat,"chat");
		//d.deleteData("chat","chat1");
		// System.out.println(((Chat) d.getData("chat", "chat1")).getParticipants());

		// First Message!
		// Message message = new Message("message1", "john", "admin", "hello");
		// d.writeData(message,"message");
		//d.deleteData("message","message1");
		//System.out.println(((Message) d.getData("message", "message1")).getMessageID());

		


		try {
			ServerSocket serverSocket = new ServerSocket(4343);
			System.out.printf("socket open, waiting for connections on %s\n", serverSocket);
			while (true) {
				
				Socket socket = serverSocket.accept();
				System.out.println("Hello Client");
				ServerClient server = new ServerClient(socket);
				new Thread(server).start();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}