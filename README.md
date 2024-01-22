# Spring Security Rest API Project

This repository contains a sample Spring Security project, implementing a REST API with JWT (JSON Web Token) authentication.

## Technologies Used
- **Spring Security**: Ensures secure authentication and authorization.
- **Spring Data JPA**: Provides data access using the Java Persistence API.
- **JWT (JSON Web Token)**: Token-based authentication for secure communication.
- **Spring Boot**: Simplifies the development and deployment of Spring applications.
- **Hibernate**: ORM (Object-Relational Mapping) framework for database interaction.

## Project Structure
- **SecurityApplication.java**: Main class to run the Spring Boot application.
- **SecurityConfig.java**: Configuration class for Spring Security, setting up authentication, authorization, and JWT filter.
- **JWTFilter.java**: Custom filter for processing JWT authentication.
- **AuthController.java**: REST controller handling user authentication and registration.
- **PeopleController.java**: REST controller managing user-related operations, such as fetching all users and changing roles.
- **JWTUtil.java**: Utility class for JWT token generation and validation.
- **application.properties**: Configuration properties, including data source, Hibernate, and JWT values.

## Configuration Properties
Ensure to customize the following properties in the `application.properties` file based on your environment:

### Data source configuration
```properties
# Data source
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/security_database
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
```
### Hibernate configuration
```properties
# Configuration Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.show_sql=true
```
### JWT configuration
```properties
# JWT Values
jwt_secret=your_secret_token
jwt_lifetime=60
```
## Build and Run
To build and run the project, execute the following command:
```bash
./mvnw spring-boot:run
```
## API Endpoints
- User Registration: POST /api/auth/registration 
- User Login: POST /api/auth/login
- Fetch All Users (Admin Only): GET /api/users
- Fetch Logged-In User Details: GET /api/user
- Change User Role (Admin Only): PATCH /api/change_role

## Note
Make sure to replace placeholders like `your_postgres_username`, `your_postgres_password`, and `your_secret_token` with your actual values.

## Contributors

- [avexcoss](https://github.com/avecoss)