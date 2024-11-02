public class Database implements DatabaseInterface {

    public String username;
    public String password;
    public String usersName;
    public String[] blockUsers;
    public String[] Friends;
    public String UserBio;
    public String[] messages;

    public Database(String username, String password,
                    String usersName, String[] blockUsers,
                    String[] Friends, String UserBio,
                    String[] messages) {
        this.username = username;
        this.password = password;
        this.usersName = usersName;
        this.blockUsers = blockUsers;
        this.Friends = Friends;
        this.UserBio = UserBio;
        this.messages = messages;
    }

    @Override
    public String[] getUserMessage() {
       return messages;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsersName() {
        return usersName;
    }

    @Override
    public String[] getBlockedUsers() {
        return blockUsers;
    }

    @Override
    public String getUserBio() {
        return UserBio;
    }

    @Override
    public String[] getUserFreinds() {
        return Friends;
    }
}
