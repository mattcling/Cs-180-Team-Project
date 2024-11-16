import java.io.*;
import java.net.*;

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
	public static Database d = new Database();

	public ServerClient(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		System.out.printf("connection received from %s\n", socket);
		
		try {
			ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream receive = new ObjectInputStream(socket.getInputStream());
			
			send.writeObject("Hello, User!");
			send.flush();
			//while later mabye
			send.writeObject("Please choose 1 option \n 1. Login \n 2. Create new user");
			send.flush();
			
			String Response = ((String) receive.readObject());
			if (Response.equals("1")) { //login
				send.writeObject("Please enter your username: ");
				String username = (String) receive.readObject();
				send.writeObject("Please enter your password: ");
				String password = (String) receive.readObject();

				if(d.containsObject("user", username)) {
					if(password.equals(( (User) d.getData("user", username)).getPassword())){
						send.writeObject("Welcome, " + username + "!");
					} else {
						send.writeObject("Invalid password.");
					}
				}
				else {
					send.writeObject("Invalid username.");
				}
			} else if (Response.equals("2")) { //create new user
				send.writeObject("Create your User!");
				send.flush();
				String username = null;
				while(true) {
					send.writeObject("Please enter a username: ");
					send.flush();
					username = (String) receive.readObject();
					if(d.getData("user", username) != null) {
						send.writeObject("Username is already taken");
						send.flush();
					} else {
						break;
					}
				}
				send.writeObject("Please enter a password: ");
				send.flush();
				String password = (String) receive.readObject();
				System.out.println("Test point 1");
				d.writeData(new User(username, password), "user");
				System.out.println("Test point 2");
			} else { //invalid selection
				send.writeObject("Invalid Input");
				//mabye cont w a while loop 
			}
			/*
			user is logged in: (need to be able to: meessage, see profile, see friends, see blocked, search);
			send.writeObject("Please choose 1 option \n 1. Go To Profile \n 2. Send Message \n 3. Logout");
			send.flush();
			Response = ((String) receive.readObject());
			switch(Response) {
				case "1":\\profile
					//DISPLAY PROFILE HERE
					send.writeObject("Please choose 1 option \n 1. Edit Username \n 2. Edit password \n 3. Edit Friends \n 4. Edit Blocked Users \n 5. Logout/back?");
					send.flush();
					Response = ((String) receive.readObject());
					switch(Response) {
						case "1"://edit username
							send.writeObject("Enter your new username");
							send.flush();
							String username = null;
							while(true) {
								send.writeObject("Please enter a username: ");
								send.flush();
								username = (String) receive.readObject();
								if(d.getData("user", username) != null) {
									send.writeObject("Username is already taken");
									send.flush();
								} else {
									break;
								}
							}
							d.changeData(String tableName, Object data, //name);
							send.writeObject("Your new username has been set ", username);
							send.flush();
						case "2"://edit password
						case "3"://edit friends list
						case "4"://edit blocked users list
						case "5"://logout/back
					}
				case "2":\\message
				
				case "3": \\logout

				default:
			}
			*/

			
			//d.getData("user", username).getFriendsList()
			//d.getData("user", username).getFriendsList()
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
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
				ServerClient server = new ServerClient(socket);
				new Thread(server).start();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}