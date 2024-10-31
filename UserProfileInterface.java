import java.util.ArrayList;

public interface UserProfileInterface {
  //getters
  public String getUsername();
  public String getPassword();
  public ArrayList<UserProfile> getFriends();  
  public ArrayList<UserProfile> getBlocked();
  public String getBio();

  //setters
  public boolean setUsername(String username);
  public boolean setPassword(String password);
  public boolean setFriends(ArrayList<UserProfile> friends);
  public boolean setBlocked(ArrayList<UserProfile> blocked);
  public boolean setBio(String bio);

  //friend methods
  public boolean addFriend(Object friend);
  public boolean deleteFriend(Object friend);
  public boolean block(Object friend);

  //misc.
  public String toString();
  public boolean deleteUser();
}
