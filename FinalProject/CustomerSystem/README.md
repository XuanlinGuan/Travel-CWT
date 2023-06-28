# CustomerSystem

## Description

CustomerSystem is a SpringBoot application that serves as the front-end for the TRAVEL - CWT system. It provides a set of APIs to handle user authentication and login functionality. The application communicates with other components in the system through Kafka messaging.

Configure the following properties in the application.properties file:

server.port=8081

spring.kafka.producer.bootstrap-servers=localhost:9092
Build and run the application using the appropriate commands or IDE tools.

### API Endpoints

#### Welcome API

Endpoint: /customersystem/index
Method: GET
Description: Returns a welcome message for the TRAVEL - CWT system.
Response: A string with the welcome message.

### Login API

Endpoint: /customersystem/login
Method: POST
Description: Handles the login request and sends the login information for processing.
Request Body: The login details in JSON format.
Response: A string indicating the status of the login process.
Controller Layer
The LogInController class is responsible for handling the API requests related to user login. It has the following methods:

### start()

Endpoint: /customersystem/index
Method: GET
Description: Returns a welcome message for the TRAVEL - CWT system.
Returns: A string with the welcome message.

### login()

Endpoint: /customersystem/login
Method: POST
Description: Handles the login request and sends the login information for processing.
Parameters:
login: The login details provided in the request body.
Returns: A string indicating the status of the login process.
Conclusion
The CustomerSystem application serves as the front-end for the TRAVEL - CWT system. It provides APIs for user authentication and login functionality. By following the setup instructions and using the provided API endpoints, you can interact with the system and perform login operations. Make sure to configure the application properties, such as the server port and Kafka bootstrap servers, according to your environment.