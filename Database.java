import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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


public class Database implements DatabaseInterface {

    // going to be doing a lot of experimenting if things dont make sense sorry ask me in person
    // i will try my best to explain in here but it will be a lot of words and work so it may be easier to ask irl
    // anyway im going to be using in memory and out memory has maps that will hopefully
    // work and stay even after resets
    // we will be using hashmap tabels and the tables will all have their own info stored

    private Map<String, User> userTable = new ConcurrentHashMap();
    private Map<String, Chat> chatTable = new ConcurrentHashMap<>();
    private Map<String, Message> messageTable = new ConcurrentHashMap<>();

    private final String userDataFile = "userTable.ser";
    private final String chatDataFile = "chatTable.ser";
    private final String messageDataFile = "messageTable.ser";
    //create a gatekeeper to prevent multiple instances of the database
    private static Object object = new Object();


    public void loadOldData() { // this needs loadtable method made
        userTable = loadTableUser(userDataFile);
        chatTable = loadTableChat(chatDataFile);
        messageTable = loadTableMessage(messageDataFile);
    }

    public synchronized void saveTableUser(Map<String, User> table, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // this saves the hasmap to a file so that i can be kept on refresh of the server
    public synchronized void saveTableChat(Map<String, Chat> table, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void saveTableMessage(Map<String, Message> table, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // so this basiclly just on refresh load the hashmaps again
    public Map<String, User> loadTableUser(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Map<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ConcurrentHashMap<>();
        }
    }

    public Map<String, Chat> loadTableChat(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Map<String, Chat>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ConcurrentHashMap<>();
        }
    }

    public Map<String, Message> loadTableMessage(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Map<String, Message>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ConcurrentHashMap<>();
        }
    }

    // this just runs the load method to get the data for the database to work!!
    public void  initializeDatabase() {
        
        System.out.println("Initializing database...");
        userTable = loadTableUser(userDataFile);
        messageTable = loadTableMessage(messageDataFile);
        chatTable = loadTableChat(chatDataFile);

        System.out.println("Database initialized!!!");
    }
    
    // synchronized to prevent multiple threads from accessing the same data
    public synchronized boolean writeData(Object data, String tableName) {
        switch (tableName) {
            case "user":
                if (data instanceof User) {
                    User user = (User) data;
                    userTable.put(user.getUserName(), user);
                    System.out.println("User " + user.getUserName() + " has been saved!");
                    this.saveTableUser(userTable, userDataFile);
                    return true;
                }
                break;
            case "chat":
                if (data instanceof Chat) {
                    Chat chat = (Chat) data;
                    chatTable.put(chat.getChatID(), chat);
                    System.out.println("Chat ID#: " + chat.getChatID() + " has been saved!");
                    this.saveTableChat(chatTable, chatDataFile);
                    return true;
                }
                break;

            case "message":
                if (data instanceof Message) {
                    Message message = (Message) data;
                    messageTable.put(message.getMessageID(), message);
                    System.out.println("Message ID#: " + message.getMessageID() + " was just saved!");
                    this.saveTableMessage(messageTable, messageDataFile);
                    return true;
                }
                break;
            default:
                System.out.println("Invalid table name!");
        }
        return false;
    }

    public boolean containsObject(String tableName, String key) {
        switch (tableName) {
            case "user":
                return userTable.containsKey(key);
            case "chat":
                return chatTable.containsKey(key);
            case "message":
                return messageTable.containsKey(key);
            default:
                System.out.println("Invalid table name!");
                return false;
        }
    }

    public Object getData(String tableName, String key) {
        switch (tableName) {
            case "user":
                return userTable.get(key);
            case "chat":
                return chatTable.get(key);
            case "message":
                return messageTable.get(key);
            default:
                System.out.println("Invalid table name!");
                return null;
        }
    }

    // synchronized to prevent multiple threads from accessing the same data
    public synchronized boolean changeData(String tableName, Object data, String key) {
        switch (tableName) {
            case "user":
                if (data instanceof User) {
                    userTable.put(key, (User) data);
                    System.out.println("User " + key + " has been updated!");
                    return true;
                }
                break;
            case "chat":
                if (data instanceof Chat) {
                    chatTable.put(key, (Chat) data);
                    System.out.println("Chat " + key + " has been updated!");
                    return true;
                }
                break;
            case "message":
                if (data instanceof Message) {
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
    // synchronized to prevent multiple threads from accessing the same data
    public synchronized boolean deleteData(String tableName, String key) {
        switch (tableName) {
            case "user":
                if (userTable.remove(key) != null) {
                    System.out.println("User " + key + " has been deleted!");
                    userTable.remove(key);
                    saveTableUser(userTable, userDataFile);
                    return true;
                }
                break;
            case "chat":
                if (chatTable.remove(key) != null) {
                    System.out.println("Chat " + key + " has been deleted!");
                    chatTable.remove(key);
                    saveTableChat(chatTable,chatDataFile);
                    return true;
                }
                break;
            case "message":
                if (messageTable.remove(key) != null) {
                    System.out.println("Message " + key + " has been deleted!");
                    messageTable.remove(key);
                    saveTableMessage(messageTable, messageDataFile);
                    return true;
                }
                break;
            default:
                System.out.println("Invalid table name!");
        }
        return false;
    }

}
