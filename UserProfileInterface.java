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
  public void setUsername(String username);
  public void setPassword(String password);
  public void setFriends(ArrayList<UserProfile> friends);
  //friend methods
  public void addFriend(String friend);
  public void deleteFriend(String friend);
}
