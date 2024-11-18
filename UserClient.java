import java.io.*;
import java.net.*;
import java.util.Scanner;
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

public class UserClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4343);
             ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream receive = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {

            String chatUser = "";
            boolean loggedIn = false;
            while (!loggedIn) {
                System.out.println("Server: " + receive.readObject());
                System.out.println("Server: " + receive.readObject());

                String choice = sc.nextLine();
                send.writeObject(choice);
                send.flush();

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
                    if (text.equals("Welcome, ")) {
                        System.out.println("Server: " + text);
                        System.out.println("Server: " + receive.readObject());
                        loggedIn = true;
                    } else {
                        System.out.println("Server: " + receive.readObject());
                    }
                } else if ("2".equals(choice)) {
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

            while (true) {
                System.out.println("Server: " + receive.readObject());
                String choice = sc.nextLine();
                send.writeObject(choice);
                send.flush();

                switch (choice) {
                    case "1":
                        while (true) {
                            String item = (String) receive.readObject();
                            if ("\n".equals(item)) {
                                break;
                            }
                            System.out.println(item);
                        }

                        System.out.println("Options: " + receive.readObject());
                        String input = sc.nextLine();
                        send.writeObject(input);
                        send.flush();

                        switch(input) {
                            case "1":
                                System.out.println("Server: " + receive.readObject());
                                send.writeObject(chatUser);
                                while (true) {
                                    String item = (String) receive.readObject();
                                    if ("\n".equals(item)) {
                                        break;
                                    }
                                    System.out.println(item);
                                }
                                break;
                            case "2":
                                System.out.println("Server: " + receive.readObject());
                                chatUser = sc.nextLine();
                                send.writeObject(chatUser);
                                send.flush();

                                String output = (String) receive.readObject();
                                System.out.println(output);
                                if (output.equals("User does not exist.") ||
                                        output.equals("You are blocked by this user.")) {
                                    continue;
                                }

                                while (true) {
                                    System.out.println("Server: " + receive.readObject());
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
                            default:
                                System.out.println("Server: " + receive.readObject());
                                continue;
                        }
                        break;
                    case "2":
                        System.out.println("Server: " + receive.readObject());
                        String searchUser = sc.nextLine();
                        send.writeObject(searchUser);
                        send.flush();
                        String serverString = ((String) receive.readObject());
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
                    case "3":
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
                            case "1":
                                System.out.println("Server: " + receive.readObject());
                                String unfriend = sc.nextLine();
                                send.writeObject(unfriend);
                                send.flush();
                                System.out.println("Server: " + receive.readObject());
                                break;
                            case "2":
                                System.out.println("Server: " + receive.readObject());
                                String block = sc.nextLine();
                                send.writeObject(block);
                                send.flush();
                                System.out.println("Server: " + receive.readObject());
                                break;
                            case "3":
                                System.out.println("Server: " + receive.readObject());
                                break;
                            default:
                                System.out.println("Server: " + receive.readObject());
                                break;
                        }
                        break;
                    case "4":
                        System.out.println("Server: " + receive.readObject());
                        while (true) {
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
                            case "1":
                                System.out.println("Server: " + receive.readObject());
                                String unblock = sc.nextLine();
                                send.writeObject(unblock);
                                send.flush();
                                System.out.println("Server: " + receive.readObject());
                                break;
                            case "2":
                                System.out.println("Server: " + receive.readObject());
                                break;
                            default:
                                System.out.println("Server: " + receive.readObject());
                                break;
                        }
                        break;
                    case "5":
                        System.out.println("Server: " + receive.readObject());
                        break;
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
