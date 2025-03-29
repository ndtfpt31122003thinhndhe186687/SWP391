<%-- 
    Document   : amountStatistics
    Created on : Feb 6, 2025, 1:37:15 AM
    Author     : default
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Amount Statistics</title>
</head>
<body>
    <h1>Amount Statistics</h1>

    <c:forEach var="tableEntry" items="${allTableStatistics}">
        <h2>Statistics for ${tableEntry.key.tableName}</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>Year</th>
                    <th>Month</th>
                    <th>Total Amount</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="stat" items="${tableEntry.value}">
                    <tr>
                        <td>${stat.year}</td>
                        <td>${stat.month}</td>
                        <td>${stat.totalAmount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:forEach>
</body>
</html>
