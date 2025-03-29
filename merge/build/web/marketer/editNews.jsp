<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh sửa tin tức ngân hàng</title>
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

        input[type="text"], textarea {
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
    </style>
</head>
<body>
    <c:set var="news" value="${requestScope.news}"/>
    <div class="container">
        <h1>Chỉnh sửa tin tức ngân hàng</h1>
        <form action="editNews" method="post" enctype="multipart/form-data">
            <input type="hidden" name="news_id" value="${news.news_id}" />
            <input type="hidden" name="category_id" value="${news.category_id}" />
            <input type="hidden" name="staff_id" value="${news.staff_id}" />
            <input type="hidden" name="oldImage" value="${news.picture}" />
            
            <label for="title">Nhập tiêu đề:</label>
            <input type="text" name="title" id="title" required value="${news.title}"/><br/>

            <label for="content">Nhập nội dung:</label>
            <textarea name="content" id="content" required>${news.content}</textarea><br/>

            <label for="image">Chọn hình ảnh (Chỉ hỗ trợ JPG/PNG):</label>
            <input type="file" name="image" id="image" value="${news.picture}"><br/>
            <img src="imageNews/${news.picture}" alt="Ảnh hiện tại" style="max-width: 200px; display: block; margin-bottom: 10px; border: 2px solid black;">

            <button type="submit">CẬP NHẬT</button>
        </form>

        <c:if test="${not empty error}">
            <div>
                <p style="color: red;">${error}</p>
            </div>
        </c:if>
    </div>
</body>
<script>
    CKEDITOR.replace('content');
</script>
</html>
