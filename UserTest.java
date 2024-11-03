import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserTest { //SUPER UNFINISHED
    User user = new User("a", "b", null);

    @Test
    public void InitialValueTest() {
        assertEquals("Initially, the username should be a.", "a", user.getUserName());
        assertEquals("Initially, the password should be b.", "b", user.getPassword());
    }
    @Test
    public void GettersAndSettersTest() {
        user.setUserName("c");
        assertEquals("Username should be c", "c", user.getUserName());
        user.setProfilePicture("a");
        assertEquals("Profile Picture should be a", "a", user.getProfilePicture());
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("a"); temp.add("b");
        user.setFreindsList(temp);
        assertEquals("Freinds list should be a, b", temp, user.getFreindsList());
        user.setBlockedUsers(temp);
        assertEquals("Blocked Users should be a, b", temp, user.getBlockedUsers());
    }
    @Test
    public void TestCreateAccount() {
        user.CreateAccount("a", "b");
        Assert.assertFalse("Should return false if account already exists.", user.CreateAccount("a", "b"));
    }
    @Test
    public void TestLogin() {
        user.CreateAccount("a", "b");
        Assert.assertTrue("Should return true when correctly inputting username and password.", user.login("a", "b"));
    }
    @Test
    public void TestAddFreind() {

    }
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCases.class);
        System.out.printf("Test Count: %d.\n", result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.print("Excellent - all local tests ran successfully.\n");
        } else {
            System.out.printf("Tests failed: %d.\n", result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}
