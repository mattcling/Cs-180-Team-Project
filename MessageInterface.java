import java.time.LocalDateTime;

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

public interface MessageInterface {

    String getMessageID();

    LocalDateTime getDateTime();

    String getChatID();

    String getContents();

    String getSenderID();

    public void setContents(String contents);

    public void setMessageID(String messageID);

    public void setDateTime(LocalDateTime dateTime);

    public void setChatID(String chatID);
}