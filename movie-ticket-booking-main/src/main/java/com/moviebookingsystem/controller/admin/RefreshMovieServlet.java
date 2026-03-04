package com.moviebookingsystem.controller.admin;

import com.moviebookingsystem.dao.MovieDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/admin/refresh")
public class RefreshMovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("movie-id");
        int movieId;

        try {
            movieId = Integer.parseInt(id);
        } catch (Exception e) {
            // redirect with error if id is invalid
            resp.sendRedirect(req.getContextPath() + "/viewMovie?error=invalid-id");
            return;
        }

        boolean success = MovieDAO.refreshSeats(movieId);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/viewMovie?msg=seats-refreshed");
        } else {
            resp.sendRedirect(req.getContextPath() + "/viewMovie?error=refresh-failed");
        }
    }

}
