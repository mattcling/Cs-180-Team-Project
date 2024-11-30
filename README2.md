# Additional info for Phase 2:

This branch has a similar idea to the first branch the main branch but i made a large number of changes to support hashmaps as a data storage method.

i then also recreated a few classes with conditions to confirm things will work the way we hope

Phase 2 consists of building the communication functionality of your team's social media platform. The core requirements for this phase are as follows:

1. CHECK Server supports multiple clients. 
    
2. CHECK Server/Database interaction is thread safe. 

3. CHECK Server should either extend Thread or implement the Runnable interface - the latter is recommended. Reminder: A class can implement multiple interfaces. If you user Runnable, you are still required to have a dedicated interface for your server class. \
    
	
4. CHECK Client does not store information locally - All information is stored on the server side and accessed via network IO using the client.
    
5. ? Interfaces and Test cases from Phase 1 should be updated to reflect any changes made to the Database in Phase 2.
    
6. NO Test cases for Server and Client classes should test any constructors and all methods, excluding the "run" methods. You do not need to test the IO functionality with JUNIT. 
    
7. NO Your README should include detailed testing information for the IO functionality - it will be tested manually by the grading TA.
    
8. Issues from Phase 1 should be corrected - failure to do so will result in further loss of points.

 ## TODO:

- Fix message functionality between two users so that its live
- Fix tests for non-threaded classes
- Fix tests for threaded classes
- Tests Fail/No Compile: ServerClient, Chat, User, Message, UserClient, ProfileTest
- -10: Issues: 
	- Cannot do anything after failed login (Fixed probably?)
	- Cannot quit (Fixed probably?)
	- Cannot acutally chat
	- No persistent chat 
	- Cannot see nor delete previous messages 
- Cannot restrict messages to everybody or just friends

Overall Feedback
Overall comments: -17.5: Tests Fail/No Compile: ServerClient, ChatInterface, Chat, User, Message, DatabaseInterface, MessageInterface, ProfileInterface, UserClient, User Pass: ProfileTest, UserInterface 2.5 points (ratio of passing tests to fail tests and also ratio is halved as all tests had to be moved to the main folder to compile) -1: Documentation above each class is just copy and pasted -1: Grading script -10: Issues: Cannot do anything after failed login Cannot quit Cannot acutally chat (like cannot receive messages) If I can it is not visible to user No persistent chat Cannot see nor delete previous messages Cannot restrict messages to everybody or just friends Score: 95-17.5-1-1-10 = 65.5 Good job overall. Missed most points from tests. Just make sure they work. Also I don’t care if you have interface tests so you can honestly just delete them. But, really, those tests should just run. Not a single one compiled and if it did it failed. There was like 2 test classes that ran and passed. Also note the issues in the issues section. 
