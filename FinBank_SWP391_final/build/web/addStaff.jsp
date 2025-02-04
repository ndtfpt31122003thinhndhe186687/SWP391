<%-- 
    Document   : new
    Created on : Sep 26, 2024, 3:49:32 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
    </head>
    <body>
        <h1>Add a new staff</h1>
        <h4 style="color:red">${requestScope.error}</h4>
        <form action="addStaff" method="get">
            enter full_name:<input type="text" name="full_name"  /></br>
            enter email:<input type="text" name="email"  /></br>
            enter username<input type="text" name="username" ></input></br>
            enter password<input type="text" name="password" "/></br>
            enter phone_number<input type="text" name="phone_number" /></br>
            enter gender<input type="text" name="gender" "/></br>
            enter date_of_birth :<input type="text" name="date_of_birth" /></br>
            enter address<input type="text" name="address" /></br>
            enter role_id<input type="number" name="role_id" /></br>
            enter status<input type="text" name="status" "/></br>
            <button type="submit">Add new</button>
        </form>
    </body>
</html>
