import java.io.*;
import java.util.*;


public class Database implements DatabaseInterface{

    // going to be doing a lot of experimenting if things dont make sense sorry ask me in person
    // i will try my best to explain in here but it will be a lot of words and work so it may be easier to ask irl
    // anyway im going to be using in memory and out memory has maps that will hopefully
    // work and stay even after resets
    // we will be using hashmap tabels and the tables will all have their own info stored

    private Map<String,User> userTable = new HashMap<>();
    private Map<String, Chat> chatTable = new HashMap<>(); // if this is still error its because i havent ended up making chat class
    private Map<String, Message> messageTable = new HashMap<>();

    private final String USERDATE_FILE = "userTable.ser";
    private final String CHATDATE_FILE = "chatTable.ser";
    private final String MESSAGEDATE_FILE = "messageTable.ser";


    public void LoadOldData(){ // this needs loadtable method made
        userTable = loadTableUser(USERDATE_FILE);
        chatTable = loadTableChat(CHATDATE_FILE);
        messageTable = loadTableMessage(MESSAGEDATE_FILE);
    }

    public void saveData() {
        saveTableUser(userTable, USERDATE_FILE);
        saveTableChat(chatTable, CHATDATE_FILE);
        saveTableMessage(messageTable, MESSAGEDATE_FILE);
        // once again unmade method if still error talk to me and i will see if the method is done also note to me finsih
    }

    public void saveTableUser(Map<String, User> table, String filename){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(table);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    // this saves the hasmap to a file so that i can be kept on refresh of the server
    public void saveTableChat(Map<String, Chat> table, String filename){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(table);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void saveTableMessage(Map<String, Message> table, String filename){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(table);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    // so this basiclly just on refresh load the hashmaps again
    public  Map<String, User> loadTableUser(String filename){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            return (Map<String, User>) in.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    public  Map<String, Chat> loadTableChat(String filename){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            return (Map<String, Chat>) in.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    public  Map<String, Message> loadTableMessage(String filename){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            return (Map<String, Message>) in.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // this just runs the load method to get the data for the database to work!!
    public void initializeDatabase(){
        System.out.println("Initializing database...");

        userTable = loadTableUser(USERDATE_FILE);
        messageTable = loadTableMessage(MESSAGEDATE_FILE);
        chatTable = loadTableChat(CHATDATE_FILE);

        System.out.println("Database initialized!!!");
    }

    public boolean saveData(Object data, String tableName){
        switch(tableName){
            case "user":
                if(data instanceof User){
                    User user = (User) data;
                    userTable.put(user.getUserName(), user);
                    System.out.println("User " + user.getUserName() + " has been saved!");
                    return true;
                }
                break;
            case "chats":
                if(data instanceof Chat){
                    Chat chat = (Chat) data;
                    chatTable.put(chat.getChatID(), chat);
                    System.out.println("Chat ID#: " + chat.getChatID() + " has been saved!" );
                    return true;
                }
                break;

            case "messages":
                if(data instanceof Message){
                    Message message = (Message) data;
                    messageTable.put(message.getMessageID(), message);
                    System.out.println("Message ID#: " + message.getMessageID() + " was just saved!");
                    return true;
                }
                break;
            default:
                System.out.println("Invalid table name!");
        }
        return false;
    }

    public Object getData(String tableName, String key){
        switch(tableName){
            case "users":
                return userTable.get(key);
            case "chats":
                return chatTable.get(key);
            case "messages":
                return messageTable.get(key);
            default:
                System.out.println("Invalid table name!");
                return null;
        }
    }
    public boolean ChangeData(String tableName,Object data ,String key){
        switch(tableName){
            case "user":
                if(data instanceof User){
                    userTable.put(key, (User) data);
                    System.out.println("User " + key + " has been updated!");
                    return true;
                }
                break;
            case "chat":
                if(data instanceof Chat){
                    chatTable.put(key, (Chat) data);
                    System.out.println("Chat " + key + " has been updated!");
                    return true;
                }
                break;
            case "message":
                if(data instanceof Message){
                    messageTable.put(key, (Message) data);
                    System.out.println("Message " + key + " has been updated!");
                    return true;
                }
                break;
            default:
                System.out.println("Invalid table name!");
        }
        return false;
    }

    public boolean deleteData(String tableName, String key){
        switch(tableName){
            case "users":
                if(userTable.remove(key) != null){
                    System.out.println("User " + key + " has been deleted!");
                    return true;
                }
                break;
            case "chats":
                if(chatTable.remove(key) != null){
                    System.out.println("Chat " + key + " has been deleted!");
                    return true;
                }
                break;
            case "messages":
                if(messageTable.remove(key) != null){
                    System.out.println("Message " + key + " has been deleted!");
                    return true;
                }
                break;
            default:
                System.out.println("Invalid table name!");
        }
        return false;
    }

}
