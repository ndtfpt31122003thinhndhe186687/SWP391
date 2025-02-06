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
        <h1>Add a new insurance Customer</h1>
        <form action="addInsuranceCustomer" method="post">
            enter Customer id<input type="text" name="customer_id"  /></br>
            enter Full name<input type="text" name="full_name" ></input></br>
            enter Email <input type="text" name="email"  /></br>
            enter Username <input type="text" name="username" /></br>
            enter Phone number <input type="text" name="phone_number" /></br>
            enter Address<input type="text" name="address" /></br>
            enter Gender<input type="text" name="gender" /></br>
            <button type="submit">Add new</button>
        </form>
    </body>
</html>
