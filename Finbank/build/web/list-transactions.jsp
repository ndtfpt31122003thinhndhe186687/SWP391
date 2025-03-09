<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>List of Transactions</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        .amount-positive { color: green; }
        .amount-negative { color: red; }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h2>List of Transactions</h2>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Service</th>
                        <th>Amount</th>
                        <th>Date</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="transaction" items="${transactions}">
                        <tr>
                            <td>${transaction.transaction_id}</td>
                            <td>${transaction.customer_id}</td>
                            <td>${transaction.service_id}</td>
                            <td class="${transaction.amount >= 0 ? 'amount-positive' : 'amount-negative'}">
                                <fmt:formatNumber value="${transaction.amount}" type="currency" currencySymbol="$"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${transaction.transaction_date}" 
                                              pattern="dd-MM-yyyy HH:mm:ss"/>
                            </td>
                            <td>${transaction.transaction_type}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>