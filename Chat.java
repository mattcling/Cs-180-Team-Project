import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A framework to control chats for a socail media
 *
 * <p>
 * Purdue University -- CS18000 -- Fall 2024
 * </p>
 *
 * @author purdue cs
 * @version November 2, 2024
 */

public class Chat implements Serializable, ChatInterface {
    private static final long serialVersion = 1L;

    private String chatID;
    private List<Message> messages;
    private List<String> participants;


    // constructor :)
    public Chat(String chatID) {
        this.chatId = chatId;
        this.messages = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void RemoveMessage(Message message) {
        messages.remove(message);
    }

    public List<Message> ReceiveChat(Message message) {
        return new ArrayList<>(messages);
    }

    public String getChatID() {
        return chatId;
    }

    public void setChatID(String chatId) {
        this.chatID = chatId;
    }

    public List<String> getParticipants() {
        return new ArrayList<>(participants);
    }


}
