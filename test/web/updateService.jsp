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
        <title>Update Service</title>
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
            
            input, select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                transition: border-color 0.3s;
            }
            
            input:focus, select:focus {
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

            select {
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
                background-repeat: no-repeat;
                background-position: right 1rem center;
                background-size: 1em;
            }

            .error-message {
                color: var(--primary-red);
                text-align: center;
                margin-bottom: 20px;
                font-weight: 500;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Update a service</h1>
            <h4 class="error-message">${requestScope.error}</h4>
            <c:set var="s" value="${requestScope.service}"/>
            <form action="updateService" method="post">
                <div class="form-group">
                    <label for="service_id">Service ID:</label>
                    <input type="number" id="service_id" readonly name="service_id" value="${s.service_id}"/>
                </div>
                
                <div class="form-group">
                    <label for="service_name">Service Name:</label>
                    <input type="text" id="service_name" name="service_name" value="${s.service_name}" required />
                </div>
                
                <div class="form-group">
                    <label for="description">Description:</label>
                    <input type="text" id="description" name="description" value="${s.description}" required />
                </div>
                
                <div class="form-group">
                    <label for="service_type">Service Type:</label>
                    <select id="service_type" name="service_type" required>
                        <option value="">Select service type</option>
                        <option value="saving" ${s.service_type == 'saving' ? 'selected' : ''}>Saving</option>
                        <option value="loan" ${s.service_type == 'loan' ? 'selected' : ''}>Loan</option>
                        <option value="deposit" ${s.service_type == 'deposit' ? 'selected' : ''}>Deposit</option>
                        <option value="withdrawal" ${s.service_type == 'withdrawal' ? 'selected' : ''}>Withdrawal</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select id="status" name="status" required>
                        <option value="">Select status</option>
                        <option value="active" ${s.status == 'active' ? 'selected' : ''}>Active</option>
                        <option value="inactive" ${s.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                
                <button type="submit">Update</button>
            </form>
        </div>
    </body>
</html>
