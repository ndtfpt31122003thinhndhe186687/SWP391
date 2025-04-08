<%-- 
    Document   : feedbackInsurance
    Created on : Mar 13, 2025, 1:47:34 PM
    Author     : Windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Phản hồi</title>
        <style>
            /* Căn chỉnh tổng thể */
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                padding: 20px;
                background-color: #f9f9f9;
            }

            form {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 400px;
                margin: auto;
                border: 2px solid #e74c3c; /* Viền đỏ */
            }

            /* Nhãn và các input */
            label {
                font-weight: bold;
                display: block;
                margin-top: 15px;
                color: #e74c3c; /* Màu chữ đỏ */
            }

            textarea {
                width: 100%;
                height: 100px;
                padding: 8px;
                border: 1px solid #e74c3c;
                border-radius: 5px;
                resize: none;
            }

            /* CSS cho dropdown chọn chính sách */
            select {
                width: 100%;
                padding: 8px;
                border-radius: 5px;
                border: 1px solid #e74c3c;
                margin-top: 5px;
            }

            /* Nút gửi */
            button {
                display: block;
                width: 100%;
                background: #e74c3c;
                color: white;
                padding: 10px;
                margin-top: 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background 0.3s;
                font-size: 16px;
            }

            button:hover {
                background: #c0392b;
            }

            /* CSS cho đánh giá sao */
            .rating {
                display: flex;
                flex-direction: row-reverse;
                justify-content: center;
                gap: 5px;
                font-size: 30px;
                margin-top: 10px;
            }

            .rating input {
                display: none;
            }

            .rating label {
                cursor: pointer;
                color: black; /* Màu mặc định */
                transition: color 0.3s;
            }

            .rating input:checked ~ label,
            .rating label:hover,
            .rating label:hover ~ label {
                color: gold;
            }

        </style>
    </head>
    <body>
        <h3>${requestScope.error}</h3>
        <form action="feedback" method="post">
    <input type="hidden" name="service_id" value="${param.service_id}">          

    <label>Nhập nội dung phản hồi</label>
    <textarea name="feedback_content" id="editor1" required class="form-control">${param.feedback_content != null ? param.feedback_content : ""}</textarea>

    <label>Đánh giá</label>
    <div class="rating">
        <input type="radio" id="star5" name="feedback_rate" value="5" ${param.feedback_rate == '5' ? 'checked' : ''}>
        <label for="star5">★</label>

        <input type="radio" id="star4" name="feedback_rate" value="4" ${param.feedback_rate == '4' ? 'checked' : ''}>
        <label for="star4">★</label>

        <input type="radio" id="star3" name="feedback_rate" value="3" ${param.feedback_rate == '3' ? 'checked' : ''}>
        <label for="star3">★</label>

        <input type="radio" id="star2" name="feedback_rate" value="2" ${param.feedback_rate == '2' ? 'checked' : ''}>
        <label for="star2">★</label>

        <input type="radio" id="star1" name="feedback_rate" value="1" ${param.feedback_rate == '1' ? 'checked' : ''}>
        <label for="star1">★</label>
    </div>

    <button type="submit">Gửi phản hồi</button>
</form>

    <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>

    <script>
        CKEDITOR.replace('editor1');
    </script>
</body>
</html>
