import java.io.Serializable;

public class Message implements MessageInterface {

    //Varaibles
    public String message;
    public String sender;
    public String receiver;
    public String chatName;

    // Constructers
    public Message(String message, String sender, String receiver, String chatName) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;

    }

    //Setters
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    //Getters
    public String getMessage() {
        return message;
    }
    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }

    @Override
    public String getChatName() {
        return chatName;
    }

}
