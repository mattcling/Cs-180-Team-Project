import java.io.*;
import java.net.*;

public class ServerClient implements Runnable {
    Socket socket;
    public static Database d = new Database();

    public ServerClient(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.printf("Connection received from %s\n", socket);
        String username = null;

        try (ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream receive = new ObjectInputStream(socket.getInputStream())) {

            send.writeObject("Hello, User!");
            send.flush();
            send.writeObject("Please choose an option:\n1. Login\n2. Create a new user");
            send.flush();

            String response = (String) receive.readObject();
            if ("1".equals(response)) { // Login
                send.writeObject("Enter your username:");
                send.flush();
                username = (String) receive.readObject();

                send.writeObject("Enter your password:");
                send.flush();
                String password = (String) receive.readObject();

                if (d.containsObject("user", username)) {
                    User user = (User) d.getData("user", username);
                    if (user.getPassword().equals(password)) {
                        send.writeObject("Welcome, " + username + "!");
                    } else {
                        send.writeObject("Invalid password.");
                    }
                } else {
                    send.writeObject("Invalid username.");
                }
            } else if ("2".equals(response)) { // Create a new user
                while (true) {
                    send.writeObject("Enter a username:");
                    send.flush();
                    username = (String) receive.readObject();

                    if (d.containsObject("user", username)) {
                        send.writeObject("Username is already taken.");
                    } else {
                        send.writeObject("Username is available.");
                        break;
                    }
                    send.flush();
                }

                send.writeObject("Enter a password:");
                send.flush();
                String password = (String) receive.readObject();

                User newUser = new User(username, password);
                d.writeData(newUser, "user");

                send.writeObject("User created successfully! Please login to continue.");
            } else {
                send.writeObject("Invalid option selected. Restart the client.");
            }
			send.writeObject("Hello," + username + "!");
            send.flush();
            send.writeObject("Please choose an option:\n1. Message User \n2. User Search \n3. Freinds lsit \n4. Quit");
			send.flush();
            String choice = (String) receive.readObject();
            switch (choice) {
                case "1"://message user
                    
                case "2"://user search
                    send.writeObject("What is the username you would like to search for?");
                    send.flush();
                    String searchUser = (String) receive.readObject();
                    if (d.containsObject("user", searchUser)) {
                        send.writeObject("Please Choose 1: \n 1. Friend usser\n 2. Block user");
                        send.flush();
						String action = (String) receive.readObject();
                        switch (action) {
                            case "1"://friend
                                (User) d.getData("user", searchUser);
                                
                            case "2"://block
                            
                            default:
                        }
                        //add friend, block
                    }
                case "3"://friends list
                    send.writeObject("Your Friends: ");
                    send.flush();
                    for (String friend : ((User) d.getData("user", username)).getFriendsList()) {
                        send.writeObject(friend);
                        send.flush();
					}
                    send.writeObject("\n");
                case "4"://quit
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        
    }

    public static void main(String[] args) {
        d.initializeDatabase();

        try (ServerSocket serverSocket = new ServerSocket(4343)) {
            System.out.printf("Server is running on %s\n", serverSocket);

            while (true) {
                Socket socket = serverSocket.accept();
                ServerClient server = new ServerClient(socket);
                new Thread(server).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
