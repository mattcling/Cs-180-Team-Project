import java.util.Map;

/**
 * A interface for the databse class
 *
 * <p>
 * Purdue University -- CS18000 -- Fall 2024
 * </p>
 *
 * @version November 2, 2024
 */
public interface DatabaseInterface {

    // this is going to attempt to use tables as a database.

    boolean saveData(Object data, String tablename);

    boolean deleteData(String tablename, String key);

    boolean ChangeData(String tablename, Object data, String key);

    Object getData(String tableName, String key);

    public void initializeDatabase();

    public void LoadOldData();

    public void saveData();

    public Map<String, Message> loadTableMessage(String filename);

    public Map<String, Chat> loadTableChat(String filename);

    public Map<String, User> loadTableUser(String filename);

    void saveTableMessage(Map<String, Message> table, String filename);

    public void saveTableChat(Map<String, Chat> table, String filename);

    public void saveTableUser(Map<String, User> table, String filename);


}
