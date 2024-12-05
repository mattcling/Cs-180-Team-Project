package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


/**
 * A class which acts as the user client for the chat application.
 * it allows the user to interact with the server side of the chat application.
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
public class UserClient extends JComponent implements Runnable {

    private UserClient user;
    private JButton loginButton; // a button to change paint color
    private JButton createNewUserButton; // a button to change paint color
    private JButton exitButton; // a button to change paint color
    private final Lock lock = new ReentrantLock();
    private final Condition choiceMade = lock.newCondition();
    private JButton loginEnter;
    private JButton createEnter;
    
    //panels
    JPanel panel = new JPanel();
    
    private static volatile ArrayList<String> sendingText;

    private static volatile JTextField usernameText;
    private static volatile JTextField passwordText;

    private static volatile String mainMenuChoice = "0";
    private static volatile String inMainMenu = "";
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new UserClient());
        try (Socket socket = new Socket("localhost", 4343); ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream receive = new ObjectInputStream(socket.getInputStream()); Scanner sc = new Scanner(System.in)) {

            String chatUser = "";
            boolean loggedIn = false;
            while (!loggedIn) {
                //System.out.println("Server: " + receive.readObject());
                //System.out.println("Server: " + receive.readObject());

                //String choice = sc.nextLine();
                send.writeObject(mainMenuChoice);
                send.flush();
                if(!mainMenuChoice.equals(" ")) {
                    if ("1".equals(mainMenuChoice)) {//login
                        //System.out.println("in login");
                        if ("4".equals(mainMenuChoice)) {//pressed enter button
                            System.out.println("in enter button");
                            System.out.println("Server: " + receive.readObject());
                            String username = usernameText.getText();
                            send.writeObject(username);
                            send.flush();

                            System.out.println("Server: " + receive.readObject());
                            String password = passwordText.getText();
                            send.writeObject(password);
                            send.flush();

                            
                            String text = (String) receive.readObject();
                            if (text.equals("Welcome, ")) {
                                sendingText = new ArrayList<>();
                                sendingText.add(text);
                                sendingText.add("\n" + (String)receive.readObject());
                                loggedIn = true;
                            } else {
                                sendingText = new ArrayList<>();
                                sendingText.add((String)receive.readObject());
                            }
                        }
                    } else if ("2".equals(mainMenuChoice)) { // Create New User
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
                    } else if ("3".equals(mainMenuChoice)) { //exit
                        System.out.println("Server: " + receive.readObject());
                        break;
                    } else {
                        continue;
                    }
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

                        switch (input) {
                            case "1":
                                System.out.println("Server: " + receive.readObject());
                                //no worky :(
                                while (true) {
                                    String item = (String) receive.readObject();
                                    if ("\n".equals(item)) {
                                        break;
                                    }
                                    System.out.println(item);
                                }

                                System.out.println("Server: " + receive.readObject());
                                String chosenChat = sc.nextLine();
                                send.writeObject(chosenChat);
                                send.flush();
                                
                                //no worky :(
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
                                if (output.equals("User does not exist.")
                                        || output.equals("You are blocked by this user.")) {
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
                    default:
                        System.out.println("Server: " + receive.readObject());
                        continue;
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ActionListener mainMenuAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            lock.lock();
            try {
                if (e.getSource() == loginButton) {
                    mainMenuChoice = "1";
                    System.out.println("changed variable - 1");
                }
                if (e.getSource() == createNewUserButton) {
                    mainMenuChoice = "2";
                    System.out.println("changed variable - 2");
                }
                if (e.getSource() == exitButton) {
                    mainMenuChoice = "3";
                    System.out.println("changed variable - 3");
                }
                if (e.getSource() == loginEnter) {
                    mainMenuChoice = "4";
                    System.out.println("changed variable - 4");
                }

                choiceMade.signal();
            } finally {
                lock.unlock();
            }
        }
    };

    @Override
    public void run() { //open the program!
        JFrame frame = new JFrame("Test");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        switch (mainMenuChoice) {
            case "0":// main menu
                loginButton = new JButton("Login");
                createNewUserButton = new JButton("Create New User");
                exitButton = new JButton("Exit");

                panel.add(loginButton);
                panel.add(createNewUserButton);
                panel.add(exitButton);
                content.add(panel, BorderLayout.CENTER);

                loginButton.addActionListener(mainMenuAction);
                createNewUserButton.addActionListener(mainMenuAction);        
                exitButton.addActionListener(mainMenuAction);
                
            case "1": //login
                System.out.println("Login line 340");
                panel.removeAll();
                
                JLabel instructions1 = new JLabel("Please enter your username and password:");
                usernameText = new JTextField(10);
                passwordText = new JTextField(10);
                
                panel.add(instructions1);
                panel.add(usernameText);
                panel.add(passwordText);
                panel.add(loginEnter);
                panel.add(exitButton);
                content.add(panel, BorderLayout.NORTH);

                loginEnter.addActionListener(mainMenuAction);
                
                panel.revalidate();
                panel.repaint();
                break;
            case "2": //create new user
                System.out.println("Create New User");
                panel.removeAll();
                
                JLabel instructions2 = new JLabel("Please enter a username and password:");
                usernameText = new JTextField(10);
                passwordText = new JTextField(10);
                createEnter = new JButton("Enter");
                
                panel.add(instructions2);
                panel.add(usernameText);
                panel.add(passwordText);
                panel.add(createEnter);
                content.add(panel, BorderLayout.NORTH);
                
                panel.revalidate();
                panel.repaint();
                break;
            case "3": //exit
                System.out.println("Exit");
                panel.removeAll();
                JLabel exitText = new JLabel("Have a good day!");
            
                panel.add(exitText);
                content.add(panel, BorderLayout.NORTH);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.dispose();
                break;
            case "4"://clicked enter button on login
                usernameText.setText("");
                passwordText.setText("");
                
                 JOptionPane.showMessageDialog(null, "Error","Error!!",JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                break;
        }
    }
}



