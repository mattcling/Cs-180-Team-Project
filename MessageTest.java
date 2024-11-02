import org.junit.Before;
import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the Message class.
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

    @Before
    public void setUp() {
        message = new Message("1", "2", "3", "4");
    }

    @Test
    
    //initail value tests
    public void testInitialMessageID() {
        assertEquals("Initially, the MessageID should be 1.", "1", message.getMessageID());
    }
    public void testInitialSenderID() {
        assertEquals("Initially, the SenderID should be 2.", "2", message.getSenderID());
    }
    public void testInitialReceiverID() {
        assertEquals( "Initially, the ReceiverID should be 3.", "3", message.getReceiverID());
    }
    public void testInitialContents() {
        assertEquals("Initially, the Contents should be 4.", "4", message.getContents());
    }
    
    //getters and setters tests
    public void testMessageID() {
        message.setMessageID("id");
        assertEquals("The MessageID should be set to id.", "id", message.getMessageID());
    }
    public void testSenderID() {
        message.setSenderID("from");
        assertEquals("The SenderID should be set to from.", "from", message.getSenderID());
    }
    public void testReceiverID() {
        message.setReceiverID("to"); 
        assertEquals("The ReceiverID should be set to to.", "to", message.getReceiverID());
    }
    public void testMessageInfo() {
        message.setMessageInfo("content");
        assertEquals("The contents should be set to content.", "content", message.getMessageInfo());
    }
    public void testTime() {
        LocalDateTime dt = LocalDateTime.now();
        message.setDateTime(dt);
        assertEquals("The DateTime is set wrong", dt, message.getDateTime());
    }
    
    //general tests
    public void testImplementsMessageInterface() {
        assertTrue("Message should implement Message interface.", message instanceof MessageInterface);
    }
    public void testImplementsSerializable() {
        assertTrue("Message should implement Serializable interface.", message instanceof Serializable);
    }
    public void testPublic() {
        int modifiers = Message.class.getModifiers();
        assertTrue("Message class should be public.", Modifier.isPublic(modifiers));
    }    
    
}

