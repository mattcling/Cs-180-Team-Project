package com.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import main.Profile; // Assuming Profile class is located in the 'main' package
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




/**
 * Test class for the Profile class.
 */
public class ProfileTest {

    private Profile profile;

    @Before
    public void setUp() {
        // Initializing a Profile object before each test
        profile = new Profile("testUserID", "This is my bio", "profilePic.jpg");
    }

    @Test
    public void testProfileInitialization() {
        // Verifying that the Profile is correctly initialized
        assertNotNull("UserID should not be null", profile.getUserID());
        assertEquals("UserID should match", "testUserID", profile.getUserID());
        assertEquals("Bio should match", "This is my bio", profile.getBio());
        assertEquals("Profile picture should match", "src/photos/profilePic.jpg", profile.getprofilePicture());
    }

    @Test
    public void testUpdateProfile() {
        // Verifying updating the profile bio and picture
        profile.updateProfile("Updated bio", "updatedPic.jpg");
        assertEquals("Bio should be updated", "Updated bio", profile.getBio());
        assertEquals("Profile picture should be updated", "updatedPic.jpg", profile.getprofilePicture());
    }

    @Test
    public void testDisplayProfile() {
        // Verifying the displayProfile() method (Since it prints to the console, we can
        // only verify if it does not throw an exception)
        profile.displayProfile(); // This will print the profile to the console
        // No assertion here, just ensuring no exceptions are thrown
    }

    @Test
    public void testSetUserID() {
        // Verifying that the user ID can be set correctly
        profile.setUserID("newUserID");
        assertEquals("UserID should be updated", "newUserID", profile.getUserID());
    }

    @Test
    public void testSetBio() {
        // Verifying that the bio can be updated correctly
        profile.editBio("New bio content");
        assertEquals("Bio should be updated", "New bio content", profile.getBio());
    }

    @Test
    public void testSetProfilePicture() {
        // Verifying that the profile picture can be updated correctly
        profile.setprofilePicture("newProfilePic.jpg");
        assertEquals("Profile picture should be updated", "newProfilePic.jpg", profile.getprofilePicture());
    }
}