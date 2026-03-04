package com.moviebookingsystem.controller.admin;

import com.moviebookingsystem.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/makeAdmin")
public class MakeAdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("user-id");
        int userId = Integer.parseInt(id);

        boolean success = UserDAO.makeAdmin(userId);

        String msg = success ? "User role updated to ADMIN" : "Failed to update role";
        req.setAttribute("message", msg);

        req.getRequestDispatcher("/view/admin/makeAdmin.jsp").forward(req, resp);
    }
}

