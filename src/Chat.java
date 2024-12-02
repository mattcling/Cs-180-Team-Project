import java.io.*;
import java.util.*;


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


public class Chat implements Serializable, ChatInterface {
    private static final long SERIAL_VERSION_UID = 1L;

    private String chatID;//to keep track of chats
    private List<Message> messages;//keeps tractk of messages in an array
    private List<String> participants;//keeps track of participants in an array
    private static Database d = new Database();


    // constructor :D
    public Chat(String user1, String user2) {//creats 1-1 chat and basically sets everyting at 0/null/empty
        d.loadOldData();
        this.messages = new ArrayList<>();
        this.participants = new ArrayList<>();
        participants.add(user1);
        participants.add(user2);
        chatID = generateChatID();
        User user = (User) d.getData("user", user1);
        System.out.println(user.addChat(chatID));        
        d.writeData(this, "chat");
        d.writeData(user, "user");
    }

    public String generateChatID() {
        // this is a way to generate a random chat id
        // we are not going to be using it right now because everything is done in text,
        // so we're just going to use every participants username concatenated tg as the chat id
        

        // String temp = "UID" + UUID.randomUUID();
        // if (d.getData(temp, "chat") != null) {
        //     return generateChatID();
        // } else {
        //     return temp;
        // }
        chatID = "";
        for (String participant : participants) {
            chatID += participant;
        }
        return chatID;
    }

    public void addParticipantToChat(String participant) {
        participants.add(participant);
    }

    public void sendMessage(String mess, String sender) {
        System.out.println(this.chatID);
        Message message = new Message(this.chatID, sender, mess);
        messages.add(message);
        //d.writeData(message, "message");
        d.writeData(this, "chat");
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public List<Message> receiveChat(Message message) {
        return new ArrayList<>(messages);
    }

    public String getChatID() {
        return this.chatID;
    }

    public void setChatID(String chatId) {
        this.chatID = chatId;
    }

    public List<String> getParticipants() {
        return new ArrayList<>(participants);
    }

    public List<Message> getMessages(){
        return messages;
    }

}
