import java.sql.*;
import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;

public interface DatabaseInterface {

    public UserProfile user = null;
    public Message message = null;
    public String freinds = null;
    public String bio = null;
    public String blockedUsers = null;
    public String UsersName = null;
    public String username = null;
    public String password = null;


    public boolean storeNewUser();
    public boolean storeNewMessage();
    public String getUserMessage();
    public String getUserName();
    public String getPassword();
    public String getUsersName();
    public String getBlockedUsers();
    public boolean removeMessage();
    public String getUserBio();
    public String getUserFreinds();


}
