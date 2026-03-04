package com.moviebookingsystem.controller;

import com.moviebookingsystem.model.User;
import com.moviebookingsystem.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("email") || c.getName().equals("username") || c.getName().equals("user-id")) {
                    c.setMaxAge(0);
                    c.setPath("/");
                    resp.addCookie(c);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");

        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("email")) {
                    System.out.println(c.getValue());
                    req.getRequestDispatcher("/viewMovie").forward(req,resp);
                    return;
                }
            }
        }

        User user = UserDAO.validation(email, password);
        if (user != null) {
            Cookie uid = new Cookie("email", email);
            String encodedUser = URLEncoder.encode(user.getUserName(), "UTF-8");
            Cookie uname = new Cookie("username", encodedUser);
            Cookie userId = new Cookie("user-id", ""+user.getUserId());

            uid.setMaxAge(12 * 60 * 60); uname.setMaxAge(12 * 60 * 60); userId.setMaxAge(12 * 60 * 60);
            uid.setPath("/"); uname.setPath("/"); userId.setPath("/");
            resp.addCookie(uid); resp.addCookie(uname); resp.addCookie(userId);
            req.getRequestDispatcher("/viewMovie").forward(req,resp);
        }
        else {
            req.setAttribute("message", "Invalid credential");
            try {
                req.getRequestDispatcher("/index.jsp").include(req,resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
