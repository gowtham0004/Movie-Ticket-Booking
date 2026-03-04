package com.moviebookingsystem.controller;

import com.moviebookingsystem.model.Booking;
import com.moviebookingsystem.dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = 0;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("user-id".equals(c.getName())) {
                    userId = Integer.parseInt(c.getValue());
                    break;
                }
            }
        }
        if (userId == 0) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        ArrayList<Booking> bookings = BookingDAO.getBookingsByUser(userId);
        req.setAttribute("bookings", bookings);
        req.getRequestDispatcher("/view/history.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
