import java.util.List;

/**
 * A interface for the user class
 *
 * <p>
 * Purdue University -- CS18000 -- Fall 2024
 * </p>
 *
 * @version November 2, 2024
 */
public interface UserInterface {

    boolean CreateAccount(String username, String password);

    boolean login(String username, String password);

    void logout();

    public boolean addBlockedUser(String blockedUserID);

    public boolean unBlockUser(String blockedUserID);

    public boolean removeFreind(String freindsID);

    public boolean addFreind(String freindsID);

    public String getUserID();

    public String getProfilePicture();

    public List<String> getFreindsList();

    public List<String> getBlockedUsers();

    public void setUserName(String userName);

    public void setProfilePicture(String profilePicture);

    public void setFreindsList(List<String> freindsList);

    public void setBlockedUsers(List<String> blockedUsers);
}
