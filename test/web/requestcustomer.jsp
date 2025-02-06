<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer List (Pending Requests)</title>
</head>
<body>
    <h1>Customer List with Pending Requests</h1>

    <!-- Form tìm kiếm -->
    <form method="get" action="customerrequest">
        <input type="text" name="search" value="<%= request.getAttribute("search") != null ? request.getAttribute("search") : "" %>" placeholder="Search by ID or Name" />
        <button type="submit">Search</button>
    </form>

    <table border="1">
        <tr>
            <th>Customer ID</th>
            <th>Customer Name</th>
        </tr>
        <%
            List<Customer> customers = (List<Customer>) request.getAttribute("listcustomer");
            if (customers != null && !customers.isEmpty()) {
                for (Customer customer : customers) {
        %>
                    <tr>
                        <td><%= customer.getCustomer_id() %></td>
                        <td>
                            <a href="updaterequestcustomer?customer_id=<%= customer.getCustomer_id() %>">
                                <%= customer.getFull_name() %>
                            </a>
                        </td>
                    </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="2">No pending requests found.</td>
                </tr>
        <%
            }
        %>
    </table>
</body>
</html>