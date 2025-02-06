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
        <h1>Add a new Policy</h1>
        <h4 style="color:red">${requestScope.error}</h4>
        <form action="addPolicy" method="post">
            enter Policy name<input type="text" name="policy_name" ></input></br>
            enter Description<input type="text" name="description" ></input></br>
            enter Coverage amount<input type="text" name="coverage_amount"  /></br>
            enter Premium amount<input type="text" name="premium_amount" /></br>
            enter Status<input type="text" name="status" /></br>
            <button type="submit">Add new</button>
        </form>
    </body>
</html>
