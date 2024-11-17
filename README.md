# Instructions on how to compile and run your project.


## Submit history
Matthew - Submitted Vocareum workspace on Brightspace for Phase 1 from team github repository

#### Commit history on Team Github Repo:
Mattew - Submitted Database and Message classes, test cases and interfaces <br>
Siddesh - Submitted ServerClient and Chat classes, test cases and interfaces <br>
Charlotte - Submitted UserClient classes, test cases and interfaces <br>
Kimaya - Submitted Profile classes, test cases and interfaces and README <br>
Luke - Helped work on Login for ServerClient <br>


## Class Descriptions

#### Chat.java
**Name** - Chat <br>
**Type** - class <br>
**Relationship** - implements Serializable, ChatInterface <br>
**Functionality** - Encapsulates the basic functions and variables needed to create a chat, add participants, send and receive messages, and manage the chat's state. <br>
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
**Testing** - ChatTest.java test case file <br>

#### ChatInterface.java
**Name** - ChatInterface <br>
**Type** - interface <br>
**Relationship** - interface for Chat class <br>
**Functionality** - It outlines methods related to managing and interacting with a chat system. <br>
**Methods** - <br>
- setChatID(String chatID) <br>
- receiveChat(Message message) <br>
- removeMessage(Message message) <br>
- generateChatID() <br>
- getChatID() <br>
- getParticipants() <br>
- sendMessage(String mess, String sender) <br>

#### Database.java <br>
**Name** - Database <br>
**Type** - class <br>
**Relationship** - implements DatabaseInterface <br>
**Functionality** - Encapsulates the basic functions and variables needed to create a chat, add participants, send and receive messages, and manage the chat's state. <br>
**Fields** - chatID, messages, participants, static SERIAL_VERSION_UID <br>
**Constructor** - Chat() <br>
**Methods** - 
- addParticipantToChat() <br>
- sendMessage(String mess, String sender) <br>
- removeMessage(Message message) <br>
- receiveChat(Message message) <br>
- getChatID() <br>
- setChatID(String chatId) <br>
- getParticipants() <br>
**Testing** - ChatTest.java test case file <br>


This branch has a similar idea to the first branch the main branch but i made a large number of changes to support hashmaps as a data storage method.

i then also recreated a few classes with conditions to confirm things will work the way we hope
