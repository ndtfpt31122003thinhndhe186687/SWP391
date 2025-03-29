<%@ page import="model.Debt_management" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Debt</title>
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
        input[type="text"], input[type="number"], textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #e53e3e;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #c53030;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #9b2c2c;
        }
        .error {
            color: #c53030;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h1>Quản lý nợ</h1>

    <%
        Debt_management debt = (Debt_management) request.getAttribute("debt");
        if (debt != null) {
    %>
        <form action="updatedebtmanagement" method="post">
            <input type="hidden" name="debt_id" value="<%= debt.getDebt_id() %>">
            <label for="debt_status">Tình trạng nợ:</label>
            <input type="text" id="debt_status" name="debt_status" value="<%= debt.getDebt_status() %>">

            <label for="overdue_days">Ngày quá hạn:</label>
            <input type="number" id="overdue_days" name="overdue_days" value="<%= debt.getOverdue_days() %>">

            <label for="notes">Ghi chú:</label>
            <textarea id="notes" name="notes"><%= debt.getNotes() %></textarea>

            <input type="submit" value="Update">
        </form>
    <%
        } else {
    %>
        <p class="error">Debt not found.</p>
    <%
        }
    %>
</body>
</html>

