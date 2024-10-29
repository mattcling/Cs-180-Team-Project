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
  public ArrayList<UserProfile> getBlocked();
  //settera
  public void setUsername(String username);
  public void setPassword(String password);
  public void setFriends(ArrayList<UserProfile> friends);
  public void setBlocked(ArrayList<UserProfile> blocked);
  //friend methods
  public void addFriend(UserProfile friend);
  public void deleteFriend(UserProfile friend);
  public void block(UserProfile friend);
}
