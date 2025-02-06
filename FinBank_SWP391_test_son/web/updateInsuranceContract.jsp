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
        <h1>Update a Insurance contract</h1>
        <c:set var="c" value="${requestScope.contract}"/>
        <form action="updateInsuranceContract" method="post">
            <input hidden name="contract_id" value="${c.contract_id}">
            <input hidden name="service_id" value="${c.service_id}">
            <input hidden name="payment_frequency" value="${c.payment_frequency}">
            <input hidden name="start_date" value="${c.start_date}">
            <input hidden name="end_date" value="${c.end_date}">
            enter Status<input type="text" name="status" value="${c.status}" /></br>

            <button type="submit">Update</button>
        </form>
    </body>
</html>
