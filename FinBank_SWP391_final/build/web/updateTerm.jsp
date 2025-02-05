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
        <title>Update Term</title>
        <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
        <style>
            :root {
                --primary-red: #dc3545;
                --dark-red: #c82333;
                --light-red: #f8d7da;
            }
            
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 20px;
                color: #333;
            }
            
            h1 {
                color: var(--primary-red);
                text-align: center;
                margin-bottom: 30px;
                padding-bottom: 10px;
                border-bottom: 3px solid var(--primary-red);
            }
            
            form {
                max-width: 600px;
                margin: 0 auto;
                background: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }
            
            .form-group {
                margin-bottom: 20px;
            }
            
            label {
                display: block;
                margin-bottom: 5px;
                color: #555;
                font-weight: 500;
            }
            
            input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                transition: border-color 0.3s;
            }
            
            input:focus {
                outline: none;
                border-color: var(--primary-red);
                box-shadow: 0 0 5px rgba(220,53,69,0.2);
            }
            
            input[readonly] {
                background-color: #f8f9fa;
                cursor: not-allowed;
            }
            
            button {
                background-color: var(--primary-red);
                color: white;
                padding: 12px 30px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                font-weight: 600;
                width: 100%;
                margin-top: 20px;
                transition: background-color 0.3s;
            }
            
            button:hover {
                background-color: var(--dark-red);
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Update a term</h1>
            <c:set var="t" value="${requestScope.term}"/>
            <form action="updateTerm" method="post">
                <div class="form-group">
                    <label for="term_id">Term ID:</label>
                    <input type="number" id="term_id" readonly name="term_id" value="${t.term_id}"/>
                </div>
                
                <div class="form-group">
                    <label for="term_name">Term Name:</label>
                    <input type="text" id="term_name" name="term_name" value="${t.term_name}" />
                </div>
                
                <div class="form-group">
                    <label for="duration">Duration:</label>
                    <input type="text" id="duration" name="duration" value="${t.duration}" />
                </div>
                
                <div class="form-group">
                    <label for="term_type">Term Type:</label>
                    <input type="text" id="term_type" name="term_type" value="${t.term_type}" />
                </div>
                
                <div class="form-group">
                    <label for="status">Status:</label>
                    <input type="text" id="status" name="status" value="${t.status}" />
                </div>
                
                <button type="submit">Update</button>
            </form>
        </div>
    </body>
</html>
