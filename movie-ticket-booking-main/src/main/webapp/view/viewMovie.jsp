<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.moviebookingsystem.model.User" %>
<%@ page import="com.moviebookingsystem.model.Movie" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Ticket Booking System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body {
            margin: 10px 50px;
        }
    </style>
</head>
<body>
    <%
        User user = (User) request.getAttribute("user");
        String role = "USER";
        int userId = 0;
        String displayName = "Guest";
        if (user != null) {
            if (user.getRole() != null) role = user.getRole();
            if (user.getUserId() != 0) userId = user.getUserId();
            if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) displayName = user.getUserName();
        }
        ArrayList<Movie> movies = (ArrayList<Movie>) request.getAttribute("movies");
    %>

    <div>
        <!-- top navigation -->
        <div class="top-nav">
        <%
            if ("ADMIN".equalsIgnoreCase(role)) {
        %>
            <a href="${pageContext.request.contextPath}/view/admin/makeAdmin.jsp"><button class="admin-btn">Make Admin</button></a>
            <a href="${pageContext.request.contextPath}/view/admin/newMovie.jsp"><button class="admin-btn">New Movie</button></a>
        <%
            } else {
        %>
            <a href="${pageContext.request.contextPath}/history"><button>Booking History</button></a>
        <%
            }
        %>
            <a href="${pageContext.request.contextPath}/login"><button>Logout</button></a>
        </div>

        <h1>Welcome
            <span class="<%= ("ADMIN".equalsIgnoreCase(role) ? "admin-name" : "user-name") %>">
                <%= displayName %> <span style="font-size: 26px;">(<%= userId %>)<span>
            </span>
        </h1>

        <!-- movies -->
        <div class="movies-container">
        <%
            if (movies != null && !movies.isEmpty()) {
                for (Movie movie : movies) {
                    if (movie == null) continue;
                    Integer movieId = movie.getMovieId();
                    String poster = movie.getPoster() == null ? "/assets/posters/default.jpg" : movie.getPoster();
                    String movieName = movie.getMovieName() == null ? "Untitled" : movie.getMovieName();
                    int available = movie.getAvailableSeats();
                    int price = movie.getTicketPrice();
        %>

            <div class="movie-card">
                <img src="<%= request.getContextPath() + poster %>">
                <h3><%= movieName %></h3>
                <p class="availability">₹ <%= price %> | Available Seats: <%= available %></p>

        <%
                if ("ADMIN".equalsIgnoreCase(role)) {
        %>
                <div style="display:flex; gap:10px;">
                    <a href="${pageContext.request.contextPath}/admin/refresh?movie-id=<%= movieId %>"
                        onclick="return confirm('Are you sure you want to REFRESH this movie?');">
                        <button class="refresh-btn">Refresh</button>
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/remove?movie-id=<%= movieId %>"
                       onclick="return confirm('Are you sure you want to REMOVE this movie?');">
                        <button class="remove-btn">Remove</button>
                    </a>
                </div>
        <%
                } else {
                    if (available > 0) {
        %>
                <a href="${pageContext.request.contextPath}/bookTicket?movie-id=<%= movieId %>"><button>Book Now</button></a>
        <%
                    } else {
        %>
                <button disabled>Booked Out</button>
        <%
                    }
                }
        %>
            </div> <!-- movie-card -->

        <%
                } // end for
            } else {
        %>
            <p>No movies available.</p>
        <%
            }
        %>
        </div> <!-- movies-container -->
    </div>
</body>
</html>
