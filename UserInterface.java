public interface UserInterface {

    boolean CreateAccount(String username, String password);
    boolean Login(String username, String password);
    void Logout();
    boolean AddFreinds(String UserID);
    boolean RemoveFreinds(String UserID);
    boolean BlockUsers(String UserID);
    boolean Unblock(String UserID);


}
