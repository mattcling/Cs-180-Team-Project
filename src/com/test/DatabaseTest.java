package com.test;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import main.Chat;
import main.Database;
import main.Message;
import main.User;

import static org.junit.Assert.*;

/**
 * Test class for the Database functionality.
 */
public class DatabaseTest {
    private Database database;
    private User user1, user2;
    private Chat chat;
    private Message message;

    @Before
    public void setUp() {
        database = new Database();
        database.initializeDatabase();

        user1 = new User("Alice", "password123");
        user2 = new User("Bob", "securePassword");
        chat = new Chat("Alice", "Bob");
        message = new Message(chat.getChatID(), "Alice", "Hello, Bob!");

        database.writeData(user1, "user");
        database.writeData(user2, "user");
        database.writeData(chat, "chat");
        database.writeData(message, "message");
    }

    @Test
    public void testWriteAndGetDataUser() {
        User retrievedUser = (User) database.getData("user", "Alice");
        assertNotNull("Retrieved user should not be null", retrievedUser);
        assertEquals("Retrieved username should match", "Alice", retrievedUser.getUserName());
    }

    @Test
    public void testWriteAndGetDataChat() {
        Chat retrievedChat = (Chat) database.getData("chat", chat.getChatID());
        assertNotNull("Retrieved chat should not be null", retrievedChat);
        assertTrue("Chat participants should include Alice and Bob",
                retrievedChat.getParticipants().containsAll(List.of("Alice", "Bob")));
    }

    @Test
    public void testWriteAndGetDataMessage() {
        Message retrievedMessage = (Message) database.getData("message", message.getMessageID());
        assertNotNull("Retrieved message should not be null", retrievedMessage);
        assertEquals("Message contents should match", "Hello, Bob!", retrievedMessage.getContents());
    }

    @Test
    public void testUpdateDataUser() {
        User retrievedUser = (User) database.getData("user", "Alice");
        assertNotNull("User should exist before update", retrievedUser);

        retrievedUser.setPassword("newPassword123");
        database.writeData(retrievedUser, "user");

        User updatedUser = (User) database.getData("user", "Alice");
        assertNotNull("User should exist after update", updatedUser);
        assertEquals("Password should be updated", "newPassword123", updatedUser.getPassword());
    }

    @Test
    public void testRemoveDataUser() {
        database.deleteData("user", "Alice");

        User removedUser = (User) database.getData("user", "Alice");
        assertNull("User should not exist after removal", removedUser);
    }

    @Test
    public void testRemoveDataChat() {
        database.deleteData("chat", chat.getChatID());

        Chat removedChat = (Chat) database.getData("chat", chat.getChatID());
        assertNull("Chat should not exist after removal", removedChat);
    }

    @Test
    public void testRemoveDataMessage() {
        database.deleteData("message", message.getMessageID());

        Message removedMessage = (Message) database.getData("message", message.getMessageID());
        assertNull("Message should not exist after removal", removedMessage);
    }
}