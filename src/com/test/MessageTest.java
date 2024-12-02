package com.test;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import main.Message;
import org.junit.Assert;
import main.MessageInterface;

import java.io.*;
import java.lang.reflect.*;
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
    //testing message with an object
    private Message message;

    @Before
    public void setUp() {
        message = new Message("1", "2", "3");
    }

    //initail value tests

    @Test
    public void testInitialSenderID() {
        assertEquals("Initially, the SenderID should be 2.", "2", message.getSenderID());
    }

    @Test
    public void testInitialContents() {
        assertEquals("Initially, the Contents should be 3.", "3", message.getContents());
    }

    @Test
    //getters and setters tests
    public void testMessageID() {
        message.setMessageID("id");
        assertEquals("The MessageID should be set to id.", "id", message.getMessageID());
    }


    @Test
    public void testContents() {
        message.setContents("content");
        assertEquals("The contents should be set to content.", "content", message.getContents());
    }

    // @Test
    // public void testTime() {
    //     LocalDateTime dt = LocalDateTime.now();
    //     message.setDateTime(dt);
    //     assertEquals("The DateTime is set wrong", dt, message.getDateTime());
    // }

    @Test
    //general tests
    public void testImplementsMessageInterface() {
        assertTrue("Message should implement Message interface.", message instanceof MessageInterface);
    }

    @Test
    public void testImplementsSerializable() {
        assertTrue("Message should implement Serializable interface.", message instanceof Serializable);
    }

    @Test
    public void testPublic() {
        int modifiers = Message.class.getModifiers();
        assertTrue("Message class should be public.", Modifier.isPublic(modifiers));
    }

    @Test
    //error tests
    public void testSetMessageIDError() {
        try {
            message.setMessageID(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("messageID cannot be null or empty.", e.getMessage());
        }
    }

    @Test
    public void testSetContentsError() {
        try {
            message.setContents(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("contents cannot be null or empty.", e.getMessage());
        }
    }

    @Test
    public void testSetDateTimeError() {
        try {
            message.setDateTime(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("time cannot be null", e.getMessage());
        }
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MessageTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }

    }
}