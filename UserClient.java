import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UserClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4343);
             ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream receive = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {
            
            boolean loggedIn = false;
            while (!loggedIn) {//setts up a login loop until successsfully logged in
                System.out.println("Server: " + receive.readObject());//elcome and propmting
                System.out.println("Server: " + receive.readObject());

                String choice = sc.nextLine(); //recives input 
                send.writeObject(choice);
                send.flush();

                if ("1".equals(choice)) {//login
                    System.out.println("Server: " + receive.readObject()); //takes username input
                    String username = sc.nextLine();
                    send.writeObject(username);
                    send.flush();

                    System.out.println("Server: " + receive.readObject()); //takes password input
                    String password = sc.nextLine();
                    send.writeObject(password);
                    send.flush();

                    String text = (String) receive.readObject();
                    if(text.equals("Welcome, ")) {//if login was successful
                        System.out.println("Server: " + text);
                         System.out.println("Server: " + receive.readObject());
                        loggedIn = true;
                    } else {//login was not successfull (either beasue of wrong username or password - all here)
                        System.out.println("Server: " + receive.readObject());
                    }
                } else if ("2".equals(choice)) { //new user creation
                    while (true) {//loops until valid username
                        System.out.println("Server: " + receive.readObject());//recives username input
                        String username = sc.nextLine();
                        send.writeObject(username);
                        send.flush();

                        String serverResponse = (String) receive.readObject();
                        System.out.println("Server: " + serverResponse);

                        if ("Username is available.".equals(serverResponse)) {
                            break;
                        }
                    }

                    System.out.println("Server: " + receive.readObject());//recives password input
                    String password = sc.nextLine();
                    
                    send.writeObject(password);
                    send.flush();

                    System.out.println("Server: " + receive.readObject());//succes or fail

                } else {
                    System.out.println("Server: " + receive.readObject());//invalid
                }
            }

			
//-------------------------


            // Main menu

            while (true) {//keeps running untill finished/exited
                System.out.println("Server: " + receive.readObject());//gives choice to message, search, see friends, see blocked users, or exit
                String choice = sc.nextLine();//takes input
                send.writeObject(choice);
                send.flush();
                switch (choice) {
                    case "1"://message user
                        System.out.println("Server: " + receive.readObject()); //prompt who to chat with
                        String chatUser = sc.nextLine();
                        send.writeObject(chatUser); //send who to chat to
                        send.flush();

                        
                        String output = (String) receive.readObject();
                        System.out.println(output); //recives and prints if user exists or not  
                        if (output.equals(("User does not exist."))) {//if issue, break switch
                            System.out.println("Server: " + output);
                            continue;
                        } 
                        if (output.equals("You are blocked by this user.")) {//if issue break switch
                            System.out.println("Server: " + output);
                            continue;
                        }
                        
                        while (true) {
                            System.out.println("Server: " + receive.readObject());
                            //System.out.println("Server: " + receive.readObject());
                            String message = sc.nextLine();
                            

                            
                            send.writeObject(message); 
                            send.flush();
                            

                            System.out.println("Server: " + receive.readObject());
                            System.out.println("Server: " + receive.readObject());
                            String response = sc.nextLine();
                            send.writeObject(response); 
                            send.flush();
                            if (response.equals("N")) {
                                break;
                            }
                        }
                        break;
                    case "2"://user search
                        System.out.println("Server: " + receive.readObject()); //What user would you like to search for?
                        String searchUser = sc.nextLine();
                        send.writeObject(searchUser); //Client sends the user they want to search for
                        send.flush();
                        String serverString = ((String) receive.readObject()); 
                        if ("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit".equals(serverString)) {//if found then the line will be returned
                            System.out.println("Server: " + serverString);//asks to select friend, block, or edit
                            String action = sc.nextLine();//sends selection 
                            send.writeObject(action);
                            send.flush();
                            System.out.println("Server: " + receive.readObject());//recives and prints only 1 line regardless of action
                        } else {//if user is not found
                            System.out.println("Server: " + serverString);
                        }
                        break;
                    case "3"://friends list actions
                        System.out.println("Server: " + receive.readObject());//writes "Your Friends: " header
                        while (true) {//prints lines untill they are no longer sent(printing the friends usernames)
                            String item = (String) receive.readObject();
                            if ("\n".equals(item)) {//detecting when the list is done
                                break;
                            }
                            System.out.println("Server: " + item); 
                        }
                        System.out.println("Server: " + receive.readObject());//promptin selction of undriend, block, or exit
                        String actionChoice = sc.nextLine();//taking and sending the input
                        send.writeObject(actionChoice);
                        send.flush();
                        switch (actionChoice) {
                            case "1"://unfriend
                                System.out.println("Server: " + receive.readObject());//enter username prompt
                                String unfriend = sc.nextLine();//recives and sends username
                                send.writeObject(unfriend);
                                send.flush();
                                System.out.println("Server: " + receive.readObject());//prints 1 line wheter successfull or not
                                break;
                            case "2"://block
                                System.out.println("Server: " + receive.readObject());//enter username prompt
                                String block = sc.nextLine();//recives and sends username
                                send.writeObject(block);
                                send.flush();
                                System.out.println("Server: " + receive.readObject());//prints result line
                                break;
                            case "3"://exit 
                                System.out.println("Server: " + receive.readObject());//prints successfull exit
                                break;
                            default://invalid input
                                System.out.println("Server: " + receive.readObject());//prints invalid input 
                                break;
                        }
                        break;
                    case "4"://blocked list actions - same structure as friends list actions(see above)
                        System.out.println("Server: " + receive.readObject());//header text "Your blocked users: "
                        while (true) {//same loop structure as to display friends
                            String item = (String) receive.readObject();
                            if ("\n".equals(item)) {
                                break;
                            }
                            System.out.println("Server: " + item); 
                        }
                        System.out.println("Server: " + receive.readObject());
                        actionChoice = sc.nextLine();
                        send.writeObject(actionChoice);
                        send.flush();
                        switch (actionChoice) {
                            case "1"://unblock
                                System.out.println("Server: " + receive.readObject());
                                String unblock = sc.nextLine();
                                send.writeObject(unblock);
                                send.flush();
                                System.out.println("Server: " + receive.readObject());
                                break;
                            case "2"://exit
                                System.out.println("Server: " + receive.readObject());
                                break;
                            default:
                                System.out.println("Server: " + receive.readObject());//invalid input
                                break;
                        }
                        break;
                    case "5"://quit
                        System.out.println("Server: " + receive.readObject());//goodbye
                        break;
                }

            }
        } catch (IOException | ClassNotFoundException e) {//error handeling
            e.printStackTrace();
        }
    }
}
