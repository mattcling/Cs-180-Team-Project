## How to run and compile project
Clone github repository on IDE (such as VSCode) and run Server side classes to compile and view project

## Submit history
Matthew - Submitted Vocareum workspace on Brightspace for Phase 1 from team github repository

#### Commit history on Team Github Repo:
Mattew - Submitted Database and Message classes, test cases and interfaces <br>
Siddesh - Submitted ServerClient and Chat classes, test cases and interfaces <br>
Charlotte - Submitted UserClient classes, test cases and interfaces <br>
Kimaya - Submitted ServerClient and User classes, test cases and interfaces and README <br>
Luke - Helped work on Login for ServerClient <br>


## Class Descriptions

#### Chat.java
**Name** - Chat <br>
**Type** - class <br>
**Relationship** - implements Serializable, ChatInterface <br>
**Functionality** - Encapsulates the basic functions and variables needed to create a chat, add participants, send and receive messages, and manage the chat's state. <br>
**Testing** - ChatTest.java test case file <br>
**Fields** - chatID, messages, participants, static SERIAL_VERSION_UID <br>
**Constructor** - Chat() <br>
**Methods** - <br>
- addParticipantToChat() <br>
- sendMessage(String mess, String sender) <br>
- removeMessage(Message message) <br>
- receiveChat(Message message) <br>
- getChatID() <br>
- setChatID(String chatId) <br>
- getParticipants() <br>
- getMessages() <br>
<br><br>

#### ChatInterface.java
**Name** - ChatInterface <br>
**Type** - interface <br>
**Relationship** - interface for Chat class <br>
**Functionality** - It outlines methods related to managing and interacting with a chat system. <br>
**Testing** - ChatInterfaceTest.java text case file <br>
**Methods** - <br>
- setChatID(String chatID) <br>
- receiveChat(Message message) <br>
- removeMessage(Message message) <br>
- generateChatID() <br>
- getChatID() <br>
- getParticipants() <br>
- sendMessage(String mess, String sender) <br>
<br><br>

#### Database.java <br>
**Name** - Database <br>
**Type** - class <br>
**Relationship** - implements DatabaseInterface <br>
**Functionality** - Encapsulates the basic functions and variables needed to create a chat, add participants, send and receive messages, and manage the chat's state. <br>
**Testing** - DatabaseTest.java test case file <br>
**Fields** - userTable, chatTable, messageTable, userDataFile, chatDataFile, messageDataFile, object <br>
**Constructor** - Chat() <br>
**Methods** - 
- loadOldData() <br>
- saveTableUser(Map<String, User> table, String filename) <br>
- saveTableChat(Map<String, Chat> table, String filename) <br>
- saveTableMessage(Map<String, Message> table, String filename) <br>
- loadTableUser(String filename) <br>
- loadTableChat(String filename) <br>
- loadTableMessage(String filename) <br>
- initializeDatabase() <br>
- writeData(Object data, String tableName) <br>
- containsObject(String tableName, String key) <br>
- getData(String tableName, String key) <br>
- changeData(String tableName, Object data, String key) <br>
- deleteData(String tableName, String key) <br>
<br><br>

#### DatabaseInterface.java <br>
**Name** - DatabaseInterface <br>
**Type** - interface <br>
**Relationship** - interface for Database class <br>
**Functionality** - Defines a contract for managing and interacting with a database-like system created in Database class. It outlines methods for common operations such as writing, deleting, retrieving, and modifying data, as well as initializing, loading, and saving tables. <br>
**Testing** - DatabaseInterfaceTest.java test case file <br>
**Methods** - 
- writeData(Object data, String tablename) <br>
- deleteData(String tablename, String key) <br>
- changeData(String tablename, Object data, String key) <br>
- getData(String tableName, String key) <br>
- initializeDatabase() <br>
- loadOldData() <br>
- loadTableMessage(String filename) <br>
- loadTableChat(String filename) <br>
- loadTableUser(String filename) <br>
- saveTableMessage(Map<String, Message> table, String filename) <br>
- saveTableChat(Map<String, Chat> table, String filename) <br>
- saveTableUser(Map<String, User> table, String filename) <br>
<br><br>

#### Message.java <br>
**Name** - Message <br>
**Type** - class <br>
**Relationship** - implements Serializable, MessageInterface <br>
**Functionality** - The Message class represents an individual message in a chat system and enables creating, validating, and managing the data associated with the message <br>
**Testing** - MessageTest.java test case file <br>
**Fields** - static Database d, chatID, contents, senderID, LocalDateTime dateTime
**Constructor** - public Message(String chatID, String senderID, String contents) <br>
**Methods** - 
- getMessageID() <br>
- getChatID() <br>
- getSenderID() <br>
- getContents() <br>
- getDateTime() <br>
- generateUserID() <br>
- setMessageID(String messageID) <br>
- setContents(String contents) <br>
- setDateTime(LocalDateTime dateTime) <br>
- setChatID(String chatID) <br>
<br><br>

#### MessageInterface.java <br>
**Name** - MessageInterface <br>
**Type** - interface <br>
**Relationship** - interface for Message class <br>
**Functionality** - Defines the structure and behavior required for Messages class in the chat system. Provides essential methods for interacting with message data, such as retrieving and modifying message details. <br>
**Testing** - MessageInterfaceTest.java test case file <br>
**Methods** - 
- getMessageID() <br>
- getChatID() <br>
- getSenderID() <br>
- getContents() <br>
- getDateTime() <br>
- setMessageID(String messageID) <br>
- setContents(String contents) <br>
- setDateTime(LocalDateTime dateTime) <br>
- setChatID(String chatID) <br>
<br><br>

#### Profile.java <br>
**Name** - Profile <br>
**Type** - class <br>
**Relationship** - implements Serializable, ProfileInterfaces <br>
**Functionality** - Represents a user profile in the chat system and provides functionality to manage and display profile information. <br>
**Testing** - ProfileTest.java test case file <br>
**Fields** - userID, bio, profilePicture <br>
**Constructor** - Profile(String userID, String bio, String userProfilePicture) <br>
**Methods** - 
- getUserID() <br>
- setUserID(String userID) <br>
- getBio() <br>
- setBio(String bio) <br>
- getDateTime() <br>
- getprofilePicture() <br>
- setprofilePicture(String userProfilePicture) <br>
- updateProfile(String userbio, String userProfilePicture) <br>
- displayProfile() <br>
<br><br>

#### ProfileInterface.java <br>
**Name** - Profile <br>
**Type** - interface <br>
**Relationship** - interface for Profile class <br>
**Functionality** - Defines the structure for managing and interacting with user profile data by outlining the essential methods for Profile class <br>
**Testing** - ProfileInterfaceTest.java test case file <br>
**Methods** - 
- getUserID() <br>
- setUserID(String userID) <br>
- getBio() <br>
- setBio(String bio) <br>
- getDateTime() <br>
- getprofilePicture() <br>
- setprofilePicture(String userProfilePicture) <br>
- updateProfile(String userbio, String userProfilePicture) <br>
- displayProfile() <br>
<br><br>

#### ServerClient.java <br>
**Name** - ServerClient <br>
**Type** - class <br>
**Relationship** - implements Runnable <br>
**Functionality** - Represents a server-side implementation of a client-server chat system using Java socket programming. Handles client connections, user authentication, and various actions like chatting, searching users, and managing friends. <br>
**Testing** - ServerClientTest.java test case file <br>
**Fields** - socket, static Database d, stop <br>
**Constructor** - ServerClient(Socket socket) <br>
**Methods** - 
- run() <br>
- public static void main(String[] args) <br>
<br><br>

#### ServerClientInterface.java <br>
**Name** - ServerClientInterface <br>
**Type** - interface <br>
**Relationship** - interface for ServerClient class <br>
**Functionality** - Defines methods required for server-client interactions <br>
**Testing** - ServerClientInterface.java test case file <br>
<br><br>

#### User.java <br>
**Name** - User <br>
**Type** - class <br>
**Relationship** - implements UserInterface, Serializable <br>
**Functionality** - Represents a user in a system, providing functionalities to manage user information, authenticate users, and manage their relationships with others. <br>
**Testing** - UserTest.java test case file <br>
**Fields** - userID, password, userName, profilePicture, friendsList, blockedUsers, static SERIAL_VERSION_UID <br>
**Constructor** - User(String username, String password) <br>
**Methods** - 
- createAccount(String username, String newPassword) <br>
- login(String username, String userpassword) <br>
- logout() <br>
- addFriend(String friendsID) <br>
- removeFriend(String friendsID) <br>
- addBlockedUser(String blockedUserID) <br>
- unBlockUser(String blockedUserID) <br>
- generateUserID() <br>
- getUserID() <br>
- getPassword() <br>
- getUserName() <br>
- getProfilePicture() <br>
- getFriendsList() <br>
- getBlockedUsers() <br>
- setUserName(String userName) <br>
- setProfilePicture(String profilePicture) <br>
- setFriendsList(List<String> friendsList) <br>
- setBlockedUsers(List<String> blockedUsers) <br>
- setPassword(String password) <br>
- setUserID(String userID) <br>
<br><br>

#### UserClient.java <br>
**Name** - UserClient <br>
**Type** - class <br>
**Relationship** - public class <br>
**Functionality** - Implements a client-side program for interacting with a server in a user management system. It facilitates user authentication, account creation, and various user-related actions by exchanging serialized objects over network socket. <br>
**Testing** - UserClientTest.java test case file <br>
**Methods** - 
- public static void main(String[] args) <br>
<br><br>

#### UserClientInterface.java <br>
**Name** - UserClientInterface <br>
**Type** - interface <br>
**Relationship** - interface for UserClient class <br>
**Functionality** -  Defines the structure for a user management system. Specifies the methods for UserClient class, ensuring consistent functionality for user account management, friends and blocked users management, and user details access/modification. <br>
**Testing** - UserClientInterfaceTest.java test case file <br>
**Methods** - 
- createAccount(String username, String password) <br>
- login(String username, String password) <br>
- logout() <br>
- generateUserID() <br>
- addBlockedUser(String blockedUserID) <br>
- unBlockUser(String blockedUserID) <br>
- removeFriend(String friendsID) <br>
- addFriend(String friendsID) <br>
- getUserID() <br>
- getProfilePicture() <br>
- getFriendsList() <br>
- getBlockedUsers() <br>
- setUserName(String userName) <br>
- setProfilePicture(String profilePicture) <br>
- setFriendsList(List<String> friendsList) <br>
- setBlockedUsers(List<String> blockedUsers) <br>