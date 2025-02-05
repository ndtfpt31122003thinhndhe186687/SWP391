<%@ page import="java.util.List" %>
<%@ page import="model.Debt_management" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Debt Management List</title>
</head>
<body>
    <h1>Debt Management List</h1>

    <!-- Form tìm kiếm -->
    <form method="get" action="listdebt">
        <input type="text" name="search" value="<%= request.getAttribute("search") != null ? request.getAttribute("search") : "" %>" placeholder="Search by Debt ID or Customer Name" />
        <button type="submit">Search</button>
    </form>

    <table border="1">
        <tr>
            <th>Debt ID</th>
            <th>Customer Name</th>
        </tr>
        <%
            List<Debt_management> debts = (List<Debt_management>) request.getAttribute("listdebt");
            if (debts != null && !debts.isEmpty()) {
                for (Debt_management debt : debts) {
        %>
                    <tr>
                        <td>
                            <a href="updatedebtmanagement?id=<%= debt.getDebt_id() %>">
                                <%= debt.getDebt_id() %>
                            </a>
                        </td>
                        <td><%= debt.getCustomerName() %></td>
                    </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="2">No debts found.</td>
                </tr>
        <%
            }
        %>
    </table>
</body>
</html>