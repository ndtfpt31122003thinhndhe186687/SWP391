<%@ page import="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Details</title>
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/customer-styles.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Customer Details</h1>
        <div class="customer-details">
            <%
                Customer customer = (Customer) request.getAttribute("customer");

                if (customer != null) {
            %>
                <p><strong>ID:</strong> <%= customer.getCustomer_id() %></p>
                <p><strong>Full Name:</strong> <%= customer.getFull_name() %></p>
                <p><strong>Email:</strong> <%= customer.getEmail() %></p>
                <p><strong>Username:</strong> <%= customer.getUsername() %></p>
                <p><strong>Phone Number:</strong> <%= customer.getPhone_number() %></p>
                <p><strong>Address:</strong> <%= customer.getAddress() %></p>
                <p><strong>Card Type:</strong> <%= customer.getCard_type() %></p>
                <p><strong>Status:</strong> <%= customer.getStatus() %></p>
                <p><strong>Gender:</strong> <%= customer.getGender() %></p>
                <p><strong>Profile Picture:</strong></p>
                <img src="<%= customer.getProfile_picture() %>" alt="Profile Picture" />
                <p><strong>Amount:</strong> <%= customer.getAmount() %></p>
                <p><strong>Credit Limit:</strong> <%= customer.getCredit_limit() %></p>
                <p><strong>Date of Birth:</strong> <%= customer.getDate_of_birth() %></p>
                <p><strong>Created At:</strong> <%= customer.getCreated_at() %></p>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>