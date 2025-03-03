<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thêm tin tức</title>
        <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>

        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                color: #333;
            }

            .container {
                width: 80%;
                margin: 0 auto;
                padding-top: 20px;
            }

            h1 {
                text-align: center;
                font-size: 36px;
                margin-bottom: 20px;
                color: red;
            }

            form {
                background-color: #fff;
                border-radius: 8px;
                padding: 20px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                display: flex;
                flex-direction: column;
                gap: 20px;
                border: 2px solid black;
            }

            label {
                font-size: 18px;
                color: red;
            }

            input[type="text"], textarea, select, input[type="file"] {
                padding: 10px;
                font-size: 16px;
                border: 2px solid black;
                border-radius: 4px;
                width: 100%;
                box-sizing: border-box;
            }

            button {
                background-color: red;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
            }

            #preview {
                width: 100%;
                max-width: 300px;
                height: auto;
                margin-top: 10px;
                display: none;
                border: 2px solid #ddd;
                border-radius: 5px;
                padding: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Thêm Tin tức Ngân hàng</h1>
            <form method="post" action="addNews" enctype="multipart/form-data">
                <label for="category">Chọn Danh mục</label><br/>
                <select name="category" required>
                    <option value="">-- Chọn Danh mục --</option>
                    <c:forEach var="n" items="${requestScope.listNc}">
                        <option value="${n.category_id}">${n.category_name}</option>
                    </c:forEach>
                </select><br/>

                <label for="title">Nhập tiêu đề:</label>
                <input type="text" name="title" id="title" required/><br/>

                <label for="content">Nhập nội dung:</label>
                <textarea name="content" id="content" required></textarea><br/>

                <label for="image">Chọn hình ảnh (chỉ JPG/PNG):</label>
                <input type="file" name="image" id="image" required><br/>
                <img id="preview" src="#" alt="Xem trước hình ảnh"/>

                <button type="submit">THÊM</button>
            </form>

            <c:if test="${not empty err}">
                <div>
                    <p style="color: red;">${err}</p>
                </div>
            </c:if>
        </div>
    </body>

    <script>
        CKEDITOR.replace('content');
    </script>
</html>
