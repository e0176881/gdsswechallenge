# Getting Started

Step 1: git clone project from Github
Step 2: Ensure you have gradle, java_jdk18, mysql8 installed, 
Step 3: Create a database with schema name: test
Step 4: Navigate to src/main/resources/application.properties 
and change the username and password to match those of yours in your mySql.
Step 5: Note that this is built using IntelliJ as the IDE,
and you can run the SwechallengeApplication by clicking on the play button.
Alternatively, you can use gradle 'bootRun' command to start the application,
gradle 'clean' and 'build' to run the unit test

Note: The database is using create only schema, every time you start the application,
it will always be reset to the initial preloaded data - John with a salary of 2000 and John2 with a salary of 4000