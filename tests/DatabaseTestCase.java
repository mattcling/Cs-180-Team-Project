package tests;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Chat;
import Database;
import Message;
import User;

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


public class DatabaseTestCase {

    private Database db;
    private User testinguser;
    private Chat testingchat;
    private Message testingmessage;


    @Before
    public void setUp() throws Exception {
        db = new Database();
        testinguser = new User("testinguser", "1234", db);
        testingchat = new Chat("1234");
        testingmessage = new Message("messageID", "sendingUser", "recivingUser", "contents");

        db.writeData(testinguser, "user");
        db.writeData(testingchat, "chats");
        db.writeData(testingmessage, "messages");
    }

    void clearfiles() {
        db.deleteData("users", "testinguser");
        db.deleteData("chats", "testingchat");
        db.deleteData("messages", "testingmessage");
    }


    @Test
    public void testwriteData() {
        assertTrue(db.writeData(new User("newuser", "1234", db), "user"));
        assertNotNull(db.getData("user", "newuser"));
    }

    @Test
    public void testgetData() {
        assertEquals(testinguser, db.getData("user", "testinguser"));
        assertEquals(testingchat, db.getData("chats", "1234"));
        assertEquals(testingmessage, db.getData("message", "messageID"));

    }

    @Test
    public void testdeleteData() {
        db.deleteData("user", "testinguser");
        assertNull(db.getData("user", "testinguser"));
    }

    @Test
    public void testloadata() {
        db.writeData();
        db.initializeDatabase();

        assertNotNull(db.getData("user", "testinguser"));
        assertNotNull(db.getData("chats", "1234"));
        assertNotNull(db.getData("messages", "messageID"));
    }

    public static void main(String[] args) {
        System.out.println("Running Database tests...");

        Result result = JUnitCore.runClasses(DatabaseTestCase.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("Test failed: " + failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("All tests passed successfully!");
        } else {
            System.out.println(result.getFailureCount() + " test(s) failed.");
        }
    }
}