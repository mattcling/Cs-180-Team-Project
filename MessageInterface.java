import java.time.LocalDateTime;

public interface MessageInterface {
    String getSenderID();
    String getReceiverID();
    String getMessageID();
    LocalDateTime getDateTime();
    String getMessageInfo();

    public void setMessageInfo(String message);
    public void setMessageID(String messageID);
    public void setSenderID(String senderID);
    public void setReceiverID(String receiverID);
    public void setDateTime(LocalDateTime dateTime);
}
