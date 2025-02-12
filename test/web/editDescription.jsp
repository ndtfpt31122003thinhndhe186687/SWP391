<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Description</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff5f5;
            color: #333;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #c53030;
            text-align: center;
            border-bottom: 2px solid #c53030;
            padding-bottom: 10px;
        }
        form {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
            color: #c53030;
        }
        textarea {
            width: 100%;
            height: 100px;
            border: 2px solid #c53030;
            border-radius: 4px;
            padding: 10px;
            margin-bottom: 20px;
            resize: none;
        }
        input[type="submit"] {
            background-color: #c53030;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #a82323;
        }
    </style>
</head>
<body>
    <h1>Edit Description</h1>
    <form action="UpdateAssetServlet" method="POST">
        <input type="hidden" name="customer_id" value="${customer_id}" />
        <label for="description">New Description:</label>
        <textarea id="description" name="description" required>${description}</textarea>
        <input type="submit" value="Update" />
    </form>
</body>
</html>