<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Receipt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

</head>

<body>

<div class="container">

    <h2>Booking Successful!</h2>
    <p>Your ticket has been booked successfully.</p>

    <div class="details-box">
        <p><strong>Movie:</strong> ${movieName}</p>
        <p><strong>Seats:</strong> ${seats}</p>
        <p><strong>Total Amount:</strong> ₹${totalAmount}</p>
    </div>

    <a href="${pageContext.request.contextPath}/viewMovie"><button>Back to Home</button></a>
</div>

</body>
</html>
