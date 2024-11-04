public interface ProfileInterface {
    /**
     * A interface for the profile class
     *
     * <p>
     * Purdue University -- CS18000 -- Fall 2024
     * </p>
     *
     * @version November 2, 2024
     */
    public String getUserID();

    public void setUserID(String userID);

    public String getBio();

    public void setBio(String bio);

    public String getprofilePicture();

    public void updateProfile(String bio, String profilePicture);

    public void setprofilePicture(String ProfilePicture);

    public void displayProfile();


}
