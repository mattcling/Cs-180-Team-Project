package main;
import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ChatterBox {
    private JFrame frame;
    private JPanel mainMenu, createUserPanel, loginPanel, loggedInMenu, chatsPanel, userSearchPanel, friendsListPanel, blockedListPanel;
    private CardLayout cardLayout;

    public ChatterBox() {
        frame = new JFrame("ChatterBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Set CardLayout for frame's content pane
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        initMainMenu();
        initCreateUserPanel();
        initLoginPanel();
        initLoggedInMenu();
        initChatsPanel();
        initUserSearchPanel();
        initFriendsListPanel();
        initBlockedListPanel();

        // Add panels to CardLayout
        frame.add(mainMenu, "MainMenu");
        frame.add(createUserPanel, "CreateUser");
        frame.add(loginPanel, "Login");
        frame.add(loggedInMenu, "LoggedInMenu");
        frame.add(chatsPanel, "Chats");
        frame.add(userSearchPanel, "UserSearch");
        frame.add(friendsListPanel, "FriendsList");
        frame.add(blockedListPanel, "BlockedList");

        // Show Main Menu
        showPanel("MainMenu");

        frame.setVisible(true);
    }

    private void initMainMenu() {
        mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(3, 1));

        JButton createUserButton = new JButton("Create New User");
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        createUserButton.addActionListener(e -> showPanel("CreateUser"));
        loginButton.addActionListener(e -> showPanel("Login"));
        exitButton.addActionListener(e -> System.exit(0));

        mainMenu.add(createUserButton);
        mainMenu.add(loginButton);
        mainMenu.add(exitButton);
    }

    private void initCreateUserPanel() {
        createUserPanel = new JPanel();
        createUserPanel.setLayout(new GridLayout(4, 1));

        JTextField usernameField = new JTextField("Enter Username");
        JTextField passwordField = new JTextField("Enter Password");
        JButton createButton = new JButton("Create User");
        JButton backButton = new JButton("Back");

        createButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            JOptionPane.showMessageDialog(frame, "User Created: " + username);
        });

        backButton.addActionListener(e -> showPanel("MainMenu"));

        createUserPanel.add(usernameField);
        createUserPanel.add(passwordField);
        createUserPanel.add(createButton);
        createUserPanel.add(backButton);
    }

    private void initLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1));

        JTextField usernameField = new JTextField("Enter Username");
        JTextField passwordField = new JTextField("Enter Password");
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            JOptionPane.showMessageDialog(frame, "Logged in as: " + username);
            showPanel("LoggedInMenu");
        });

        backButton.addActionListener(e -> showPanel("MainMenu"));

        loginPanel.add(usernameField);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(backButton);
    }

    private void initLoggedInMenu() {
        loggedInMenu = new JPanel();
        loggedInMenu.setLayout(new GridLayout(5, 1));

        JButton chatsButton = new JButton("Chats");
        JButton userSearchButton = new JButton("User Search");
        JButton friendsListButton = new JButton("Friends List");
        JButton blockedListButton = new JButton("Blocked Users");
        JButton logoutButton = new JButton("Logout");

        chatsButton.addActionListener(e -> showPanel("Chats"));
        userSearchButton.addActionListener(e -> showPanel("UserSearch"));
        friendsListButton.addActionListener(e -> showPanel("FriendsList"));
        blockedListButton.addActionListener(e -> showPanel("BlockedList"));
        logoutButton.addActionListener(e -> showPanel("MainMenu"));

        loggedInMenu.add(chatsButton);
        loggedInMenu.add(userSearchButton);
        loggedInMenu.add(friendsListButton);
        loggedInMenu.add(blockedListButton);
        loggedInMenu.add(logoutButton);
    }

    private void initChatsPanel() {
        chatsPanel = new JPanel();
        chatsPanel.setLayout(new GridLayout(3, 1));

        JButton removeChatButton = new JButton("Remove Chat");
        JButton backButton = new JButton("Back");

        removeChatButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Chat Removed"));
        backButton.addActionListener(e -> showPanel("LoggedInMenu"));

        chatsPanel.add(new JLabel("Chats Section", SwingConstants.CENTER));
        chatsPanel.add(removeChatButton);
        chatsPanel.add(backButton);
    }

    private void initUserSearchPanel() {
        userSearchPanel = new JPanel();
        userSearchPanel.setLayout(new GridLayout(3, 1));

        JButton addFriendButton = new JButton("Add Friend");
        JButton blockButton = new JButton("Block User");
        JButton backButton = new JButton("Back");

        addFriendButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Friend Added"));
        blockButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "User Blocked"));
        backButton.addActionListener(e -> showPanel("LoggedInMenu"));

        userSearchPanel.add(addFriendButton);
        userSearchPanel.add(blockButton);
        userSearchPanel.add(backButton);
    }

    private void initFriendsListPanel() {
        friendsListPanel = new JPanel();
        friendsListPanel.setLayout(new GridLayout(3, 1));

        JButton unfriendButton = new JButton("Unfriend");
        JButton blockButton = new JButton("Block");
        JButton backButton = new JButton("Back");

        unfriendButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Friend Removed"));
        blockButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "User Blocked"));
        backButton.addActionListener(e -> showPanel("LoggedInMenu"));

        friendsListPanel.add(unfriendButton);
        friendsListPanel.add(blockButton);
        friendsListPanel.add(backButton);
    }

    private void initBlockedListPanel() {
        blockedListPanel = new JPanel();
        blockedListPanel.setLayout(new GridLayout(3, 1));

        JButton unblockButton = new JButton("Unblock User");
        JButton backButton = new JButton("Back");

        unblockButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "User Unblocked"));
        backButton.addActionListener(e -> showPanel("LoggedInMenu"));

        blockedListPanel.add(unblockButton);
        blockedListPanel.add(backButton);
    }

    private void showPanel(String panelName) {
        cardLayout.show(frame.getContentPane(), panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatterBox::new);
    }
}
