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
        <h1>Update Insurance term</h1>
        <h4>${requestScope.error}</h4>
        <c:set var="t" value="${requestScope.term}"/>
        <form action="updateInsuranceTerm" method="post">            
            <input type="hidden" name="term_id" value="${t.term_id}">
            <label>Enter Policy ID</label> <br>
            <select class="filter-dropdown" name="policy_id" >
                <c:if test ="${not empty listPolicy}">
                    <c:forEach var="p" items="${requestScope.listPolicy}">
                        <option value="${p.policy_id}" ${p.policy_id == t.policy_id ? 'selected' : ''}>${p.policy_name}</option>  
                    </c:forEach>                
                </c:if>
            </select>
            <br>
            <label>Enter Term Name</label>
            <input type="text" name="term_name" value="${t.term_name}" required />
            <label>Enter Term Description</label>
            <input type="text" name="term_description" value="${t.term_description}" required/>
            <label>Enter Start Date</label>
            <input type="text" name="start_date" value="${t.start_date}" required/>
            <label>Enter End Date</label>
            <input type="text" name="end_date" value="${t.end_date}" required/>
            <label>Enter Status</label><br>
            <select class="filter-dropdown" name="status" >                
                <c:forEach var="s" items="${listStatus}">
                    <option value="${s}" ${s == t.status ? 'selected' : ''}>${s}</option>
                </c:forEach>   
            </select>
            <br>
            <br>
            <button type="submit">Update</button>
        </form>
    </body>
</html>
