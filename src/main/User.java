package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * this class store the data and accesses the data on our users of our social media platform
 * this class also works with the chat class to make chats and store them
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
public class User implements UserInterface, Serializable {

    private static final long SERIAL_VERSION_UID = 1L;

    private String userID; // will be a set of 12 digits for a mostly uncapped user count
    private String password; // make sure only numbers and letters
    private String userName; // this will be chosen by user
    private String profilePicture; // should be the file i want to acces
    private List<String> friendsList; // stores all frreinds id numbers
    // using lists for easier access and this way we know that things wont get complicated with lengths of a basic array
    private List<String> blockedUsers; // stores blocked friends id numbers
    private List<String> chatIds;
    private Profile profile;
    private static Database d = new Database();

    public User(String username,
                String password) {//creates new user with a username passowrd, empty friends and blocked lists, and a random id

        d.loadOldData();

        this.userID = generateUserID(); // this method uses a io class to make a random sting of numbers and letters

        this.password = password;

        this.userName = username;
        this.blockedUsers = new ArrayList<>();
        this.friendsList = new ArrayList<>();
        this.chatIds = new ArrayList<>();
        profile = new Profile(userName, "This is my bio", "profilePic.jpg");
        d.writeData(this, "user");

    }


    public boolean login(String username, String userpassword) {//login function with username and password -
        // checking if logged in and returning
        d.loadOldData();
        User user = (User) d.getData("user", username);
        if (user != null && user.password.equals(userpassword)) {
            System.out.println("User logged in");
            return true;
        }
        System.out.println("User not logged in!");
        return false;
    }

    public boolean addFriend(String friendsName) {// adds friend to array or prints already friend
        if (!friendsList.contains(friendsName)) {
            friendsList.add(friendsName);
            System.out.println("Friend added");
            return true;
        }
        System.out.println("this person is already your friend");
        return false;
    }

    public boolean addChat(String Id) {
        if (chatIds == null) {
            chatIds = new ArrayList<>();
        }
        if (chatIds.isEmpty() || !chatIds.contains(Id)) {
            chatIds.add(Id);
            d.writeData(this, "user");
            return true;
        } else {
            System.out.println("This id is already in the list");
            return false;
        }
    }

    public boolean removeFriend(String friendsName) {//removes friend and returns confirmation
        if (friendsList.contains(friendsName)) {
            friendsList.remove(friendsName);
            System.out.println("Friend removed");
            return true;
        }
        System.out.println("this person is not your friend");
        return false;
    }

    public boolean addBlockedUser(String blockedUserName) {// similar to friend functions but with blocked users
        if (!blockedUsers.contains(blockedUserName)) {
            blockedUsers.add(blockedUserName);
            System.out.println("Blocked user!");
            return true;
        }
        System.out.println("this person is already blocked");
        return false;
    }

    public boolean unBlockUser(String blockedUserName) {// similar to friend functions but with blocked users
        if (blockedUsers.contains(blockedUserName)) {
            blockedUsers.remove(blockedUserName);
            System.out.println("Blocked user unblocked");
            return true;
        }
        System.out.println("this person is not blocked");
        return false;
    }

    public String generateUserID() {//method to randomly generate the id 
        String temp = "UID" + UUID.randomUUID();
        if (d.getData("user", temp) != null) {
            return generateUserID();
        } else {
            return temp;
        }
    }

    // getters and setters
    public String getUserID() {
        return userID;
    }

    public List<String> getChatIds() {
        d.loadOldData();
        return this.chatIds;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public Profile getProfile() {
        return profile;
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
        d.writeData(this, "user");
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
        d.writeData(this, "user");
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
