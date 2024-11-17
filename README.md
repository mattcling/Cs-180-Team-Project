# Instructions on how to compile and run your project.


## Submit history
Matthew - Submitted Vocareum workspace on Brightspace for Phase 1 from team github repository

#### Commit history on Team Github Repo:
Mattew - Submitted Database and Message classes, test cases and interfaces
Siddesh - Submitted ServerClient and Chat classes, test cases and interfaces
Charlotte - Submitted UserClient classes, test cases and interfaces
Kimaya - Submitted Profile classes, test cases and interfaces and README
Luke - Helped work on Login for ServerClient


### Class Descriptions

#### Chat.java
Name - Chat
Type - class
Relationship - implements Serializable, ChatInterface
Functionality - Encapsulates the basic functions and variables needed to create a chat, add participants, send and receive messages, and manage the chat's state. 
Fields - chatID, messages, participants, static SERIAL_VERSION_UID
Constructor - Chat()
Methods - 
&nbsp;&nbsp;&nbsp;addParticipantToChat()
&nbsp;&nbsp;&nbsp;sendMessage(String mess, String sender)
&nbsp;&nbsp;&nbsp;removeMessage(Message message)
&nbsp;&nbsp;&nbsp;receiveChat(Message message)
&nbsp;&nbsp;&nbsp;getChatID()
&nbsp;&nbsp;&nbsp;setChatID(String chatId)
&nbsp;&nbsp;&nbsp;getParticipants()
Testing - ChatTest.java test case file

#### ChatInterface.java
Name - ChatInterface
Type - interface
Relationship - interface for Chat class
Functionality - It outlines methods related to managing and interacting with a chat system.
Methods - 
&nbsp;&nbsp;&nbsp;setChatID(String chatID)
&nbsp;&nbsp;&nbsp;receiveChat(Message message)
&nbsp;&nbsp;&nbsp;removeMessage(Message message)
&nbsp;&nbsp;&nbsp;generateChatID()
&nbsp;&nbsp;&nbsp;getChatID()
&nbsp;&nbsp;&nbsp;getParticipants()
&nbsp;&nbsp;&nbsp;sendMessage(String mess, String sender)

#### Database.java
Name - Database
Type - class
Relationship - implements DatabaseInterface
Functionality - Encapsulates the basic functions and variables needed to create a chat, add participants, send and receive messages, and manage the chat's state. 
Fields - chatID, messages, participants, static SERIAL_VERSION_UID
Constructor - Chat()
Methods - 
&nbsp;&nbsp;&nbsp;addParticipantToChat()
&nbsp;&nbsp;&nbsp;sendMessage(String mess, String sender)
&nbsp;&nbsp;&nbsp;removeMessage(Message message)
&nbsp;&nbsp;&nbsp;receiveChat(Message message)
&nbsp;&nbsp;&nbsp;getChatID()
&nbsp;&nbsp;&nbsp;setChatID(String chatId)
&nbsp;&nbsp;&nbsp;getParticipants()
Testing - ChatTest.java test case file


This branch has a similar idea to the first branch the main branch but i made a large number of changes to support hashmaps as a data storage method.

i then also recreated a few classes with conditions to confirm things will work the way we hope
