<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Movie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body {
            margin: 10px 50px;
        }
    </style>
</head>
<body>

    <% String msg = (String) request.getAttribute("message"); %>
    <% if (msg != null) { %>
        <p style="color: #4A8FE8; font-weight: bold;"><%= msg %></p>
    <% } %>

    <div class="top-nav">
        <a href="${pageContext.request.contextPath}/viewMovie"><button class="admin-btn">Home</button></a>
    </div>

    <div class="movie-form-box">
        <h2>Add New Movie</h2>

        <form class="movie-form" action="${pageContext.request.contextPath}/newMovie"
              method="post" enctype="multipart/form-data">

            <label>Movie Name:</label>
            <input type="text" name="movie-name" required>

            <label>Ticket Price:</label>
            <input type="number" name="ticket-price" required>

            <label>Available Seats:</label>
            <input type="number" name="available-seats" value="25" required>

            <input type="file" name="poster" id="poster">

            <button type="submit">Add Movie</button>
        </form>

    </div>

    </div>
</body>
</html>
