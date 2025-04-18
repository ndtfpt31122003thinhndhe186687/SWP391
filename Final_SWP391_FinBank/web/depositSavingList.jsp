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
                <a class="navbar-brand text-white" href="home">
                    <i class="bi-box"></i>
                    Finbank
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
                            <small class="text-danger">Thông báo</small>
                            <c:forEach items="${requestScope.listNotify}" var="n" begin="0" end="2">
                                <li class="notifications-block border-bottom border-danger pb-2 mb-2">
                                    <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                        <div class="notifications-icon-wrap bg-danger text-white">
                                            <i class="notifications-icon bi-check-circle-fill"></i>
                                        </div>
                                        <div>
                                            <span>${n.message}</span>
                                        </div>
                                    </a>
                                </li>
                            </c:forEach>
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
                        <a class="nav-link " aria-current="page" href="balanceCustomer">
                            <i class="bi-house-fill me-2"></i> Tổng quan
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="wallet">
                            <i class="bi-wallet me-2"></i> Ví của tôi
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="insuranceWallet">
                            <i class="bi-shield-check me-2"></i> Lịch sử bảo hiểm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="transferAmount">
                            <i class="bi-arrow-left-right me-2"></i> Chuyển tiền
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="viewprofile">
                            <i class="bi-person me-2"></i> Hồ sơ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="savingList">
                            <i class="bi-piggy-bank me-2"></i> Sổ tiết kiệm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="loanList">
                            <i class="bi-bank me-2"></i> Vay
                        </a>
                    </li>
                    <c:if test="${sessionScope.account.role_id==6}">
                        <c:if test="${sessionScope.account.card_type == 'credit' && sessionScope.account.credit_limit == 0 }">
                            <li class="nav-item">
                                <a class="nav-link" href="registerCreditCard">
                                    <i class="bi-credit-card me-2"></i> Đăng Ký Thẻ Tín Dụng
                                </a>
                            </li>
                        </c:if>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link" href="notificationsList">
                            <i class="bi-bell me-2"></i> Thông báo
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="CustomerInsuranceList">
                            <i class="bi-shield-lock me-2"></i> Bảo hiểm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="changeInfor">
                            <i class="bi-gear me-2"></i> Cài đặt
                        </a>
                    </li>
                    <li class="nav-item border-top mt-auto pt-2">
                        <a class="nav-link" href="logout">
                            <i class="bi-box-arrow-left me-2"></i> Đăng xuất
                        </a>
                    </li>
                        </ul>
                    </div>
                </nav>

                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger"> Sổ tiết kiệm </h1>    
                    </div>
                    <div class="custom-block custom-block-balance">
                        <small>Tổng số dư tiết kiệm</small>
                        <h2 class="mt-2 mb-3">
                            <fmt:setLocale value="vi_VN" />
                            <fmt:formatNumber value="${totalSavings}" type="currency" currencySymbol="VND" groupingUsed="true" />
                        </h2>
                        <div class="savings-list">
                            <c:forEach var="saving" items="${savingsList}">
                                <div class="savings-item">
                                    <p><strong>Deposit ID:</strong> 
                                        <a href="#" class="deposit-id" onclick="toggleDetails('${saving.savings_id}')">Sổ tiết kiệm thứ ${saving.savings_id}</a>
                                    </p>
                                    <div id="${saving.savings_id}" class="savings-details" style="display: none;">
                                        <div class="savings-table">
                                            <div class="row"><span class="label">Số tiền đã gửi:</span><span class="value"><fmt:formatNumber value="${saving.amount}" type="currency" currencySymbol="VND" groupingUsed="true" /></span></div>
                                            <div class="row"><span class="label">Ngày gửi:</span><span class="value"> <fmt:formatDate value="${saving.start_date}" pattern="dd-MM-yyyy" /></span></div>
                                            <div class="row"><span class="label">Kỳ hạn:</span><span class="value">${saving.duration} tháng</span></div>
                                            <fmt:parseDate var="savingStartDate" value="${saving.start_date}" pattern="yyyy-MM-dd HH:mm:ss" />
                                            <fmt:parseDate var="vipStartDate" value="${requestScope.vipStartDate}" pattern="yyyy-MM-dd HH:mm:ss" />

                                            <c:choose>
                                                <c:when test="${savingStartDate.time >= vipStartDate.time}">
                                                    <div class="row"><span class="label">Lãi suất:</span><span class="value">${saving.interest_rate + savingsRateBonus}%</span></div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="row"><span class="label">Lãi suất:</span><span class="value">${saving.interest_rate}%</span></div>
                                                </c:otherwise>
                                            </c:choose>



                                            <div class="row"><span class="label">Ngày đáo hạn:</span><span class="value"><fmt:formatDate value="${saving.end_date}" pattern="dd-MM-yyyy" /></span></div>
                                            <div class="row accrued-interest"><span class="label">Lãi suất tích lũy dự kiến nhận về:</span><span class="value"><fmt:formatNumber value="${accruedInterestMap[saving.savings_id]}" type="currency" currencySymbol="VND" groupingUsed="true" /></span></div>
                                        </div>
                                        <form action="withdrawSaving" method="post" onsubmit="return confirmWithdraw(event, '${saving.savings_id}', '${saving.end_date}')">
                                            <input type="hidden" name="saving_id" value="${saving.savings_id}">
                                            <button class="btn btn-danger btn-sm">Rút tiền</button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>

                        </div>
                    </div>
            </div>

            <script>
                function toggleDetails(id) {
                    var details = document.getElementById(id);

                    if (details.style.display === "none") {
                        details.style.display = "block";
                    } else {
                        details.style.display = "none";
                    }
                }

                function confirmWithdraw(event, savingId, endDate) {
                    var today = new Date();
                    var maturityDate = new Date(endDate);

                    if (today < maturityDate) {
                        var confirmAction = confirm("Gói tiết kiệm này chưa đến kỳ hạn. Bạn có chắc chắn muốn rút tiền không?");
                        if (!confirmAction) {
                            event.preventDefault();
                            return false;
                        }
                    }
                    return true;
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