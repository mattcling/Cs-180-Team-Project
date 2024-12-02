package tests;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Profile;
import ProfileInterface;

import java.io.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for our profile class.
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


public class ProfileTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ProfileTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }

    }

    //testing profile with an object
    private Profile profile;

    @Before
    public void setUp() {
        profile = new Profile("0", "Purdue CS 2028", "Trees");
    }

    @Test

    //initail value tests
    public void testInitialUserID() {
        assertEquals("Initially, the userID should be 0", "0", profile.getUserID());
    }

    public void testInitialBio() {
        assertEquals("Initially, the bio should be null", "null", profile.getBio());
    }

    public void testInitialReceiverID() {
        assertEquals("Initially, the ProfilePicture should be null", "null", profile.getprofilePicture());
    }

    //getters and setters tests
    public void testProfileID() {
        profile.setUserID("id");
        assertEquals("The userID should be set to id.", "id", profile.getUserID());
    }

    public void testBio() {
        profile.setBio("Purdue CS 2028");
        assertEquals("The bio should be set to Purdue CS 2028", "Purdue CS 2028", profile.getBio());
    }

    public void testProfilePicture() {
        profile.setprofilePicture("Trees");
        assertEquals("The profile picture should be set to to Trees.", "Trees", profile.getprofilePicture());
    }

    //general tests
    public void testImplementsProfileInterface() {
        assertTrue("Profile should implement Profile interface.", profile instanceof ProfileInterface);
    }

    public void testImplementsSerializable() {
        assertTrue("Profile should implement Serializable interface.", profile instanceof Serializable);
    }

    public void testPublic() {
        int modifiers = Profile.class.getModifiers();
        assertTrue("Profile class should be public.", Modifier.isPublic(modifiers));
    }

}