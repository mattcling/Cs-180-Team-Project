import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.*;

import static org.junit.Assert.*;


public class DatabaseTestCase {

    private Database db;
    private User testinguser;
    private Chat testingchat;
    private Message testingmessage;

    //to clear files after each test
    void clearfiles(){
        db.deleteData("users", "testinguser");
        db.deleteData("chats", "testingchat");
        db.deleteData("messages", "testingmessage");
    }

    @Before
    public void setUp() throws Exception {
        clearfiles();
        db = new Database();
        testinguser = new User("testinguser","1234",db);
        testingchat = new Chat("1234");
        testingmessage = new Message("messageid","sendingUser","recivingUser","contents");

        db.saveData(testinguser,"user");
        db.saveData(testingchat,"chat");
        db.saveData(testingmessage,"message");
    }


    @Test
    public void testsaveData(){
        assertTrue(db.saveData(new User("newuser","1234",db),"users"));
        assertNotNull(db.getData("users","newuser"));
    }
    @Test
    public void testgetData(){
        assertEquals(testinguser,db.getData("users","testinguser"));
        assertEquals(testingchat,db.getData("chat","testingchat"));
        assertEquals(testingmessage,db.getData("message","testingmessage"));

    }

    @Test
    public void testdeleteData(){
        db.deleteData("users", "testinguser");
        assertNull(db.getData("users","testinguser"));
    }

    @Test
    public void testloadata(){
        db.saveData();
        db.initializeDatabase();

        assertNotNull(db.getData("users","testinguser"));
        assertNotNull(db.getData("chat","testingchat"));
        assertNotNull(db.getData("message","testingmessage"));
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


