public class Login extends UserProfile{
    private static String username;
    private static String password;
    private static boolean isValid;
    private static boolean existingUser;

    public Login(String username, String password, boolean isValid) {
        this.username = username;
        this.password = password;
        this.isValid = false;
        this.isValid = existingUser = false;
    }
}
