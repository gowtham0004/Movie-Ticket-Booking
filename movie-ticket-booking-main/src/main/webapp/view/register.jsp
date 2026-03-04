<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="register">
    <h2>New Account</h2>
    <form action="${pageContext.request.contextPath}/register" method="POST">
        <table>
            <tr>
                <td><label>User Name: </label></td>
                <td><input type="text" name="username" required></td>
            </tr>
            <tr>
                <td><label>Email: </label></td>
                <td><input type="email" name="email" required></td>
            </tr>
            <tr>
                <td><label>Password: </label></td>
                <td><input type="password" name="password" required></td>
            </tr>
        </table>
        <button type="submit">Register</button>
    </form>
</div>
</body>
</html>