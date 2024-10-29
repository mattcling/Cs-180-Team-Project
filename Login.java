public class Login implements UserProfileInterface {
    public static String username;
    public static String password;
    public static boolean isValid;
    public static boolean existingUser;

    public Login(String username, String password, boolean isValid) {
        this.username = username;
        this.password = password;
        this.isValid = false;
        this.existingUser = false;
    }
}
