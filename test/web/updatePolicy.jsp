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
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #ffe6e6;
                color: #990000;
                text-align: center;
            }
            h1 {
                color: #cc0000;
            }
            form {
                background: #ffcccc;
                padding: 20px;
                border-radius: 10px;
                display: inline-block;
                text-align: left;
            }
            input {
                display: block;
                margin: 10px 0;
                padding: 5px;
                border: 1px solid #cc0000;
                border-radius: 5px;
            }
            input[type="hidden"] {
                display: none;
            }
            button {
                background-color: #cc0000;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
            }
            button:hover {
                background-color: #990000;
            }
            
            select{
                width: calc(100% - 12px); /* Đảm bảo chiều rộng tương tự input */
                padding: 5px;
                border: 1px solid #cc0000;
                border-radius: 5px;
            }
            a {
                background-color: #cc0000;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <h1>Update a Policy</h1>
        <h4>${requestScope.error}</h4>
        <c:set var="p" value="${requestScope.policy}"/>
        <form action="updatePolicy" method="post">
            <input type="hidden" name="policy_id" value="${p.policy_id}">
            Enter Policy name<input type="text" name="policy_name" value="${p.policy_name}"  /></br>
            Enter Description<input type="text" name="description" value="${p.description}" /></br>
            Enter Coverage amount<input type="text" name="coverage_amount"value="${p.coverage_amount} "/></br>
            Enter Premium amount<input type="text" name="premium_amount" value="${p.premium_amount}"/></br>
            Enter Status
            <select class="filter-dropdown" name="status">
                <option value="active" ${p.status == 'active' ? 'selected' : ''}>Active</option>
                    <option value="inactive" ${p.status == 'inactive' ? 'selected' : ''}>Inactive</option>
            </select>
            </br>
            <button type="submit">Update</button>
        </form>
            <br>
    </body>
</html>
