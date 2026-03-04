package com.moviebookingsystem.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASS");

            // Ensure variables exist
            if (url == null || user == null || pass == null) {
                throw new RuntimeException("❌ Environment variables DB_URL, DB_USER, DB_PASS are missing.");
//                url = "jdbc:mysql://localhost:3306/TicketBooking";
//                user = "root";
//                pass = "Vinu@2003";
            }

            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            throw new RuntimeException("❌ Database connection failed: " + e.getMessage(), e);
        }
    }
}

