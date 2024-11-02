import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.Assert;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.*;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the Messages class.
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Purdue CS
 * @version November 2, 2024
 */


public class MessageInterfaceTests {

    @Test(timeout = 1000)
    public void classDeclarationTestOne() {
        Class<?> clazz = MessageInterface.class;
        int modifiers;
        Class<?> superclass;
        Class<?>[] superinterfaces;
        
        //testing if methods are there that should be
        //getters:
        try {
            Method get = clazz.getMethod("getSenderID");
            assertNotNull("The Message interface should have an 'getSenderID' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'getSenderID' method.");
        }
        try {
            Method get = clazz.getMethod("getReceiverID");
            assertNotNull("The Message interface should have an 'getReceiverID' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'getReceiverID' method.");
        }
        try {
            Method get = clazz.getMethod("getMessageID");
            assertNotNull("The Message interface should have an 'getMessageID' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'getMessageID' method.");
        }
        try {
            Method get = clazz.getMethod("getDateTime");
            assertNotNull("The Message interface should have an 'getDateTime' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'getDateTime' method.");
        }
        try {
            Method get = clazz.getMethod("getMessageInfo");
            assertNotNull("The Message interface should have an 'getMessageInfo' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'getMessageInfo' method.");
        }
        
        //setters
        try {
            Method set = clazz.getMethod("setMessageInfo", String.class);
            assertNotNull("The Message interface should have an 'setMessageInfo' method.", set);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'setMessageInfo' method.");
        }
        try {
            Method set = clazz.getMethod("setMessageID", String.class);
            assertNotNull("The Message interface should have an 'setMessageID' method.", set);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'setMessageID' method.");
        }
        try {
            Method set = clazz.getMethod("setSenderID", String.class);
            assertNotNull("The Message interface should have an 'setSenderID' method.", set);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'setSenderID' method.");
        }
        try {
            Method set = clazz.getMethod("setReceiverID", String.class);
            assertNotNull("The Message interface should have an 'setReceiverID' method.", set);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'setReceiverID' method.");
        }
        try {
            Method set = clazz.getMethod("setDateTime", LocalDateTime.class);
            assertNotNull("The Message interface should have an 'setDateTime' method.", set);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'setDateTime' method.");
        }
        
        modifiers = clazz.getModifiers();

        superclass = clazz.getSuperclass();

        superinterfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that 'Messages' is public", Modifier.isPublic(modifiers));

        Assert.assertFalse("Ensure that `Messages` is NOT `abstract`!", Modifier.isAbstract(modifiers));

        Assert.assertEquals("Ensure that `Messages` extends `Object'!", Object.class, superclass);

        Assert.assertEquals("Ensure that `Messages` implements 'UserProfile interfaces!", superinterfaces.length);
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MessageInterfaceTests.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }

    }

}

