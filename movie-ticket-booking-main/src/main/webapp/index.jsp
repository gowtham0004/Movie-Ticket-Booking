<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

</head>
<body>
    <%
        Cookie[] cookies = request.getCookies();
        String username = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) {
                    username = java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                    break;
                }
            }
        }
        if (username != null) {
            // Already logged in → redirect to home page
            response.sendRedirect("viewMovie");
            return;
        }
    %>

    <% String msg = (String) request.getAttribute("message");
       if (msg != null) { %>
        <p style="color: #4A8FE8; font-weight: bold;"><%= msg %></p>
    <% } %>

    <div class="container">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <table>
                <tr>
                    <td><label>Email: </label></td>
                </tr>
                <tr>
                    <td><input type="email" name="email" required></td>
                </tr>
                <tr>
                    <td><label>Password: </label></td>
                </tr>
                <tr>
                    <td><input type="password" name="password" required></td>
                </tr>
            </table>
            <button type="submit">Login</button>
        </form>
        <p>Don&apos;t have an account?</p>
        <a style="color: blue; text-decoration: none; color: #aaaaff" href="view/register.jsp">Register</a>
    </div>
</body>
</html>