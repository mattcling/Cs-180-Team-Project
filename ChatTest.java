import org.junit.Before;
import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


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


public class ChatTest {
    private Chat chat;
    private Message message;

    @Before
    public void setUp() {
        chat = new Chat("chat1");
        message = new Message("1", "2", "3", "Hello, world!");
    }

    @Test
    public void testInitialChatID() {
        assertEquals("Initially, the ChatID should be chat1.", "chat1", chat.getChatID());
    }

    @Test
    public void testSetChatID() {
        chat.setChatID("chat2");
        assertEquals("The ChatID should be set to chat2.", "chat2", chat.getChatID());
    }

    @Test
    public void testAddMessage() {
        chat.addMessage(message);
        List<Message> messages = chat.receiveChat(message);
        assertEquals("The chat should contain 1 message after adding a message.", 1, messages.size());
        assertEquals("The message content should match the added message.", "Hello, world!",
                messages.get(0).getContents());
    }

    @Test
    public void testRemoveMessage() {
        chat.addMessage(message);
        chat.removeMessage(message);
        List<Message> messages = chat.receiveChat(message);
        assertTrue("The chat should be empty after removing the message.", messages.isEmpty());
    }

    @Test
    public void testGetParticipantsInitiallyEmpty() {
        List<String> participants = chat.getParticipants();
        assertTrue("Participants list should initially be empty.", participants.isEmpty());
    }

    @Test
    public void testReceiveChat() {
        chat.addMessage(message);
        List<Message> messages = chat.receiveChat(message);
        assertEquals("ReceiveChat should return a list containing the added message.", 1, messages.size());
        assertEquals("The returned message should match the added message.", message, messages.get(0));
    }

    @Test
    public void testImplementsChatInterface() {
        assertTrue("Chat should implement ChatInterface.", chat instanceof ChatInterface);
    }

    @Test
    public void testImplementsSerializable() {
        assertTrue("Chat should implement Serializable interface.", chat instanceof Serializable);
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ChatTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}