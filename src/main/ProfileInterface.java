package main;
/**
 * A framework for the Profile class
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


public interface ProfileInterface {

    public String getUserID();

    public void setUserID(String userID);

    public String getBio();

    public void editBio(String bio);

    public String getprofilePicture();

    public void updateProfile(String bio, String profilePicture);

    public void setprofilePicture(String profilePicture);

    public void displayProfile();


}
