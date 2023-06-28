Project 2: TRAVEL - CWT
Description
This project involves the development of three SpringBoot applications: CustomerSystem, CWT, and CONCUR. These applications use Kafka streams to interact with each other to retrieve customer data and provide detailed user information and payments.

The CustomerSystem application receives user email, first name, and last name via an API. It then sends the user email to the CWT application through Kafka flow 1 producer. The CWT application retrieves the token associated with the input data from its database. If the token is valid, it is used; otherwise, a new token is generated and stored in the database before being returned. The token contains encoded user email and expiration timestamp.

The CONCUR application acts as a consumer of Kafka flow 1. It receives the customer email, first name, and last name from CWT and fetches detailed user information using the token. The token is decoded to check its expiration. If the token has expired, the request is rejected, and a new token is generated by calling the CWT application. Once the user details are obtained from the database, they are sent back to the CustomerSystem application as the producer of Kafka flow 2.

Goals
CustomerSystem Application
Provide an API to receive user email, first name, and last name.
Implement Kafka flow 1 producer to send user email to CWT.
Implement Kafka flow 2 consumer to receive user information from CONCUR and print it out.
CWT Application
Implement Kafka flow 1 consumer to receive customer email, first name, and last name and fetch the associated token from the database.
If the token is valid, use it. Otherwise, if the token is expired or will expire in 2 minutes, create a new token, store it in the database, and return it.
Encode the user email and expiration timestamp inside the token.
CONCUR Application
Manually use the token returned by CWT to call the API and fetch user details.
Decode the token to check if it has expired. If yes, reject the request and generate a new token by calling the CWT application. If not, decode the email address from the token and retrieve data from the database.
Simulate data from the CONCUR server using APIs (e.g., https://jsonplaceholder.typicode.com/users).
Implement APIs to update or delete customer information inside the CONCUR server.
Send the user details obtained from the database as the producer of Kafka flow 2 back to the CustomerSystem application.
Setup Instructions
Clone the repository: git clone <repository-url>
Set up the required dependencies and frameworks (SpringBoot, Kafka, etc.) for each application.
Configure the Kafka streams in the applications to connect to the correct topics and brokers.
Set up and configure the databases for CWT and CONCUR applications.
Create database tables for CWT and CONCUR applications.
Insert dummy data into the tables if necessary.
Build and run each application using the appropriate commands or IDE tools.
Project Structure
The project consists of the following applications:

CustomerSystem: Contains the API endpoints and Kafka flow 1 producer.
CWT: Handles the token generation, decoding, and database operations. It includes Kafka flow 1 consumer.
CONCUR: Implements the APIs to interact with the CONCUR server, decodes the token, and retrieves data from the database. It includes Kafka flow 2 producer.
Each application should have the necessary classes, annotations, logging, and unit tests to ensure proper functionality and maintainability.

Usage
Start the CustomerSystem application.
Make API requests to the CustomerSystem API endpoint to provide user email, first name, and last name.
The CustomerSystem application sends the user email to CWT via Kafka flow 1.
The CWT application retrieves and processes the token based on the provided email.
If the token is valid, it is used. Otherwise, a new token is generated, stored in the database, and returned.
The CWT application sends the customer email, first name, and last name to the CONCUR application via Kafka flow 1.
The CONCUR application decodes the token, checks its expiration, and performs the necessary actions.
If the token has expired, the CONCUR application rejects the request and generates a new token by calling the CWT application.
If the token is valid, the CONCUR application retrieves user details from the database or simulates data using APIs.
The CONCUR application sends the user details back to the CustomerSystem application as the producer of Kafka flow 2.
The CustomerSystem application consumes the user details from Kafka flow 2 and prints them out.
Conclusion
This project demonstrates the integration of three SpringBoot applications using Kafka streams to retrieve customer data. The applications communicate with each other to fetch tokens, decode them, and fetch detailed user information. By following the setup instructions and understanding the project structure, you can successfully run and test the applications.

Note: Make sure to add the required dependencies, handle security measures, and configure the applications according to your environment and specific requirements.