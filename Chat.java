import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Chat implements Serializable, ChatInterface{
    private static final long serialVersionUID = 1L;

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
    public void RemoveMessage(Message message) {
        messages.remove(message);
    }

    public List<Message> ReceiveChat(Message message){
        return new ArrayList<>(messages);
    }

    public String getChatID(){
        return chatID;
    }

    public void setChatID(String chatID){
        this.chatID = chatID;
    }
    public List<String> getParticipants(){
        return new ArrayList<>(participants);
    }




}
