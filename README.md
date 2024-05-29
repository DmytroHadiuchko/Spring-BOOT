# 📚Book Shop📚
Welcome to the Online Bookstore API! This API provides essential 
functionalities for managing books, user authentication,
and order processing, forming the backbone of a scalable and
efficient online bookstore. Inspired by the need for a 
robust solution
in the digital book retail space, this project showcases
a fully functional online
bookstore built with Spring Boot. It includes features
like user registration, book browsing, shopping cart
management, and order processing, offering a solid foundation
for real-world applications.

## 🛠️Technologies and Tools🛠️
-**Spring Boot**: Core framework for building application.

-**Spring Security**: Ensures robust authentication and authorization mechanisms for the application.

-**Spring Data JPA**: Simplifies database interactions through JPA.

-**Swagger**: Offers interactive API documentation and testing capabilities.

-**JUnit**: Enables efficient unit testing.

-**Mockito**: Aids in isolating code under test by mocking its dependencies, facilitating effective unit testing.

-**MYSQL Database**: For the relational database.

-**Liquibase**: Manages database migrations.

## Features and Functionality
### Authentication
- **Register user**: `POST /api/auth/register`
- **Login user**: `POST /api/auth/login`
### Books
- **User endpoints**
   - **List all books**: `GET /api/books`
   - **Get book details**: `GET /api/books/{id}`
   - **Search book by criteria**: `GET /api/books/search?author= &title=`
- **Admin endpoint**
   - **Add new book**: `POST /api/books`
   - **Update book**: `PUT /api/books/{id}`
   - **Delete book**: `DELETE /api/books/{id}`
### Categories
- **User endpoints**
   - **List all categories**: `GET /api/categories`
   - **Get category by id**: `GET /api/categories/{id}`
   - **Get all books by category**: `GET /api/categories/{id}/books`
- **Admin endpoint**
   - **Add new category**: `POST /api/categories` 
   - **Update category**: `PUT /api/categories/{id}`
   - **Delete category**: `DELETE /api/categories/{id}`
### Shopping Cart
- **User endpoints**
   - **Add book to cart**: `POST /api/cart`
   - **Update book quantity in cart**: `PUT /api//cart-items/{cartItemId}`
   - **Get user's cart**: `GET /api/cart`
   - **Remove item from cart by id**: `DELETE /api//cart-items/{cartItemId}`
### Orders
- **User endpoints**
   - **Get order history**: `GET /api/orders`
   - **Place order**: `POST /api/orders`
   - **Get all items from order**: `GET /api/orders/{id}/items`
   - **Get specific item from order**: `GET /api/orders/{orderId}/items/{itemId}`
- **Admin endpoint** 
  - **Update order status**: `PATCH /api/orders/{id}`

## Demo video
[📹 Watch video](https://www.loom.com/share/2e6af377787e40c98345b227dbaba7c2?sid=8e6ac58f-8de2-4e0c-b83d-f6133f6fc444)

### Installation
1. **Clone github repository**
   ```bash
   git clone https://github.com/DmytroHadiuchko/Spring-BOOT.git
   ```
   Open project in Intellij IDEA


2. **Set environment variables:**

Create a `.env` file in your root directory, and add variables:

```env
MYSQLDB_DATABASE=your_db_name
MYSQLDB_USER=_your_user_name
MYSQLDB_ROOT_PASSWORD=your_password
MYSQLDB_LOCAL_PORT=3307
MYSQLDB_PORT=3306
DEBUG_PORT=5005

SPRING_LOCAL_PORT=8081
SPRING_DOCKER_PORT=8080
```

3. **Create `docker-compose.yaml` in your root directory**

```yaml
version: "3.8"

services:
  mysqldb:
    image: mysql:latest
    restart: always
    env_file: ./.env
    environment:
      MYSQL_DATABASE: ${MYSQLDB_DATABASE}
      MYSQL_ROOT_PASSWORD: ${MYSQLDB_ROOT_PASSWORD}
    ports:
      - "${MYSQLDB_LOCAL_PORT}:${MYSQLDB_PORT}"
    healthcheck:
      test: ["CMD-SHELL", "mysqladmin ping -h 127.0.0.1 -u${MYSQLDB_USER} -p${MYSQLDB_ROOT_PASSWORD}"]
      interval: 30s
      timeout: 10s
      retries: 3

  app:
    depends_on:
      mysqldb:
        condition: service_healthy
    restart: on-failure
    image: book-shop
    build: .
    env_file: ./.env
    ports:
      - ${SPRING_LOCAL_PORT}:${SPRING_DOCKER_PORT}
      - ${DEBUG_PORT}:${DEBUG_PORT}
    environment:
      SPRING_APPLICATION_JSON: '{
        "spring.datasource.url": "jdbc:mysql://mysqldb:${MYSQLDB_PORT}/${MYSQLDB_DATABASE}",
        "spring.datasource.username":"${MYSQLDB_USER}",
        "spring.datasource.password":"${MYSQLDB_ROOT_PASSWORD}",
        "spring.jpa.properties.hibernate.dialect":"org.hibernate.dialect.MySQLDialect"
      }'
      JAVA_TOOL_OPTIONS: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
```

4. **Build the project**
```bash
./mvn clean package
```
5. **Build the images**
``` bash 
docker-compose build
```
6. **Run the Docker compose**
```bash
docker-compose up
```
## Challenges and Solution
While developing this project, I encountered several challenges:

Implementing secure authentication and authorization mechanisms.

Ensuring seamless integration between Spring Boot and Spring Security.

Designing efficient database models for book categorization and user management.

Managing database schema changes and versioning with Liquibase.
