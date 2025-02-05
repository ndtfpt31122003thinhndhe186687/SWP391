<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Request Status</title>
</head>
<body>
    <h1>Update Request Status for Customer ID: <%= request.getAttribute("customer_id") %></h1>

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