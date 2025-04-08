<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Mini Finance - Profile Page</title>
        <!-- CSS FILES -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="css/tooplate-mini-finance.css" rel="stylesheet">
    </head>
    <body>
        <fmt:setLocale value="vi_VN"/>
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
                            <img src="imageCustomer/${customer.profile_picture}" class="profile-image img-fluid me-3" alt="">

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

        </div>
    </div>
</header>

<div class="container-fluid">
    <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
            <div class="position-sticky py-4 px-3 sidebar-sticky">
                <ul class="nav flex-column h-100">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="balanceCustomer">
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
                        <a class="nav-link active" href="viewprofile">
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
                <h1 class="h2 mb-0 text-danger">Profile</h1>
            </div>
            <% 
             String successMessage = (String) session.getAttribute("successMessage");
             if (successMessage != null) { 
            %>
            <div class="alert alert-success">
                <%= successMessage %>
            </div>
            <% session.removeAttribute("successMessage"); %>
            <% } %>

            <div class="row my-4">
                <div class="col-lg-7 col-12">
                    <div class="custom-block custom-block-profile">
                        <div class="row">
                            <div class="col-lg-12 col-12 mb-3">
                                <h6 class="text-danger">General</h6>
                            </div>

                            <div class="col-lg-3 col-12 mb-4 mb-lg-0">
                                <div class="custom-block-profile-image-wrap">
                                    <img src="imageCustomer/${customer.profile_picture}" class="custom-block-profile-image img-fluid" alt="Profile picture">
                                    <a href="changeInfor" class="bi-pencil-square custom-block-edit-icon"></a>
                                </div>
                            </div>

                            <div class="col-lg-9 col-12">
                                <p class="d-flex flex-wrap mb-2">
                                    <strong>Name:</strong>
                                    <span>${customer.full_name}</span>
                                </p>
                                <p class="d-flex flex-wrap mb-2">
                                    <strong>Email:</strong>
                                    <a href="mailto:${customer.email}">${customer.email}</a>
                                </p>
                                <p class="d-flex flex-wrap mb-2">
                                    <strong>Phone:</strong>
                                    <a href="tel:${customer.phone_number}">${customer.phone_number}</a>
                                </p>
                                <p class="d-flex flex-wrap mb-2">
                                    <strong>Birthday:</strong>
                                    <span><fmt:formatDate value="${customer.date_of_birth}" pattern="dd/MM/yyyy" /></span>
                                </p>
                                <p class="d-flex flex-wrap">
                                    <strong>Address:</strong>
                                    <span>${customer.address}</span>
                                </p>
                                <p class="d-flex flex-wrap">
                                    <strong>Gender:</strong>
                                    <span>${customer.gender}</span>
                                </p>
                                <p class="d-flex flex-wrap">
                                    <strong>Card Type:</strong>
                                    <span>${customer.card_type}</span>
                                </p>
                                <p class="d-flex flex-wrap">
                                    <strong>Amount:</strong>
                                    <span>
                                         <fmt:formatNumber value="${customer.amount}"  pattern="#,###" /> VND 
                                    </span>
                                </p>
                                <p class="d-flex flex-wrap">
                                    <strong>Credit Limit:</strong>
                                    <span>
                                        
                                        <fmt:formatNumber value="${customer.credit_limit}"  pattern="#,###" /> VND 
                                    </span>
                                </p>
                                <p class="d-flex flex-wrap">
                                    <strong>Status:</strong>
                                    <span>${customer.status}</span>
                                </p>                               
                        </div>
                        </div>
                    </div>
                </div>

                <div class="custom-block custom-block-profile bg-white">
                    <h6 class="mb-4 text-danger">Card Information</h6>
                    <p class="d-flex flex-wrap mb-2">
                        <strong>User ID:</strong>
                        <span>${customer.customer_id}</span>
                    </p>
                    <p class="d-flex flex-wrap mb-2">
                        <strong>Type:</strong>
                        <span>${customer.card_type}</span>
                    </p>
                    <p class="d-flex flex-wrap mb-2">
                        <strong>Created:</strong>
                        <span><fmt:formatDate value="${customer.created_at}" pattern="MMMM d, yyyy" /></span>
                    </p>
                </div>
            </div>

            <div class="col-lg-5 col-12">
                <div class="custom-block custom-block-contact">
                    <h6 class="mb-4">Still can't find what you looking for?</h6>
                    <p>
                        <strong>Call us:</strong>
                        <a href="tel:305-240-9671" class="ms-2">
                            (60)
                            305-240-9671
                        </a>
                    </p>
                    <a href="#" class="btn custom-btn custom-btn-bg-white mt-3">
                        Chat with us
                    </a>
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
</div>

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/custom.js"></script>
</body>
</html>