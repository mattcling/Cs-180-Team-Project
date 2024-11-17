import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private static Database d = new Database();


    // constructor :)
    public Chat(String user1, String user2) {
        d.loadOldData();
        this.chatID = generateChatID();
        this.messages = new ArrayList<>();
        this.participants = new ArrayList<>();
        participants.add(user1);
        participants.add(user2);
    }

    public String generateChatID() {
        // this is a way to generate a random chat id
        // we are not going to be using it right now because everything is done in text,
        // so we're just going to use every participants username concatenated tg as the chat id
        

        // String temp = "UID" + UUID.randomUUID();
        // if (d.getData(temp, "chat") != null) {
        //     return generateChatID();
        // } else {
        //     return temp;
        // }

        for (String participant : participants) {
            chatID += participant;
        }
        return chatID;
    }

    public void addParticipantToChat(String participant) {
        participants.add(participant);
    }

    public void sendMessage(String mess, String sender) {
        Message message = new Message(this.chatID, sender, mess);
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
