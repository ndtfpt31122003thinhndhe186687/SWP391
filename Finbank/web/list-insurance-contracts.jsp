<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>List of Insurance Contracts</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>List of Insurance Contracts</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Contract ID</th>
                    <th>Customer ID</th>
                    <th>Service</th>
                    <th>Policy</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Payment Frequency</th>
                    <th>Status</th>
                    <th>Created At</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="contract" items="${contracts}">
                    <tr>
                        <td>${contract.contract_id}</td>
                        <td>${contract.customer_id}</td>
                        <td>${contract.service_id}</td>
                        <td>${contract.policy_id}</td>
                        <td><fmt:formatDate value="${contract.start_date}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${contract.end_date}" pattern="yyyy-MM-dd"/></td>
                        <td>${contract.payment_frequency}</td>
                        <td>${contract.status}</td>
                        <td><fmt:formatDate value="${contract.created_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>