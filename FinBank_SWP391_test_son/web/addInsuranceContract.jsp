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
        <h1>Add a new insurance contract</h1>
        <form action="addInsuranceContract" method="post">
            enter Customer id<input type="text" name="customer_id"  /></br>
            enter Service id<input type="text" name="service_id" ></input></br>
            enter Start date <input type="text" name="start_date"  /></br>
            enter End date <input type="text" name="end_date" /></br>
            enter Payment frequency <input type="text" name="payment_frequency" /></br>
            enter Status<input type="text" name="status" /></br>
            <button type="submit">Add new</button>
        </form>
    </body>
</html>
