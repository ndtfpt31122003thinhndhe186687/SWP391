<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Request Status</title>
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
            border-bottom: 2px solid #c53030;
            padding-bottom: 10px;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            max-width: 500px;
            margin: 20px auto;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #c53030;
        }
        select {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #e53e3e;
            border-radius: 4px;
        }
        button {
            background-color: #c53030;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #9b2c2c;
        }
    </style>
</head>
<body>
    <h1>Update Request Status</h1>
    <form method="post" action="updaterequestcustomer">
        <input type="hidden" name="customer_id" value="<%= request.getAttribute("customer_id") %>">
        <label for="status">Status:</label>
        <select name="status" id="status">
            <option value="approved">Approved</option>
            <option value="rejected">Rejected</option>
        </select>
        <button type="submit">Update Status</button>
    </form>
</body>
</html>

