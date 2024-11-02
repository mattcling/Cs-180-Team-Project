import java.io.Serializable;

public class Profile implements Serializable, ProfileInterface {
    private static final long serialVersionUID = 1L;

    public String userID;
    public String bio;
    public String ProfilePicture;


    public Profile(String userID, String bio, String profilePicture) {
        this.userID = userID;
        this.bio = bio;
        this.ProfilePicture = profilePicture;
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
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getProfilePicture() {
        return ProfilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public void updateProfile(String bio, String profilePicture) {
        setBio(bio);
        setProfilePicture(profilePicture);
        System.out.println("Profile updated successfully.");
    }

    public void displayProfile() {
        System.out.println("User ID: " + userID);
        System.out.println("Bio: " + bio);
        if(ProfilePicture != null) {
            System.out.println("Profile Picture: " + ProfilePicture);
        }
        else {
            System.out.println("Profile Picture is null or empty.");
        }
    }




}
