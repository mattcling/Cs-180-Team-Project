import java.io.*;
import java.net.*;

public class ServerClient implements Runnable {
    Socket socket;
    public static Database d = new Database();
    boolean stop; //for conditional control

    public ServerClient(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.printf("Connection received from %s\n", socket);
        String username = null;

        try (ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream receive = new ObjectInputStream(socket.getInputStream())) {

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
                    d.writeData(newUser, "user");

                    send.writeObject("User created successfully! Please login to continue.");
                } else {
                    send.writeObject("Invalid option selected. Retry");
                }
            }

//-------------------------


            // Main menu

            while (true) {
                send.writeObject("Please choose an option:\n1. Create / Open Chat \n2. User Search \n3. Friends list \n4. Blocked Users List \n5. Quit");
                send.flush();
                String choice = (String) receive.readObject();
                switch (choice) {

                    case "1": //create/open chat
                        send.writeObject("Please enter the username of the person you would like to chat with:");
                        send.flush();
                        String chatUser = (String) receive.readObject();
                        if (d.containsObject("user", chatUser)) {
                            if (((User) d.getData("user", chatUser)).getBlockedUsers().contains(((User) d.getData("user", username)).getUserName())) {
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
                            switch (action) {
                                case "1"://friend
                                    String userName = ((User) d.getData("user", searchUser)).getUserName();
                                    stop = false;
                                    for (String friend : ((User) d.getData("user", username)).getFriendsList()) {
                                        if (friend.equals(userName)) {
                                            stop = true;
                                            send.writeObject("That user is already your friend");
                                            break;
                                        }
                                    }
                                    for (String blcoked : ((User) d.getData("user", username)).getBlockedUsers()) {
                                        if (blcoked.equals(userName)) {
                                            stop = true;
                                            send.writeObject("That user is blocked");
                                            break;
                                        }
                                    }
                                    if (!stop) {
                                        ((User) d.getData("user", username)).addFriend(userName);
                                        d.changeData("user", d.getData("user", username), username);
                                        send.writeObject("Friend added");
                                    }
                                    break;
                                case "2": //block
                                    userName = ((User) d.getData("user", searchUser)).getUserName();
                                    stop = false;
                                    for (String blockedName : ((User) d.getData("user", username)).getBlockedUsers()) {
                                        if (blockedName.equals(userName)) {
                                            stop = true;
                                            send.writeObject("That user is already blocked");
                                            break;
                                        }
                                    }
                                    if (!stop) {
                                        ((User) d.getData("user", username)).addBlockedUser(userName);
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
                        for (String friend : ((User) d.getData("user", username)).getFriendsList()) {
                            send.writeObject(friend);
                            send.flush();
                        }
                        send.writeObject("\n");
                        send.flush();
                        send.writeObject("Please choose 1: \n 1. UnFriend \n 2. Block \n 3. Exit");
                        send.flush();
                        String actionChoice = (String) receive.readObject();
                        switch (actionChoice) {
                            case "1"://unfriend
                                send.writeObject("Please enter the username to unfriend");
                                send.flush();
                                String unfriend = (String) receive.readObject();
                                stop = true;
                                for (String friend : ((User) d.getData("user", username)).getFriendsList()) {
                                    if ((friend.equals(unfriend))) {
                                        stop = false;
                                        ((User) d.getData("user", username)).removeFriend(unfriend);
                                        d.changeData("user", d.getData("user", username), username);
                                        send.writeObject("Friend Removed");
                                        send.flush();
                                        break;
                                    }
                                }
                                if (stop) {
                                    send.writeObject("That user is not your friend");
                                    send.flush();
                                }
                                continue;
                            case "2"://block
                                send.writeObject("Please enter the username to block");
                                send.flush();
                                String block = (String) receive.readObject();
                                stop = true;
                                for (String b : ((User) d.getData("user", username)).getBlockedUsers()) {
                                    if ((b.equals(block))) {
                                        stop = false;
                                        send.writeObject("That user is already blocked");
                                        send.flush();
                                        break;
                                    }
                                }
                                if (stop) {
                                    ((User) d.getData("user", username)).addBlockedUser(block);
                                    ((User) d.getData("user", username)).removeFriend(block);
                                    d.changeData("user", d.getData("user", username), username);
                                    send.writeObject("Blocked User");
                                    send.flush();
                                }
                                continue;
                            case "3"://exit
                                send.writeObject("Exited");
                                break;
                            default:
                                send.writeObject("Invalid option selected.");
                                continue;
                        }
                        break;
                    case "4": //blocked Users list 
                        send.writeObject("Your Blocked Users: ");
                        send.flush();
                        for (String b : ((User) d.getData("user", username)).getBlockedUsers()) {
                            send.writeObject(b);
                            send.flush();
                        }
                        send.writeObject("\n");
                        send.flush();
                        send.writeObject("Please choose 1: \n 1. UnBlock \n 2. Exit");
                        send.flush();
                        actionChoice = (String) receive.readObject();
                        switch (actionChoice) {
                            case "1": //unblock
                                send.writeObject("Please enter the username to unblock");
                                send.flush();
                                String unblock = (String) receive.readObject();
                                stop = true;
                                for (String b : ((User) d.getData("user", username)).getBlockedUsers()) {
                                    if ((b.equals(unblock))) {
                                        stop = false;
                                        ((User) d.getData("user", username)).unBlockUser(unblock);
                                        d.changeData("user", d.getData("user", username), username);
                                        send.writeObject("User unblocked");
                                        send.flush();
                                        break;
                                    }
                                }
                                if (stop) {
                                    send.writeObject("That user is not blocked");
                                    send.flush();
                                }
                                continue;
                            case "2"://exit
                                send.writeObject("Exited");
                                break;
                            default:
                                send.writeObject("Invalid option selected.");
                                continue;
                        }
                        break;
                    case "5"://quit
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
