import org.junit.jupiter.api.*;

import java.io.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;
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
public class ServerClientTest {
    private static final int PORT = 4343;
    private Thread serverThread;

    @BeforeEach
    void setupServer() throws InterruptedException {
        serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                Socket socket = serverSocket.accept();
                new ServerClient(socket).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
        Thread.sleep(1000); // Waiting for server
    }

    @AfterEach
    void tearDownServer() throws InterruptedException {
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt(); // Stop the server thread
            serverThread.join(); // Wait for the server thread to terminate
        }
    }

    @Test
    void testLoginSuccess() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("2"); // Create a new user
            output.flush();
            String username = "test"; // Sending username
            output.writeObject(username);
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Username is available.", response);

            String password = "123"; // Sending password
            output.writeObject(password);
            output.flush();
            response = (String) input.readObject();
            assertEquals("User created successfully! Please login to continue.", response);

            output.writeObject("1"); // Login test
            output.flush();
            output.writeObject(username); // Sending username
            output.flush();
            output.writeObject(password); // Sending password
            output.flush();
            response = (String) input.readObject();
            assertEquals("Welcome, test!", response);

            output.writeObject("exit"); // Quit
            output.flush();
        }
    }

    @Test
    void testLoginFailure() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("1"); // Invalid login test
            output.flush();
            String invalidUsername = "invalid_user";
            output.writeObject(invalidUsername);
            output.flush();
            String password = "123";
            output.writeObject(password);
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Invalid username.", response);

            output.writeObject("admin"); // Valid username
            output.flush();
            output.writeObject("wrong_password"); // Invalid password
            output.flush();
            response = (String) input.readObject();
            assertEquals("Invalid password.", response);
        }
    }

    @Test
    void testUserSearch() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("1");
            output.flush();
            output.writeObject("Sid");
            output.flush();
            output.writeObject("monkey");
            output.flush();
            output.writeObject("2"); // User search test for friending
            output.flush();
            output.writeObject("admin");
            output.flush();

            String response = (String) input.readObject();
            assertEquals("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit", response);
            output.writeObject("1");
            output.flush();
            response = (String) input.readObject();
            assertEquals("Friend added", response);
        }
    }

    @Test
    void testFriendsList() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("1");
            output.flush();
            output.writeObject("Sid");
            output.flush();
            output.writeObject("monkey");
            output.flush();
            output.writeObject("3");
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Your Friends: \n admin\n", response);
        }
    }

    @Test
    void testBlockedList() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("1");
            output.flush();
            output.writeObject("Sid");
            output.flush();
            output.writeObject("monkey");
            output.flush();
            output.writeObject("4");
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Your Blocked Users: \n admin \n", response);
        }
    }

    @Test
    void testQuit() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("1");
            output.flush();
            output.writeObject("Sid");
            output.flush();
            output.writeObject("monkey");
            output.flush();
            output.writeObject("5");
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Goodbye!", response);
        }
    }
}
     }
        
        
    }
}  
