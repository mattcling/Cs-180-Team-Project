public interface Message {


    //getters
    public String getMessage();
    public UserProfile getSender();
    public UserProfile getReceiver();
    //setters
    public UserProfile setMessage(String message);
    public UserProfile setSender(UserProfile sender);
    public UserProfile setReceiver(UserProfile receiver);
    
}
