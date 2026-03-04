package com.moviebookingsystem.controller.admin;

import com.moviebookingsystem.dao.MovieDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/admin/remove")
public class RemoveMovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("movie-id");
        int movieId;

        try {
            movieId = Integer.parseInt(id);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/viewMovie?error=invalid-id");
            return;
        }

        // Get poster path from DB
        String posterWebPath = MovieDAO.getPosterPath(movieId);

        // Convert web path -> actual filesystem path
        String realPosterPath = null;
        if (posterWebPath != null) {
            realPosterPath = req.getServletContext().getRealPath(posterWebPath);
//            System.out.println("Real poster disk path = " + realPosterPath);
        }

        boolean success = MovieDAO.deleteMovie(movieId, realPosterPath);

        resp.sendRedirect(req.getContextPath() +
                (success ? "/viewMovie?msg=deleted" : "/viewMovie?error=delete-failed"));
    }

}
