package tests;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import UserInterface;

import java.lang.reflect.*;
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

public class UserInterfaceTest {
    @Test(timeout = 1000)
    public void classDeclarationTestOne() {
        Class<?> clazz = UserInterface.class;
        Class<?> superclass;
        Class<?>[] superinterfaces;

        //testing if methods are there that should be
        //setters:
        try {
            Method get = clazz.getMethod("setUserName", String.class);
            assertNotNull("The User interface should have a 'setUserName' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'setUserName' method.");
        }
        try {
            Method get = clazz.getMethod("setProfilePicture", String.class);
            assertNotNull("The User interface should have a 'setProfilePicture' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'setProfilePicture' method.");
        }
        try {
            Method get = clazz.getMethod("setFreindsList", List.class);
            assertNotNull("The User interface should have a 'setFreindsList' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'setFreindsList' method.");
        }
        try {
            Method get = clazz.getMethod("setBlockedUsers", List.class);
            assertNotNull("The User interface should have a 'setBlockedUsers' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'setBlockedUsers' method.");
        }
        //getters
        try {
            Method get = clazz.getMethod("getUserID");
            assertNotNull("The User interface should have a 'getUserID' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'getUserID' method.");
        }
        try {
            Method get = clazz.getMethod("getProfilePicture");
            assertNotNull("The User interface should have a 'getProfilePicture' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'getProfilePicture' method.");
        }
        try {
            Method get = clazz.getMethod("getFreindsList");
            assertNotNull("The User interface should have a 'getFreindsList' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'getFreindsList' method.");
        }
        try {
            Method get = clazz.getMethod("getBlockedUsers");
            assertNotNull("The User interface should have a 'getBlockedUsers' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'getBlockedUsers' method.");
        }
        //others
        try {
            Method get = clazz.getMethod("addBlockedUser", String.class);
            assertNotNull("The User interface should have a 'addBlockedUser' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'addBlockedUser' method.");
        }
        try {
            Method get = clazz.getMethod("unBlockUser", String.class);
            assertNotNull("The User interface should have a 'unBlockUser' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'unBlockUser' method.");
        }
        try {
            Method get = clazz.getMethod("removeFreind", String.class);
            assertNotNull("The User interface should have a 'removeFreind' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'removeFreind' method.");
        }
        try {
            Method get = clazz.getMethod("addFreind", String.class);
            assertNotNull("The User interface should have a 'addFreind' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The User interface is missing the 'addFreind' method.");
        }
        int modifiers = clazz.getModifiers();
        superclass = clazz.getSuperclass();
        superinterfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that 'Message Interface' is public", Modifier.isPublic(modifiers));

        Assert.assertTrue("Ensure that `Message Interface` is NOT `abstract`!", Modifier.isAbstract(modifiers));

        Assert.assertEquals("Ensure that Message Interface has no interfaces itself", 0, superinterfaces.length);
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(UserInterfaceTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }

    }
}