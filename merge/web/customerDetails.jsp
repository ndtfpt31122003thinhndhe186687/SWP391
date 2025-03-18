<%@ page import="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Customer Details</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #fff5f5;
                color: #333;
                line-height: 1.6;
                margin: 0;
                padding: 20px;
            }
            h1, h2 {
                color: #c53030;
                border-bottom: 2px solid #c53030;
                padding-bottom: 10px;
            }
            .customer-info {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                margin-bottom: 20px;
            }
            .customer-info p {
                margin: 10px 0;
            }
            img {
                max-width: 200px;
                border-radius: 50%;
                border: 3px solid #c53030;
            }
            ul {
                list-style-type: none;
                padding: 0;
            }
            li {
                background-color: #fff;
                margin-bottom: 10px;
                padding: 10px;
                border-radius: 4px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .error {
                color: #c53030;
                font-weight: bold;
            }
        </style>
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
        <div class="customer-info">
            <img src="<%= customer.getProfile_picture() %>" alt="Profile Picture" />
            <p><strong>ID:</strong> <%= customer.getCustomer_id() %></p>
            <p><strong>Full Name:</strong> <%= customer.getFull_name() %></p>
            <p><strong>Email:</strong> <%= customer.getEmail() %></p>
            <p><strong>Username:</strong> <%= customer.getUsername() %></p>
            <p><strong>Phone Number:</strong> <%= customer.getPhone_number() %></p>
            <p><strong>Address:</strong> <%= customer.getAddress() %></p>
            <p><strong>Card Type:</strong> <%= customer.getCard_type() %></p>
            <p><strong>Status:</strong> <%= customer.getStatus() %></p>
            <p><strong>Gender:</strong> <%= customer.getGender() %></p>
            <p><strong>Amount:</strong> <%= customer.getAmount() %></p>
            <p><strong>Credit Limit:</strong> <%= customer.getCredit_limit() %></p>
            <p><strong>Date of Birth:</strong> <%= customer.getDate_of_birth() %></p>
            <p><strong>Created At:</strong> <%= customer.getCreated_at() %></p>
            <p><strong>Debt Status:</strong> <%= debtStatus != null ? debtStatus : "No debt information" %></p>
        </div>

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
        <h2>Edit Asset Status</h2>
        <form action="updateAssetStatus" method="POST">
            <input type="hidden" name="customer_id" value="<%= customer.getCustomer_id() %>" />
            <label for="asset_id">Asset ID:</label>
            <input type="text" id="asset_id" name="asset_id" required />
            <label for="new_status">New Status:</label>
            <select id="new_status" name="new_status" required>
                <option value="verified">Verified</option>
                <option value="unverified">Unverified</option>
            </select>
            <input type="submit" value="Update Status" />
        </form>

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
        <p class="error">Error: Customer not found.</p>
        <%
            }
        %>
    </body>
</html>

