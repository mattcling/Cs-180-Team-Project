package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UserClient {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JTextField usernameField, passwordField, chatInputField, searchField;
    private JTextArea chatArea, friendsArea, blockedArea;

    private Socket socket;
    private ObjectOutputStream send;
    private ObjectInputStream receive;

    private String username;

    public UserClient() {
        try {
            // Connect to the server
            socket = new Socket("localhost", 4343);
            send = new ObjectOutputStream(socket.getOutputStream());
            receive = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initMainMenu();
        initLoginPanel();
        initCreateUserPanel();
        initLoggedInMenu();
        initChatsPanel();
        initFriendsListPanel();
        initBlockedUsersPanel();

        frame.add(mainPanel);
        frame.setVisible(true);
        showPanel("MainMenu");
    }

    private void initMainMenu() {
        JPanel mainMenu = new JPanel(new GridLayout(3, 1));

        JButton loginButton = new JButton("Login");
        JButton createUserButton = new JButton("Create New User");
        JButton exitButton = new JButton("Exit");

        loginButton.addActionListener(e -> showPanel("Login"));
        createUserButton.addActionListener(e -> showPanel("CreateUser"));
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });

        mainMenu.add(loginButton);
        mainMenu.add(createUserButton);
        mainMenu.add(exitButton);

        mainPanel.add(mainMenu, "MainMenu");
    }

    private void initLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(4, 1));

        usernameField = new JTextField("Enter Username");
        passwordField = new JTextField("Enter Password");
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        loginButton.addActionListener(e -> handleLogin());
        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        loginPanel.add(usernameField);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(backButton);

        mainPanel.add(loginPanel, "Login");
    }

    private void initCreateUserPanel() {
        JPanel createUserPanel = new JPanel(new GridLayout(4, 1));

        JTextField newUserField = new JTextField("Enter Username");
        JTextField newPasswordField = new JTextField("Enter Password");
        JButton createButton = new JButton("Create");
        JButton backButton = new JButton("Back");

        createButton.addActionListener(e -> {
            try {
                send.writeObject("2"); // Create new user
                send.flush();
                send.writeObject(newUserField.getText());
                send.flush();
                String serverResponse = (String) receive.readObject();
                if (serverResponse.equals("Username is already taken.")) {
                    JOptionPane.showMessageDialog(frame, serverResponse, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                send.writeObject(newPasswordField.getText());
                send.flush();
                JOptionPane.showMessageDialog(frame, "User created successfully! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                showPanel("Login");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Error creating user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        createUserPanel.add(newUserField);
        createUserPanel.add(newPasswordField);
        createUserPanel.add(createButton);
        createUserPanel.add(backButton);

        mainPanel.add(createUserPanel, "CreateUser");
    }

    private void initLoggedInMenu() {
        JPanel loggedInMenu = new JPanel(new GridLayout(5, 1));

        JButton chatsButton = new JButton("Chats");
        JButton userSearchButton = new JButton("Search Users");
        JButton friendsListButton = new JButton("Friends List");
        JButton blockedUsersButton = new JButton("Blocked Users");
        JButton logoutButton = new JButton("Logout");

        chatsButton.addActionListener(e -> showPanel("Chats"));
        userSearchButton.addActionListener(e -> handleUserSearch());
        friendsListButton.addActionListener(e -> handleFriendsList());
        blockedUsersButton.addActionListener(e -> handleBlockedUsers());
        logoutButton.addActionListener(e -> showPanel("MainMenu"));

        loggedInMenu.add(chatsButton);
        loggedInMenu.add(userSearchButton);
        loggedInMenu.add(friendsListButton);
        loggedInMenu.add(blockedUsersButton);
        loggedInMenu.add(logoutButton);

        mainPanel.add(loggedInMenu, "LoggedInMenu");
    }

    private void initChatsPanel() {
        JPanel chatsPanel = new JPanel(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatInputField = new JTextField();

        JButton sendMessageButton = new JButton("Send");
        JButton backButton = new JButton("Back");

        sendMessageButton.addActionListener(e -> {
            String message = chatInputField.getText();
            if (!message.isEmpty()) {
                sendMessageToServer(message);
                chatArea.append("Me: " + message + "\n");
                chatInputField.setText("");
            }
        });

        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        chatsPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        chatsPanel.add(chatInputField, BorderLayout.SOUTH);
        chatsPanel.add(sendMessageButton, BorderLayout.EAST);
        chatsPanel.add(backButton, BorderLayout.NORTH);

        mainPanel.add(chatsPanel, "Chats");
    }

    private void initFriendsListPanel() {
        JPanel friendsListPanel = new JPanel(new BorderLayout());

        friendsArea = new JTextArea();
        friendsArea.setEditable(false);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        friendsListPanel.add(new JScrollPane(friendsArea), BorderLayout.CENTER);
        friendsListPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(friendsListPanel, "FriendsList");
    }

    private void initBlockedUsersPanel() {
        JPanel blockedListPanel = new JPanel(new BorderLayout());

        blockedArea = new JTextArea();
        blockedArea.setEditable(false);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }); 
        blockedListPanel.add(new JScrollPane(blockedArea), BorderLayout.CENTER);
        blockedListPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(blockedListPanel, "BlockedList");
    }

    private void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            send.writeObject("1"); // Login
            send.flush();
            send.writeObject(username);
            send.flush();
            send.writeObject(password);
            send.flush();

            String response = (String) receive.readObject();
            if (response.startsWith("Welcome")) {
                this.username = username;
                JOptionPane.showMessageDialog(frame, response + " " + username, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                showPanel("LoggedInMenu");
            } else {
                JOptionPane.showMessageDialog(frame, response, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error logging in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessageToServer(String message) {
        try {
            send.writeObject(message);
            send.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to send message.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUserSearch() {
        try {
            String searchUser = JOptionPane.showInputDialog(frame, "Enter username to search:");
            if (searchUser != null && !searchUser.isEmpty()) {
                send.writeObject("2");
                send.flush();
                send.writeObject(searchUser);
                send.flush();

                String response = (String) receive.readObject();//shown
                //prints main menu for some reason:FIX
                JOptionPane.showMessageDialog(frame, response, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error searching user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleFriendsList() {
        try {
            send.writeObject("3");
            send.flush();
            StringBuilder friends = new StringBuilder("Friends:\n");
            String friend;
            while (!(friend = (String) receive.readObject()).equals("\n")) {
                friends.append(friend).append("\n");
            }
            friendsArea.setText(friends.toString());
            showPanel("FriendsList");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error retrieving friends list.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBlockedUsers() {
        try {
            send.writeObject("4");
            send.flush();
            StringBuilder blockedUsers = new StringBuilder("Blocked Users:\n");
            String blocked;
            while (!(blocked = (String) receive.readObject()).equals("\n")) {
                blockedUsers.append(blocked).append("\n");
            }
            blockedArea.setText(blockedUsers.toString());
            showPanel("BlockedList");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error retrieving blocked users.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserClient::new);
    }
}
