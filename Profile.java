import java.io.Serializable;

public class Profile implements Serializable, ProfileInterface {
    private static final long serialVersionUID = 1L;

    public String userID;
    public String bio;
    public String profilePicture;


    public Profile(String userID, String bio, String ProfilePicture) {
        this.userID = userID;
        this.bio = bio;
        this.profilePicture = ProfilePicture;
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
    public String getprofilePicture() {
        return profilePicture;
    }
    public void setprofilePicture(String ProfilePicture) {
        profilePicture = ProfilePicture;
    }

    public void updateProfile(String bio, String ProfilePicture) {
        setBio(bio);
        setprofilePicture(ProfilePicture);
        System.out.println("Profile updated successfully.");
    }

    public void displayProfile() {
        System.out.println("User ID: " + userID);
        System.out.println("Bio: " + bio);
        if(profilePicture != null) {
            System.out.println("Profile Picture: " + profilePicture);
        }
        else {
            System.out.println("Profile Picture is null or empty.");
        }
    }




}
