import java.util.*;
import java.io.*;

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

public class User implements UserInterface, Serializable {
    private static final long SERIAL_VERSION_UID = 1L;

    private String userID; // will be a set of 12 digits for a mostly uncapped user count
    private String password; // make sure only numbers and letters
    private String userName; // this will be chosen by user
    private String profilePicture; // should be the file i want to acces
    private List<String> friendsList; // stores all frreinds id numbers
    // using lists for easier access and this way we know that things wont get complicated with lengths of a basic array
    private List<String> blockedUsers; // stores blocked friends id numbers

    private static Database database = new Database();

    public User(String username,
                String password) {
        this.userID = generateUserID(); // this method uses a io class to make a random sting of numbers and letters
        this.password = password;
        
        this.userName = username;
        this.blockedUsers = new ArrayList<>();
        this.friendsList = new ArrayList<>();
        
    }

    public boolean createAccount(String username, String newPassword) {

        database.initializeDatabase();
        if (database.getData("user", username) != null) {
            System.out.println("User already exists");
            return false;
        }

        User newUser = new User(username, newPassword);
        return database.writeData(newUser, "user");
    }

    public boolean login(String username, String userpassword) {
        User user = (User) database.getData("user", username);
        if (user != null && user.password.equals(userpassword)) {
            System.out.println("User logged in");
            return true;
        }
        System.out.println("User not logged in!");
        return false;
    }

    public void logout() {
        System.out.println("User logged out");
    }

    public boolean addFriend(String friendsName) {
        if (!friendsList.contains(friendsName)) {
            friendsList.add(friendsName);
            System.out.println("Friend added");
            return true;
        }
        System.out.println("this person is already your friend");
        return false;
    }

    public boolean removeFriend(String friendsName) {
        if (friendsList.contains(friendsName)) {
            friendsList.remove(friendsName);
            System.out.println("Friend removed");
            return true;
        }
        System.out.println("this person is not your friend");
        return false;
    }

    public boolean addBlockedUser(String blockedUserName) {
        if (!blockedUsers.contains(blockedUserName)) {
            blockedUsers.add(blockedUserName);
            System.out.println("Blocked user!");
            return true;
        }
        System.out.println("this person is already blocked");
        return false;
    }

    public boolean unBlockUser(String blockedUserName) {
        if (blockedUsers.contains(blockedUserName)) {
            blockedUsers.remove(blockedUserName);
            System.out.println("Blocked user unblocked");
            return true;
        }
        System.out.println("this person is not blocked");
        return false;
    }

    public String generateUserID() {
        String temp = "UID" + UUID.randomUUID();
        if (database.getData("user" , temp) != null) {
            return generateUserID();
        } else {
            return temp;
        }
    }

    // getters and setters
    public String getUserID() {
        return userID;
    }
    
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public List<String> getFriendsList() {
        return friendsList;
    }

    public List<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }

    public void setBlockedUsers(List<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
