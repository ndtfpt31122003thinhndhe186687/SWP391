<%-- 
    Document   : depositSaving
    Created on : Mar 17, 2025, 11:23:56 PM
    Author     : Acer Nitro Tiger
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banking Services</title>
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

            .service-header {
                background: linear-gradient(135deg, #dc3545, #c82333);
                color: white;
                padding: 1rem 0;
                margin-bottom: 2rem;
                text-align: center;
            }

            .service-card {
                background: white;
                border: none;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                transition: transform 0.3s, box-shadow 0.3s;
                margin-bottom: 2rem;
                border-radius: 12px;
                overflow: hidden;
                height: 100%;
            }

            .service-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 20px rgba(220,53,69,0.2);
            }

            .service-icon {
                font-size: 3rem;
                margin: 1.5rem 0;
                color: var(--primary-color);
            }

            .service-title {
                color: #202124;
                font-size: 1.5rem;
                font-weight: 600;
                margin-bottom: 1rem;
            }

            .service-type-badge {
                display: inline-block;
                padding: 0.4rem 1rem;
                border-radius: 20px;
                font-size: 0.875rem;
                font-weight: 500;
                margin-bottom: 1rem;
            }

            .service-type-badge.saving {
                background-color: #fce8e6;
                color: #c82333;
            }

            .service-type-badge.loan {
                background-color: #fce8e6;
                color: #c82333;
            }

            .service-type-badge.deposit {
                background-color: #fce8e6;
                color: #c82333;
            }

            .service-type-badge.withdrawal {
                background-color: #fce8e6;
                color: #c82333;
            }

            .service-description {
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

            .service-inactive {
                opacity: 0.7;
            }

            .service-card .card-body {
                padding: 1.5rem;
                display: flex;
                flex-direction: column;
                height: 100%;
            }

            .service-footer {
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
                    <div class="navigation_primary-item"> <a href="#">Chi tiêu</a> </div>
                    <div class="navigation_primary-item"> <a href="depositSaving">Tiết kiệm</a> </div>
                    <div class="navigation_primary-item"> <a href="#">Vay</a> </div>
                    <div class="navigation_primary-item"> <a href="#">Bảo hiểm</a> </div>
                    <div class="navigation_primary-item"> <a href="#">Thông tin mới</a> </div>
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




        <div class="service-header text-center py-5 bg-light">
            <div class="container">
                <h1 class="display-4"><i class="bi bi-piggy-bank"></i> Dịch Vụ Gửi Tiết Kiệm</h1>
                <p class="lead">Giúp bạn bảo vệ tài sản và sinh lời một cách an toàn</p>
            </div>
        </div>

        <div class="container mt-4">
            <div class="row">
                <div class="col-md-6">
                    <img src="images/saving_image.jpg" alt="Gửi tiết kiệm" class="img-fluid rounded">
                </div>
                <div class="col-md-6">
                    <h2>Lợi ích của Tiết Kiệm</h2>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><i class="bi bi-check-circle text-success"></i> Lãi suất hấp dẫn</li>
                        <li class="list-group-item"><i class="bi bi-check-circle text-success"></i> An toàn và bảo mật</li>
                        <li class="list-group-item"><i class="bi bi-check-circle text-success"></i> Kỳ hạn linh hoạt</li>
                        <li class="list-group-item"><i class="bi bi-check-circle text-success"></i> Rút tiền dễ dàng</li>
                    </ul>
                    <a href="sendSavingsApplication" class="btn btn-primary mt-3"><i class="bi bi-arrow-right-circle"></i> Đăng ký ngay</a>
                </div>
            </div>
        </div>

        <!-- Các Gói Tiết Kiệm -->
        <div class="container mt-5">
            <h2 class="text-center">📌 Các Gói Tiết Kiệm</h2>
            <div class="row">
                <c:forEach items="${requestScope.listS}" var="s">
                    <div class="col-md-4">
                        <div class="card text-center">
                            <div class="card-body">
                                <h5 class="card-title">${s.term_name}</h5>
                                <p class="card-text">Lãi suất: <strong>${s.interest_rate}%/năm</strong></p>
                                <a href="#" class="btn btn-outline-primary" 
                                   onclick="saveSelectedTerm('${s.serviceTerm_id}', '${s.term_name}', '${s.interest_rate}',
                                                   '${s.duration}', '${s.contract_terms}', '${s.min_deposit}')">                                 
                                    Chọn gói này</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <script>
                    function saveSelectedTerm(serviceTermId, termName, interestRate, duration, contractTerms, minDeposit) {
                        let selectedTerm = {
                            serviceTermId: serviceTermId,
                            termName: termName,
                            interestRate: interestRate,
                            duration: duration,
                            contractTerms: contractTerms,
                            minDeposit: minDeposit
                        };
                        localStorage.setItem("selectedTerm", JSON.stringify(selectedTerm)); 
                        window.location.href = "sendSavingsApplication"; 
                    }
                </script>

            </div>
        </div>
        <!-- Bảo Mật & An Toàn -->
        <div class="container mt-5">
            <h2 class="text-center">🔒 Bảo Mật & An Toàn</h2>
            <p class="text-center">Tài khoản tiết kiệm của bạn được bảo vệ với công nghệ bảo mật tiên tiến.</p>
            <div class="row text-center">
                <div class="col-md-4">
                    <i class="bi bi-shield-lock display-4 text-primary"></i>
                    <h5>Xác thực hai lớp</h5>
                </div>
                <div class="col-md-4">
                    <i class="bi bi-bank display-4 text-success"></i>
                    <h5>Bảo hiểm ngân hàng</h5>
                </div>
                <div class="col-md-4">
                    <i class="bi bi-file-lock display-4 text-danger"></i>
                    <h5>Dữ liệu mã hóa</h5>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
