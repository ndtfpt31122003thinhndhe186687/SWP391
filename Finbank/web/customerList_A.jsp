<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer List</title>
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/customer-styles.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Customer List</h1>
        <form action="CustomerList_AServlet" method="get" class="search-box">
            <input type="text" name="search" placeholder="Search" required>
            <button type="submit" class="button">Search</button>
        </form>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
            </tr>
            <%
                List<Customer> customers = (List<Customer>) request.getAttribute("customer");

                if (customers != null) {
                    for (Customer customer : customers) {
            %>
                        <tr>
                            <td><%= customer.getCustomer_id() %></td>
                            <td><a href="CustomerDetails_AServlet?id=<%= customer.getCustomer_id() %>"><%= customer.getFull_name() %></a></td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr>
                        <td colspan="2">Error: No customers found.</td>
                    </tr>
            <%
                }
            %>
        </table>
    </div>
</body>
</html>