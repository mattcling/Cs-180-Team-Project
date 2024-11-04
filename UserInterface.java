import java.util.List;
/**
 * A framework to run public test cases for the User class.
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

public interface UserInterface {

    boolean createAccount(String username, String password);

    boolean login(String username, String password);

    void logout();

    String generateUserID();

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
