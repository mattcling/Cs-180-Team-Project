import java.util.ArrayList;

/**
 * Group Project
 *
 * This interface contains user profile details for user class
 *
 * @author Charlotte Falus, LO4
 *
 * @version October 29, 2024
 *
 */
public interface UserProfileInterface {
  //getters
  public String getUsername();
  public String getPassword();
  public ArrayList<UserProfile> getFriends();
  public String toString();
  //settera
  public boolean setUsername(String username);
  public boolean setPassword(String password);
  public boolean setFriends(ArrayList<UserProfile> friends);
  //friend methods
  public boolean addFriend(String friend);
  public boolean deleteFriend(String friend);
}
