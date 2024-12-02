import java.util.List;
/**
 * The blueprint for our user class.
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

    String generateUserID();

    public boolean addBlockedUser(String blockedUserID);

    public boolean unBlockUser(String blockedUserID);

    public boolean removeFriend(String friendsID);

    public boolean addFriend(String friendsID);

    public String getUserID();

    public String getProfilePicture();

    public List<String> getFriendsList();

    public List<String> getBlockedUsers();

    public void setUserName(String userName);

    public void setProfilePicture(String profilePicture);

    public void setFriendsList(List<String> friendsList);

    public void setBlockedUsers(List<String> blockedUsers);
}
