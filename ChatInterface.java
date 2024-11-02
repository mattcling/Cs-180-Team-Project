import java.util.*;

public interface ChatInterface {

    void setChatID(String chatID);
    List<Message> ReceiveChat(Message message);
    void RemoveMessage(Message message);
    String getChatID();
    List<String> getParticipants();
    void addMessage(Message message);

}