# 🎬 Movie Ticket Booking System

A full-stack Java web application that allows users to browse movies, select seats, book tickets, view booking history, and manage accounts.  
Includes a dedicated admin panel for managing movies and user roles.

This project is built using **Java, JSP, Servlets, MVC architecture, and MySQL**, following clean coding and modular design practices.

---

## 📌 Features

### 👤 User Features
- User Registration & Login
- View available movies
- Detailed movie information
- Seat selection interface
- Book tickets with real-time seat update
- View booking history

### 🛠 Admin Features
- Add new movies
- Remove or update movies
- Promote/demote users (Admin/User controls)
- Refresh movie listings

---

## 🧱 Tech Stack

| Category | Technology |
|---------|------------|
| **Backend** | Java, JSP, Servlets |
| **Frontend** | HTML, CSS |
| **Database** | MySQL |
| **Architecture** | MVC + DAO Layer |
| **Build Tool** | Maven |
| **IDE Used** | IntelliJ IDEA |
| **Server** | Apache Tomcat / SmartTomcat |

---

## 🚀 How to Run the Project

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/Vinu-Java/movie-ticket-booking.git
```

### 2️⃣ Import the Project
- Open IntelliJ IDEA
- Select Import Project
- Choose Maven project
- Wait for dependencies to download

### 3️⃣ Configure Database
- Create a MySQL database:

```
CREATE DATABASE moviebooking;
```

- Update your DB connection inside your DB.java file:
```
String url = "jdbc:mysql://localhost:3306/moviebooking";
String username = "root";
String password = "yourpassword";
```

### 4️⃣ Run the Project
- Select Tomcat/SmartTomcat
- Add src/main/webapp as Deployment
- Run the application
- Open browser:
```
http://localhost:8080/MovieTicketBooking
```

---
