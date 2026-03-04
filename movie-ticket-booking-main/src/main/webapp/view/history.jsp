<%@ page import="java.util.ArrayList" %>
<%@ page import="com.moviebookingsystem.model.Booking" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.ZonedDateTime" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Booking History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

</head>

<body class="history">

    <h2>Your Booking History</h2>
    <%
        ArrayList<Booking> bookings = (ArrayList<Booking>) request.getAttribute("bookings");
        if (bookings == null || bookings.isEmpty()) {
    %>
        <p class="no-records">No booking history found.</p>
    <%
        } else {
    %>

    <table>
        <tr>
            <th>Booking ID</th>
            <th>Movie Name</th>
            <th>Seat No</th>
            <th>Booked On</th>
        </tr>
        <%
            for (Booking b : bookings) {
        %>
        <tr>
            <td><%= b.getBookingId() %></td>
            <td><%= b.getMovieName() %></td>
            <td><%= b.getSeatNo() %></td>
            <td>
                <%
                    if (b.getBookedOn() != null) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
                        String formatted = b.getBookedOn()
                                .atZone(ZoneId.of("Asia/Kolkata"))
                                .format(dtf);
                        out.print(formatted);
                    } else {
                        out.print("-");
                    }
                %>
            </td>
        </tr>
        <%
            }
        %>

    </table>

    <%
        }
    %>
    <br>
    <a href="${pageContext.request.contextPath}/viewMovie"><button>Back to Home</button></a>

</body>
</html>
