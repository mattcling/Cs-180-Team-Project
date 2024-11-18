import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerClientTest {
    private Database mockD;
    private Socket mockS;
    private ObjectOutputStream mockO;
    private ObjectInputStream mockI;
    private ServerClient serverClient;

    public void setUp() throws IOException {
        mockD = mock(Database.class);
        mockS = mock(Socket.class);
        mockO = mock(ObjectOutputStream.class);
        mockI = mock(ObjectInputStream.class);
        when(mockS.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(mockS.getInputStream()).thenReturn(mockI);
        serverClient = new ServerClient(mockS);
        ServerClient.d = mockD;  
    }

    public void testLogin() throws Exception {
        String username = "testUsername";
        String password = "testPassword";
        User mockUser = new User(username, password);

        when(mockD.containsObject("user", username)).thenReturn(true);
        when(mockD.getData("user", username)).thenReturn(mockUser);
        when(mockIn.readObject()).thenReturn("1");
        
    }
}