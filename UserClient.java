import java.io.*;
import java.net.*;
import java.util.*;

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
			try (Socket socket = new Socket("localhost", 4343);) {
				ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream receive = new ObjectInputStream(socket.getInputStream());
				Scanner sc = new Scanner(System.in);

				//while loop here eventually
				String fromServer;
				fromServer = (String) receive.readObject();
				System.out.println(fromServer);
				fromServer = (String) receive.readObject();
				System.out.println(fromServer);
				send.writeObject(sc.nextLine());
				while(true) {
					System.out.println((String) receive.readObject());

					System.out.println((String) receive.readObject());
					String response = sc.nextLine();
					send.writeObject(response);
					send.flush();
					if (response.equals("1")) { 
						System.out.println((String) receive.readObject());
						String username = sc.nextLine();
						send.writeObject(username);
						send.flush();

						System.out.println((String) receive.readObject()); 
						String password = sc.nextLine();
						send.writeObject(password);
						send.flush();

						String serverResponse = (String) receive.readObject();
						System.out.println(serverResponse);

						if (serverResponse.contains("Welcome")) {
							break;
						}

					} else if(response.equals("2")) {
						while (true) { 
							System.out.println((String) receive.readObject()); 
							String username = sc.nextLine();
							send.writeObject(username);
							send.flush();
							String serverResponse = (String) receive.readObject();
							System.out.println(serverResponse);
							if (!serverResponse.equals("Username is already taken")) {
								break;
							}
						}
						System.out.println((String) receive.readObject()); 
						String password = sc.nextLine();
						send.writeObject(password);
						send.flush();

	
						System.out.println("User created successfully!");
					}
				}
						
						// fromServer = (String) receive.readObject();
						// System.out.println(fromServer);
						// fromServer = (String) receive.readObject();
						// System.out.println(fromServer);
						// send.writeObject(sc.nextLine());
						// send.flush();
						// fromServer = (String) receive.readObject();
						// System.out.println(fromServer);
						// send.writeObject(sc.nextLine());
						// send.flush();
						// fromServer = (String) receive.readObject();
						// System.out.println(fromServer);

				} catch (IOException  | ClassNotFoundException e) {
						e.printStackTrace();
				}
		}
    
}