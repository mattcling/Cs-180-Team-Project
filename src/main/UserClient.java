package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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

    private JTextField usernameField, passwordField, chatInputField, searchField, bioEditField, usernameEditField,
            passwordEditField;

    private JTextArea chatArea, friendsArea, blockedArea, instructionsArea;

    private Socket socket;
    private ObjectOutputStream send;
    private ObjectInputStream receive;

    private String username;
    private boolean leaveChat = false;


    public UserClient() {
        try {
            // Connect to the server
            socket = new Socket("localhost", 4343);
            send = new ObjectOutputStream(socket.getOutputStream());
            receive = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to connect to server.", "Error",
                    JOptionPane.ERROR_MESSAGE);
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
        //initProfilePanel();
        initChatsPanel();
        initFriendsListPanel();
        initFriendsOptionsPanel();
        initBlockedListPanel();
        initBlockedOptionsPanel();

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

        usernameField = new JTextField("Sid");
        passwordField = new JTextField("monkey");
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        loginButton.addActionListener(e -> handleLogin());
        backButton.addActionListener(e -> {
            showPanel("MainMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
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

        JTextField newUserField = new JTextField("Username");
        JTextField newPasswordField = new JTextField("Password");
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
                JOptionPane.showMessageDialog(frame, "User created successfully! Please log in.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                showPanel("Login");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Error creating user.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        createUserPanel.add(newUserField);
        createUserPanel.add(newPasswordField);
        createUserPanel.add(createButton);
        createUserPanel.add(backButton);

        mainPanel.add(createUserPanel, "CreateUser");
    }

    private void initLoggedInMenu() {
        JPanel loggedInMenu = new JPanel(new GridLayout(6, 1));

        JButton profileButton = new JButton("Edit Profile");
        JButton chatsButton = new JButton("Chats");
        JButton userSearchButton = new JButton("Search Users");
        JButton friendsListButton = new JButton("Friends List");
        JButton blockedUsersButton = new JButton("Blocked Users");
        JButton logoutButton = new JButton("Logout");

        profileButton.addActionListener(e -> {
            initProfilePanel();
            showPanel("ProfilePanel");
        });
        chatsButton.addActionListener(e -> handleChat());
        userSearchButton.addActionListener(e -> handleUserSearch());
        friendsListButton.addActionListener(e -> handleFriendsList());
        blockedUsersButton.addActionListener(e -> handleBlockedList());
        logoutButton.addActionListener(e -> {
            showPanel("MainMenu");
            try {
                send.writeObject("5");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        loggedInMenu.add(profileButton);
        loggedInMenu.add(chatsButton);
        loggedInMenu.add(userSearchButton);
        loggedInMenu.add(friendsListButton);
        loggedInMenu.add(blockedUsersButton);
        loggedInMenu.add(logoutButton);

        mainPanel.add(loggedInMenu, "LoggedInMenu");
    }

    private void initProfilePanel() {//uner development profile page
        ArrayList<String> info = receiveProfile();
        JPanel profilePanel = new JPanel(new GridLayout(6, 1));

        JTextArea instructionsArea = new JTextArea("     Edit your bio, and password here (displayed in order)",
                1, 30);
        instructionsArea.setEditable(false);
        usernameEditField = new JTextField(info.get(0));
        usernameEditField.setEditable(false);
        passwordEditField = new JTextField(info.get(1));
        bioEditField = new JTextField(info.get(2));
        JButton saveButton = new JButton("Save Changes");
        JButton backButton = new JButton("Exit");

        saveButton.addActionListener(e -> {
            handleSaveProfile();
            showPanel("LoggedInMenu");
        });
        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("2");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        profilePanel.add(instructionsArea);
        profilePanel.add(usernameEditField);
        profilePanel.add(passwordEditField);
        profilePanel.add(bioEditField);
        profilePanel.add(saveButton);
        profilePanel.add(backButton);

        mainPanel.add(profilePanel, "ProfilePanel");
    }

    private void initChatsPanel() {
        JPanel chatsPanel = new JPanel(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatInputField = new JTextField();

        JButton sendMessageButton = new JButton("Send");
        JButton backButton = new JButton("Back");
        JButton deleteButton = new JButton("Delete Messages [brokey :(]"); //WE'RE COOKED

        sendMessageButton.addActionListener(e -> {
            String message = chatInputField.getText();
            if (!message.isEmpty()) {
                sendMessageToServer(message);
                chatArea.append(username + ": " + message + "\n");
                chatInputField.setText("");
            }
        });

        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            chatArea.setText("");
            leaveChat = true;
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            handleDeleteMessages();
        });

        chatsPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        chatsPanel.add(chatInputField, BorderLayout.SOUTH);
        chatsPanel.add(sendMessageButton, BorderLayout.EAST);
        chatsPanel.add(backButton, BorderLayout.NORTH);
        chatsPanel.add(deleteButton, BorderLayout.WEST);

        mainPanel.add(chatsPanel, "Chats");
    }

    private void initFriendsListPanel() {
        JPanel friendsListPanel = new JPanel(new BorderLayout());

        friendsArea = new JTextArea();
        friendsArea.setEditable(false);
        JButton backButton = new JButton("Back");
        JButton editButton = new JButton("Edit List");

        editButton.addActionListener(e -> showPanel("friendsOptionsPanel"));
        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        friendsListPanel.add(new JScrollPane(friendsArea), BorderLayout.CENTER);
        friendsListPanel.add(editButton, BorderLayout.NORTH);
        friendsListPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(friendsListPanel, "FriendsList");
    }

    private void initFriendsOptionsPanel() {
        JPanel friendsOptionsPanel = new JPanel(new GridLayout(3, 1));

        JButton unfriendUserButton = new JButton("UnFriend User");
        JButton blockUserButton = new JButton("Block User");
        JButton backButton = new JButton("Exit");

        unfriendUserButton.addActionListener(e -> unfriendUser());
        blockUserButton.addActionListener(e -> blockUser());
        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        friendsOptionsPanel.add(unfriendUserButton);
        friendsOptionsPanel.add(blockUserButton);
        friendsOptionsPanel.add(backButton);

        mainPanel.add(friendsOptionsPanel, "friendsOptionsPanel");
        showPanel("friendsOptionsPanel");
    }

    private void initBlockedListPanel() {
        JPanel blockedListPanel = new JPanel(new BorderLayout());

        blockedArea = new JTextArea();
        blockedArea.setEditable(false);
        JButton backButton = new JButton("Back");
        JButton editButton = new JButton("Edit List");

        editButton.addActionListener(e -> showPanel("blockedOptionsPanel"));
        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("3");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        blockedListPanel.add(new JScrollPane(blockedArea), BorderLayout.CENTER);
        blockedListPanel.add(editButton, BorderLayout.NORTH);
        blockedListPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(blockedListPanel, "BlockedList");
    }

    private void initBlockedOptionsPanel() {
        JPanel blockedOptionsPanel = new JPanel(new GridLayout(3, 1));

        JButton unblockUserButton = new JButton("UnBlock User");
        JButton backButton = new JButton("Exit");

        unblockUserButton.addActionListener(e -> unblockUser());
        backButton.addActionListener(e -> {
            showPanel("LoggedInMenu");
            try {
                send.writeObject("2");
                send.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error sending request to server.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        blockedOptionsPanel.add(unblockUserButton);
        blockedOptionsPanel.add(backButton);

        mainPanel.add(blockedOptionsPanel, "blockedOptionsPanel");
        showPanel("blockedOptionsPanel");
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
                JOptionPane.showMessageDialog(frame, response + " " + username, "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                showPanel("LoggedInMenu");
            } else {
                JOptionPane.showMessageDialog(frame, response, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error logging in.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessageToServer(String message) {
        try {
            send.writeObject(message);
            send.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to send message.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleSaveProfile() {
        try {
            send.writeObject("1");
            send.flush();
            send.writeObject(bioEditField.getText());
            send.flush();
            send.writeObject(passwordEditField.getText());
            send.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Failed to edit profile.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private ArrayList<String> receiveProfile() {
        try {
            send.writeObject("6");// sending they selected edit profile
            send.flush();
            ArrayList<String> profile = new ArrayList<>();
            profile.add((String) receive.readObject());
            profile.add((String) receive.readObject());
            profile.add((String) receive.readObject());
            return profile;
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Failed to receive profile.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void handleChat() {
        new Thread(() -> {
            try {
                sendMessageToServer("1");
                showPanel("Chats");

                String usernameToChat = JOptionPane.showInputDialog(frame, "Enter the username" +
                        " of the person you want to chat with:");
                if (usernameToChat != null && !usernameToChat.isEmpty()) {
                    send.writeObject(usernameToChat);
                    send.flush();

                    String response = (String) receive.readObject();
                    if (response.equals("User does not exist.") || response.equals("You are blocked by this user.")) {
                        JOptionPane.showMessageDialog(frame, response, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Read chat history
                    SwingUtilities.invokeLater(() -> chatArea.setText(""));
                    while (true) {
                        String item = (String) receive.readObject();
                        if ("\n".equals(item)) break;
                        final String chatMessage = item;
                        SwingUtilities.invokeLater(() -> chatArea.append(chatMessage + "\n"));
                    }
                }

                // Listen for new messages
                while (!leaveChat) {
                    if (receive.available() > 0) {
                        String serverMessage = (String) receive.readObject();
                        SwingUtilities.invokeLater(() -> chatArea.append(serverMessage + "\n"));
                    }

                    Thread.sleep(100); // Avoid busy-waiting
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Failed to chat with the server.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    private void handleDeleteMessages() {
        try {
            send.writeObject("4");
            send.flush();
            String response = (String) receive.readObject();
            if (response.equals("Messages Deleted")) {
                JOptionPane.showMessageDialog(frame, "Messages Deleted", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (response.equals("No messages to delete")) {
                JOptionPane.showMessageDialog(frame, "No messages to delete", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            showPanel("LoggedInMenu");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Server did not respond properly", "Error",
                    JOptionPane.ERROR_MESSAGE);
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

                //String response = (String) receive.readObject();
                //JOptionPane.showMessageDialog(frame, response, "Search Result", JOptionPane.INFORMATION_MESSAGE);

                //for testing for now:
                String response = (String) receive.readObject();
                if (response.equals("found")) {//change so it checks if it says user panel
                    //recive  mroe for the details (3)
                    String usernameSearched = (String) receive.readObject();
                    String bioSearched = (String) receive.readObject();
                    String q2 = JOptionPane.showInputDialog(frame, ("Profile:\n     Username: " +
                            usernameSearched + "\n     Bio: " + bioSearched + "\nWhat you want to do? " +
                            "write \"friend\"," +
                            " \"search\", or, \"exit\""));
                    if (q2 != null && !q2.isEmpty() && q2.equals("friend")) {
                        send.writeObject("1");
                        send.flush();
                        String gha = (String) receive.readObject();
                        JOptionPane.showMessageDialog(frame, gha, "Search Result",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else if (q2 != null && !q2.isEmpty() && q2.equals("block")) {
                        send.writeObject("2");
                        send.flush();
                        String gha = (String) receive.readObject();
                        JOptionPane.showMessageDialog(frame, gha, "Search Result",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (response.equals("User Does Not exist.")) {
                    JOptionPane.showMessageDialog(frame, response, "Search Result",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error searching user.", "Error",
                    JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(frame, "Error retrieving friends list.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void unfriendUser() {
        try {
            String friend = JOptionPane.showInputDialog(frame, "Enter username to search:");
            if (friend != null && !friend.isEmpty()) {
                send.writeObject("1");
                send.flush();
                send.writeObject(friend);
                send.flush();
            }
            send.writeObject(friend);
            send.flush();
            String response = (String) receive.readObject();
            if (response.equals("That user is not your friend")) {
                JOptionPane.showMessageDialog(frame, "That user is not your friend", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (response.equals("Friend Removed")) {
                JOptionPane.showMessageDialog(frame, "Friend Removed", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            showPanel("LoggedInMenu");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Server did not respond properly", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void blockUser() {
        try {
            String friend = JOptionPane.showInputDialog(frame, "Enter username to search:");
            if (friend != null && !friend.isEmpty()) {
                send.writeObject("2");
                send.flush();
                send.writeObject(friend);
                send.flush();
            }
            send.writeObject(friend);
            send.flush();
            String response = (String) receive.readObject();
            if (response.equals("Blocked User")) {
                JOptionPane.showMessageDialog(frame, "Blocked User", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (response.equals("That user is already blocked")) {
                JOptionPane.showMessageDialog(frame, "That user is already blocked", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            showPanel("LoggedInMenu");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Server did not respond properly", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBlockedList() {
        try {
            send.writeObject("4");
            send.flush();
            StringBuilder blockeds = new StringBuilder("Blocked:\n");
            String blocked;
            while (!(blocked = (String) receive.readObject()).equals("\n")) {
                blockeds.append(blocked).append("\n");
            }
            blockedArea.setText(blockeds.toString());
            showPanel("BlockedList");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error retrieving blocked list.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void unblockUser() {
        try {
            String blocked = JOptionPane.showInputDialog(frame, "Enter username to search:");
            if (blocked != null && !blocked.isEmpty()) {
                send.writeObject("1");
                send.flush();
                send.writeObject(blocked);
                send.flush();
            }
            send.writeObject(blocked);
            send.flush();
            String response = (String) receive.readObject();
            if (response.equals("User unblocked")) {
                JOptionPane.showMessageDialog(frame, "User unblocked", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (!response.equals("That user is not blocked")) {
                JOptionPane.showMessageDialog(frame, "That user is not blocked",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            showPanel("LoggedInMenu");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Server did not respond properly",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserClient::new);
    }
}