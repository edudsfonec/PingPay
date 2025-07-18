# PingPay API


Welcome to the PingPay API repository\! This project is a robust backend solution designed to manage financial transactions and user accounts, with a strong focus on security, scalability, and ease of documentation.

-----

## 🛠️ Technologies and Tools

  * **Programming Language:** Java
  * **Framework:** Spring Boot
  * **Version Control:** Git & GitHub
  * **Dependency Manager:** Maven

-----


## ⚙️ Implemented Dependencies

To ensure the API's security, authentication, and efficient documentation, these dependencies have been carefully integrated:

### Spring Security

Used to configure **authentication** and **authorization** within the application. Spring Security offers a powerful and highly configurable framework to protect your application from unauthorized access, ensuring that only authenticated users with the correct permissions can access specific API resources.

### JWT (JSON Web Tokens)

Implemented for a **stateless** authentication mechanism. After successful authentication, a JWT is generated and returned to the client. This digitally signed token is used to verify the user's identity in subsequent requests, eliminating the need for server-side sessions and providing greater scalability and performance for the API.

### Swagger (OpenAPI)

Integrated for **interactive documentation** and API **testing**. Swagger automatically generates a web interface that allows developers and API consumers to view all available **endpoints**, their methods, request parameters, and response models. This significantly facilitates understanding and using the API, accelerating the development and integration process.

-----

## 🚀 How to Run the Project

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/edudsfonec/PingPay.git
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd PingPay
    ```
3.  **Build the project with Maven:**
    ```bash
    mvn clean install
    ```
4.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

The API will be available at `http://localhost:8080` (or your configured port).
The Swagger documentation will be accessible at `http://localhost:8080/swagger-ui.html`.
