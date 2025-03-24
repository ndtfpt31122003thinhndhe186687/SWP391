<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Mini Finance - Settings</title>

        <!-- CSS FILES -->      
        <link rel="preconnect" href="https://fonts.googleapis.com">

        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">

        <link href="css/bootstrap.min.css" rel="stylesheet">

        <link href="css/bootstrap-icons.css" rel="stylesheet">

        <link href="css/tooplate-mini-finance.css" rel="stylesheet">

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
                                <small>${sessionScope.account.customer_id}</small>
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
                        <a class="nav-link " aria-current="page" href="balanceCustomer">
                            <i class="bi-house-fill me-2"></i>
                            Tổng quan
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="wallet">
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
                        <a class="nav-link active" href="changeInfor">
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
                <h1 class="h2 mb-0 text-danger">Settings</h1>
            </div>

            <div class="row my-4">

                <div class="col-lg-7 col-12">
                    <div class="custom-block bg-white">
                        <ul class="nav nav-tabs" id="myTab" role="tablist">
                            <li class="nav-item" role="presentation">
                                <a class="nav-link" href="changeInfor" id="profile-tab" role="tab" aria-controls="profile-tab-pane" aria-selected="true">Profile</a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link active" href="changepass" id="password-tab" role="tab" aria-controls="password-tab-pane" aria-selected="false">Password</a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link" href="changenotification" id="notification-tab" role="tab" aria-controls="notification-tab-pane" aria-selected="false">Notification</a>
                            </li>
                        </ul>
                        <div class="tab-content" id="myTabContent">
                            <div class=" show" id="password-tab-pane" role="tabpanel" aria-labelledby="password-tab" tabindex="0">
                                <h6 class="mb-4 text-danger">Password</h6>
                                <form class="custom-form password-form" action="changepass" method="post" role="form">
                                    <input type="password" name="opass" id="password" class="form-control" placeholder="Current Password" required="">
                                    <input type="password" name="newpass" id="newpass" class="form-control" placeholder="New Password" required="">
                                    <c:if test="${not empty error}">
                                        <div class="text-danger">${error}</div>
                                    </c:if>

                                    <input type="password" name="confirmpass" id="confirmpass" class="form-control" placeholder="Confirm Password" required="">
                                    <c:if test="${not empty error}">
                                        <div class="text-danger">${error}</div>
                                    </c:if>

                                    <hr>
                                    <div class="d-flex">
                                        <button type="button" class="form-control me-3 text-bg-danger">Reset</button>
                                        <button type="submit" class="form-control ms-2 text-bg-danger">Update Password</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="notification-tab-pane" role="tabpanel" aria-labelledby="notification-tab" tabindex="0">
                            <h6 class="mb-4 text-danger">Notification</h6>

                            <form class="custom-form notification-form" action="#" method="post" role="form">

                                <div class="form-check form-switch d-flex mb-3 ps-0">
                                    <label class="form-check-label" for="flexSwitchCheckCheckedOne">Account activity</label>

                                    <input class="form-check-input ms-auto" type="checkbox" name="form-check-input" role="switch" id="flexSwitchCheckCheckedOne" checked>
                                </div>

                                <div class="form-check form-switch d-flex mb-3 ps-0">
                                    <label class="form-check-label" for="flexSwitchCheckCheckedTwo">Payment updated</label>

                                    <input class="form-check-input ms-auto" type="checkbox" name="form-check-input" role="switch" id="flexSwitchCheckCheckedTwo" checked>
                                </div>

                                <div class="d-flex mt-4">
                                    <button type="button" class="form-control me-3 text-bg-danger">
                                        Reset
                                    </button>

                                    <button type="submit" class="form-control ms-2 text-bg-danger">
                                        Update Password
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-5 col-12">
                <div class="custom-block custom-block-contact">
                    <h6 class="mb-4">Still can?t find what you looking for?</h6>

                    <p>
                        <strong>Call us:</strong>
                        <a href="tel: 305-240-9671" class="ms-2">
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