public interface Message {


    //getters
    public String getMessage();
    public User getSender();
    public User getReceiver();
    //setters
    public User setMessage(String message);
    public User setSender(User sender);
    public User setReceiver(User receiver);
    
}
