# Finbud Hiring Backend 🚀

Spring Boot backend application for managing the hiring process at Finbud.  
This system allows HR/Admin to manage job applications, candidates, and hiring workflow efficiently.

---

## 🏢 Project Overview

Finbud Hiring Backend is a RESTful API built using **Java + Spring Boot**.  
It provides endpoints to:

- Create job applications
- Manage candidate details
- Validate input data
- Persist records in MySQL database
- Handle exceptions globally
- Map DTOs using ModelMapper

---

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Lombok**
- **ModelMapper**
- **Maven**

---

## 📂 Project Structure

finbud-hiring_backend
│
├── src/main/java/com/financebuddha/finbud
│ ├── config
│ ├── controller
│ ├── dto
│ ├── entity
│ ├── exception
│ ├── repository
│ └── service
│
├── src/main/resources
│ └── application.properties
│
├── pom.xml
└── README.md


---

## ⚙️ How to Run the Project

### 1️⃣ Clone Repository

```bash
git clone https://github.com/Amanrastogii/finbud-hiring_backend.git
cd finbud-hiring_backend

2️⃣ Configure Database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/finbud_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3️⃣ Run Application

Using Maven:

mvn clean install
mvn spring-boot:run


OR run directly from IntelliJ.

Application will start on:

http://localhost:8080

📡 API Endpoints (Example)
Method	Endpoint	Description
POST	/api/applications	Create new application
GET	/api/applications	Get all applications
GET	/api/applications/{id}	Get application by ID
PUT	/api/applications/{id}	Update application
DELETE	/api/applications/{id}	Delete application
✅ Features

Clean layered architecture

DTO pattern implementation

Global exception handling

Input validation

RESTful API design

Production-ready Maven configuration

📌 Future Enhancements

JWT Authentication & Role-based access

Swagger API Documentation

Docker Deployment

CI/CD Integration

Unit & Integration Tests

👨‍💻 Author

Aman Rastogi
Backend Developer | Java | Spring Boot | System Design

GitHub: https://github.com/Amanrastogii

📜 License

This project is developed for learning and internal company usage.


---

# 🚀 After Adding This

Run:

```bash
git add README.md
git commit -m "Updated professional README"
git push
