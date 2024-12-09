package main;

import java.io.Serializable;

/**
 * A class that represents the profile of a user.
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

public class Profile implements Serializable, ProfileInterface {//will be used in gui
    private static final long serialVersionUID = 1L;

    public String userID;
    public String bio;
    public String profilePicture;//name of the photo


    public Profile(String userID, String bio, String userProfilePicture) {//creats new profile

        this.userID = userID;
        this.bio = bio;
        //we need to generate a unique string for the profile picture file name
        this.profilePicture = "src/photos/" + userProfilePicture;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBio() {
        return bio;
    }

    public void editBio(String bio) {
        this.bio = bio;
    }

    public String getprofilePicture() {
        return profilePicture;
    }

    public void setprofilePicture(String userProfilePicture) {
        this.profilePicture = userProfilePicture;
    }

    public void updateProfile(String userbio, String userProfilePicture) {
        editBio(userbio);
        setprofilePicture(userProfilePicture);
        System.out.println("Profile updated successfully.");
    }

    public void displayProfile() {
        System.out.println("User ID: " + userID);
        System.out.println("Bio: " + bio);
        if (profilePicture != null) {
            System.out.println("Profile Picture: " + profilePicture);
        } else {
            System.out.println("Profile Picture is null or empty.");
        }
    }


}
