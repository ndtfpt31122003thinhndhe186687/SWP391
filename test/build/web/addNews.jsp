<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Bank News</title>
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
    <div class="container">
        <h1>Add Bank News</h1>
        <form action="addNews" method="post">
            <label for="title">Enter title:</label>
            <input type="text" name="title" id="title" required/><br/>
            <label for="content">Enter content:</label>
            <textarea name="content" id="content" required></textarea><br/>
            <button type="submit">ADD</button>
        </form>
    </div>
</body>
</html>
