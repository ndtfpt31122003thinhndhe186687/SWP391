<%@ page import="model.Debt_management" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Debt</title>
</head>
<body>
    <h1>Update Debt Management</h1>

    <%
        Debt_management debt = (Debt_management) request.getAttribute("debt");
        if (debt != null) {
    %>
        <form action="updatedebtmanagement" method="post">
            <input type="hidden" name="debt_id" value="<%= debt.getDebt_id() %>">
            <label for="debt_status">Debt Status:</label>
            <input type="text" id="debt_status" name="debt_status" value="<%= debt.getDebt_status() %>"><br>

            <label for="overdue_days">Overdue Days:</label>
            <input type="number" id="overdue_days" name="overdue_days" value="<%= debt.getOverdue_days() %>"><br>

            <label for="notes">Notes:</label>
            <textarea id="notes" name="notes"><%= debt.getNotes() %></textarea><br>

            <input type="submit" value="Update">
        </form>
    <%
        } else {
    %>
        <p>Debt not found.</p>
    <%
        }
    %>
</body>
</html>