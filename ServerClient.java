import java.io.*;
import java.net.*;

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
            //try with resources to create ObjectOutputStream send

            boolean loggedIn = false;
            while (!loggedIn) { //loop while login is not complete (before login)
                send.writeObject("Hello, User!");
                send.flush();
                send.writeObject("Please choose an option:\n1. Login\n2. Create a new user");
                send.flush();
                //printed menu

                String response = (String) receive.readObject(); //store response input of menu option
                if ("1".equals(response)) { // Login option
                    send.writeObject("Enter your username:"); //prompt for username
                    send.flush();
                    username = (String) receive.readObject(); //store username input

                    send.writeObject("Enter your password:"); //prompt for password
                    send.flush();
                    String password = (String) receive.readObject(); //store password input

                    if (d.containsObject("user", username)) { //check if user exists in database
                        User user = (User) d.getData("user", username); //get data of user and welcome uesr
                        if (user.getPassword().equals(password)) { //check if password entered matches
                            send.writeObject("Welcome, ");
                            send.flush();
                            send.writeObject(username + "!");
                            send.flush();
                            loggedIn = true;
                        } else {
                            send.writeObject("Invalid password."); //error message for mismatching password
                        }
                    } else {
                        send.writeObject("Invalid username."); //error message for nonexisting username in database
                    }
                } else if ("2".equals(response)) { // Create a new user option
                    while (true) {
                        send.writeObject("Enter a username:"); //prompt to enter username for new user
                        send.flush();
                        username = (String) receive.readObject(); //store username input

                        if (d.containsObject("user", username)) { //check if database contains username already
                            send.writeObject("Username is already taken."); //error message for already existing user
                            send.flush();
                        } else {
                            send.writeObject("Username is available."); //confirmation message
                            send.flush();
                            break;
                        }
                        send.flush();
                    }

                    send.writeObject("Enter a password:"); //prompt to enter password
                    send.flush();
                    String password = (String) receive.readObject(); //store password input

                    User newUser = new User(username, password); //create new user object with given inputs as User fields
                    d.writeData(newUser, "user");

                    send.writeObject("User created successfully! Please login to continue."); //confirmation message
                } else {
                    send.writeObject("Invalid option selected. Retry"); //error message for invalid option from menu entered
                }
            }

//-------------------------


            // Main menu

            while (true) {
                send.writeObject("Please choose an option:\n1. Create / Open Chat \n2. User Search \n3. Friends list \n4. Blocked Users List \n5. Quit");
                //print menu
                send.flush();
                String choice = (String) receive.readObject();
                //store option choice input
                switch (choice) {

                    case "1": //create/open chat option
                        send.writeObject("Please enter the username of the person you would like to chat with:");
                        //prompt to enter username of correspondant
                        send.flush();
                        String chatUser = (String) receive.readObject();
                        //store correspondant username
                        if (d.containsObject("user", chatUser)) {
                            if (((User) d.getData("user", chatUser)).getBlockedUsers().contains(((User) d.getData("user", username)).getUserName())) {
                                send.writeObject("You are blocked by this user.");
                                continue;
                            }
                            //erroring below
                            Chat chat = new Chat(username, chatUser);

                            
                            send.writeObject("Chat created with " + chatUser);
                            send.flush();
                            while (true) {
                                send.writeObject("Please enter your message:");
                                send.flush();

                                String message = (String) receive.readObject();
                                System.out.println("Message: " + message + " line 117");

                                System.out.println("about to add message to array list");
                                chat.sendMessage(message, username);
                                System.out.println("message successfully added to arraylist");
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


                    case "2"://user search option
                        send.writeObject("What is the username you would like to search for?"); //prompt for entering username to search for
                        send.flush();
                        String searchUser = (String) receive.readObject(); //store username that is to be searched input
                        if (d.containsObject("user", searchUser)) { //check if username entered exists in database and print it
                            send.writeObject("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit"); // print menu
                            send.flush();
                            String action = (String) receive.readObject(); //store action option from choices input
                            switch (action) {
                                case "1"://add user as friend
                                    String userName = ((User) d.getData("user", searchUser)).getUserName();
                                    stop = false;
                                    for (String friend : ((User) d.getData("user", username)).getFriendsList()) { //check if username is already in friendslist
                                        if (friend.equals(userName)) {
                                            stop = true;
                                            send.writeObject("That user is already your friend"); //error message if user is already in friendslist
                                            break;
                                        }
                                    }
                                    for (String blocked : ((User) d.getData("user", username)).getBlockedUsers()) {
                                        if (blocked.equals(userName)) { //check if user is blocked
                                            stop = true;
                                            send.writeObject("That user is blocked"); //error message if user is blocked because blocked users cannot be friended
                                            break;
                                        }
                                    }
                                    if (!stop) {
                                        ((User) d.getData("user", username)).addFriend(userName); //adding user as friend if all conditions met
                                        d.changeData("user", d.getData("user", username), username);
                                        send.writeObject("Friend added"); //confirmation message for friend added
                                    }
                                    break;
                                case "2": //block
                                    userName = ((User) d.getData("user", searchUser)).getUserName();
                                    stop = false;
                                    for (String blockedName : ((User) d.getData("user", username)).getBlockedUsers()) {
                                        if (blockedName.equals(userName)) { //checking if userName is already blocked
                                            stop = true;
                                            send.writeObject("That user is already blocked"); //error message if user is already blocked
                                            break;
                                        }
                                    }
                                    if (!stop) { //if no other conditions satisfied, enter this condition
                                        ((User) d.getData("user", username)).addBlockedUser(userName); //adding user to blocked users
                                        d.changeData("user", d.getData("user", username), username); //updating user data for blocked user
                                        send.writeObject("User blocked"); //confirmation message for user blocked
                                    }
                                    break;
                                case "3":
                                    send.writeObject("Exit"); //exit message for exit option
                                    break;
                                default:
                                    send.writeObject("Invalid option selected."); //error message for invalid menu option entered
                                    break;
                            }
                        } else {
                            send.writeObject("User Does Not exist."); //error message for username not in database
                            send.flush();
                        }
                        break;

                    case "3"://friends list option
                        send.writeObject("Your Friends: "); //displaying friends
                        send.flush();
                        for (String friend : ((User) d.getData("user", username)).getFriendsList()) { //loop through and display friends
                            send.writeObject(friend); //display user's friends
                            send.flush();
                        }
                        send.writeObject("\n");
                        send.flush();
                        send.writeObject("Please choose 1: \n 1. UnFriend \n 2. Block \n 3. Exit"); //print menu
                        send.flush();
                        String actionChoice = (String) receive.readObject();
                        //store choice input in actionChoice
                        switch (actionChoice) {
                            case "1"://unfriend option
                                send.writeObject("Please enter the username to unfriend"); //prompt to enter username to unfriend
                                send.flush();
                                String unfriend = (String) receive.readObject(); //store username to be unfriended in unfriend input
                                stop = true;
                                for (String friend : ((User) d.getData("user", username)).getFriendsList()) { //loop through friendslist
                                    if ((friend.equals(unfriend))) { //check if unfriend input is in friendslist
                                        stop = false;
                                        ((User) d.getData("user", username)).removeFriend(unfriend); //remove friend from friendslist
                                        d.changeData("user", d.getData("user", username), username); //update user data
                                        send.writeObject("Friend Removed"); //confirmation message
                                        send.flush();
                                        break;
                                    }
                                }
                                if (stop) {
                                    send.writeObject("That user is not your friend"); //error message for unfriend username not in friendslist
                                    send.flush();
                                }
                                continue;
                            case "2"://block option
                                send.writeObject("Please enter the username to block"); //prompt for blocking
                                send.flush();
                                String block = (String) receive.readObject(); //store block username input
                                stop = true;
                                for (String b : ((User) d.getData("user", username)).getBlockedUsers()) { //loop through blocked users list
                                    if ((b.equals(block))) { //check if blocked user username is in blockedusers list
                                        stop = false;
                                        send.writeObject("That user is already blocked"); //if already in list, show error message
                                        send.flush();
                                        break;
                                    }
                                }
                                if (stop) {
                                    ((User) d.getData("user", username)).addBlockedUser(block); //else add username to blockedusers list
                                    ((User) d.getData("user", username)).removeFriend(block); //remove user from friendslist
                                    d.changeData("user", d.getData("user", username), username); //update user data
                                    send.writeObject("Blocked User"); //confirmation message
                                    send.flush();
                                }
                                continue;
                            case "3"://exit option
                                send.writeObject("Exited"); //display exit
                                break;
                            default:
                                send.writeObject("Invalid option selected."); //invalid option selected message
                                continue;
                        }
                        break;
                    case "4": //blocked Users list 
                        send.writeObject("Your Blocked Users: "); //display blocked users
                        send.flush();
                        for (String b : ((User) d.getData("user", username)).getBlockedUsers()) { //loop through blocked uesrs list and show each one
                            send.writeObject(b);
                            send.flush();
                        }
                        send.writeObject("\n");
                        send.flush();
                        send.writeObject("Please choose 1: \n 1. UnBlock \n 2. Exit");
                        //print menu
                        send.flush();
                        actionChoice = (String) receive.readObject(); //store option choice in actionChoice variable
                        switch (actionChoice) {
                            case "1": //unblock option
                                send.writeObject("Please enter the username to unblock"); //unblock prompt
                                send.flush();
                                String unblock = (String) receive.readObject(); //store unblock user username input in unblock variable
                                stop = true;
                                for (String b : ((User) d.getData("user", username)).getBlockedUsers()) { //loop through blocked users list
                                    if ((b.equals(unblock))) { //check if username equals unblock username entered
                                        stop = false;
                                        ((User) d.getData("user", username)).unBlockUser(unblock); //unblock user
                                        d.changeData("user", d.getData("user", username), username); //update user data
                                        send.writeObject("User unblocked"); //confirmation message
                                        send.flush();
                                        break;
                                    }
                                }
                                if (stop) {
                                    send.writeObject("That user is not blocked"); //error message for if user is not blocked so cannot unblock
                                    send.flush();
                                }
                                continue;
                            case "2"://exit
                                send.writeObject("Exited"); //exit message
                                break;
                            default:
                                send.writeObject("Invalid option selected."); //invalid option message
                                continue;
                        }
                        break;
                    case "5"://quit
                        send.writeObject("Goodbye!"); //goodbye message
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) { //error handling
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        d.initializeDatabase(); //initialise new database

        try (ServerSocket serverSocket = new ServerSocket(4343)) { //connect to port
            System.out.printf("Server is running on %s\n", serverSocket); //server running on port confirmation message

            while (true) {
                Socket socket = serverSocket.accept();
                ServerClient server = new ServerClient(socket);
                new Thread(server).start();
            }
        } catch (IOException e) { //error handling
            e.printStackTrace();
        }
    }
}
