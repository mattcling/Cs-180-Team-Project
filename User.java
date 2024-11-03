import java.util.*;
import java.io.*;

public class User implements UserInterface {

    private String UserID; // will be a set of 12 digits for a mostly uncapped user count
    private String Password; // make sure only numbers and letters
    private String userName; // this will be chosen by user
    private String ProfilePicture; // should be the file i want to acces
    private List<String> FreindsList; // stores all frreinds id numbers
    // using lists for easier access and this way we know that things wont get complicated with lengths of a basic array
    private List<String> blockedUsers; // stores blocked freinds id numbers

    private DatabaseInterface database;

    public User(String username,
                String password,
                DatabaseInterface database ) {
        this.UserID = GenerateUserID(); // this method uses a io class to make a random sting of numbers and letters
        this.Password = password;
        this.database = database;
        this.userName = username;
        this.blockedUsers = new ArrayList<>();
        this.FreindsList = new ArrayList<>();
    }

    public boolean CreateAccount(String username,String password) {

        if(database.getData(username, "users") != null){
            System.out.println("User already exists");
            return false;
        }

        User newUser = new User(username, password, database);
        return database.saveData(newUser, "users");
    }

    public boolean login(String username,String password) {
        User user = (User) database.getData(username, "users");
        if(user != null && user.Password.equals(password)){
            System.out.println("User logged in");
            return true;
        }
        System.out.println("User not logged in!");
        return false;
    }

    public void logout() {
        System.out.println("User logged out");
    }

    public boolean addFreind(String freindsID) {
        if(!FreindsList.contains(freindsID)){
            FreindsList.add(freindsID);
            System.out.println("Freind added");
            return true;
        }
        System.out.println("this person is already your freind");
        return false;
    }
    public boolean removeFreind(String freindsID) {
        if(FreindsList.contains(freindsID)){
            FreindsList.remove(freindsID);
            System.out.println("Freind removed");
            return true;
        }
        System.out.println("this person is not your freind");
        return false;
    }

    public boolean addBlockedUser(String blockedUserID) {
        if(!blockedUsers.contains(blockedUserID)){
            blockedUsers.add(blockedUserID);
            System.out.println("Blocked user!");
            return true;
        }
        System.out.println("this person is already blocked");
        return false;
    }

    public boolean unBlockUser(String blockedUserID) {
        if(blockedUsers.contains(blockedUserID)){
            blockedUsers.remove(blockedUserID);
            System.out.println("Blocked user unblocked");
            return true;
        }
        System.out.println("this person is not blocked");
        return false;
    }

    private String GenerateUserID() {
        return "UID" + UUID.randomUUID();
    }


    // getters and setters
    public String getUserID() {
        return UserID;
    }
    public String getPassword() {
        return Password;
    }
    public String getUserName() {
        return userName;
    }
    public String getProfilePicture() {
        return ProfilePicture;
    }
    public List<String> getFreindsList() {
        return FreindsList;
    }
    public List<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }
    public void setFreindsList(List<String> freindsList) {
        FreindsList = freindsList;
    }
    public void setBlockedUsers(List<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }






}
