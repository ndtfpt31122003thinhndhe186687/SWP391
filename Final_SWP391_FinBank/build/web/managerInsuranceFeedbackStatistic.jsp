<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

        <style>
            .statistic-card {
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                padding: 20px;
                margin-bottom: 20px;
            }
            .statistic-title {
                color: #333;
                font-size: 18px;
                margin-bottom: 10px;
            }
            .statistic-value {
                color: #007bff;
                font-size: 24px;
                font-weight: bold;
            }
            .statistic-section {
                margin-bottom: 30px;
            }


        </style>


    </head>
    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="home">
                    <i class="bi-box"></i>
                    Mini Finance
                </a>
            </div>

            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="searchByPolicyName" method="post" role="form">
                <input class="form-control bg-white text-dark" name="search_policy_name" type="text" placeholder="Search" aria-label="Search">
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
                        <a class="nav-link " href="sortInsurancePolicy?sortInsurancePolicy=none&status=all&quantity=5&offset=1">
                            <i class="me-2"></i>
                            Quản lý chính sách bảo hiểm
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="sortInsuranceTerm?sortInsuranceTerm=none&status=all&quantity=5&offset=1">
                            <i class="me-2"></i>
                            Quản lý điều khoản bảo hiểm
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="filterInsuranceCustomer?gender=all&quantity=5&offset=1">
                            <i class="me-2"></i>
                            Quản lý khách hàng đã mua bảo hiểm
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="sortInsuranceContract?sortInsuranceContract=none&status=all&quantity=5&offset=1">
                            <i class=" me-2"></i>
                            Quản lý hợp đồng bảo hiểm
                        </a>
                    </li>                   

                    <li class="nav-item">
                        <a class="nav-link " href="sortInsuranceTransaction?sortInsuranceTransaction=none&transaction_type=all&quantity=5&offset=1">
                            <i class="me-2"></i>
                            Quản lý giao dịch bảo hiểm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="managerStatisticInsurance?${account.insurance_id}">
                            <i class="me-2"></i>
                            Quản lý thống kê của bảo hiểm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="ManagerInsuranceFeedback">
                            <i class="me-2"></i>
                            Quản lý phản hồi bảo hiểm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="ManagerStatisticFeedbackInsurance">
                            <i class="me-2"></i>
                            Quản lý thống kê phản hồi bảo hiểm
                        </a>
                    </li>

                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Quản lý thống kê phản hồi</h1>
            </div>

            <!-- Tabs choose staff -->


            <!-- View list staff -->

            <div class="row statistic-section">
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số phản hồi</div>
                        <div class="statistic-value">${totalFeedback}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Trung bình số sao</div>
                        <div class="statistic-value">${totalRate}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số phản hồi được đánh giá 5 sao</div>
                        <div class="statistic-value">${totalRate5}</div>
                    </div>
                </div>


            </div>

            <!-- Active Counts Section -->
            <div class="row statistic-section">
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số phản hồi được đánh giá 4 sao</div>
                        <div class="statistic-value">${totalRate4}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số phản hồi được đánh giá 3 sao</div>
                        <div class="statistic-value">${totalRate3}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số phản hồi được đánh giá 2 sao</div>
                        <div class="statistic-value">${totalRate2}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số phản hồi được đánh giá 1 sao</div>
                        <div class="statistic-value">${totalRate1}</div>
                    </div>
                </div>
            </div>

            <div class="row statistic-section">
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Chính sách được đánh giá cao nhất</div>
                        <div class="statistic-value">${policyHighest.policy_name}</div>
                        <div class="statistic-title">Điểm đánh giá trung bình</div>
                        <div class="statistic-value">${policyHighest.avg_rating}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Chính sách được đánh giá thấp nhất</div>
                        <div class="statistic-value">${policyLowest.policy_name}</div>
                        <div class="statistic-title">Điểm đánh giá trung bình</div>
                        <div class="statistic-value">${policyLowest.avg_rating}</div>
                    </div>
                </div>               
            </div>
            <div class="row statistic-section">
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Chính sách được đánh giá 5 sao nhiều nhất</div>
                        <div class="statistic-value">${policyHighestRate5.policy_name}</div>
                        <div class="statistic-title">Số lần được đánh giá</div>
                        <div class="statistic-value">${policyHighestRate5.avg_rating}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Chính sách được đánh giá 5 sao ít nhất</div>
                        <div class="statistic-value">${policyLowestRate5.policy_name}</div>
                        <div class="statistic-title">Số lần được đánh giá</div>
                        <div class="statistic-value">${policyLowestRate5.avg_rating}</div>
                    </div>
                </div>               
            </div>
            <div class="row statistic-section">
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Chính sách được đánh giá 1 sao nhiều nhất</div>
                        <div class="statistic-value">${policyHighestRate1.policy_name}</div>
                        <div class="statistic-title">Số lần được đánh giá</div>
                        <div class="statistic-value">${policyHighestRate1.avg_rating}</div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Chính sách được đánh giá 1 sao ít nhất</div>
                        <div class="statistic-value">${policyLowestRate1.policy_name}</div>
                        <div class="statistic-title">Số lần được đánh giá</div>
                        <div class="statistic-value">${policyLowestRate1.avg_rating}</div>
                    </div>
                </div>               
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