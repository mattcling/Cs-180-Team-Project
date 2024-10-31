import java.util.*;
public class UserProfile {
  private String username;
  private String password;
  private ArrayList<UserProfile> friends;
  private ArrayList<UserProfile> blocked;
  private String bio;

  public UserProfile(String username, String password) {
    this.username = username;
    this.password = password;
    friends = new ArrayList<UserProfile>();
    blocked = new ArrayList<UserProfile>();
  }

  //getters
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public ArrayList<UserProfile> getFriends() {
    return friends;
  }
  public ArrayList<UserProfile> getBlocked() {
    return blocked;
  }
  public String getBio() {
    return bio;
  }

  //setters
  public boolean setUsername(String username) {
    this.username = username;
    return true;
  }
  public boolean setPassword(String password) {
    this.password = password;
    return true;
  }
  public boolean setFriends(ArrayList<UserProfile> friends) {
    this.friends = friends;
    return true;
  }
  public boolean setBlocked(ArrayList<UserProfile> blocked) {
    this.blocked = blocked;
    return true;
  }
  public boolean setBio(String bio) {
    this.bio = bio;
    return true;
  }

  //friend methods:
  public boolean addFriend(Object friend) {
    if (friend instanceof UserProfile) {
      friends.add((UserProfile)friend);
      return true;
    }
    return false;
  }
  public boolean deleteFriend(Object friend) {
    if (friend instanceof UserProfile) {
      friends.remove((UserProfile)friend);
      return true;
    }
    return false;
  }
  public boolean block(UserProfile friend) {
    if (friend instanceof UserProfile) {
      friends.remove((UserProfile)friend);
      blocked.add((UserProfile)friend);
      return true;
    }
    return false;
  }

  //put serch method, delete user method, and login methods here later
}
