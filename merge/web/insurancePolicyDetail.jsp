<%-- 
    Document   : insurancePolicyDetail
    Created on : Feb 24, 2025, 3:41:17 PM
    Author     : Windows
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết chính sách</title>
         <!-- Bootstrap 5 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<style>
    /* Tổng thể */
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        margin: 20px;
    }

    /* Tiêu đề */
    .title-group h1 {
        color: #dc3545;
        font-weight: bold;
        text-align: center;
        margin-bottom: 20px;
    }

    /* Bảng */
    .table {
        width: 100%;
        border-collapse: collapse;
        background-color: white;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        overflow: hidden;
    }

    .table thead {
        background-color: #dc3545;
        color: white;
        text-align: center;
    }

    .table thead th {
        padding: 12px;
        font-size: 16px;
    }

    .table tbody tr {
        text-align: center;
        border-bottom: 1px solid #ddd;
    }

    .table tbody tr:hover {
        background-color: #f8d7da;
        transition: 0.3s;
    }

    .table td {
        padding: 10px;
        font-size: 14px;
        color: #333;
    }

    /* Định dạng số tiền */
    .format-number {
        font-weight: bold;
        color: #dc3545;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .table {
            font-size: 12px;
        }

        .table thead th, .table td {
            padding: 8px;
        }
    }
</style>

    </head>
<body class="bg-light">
    <div class="container mt-4">
        <!-- Tiêu đề -->
        <div class="text-center mb-4">
            <h1 class="h2 text-danger">Chi Tiết Chính Sách Của Bảo Hiểm</h1>
        </div>

        <!-- Duyệt danh sách chính sách -->
        <c:forEach items="${listPolicy}" var="P">
            <div class="mb-4">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th class="w-25 bg-light">Tên Chính Sách</th>
                            <td>${P.policy_name}</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Mô tả</th>
                            <td>${P.description}</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Số Tiền Được Nhận</th>
                            <td>
                                <strong class="text-success">
                                    <fmt:formatNumber value="${P.coverage_amount}" pattern="#,##0.00" /> VND
                                </strong>
                            </td>
                        </tr>
                        <tr>
                            <th class="bg-light">Số Tiền Cần Đóng</th>
                            <td>
                                <strong class="text-danger">
                                    <fmt:formatNumber value="${P.premium_amount}" pattern="#,##0.00" /> VND
                                </strong>
                            </td>
                        </tr>
                        <tr>
                            <th class="bg-light">Trạng Thái</th>
                            <td>
                                <span class="badge ${P.status eq 'active' ? 'bg-success' : 'bg-secondary'}">
                                    ${P.status == 'active' ? 'Hoạt động' : 'Ngừng hoạt động'}
                                </span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </c:forEach>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
