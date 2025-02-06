<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Debt Customers List</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Debt Customers List</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Debt ID</th>
                    <th>Customer Name</th>
                    <th>Customer Email</th>
                    <th>Loan ID</th>
                    <th>Debt Status</th>
                    <th>Overdue Days</th>
                    <th>Notes</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="debt" items="${debtCustomers}">
                    <tr>
                        <td>${debt.debt_id}</td>
                        <td>${debt.customerName}</td>
                        <td>${debt.customerEmail}</td>
                        <td>${debt.loan_id}</td>
                        <td>${debt.debt_status}</td>
                        <td>${debt.overdue_days}</td>
                        <td>${debt.notes}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>