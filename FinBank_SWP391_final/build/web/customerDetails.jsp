<%@ page import="model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Details</title>
</head>
<body>
    <h1>CustomerDetails</h1>
    <%
        Customer customer = (Customer) request.getAttribute("customer");
        if (customer != null) {
    %>
        <p>ID: <%= customer.getCustomer_id() %></p>
        <p>Fullname: <%= customer.getFull_name() %></p>
        <p>Email: <%= customer.getEmail() %></p>
        <p>User: <%= customer.getUsername() %></p>
        <p>PhoneNumber: <%= customer.getPhone_number() %></p>
        <p>Address: <%= customer.getAddress() %></p>
        <p>Cardtype: <%= customer.getCard_type() %></p>
        <p>Status: <%= customer.getStatus() %></p>
        <p>Gender: <%= customer.getGender() %></p>
        <p>Picture: <img src="<%= customer.getProfile_picture() %>" alt="Profile Picture" /></p>
        <p>Amount: <%= customer.getAmount() %></p>
        <p>Creditlimit: <%= customer.getCredit_limit() %></p>
        <p>DateOfBirth: <%= customer.getDate_of_birth() %></p>
        <p>Createdat: <%= customer.getCreated_at() %></p>
    <%
        } else {
    %>
        <p>Err.</p>
    <%
        }
    %>
</body>
</html>