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
        <h1>Update a Policy</h1>
        <c:set var="p" value="${requestScope.policy}"/>
        <form action="updatePolicy" method="post">
            <input hidden name="policy_id" value="${p.policy_id}">
            enter Policy name<input type="text" name="policy_name" value="${p.policy_name}"  /></br>
            enter Description<input type="text" name="description" value="${p.description}" /></br>
            enter Coverage amount<input type="text" name="coverage_amount"value="${p.coverage_amount} "/></br>
            enter Premium amount<input type="text" name="premium_amount" value="${p.premium_amount}"/></br>
            enter Status<input type="text" name="status" value="${p.status}" /></br>
            <button type="submit">Update</button>
        </form>
    </body>
</html>
