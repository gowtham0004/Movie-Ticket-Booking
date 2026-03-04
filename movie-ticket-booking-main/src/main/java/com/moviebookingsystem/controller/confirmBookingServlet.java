package com.moviebookingsystem.controller;

import com.moviebookingsystem.model.Booking;
import com.moviebookingsystem.model.Movie;
import com.moviebookingsystem.dao.BookingDAO;
import com.moviebookingsystem.dao.MovieDAO;
import com.moviebookingsystem.dao.SeatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebServlet("/confirmBooking")
public class confirmBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] seats = req.getParameterValues("seat_no[]");
        int movieId = Integer.parseInt(req.getParameter("movie-id"));

        if (seats == null || seats.length == 0) {
            req.setAttribute("error", "Please select at least one seat.");
            req.getRequestDispatcher("/bookTicket").forward(req, resp);
            return;
        }

        List<String> selectedSeats = Arrays.asList(seats);

        try {
            if (!SeatDAO.areSeatsAvailable(movieId, selectedSeats)) {
                req.setAttribute("error", "Some seats were already booked. Please choose again.");
                req.getRequestDispatcher("/view/seatSelection.jsp").forward(req, resp);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int userId = 0;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-id".equals(cookie.getName())) {
                    userId = Integer.parseInt(cookie.getValue());
                }
            }
        }
        if (userId == 0) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        int booked;
        try {
            booked = SeatDAO.bookSeats(movieId, selectedSeats, userId);
            if (booked > 0)
                MovieDAO.reduceAvailability(movieId, booked);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (booked > 0) {
            String seatStr = String.join(", ", seats);
            Movie movie = MovieDAO.getMovieById(movieId);
            req.setAttribute("movieName", movie.getMovieName());
            req.setAttribute("seats", seatStr);
            req.setAttribute("totalAmount", seats.length * movie.getTicketPrice());
            if (!BookingDAO.addBooking(new Booking(userId, movie.getMovieName(), seatStr, Instant.now())))
                resp.getWriter().println("Due to some reasons, your 'history' is not stored but your tickets are confirmed");
            req.getRequestDispatcher("/view/success.jsp").forward(req,resp);
        } else {
            req.setAttribute("error", "Booking failed due to conflict.");
            req.getRequestDispatcher("/view/seatSelection.jsp").forward(req, resp);
        }

    }
}
