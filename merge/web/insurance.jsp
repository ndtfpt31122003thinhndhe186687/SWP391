<%-- 
    Document   : insurance
    Created on : Feb 11, 2025, 12:36:07 AM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bảo Hiểm Ngân Hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            :root {
                --primary-color: #dc3545;
                --secondary-color: #6c757d;
            }

            body {
                background-color: #f8f9fa;
            }

            .insurance-header {
                background: linear-gradient(135deg, #dc3545, #c82333);
                color: white;
                padding: 3rem 0;
                margin-bottom: 2rem;
                text-align: center;
            }

            .insurance-card {
                background: white;
                border: none;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                transition: transform 0.3s, box-shadow 0.3s;
                margin-bottom: 2rem;
                border-radius: 12px;
                overflow: hidden;
                height: 100%;
            }

            .insurance-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 20px rgba(220,53,69,0.2);
            }

            .insurance-icon {
                font-size: 3rem;
                margin: 1.5rem 0;
                color: var(--primary-color);
            }

            .insurance-title {
                color: #202124;
                font-size: 1.5rem;
                font-weight: 600;
                margin-bottom: 1rem;
            }

            .insurance-type-badge {
                display: inline-block;
                padding: 0.4rem 1rem;
                border-radius: 20px;
                font-size: 0.875rem;
                font-weight: 500;
                margin-bottom: 1rem;
            }

            .insurance-type-badge.saving {
                background-color: #fce8e6;
                color: #c82333;
            }

            .insurance-type-badge.loan {
                background-color: #fce8e6;
                color: #c82333;
            }

            .insurance-type-badge.deposit {
                background-color: #fce8e6;
                color: #c82333;
            }

            .insurance-type-badge.withdrawal {
                background-color: #fce8e6;
                color: #c82333;
            }

            .insurance-description {
                color: var(--secondary-color);
                margin-bottom: 1.5rem;
                line-height: 1.6;
            }

            .btn-learn-more {
                background-color: var(--primary-color);
                color: white;
                border: none;
                padding: 0.5rem 1.5rem;
                border-radius: 25px;
                font-weight: 500;
                transition: background-color 0.3s;
            }

            .btn-learn-more:hover {
                background-color: #c82333;
                color: white;
            }

            .insurance-inactive {
                opacity: 0.7;
            }

            .insurance-card .card-body {
                padding: 1.5rem;
                display: flex;
                flex-direction: column;
                height: 100%;
            }

            .insurance-footer {
                margin-top: auto;
                padding-top: 1rem;
            }
            nav {
                text-align: center;
                margin: 20px 0;
            }

            nav ul {
                list-style-type: none;
                padding: 0;
                display: inline-block;
                background: #e74c3c; /* Màu đỏ chủ đạo */
                padding: 12px 25px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(231, 76, 60, 0.4);
            }

            nav ul li {
                display: inline;
                margin: 0 15px;
            }

            nav ul li a {
                text-decoration: none;
                color: white;
                font-size: 18px;
                font-weight: bold;
                padding: 10px 15px;
                transition: 0.3s ease-in-out;
                border-radius: 5px;
                position: relative;
            }

            nav ul li a::after {
                content: "";
                display: block;
                width: 0;
                height: 3px;
                background: white;
                position: absolute;
                left: 50%;
                bottom: -5px;
                transition: width 0.3s ease-in-out, left 0.3s ease-in-out;
            }

            nav ul li a:hover::after {
                width: 100%;
                left: 0;
            }

            nav ul li a:hover {
                background: #c0392b; /* Màu đỏ đậm hơn */
                box-shadow: 0 3px 6px rgba(192, 57, 43, 0.5);
            }

            /* menu */
            /* Giao diện chính */
            .navigation_primary {
                background-color: white;
                padding: 10px 20px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .navigation_primary-wrapper {
                display: flex;
                justify-content: space-between;
                align-items: center;
                max-width: 1200px;
                margin: 0 auto;
            }

            /* Logo */
            .header-logo a {
                font-size: 22px;
                font-weight: bold;
                color: red;
                text-decoration: none;
            }

            /* Menu chính */
            .navigation_primary-menu {
                display: flex;
                gap: 20px;
            }

            .navigation_primary-item a {
                text-decoration: none;
                color: black;
                font-weight: 500;
                padding: 8px 12px;
                transition: color 0.3s ease-in-out;
            }

            .navigation_primary-item a:hover {
                color: red;
                text-decoration: underline;
            }

            /* Nút đăng nhập */
            .navigation_primary-actions a {
                background-color: red;
                color: white;
                padding: 8px 16px;
                border-radius: 5px;
                font-weight: bold;
                text-decoration: none;
            }

            .navigation_primary-actions a:hover {
                background-color: darkred;
            }
            
            .navigation_secondary-actions a {
                padding: 8px 16px;
                border-radius: 5px;
                font-weight: bold;
                text-decoration: red;
            }
                     
            .fin {
                color: red;
            }

            .bank {
                color: black;
            }



        </style>
    </head>
    <body>
<div class="navigation_primary">
            <div class="navigation_primary-wrapper">
                <!-- Logo -->
                <div class="header-logo">                 
                    <a href="home" class="banner_taital">
                        <span class="fin">FIN</span>
                        <span class="bank">BANK</span>                  
                    </a>
                </div>

                <!-- Menu -->
                <div class="navigation_primary-menu">
                    <div class="navigation_primary-item"> <a href="home">Trang chủ</a> </div>
                    <div class="navigation_primary-item"> <a href="depositSaving">Tiết kiệm</a> </div>
                    <div class="navigation_primary-item"> <a href="loanService">Vay</a> </div>
                    <div class="navigation_primary-item"> <a href="Insurance">Bảo hiểm</a> </div>
                    <div class="navigation_primary-item"> <a href="news">Tin tức</a> </div>
                </div>

                <!-- Nút đăng nhập -->
                <c:if test="${sessionScope.account == null}">
                    <div class="navigation_primary-actions">
                        <a href="login">Đăng nhập →</a>
                    </div>
                </c:if>
                <c:if test="${sessionScope.account != null}">
                    <c:if test="${sessionScope.account.role_id !=5}">     
                        <div class="navigation_secondary-actions">
                            <a>
                                Xin chào ${sessionScope.account.full_name}
                            </a>
                            <a href="logout">Đăng xuất</a>
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.account.role_id == 5}">
                        <div class="navigation_secondary-actions">
                            <a >Xin chào ${sessionScope.account.insurance_name}</a>                                          
                        <a href="logout">Đăng xuất</a>
                        </div>
                        </c:if>
                    </c:if>   
            </div>
        </div>
        <!-- Header Section -->
        <div class="insurance-header">
            <div class="container">
                <h1 class="display-4"><i class="bi bi-bank"></i> Bảo hiểm ngân hàng</h1>
                <p class="lead">Khám phá các loại bảo hiểm tài chính của chúng tôi</p>
            </div>
        </div>


       

        <!-- Main Content -->
        <div class="container">
            <!-- Services Grid -->
            <div class="row g-4">
                <c:forEach items="${ListInsurance}" var="i">
                    <div class=" col-md-4">
                        <div class="card-body text-center">
                        <div class="insurance-card ${i.status != 'active' ? 'insurance-inactive' : ''}">
                            <div class="card-body text-center">
                                <h3 class="insurance-title">${i.insurance_name}</h3>                               
                            </div>
                            <div class="insurance-footer">
                                <c:if test="${i.status == 'active'}">
                                    <a href="Insurance?action=details&insurance_id=${i.insurance_id}"
                                       class="btn btn-learn-more">
                                        <i class="bi bi-arrow-right-circle"></i> Xem Thông Tin
                                    </a>
                                </c:if>
                                <c:if test="${i.status != 'active'}">
                                    <button class="btn btn-secondary" disabled>
                                        <i class="bi bi-clock"></i> Hiện Đang Cập Nhật.
                                    </button>
                                </c:if>
                            </div>
                        </div>
                    </div>
                            </div>
                </c:forEach>
            </div>

        </div>

        <!-- No Services Message -->
        <c:if test="${empty ListInsurance}">
            <div class="alert alert-info text-center">
                <i class="bi bi-info-circle"></i> Không có bảo hiểm nào hoạt động thời điểm này.
            </div>
        </c:if>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
  
</html>
