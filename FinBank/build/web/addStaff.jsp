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
        <title>Add New Staff</title>
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
            
            .error-message {
                color: var(--primary-red);
                text-align: center;
                margin-bottom: 20px;
                font-weight: 500;
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
            <h1>Add New Staff</h1>
            <h4 class="error-message">${requestScope.error}</h4>
            <form action="addStaff" method="get">
                <div class="form-group">
                    <label for="full_name">Full Name:</label>
                    <input type="text" id="full_name" name="full_name" required />
                </div>
                
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required />
                </div>
                
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required />
                </div>
                
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required />
                </div>
                
                <div class="form-group">
                    <label for="phone_number">Phone Number:</label>
                    <input type="tel" id="phone_number" name="phone_number" required />
                </div>
                
                <div class="form-group">
                    <label for="gender">Gender:</label>
                    <input type="text" id="gender" name="gender" required />
                </div>
                
                <div class="form-group">
                    <label for="date_of_birth">Date of Birth:</label>
                    <input type="date" id="date_of_birth" name="date_of_birth" required />
                </div>
                
                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" required />
                </div>
                
                <div class="form-group">
                    <label for="role_id">Role ID:</label>
                    <input type="number" id="role_id" name="role_id" required />
                </div>
                
                <div class="form-group">
                    <label for="status">Status:</label>
                    <input type="text" id="status" name="status" required />
                </div>
                
                <button type="submit">Add New Staff</button>
            </form>
        </div>
    </body>
</html>
