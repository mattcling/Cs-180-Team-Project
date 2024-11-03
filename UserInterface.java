import java.util.List;

public interface UserInterface {

    boolean CreateAccount(String username, String password);
    boolean login(String username, String password);
    void logout();
    public boolean addBlockedUser(String blockedUserID);
    public boolean unBlockUser(String blockedUserID);
    public boolean removeFreind(String freindsID);
    public boolean addFreind(String freindsID);
    public String getUserID();
    public String getPassword();
    public String getProfilePicture();
    public List<String> getFreindsList();
    public List<String> getBlockedUsers();
    public boolean setUserName(String userName);
    public boolean setProfilePicture(String profilePicture);
    public boolean setFreindsList(List<String> freindsList);
    public boolean setBlockedUsers(List<String> blockedUsers);

}
