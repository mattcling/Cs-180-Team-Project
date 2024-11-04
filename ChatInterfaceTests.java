import java.util.List;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.*;

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


public class ChatInterfaceTests {

    @Test(timeout = 1000)
    public void classDeclarationTestOne() {
        Class<?> clazz = ChatInterface.class;
        int modifiers;
        Class<?>[] superinterfaces;

        // Getters:
        try {
            Method getChatID = clazz.getMethod("getChatID");
            assertNotNull("The ChatInterface should have a 'getChatID' method.", getChatID);
        } catch (NoSuchMethodException e) {
            fail("The ChatInterface is missing the 'getChatID' method.");
        }

        try {
            Method getParticipants = clazz.getMethod("getParticipants");
            assertNotNull("The ChatInterface should have a 'getParticipants' method.", getParticipants);
        } catch (NoSuchMethodException e) {
            fail("The ChatInterface is missing the 'getParticipants' method.");
        }

        // Setters:
        try {
            Method setChatID = clazz.getMethod("setChatID", String.class);
            assertNotNull("The ChatInterface should have a 'setChatID' method.", setChatID);
        } catch (NoSuchMethodException e) {
            fail("The ChatInterface is missing the 'setChatID' method.");
        }

        // Chat management methods:
        try {
            Method receiveChat = clazz.getMethod("ReceiveChat", Message.class);
            assertNotNull("The ChatInterface should have a 'ReceiveChat' method.", receiveChat);
        } catch (NoSuchMethodException e) {
            fail("The ChatInterface is missing the 'ReceiveChat' method.");
        }

        try {
            Method removeMessage = clazz.getMethod("RemoveMessage", Message.class);
            assertNotNull("The ChatInterface should have a 'RemoveMessage' method.", removeMessage);
        } catch (NoSuchMethodException e) {
            fail("The ChatInterface is missing the 'RemoveMessage' method.");
        }

        try {
            Method addMessage = clazz.getMethod("addMessage", Message.class);
            assertNotNull("The ChatInterface should have an 'addMessage' method.", addMessage);
        } catch (NoSuchMethodException e) {
            fail("The ChatInterface is missing the 'addMessage' method.");
        }

        modifiers = clazz.getModifiers();
        Assert.assertTrue("Ensure that 'ChatInterface' is public", Modifier.isPublic(modifiers));

        Assert.assertTrue("Ensure that `ChatInterface` is NOT `abstract`!", Modifier.isAbstract(modifiers));

        superinterfaces = clazz.getInterfaces();
        Assert.assertEquals("Ensure that ChatInterface has no interfaces itself", 0, superinterfaces.length);
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ChatInterfaceTests.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}