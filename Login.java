public class Login extends UserProfile{
    public static String username;
    public static String password;
    public static boolean isValid;
    public static boolean existingUser;

    public Login(String username, String password, boolean isValid) {
        this.username = username;
        this.password = password;
        this.isValid = false;
        this.isValid = existingUser = false;
    }
}
