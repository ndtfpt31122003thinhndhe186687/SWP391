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
        <h1>Update a staff</h1>
        <c:set var="s"  value="${requestScope.staff}"/>
        <form action="updateStaff" method="post">
            enter id:<input type="number" readonly name="staff_id" value="${s.staff_id}"/></br>
            enter full_name:<input type="text" name="full_name" value="${s.full_name}" /></br>
            enter email:<input type="text" name="email" value="${s.email}" /></br>
            enter username<input type="text" name="username" value="${s.username}"></input></br>
            enter password<input type="text" name="password" value="${s.password}"/></br>
            enter phone_number<input type="text" name="phone_number" value="${s.phone_number}"/></br>
            enter gender<input type="text" name="gender" value="${s.gender}"/></br>
            enter date_of_birth :<input type="text" name="date_of_birth" value="${s.date_of_birth}"/></br>
            enter address<input type="text" name="address" value="${s.address}"/></br>
            enter role_id<input type="number" name="role_id" value="${s.role_id}"/></br>
            enter status<input type="text" name="status" value="${s.status}"/></br>

            <button type="submit">Update</button>
        </form>
    </body>
</html>
