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
        <title>Add New Service</title>
        <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
        <style>
            :root {
                --primary-red: #dc3545;
                --dark-red: #c82333;
                --light-red: #f8d7da;
            }
<<<<<<< HEAD

=======
            
>>>>>>> origin/phong
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 20px;
                color: #333;
            }
<<<<<<< HEAD

=======
            
>>>>>>> origin/phong
            h1 {
                color: var(--primary-red);
                text-align: center;
                margin-bottom: 30px;
                padding-bottom: 10px;
                border-bottom: 3px solid var(--primary-red);
            }
<<<<<<< HEAD

=======
            
>>>>>>> origin/phong
            .error-message {
                color: var(--primary-red);
                text-align: center;
                margin-bottom: 20px;
                font-weight: 500;
            }
<<<<<<< HEAD

=======
            
>>>>>>> origin/phong
            form {
                max-width: 600px;
                margin: 0 auto;
                background: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }
<<<<<<< HEAD

            .form-group {
                margin-bottom: 20px;
            }

=======
            
            .form-group {
                margin-bottom: 20px;
            }
            
>>>>>>> origin/phong
            label {
                display: block;
                margin-bottom: 5px;
                color: #555;
                font-weight: 500;
            }
<<<<<<< HEAD

=======
            
>>>>>>> origin/phong
            input, select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                transition: border-color 0.3s;
            }
<<<<<<< HEAD

=======
            
>>>>>>> origin/phong
            input:focus, select:focus {
                outline: none;
                border-color: var(--primary-red);
                box-shadow: 0 0 5px rgba(220,53,69,0.2);
            }
<<<<<<< HEAD

=======
            
>>>>>>> origin/phong
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
<<<<<<< HEAD

=======
            
>>>>>>> origin/phong
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
<<<<<<< HEAD
            /* Căn giữa nội dung */
            .form-group-status {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }

            .radio-group {
                display: flex;
                gap: 20px;
                align-items: center;
            }

            /* Ẩn radio button mặc định */
            .radio-input {
                display: none;
            }

            /* Tạo nút toggle */
            .radio-label {
                position: relative;
                display: flex;
                align-items: center;
                justify-content: center;
                width: 70px;
                height: 35px;
                border-radius: 20px;
                background-color: #ccc;
                cursor: pointer;
                transition: background-color 0.3s ease-in-out;
            }

            .radio-label::before {
                content: "";
                position: absolute;
                width: 30px;
                height: 30px;
                border-radius: 50%;
                background-color: white;
                top: 50%;
                left: 3px;
                transform: translateY(-50%);
                transition: all 0.3s ease-in-out;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            }

            /* Khi radio được chọn */
            .radio-input:checked + .radio-label {
                background-color: #007bff;
            }

            .radio-input:checked + .radio-label::before {
                left: calc(100% - 33px);
            }

            /* Màu đỏ khi chọn inactive */
            .radio-input[value="inactive"]:checked + .radio-label {
                background-color: #dc3545;
            }
=======
>>>>>>> origin/phong
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Add New Service</h1>
            <h4 class="error-message">${requestScope.error}</h4>
            <form action="addService" method="get">
                <div class="form-group">
                    <label for="service_name">Service Name:</label>
                    <input type="text" id="service_name" name="service_name" required />
                </div>
<<<<<<< HEAD

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea type="text" id="description" name="description" required ></textarea>
                </div>

=======
                
                <div class="form-group">
                    <label for="description">Description:</label>
                    <input type="text" id="description" name="description" required />
                </div>
                
>>>>>>> origin/phong
                <div class="form-group">
                    <label for="service_type">Service Type:</label>
                    <select id="service_type" name="service_type" required>
                        <option value="">Select service type</option>
                        <option value="saving">Saving</option>
                        <option value="loan">Loan</option>
                        <option value="deposit">Deposit</option>
                        <option value="withdrawal">Withdrawal</option>
                    </select>
                </div>
<<<<<<< HEAD

                <div class="form-group-status">
                    <label>Status:</label>
                    <div class="radio-group">
                        <input type="radio" id="active" name="status" value="active" class="radio-input" required>
                        <label for="active" class="radio-label"></label>

                        <input type="radio" id="inactive" name="status" value="inactive" class="radio-input" required>
                        <label for="inactive" class="radio-label"></label>
                    </div>
                </div>
                <button type="submit">Add New Service</button>
            </form>
        </div>
        <script>
            CKEDITOR.replace('description');
        </script>
=======
                
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select id="status" name="status" required>
                        <option value="">Select status</option>
                        <option value="active">Active</option>
                        <option value="inactive">Inactive</option>
                    </select>
                </div>
                
                <button type="submit">Add New Service</button>
            </form>
        </div>
>>>>>>> origin/phong
    </body>
</html>
