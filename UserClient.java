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
            while (!loggedIn) {
                System.out.println("Server: " + receive.readObject());
                System.out.println("Server: " + receive.readObject());

                String choice = sc.nextLine();
                send.writeObject(choice);
                send.flush();

                //login
                if ("1".equals(choice)) {
                    System.out.println("Server: " + receive.readObject());
                    String username = sc.nextLine();
                    send.writeObject(username);
                    send.flush();

                    System.out.println("Server: " + receive.readObject());
                    String password = sc.nextLine();
                    send.writeObject(password);
                    send.flush();
                    String text = (String) receive.readObject();
                    if(text.equals("Welcome, ")) {
                        System.out.println("Server: " + text);
                         System.out.println("Server: " + receive.readObject());
                        loggedIn = true;
                    } else {
                        System.out.println("Server: " + receive.readObject());
                    }
                } else if ("2".equals(choice)) { //new user 
                    while (true) {
                        System.out.println("Server: " + receive.readObject());
                        String username = sc.nextLine();
                        send.writeObject(username);
                        send.flush();

                        String serverResponse = (String) receive.readObject();
                        System.out.println("Server: " + serverResponse);

                        if ("Username is available.".equals(serverResponse)) {
                            break;
                        }
                    }

                    System.out.println("Server: " + receive.readObject());
                    String password = sc.nextLine();
                    
                    send.writeObject(password);
                    send.flush();

                    System.out.println("Server: " + receive.readObject());


                } else {
                    System.out.println("Server: " + receive.readObject());
                }
            }

			
//-------------------------


            // Main menu
            //System.out.println("Server: " + receive.readObject());

            while (true) {
                System.out.println("Server: " + receive.readObject());
                String choice = sc.nextLine();
                send.writeObject(choice);
                send.flush();
                switch (choice) {
                    case "1"://message user
                        //will do once yall finish the code for it
                        break;
                    case "2"://user search
                        System.out.println("Server: " + receive.readObject()); //What user would you like to search for?
                        String searchUser = sc.nextLine();
                        send.writeObject(searchUser); //Client sends the user they want to search for
                        send.flush();
                        String serverString = ((String) receive.readObject()); //
                        if ("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit".equals(serverString)) {
                            System.out.println("Server: " + serverString);
                            String action = sc.nextLine();
                            send.writeObject(action);
                            send.flush();
                            System.out.println("Server: " + receive.readObject());
                        } else {
                            System.out.println("Server: " + serverString);
                        }
                        break;
                    case "3"://friends list actions
                        System.out.println("Server: " + receive.readObject());
                        while (true) {
                            String item = (String) receive.readObject();
                            if ("\n".equals(item)) {
                                break;
                            }
                            System.out.println("Server: " + item); 
                        }
                        System.out.println("Server: " + receive.readObject());
                        String actionChoice = sc.nextLine();
                        send.writeObject(actionChoice);
                        send.flush();
                        switch (actionChoice) {
                            case "1"://unfriend
                                System.out.println("Server: " + receive.readObject());
                                String unfriend = sc.nextLine();
                                send.writeObject(unfriend);
                                send.flush();
                                System.out.println("Server: " + receive.readObject());
                                break;
                            case "2"://block
                                System.out.println("Server: " + receive.readObject());
                                String block = sc.nextLine();
                                send.writeObject(block);
                                send.flush();
                                System.out.println("Server: " + receive.readObject());
                                break;
                            default:
                                System.out.println("Server: " + receive.readObject());
                                break;
                        }
                        break;
                    case "4"://quit
                        System.out.println("Server: " + receive.readObject());
                        break;
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
