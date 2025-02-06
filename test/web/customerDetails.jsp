<%@ page import="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Details</title>
</head>
<body>
    <h1>Customer Details</h1>
    <%
        Customer customer = (Customer) request.getAttribute("customer");
        String debtStatus = (String) request.getAttribute("debtStatus");
        List<String> assetStatuses = (List<String>) request.getAttribute("assetStatuses");
        List<String> serviceNames = (List<String>) request.getAttribute("serviceNames");

        if (customer != null) {
    %>
        <p>ID: <%= customer.getCustomer_id() %></p>
        <p>Full Name: <%= customer.getFull_name() %></p>
        <p>Email: <%= customer.getEmail() %></p>
        <p>Username: <%= customer.getUsername() %></p>
        <p>Phone Number: <%= customer.getPhone_number() %></p>
        <p>Address: <%= customer.getAddress() %></p>
        <p>Card Type: <%= customer.getCard_type() %></p>
        <p>Status: <%= customer.getStatus() %></p>
        <p>Gender: <%= customer.getGender() %></p>
        <p>Profile Picture: <img src="<%= customer.getProfile_picture() %>" alt="Profile Picture" /></p>
        <p>Amount: <%= customer.getAmount() %></p>
        <p>Credit Limit: <%= customer.getCredit_limit() %></p>
        <p>Date of Birth: <%= customer.getDate_of_birth() %></p>
        <p>Created At: <%= customer.getCreated_at() %></p>
        <p>Debt Status: <%= debtStatus != null ? debtStatus : "No debt information" %></p>

        <h2>Asset Statuses</h2>
        <ul>
        <%
            if (assetStatuses.isEmpty()) {
        %>
            <li>No assets found.</li>
        <%
            } else {
                for (String status : assetStatuses) {
        %>
            <li><%= status %></li>
        <%
                }
            }
        %>
        </ul>

        <h2>Services</h2>
        <ul>
        <%
            if (serviceNames.isEmpty()) {
        %>
            <li>No services found.</li>
        <%
            } else {
                for (String serviceName : serviceNames) {
        %>
            <li><%= serviceName %></li>
        <%
                }
            }
        %>
        </ul>
    <%
        } else {
    %>
        <p>Error: Customer not found.</p>
    <%
        }
    %>
</body>
</html>