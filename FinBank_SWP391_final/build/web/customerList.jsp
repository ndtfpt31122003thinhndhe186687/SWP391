<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sách khách hàng</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>CustomerList</h1>
<form action="customerList" method="get" class="search-box">
    <input type="text" name="search" placeholder="search" required>
    <button type="submit" class="button">search</button>
</form>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
        </tr>
        <%
            List<Customer> customers = (List<Customer>) request.getAttribute("customerList");

            if (customers != null) {
                for (Customer customer : customers) {
        %>
                    <tr>
                        <td><%= customer.getCustomer_id() %></td>
                       <td><a href="customerDetails?id=<%= customer.getCustomer_id() %>"><%= customer.getFull_name() %></a></td>
                    </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="2">Err.</td>
                </tr>
        <%
            }
        %>
    </table>
</body>
</html>