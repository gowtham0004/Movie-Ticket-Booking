package com.moviebookingsystem.controller.admin;

import com.moviebookingsystem.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/makeUser")
public class MakeUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String id = req.getParameter("admin-id");
        int adminId = Integer.parseInt(id);

        boolean success = UserDAO.makeUser(adminId);

        String msg = success ? "User role updated to USER" : "Failed to update role";
        req.setAttribute("message", msg);

        req.getRequestDispatcher("/view/admin/makeUser.jsp").forward(req, resp);
    }
}
