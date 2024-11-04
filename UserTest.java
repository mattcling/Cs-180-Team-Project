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

/**
 * A framework to run public test cases for the User class.
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Purdue CS
 * @author Matthew Clingerman
 * @author Charlotte Falus
 * @author Luke Guiboux
 * @author Kimaya Deshpande
 * @author Sid Songirkar
 * @version November 3, 2024
 */

public class UserTest {
    User user = new User("a", "b", null);

    @Test(timeout = 1000)
    public void initialValueTest() {
        assertEquals("Initially, the username should be a.", "a", user.getUserName());
        assertEquals("Initially, the password should be b.", "b", user.getPassword());
    }

    @Test(timeout = 1000)
    public void gettersAndSettersTest() {
        user.setUserName("c");
        assertEquals("Username should be c", "c", user.getUserName());
        user.setProfilePicture("a");
        assertEquals("Profile Picture should be a", "a", user.getProfilePicture());
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("a");
        temp.add("b");
        user.setFreindsList(temp);
        assertEquals("Freinds list should be a, b", temp, user.getFreindsList());
        user.setBlockedUsers(temp);
        assertEquals("Blocked Users should be a, b", temp, user.getBlockedUsers());
        user.setPassword("a");
        assertEquals("Password should be a", "a", user.getPassword());
        user.setUserID("a");
        assertEquals("UserID should be a", "a", user.getUserID());
    }

    @Test(timeout = 1000)
    public void testCreateAccount() {
        user.createAccount("a", "b");
        Assert.assertFalse("Should return false if account already exists.", user.createAccount("a", "b"));
    }

    @Test(timeout = 1000)
    public void testLogin() {
        user.createAccount("a", "b");
        Assert.assertTrue("Should return true when correctly inputting username and password.", user.login("a", "b"));
    }

    @Test(timeout = 1000)
    public void testAddFreind() {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("a");
        temp.add("b");
        user.setFreindsList(temp);
        temp.add("c");
        user.addFreind("c");
        assertEquals("Freinds list should contain a, b, c.", temp, user.getFreindsList());
    }

    @Test(timeout = 1000)
    public void testRemoveFreind() {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("a");
        temp.add("b");
        user.setFreindsList(temp);
        temp.remove("b");
        user.removeFreind("b");
        assertEquals("Freinds list should only contain a.", temp, user.getFreindsList());
    }

    @Test(timeout = 1000)
    public void testAddBlockedUser() {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("a");
        temp.add("b");
        user.setBlockedUsers(temp);
        temp.add("c");
        user.addBlockedUser("c");
        assertEquals("Blocked list should contain a, b, c.", temp, user.getBlockedUsers());
    }

    @Test(timeout = 1000)
    public void testUnblockUser() {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("a");
        temp.add("b");
        user.setBlockedUsers(temp);
        temp.remove("b");
        user.unBlockUser("b");
        assertEquals("Blocked list should only contain a.", temp, user.getBlockedUsers());
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(UserTest.class);
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
