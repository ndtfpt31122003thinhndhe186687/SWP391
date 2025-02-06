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
        <h1>Update a term</h1>
        <c:set var="t"  value="${requestScope.term}"/>
        <form action="updateTerm" method="post">
            enter term_id:<input type="number" readonly name="term_id" value="${t.term_id}"/></br>
            enter term_name: <input type="text" name="term_name" value="${t.term_name}" /></br>
            enter duration <input type="text" name="duration" value="${t.duration}" /></br>
            enter term_type: <input type="text" name="term_type" value="${t.term_type}" ></input></br>
            enter status: <input type="text" name="status" value="${t.status}"/></br>         
            <button type="submit">Update</button>
        </form>
    </body>
</html>
