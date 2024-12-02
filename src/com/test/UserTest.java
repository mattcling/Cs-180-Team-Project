package com.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.After;

import main.User; // Assuming the User class is located in the 'main' package
import main.Database;
/**
 * A test case set for the User Class
 *
 * <p>
 * Purdue University -- CS18000 -- Fall 2024</p>
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

    private User user;

    @Before
    public void setUp() {
        // Initializing a user object before each test
        user = new User("testUser", "password123");
    }

    @Test
    public void testUserInitialization() {
        // Verifying that the User is correctly initialized
        assertNotNull("User ID should not be null", user.getUserID());
        assertEquals("Username should match", "testUser", user.getUserName());
        assertEquals("Password should match", "password123", user.getPassword());
        assertNotNull("Friends list should not be null", user.getFriendsList());
        assertNotNull("Blocked users list should not be null", user.getBlockedUsers());
        assertNotNull("Chat IDs list should not be null", user.getChatIds());
    }

    @Test
    public void testCreateAccount_Failure() {
        // Attempting to create an account with an already existing user
        user.createAccount("existingUser", "password123");
        boolean result = user.createAccount("existingUser", "newPassword123");
        assertFalse("Account creation should fail if user already exists", result);
    }

    @Test
    public void testLogin_Success() {
        // Testing login with correct credentials
        user.createAccount("loginUser", "loginPassword123");
        boolean result = user.login("loginUser", "loginPassword123");
        assertTrue("Login should be successful with correct credentials", result);
    }

    @Test
    public void testLogin_Failure() {
        // Testing login with incorrect credentials
        boolean result = user.login("nonexistentUser", "wrongPassword");
        assertFalse("Login should fail with incorrect credentials", result);
    }

    @Test
    public void testAddFriend() {
        // Testing adding a friend
        boolean result = user.addFriend("friendUser");
        assertTrue("Friend should be added successfully", result);
        assertTrue("Friend should be in the friends list", user.getFriendsList().contains("friendUser"));
    }

    @Test
    public void testRemoveFriend() {
        // Testing removing a friend
        user.addFriend("friendUser");
        boolean result = user.removeFriend("friendUser");
        assertTrue("Friend should be removed successfully", result);
        assertFalse("Friend should no longer be in the friends list", user.getFriendsList().contains("friendUser"));
    }

    @Test
    public void testAddBlockedUser() {
        // Testing blocking a user
        boolean result = user.addBlockedUser("blockedUser");
        assertTrue("Blocked user should be added successfully", result);
        assertTrue("Blocked user should be in the blocked users list", user.getBlockedUsers().contains("blockedUser"));
    }

    @Test
    public void testUnBlockUser() {
        // Testing unblocking a user
        user.addBlockedUser("blockedUser");
        boolean result = user.unBlockUser("blockedUser");
        assertTrue("Blocked user should be unblocked successfully", result);
        assertFalse("Blocked user should no longer be in the blocked users list",
                user.getBlockedUsers().contains("blockedUser"));
    }

    @Test
    public void testAddChat() {
        // Testing adding a chat ID to the user
        boolean result = user.addChat("chat123");
        assertTrue("Chat should be added successfully", result);
        assertTrue("Chat ID should be in the chat list", user.getChatIds().contains("chat123"));
    }

    @Test
    public void testAddChat_AlreadyExist() {
        // Testing adding an already existing chat ID
        user.addChat("chat123");
        boolean result = user.addChat("chat123");
        assertFalse("Chat ID should not be added if it already exists", result);
    }

    @After
    public void tearDown() {
        // Cleaning up after each test
        Database d = new Database();
        user = null;
        d.deleteData("user", "testUser");

    }
}