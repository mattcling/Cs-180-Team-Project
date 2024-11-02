import java.time.LocalDateTime; // need this for timestamps if we want to use them
import java.io.Serializable;
// this is used for serialization to track version stuff and all that hard to explain
// easy way t think about it is just making it easier to store later on!


public class Message implements Serializable, MessageInterface {
    private final static long SerialVersionUID = 1L; // this sets the serialization for later when needed

    private String messageID;
    private String SenderID;
    private String ReceiverID;
    private String contents;
    private LocalDateTime dateTime; // this may be a way to track time sent for now im not gunna do much with it.





    public Message(String messageID, String SenderID, String ReceiverID, String contents) {
        this.messageID = messageID;
        this.SenderID = SenderID;
        this.ReceiverID = ReceiverID;
        this.contents = contents;
        this.dateTime = LocalDateTime.now(); // this is how to set it as a variable!!!

    }

    public String getMessageID() {
        return messageID;
    }
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
    public String getSenderID() {
        return SenderID;
    }
    public void setSenderID(String senderID) {
        SenderID = senderID;
    }
    public String getReceiverID() {
        return ReceiverID;
    }
    public void setReceiverID(String receiverID) {
        ReceiverID = receiverID;
    }
    public String getMessageInfo() {
        return contents;
    }
    public void setMessageInfo(String contents) {
        this.contents = contents;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = LocalDateTime.now();
    }

}
