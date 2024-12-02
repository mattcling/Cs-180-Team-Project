package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import main.Database;
import main.DatabaseInterface;
import main.Chat;
import main.Message;
import main.User;


import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class DatabaseTest {
    private Database database;
    private User testUser;
    private Chat testChat;
    private Message testMessage;

    @Before
    public void setUp() {
        // Initialize the database and test objects
        database = new Database();
        testUser = new User("testUser", "password123");
        testChat = new Chat("chat1", "Test Chat");
        testMessage = new Message("message1", "testUser", "This is a test message");
    }

    // @After
    // public void tearDown() {
    //     // Clean up serialized files to avoid persistence issues between tests
    //     deleteFile("userTable.ser");
    //     deleteFile("chatTable.ser");
    //     deleteFile("messageTable.ser");
    // }

    // private void deleteFile(String filename) {
    //     File file = new File(filename);
    //     if (file.exists()) {
    //         file.delete();
    //     }
    // }

    @Test
    public void testWriteAndGetData_User() {
        // Test writing and retrieving a user
        assertTrue(database.writeData(testUser, "user"));
        assertTrue(database.containsObject("user", "testUser"));

        User retrievedUser = (User) database.getData("user", "testUser");
        assertNotNull(retrievedUser);
        assertEquals(testUser.getUserName(), retrievedUser.getUserName());
        assertEquals(testUser.getPassword(), retrievedUser.getPassword());
    }

    @Test
    public void testWriteAndGetData_Chat() {
        // Test writing and retrieving a chat
        assertTrue(database.writeData(testChat, "chat"));
        assertTrue(database.containsObject("chat", "chat1"));

        Chat retrievedChat = (Chat) database.getData("chat", "chat1");
        assertNotNull(retrievedChat);
        assertEquals(testChat.getChatID(), retrievedChat.getChatID());
    }

    @Test
    public void testWriteAndGetData_Message() {
        // Test writing and retrieving a message
        assertTrue(database.writeData(testMessage, "message"));
        assertTrue(database.containsObject("message", "message1"));

        Message retrievedMessage = (Message) database.getData("message", "message1");
        assertNotNull(retrievedMessage);
        assertEquals(testMessage.getMessageID(), retrievedMessage.getMessageID());
        //assertEquals(testMessage.getContent(), retrievedMessage.getContent());
        assertEquals(testMessage.getSenderID()(), retrievedMessage.getSender());
        assertEquals(testMessage.getChatID(), retrievedMessage.getChatID());
    }

    @Test
    public void testUpdateData_User() {
        // Test updating a user
        database.writeData(testUser, "user");
        User updatedUser = new User("testUser", "newPassword456", "Updated User");
        assertTrue(database.changeData("user", updatedUser, "testUser"));

        User retrievedUser = (User) database.getData("user", "testUser");
        assertNotNull(retrievedUser);
        assertEquals(updatedUser.getPassword(), retrievedUser.getPassword());
        assertEquals(updatedUser.getDisplayName(), retrievedUser.getDisplayName());
    }

    @Test
    public void testDeleteData_User() {
        // Test deleting a user
        database.writeData(testUser, "user");
        assertTrue(database.containsObject("user", "testUser"));

        assertTrue(database.deleteData("user", "testUser"));
        assertFalse(database.containsObject("user", "testUser"));

        User retrievedUser = (User) database.getData("user", "testUser");
        assertNull(retrievedUser);
    }

    @Test
    public void testPersistence_User() {
        // Test persistence by saving, loading, and checking data
        database.writeData(testUser, "user");
        database.saveTableUser(database.loadTableUser("userTable.ser"), "userTable.ser");

        Database newDatabase = new Database();
        newDatabase.initializeDatabase();
        assertTrue(newDatabase.containsObject("user", "testUser"));

        User retrievedUser = (User) newDatabase.getData("user", "testUser");
        assertNotNull(retrievedUser);
        assertEquals(testUser.getUserName(), retrievedUser.getUserName());
    }
}