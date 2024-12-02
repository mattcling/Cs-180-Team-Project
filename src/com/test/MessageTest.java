package com.test;

import main.Message;


import org.junit.Before;
import org.junit.Test;


import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * A file that runs public tests for the message class
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

public class MessageTest {
    private Message message;

    @Before
    public void setUp() {
        String chatID = "chat123";
        String senderID = "user456";
        String contents = "Hello, world!";
        message = new Message(chatID, senderID, contents);
    }

    @Test
    public void testMessageInitialization() {

        assertEquals("Chat ID should match", "chat123", message.getChatID());
        assertEquals("Sender ID should match", "user456", message.getSenderID());
        assertEquals("Contents should match", "Hello, world!", message.getContents());

    }

    @Test
    public void testSetAndGetContents() {
        String newContents = "Updated message content.";
        message.setContents(newContents);
        assertEquals("Contents should be updated", newContents, message.getContents());
    }

    @Test
    public void testSetAndGetChatID() {
        String newChatID = "newChat123";
        message.setChatID(newChatID);
        assertEquals("Chat ID should be updated", newChatID, message.getChatID());
    }

    // @Test
    // public void testSetAndGetDateTime() {
    //     LocalDateTime newDateTime = LocalDateTime.now().minusDays(1);
    //     message.setDateTime(newDateTime);
    //     assertEquals("DateTime should be updated", newDateTime, message.getDateTime());
    // }

    @Test
    public void testGenerateMessageID() {
        String newMessageID = message.generateUserID();
        assertNotNull("Generated message ID should not be null", newMessageID);
        assertNotEquals("Generated message ID should be unique", message.getMessageID(), newMessageID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetContents_Null() {
        message.setContents(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetChatID_Null() {
        message.setChatID(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateTime_Null() {
        message.setDateTime(null);
    }
}