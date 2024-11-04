import java.time.LocalDateTime;
import java.io.Serializable;

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


public class Message implements Serializable, MessageInterface {
    //private final static long SerialVersionUID = 1L; // this will set the serialization for later when needed

    private String messageID;
    private String senderID;
    private String receiverID;
    private String contents;
    private LocalDateTime dateTime; // this may be a way to track time sent for now im not gunna do much with it.

    public Message(String messageID, String senderID, String receiverID, String contents) {
        if (messageID == null || messageID.isEmpty()) {
            throw new IllegalArgumentException("messageID cannot be null or empty.");
        }
        if (senderID == null || senderID.isEmpty()) {
            throw new IllegalArgumentException("senderID cannot be null or empty.");
        }
        if (receiverID == null || receiverID.isEmpty()) {
            throw new IllegalArgumentException("receiverID cannot be null or empty.");
        }
        if (contents == null || contents.isEmpty()) {
            throw new IllegalArgumentException("contents cannot be null or empty.");
        }
        this.messageID = messageID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.contents = contents;
        this.dateTime = LocalDateTime.now(); // this is how to set it as a variable!!!
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        if (messageID == null || messageID.isEmpty()) {
            throw new IllegalArgumentException("messageID cannot be null or empty.");
        }
        this.messageID = messageID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        if (senderID == null || senderID.isEmpty()) {
            throw new IllegalArgumentException("senderID cannot be null or empty.");
        }
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        if (receiverID == null || receiverID.isEmpty()) {
            throw new IllegalArgumentException("receiverID cannot be null or empty.");
        }
        this.receiverID = receiverID;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        if (contents == null || contents.isEmpty()) {
            throw new IllegalArgumentException("contents cannot be null or empty.");
        }
        this.contents = contents;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("receiverID cannot be null or empty.");
        }
        this.dateTime = LocalDateTime.now();
    }

}