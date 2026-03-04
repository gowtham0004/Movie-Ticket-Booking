package com.moviebookingsystem.controller;

import com.moviebookingsystem.model.Movie;
import com.moviebookingsystem.model.Seat;
import com.moviebookingsystem.dao.MovieDAO;
import com.moviebookingsystem.dao.SeatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/bookTicket")
public class BookTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = 0;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("user-id".equals(c.getName())) {
                    try { userId = Integer.parseInt(c.getValue()); } catch(NumberFormatException ignored) {}
                    break;
                }
            }
        }
        if (userId == 0) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String movieIdStr = req.getParameter("movie-id");
        if (movieIdStr == null) {
            req.setAttribute("error", "Movie not specified.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        int movieId;
        try {
            movieId = Integer.parseInt(movieIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid movie id.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        Movie movie = MovieDAO.getMovieById(movieId);
        if (movie == null) {
            req.setAttribute("error", "Selected movie not found.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        ArrayList<Seat> availableSeats = SeatDAO.getAvailableSeats(movieId);
        if (availableSeats == null) availableSeats = new ArrayList<>();

        req.setAttribute("movie", movie);
        req.setAttribute("availableSeats", availableSeats);

        req.getRequestDispatcher("/view/seatSelection.jsp").forward(req, resp);
    }

}