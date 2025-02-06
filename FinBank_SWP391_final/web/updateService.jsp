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
        <h1>Update a service</h1>
        <c:set var="s"  value="${requestScope.service}"/>
        <form action="updateService" method="post">
            enter service_id:<input type="number" readonly name="service_id" value="${s.service_id}"/></br>
            enter service_name: <input type="text" name="service_name" value="${s.service_name}" /></br>
            enter description: <input type="text" name="description" value="${s.description}" /></br>
            enter service_type: <input type="text" name="service_type" value="${s.service_type}" ></input></br>
            enter status: <input type="text" name="status" value="${s.status}"/></br>         
            <button type="submit">Update</button>
        </form>
    </body>
</html>
