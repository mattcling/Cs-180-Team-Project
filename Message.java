public class Message {

    //Varaibles
    public String message;
    public String sender;
    public String receiver;

    // Constructers
    public Message(String message, String sender, String receiver) {
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

}
