public interface MessageInterface {
    //getters
    public String getMessage();
    public UserProfile getSender();
    public UserProfile getReceiver();
    public String getChat();
    //setters
    public boolean setMessage(String message);
    public boolean setSender(UserProfile sender);
    public boolean setReceiver(UserProfile receiver);
    public boolean setChat(String chatName);

    public boolean deleteMessage(Message message);
    public boolean sendMessage(UserProfile receiver, Message message);
}
