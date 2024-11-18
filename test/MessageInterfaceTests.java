package tests;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.Assert;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

//import MessageInterface;

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
            Method get = clazz.getMethod("getContents");
            assertNotNull("The Message interface should have an 'getContents' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'getContents' method.");
        }

        //setters
        try {
            Method set = clazz.getMethod("setContents", String.class);
            assertNotNull("The Message interface should have an 'setContents' method.", set);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'setContents' method.");
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

        Assert.assertTrue("Ensure that 'Message Interface' is public", Modifier.isPublic(modifiers));

        Assert.assertTrue("Ensure that `Message Interface` is NOT `abstract`!", Modifier.isAbstract(modifiers));

        Assert.assertEquals("Ensure that Message Interface has no interfaces itself", 0, superinterfaces.length);
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