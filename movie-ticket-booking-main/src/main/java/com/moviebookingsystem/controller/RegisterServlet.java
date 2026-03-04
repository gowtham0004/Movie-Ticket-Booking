package com.moviebookingsystem.controller;

import com.moviebookingsystem.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");

        String name = req.getParameter("username").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            res.getWriter().println("All fields are required!");
            req.getRequestDispatcher("/view/register.jsp").include(req, res);
            return;
        }

        if (UserDAO.addUser(name, email, password)) {
            res.getWriter().println("Account created");
            req.getRequestDispatcher("/index.jsp").include(req, res);
        }
        else {
            res.getWriter().println("Registration failed. Try again.");
            req.getRequestDispatcher("/view/register.jsp").include(req, res);
        }
    }
}
