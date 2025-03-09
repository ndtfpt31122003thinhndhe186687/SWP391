<%-- 
    Document   : insuranceTermDetail
    Created on : Feb 24, 2025, 4:14:19 PM
    Author     : Windows
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    <body>
         <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Chi Tiết Điều Khoản Của Bảo Hiểm</h1>
            </div>
        <div class="mt-3">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID Điều Khoản</th>
                        <th>Tên Điều Khoản</th>
                        <th>Tên Chính Sách</th>
                        <th>Mô Tả</th>                    
                        <th>Trạng Thái</th>

                    </tr>
                </thead>
                <c:forEach items="${listTerm}" var="T">
                    <tr>
                        <td>${T.term_id}</td>
                        <td>${T.term_name}</td>
                        <td>${T.policy_name}</td>
                        <td>${T.term_description}</td>                    
                        <td>${T.status}</td> 
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
