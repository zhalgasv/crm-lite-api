# CRM Lite API

CRM Lite API is a simple backend application for managing customers in a CRM system.

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven
- JUnit 5
- MockMvc
- Docker Compose

## Features

- Create customer
- Get all customers
- Get customer by id
- Update customer
- Delete customer
- Validation
- Global exception handling
- Repository tests
- Controller tests

## Run PostgreSQL

```bash
docker compose up -d
```

## Run Application

```bash
./mvnw spring-boot:run
```

## Run Tests

```bash
./mvnw test
```

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/customers` | Create customer |
| GET | `/api/customers` | Get all customers |
| GET | `/api/customers/{id}` | Get customer by id |
| PUT | `/api/customers/{id}` | Update customer |
| DELETE | `/api/customers/{id}` | Delete customer |
