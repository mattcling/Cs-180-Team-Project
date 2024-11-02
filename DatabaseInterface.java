public interface DatabaseInterface {

    public String username = "";
    public String password = "";
    public String usersName = "";
    public String[] blockUsers = new String[0];
    public String[] Freinds = new String[0];
    public String UserBio = "";
    public String[] messages = new String[0];



    public String[] getUserMessage();
    public String getUserName();
    public String getPassword();
    public String getUsersName();
    public String[] getBlockedUsers();
    public String getUserBio();
    public String[] getUserFreinds();


}
