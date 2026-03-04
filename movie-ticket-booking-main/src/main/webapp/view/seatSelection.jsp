<%@ page import="java.util.ArrayList" %>
<%@ page import="com.moviebookingsystem.model.Movie" %>
<%@ page import="com.moviebookingsystem.model.Seat" %>
<!DOCTYPE html>
<html>
<head>
    <title>Select Seats</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

</head>
<body>
<%
    if(request.getAttribute("error") != null) {
%>
    <p style="color:red;"><%= request.getAttribute("error") %></p>
<%
    }
%>

<%
    Movie movie = (Movie) request.getAttribute("movie");
    ArrayList<Seat> seats = (ArrayList<Seat>) request.getAttribute("availableSeats");
%>
<div class="seat-center-wrapper">
    <h2>
        Book Tickets for
        <span class="colorful-name"><%= movie.getMovieName() %></span>
    </h2>

    <form action="${pageContext.request.contextPath}/confirmBooking" method="POST">
        <input type="hidden" name="movie-id" value="<%= movie.getMovieId() %>">

        <div class="seat-grid">
        <%
            for (Seat s : seats) {

                if (s.isBooked()) {
        %>
                <label class="selected-seat">
                    <span><%= s.getSeatNo() %></span>
                </label>
        <%
                } else {
        %>
                <label class="seat">
                    <input type="checkbox" name="seat_no[]" value="<%= s.getSeatNo() %>">
                    <span><%= s.getSeatNo() %></span>
                </label>
        <%
                }
            }
        %>
        </div>

        <br>
        <button type="submit">Confirm Booking</button>
    </form>
</div>
</body>
</html>
