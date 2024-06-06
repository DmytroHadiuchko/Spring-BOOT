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
### Backend:
- **Java**: The primary programming language used for the backend services.
- **Spring Boot**: A powerful framework for building production-ready applications quickly,
  providing features such as dependency injection, web frameworks, data access, and security.
- **Spring Security**: Used to handle authentication and authorization.
- **Spring Data JPA**: Simplifies data access and management using Java Persistence Api.
- **Liquibase**: An open-source library for tracking, managing, and applying database schema changes.
### Database:
- **MySql**: A widely-used open-source relational database system, chosen for its reliability,
  ease of use, and support for complex queries and transactions.
### Testing:
- **JUnit**: A widely used testing framework for Java applications.
- **Mockito**: A mocking framework for unit test in Java.
- **Spring Boot Test**: Provide utilities for testing Spring Boot applications.
### CI/CD:
- **GitHub Actions**: Provides CI capabilities to automate building and testing the application.
### Containerization:
- **Docker**: Used to create, deploy, and run application in containers.
- **Docker Compose**: Used for defining and running multi-container Docker application.
### API Documentation:
- **Swagger/OpenApi**: Tools for generating and visualizing API documentation, making it easier to understand and use the API endpoints.
### Code Quality:
- **Checkstyle**: A development tool to help ensure that Java code adheres a coding standard.
- **Maven**: A build automation tool used primary for Java projects. It simplifies the build process and dependency management.
### Development Environment:
- **IntelliJIdea**: An integrated development environment for Java development.
- **Postman**: A tool for testing APIs by sending request and receiving response.
### Environment managing:
- **dotenv (.env)**: A module that loads environment variables from a '.env' file into the application, ensuring sensitive information is kept secure and not pushed to version control.
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
3. **Build the project**
```bash
./mvn clean package
```
4. **Run the Application**
  - **Using Docker Compose**
   ```bash
   docker-compose up --build
   ```
## Features and Functionality
## Demo video
[📹 Watch video](https://www.loom.com/share/2e6af377787e40c98345b227dbaba7c2?sid=8e6ac58f-8de2-4e0c-b83d-f6133f6fc444)

[Postman Collection](https://interstellar-resonance-477340.postman.co/workspace/Team-Workspace~862b3ede-7197-4868-867f-7f79a2112ce2/collection/18673184-b4f2570c-38b0-47bc-9758-b506b1c178a9?action=share&creator=18673184)
### Authentication
- **Register user**: Endpoint for registering a new user.
   ```bash
   POST /api/auth/register
   ```
- **Login user**: Endpoint for logging in a user and obtaining an authentication token.
   ```bash
   POST /api/auth/login
   ```
  **User Roles**
  - **User**: A regular user who can browse books, add items to the shopping cart, place orders, and view their order history.
  - **Admin**: An administrator who has additional privileges such as adding, updating, and deleting books and categories.
  
  **Sample User Credentials**
  - User Role Credentials:
        
    Email: `user@gmail.com`
    
    Password: `user`
  - Admin Role Credentials:
  
    Email: `admin@gmail.com`
    
    Password: `admin`
### Books
- **User endpoints**
    - **List all books**: Retrieves a list of all available books in the bookstore.
    `
    GET /api/books
    `
    - **Get book details**: Retrieves detailed information about a specific book
  `GET /api/books/{id}`
    - **Search book by criteria**: Searches for books based on specified criteria such as author or title.
  `GET /api/books/search?author= &title=`
- **Admin endpoint**
    - **Add new book**: Allows administrators to add a new book to the bookstore inventory.
  `POST /api/books`
    - **Update book**: Allows administrators to update the details of an existing book.
  `PUT /api/books/{id}`
    - **Delete book**: Allows administrators to delete a book from the bookstore inventory.
  `DELETE /api/books/{id}`
### Categories
- **User endpoints**
    - **List all categories**: Retrieves a list of all available categories in the bookstore.
  `GET /api/categories`
    - **Get category by id**: Retrieves detailed information about a specific category.
  `GET /api/categories/{id}`
    - **Get all books by category**: Retrieves a list of all books belonging to a specific category.
  `GET /api/categories/{id}/books`
- **Admin endpoint**
    - **Add new category**: Allows administrators to add a new category to the bookstore.
  `POST /api/categories`
    - **Update category**: Allows administrators to update the details of an existing category.
  `PUT /api/categories/{id}`
    - **Delete category**: Allows administrators to delete a category from the bookstore.
  `DELETE /api/categories/{id}`
### Shopping Cart
- **User endpoints**
    - **Add book to cart**: Adds a book to the user's shopping cart.
  `POST /api/cart`
    - **Update book quantity in cart**: Updates the quantity of a book in the user's shopping cart.
  `PUT /api//cart-items/{cartItemId}`
    - **Get user's cart**: Retrieves the user's current shopping cart.
  `GET /api/cart`
    - **Remove item from cart by id**: Removes a specific item from the user's shopping cart.
  `DELETE /api//cart-items/{cartItemId}`
### Orders
- **User endpoints**
    - **Get order history**: Retrieves the user's order history.
  `GET /api/orders`
    - **Place order**:  Places a new order.
  `POST /api/orders`
    - **Get all items from order**: Retrieves all the items included in a specific order.
  `GET /api/orders/{id}/items`
    - **Get specific item from order**: Retrieves details of a specific item from a particular order.
  `GET /api/orders/{orderId}/items/{itemId}`
- **Admin endpoint** Allows administrators to update the status of an order.
    - **Update order status**: 
  `PATCH /api/orders/{id}`

## Challenges and Solution
While developing this project, I encountered several challenges:
- Implementing secure authentication and authorization mechanisms.
- Ensuring seamless integration between Spring Boot and Spring Security.
- Designing efficient database models for book categorization and user management.
- Managing database schema changes and versioning with Liquibase.
## Author:
 [Hadiuchko Dmytro](https://github.com/DmytroHadiuchko)