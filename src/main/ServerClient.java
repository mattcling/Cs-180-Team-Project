package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * A class which acts as the server side of the chat application.
 * It receives messages from the user and sends messages to the user.
 *
 * <p>
 * Purdue University -- CS18000 -- Fall 2024</p>
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

    Socket socket;
    public static Database d = new Database();
    boolean stop;

    public ServerClient(Socket socket) { //created ServerClient socket through constructor
        this.socket = socket;
    }

    public void run() {
        System.out.printf("Connection received from %s\n", socket); //connection established
        String username = null;

        try (ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream receive = new ObjectInputStream(socket.getInputStream())) {
            boolean loggedIn = false;
            while(true){
                while (!loggedIn) { //loop while login is not complete (before login)
                    //send.writeObject("Hello, User!");
                    //send.flush();
                    //send.writeObject("Please choose an option:\n1. Login\n2. Create a new user");
                    //send.flush();
                    String response = (String) receive.readObject(); //store response input of menu option
                    if(!response.equals(" ")) {
                        if ("1".equals(response)) { // Login option
                            //send.writeObject("Enter your username:"); //prompt for username
                            //send.flush();
                            username = (String) receive.readObject(); //store username input

                            //send.writeObject("Enter your password:"); //prompt for password
                            //send.flush();
                            String password = (String) receive.readObject(); //store password input

                            if (d.containsObject("user", username)) { //check if user exists in database
                                User user = (User) d.getData("user", username); //get data of user and welcome user
                                if (user.getPassword().equals(password)) { //check if password entered matches
                                    send.writeObject("Welcome, ");//used
                                    send.flush();
                                    // send.writeObject(username + "!");//not recived
                                    // send.flush();
                                    loggedIn = true;
                                } else {
                                    send.writeObject("Invalid password.");//used
                                    continue;//error message for mismatching password
                                }
                            } else {
                                send.writeObject("Invalid username.");//used
                                continue; //error message for nonexisting username in database
                            }
                        } else if ("2".equals(response)) { // Create a new user option
                            while (true) {
                                //send.writeObject("Enter a username:"); //prompt to enter username for new user
                                //send.flush();
                                username = (String) receive.readObject(); //store username input

                                if (d.containsObject("user", username)) {
                                    //check if database contains username already
                                    send.writeObject("Username is already taken.");
                                    //error message for already existing user
                                    send.flush();
                                    continue;

                                } else {
                                    send.writeObject("Username is available."); //confirmation message
                                    send.flush();
                                    break;
                                }
                            }

                            //send.writeObject("Enter a password:"); //prompt to enter password
                            //send.flush();
                            String password = (String) receive.readObject(); //store password input

                            User newUser = new User(username, password);
                            //create new user object with given inputs as User fields
                            d.writeData(newUser, "user");

                            //send.writeObject("User created successfully! Please login to continue.");
                            continue;//confirmation message
                        } else {
                            //send.writeObject("Invalid option selected. Retry");
                            continue;
                            //error message for invalid option from menu entered
                        }
                    }
                }
                // Main menu
                boolean quit = false;

                while (true) {
                    // send.writeObject("Please choose an option:\n1. Create / Open Chat \n2. User Search "
                    //         + "\n3. Friends list \n4. Blocked Users List \n5. Quit");
                    // send.flush();
                    String choice = (String) receive.readObject();
                    String chatUser = "";
                    String action = "";
                    switch (choice) {
                        case "1": //create/open chat option
                            //send.writeObject("Your Friends: ");
                            //send.flush();
                            for (String friend : ((User) d.getData("user", username)).getFriendsList()) {
                                send.writeObject(friend); // display user's friends
                                send.flush();
                            }
                            // send.writeObject("\n");
                            // send.flush();
                            // send.writeObject("Please choose 1: \n1."
                            //         + " Open existing Chat \n2."
                            //         + " Create new chat \n3. Exit");
                            // send.flush();
                            action = (String) receive.readObject();
                            chatUser = "";

                            switch (action) {
                                case "1":
                                    send.writeObject("Below is all of your chats you can access");
                                    send.flush();
                                    d.loadOldData();
                                    if (((User) d.getData("user", username)).getChatIds().isEmpty()) {
                                        send.writeObject("You have no chats.");
                                        send.flush();
                                        send.writeObject("\n");
                                        send.flush();
                                        break;
                                    }
                                    
                                    for (String chats : (((User) d.getData("user", username)).getChatIds())) {
                                        send.writeObject(chats);
                                        send.flush();
                                    }
                                    send.writeObject("\n");
                                    send.flush();

                                    send.writeObject("Please enter the ID of chat you want to open");
                                    send.flush();

                                    String chatOpen = (String) receive.readObject();

                                    d.loadOldData();
                                    Chat existingChat = (Chat) d.getData("chat", chatOpen);
                                    // this no print
                                    List<Message> messages = existingChat.getMessages();
                                    for (Message message : messages) {
                                        send.writeObject(message.getSenderID() + ": " + message.getContents());
                                        send.flush();
                                    }
                                    send.writeObject("\n");
                                    send.flush();

                                    chatUser = "";
                                    break;
                                case "2":
                                    send.writeObject("Please enter the username of the"
                                            + " person you would like to chat with:");
                                    send.flush();
                                    chatUser = (String) receive.readObject();

                                    if (d.containsObject("user", chatUser)) {
                                        if (((User) d.getData("user", chatUser)).
                                                getBlockedUsers().contains(((User) d.getData("user", username)).
                                                        getUserName())) {
                                            send.writeObject("You are blocked by this user.");
                                            continue;
                                        }

                                        Chat chat = new Chat(username, chatUser);
                                        ((User) d.getData("user", username)).addChat(chat.getChatID());
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
                                        continue;
                                    }
                                    break;

                                case "3":
                                    send.writeObject("Exit");
                                    break;
                                default:
                                    //send.writeObject("Invalid option selected.");
                                    continue;

                            }
                            break;

                        case "2"://user search option
                            // send.writeObject("What is the username you would like to search for?");//used for a check
                            // send.flush();
                            String searchUser = (String) receive.readObject();
                            if (d.containsObject("user", searchUser)) {
                                send.writeObject("found");//expected to recieve
                                send.flush();
                                //now sending profile details:
                                String bioSend = ((User) d.getData("user", searchUser)).getProfile().getBio();
                                send.writeObject(searchUser);
                                send.writeObject(bioSend);
                                send.flush();
                                //reciving input
                                action = (String) receive.readObject();
                                switch (action) {
                                    case "1":
                                        String userName = ((User) d.getData("user", searchUser)).getUserName();
                                        stop = false;
                                        for (String friend : ((User) d.getData("user", username)).
                                                getFriendsList()) {
                                            if (friend.equals(userName)) {
                                                stop = true;
                                                send.writeObject("That user is already your friend");//used
                                                break;
                                            }
                                        }
                                        for (String blocked : ((User) d.getData("user", username))
                                                .getBlockedUsers()) {
                                            if (blocked.equals(userName)) {
                                                stop = true;
                                                send.writeObject("That user is blocked");//used
                                                break;
                                            }
                                        }
                                        if (!stop) {
                                            ((User) d.getData("user", username)).addFriend(userName);
                                            d.changeData("user", d.getData("user", username), username);
                                            send.writeObject("Friend added");//used
                                        }
                                        break;

                                    case "2": //block
                                        userName = ((User) d.getData("user", searchUser)).getUserName();
                                        stop = false;
                                        for (String blockedName : ((User) d.getData("user", username))
                                                .getBlockedUsers()) {
                                            if (blockedName.equals(userName)) {
                                                stop = true;
                                                send.writeObject("That user is already blocked");//used
                                                break;
                                            }
                                        }
                                        if (!stop) {
                                            ((User) d.getData("user", username)).addBlockedUser(userName);
                                            d.changeData("user", d.getData("user", username), username);
                                            send.writeObject("User blocked");//used
                                        }
                                        break;

                                    case "3":
                                        //send.writeObject("Exit");//not used
                                        break;
                                    default:
                                        //send.writeObject("Invalid option selected.");//not used
                                        break;
                                }
                            } else {
                                send.writeObject("User Does Not exist.");
                                send.flush();
                            }
                            break;

                        case "3"://friends list option
                            //send.writeObject("Your Friends: ");
                            //send.flush();
                            for (String friend : ((User) d.getData("user", username)).getFriendsList()) {
                                send.writeObject(friend);//need
                                send.flush();
                            }
                            send.writeObject("\n");//need
                            send.flush();
                            // send.writeObject("Please choose 1: \n 1. UnFriend \n 2. Block \n 3. Exit");
                            // send.flush();//dont use i dont think
                            String actionChoice = (String) receive.readObject();
                            switch (actionChoice) {
                                case "1":
                                    //send.writeObject("Please enter the username to unfriend");
                                    //send.flush();
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

                                case "2":
                                    //send.writeObject("Please enter the username to block");
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

                                case "3":
                                    //send.writeObject("Exited");
                                    break;
                                    
                                default:
                                    //send.writeObject("Invalid option selected.");
                                    continue;
                            }
                            break;

                        case "4": //blocked Users list
                            //send.writeObject("Your Blocked Users: ");
                            //send.flush();
                            for (String b : ((User) d.getData("user", username)).getBlockedUsers()) {
                                send.writeObject(b);
                                send.flush();
                            }
                            send.writeObject("\n");
                            send.flush();
                            // send.writeObject("Please choose 1: \n 1. UnBlock \n 2. Exit");
                            // send.flush();
                            actionChoice = (String) receive.readObject();
                            switch (actionChoice) {
                                case "1":
                                    send.writeObject("Please enter the username to unblock");
                                    send.flush();
                                    String unblock = (String) receive.readObject();
                                    stop = true;
                                    for (String b : ((User) d.getData("user", username)).getBlockedUsers()) {
                                        if ((b.equals(unblock))) {
                                            stop = false;
                                            System.out.println("stop = false");
                                            System.out.println(stop);
                                            ((User) d.getData("user", username)).unBlockUser(unblock);
                                            d.changeData("user", d.getData("user", username), username);
                                            send.writeObject("User unblocked");
                                            System.out.println("blocked HERE");
                                            send.flush();
                                            break;
                                        }
                                    }//says not blocked and then user unblocked afterwards
                                    if (stop == true) {
                                        send.writeObject("That user is not blocked");
                                        send.flush();
                                    }
                                    continue;

                                case "2":
                                    send.writeObject("Exited");
                                    break;

                                default:
                                    //send.writeObject("Invalid option selected.");//not used
                                    continue;
                            }

                            break;

                        case "5": //quit
                            //send.writeObject("Goodbye!");//used
                            quit = true;
                            loggedIn = false;
                            break;
                        case "6": //profile case
                            String bio = ((User) d.getData("user", username)).getProfile().getBio();
                            //username and password are stored alr
                            String password = ((User) d.getData("user", username)).getPassword();

                            send.writeObject(username);
                            send.writeObject(bio);
                            send.writeObject(password);
                            send.flush();

                            String response = (String) receive.readObject();
                            switch (response) {
                                case "1"://edited recives
                                    //username = (String) receive.readObject();
                                    password = (String) receive.readObject();
                                    bio = (String) receive.readObject();
                                    // System.out.println(username);
                                    // System.out.println(password);
                                    // System.out.println(bio);
                                    ((User) d.getData("user", username)).getProfile().editBio(bio);
                                    //((User) d.getData("user", username)).setUserName(username);
                                    ((User) d.getData("user", username)).setPassword(password);
                                    d.writeData((User) d.getData("user", username), "user");
                                    break;
                                case "2"://exit
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            //send.writeObject("Invalid option selected.");//not used
                            continue;
                    }
                    if (quit) {
                        break;
                    }
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
