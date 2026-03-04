<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Make Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body {
            margin: 10px 50px;
        }
    </style>
</head>
<body>

    <% String msg = (String) request.getAttribute("message");
       if (msg != null) { %>
        <p style="color: #4A8FE8; font-weight: bold;"><%= msg %></p>
    <% } %>

    <div class="top-nav">
        <a href="${pageContext.request.contextPath}/view/admin/makeUser.jsp"><button class="admin-btn">Make User</button></a>
        <a href="${pageContext.request.contextPath}/viewMovie"><button>Home</button></a>
    </div>

    <div class="container make">
        <h2>New Admin</h2>

        <form action="${pageContext.request.contextPath}/makeAdmin" method="post">
            <label>User ID:</label><br>
            <input type="number" name="user-id" required><br><br>

            <button type="submit">Change</button>
        </form>
    </div>
</body>
</html>
