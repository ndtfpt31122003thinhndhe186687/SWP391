<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta name="description" content="">
        <meta name="author" content="">

        <title>Mini Finance - Profile Page</title>

        <!-- CSS FILES -->      
        <link rel="preconnect" href="https://fonts.googleapis.com">

        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">

        <link href="css/bootstrap.min.css" rel="stylesheet">

        <link href="css/bootstrap-icons.css" rel="stylesheet">

        <link href="css/tooplate-mini-finance.css" rel="stylesheet">
        <!--
        
        Tooplate 2135 Mini Finance
        
        https://www.tooplate.com/view/2135-mini-finance
        
        Bootstrap 5 Dashboard Admin Template
        
        -->
        <style>
            .savings-item {
                background: #fff;
                padding: 15px;
                border-radius: 8px;
                box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.1);
                margin-bottom: 10px;
                text-align: center; /* Căn giữa nội dung */
            }

            .deposit-id {
                color: #007bff;
                cursor: pointer;
                text-decoration: underline;
            }

            .savings-table {
                display: flex;
                flex-direction: column;
                align-items: center; /* Căn giữa nội dung */
                gap: 10px;
            }

            .row {
                display: flex;
                justify-content: space-between; /* Căn cách tiêu đề và giá trị */
                width: 100%; /* Giới hạn chiều rộng */
                padding: 5px 0;
                border-bottom: 1px solid #ddd;
            }

            .label {
                font-weight: bold;
                flex: 1;
                text-align: left;
            }

            .value {
                flex: 1;
                text-align: right;
            }

            .accrued-interest {
                font-size: 1.2rem;
                font-weight: bold;
                color: #28a745;
            }
            .deposit-id {
                color: black;
                cursor: pointer;
                text-decoration: none; /* Loại bỏ gạch chân */
            }

            .deposit-id:hover {
                text-decoration: underline; /* Chỉ hiện gạch chân khi di chuột vào */
            }




        </style>
    </head>

    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="index.html">
                    <i class="bi-box"></i>
                    Mini Finance
                </a>
            </div>

            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="#" method="get" role="form">
                <input class="form-control bg-white text-dark" name="search" type="text" placeholder="Search" aria-label="Search">
            </form>

            <div class="navbar-nav me-lg-2">
                <div class="nav-item text-nowrap d-flex align-items-center">
                    <div class="dropdown ps-3">
                        <a class="nav-link dropdown-toggle text-center text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" id="navbarLightDropdownMenuLink">
                            <i class="bi-bell"></i>
                            <span class="position-absolute start-100 translate-middle p-1 bg-white border border-danger rounded-circle">
                                <span class="visually-hidden">New alerts</span>
                            </span>
                        </a>

                        <ul class="dropdown-menu dropdown-menu-lg-end notifications-block-wrap bg-white text-danger shadow" aria-labelledby="navbarLightDropdownMenuLink">
                            <small class="text-danger">Notifications</small>

                            <li class="notifications-block border-bottom border-danger pb-2 mb-2">
                                <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                    <div class="notifications-icon-wrap bg-danger text-white">
                                        <i class="notifications-icon bi-check-circle-fill"></i>
                                    </div>
                                    <div>
                                        <span>Your account has been created successfully.</span>
                                        <p>12 days ago</p>
                                    </div>
                                </a>
                            </li>

                            <li class="notifications-block border-bottom border-danger pb-2 mb-2">
                                <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                    <div class="notifications-icon-wrap bg-danger text-white">
                                        <i class="notifications-icon bi-folder"></i>
                                    </div>
                                    <div>
                                        <span>Please check. We have sent a Daily report.</span>
                                        <p>10 days ago</p>
                                    </div>
                                </a>
                            </li>

                            <li class="notifications-block">
                                <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                    <div class="notifications-icon-wrap bg-danger text-white">
                                        <i class="notifications-icon bi-question-circle"></i>
                                    </div>
                                    <div>
                                        <span>Account verification failed.</span>
                                        <p>1 hour ago</p>
                                    </div>
                                </a>
                            </li>
                        </ul>

                    </div>
                </div>
            </div>

            <div class="dropdown px-3">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid" alt="">
                </a>
                <ul class="dropdown-menu bg-white shadow">
                    <li>
                        <div class="dropdown-menu-profile-thumb d-flex">
                            <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid me-3" alt="">

                            <div class="d-flex flex-column">
                                <small>${sessionScope.account.full_name}</small>
                                <small>${sessionScope.account.email}</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <a class="dropdown-item" href="profile.html">
                            <i class="bi-person me-2"></i>
                            Profile
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item" href="setting.html">
                            <i class="bi-gear me-2"></i>
                            Settings
                        </a>
                    </li>
                    <li class="border-top mt-3 pt-2 mx-4">
                        <a class="dropdown-item ms-0 me-0" href="logout">
                            <i class="bi-box-arrow-left me-2"></i>
                            Logout
                        </a>
                    </li>
                </ul>
            </div>
        </header>

        <div class="container-fluid">
            <div class="row">
                <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
                    <div class="position-sticky py-4 px-3 sidebar-sticky">
                        <ul class="nav flex-column h-100">
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="index.html">
                                    <i class="bi-house-fill me-2"></i>
                                    Tổng quan                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="wallet.html">
                                    <i class="bi-wallet me-2"></i>
                                    Ví của tôi
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="viewprofile">
                                    <i class="bi-person me-2"></i>
                                    Hồ sơ
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link " href="savingList">
                                    <i class="bi-person me-2"></i>
                                    Sổ tiết kiệm 
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link active" href="loanList">
                                    <i class="bi-person me-2"></i>
                                    Vay 
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="changeInfor">
                                    <i class="bi-gear me-2"></i>
                                    Cài đặt
                                </a>
                            </li>

                            <li class="nav-item border-top mt-auto pt-2">
                                <a class="nav-link" href="logout">
                                    <i class="bi-box-arrow-left me-2"></i>
                                    Đăng xuất
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger"> Sổ Vay </h1>    
                    </div>
                    <div class="custom-block custom-block-balance">
                        <small>Tổng số tiền vay</small>
                        <h2 class="mt-2 mb-3"><fmt:formatNumber value="${totalLoan}" pattern="#,##0.00"/>VND</h2>
                        <div class="savings-list">
                            <c:forEach var="loan" items="${list}">
                                <div class="savings-item">                              
                                    <div id="${loan.loan_id}" class="savings-details" >
                                        <div class="savings-table">
                                            <div class="row"><span class="label">STT:</span>
                                                <span class="value">${loan.loan_payments_id}</span>
                                            </div>
                                            <div class="row"><span class="label">Số tiền gốc:</span>
                                                <span class="value"><fmt:formatNumber value="${loan.principal_amount}" pattern="#,##0.00" />VND</span>
                                            </div>
                                            <div class="row"><span class="label">Số tiền lãi:</span>
                                                <span class="value"><fmt:formatNumber value="${loan.interest_amount}" pattern="#,##0.00" />VND</span>
                                            </div>
                                            <div class="row"><span class="label">Tổng gốc + lãi:</span>
                                                <span class="value"><fmt:formatNumber value="${loan.payment_amount}" pattern="#,##0.00" />VND</span>
                                            </div>
                                            <div class="row"><span class="label">Số gốc còn lại:</span>
                                                <span class="value"><fmt:formatNumber value="${loan.remaining_amount}" pattern="#,##0.00" />VND</span>
                                            </div>
                                            <div class="row"><span class="label">Ngày trả nợ:</span>
                                                <span class="value"> <fmt:formatDate value="${loan.payment_date}" pattern="dd-MM-yyyy" /></span>
                                            </div>
                                            <div class="row"><span class="label">Trạng thái:</span>
                                                <span class="value">${loan.payment_status}</span>                                                                   
                                            </div>
                                            <form action="loanList?id=${loan.loan_id}" method="post">                     
                                                <button class="btn btn-danger btn-sm">Trả nợ</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>



                        </div>
                    </div>

                    <script>
                        function toggleDetailsLoan(id) {
                            var details = document.getElementById(id);

                            if (details.style.display === "none") {
                                details.style.display = "block";
                            } else {
                                details.style.display = "none";
                            }
                        }
                    </script>


                    <footer class="site-footer">
                        <div class="container">
                            <div class="row">

                                <div class="col-lg-12 col-12">
                                    <p class="copyright-text">Copyright © Mini Finance 2048 
                                        - Design: <a rel="sponsored" href="https://www.tooplate.com" target="_blank">Tooplate</a></p>
                                </div>

                            </div>
                        </div>
                    </footer>
            </div>

        </main>
    </div>
</div>

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>