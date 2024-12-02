package com.test;

import main.Chat;
import main.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the Chat class.
 */
public class ChatTest {
    private Chat chat;

    @Before
    public void setUp() {
        // Simulate a chat between two users, "user1" and "user2".
        chat = new Chat("user1", "user2");
    }

    @Test
    public void tesChatID() {
        // Check that the chat ID is correctly generated based on participants.
        String expectedChatID = "user1user2";
        assertEquals("Chat ID should be the concatenation of participant usernames.", expectedChatID, chat.getChatID());
    }

    @Test
    public void testAddParticipantToChat() {
        // Add a new participant and verify they are added.
        chat.addParticipantToChat("user3");
        List<String> participants = chat.getParticipants();
        assertTrue("Participant list should contain user3.", participants.contains("user3"));
    }

    @Test
    public void testSendMessage() {
        // Send a message and verify it's added to the chat.
        String content = "Hello, world!";
        String sender = "user1";

        chat.sendMessage(content, sender);

        List<Message> messages = chat.getMessages();
        assertEquals("Chat should contain one message after sending a message.", 1, messages.size());

        Message message = messages.get(0);
        assertEquals("Message content should match the sent message.", content, message.getContents());
        assertEquals("Message sender should match the user who sent it.", sender, message.getSenderID());
        assertEquals("Message chat ID should match the chat's ID.", chat.getChatID(), message.getChatID());
    }

    @Test
    public void testRemoveMessage() {
        // Send and remove a message, then verify it's removed.
        chat.sendMessage("Hello, world!", "user1");
        Message message = chat.getMessages().get(0);
        chat.removeMessage(message);
        assertTrue("Chat should be empty after removing the message.", chat.getMessages().isEmpty());
    }

    @Test
    public void testReceiveChat() {
        // Send a message and retrieve chat messages.
        String content = "Test message";
        String sender = "user1";

        chat.sendMessage(content, sender);

        List<Message> receivedMessages = chat.receiveChat(null);
        assertEquals("Received chat should contain one message.", 1, receivedMessages.size());

        Message receivedMessage = receivedMessages.get(0);
        assertEquals("Received message content should match the sent message.", content, receivedMessage.getContents());
        assertEquals("Received message sender should match the user who sent it.", sender,
                receivedMessage.getSenderID());
    }

    @Test
    public void testGetParticipants() {
        // Verify the initial participants are correctly set.
        List<String> participants = chat.getParticipants();
        assertEquals("Participants list should contain two users initially.", 2, participants.size());
        assertTrue("Participants should contain user1.", participants.contains("user1"));
        assertTrue("Participants should contain user2.", participants.contains("user2"));
    }

    @Test
    public void testSetChatID() {
        // Change the chat ID and verify the update.
        chat.setChatID("newChatID");
        assertEquals("Chat ID should be updated.", "newChatID", chat.getChatID());
    }

    @Test
    public void testMessageObjectFields() {
        // Create a new message and validate its fields.
        String chatID = "chat123";
        String senderID = "user1";
        String contents = "Hello!";
        Message message = new Message(chatID, senderID, contents);

        assertNotNull("Message ID should be generated.", message.getMessageID());
        assertEquals("Chat ID should match the provided chat ID.", chatID, message.getChatID());
        assertEquals("Sender ID should match the provided sender ID.", senderID, message.getSenderID());
        assertEquals("Contents should match the provided content.", contents, message.getContents());
        assertNotNull("DateTime should be initialized.", message.getDateTime());
    }
}