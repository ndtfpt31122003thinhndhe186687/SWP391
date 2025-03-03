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
        <title>Update Staff Information</title>
        <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
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

            input, select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                transition: border-color 0.3s;
            }

            input:focus, select:focus{
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

            .form-header {
                text-align: center;
                margin-bottom: 30px;
            }
            .password-requirements {
                font-size: 12px;
                color: #666;
                margin-top: 5px;
            }

            .invalid-feedback {
                color: var(--primary-red);
                font-size: 12px;
                margin-top: 5px;
                display: none;
            }
            /* Căn giữa nội dung */
            .form-group-status {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }

            /* ======= RADIO BUTTON CHO GENDER VÀ STATUS ======= */
            input[type="radio"].custom-radio {
                appearance: none; /* Ẩn radio mặc định */
                -webkit-appearance: none;
                -moz-appearance: none;
                width: 16px;
                height: 16px;
                border: 2px solid #888; /* Viền mặc định */
                border-radius: 50%;
                outline: none;
                cursor: pointer;
                position: relative;
            }

            /* Khi radio được chọn */
            input[type="radio"].custom-radio:checked {
                border: 6px solid transparent; /* Ẩn viền ngoài khi chọn */
            }

            /* Màu sắc theo từng loại */
            input#male.custom-radio:checked {
                background-color: blue;
            }

            input#female.custom-radio:checked {
                background-color: red;
            }

            input#active.custom-radio:checked {
                background-color: green;
            }

            input#inactive.custom-radio:checked {
                background-color: red;
            }

            /* Mặc định label có màu xám */
            label[for="male"], label[for="female"], label[for="active"], label[for="inactive"] {
                font-weight: bold;
                transition: color 0.3s ease-in-out;
                cursor: pointer;
            }

            /* Khi chọn Active -> Màu xanh lá */
            input#active.custom-radio:checked + label[for="active"] {
                color: green;
            }

            /* Khi chọn Inactive -> Màu đỏ */
            input#inactive.custom-radio:checked + label[for="inactive"] {
                color: red;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Update Staff Information</h1>
            <h4 class="error-message">${requestScope.error}</h4>
            <c:set var="s" value="${requestScope.staff}"/>
            <form action="updateStaff" method="post" id="staffForm" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="staff_id">Staff ID:</label>
                    <input type="number" id="staff_id" readonly name="staff_id" value="${s.staff_id}"/>
                </div>

                <div class="form-group">
                    <label for="full_name">Full Name:</label>
                    <input type="text" id="full_name" name="full_name" value="${s.full_name}" />
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>                 
                    <input type="email" id="email" name="email" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" value="${s.email}" />
                    <div class="invalid-feedback" id="email-feedback">Please enter a valid email address.</div>
                </div>

                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="${s.username}" />

                </div>

                <div class="form-group">
                    <label for="phone_number">Phone Number:</label>
                    <input type="tel" id="phone_number" name="phone_number" required 
                           pattern="[0-9]{10}" maxlength="10" value="${s.phone_number}"  />
                    <div class="invalid-feedback" id="phone-feedback">Please enter a valid 10-digit phone number.</div>
                </div>

                <div class="form-group">
                    <label>Gender:</label>
                    <div style="display: flex; gap: 20px; align-items: center;">
                        <input type="radio" id="male" name="gender" value="male" class="custom-radio" required
                               ${s.gender == 'male' ? 'checked' : ''}>
                        <label for="male"><i class="fa-solid fa-mars" style="color:blue"></i> Male</label>

                        <input type="radio" id="female" name="gender" value="female" class="custom-radio" required
                               ${s.gender == 'female' ? 'checked' : ''}>
                        <label for="female"><i class="fa-solid fa-venus" style="color:red"></i> Female</label>
                    </div>
                </div>        

                <div class="form-group">
                    <label for="date_of_birth">Date of Birth:</label>
                    <input type="date" id="date_of_birth" name="date_of_birth" value="${s.date_of_birth}" />
                </div>

                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" value="${s.address}" />
                </div>

                <div class="form-group">
                    <label for="role_id">Role ID:</label>
                    <input type="number" id="role_id" name="role_id" value="${s.role_id}" />
                </div>

                <div class="form-group">
                    <label>Status:</label>
                    <div style="display: flex; gap: 20px; align-items: center;">
                        <input type="radio" id="active" name="status" value="active" class="custom-radio" required
                               ${s.status == "active" ? "checked" : ""}>
                        <label for="active"><i class="fa-solid fa-circle-check"></i> Active</label>

                        <input type="radio" id="inactive" name="status" value="inactive" class="custom-radio" required
                               ${s.status == "inactive" ? "checked" : ""}>
                        <label for="inactive"><i class="fa-solid fa-circle-xmark"></i> Inactive</label>
                    </div>
                </div>        

                <button type="submit">Update Staff Information</button>
            </form>
        </div>
        <script>
            function validateForm() {
                let isValid = true;
                const form = document.getElementById('staffForm');

                const full_name = document.getElementById('full_name');
                const full_nameFeedback = document.getElementById('full_name-feedback');
                const full_nameValue = full_name.value.trim();
                const nameRegex = /^[A-Za-zÀ-Ỹà-ỹ\s]+$/;
                if (!nameRegex.test(full_nameValue)) {
                    full_nameFeedback.style.display = 'block';
                    full_nameFeedback.textContent = 'Họ tên không được chứa số hoặc ký tự đặc biệt.';
                    isValid = false;
                } else {
                    full_nameFeedback.style.display = 'none';
                }
                
               
                // Email validation
                const email = document.getElementById('email');
                const emailFeedback = document.getElementById('email-feedback');
                if (!email.checkValidity()) {
                    emailFeedback.style.display = 'block';
                    isValid = false;
                } else {
                    emailFeedback.style.display = 'none';
                }

                // Phone validation
                const phone = document.getElementById('phone_number');
                const phoneFeedback = document.getElementById('phone-feedback');
                if (!phone.checkValidity()) {
                    phoneFeedback.style.display = 'block';
                    isValid = false;
                } else {
                    phoneFeedback.style.display = 'none';
                }

                // Only allow numbers in phone field
                phone.addEventListener('input', function (e) {
                    this.value = this.value.replace(/[^0-9]/g, '');
                });

                return isValid;
            }

            // Prevent non-numeric input in phone field
            document.getElementById('phone_number').addEventListener('keypress', function (e) {
                if (e.key < '0' || e.key > '9') {
                    e.preventDefault();
                }
            });
        </script>                
    </body>
</html>
