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

            boolean loggedIn = false;
            while (!loggedIn) {
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
                            send.writeObject("Welcome, ");
                            send.flush();
                            send.writeObject(username + "!");
                            send.flush();
                            loggedIn = true;
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
                            send.flush();
                        } else {
                            send.writeObject("Username is available.");
                            send.flush();
                            break;
                        }
                        send.flush();
                    }

                    send.writeObject("Enter a password:");
                    send.flush();
                    String password = (String) receive.readObject();

                    User newUser = new User(username, password);
                    d.writeData(newUser,"user");

                    send.writeObject("User created successfully! Please login to continue.");
                } else {
                    send.writeObject("Invalid option selected. Retry");
                }
            }
			
//-------------------------


            // Main menu
            //send.writeObject("Hello," + username + "!");
            //send.flush();

            while(true) {
                send.writeObject("Please choose an option:\n1. Create / Open Chat \n2. User Search \n3. Friends list \n4. Quit");
                send.flush();
                String choice = (String) receive.readObject();
                switch (choice) {

                    case "1": //create/open chat
                        send.writeObject("Please enter the username of the person you would like to chat with:");
                        send.flush();
                        String chatUser = (String) receive.readObject();
                        if (d.containsObject("user", chatUser)) {
                            if (((User) d.getData("user", chatUser)).getBlockedUsers().contains(((User) d.getData("user", username)).getUserID())) {
                                send.writeObject("You are blocked by this user.");
                                continue;
                            }
                            
                            Chat chat = new Chat(username, chatUser);
                            send.writeObject("Chat created with " + chatUser);
                            send.flush();
                            while (true) {
                                send.writeObject("Please enter your message:");
                                send.flush();
                                String message = (String) receive.readObject();
                                chat.sendMessage(message, username);
                                send.writeObject("Message sent!");
                                send.flush();
                                send.writeObject("Would you like to send another message? (Y/N)");
                                send.flush();
                                String response = (String) receive.readObject();
                                if (response.equals("N")) {
                                    break;
                                }
                            }
                        } else {
                            send.writeObject("User does not exist.");
                        }
                        break;
    
                        
                        
                    case "2"://user search
                        send.writeObject("What is the username you would like to search for?");
                        send.flush();
                        String searchUser = (String) receive.readObject(); 
                        if (d.containsObject("user", searchUser)) {
                            send.writeObject("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit"); // isn't getting received
                            System.out.println("here after if"); 
                            send.flush();
                            String action = (String) receive.readObject();
                            boolean stop;
                            switch (action) {
                                case "1"://friend
                                    String userName = ((User) d.getData("user", searchUser)).getUserName();
                                    stop = false;
                                    for (String friend : ((User) d.getData("user", username)).getFriendsList()) {
                                        if (friend.equals(userName)){
                                            stop = true;
                                            send.writeObject("That user is already your friend");
                                            break;
                                        }
                                    }
                                    if (!stop) {
                                        ((User)d.getData("user", username)).addFriend(userName);
                                        d.changeData("user", d.getData("user", username), username);
                                        send.writeObject("Friend added");
                                    }
                                    break;
                                case "2"://block
                                    userName = ((User) d.getData("user", searchUser)).getUserName();
                                    stop = false;
                                    for (String blockedName : ((User) d.getData("user", username)).getBlockedUsers()) {
                                        if (blockedName.equals(userName)){
                                            stop = true;
                                            send.writeObject("That user is already blocked");
                                            break;
                                        }
                                    }
                                    if (!stop) {
                                        ((User)d.getData("user", username)).addBlockedUser(userName);
                                        d.changeData("user", d.getData("user", username), username);
                                        send.writeObject("User blocked");
                                    }
                                    break;
                                case "3":
                                    send.writeObject("Exit");
                                    break;
                                default:
                                    send.writeObject("Invalid option selected.");
                                    break;
                            } 
                        } else {
                            send.writeObject("User Does Not exist.");
                            send.flush();
                        }
                        break;

                    case "3"://friends list
                        send.writeObject("Your Friends: ");
                        send.flush();
                        try {
                            for (String friend : ((User) d.getData("user", username)).getFriendsList()) {
                                send.writeObject(friend);
                                send.flush();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        send.writeObject("\n");
                        send.flush();
                        send.writeObject("Please choose 1: \n 1. UnFriend \n 2. Block \n 3. Exit");
                        send.flush();
                        String actionChoice = (String) receive.readObject();
                        boolean stop;
                        switch (actionChoice) {
                            case "1"://unfriend
                                send.writeObject("Please enter the username to unfriend");
                                send.flush();
                                String unfriend = (String) receive.readObject();
                                String userName = ((User) d.getData("user", unfriend)).getUserName();
                                stop = false;
                                for (String friendId : ((User) d.getData("user", username)).getFriendsList()) {
                                    if (!(friendId.equals(userName))){
                                        stop = true;
                                        send.writeObject("That user is already not your friend");
                                        break;
                                    }
                                }
                                if (!stop) {
                                    ((User)d.getData("user", username)).removeFriend(userName);
                                    d.changeData("user", d.getData("user", username), username);
                                    send.writeObject("Freind Removed");
                                }
                                break;
                            case "2"://block
                                send.writeObject("Please enter the username to unfriend");
                                send.flush();
                                String block = (String) receive.readObject();
                                userName = ((User) d.getData("user", block)).getUserName();
                                stop = false;
                                for (String blockedId : ((User) d.getData("user", username)).getBlockedUsers()) {
                                    if (blockedId.equals(userName)){
                                        stop = true;
                                        send.writeObject("That user is already blocked");
                                        break;
                                    }
                                }
                                if (!stop) {
                                    ((User)d.getData("user", username)).addBlockedUser(userName);
                                    d.changeData("user", d.getData("user", username), username);
                                    send.writeObject("User Blocked");
                                }
                                break;
                            case "3"://exit
                                send.writeObject("Exited");
                                break;
                            default:
                                send.writeObject("Invalid option selected.");
                                continue;
                        }
                        break;  
                    case "4"://quit
                        send.writeObject("Goodbye!");
                        break;
                }
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
