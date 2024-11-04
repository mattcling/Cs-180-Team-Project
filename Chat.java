import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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


public class Chat implements Serializable, ChatInterface {
    private static final long SERIAL_VERSION_UID = 1L;

    private String chatID;
    private List<Message> messages;
    private List<String> participants;


    // constructor :)
    public Chat(String chatID) {
        this.chatID = chatID;
        this.messages = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public List<Message> receiveChat(Message message) {
        return new ArrayList<>(messages);
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatId) {
        this.chatID = chatId;
    }

    public List<String> getParticipants() {
        return new ArrayList<>(participants);
    }

}
