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
            .custom-block-profile {
                border-radius: 10px;
                padding: 15px;
                background: #f8f9fa;
            }

            /* Container của từng thông báo */
            .notification-item {
                display: flex;
                align-items: center;
                padding: 12px;
                background: #fff;
                border-radius: 8px;
                margin-bottom: 10px;
                border-left: 5px solid #dc3545; /* Viền trái màu đỏ */
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
                transition: 0.3s;
            }

            .notification-item:hover {
                background: #f1f1f1;
            }



            /* Nội dung thông báo */
            .notification-content {
                flex: 1;
            }

            .notification-message {
                font-size: 16px;
                font-weight: 600;
                color: #333;
                margin: 0;
            }

            .notification-time {
                font-size: 14px;
                color: #777;
                margin-top: 3px;
            }
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 20px;
                gap: 5px;
            }

            .pagination a, .pagination span {
                display: inline-block;
                padding: 8px 12px;
                font-size: 16px;
                text-decoration: none;
                border: 2px solid #dc3545;
                color: #dc3545;
                border-radius: 5px;
                transition: all 0.3s ease;
                background-color: white;
            }

            .pagination a:hover {
                background-color: #dc3545;
                color: white;
            }

            .current-page {
                display: inline-block;
                padding: 8px 12px;
                font-size: 16px;
                font-weight: bold;
                background-color: #ffecec;
                color: #b71c1c;
                border-radius: 5px;
                border: 2px solid #b71c1c;
            }

            .badge-new {
                background-color: #ffcccc !important; /* Màu đỏ nhạt */
                color: #990000 !important; /* Màu chữ đỏ đậm hơn */
                font-weight: normal;
                padding: 4px 8px;
                border-radius: 5px;
                font-size: 12px;
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
                            <small class="text-danger">Notifications</small>
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
                                <a class="nav-link" href="savingList">
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
                                <a class="nav-link active" href="notificationsList">
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
            </div>
        </div>
        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">

            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Thông báo</h1>
            </div>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <form action="notificationsList" method="get">
                <div class="row g-3 row-cols-auto align-items-end">
                    <div class="col">
                        <label for="notificationType" class="form-label">Loại thông báo</label>
                        <select name="type" id="notificationType" class="form-select">
                            <option value="">Tất cả</option>
                            <c:forEach items="${requestScope.listTypes}" var="type">
                                <option value="${type}" ${param.type == type ? 'selected' : ''}>${type}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col">
                        <label for="status" class="form-label">Trạng thái</label>
                        <select name="status" id="status" class="form-select">
                            <option value="">Tất cả</option>
                            <option value="unread" ${param.status == 'unread' ? 'selected' : ''}>Chưa đọc</option>
                            <option value="read" ${param.status == 'read' ? 'selected' : ''}>Đã đọc</option>
                        </select>
                    </div>

                    <div class="col">
                        <label for="fromDate" class="form-label">Từ ngày</label>
                        <input type="text" value="${param.fromDate}" id="fromDate" name="fromDate" class="form-control" placeholder="dd-MM-yyyy">
                    </div>

                    <div class="col">
                        <label for="toDate" class="form-label">Đến ngày</label>
                        <input type="text" value="${param.toDate}" id="toDate" name="toDate" class="form-control" placeholder="dd-MM-yyyy">
                    </div>

                    <div class="col">
                        <label for="limit" class="form-label">Hiển thị</label>
                        <select name="pageSize" id="limit" class="form-select">
                            <option value="3" ${param.pageSize == '3' ? 'selected' : ''}>3</option>
                            <option value="6" ${param.pageSize == '6' ? 'selected' : ''}>6</option>
                            <option value="9" ${param.pageSize == '9' ? 'selected' : ''}>9</option>
                        </select>
                    </div>

                    <div class="col">
                        <button type="submit" class="btn btn-danger mt-4">Lọc</button>
                    </div>
                </div>
            </form>



            <div class="row my-4">
                <div class="col-lg-12 col-12">
                    <div class="custom-block bg-white">
                        <h6 class="mb-4 text-danger">Thông báo gần đây</h6>
                        <div class="custom-block-profile bg-light p-3 mb-4">
                            <c:forEach items="${requestScope.listNotify}" var="n">
                                <div class="notification-item d-flex align-items-center">
                                    <div class="notification-content">
                                        <a href="readNotification?id=${n.notification_id}" class="${n.is_read == 'unread' ? 'fw-bold text-danger' : 'text-muted'}">
                                            <span class="notification-message">${n.message}</span>
                                        </a>
                                        <c:if test="${n.is_read == 'unread'}">
                                            <span class="badge badge-new ms-2">Mới</span>
                                        </c:if>
                                        <p class="notification-time"><fmt:formatDate value="${n.created_at}" pattern="dd-MM-yyyy"/></p>
                                    </div>
                                </div>                           
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="pagination">
                <c:forEach begin="1" end="${totalPage}" var="i">
                    <c:choose>
                        <c:when test="${i == page}">
                            <span class="current-page">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="notificationsList?type=${type}&status=${status}&fromDate=${fromDate}&toDate=${toDate}&pageSize=${pageSize}&page=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>


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