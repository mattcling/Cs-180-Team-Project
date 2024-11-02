import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the Messages class.
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Purdue CS
 * @version November 2, 2024
 */

public class MessageTest {
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
    //testing message with an object
    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message("1", "2", "3", "4");
    }

    @Test
    
    //initail value tests
    public void testInitialMessageID() {
        assertEquals("1", message.getMessageID(), "Initially, the MessageID should be 1.");
    }
    public void testInitialSenderID() {
        assertEquals("2", message.getSenderID(), "Initially, the SenderID should be 2.");
    }
    public void testInitialReceiverID() {
        assertEquals("3", message.getReceiverID(), "Initially, the ReceiverID should be 3.");
    }
    public void testInitialContents() {
        assertEquals("4", message.getContents(), "Initially, the Contents should be 4.");
    }
    
    //getters and setters tests
    public void testMessageID() {
        message.setMessageID("id");
        assertEquals("id", message.getMessageID(), "The MessageID should be set to id.");
    }
    public void testSenderID() {
        message.setSenderID("from");
        assertEquals("from", message.getSenderID(), "The SenderID should be set to from.");
    }
    public void testReceiverID() {
        message.setReceiverID("to"); 
        assertEquals("to", message.getReceiverID(), "The ReceiverID should be set to to.");
    }
    public void testMessageInfo() {
        message.setMessageInfo("content");
        assertEquals("content", message.getMessageInfo(), "The contents should be set to content.");
    }
    public void testTime() {
        LocalDateTime dt = LocalDateTime.now();
        message.setDateTime(dt);
        assertEquals(dt, message.getMessageInfo(), "The contents should be set to " + dt + ".");
    }
    
    //general tests
    public void testImplementsMessageInterface() {
        assertTrue(message instanceof MessageInterface, "Message should implement Message interface.");
    }
    public void testImplementsSerializable() {
        assertTrue(message instanceof Serializable, "Message should implement Serializable interface.");
    }
    public void testPublic() {
        int modifiers = Person.class.getModifiers();
        assertTrue(Modifier.isPublic(modifiers), "Message class should be public.");
    }    
    
}

