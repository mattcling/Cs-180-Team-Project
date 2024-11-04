import java.time.LocalDateTime;

/**
 * A interface for Message class and its functions.
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Purdue CS
 * @version November 2, 2024
 */

public interface MessageInterface {
    String getSenderID();

    String getReceiverID();

    String getMessageID();

    LocalDateTime getDateTime();

    String getContents();

    public void setContents(String contents);

    public void setMessageID(String messageID);

    public void setSenderID(String senderID);

    public void setReceiverID(String receiverID);

    public void setDateTime(LocalDateTime dateTime);
}
