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
            h4 {
                color: red;
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
        </style>
    </head>
    <body>
        <h1>Add a new Insurance term</h1>
        <h4>${requestScope.error}</h4>
        <form action="addInsuranceTerm" method="post">
            <label>Enter Policy ID</label> <br>
            <select class="filter-dropdown" name="policy_id" >
                <c:if test ="${not empty listPolicy}">
                    <c:forEach var="p" items="${requestScope.listPolicy}">
                      <option value="${p.policy_id}">${p.policy_name}</option>  
                    </c:forEach>                
                </c:if>
            </select>
            <br>
            <label>Enter Term Name</label>
            <input type="text" name="term_name" />
            <label>Enter Term Description</label>
            <input type="text" name="term_description" />
            <label>Enter Start Date</label>
            <input type="text" name="start_date" />
            <label>Enter End Date</label>
            <input type="text" name="end_date" />
            <label>Enter Status</label><br>
            <select class="filter-dropdown" name="status" >
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
            </select>
            <br>
            <br>
            <button type="submit">Add new</button>
        </form>
    </body>
</html>
