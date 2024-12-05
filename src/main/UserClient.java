package main;

import java.awt.*;

import java.awt.event.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
import javax.swing.BoxLayout;
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
 * Purdue University -- CS18000 -- Fall 2024
 * </p>
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

    // panels
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
                // System.out.println("Server: " + receive.readObject());
                // System.out.println("Server: " + receive.readObject());

                // String choice = sc.nextLine();
                send.writeObject(mainMenuChoice);
                send.flush();
                if (!mainMenuChoice.equals(" ")) {
                    if ("1".equals(mainMenuChoice)) {// login
                        // System.out.println("in login");
                        if ("4".equals(mainMenuChoice)) {// pressed enter button
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
                    } else if ("3".equals(mainMenuChoice)) { // exit
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
                                if (output.equals("User does not exist.") || output.equals("You are blocked by this user.")) {
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
            return;
        }

        try (Socket socket = new Socket("localhost", 4343); ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream receive = new ObjectInputStream(socket.getInputStream())) {

            // Send login request
            send.writeObject("1"); // Option 1 for login
            send.flush();

            // Step 1: Server asks for username
            String serverPrompt = (String) receive.readObject();
            if (!serverPrompt.equals("Enter your username:")) {
                JOptionPane.showMessageDialog(frame, "Unexpected response from server.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            send.writeObject(username);
            send.flush();

            // Step 2: Server asks for password
            serverPrompt = (String) receive.readObject();
            if (!serverPrompt.equals("Enter your password:")) {
                JOptionPane.showMessageDialog(frame, "Unexpected response from server.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            send.writeObject(password);
            send.flush();

            // Step 3: Server response (success or failure)
            String response = (String) receive.readObject();
            if (response.equals("Welcome, ")) {
                String welcomeMessage = (String) receive.readObject();
                JOptionPane.showMessageDialog(frame, response + welcomeMessage, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                switchToChatWindow(frame, username);
            } else {
                JOptionPane.showMessageDialog(frame, response, "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void handleCreateUser(JFrame frame) {
        String username = usernameText.getText();
        String password = passwordText.getText();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Socket socket = new Socket("localhost", 4343); ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream receive = new ObjectInputStream(socket.getInputStream())) {

            // Send create user request
            send.writeObject("2"); // Option 2 for creating a user
            send.flush();

            // Step 1: Enter username
            String serverPrompt = (String) receive.readObject();
            if (!serverPrompt.equals("Enter a username:")) {
                JOptionPane.showMessageDialog(frame, "Unexpected response from server.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            send.writeObject(username);
            send.flush();

            // Step 2: Check username availability
            String usernameResponse = (String) receive.readObject();
            if (usernameResponse.equals("Username is already taken.")) {
                JOptionPane.showMessageDialog(frame, usernameResponse, "Create User Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Step 3: Enter password
            serverPrompt = (String) receive.readObject();
            if (!serverPrompt.equals("Enter a password:")) {
                JOptionPane.showMessageDialog(frame, "Unexpected response from server.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            send.writeObject(password);
            send.flush();

            // Step 4: Success or failure
            String finalResponse = (String) receive.readObject();
            if (finalResponse.equals("User created successfully! Please login to continue.")) {
                JOptionPane.showMessageDialog(frame, finalResponse, "Success", JOptionPane.INFORMATION_MESSAGE);
                returnToMainMenu(frame);
            } else {
                JOptionPane.showMessageDialog(frame, "Unexpected error: " + finalResponse, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void switchToChatWindow(JFrame frame, String username) {
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton chatButton = new JButton("Chats");
        JButton friendsListButton = new JButton("Friends List");
        JButton blockedUsersButton = new JButton("Blocked User List");
        JButton userSearchButton = new JButton("User Search");
        JButton logOutButton = new JButton("Log out");

        chatButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        friendsListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        blockedUsersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        userSearchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel
        chatPanel.add(welcomeLabel);
        chatPanel.add(Box.createVerticalStrut(20)); // Add spacing
        chatPanel.add(chatButton);
        chatPanel.add(Box.createVerticalStrut(10));
        chatPanel.add(friendsListButton);
        chatPanel.add(Box.createVerticalStrut(10));
        chatPanel.add(blockedUsersButton);
        chatPanel.add(Box.createVerticalStrut(10));
        chatPanel.add(userSearchButton);
        chatPanel.add(Box.createVerticalStrut(10));
        chatPanel.add(logOutButton);

        // Add panel to frame
        frame.getContentPane().removeAll();
        frame.add(chatPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

        // Define button actions
        chatButton.addActionListener(e -> handleMessages(frame, username));
        friendsListButton.addActionListener(e -> handleFriendsList(frame, username));
        blockedUsersButton.addActionListener(e -> handleBlockedUsers(frame, username));
        userSearchButton.addActionListener(e -> handleUserSearch(frame, username));
        logOutButton.addActionListener(e -> returnToMainMenu(frame));
    }

    private void handleMessages(JFrame frame, String username) {
        JOptionPane.showMessageDialog(frame, "Messages functionality is not implemented yet.", "Messages", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleFriendsList(JFrame frame, String username) {
        JOptionPane.showMessageDialog(frame, "Friends List functionality is not implemented yet.", "Friends List", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleBlockedUsers(JFrame frame, String username) {
        JOptionPane.showMessageDialog(frame, "Blocked User List functionality is not implemented yet.", "Blocked User List", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleUserSearch(JFrame frame, String username) {
        JOptionPane.showMessageDialog(frame, "User Search functionality is not implemented yet.", "User Search", JOptionPane.INFORMATION_MESSAGE);
    }

}
