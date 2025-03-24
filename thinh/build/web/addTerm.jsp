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
        <title>Thêm kỳ hạn</title>
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
            <h1>Thêm kỳ hạn mới</h1>
            <h4 class="error-message">${requestScope.error}</h4>
            <form action="addTerm" method="get" id="termForm" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="term_name">Tên kỳ hạn:</label>
                    <input type="text" id="term_name" name="term_name" required />
                </div>
                
                <div class="form-group">
                    <label for="duration">Thời gian:</label>
                    <input type="number" id="duration" name="duration" required min="1" 
                           oninput="this.value = this.value.replace(/[^0-9]/g, '')" />
                    <div class="invalid-feedback" id="duration-feedback">Hãy nhập đúng giá trị hợp lệ .</div>
                </div>
                
                <div class="form-group">
                    <label for="term_type">Loại kỳ hạn:</label>
                    <select id="term_type" name="term_type" required>
                        <option value="">Chọn loại kỳ hạn </option>
                        <option value="monthly">Tháng</option>
                        <option value="quarterly">Quý</option>
                        <option value="annually">Năm</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Trạng thái:</label>
                    <div style="display: flex; gap: 20px; align-items: center;">
                        <input type="radio" id="active" name="status" value="active" class="custom-radio" required>
                        <label for="active"><i class="fa-solid fa-circle-check"></i> Hoạt động</label>

                        <input type="radio" id="inactive" name="status" value="inactive" class="custom-radio" required>
                        <label for="inactive"><i class="fa-solid fa-circle-xmark"></i> Không hoạt động</label>
                    </div>
                </div>
                
                <button type="submit">Thêm mới</button>
            </form>
        </div>

        <script>
            function validateForm() {
                let isValid = true;
                
                // Duration validation
                const duration = document.getElementById('duration');
                const durationFeedback = document.getElementById('duration-feedback');
                if (!duration.checkValidity() || duration.value < 1) {
                    durationFeedback.style.display = 'block';
                    isValid = false;
                } else {
                    durationFeedback.style.display = 'none';
                }

                return isValid;
            }

            // Prevent non-numeric input in duration field
            document.getElementById('duration').addEventListener('keypress', function(e) {
                if (e.key < '0' || e.key > '9') {
                    e.preventDefault();
                }
            });
        </script>
    </body>
</html>
