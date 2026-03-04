package com.moviebookingsystem.controller.admin;

import com.moviebookingsystem.dao.MovieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/newMovie")
@MultipartConfig
public class NewMovieServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("movie-name");
        int price = Integer.parseInt(req.getParameter("ticket-price"));
        int seats = Integer.parseInt(req.getParameter("available-seats"));

        Part posterPart = req.getPart("poster");

        String posterFileName = null;

        if (posterPart != null && posterPart.getSize() > 0) {
            posterFileName = posterPart.getSubmittedFileName();

            // Saving location inside webapp/assets/posters/
            String uploadPath = getServletContext().getRealPath("/assets/posters/");
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) uploadDir.mkdirs();

            // save file
            posterPart.write(uploadPath + File.separator + posterFileName);
        }

        // store only file path (not full physical path)
        String posterPath = posterFileName != null
                ? "/assets/posters/" + posterFileName
                : "/assets/posters/default.jpg";

        boolean success = MovieDAO.addMovieWithSeats(name, price, posterPath, seats);

        req.setAttribute("message", success ? "Movie added successfully!" : "Failed to add movie.");
        req.getRequestDispatcher("/view/admin/newMovie.jsp").forward(req, resp);
    }
}
