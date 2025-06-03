# Marketplace Microservices Architecture Showcase

This project is a comprehensive showcase of a microservices-based marketplace application, developed with a focus on modularity, scalability, and real-world business logic. It demonstrates how to manage shopping orders, products, and users, leveraging asynchronous processing and message queues for robust workflow orchestration.

### Overview

The application allows users to:
- Add, modify, and delete products
- Add, modify, and delete users
- Create shopping orders, which are automatically managed through various processing stages
- Cancel orders (by the user)

### Order Lifecycle
1. **CREATED**: When an order is placed, it is saved in the database with the status `CREATED`.
2. **PROCESSING**: The order transitions to `PROCESSING`, where various business and validation checks are performed asynchronously.
3. **PENDING_PAYMENT**: If all checks pass, the order moves to `PENDING_PAYMENT`, awaiting payment from the frontend. Payments can be made in full or split into 12 installments.
4. **CANCELLED**: The order can be cancelled by the user at any time before payment.
5. **REJECTED**: The order can be rejected due to failed validation or business rules.
6. **PAID**: The order is successfully paid (either in full or via installments).
7. **ERROR**: The order can enter an error state due to processing or payment issues.

All order state transitions and business logic are handled asynchronously using message queues (RabbitMQ), ensuring scalability and decoupling between services.

## Technologies Used

- **Java 21**
- **Spring Boot** (REST APIs, dependency injection, configuration)
- **Spring Data JPA** (data persistence)
- **Hibernate** (ORM)
- **Spring Validation** (Bean Validation, custom validation)
- **MySQL** (primary database)
- **MongoDB** (optional, for additional data storage)
- **RabbitMQ** (asynchronous messaging and event-driven processing)
    - Download RabbitMQ: [https://www.rabbitmq.com/download.html](https://www.rabbitmq.com/download.html)
- **Maven** (build and dependency management)

## Architecture & Structure

- `controller/` — REST endpoints and data validation
- `service/` — Business logic and application rules
- `repository/` — Database access layer
- `model/` — JPA entity definitions
- `dto/` — Data Transfer Objects (request/response models)
- `exception/` — Custom error handling
- `validation/` — Custom validation handling

The project is designed with a clear separation of concerns, following best practices for microservices and clean architecture. Error handling is centralized, and validation is enforced both at the backend and via custom logic.

## Practical Usage

- **Frontend**: Access the user interface at [http://localhost:8080/](http://localhost:8080/)
- **API Documentation**: Swagger UI available at [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
- **Database Console**: H2 in-memory database console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### RabbitMQ Requirement
To use the project, you must install and configure RabbitMQ locally. The application relies on RabbitMQ for asynchronous order processing and inter-service communication.

You can download RabbitMQ from the official website: [https://www.rabbitmq.com/download.html](https://www.rabbitmq.com/download.html)

### Example Requests
In the `.external-service-api` folder, you will find example request bodies for use with the "add-all" endpoints in Swagger. These can be used to quickly populate the system with sample data.

## Theoretical Highlights
- **Asynchronous Processing**: All critical workflows (such as order validation and payment processing) are handled asynchronously via message queues, ensuring high throughput and resilience.
- **Microservices Architecture**: The codebase is modular, with clear boundaries between components, making it easy to extend or integrate with other services.
- **Spring Validation**: Extensive use of Spring’s validation framework ensures data integrity and robust error feedback.
- **Queue-based Messaging**: RabbitMQ is used to decouple services and manage complex workflows without blocking API calls.

## Getting Started
1. Clone the repository
2. Install and start RabbitMQ locally
3. Configure your database (MySQL or use the default H2 for testing)
4. Run the application with Maven or your preferred IDE
5. Access the frontend, Swagger UI, and database console as described above

---

This project is a practical demonstration of building a modern, event-driven, microservices-based marketplace with Spring Boot and related technologies. For any questions or contributions, please refer to the code or open an issue.
