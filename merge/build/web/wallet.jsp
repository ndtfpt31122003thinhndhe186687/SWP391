<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Mini Finance - Wallet</title>

        <!-- CSS FILES -->      
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="css/tooplate-mini-finance.css" rel="stylesheet">

        <style>
            .transaction-date {
                font-size: 1.25rem;
                font-weight: bold;
                padding: 1rem 0;
                border-bottom: 1px solid #dee2e6;
                margin-bottom: 1rem;
            }

            .transaction-item {
                padding: 1rem;
                margin-bottom: 1rem;
                border-radius: 0.5rem;
                background-color: #fff;
                box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            }

            .transaction-time {
                color: #6c757d;
                font-size: 0.9rem;
            }

            .transaction-amount {
                font-weight: bold;
                font-size: 1.1rem;
            }

            .transaction-description {
                color: #6c757d;
                margin-top: 0.5rem;
            }

            .transaction-amount.deposit {
                color: #28a745 !important;
            }

            .transaction-amount.withdrawal {
                color: #dc3545 !important;
            }
            .pagination {
                display: flex;
                justify-content: center;
                list-style: none;
                padding: 0;
            }
            .pagination li {
                margin: 0 5px;
            }
            .pagination a {
                color: #dc3545;
                text-decoration: none;
                padding: 5px 10px;
                border: 1px solid #dc3545;
                border-radius: 5px;
            }
            .pagination .active a {
                background-color: #dc3545;
                color: white;
            }
        </style>
    </head>
    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="home">
                    <i class="bi-box"></i>
                    Finbank                </a>
            </div>

            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="" method="get" role="form">
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
                        <a class="dropdown-item" href="viewprofile">
                            <i class="bi-person me-2"></i>
                            Hồ sơ                       
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item" href="changeInfor">
                            <i class="bi-gear me-2"></i>
                            Cài đặt
                        </a>
                    </li>               
                    </li>
                    <li class="border-top mt-3 pt-2 mx-4">
                        <a class="dropdown-item ms-0 me-0" href="logout">
                            <i class="bi-box-arrow-left me-2"></i>
                            Đăng xuất
                        </a>
                    </li>
                </ul>
            </div>

        </div>
    </div>
</header>

<div class="container-fluid">
    <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
            <div class="position-sticky py-4 px-3 sidebar-sticky">
                <ul class="nav flex-column h-100">
                    <li class="nav-item">
                        <a class="nav-link " aria-current="page" href="balanceCustomer">
                            <i class="bi-house-fill me-2"></i>
                            Tổng quan
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link active" href="wallet">
                            <i class="bi-wallet me-2"></i>
                            Ví của tôi
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="insuranceWallet">
                            <i class="bi-shield-check me-2"></i>
                            Lịch sử bảo hiểm
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="transferAmount">
                            <i class="bi-arrow-left-right me-2"></i>
                            Chuyển tiền
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="viewprofile">
                            <i class="bi-person me-2"></i>
                            Hồ sơ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="savingList">
                            <i class="bi-person me-2"></i>
                            Sổ tiết kiệm 
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="loanList">
                            <i class="bi-person me-2"></i>
                            Vay 
                        </a>
                    </li>


                    <c:if test="${sessionScope.account.role_id==6}">
                        <c:if test="${sessionScope.account.card_type == 'credit' 
                                      && sessionScope.account.credit_limit == 0 }">
                              <li class="nav-item">                                             

                                  <a class="nav-link" href="registerCreditCard">
                                      <i class="bi-person me-2"></i>
                                      Đăng Ký Thẻ Tín Dụng
                                  </a>                          
                              </li>
                        </c:if>  
                    </c:if>  
                    <li class="nav-item">
                        <a class="nav-link" href="notificationsList">
                            <i class="bi-person me-2"></i>
                            Thông báo 
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="CustomerInsuranceList">
                            <i class="bi-gear me-2"></i>
                            Bảo hiểm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="changeInfor">
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
                        <h1 class="h2 mb-0 text-danger">My Wallet</h1>
                    </div>

                    <div class="row my-4">
                        <div class="col-lg-12 col-12">
                            <div class="custom-block bg-white">
                                <h6 class="mb-4 text-danger">Người dùng</h6>
                                <div class="custom-block-profile mb-5">
                                    <p><strong>Mã người dùng :</strong> ${customer.customer_id}</p>
                                    <p><strong>Tên Người Dùng:</strong> ${customer.full_name}</p>
                                </div>

                                <h6 class="mb-4 text-danger">Lịch sử giao dịch</h6>
                                <div class="custom-block-profile bg-light p-3 mb-4">
                                    <form action="wallet" method="get" class="row g-3">
                                        <div class="col-md-4">
                                            <label for="startDate" class="form-label">Từ ngày:</label>
                                            <input type="text" class="form-control" id="startDate" name="startDate" value="${param.startDate}" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="endDate" class="form-label">Đến ngày:</label>
                                            <input type="text" class="form-control" id="endDate" name="endDate" value="${param.endDate}" required>
                                        </div>
                                        <div class="col-md-4 d-flex align-items-end">
                                            <button type="submit" class="btn btn-danger">Lọc</button>
                                        </div>
                                        <div>
                                            <c:if test="${not empty err}">
                                                <span class="text-danger">${requestScope.err}</span>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>

                                <!-- Transactions display -->
                                <c:if test="${not empty transactions}">
                                    <div class="transactions-container">
                                        <c:set var="currentDate" value="" />
                                        <c:forEach var="transaction" items="${transactions}">
                                            <fmt:formatDate value="${transaction.transaction_date}" pattern="dd/MM/yyyy" var="transactionDate" />
                                            <c:if test="${currentDate ne transactionDate}">
                                                <div class="transaction-date">${transactionDate}</div>
                                                <c:set var="currentDate" value="${transactionDate}" />
                                            </c:if>

                                            <div class="transaction-item">
                                                <div class="d-flex justify-content-between align-items-start">
                                                    <div>
                                                        <div class="transaction-description">
                                                            <c:choose>
                                                                <c:when test="${transaction.transaction_type eq 'withdrawal'}">
                                                                    <i class="bi bi-arrow-right-circle text-danger"></i> Chuyển tiền
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <i class="bi bi-arrow-down-circle text-success"></i> Nhận tiền
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div>${customer.full_name}</div>
                                                        <div class="transaction-description">
                                                            Giao dịch - Mã giao dịch/ Trace ${transaction.transaction_id}
                                                        </div>
                                                    </div>
                                                    <div class="text-end">
                                                        <fmt:setLocale value="vi_VN" />
                                                        <div class="text-end">
                                                            <div class="transaction-amount ${transaction.transaction_type}">
                                                                <c:choose>
                                                                    <c:when test="${transaction.transaction_type eq 'withdrawal'}">
                                                                        - <fmt:formatNumber value="${Math.abs(transaction.amount)}" type="number" groupingUsed="true"  /> VND
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        + <fmt:formatNumber value="${Math.abs(transaction.amount)}" type="number" groupingUsed="true" /> VND
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <div class="transaction-time">
                                                            <fmt:formatDate value="${transaction.transaction_date}" pattern="" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <!-- Nút Xuất PDF -->
                                    <div class="text-center mt-3">
                                        <form action="wallet" method="post">
                                            <input type="hidden" name="startDate" value="${param.startDate}">
                                            <input type="hidden" name="endDate" value="${param.endDate}">
                                            <input type="hidden" name="action" value="exportPDF">
                                            <button type="submit" class="btn btn-primary">Xuất PDF</button>
                                        </form>
                                    </div>

                                    <div class="pagination">
                                        <c:if test="${currentPage > 1}">
                                            <li><a href="wallet?page=${currentPage - 1}&startDate=${param.startDate}&endDate=${param.endDate}">« Previous</a></li>
                                            </c:if>
                                            <c:forEach var="i" begin="1" end="${totalPages}">
                                            <li class="${i == currentPage ? 'active' : ''}">
                                                <a href="wallet?page=${i}&startDate=${param.startDate}&endDate=${param.endDate}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${currentPage < totalPages}">
                                            <li><a href="wallet?page=${currentPage + 1}&startDate=${param.startDate}&endDate=${param.endDate}">Next »</a></li>
                                            </c:if>
                                    </div>
                                </c:if>
                            </div>
                        </div>
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
                </main>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/custom.js"></script>
</body>
</html>