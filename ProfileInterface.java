public interface ProfileInterface {

    String getUserID();
    String getUserBio();
    String GetprofilePicture(String ProfilePicture); // this is going to be a file location
    String getStatus();
    void setBio(String Bio);
    void setProfilePicture(String ProfilePicture);
    void setStatus(String Status);
    void veiwProfile(String userID);




}
