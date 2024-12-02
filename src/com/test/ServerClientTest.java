import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServerClientTest {
    private static final int PORT = 4343;
    public Thread thread;

    @BeforeEach
    void setupServer() {
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                    Socket socket = serverSocket.accept();
                    new ServerClient(socket).run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();
        Thread.sleep(1000);//waiting for server
    }

    @AfterEach
    void tearDownServer() throws InterruptedException {
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt();  // Stop the server thread
            serverThread.join();  // Wait for the server thread to terminate
        }
    }

    public void testLoginSuccess() throws Exception {
        
        //Testing Login and User Creation
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("2");  // Create a new user
            output.flush();
            String username = "test";//sending username
            output.writeObject(username);
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Username is available.", response);

            String password = "123";//sending password
            output.writeObject(password);
            output.flush();
            response = (String) input.readObject();
            assertEquals("User created successfully! Please login to continue.", response);//check for success

            output.writeObject("1");  //login test
            output.flush();
            output.writeObject(username);//sending username
            output.flush();
            output.writeObject(password);//sending password
            output.flush();
            response = (String) input.readObject();
            assertEquals("Welcome, test!", response);//login success check

            output.writeObject("exit");  //quit
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testLoginFailure() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("1");//invalid login test
            output.flush();
            String invalidUsername = "qowufjp9u8233u498";//invalid username input
            output.writeObject(invalidUsername);
            output.flush();
            String password = "123";//password
            output.writeObject(password);
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Invalid username.", response);//testing if it returns invalid correctly

            output.writeObject("admin");//valid username
            output.flush();
            output.writeObject("banana");//invalid password
            output.flush();
            response = (String) input.readObject();
            assertEquals("Invalid password.", response);//checking if it returns invalid password - it should

            //output.writeObject("exit");//closing
            //output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void testUserSearch() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            //login
            output.writeObject("1");
            output.flush();
            output.writeObject("Sid");//valid username
            output.flush();
            output.writeObject("monkey");//valid password
            output.flush();
            output.writeObject("2");//user search test for friending
            output.flush();
            output.writeObject("admin");//valid user search
            output.flush();

            //usser search test for friending
            String response = (String) input.readObject();
            assertEquals("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit", response);//testing if options are properly give
            output.writeObject("1");
            output.flush();
            response = (String) input.readObject();
            assertEquals("Friend added", response);//making sure it correctly resturns
            
            //User search for invalid friending
            output.writeObject("2");
            output.flush();
            output.writeObject("admin");//valid user search
            output.flush();
            response = (String) input.readObject();
            assertEquals("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit", response);//testing if options are properly giv
            output.writeObject("1");
            output.flush();
            response = (String) input.readObject();
            assertEquals("That user is already your friend", response);//making sure it correctly resturns

            //user search for blocking
            output.writeObject("2");
            output.flush();
            output.writeObject("admin");//valid user search
            output.flush();
            response = (String) input.readObject();
            assertEquals("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit", response);//testing if options are properly giv
            output.writeObject("2");
            output.flush();
            response = (String) input.readObject();
            assertEquals("User blocked", response);//making sure it correctly resturns

            //user search for invalid blocking
            output.writeObject("2");
            output.flush();
            output.writeObject("admin");//valid user search
            output.flush();
            response = (String) input.readObject();
            assertEquals("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit", response);//testing if options are properly giv
            output.writeObject("2");
            output.flush();
            response = (String) input.readObject();
            assertEquals("That user is already blocked", response);//making sure it correctly resturns

            //user search for exit 
            output.writeObject("2");
            output.flush();
            output.writeObject("admin");//valid user search
            output.flush();
            response = (String) input.readObject();
            assertEquals("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit", response);//testing if options are properly giv
            output.writeObject("3");
            output.flush();
            response = (String) input.readObject();
            assertEquals("Exit", response);//making sure it correctly resturns

            //user search for invalid option 
            output.writeObject("2");
            output.flush();
            output.writeObject("admin");//valid user search
            output.flush();
            response = (String) input.readObject();
            assertEquals("Please Choose 1: \n 1. Friend user\n 2. Block user \n 3. Exit", response);//testing if options are properly giv
            output.writeObject("7");
            output.flush();
            response = (String) input.readObject();
            assertEquals("Invalid option selected.", response);//making sure it correctly resturns

            //user search for invalid user 
            output.writeObject("2");
            output.flush();
            output.writeObject("afopweifh");//valid user search
            output.flush();
            response = (String) input.readObject();
            assertEquals("User Does Not exist.", response);//testing if it sees the user does not exits

            output.writeObject("5");
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testFriendsList() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            //login
            output.writeObject("1");
            output.flush();
            output.writeObject("Sid");//valid username
            output.flush();
            output.writeObject("monkey");//valid password
            output.flush();
            output.writeObject("3");//friends list selection
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Your Friends: \n Diya \n admin \n", response);//testing if it correctly returns sid has no firends
            
            response = (String) input.readObject();
            assertEquals("Please choose 1: \n 1. UnFriend \n 2. Block \n 3. Exit", response);//making sure it correctly gives menu

            output.writeObject("1");
            output.flush();

            response = (String) input.readObject();
            assertEquals("Please enter the username to unfriend", response);//checking if it gives the correct prompt
            output.writeObject("admin");
            output.flush();

            response = (String) input.readObject();
            assertEquals("Friend Removed", response);//making sure it returns right 
            output.writeObject("3");//friends list selection again to confirm friend was removed
            output.flush();
            response = (String) input.readObject();
            assertEquals("Your Friends: \n Diya \n", response);

            output.writeObject("3");//friends list selection again to test 
            output.flush();
            response = (String) input.readObject();
            assertEquals("Your Friends: \n Diya \n admin\n", response);

            output.writeObject("2");//blocking test
            output.flush();
            response = (String) input.readObject();
            assertEquals("Please enter the username to block", response);
            output.writeObject("admin");
            output.flush();
            response = (String) input.readObject();
            assertEquals("Blocked User", response);
            
            output.writeObject("3");
            output.flush();
            output.writeObject("2");//invalid blocking test
            output.flush();
            response = (String) input.readObject();
            assertEquals("Please enter the username to block", response);
            output.writeObject("admin");
            output.flush();
            response = (String) input.readObject();
            assertEquals("That user is already blocked", response);

            output.writeObject("3");
            output.flush();
            output.writeObject("3");
            output.flush();
            response = (String) input.readObject();
            assertEquals("Exited", response);

            output.writeObject("3");//exit test
            output.flush();
            output.writeObject("3");
            output.flush();
            response = (String) input.readObject();
            assertEquals("Exited", response);

            output.writeObject("3");//invalid option test
            output.flush();
            output.writeObject("weoifjapweo]");
            output.flush();
            response = (String) input.readObject();
            assertEquals("Invalid option selected.", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testBlockedList() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            //login
            output.writeObject("1");
            output.flush();
            output.writeObject("Sid");//valid username
            output.flush();
            output.writeObject("monkey");//valid password
            output.flush();
            output.writeObject("4");//blocked list selection
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Your Blocked Users: \n admin \n", response);//testing if it correctly returns sid has only blocked admin(he did in the previous test)
            
            response = (String) input.readObject();
            assertEquals("Please choose 1: \n 1. UnBlock \n 2. Exit", response);//making sure it correctly gives menu

            output.writeObject("1");//unblock test
            output.flush();
            response = (String) input.readObject();
            assertEquals("Please enter the username to unblock", response);//checking if it gives the correct prompt
            output.writeObject("admin");
            output.flush();
            response = (String) input.readObject();
            assertEquals("User unblocked", response);//making sure it returns right 
            output.writeObject("4");//blocked list selection again to confirm admin was removed
            output.flush();
            response = (String) input.readObject();
            assertEquals("Your Blocked Users: \n", response);

            output.writeObject("4");//blocked list selection again to test exit
            output.flush();
            output.writeObject("2");//exit test
            output.flush();
            response = (String) input.readObject();
            assertEquals("Exited", response);
            
            output.writeObject("4");//blocked list again to test invalid input
            output.flush();
            output.writeObject("aoiwjef");//invalid test
            output.flush();
            response = (String) input.readObject();
            assertEquals("Invalid option selected.", response);//testing if it returns properly
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testQuit() throws Exception {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            //login
            output.writeObject("1");
            output.flush();
            output.writeObject("Sid");//valid username
            output.flush();
            output.writeObject("monkey");//valid password
            output.flush();
            output.writeObject("5");//blocked list selection
            output.flush();
            String response = (String) input.readObject();
            assertEquals("Goodbye!", response);//testing if it correctly returns goodbye
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}  