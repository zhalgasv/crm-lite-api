# CRM Lite API

CRM Lite API is a Spring Boot backend application for managing customers in a lightweight CRM system.

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Flyway
- Docker Compose
- JUnit 5
- MockMvc
- Maven

## Features

- Create, read, update, and delete customers
- Customer statuses: `NEW`, `ACTIVE`, `INACTIVE`
- Customer ownership through `ownerId`
- Request validation for required fields and email format
- Global exception handling
- PostgreSQL schema migrations with Flyway
- Controller tests with MockMvc
- Repository tests for persistence behavior
- Maven Wrapper for reproducible local runs

## Architecture

The application uses a compact layered Spring Boot structure:

- `customer`: customer entity, REST controller, service, mapper, repository, DTOs, and status enum
- `user`: user entity, role enum, and repository used as the customer owner
- `exception`: centralized handling for missing resources and validation failures
- `db/migration`: Flyway SQL migrations for users and customers tables

The controller layer accepts request DTOs, the service layer contains customer business logic, and repositories isolate database access through Spring Data JPA.

## How to Run

Start PostgreSQL:

```bash
docker compose up -d
```

Run the application:

```bash
./mvnw spring-boot:run
```

Run tests:

```bash
./mvnw test
```

## API Examples

### Create Customer

```bash
curl -i -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Adil Zhalgas",
    "email": "adil@example.com",
    "phone": "+77001234567",
    "company": "Acme",
    "status": "NEW",
    "ownerId": 1
  }'
```

### Get All Customers

```bash
curl -i http://localhost:8080/api/customers
```

### Get Customer By Id

```bash
curl -i http://localhost:8080/api/customers/1
```

### Update Customer

```bash
curl -i -X PUT http://localhost:8080/api/customers/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Adil Zhalgas",
    "email": "adil@example.com",
    "phone": "+77001234567",
    "company": "Acme",
    "status": "ACTIVE",
    "ownerId": 1
  }'
```

### Delete Customer

```bash
curl -i -X DELETE http://localhost:8080/api/customers/1
```

## API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/customers` | Create customer |
| GET | `/api/customers` | Get all customers |
| GET | `/api/customers/{id}` | Get customer by id |
| PUT | `/api/customers/{id}` | Update customer |
| DELETE | `/api/customers/{id}` | Delete customer |

## Tests

Current test coverage includes:

- `CustomerControllerTest`: REST endpoint behavior and validation
- `CustomerRepositoryTest`: customer persistence queries
- `UserRepositoryTest`: user persistence behavior
- `CrmLiteApiApplicationTests`: Spring context smoke test

Run:

```bash
./mvnw test
```
