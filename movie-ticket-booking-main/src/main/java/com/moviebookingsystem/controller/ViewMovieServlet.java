package com.moviebookingsystem.controller;

import com.moviebookingsystem.model.Movie;
import com.moviebookingsystem.model.User;
import com.moviebookingsystem.dao.MovieDAO;
import com.moviebookingsystem.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/viewMovie")
public class ViewMovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

        ArrayList<Movie> movies = MovieDAO.getMovies();
        User user = UserDAO.getUserById(userId);
        req.setAttribute("user", user);
        req.setAttribute("movies", movies);
        req.getRequestDispatcher("/view/viewMovie.jsp").forward(req, resp);
    }
}
