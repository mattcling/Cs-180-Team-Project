import org.junit.jupiter.api.*;

import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

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

public class UserClientTest {

    private static final int PORT = 4343;
    private ServerSocket serverSocket;

    @BeforeEach
    void setUp() throws IOException {
        // Create a mock server
        serverSocket = new ServerSocket(PORT);
        Executors.newSingleThreadExecutor().submit(() -> {
            try (Socket socket = serverSocket.accept();
                 ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream receive = new ObjectInputStream(socket.getInputStream())) {

                // Mock server responses
                send.writeObject("Welcome to the server!");
                send.writeObject("Please enter your choice (1 for login, 2 for sign up):");

                String optionchoice = (String) receive.readObject();
                if ("1".equals(optionchoice)) { // Login test
                    send.writeObject("Enter username:");
                    String username = (String) receive.readObject();
                    send.writeObject("Enter password:");
                    String password = (String) receive.readObject();

                    if ("user".equals(username) && "pass".equals(password)) {
                        send.writeObject("Welcome, user!");
                        send.writeObject("You are logged in.");
                    } else {
                        send.writeObject("Invalid credentials.");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @AfterEach
    void tearDown() throws IOException {
        serverSocket.close();
    }

    @Test
    void testLoginSuccess() {
        // Simulate user input
        InputStream in = new ByteArrayInputStream("1\nuser\npass\n".getBytes());
        System.setIn(in);

        // Capture console output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Run the client
        assertDoesNotThrow(() -> UserClient.main(new String[]{}));

        // Verify output
        String output = out.toString();
        assertTrue(output.contains("Welcome, user!"));
        assertTrue(output.contains("You are logged in."));
    }

    @Test
    void testLoginFailure() {
        // Simulate user input
        InputStream in = new ByteArrayInputStream("1\nwrongUser\nwrongPass\n".getBytes());
        System.setIn(in);

        // Capture console output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Run the client
        assertDoesNotThrow(() -> UserClient.main(new String[]{}));

        // Verify output
        String output = out.toString();
        assertTrue(output.contains("Invalid credentials."));
    }
}
