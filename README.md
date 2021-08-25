# Technical Test using Spring Boot 2.5 and JUnit 5 compiling with Java 11

## How to install / run the app
### Install the necessary applications

Download JDK 11: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html

Configure JDK: https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/index.html

Download Gradle: https://gradle.org/releases/

Configure Gradle: https://gradle.org/install/

### Run the Munro Library app
Clone the *munro-library* repository which contains de project.

Once you have configured the JDK and Gradle, run the following commands to:

*gradle build* => Downloads all the dependencies found in build.gradle, compiles the project and also ***runs*** the tests.

*gradle build -x test* => Same as the previous command ***without running*** the tests

*gradle test* => Run only the tests

*gradle bootRun* => Run Spring Boot app


### Postman
In order to test the api, I have provided a Postman collection (*Munro-Library.postman_collection*), which contains the existing endpoints and examples of calls for the different scenarios.

### Swagger
The project is also delivered with the Swagger tool.

Once the application is running, you can access it from http://localhost:8080/swagger-ui/#/

### Current stack

- Java 11
- Spring Boot 2.5
- JUnit 5.7
- Mockito 3.11
- OpenCSV 5.4
- Gradle 7.1