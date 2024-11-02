import java.time.LocalDateTime;

public interface MessageInterface {

    String getSenderID();
    String getReceiverID();
    String getMessageID();
    void setMessageInfo(String message);
    public void setMessageID(String messageID);
    public void setSenderID(String senderID);
    public void setReceiverID(String receiverID);
    public String getMessageInfo();
    public LocalDateTime getDateTime();
    public void setDateTime(LocalDateTime dateTime);

}
