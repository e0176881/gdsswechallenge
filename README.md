# Getting Started


### Prerequisites

- [Java 18](https://www.oracle.com/java/technologies/downloads/)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- [Gradle](https://gradle.org/releases/)


### Setting up

1. Step 1: git clone project from Github
2. Step 2: Ensure you have gradle, java_jdk18, mysql8 installed
3. Step 3: Create a database with schema name: test
4. Step 4: Navigate to src/main/resources/application.properties 
and change the username and password to match those of yours in your mySql.
5. Step 5: Note that this is built using IntelliJ as the IDE,
and you can run the SwechallengeApplication by clicking on the play button.
6. Alternatively, to start the application, open up your terminal or command prompt and navigate to the project root directory 
 ```bash
./gradlew bootrun
```
7. To run the unit test
 ```bash
./gradlew clean build
```

Note: For ease of testing, the database is using hibernate to automatically create a schema, and every time you start the application,
it will always be reset to the initial preloaded data - John with a salary of 2000 and John2 with a salary of 4000

### Main API routes

The two apis used are: http://localhost:8080/users [GET] and http://localhost:8080/upload [POST]
| Method | Route                         |
| :----- | :---------------------------- |
| GET    | /users                        | 
| POST   | /upload                       | 

