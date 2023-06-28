# CONCUR

## Description 

CONCUR is a SpringBoot application that interacts with the CONCUR server to manage customer information. It provides a set of APIs to create, delete, update, and retrieve customer details. The application uses a MySQL database for data storage and Kafka for communication with other applications in the TRAVEL - CWT project.


Configure the following properties in the application.properties file:

server.port=8080

logging.level.root=info

spring.datasource.password=pilotFinal
spring.datasource.username=pilotFinal
spring.datasource.url=jdbc:mysql://localhost:3306/ConcurDB

spring.jpa.show-sql=true

spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id="TRAVEL - CWT"
Build and run the application using the appropriate commands or IDE tools.

## APIs

### Create Customer API

Endpoint: localhost:8080/concur/create/customer

Method: POST

Description: Creates a new customer with the provided customer details.

Request Body:

{
"firstName": "John",

"lastName": "Doe",

"email": "john.doe@example.com"
}


### Delete Customer API

Endpoint: localhost:8080/concur/delete/customer
Method: DELETE
Description: Deletes the customer with the specified customer ID.
Request Parameters:
customerId: The ID of the customer to be deleted.
Update Customer API
Endpoint: localhost:8080/concur/update/customer

Method: PUT

Description: Updates the customer details with the provided information.

Request Body:

{
"customerId": 1,
"firstName": "John",
"lastName": "Smith",
"email": "john.smith@example.com"
}


### Get Customer by Email API

Endpoint: localhost:8080/concur/get/customerByEmail
Method: GET
Description: Retrieves the customer details associated with the specified email address.
Request Parameters:
email: The email address of the customer to retrieve.


### Find Customer Info by Token API

Endpoint: localhost:8080/concur/token
Method: GET
Description: Retrieves the customer information based on the provided token.
Request Parameters:
token: The token associated with the customer.
Conclusion
The CONCUR application provides a set of APIs to manage customer information. By following the setup instructions and using the provided APIs, you can create, delete, update, and retrieve customer details. Make sure to configure the application properties and database connection details according to your environment.