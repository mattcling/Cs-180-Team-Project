import java.util.ArrayList;

public interface UserProfileInterface {
  //getters
  public String getUsername();
  public String getPassword();
  public ArrayList<UserProfile> getFriends();
  public String toString();
  public ArrayList<UserProfile> getBlocked();
  public String getBio();

  //setters
  public boolean setUsername(String username);
  public boolean setPassword(String password);
  public boolean setFriends(ArrayList<UserProfile> friends);
  public boolean setBlocked(ArrayList<UserProfile> blocked);
  public boolean setBio(String bio);

  //friend methods
  public boolean addFriend(UserProfile friend);
  public boolean deleteFriend(UserProfile friend);
  public boolean block(UserProfile friend);

  public boolean deleteUser();
  public boolean createUser(String username, String password);
}
