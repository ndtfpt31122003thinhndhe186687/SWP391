<!DOCTYPE html>
<%@ page import="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Customer Details</title>
    
    <!-- CSS FILES -->
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-icons.css" rel="stylesheet">
    <link href="css/tooplate-mini-finance.css" rel="stylesheet">
</head>
<body>
    <header class="navbar sticky-top flex-md-nowrap bg-danger">
        <div class="col-md-3 col-lg-3 px-3 fs-6">
            <a class="navbar-brand text-white" href="home">
                <i class="bi-box"></i>
                Finbank
            </a>
        </div>
    </header>
    
    <div class="container py-5">
        <h3 class="text-danger mb-4">Chi tiết khách hàng</h3>
        
        <div class="card shadow p-4 mb-4">
            <%
                Customer customer = (Customer) request.getAttribute("customer");
                if (customer != null) {
            %>
            <div class="row">
                <div class="col-md-4 text-center">
                    <img src="<%= customer.getProfile_picture() %>" class="img-fluid rounded-circle border" alt="Profile Picture" style="width: 150px; height: 150px;">
                </div>
                <div class="col-md-8">
                    <p><strong>ID:</strong> <%= customer.getCustomer_id() %></p>
                    <p><strong>Họ và Tên:</strong> <%= customer.getFull_name() %></p>
                    <p><strong>Email:</strong> <%= customer.getEmail() %></p>
                    <p><strong>Tên đăng nhập:</strong> <%= customer.getUsername() %></p>
                    <p><strong>Số điện thoại:</strong> <%= customer.getPhone_number() %></p>
                    <p><strong>Địa chỉ:</strong> <%= customer.getAddress() %></p>
                    <p><strong>Loại thẻ:</strong> <%= customer.getCard_type() %></p>
                    <p><strong>Trạng thái:</strong> <%= customer.getStatus() %></p>
                    <p><strong>Giới tính:</strong> <%= customer.getGender() %></p>
                    <p><strong>Số dư tài khoản:</strong> <%= customer.getAmount() %></p>
                    <p><strong>Hạn mức tín dụng:</strong> <%= customer.getCredit_limit() %> VNĐ</p>
                    <p><strong>Ngày sinh:</strong> <%= customer.getDate_of_birth() %></p>
                    <p><strong>Ngày tạo tài khoản:</strong> <%= customer.getCreated_at() %></p>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
    
    <footer class="site-footer bg-light py-3 text-center">
        <p class="mb-0">Copyright © Mini Finance 2048</p>
    </footer>