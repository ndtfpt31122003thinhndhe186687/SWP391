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
        <h1>Add a new Insurance transaction</h1>
        <form action="addInsuranceTransaction" method="post">
            enter Contract id<input type="text" name="contract_id"  /></br>
            enter Customer id<input type="text" name="customer_id" ></input></br>
            enter Amount<input type="text" name="amount"  /></br>
            enter Transaction type<input type="text" name="transaction_type" /></br>
            enter Notes<input type="text" name="notes" /></br>         
            <button type="submit">Add new</button>
        </form>
    </body>
</html>
