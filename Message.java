import java.time.LocalDateTime;
import java.util.UUID;
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
    private String chatID;
    private String contents;
    private String senderID;
    private LocalDateTime dateTime;
    private static Database d = new Database();

     // this may be a way to track time sent for now im not gunna do much with it.

    public Message(String chatID, String senderID, String contents) {//creates new message and makes sure noting is null/empty
        d.loadOldData();
    
        this.messageID = generateUserID();
        this.contents = contents;
        this.chatID = chatID;
        this.senderID = senderID;
        this.dateTime = LocalDateTime.now(); // this is how to set it as a variable!!!
        
        if (senderID == null || messageID.isEmpty()) {
            throw new IllegalArgumentException("messageID cannot be null or empty.");
        }
        if (contents == null || contents.isEmpty()) {
            throw new IllegalArgumentException("contents cannot be null or empty.");
        }
        if (chatID == null || chatID.isEmpty()) {
            throw new IllegalArgumentException("chatID cannot be null or empty.");
        }

        d.writeData(this, "message");
    }

    public String getMessageID() {
        return messageID;
    }

    public String getChatID(){
        return chatID;
    }

    public String getSenderID(){
        return senderID;
    }

    public String getContents() {
        return contents;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String generateUserID() {//randomly generates message id
        String temp = "UID" + UUID.randomUUID();
        if (d.getData(temp, "message") != null) {
            return generateUserID();
        } else {
            return temp;
        }
    }

    //setters
    public void setMessageID(String messageID) {
        if (messageID == null || messageID.isEmpty()) {
            throw new IllegalArgumentException("messageID cannot be null or empty.");
        }
        this.messageID = messageID;
    }

    public void setContents(String contents) {
        if (contents == null || contents.isEmpty()) {
            throw new IllegalArgumentException("contents cannot be null or empty.");
        }
        this.contents = contents;
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("time cannot be null");
        }
        this.dateTime = LocalDateTime.now();
    }

    public void setChatID(String chatID) {
        if (chatID == null || chatID.isEmpty()) {
            throw new IllegalArgumentException("chatID cannot be null or empty.");
        }
        this.chatID = chatID;
    }

}