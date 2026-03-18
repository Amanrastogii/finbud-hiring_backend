# 🚀 Finbud Hiring Backend

A scalable **Spring Boot backend system** for managing job applications, candidates, and hiring workflows.

This project simulates a real-world hiring system used by companies, including **admin dashboard APIs, resume handling, and status management**.

---

## 🧠 Project Overview

Finbud Hiring Backend is a RESTful API built using **Java + Spring Boot** that allows:

- 📄 Candidates to submit job applications
- 📂 Resume upload & secure download
- 📊 HR/Admin to view and manage applications
- 🔄 Update candidate status (Shortlisted / Rejected)
- 🛡️ Basic admin authentication (token-based)

---

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL** ✅
- **Lombok**
- **Maven**

---

## 📂 Project Structure


src/
├── controller/ # REST APIs
├── service/ # Business logic
├── repository/ # Database layer
├── entity/ # JPA entities
├── dto/ # Data Transfer Objects
├── config/ # CORS & config classes
└── exception/ # Global exception handling


---

## ⚙️ How to Run the Project

### 1️⃣ Clone Repository

```bash
git clone https://github.com/Amanrastogii/finbud-hiring_backend.git
cd finbud-hiring_backend
2️⃣ Configure Database

Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/finbud_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3️⃣ Run Application
mvn clean install
mvn spring-boot:run
🚀 Server runs at:
http://localhost:8080
📡 API Endpoints
📌 Applications
Method	Endpoint	Description
POST	/api/applications	Create new application
GET	/api/applications	Get all applications
GET	/api/applications/{id}	Get application by ID
PATCH	/api/applications/{id}/status	Update status
GET	/api/applications/{id}/resume	Download resume
🔐 Admin Access (Basic)

Currently implemented:

Token-based authentication (admin-token)

Protected APIs for admin dashboard

👉 Future upgrade:

JWT Authentication

Role-based access (Admin / HR)

📦 Features

✅ Clean layered architecture

✅ DTO-based design

✅ Global exception handling

✅ Resume upload & download system

✅ Admin dashboard API integration

✅ Status management system

✅ PostgreSQL integration

📌 Future Enhancements

🔐 JWT Authentication & Role-based access

☁️ AWS S3 for file storage

📊 Dashboard filters & pagination

📧 Email notifications (interview updates)

🐳 Docker deployment

⚙️ CI/CD pipeline

🧪 Unit & Integration testing

👨‍💻 Author

Aman Rastogi
Backend Developer | Java | Spring Boot | System Design

🔗 GitHub: https://github.com/Amanrastogii

🔗 LinkedIn: https://www.linkedin.com/in/aman-rastogi

⭐ Contribution / Feedback

Feel free to:

Fork the repo

Raise issues

Suggest improvements
