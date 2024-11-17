import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UserClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4343);
             ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream receive = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Server: " + receive.readObject());
            System.out.println("Server: " + receive.readObject());

            String choice = sc.nextLine();
            send.writeObject(choice);
            send.flush();

			//login
            if ("1".equals(choice)) {
                System.out.println("Server: " + receive.readObject());
                String username = sc.nextLine();
                send.writeObject(username);
                send.flush();

                System.out.println("Server: " + receive.readObject());
                String password = sc.nextLine();
                send.writeObject(password);
                send.flush();

                System.out.println("Server: " + receive.readObject());
				
            } else if ("2".equals(choice)) { //new user 
                while (true) {
                    System.out.println("Server: " + receive.readObject());
                    String username = sc.nextLine();
                    send.writeObject(username);
                    send.flush();

                    String serverResponse = (String) receive.readObject();
                    System.out.println("Server: " + serverResponse);

                    if ("Username is available.".equals(serverResponse)) {
                        break;
                    }
                }

                System.out.println("Server: " + receive.readObject());
                String password = sc.nextLine();
                send.writeObject(password);
                send.flush();

                System.out.println("Server: " + receive.readObject());
            } else {
                System.out.println("Server: " + receive.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
