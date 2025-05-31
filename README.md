# User Service - Spring Boot Microservice

This project implements the **User Service** microservice, part of a larger microservices architecture for a marketplace application.  
The service handles user registration, update, and retrieval, with a strong focus on data validation and separation of concerns.

## Key Features

- Dedicated REST APIs for user management (create, update, search)
- Backend data validation using Spring Validation (`@Valid`, Bean Validation annotations)
- Data persistence via Spring Data JPA with MySQL
- Modular architecture with separated controller, service, repository, and model layers
- Centralized and clean error handling
- Designed for easy integration into a broader microservices ecosystem

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Validation (Bean Validation)
- MySQL
- MongoDB
- Maven

## Project Structure

- `controller/` — REST endpoints and data validation
- `service/` — Business logic and application rules
- `repository/` — Database access layer
- `model/` — JPA entity definitions
- `dto/` — Data Transfer Objects (request/response models)
- `exception/` — Custom error handling  
- `validatin/` — Custom validation handling  
