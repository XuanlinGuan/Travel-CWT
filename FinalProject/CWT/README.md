# CWT

## Description

CWT is a SpringBoot application that handles the retrieval of customer data and token management for the TRAVEL - CWT system. It communicates with the CONCUR application to fetch detailed user information and payments. The application utilizes Kafka for stream processing and database for data storage.

Setup Instructions
Clone the repository: git clone <repository-url>

Configure the following properties in the application.properties file:

server.port=8083

spring.datasource.url=jdbc:mysql://localhost:3306/cwtDB
spring.datasource.username=cwt
spring.datasource.password=cwt1

spring.jpa.show-sql=false

spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.consumer.bootstrap-servers=localhost:9092

spring.kafka.consumer.group-id="TRAVELCWT"

message.topic.name=topicUserCustomerInfo
spring.kafka.consumer.properties.spring.deserializer.key.delegate.class=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.properties.spring.deserializer.value.delegate.class=org.springframework.kafka.support.serializer.JsonDeserializer

spring.kafka.consumer.properties.spring.json.trusted.packages="*"
Build and run the application using the appropriate commands or IDE tools.

## Functionality

The CWT application provides the following functionality:

Fetching customer data from the CONCUR application using Kafka streams.
Token management for authentication and authorization purposes.
Database storage for tokens and customer information.
Conclusion
The CWT application plays a critical role in the TRAVEL - CWT system by handling customer data retrieval and token management. By following the setup instructions and configuring the application properties accordingly, you can ensure the proper functioning of the application. Make sure to have the necessary database and Kafka configurations in place.