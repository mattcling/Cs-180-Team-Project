public interface FreindListInterface {

    boolean SendFR(String senderID, String ReceicerID);
    boolean AccpetFR(String senderID, String ReceicerID);
    boolean RemoveFreind(String removedID, String userID);
    boolean blockFreind(String blockedID, String userID);
    boolean unblockFreind(String unblockedID, String userID);

}
