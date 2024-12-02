package main;
import java.util.List;

/**
 * A framework for the Chat class.
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

public interface ChatInterface {

    void setChatID(String chatID);

    List<Message> receiveChat(Message message);

    void removeMessage(Message message);

    String generateChatID();

    String getChatID();

    List<String> getParticipants();

    public void sendMessage(String mess, String sender);

}