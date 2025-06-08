# Electronic Store API

## 1. Overview

Altech Electronic Store API contains following components:

| Component  | URL                                         | Description                  | Language                                                          |
| ---------- | ------------------------------------------- | ---------------------------- | ----------------------------------------------------------------- |
| API        | http://localhost:8080                       | Altech Electronic Store API. | Java, Spring, JPA, Flyway, Lombok, Log4j2, Swagger, JUnit, Mokito |
| Database   | http://localhost:8080/h2-console/           | Electronic Store Database.   | H2                                                                |
| Swagger UI | http://localhost:8080/swagger-ui/index.html | API documentation.           | HTML                                                              |

## 2. Directory structure

```plaintext
   project-root/
   ├── gradle                                           # Gradle wrapper, manage library versions
   └── src/
      ├── main/java/com/altech/electronic/store/        # Spring Boot application source files
      └── main/resources/
         ├── db/migration/                              # Flyway migration scripts
         ├── postman                                    # Postman collection
         └── application.yml                            # Spring Boot application configuration
         └── log4j2.xml                                 # Log4j2 configuration
   ├── build.gradle                                     # Gradle build script
   ├── Dockerfile                                       # Dockerfile
   ├── docker-compose.yml                               # Docker Compose configuration
   └── README.md                                        # Project documentation
```

## 3. Installation

### 3.1. Prerequisites

- JDK >= 21
- Docker
- Gradle >= 7.5

### 3.2. Build

#### 3.2.1. Start project

```shell
  docker compose up --build -d
```

#### 3.2.2. Stop project

```shell
  docker compose down
```

#### 3.2.3. Default credentials

- Admin user: `admin@store.com / Password123!`
- Customer user: `customer@store.com / Password123!`

## 4. API Endpoint

Once the service is up, the following endpoints will be available:

- `Private` endpoints can only be accessed with a `Bearer access token`.
- A user with the role of `ADMIN` or `CUSTOMER` can access `Admin` and `Customer` endpoints.
- A user with the role of `CUSTOMER` can only access `Customer` endpoints.

### Authentication

| Endpoints             | Method | Type   | Description               |
| --------------------- | ------ | ------ | ------------------------- |
| /api/v1/user/register | POST   | Public | Register a new user       |
| /api/v1/user/login    | POST   | Public | Login to get access token |
| /api/v1/user/me       | GET    | Public | Get current user          |

### Admin

| Endpoints                                   | Method | Type    | Description                                  |
| ------------------------------------------- | ------ | ------- | -------------------------------------------- |
| /api/v1/admin/products                      | GET    | Private | Return a list of all products in the store   |
| /api/v1/admin/products                      | POST   | Private | Create a new product in the store            |
| /api/v1/admin/products/{id}                 | GET    | Private | Return the details of a product in the store |
| /api/v1/admin/products/{id}                 | DELETE | Private | Remove a product from the store              |
| /api/v1/admin/product-discounts             | GET    | Private | Return a list of product discounts           |
| /api/v1/admin/product-discounts/{productId} | GET    | Private | Return a product discount                    |
| /api/v1/admin/product-discounts             | POST   | Private | Add a discount to a product                  |
| /api/v1/admin/product-discounts/{id}        | DELETE | Private | Delete product discount                      |

### Customer

| Endpoints                           | Method | Type    | Description                     |
| ----------------------------------- | ------ | ------- | ------------------------------- |
| /api/v1/customer/baskets/addItem    | POST   | Private | Add an item to the basket       |
| /api/v1/customer/baskets/removeItem | POST   | Private | Remove an item from the basket  |
| /api/v1/customer/baskets            | GET    | Private | Get the basket of current user  |
| /api/v1/customer/baskets            | DELETE | Private | Clear all items from the basket |
