import java.util.*;

/**
 * A interface for the chat class
 *
 * <p>
 * Purdue University -- CS18000 -- Fall 2024
 * </p>
 *
 * @version November 2, 2024
 */
public interface ChatInterface {

    void setChatID(String chatID);

    List<Message> ReceiveChat(Message message);

    void RemoveMessage(Message message);

    String getChatID();

    List<String> getParticipants();

    void addMessage(Message message);

}