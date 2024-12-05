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
                if (!mainMenuChoice.equals(" ")) {
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
                                sendingText.add("\n" + (String) receive.readObject());
                                loggedIn = true;
                            } else {
                                sendingText = new ArrayList<>();
                                sendingText.add((String) receive.readObject());
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
    public void run() {
        // Initialize the JFrame
        JFrame frame = new JFrame("Chat Application");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Create the initial main menu panel
        JPanel mainMenuPanel = new JPanel();
        loginButton = new JButton("Login");
        createNewUserButton = new JButton("Create New User");
        exitButton = new JButton("Exit");

        mainMenuPanel.add(loginButton);
        mainMenuPanel.add(createNewUserButton);
        mainMenuPanel.add(exitButton);

        frame.add(mainMenuPanel, BorderLayout.CENTER);

        // Define action listeners for main menu buttons
        loginButton.addActionListener(e -> switchToLoginPanel(frame));
        createNewUserButton.addActionListener(e -> switchToCreateUserPanel(frame));
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    private void switchToLoginPanel(JFrame frame) {
        JPanel loginPanel = new JPanel();
        JLabel instructions = new JLabel("Please enter your username and password:");
        usernameText = new JTextField(10);
        passwordText = new JTextField(10);
        JButton loginEnter = new JButton("Enter");
        JButton backButton = new JButton("Back");

        loginPanel.add(instructions);
        loginPanel.add(usernameText);
        loginPanel.add(passwordText);
        loginPanel.add(loginEnter);
        loginPanel.add(backButton);

        frame.getContentPane().removeAll(); // Clear the frame
        frame.add(loginPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

        // Define action listeners for login buttons
        loginEnter.addActionListener(e -> handleLogin(frame));
        backButton.addActionListener(e -> returnToMainMenu(frame));
    }

    private void switchToCreateUserPanel(JFrame frame) {
        JPanel createUserPanel = new JPanel();
        JLabel instructions = new JLabel("Please enter a username and password:");
        usernameText = new JTextField(10);
        passwordText = new JTextField(10);
        JButton createEnter = new JButton("Create");
        JButton backButton = new JButton("Back");

        createUserPanel.add(instructions);
        createUserPanel.add(usernameText);
        createUserPanel.add(passwordText);
        createUserPanel.add(createEnter);
        createUserPanel.add(backButton);

        frame.getContentPane().removeAll(); // Clear the frame
        frame.add(createUserPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

        // Define action listeners for create user buttons
        createEnter.addActionListener(e -> handleCreateUser(frame));
        backButton.addActionListener(e -> returnToMainMenu(frame));
    }

    private void returnToMainMenu(JFrame frame) {
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.add(loginButton);
        mainMenuPanel.add(createNewUserButton);
        mainMenuPanel.add(exitButton);

        frame.getContentPane().removeAll(); // Clear the frame
        frame.add(mainMenuPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void handleLogin(JFrame frame) {
        String username = usernameText.getText();
        String password = passwordText.getText();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Add logic for sending login credentials to the server
            JOptionPane.showMessageDialog(frame, "Logging in...", "Login", JOptionPane.INFORMATION_MESSAGE);
            // Transition to the next screen or handle errors
        }
    }

    private void handleCreateUser(JFrame frame) {
        String username = usernameText.getText();
        String password = passwordText.getText();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Add logic for sending new user data to the server
            JOptionPane.showMessageDialog(frame, "Creating user...", "Create User", JOptionPane.INFORMATION_MESSAGE);
            // Transition to the next screen or handle errors
        }
    }
}


