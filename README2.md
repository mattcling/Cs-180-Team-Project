 # Additional info for Phase 2:

Phase 2 consists of building the communication functionality of your team's social media platform. The core requirements for this phase are as follows:

1. Server supports multiple clients. CHECK  |||||||||||||||||||||||||
    
2. Server/Database interaction is thread safe. (wtf) (cooked)

3. Server should either extend Thread or implement the Runnable interface - the latter is recommended. Reminder: A class can implement multiple interfaces. If you user Runnable, you are still required to have a dedicated interface for your server class. CHECK |||||||||||||||||||||||||
    
	
4. Client does not store information locally - All information is stored on the server side and accessed via network IO using the client.
    
5. Interfaces and Test cases from Phase 1 should be updated to reflect any changes made to the Database in Phase 2.
    
6. Test cases for Server and Client classes should test any constructors and all methods, excluding the "run" methods. You do not need to test the IO functionality with JUNIT. 
    
7. Your README should include detailed testing information for the IO functionality - it will be tested manually by the grading TA.
    
8. Issues from Phase 1 should be corrected - failure to do so will result in further loss of points.

 ## TODO:

- Ensure server/database interaction is thread safe.
- Implement server class using Runnable interface.
- Ensure client does not store information locally.
- Update interfaces and test cases from Phase 1.
- Write test cases for Server and Client classes.
- Include detailed testing information for IO functionality in README.
- Correct issues from Phase 1.
