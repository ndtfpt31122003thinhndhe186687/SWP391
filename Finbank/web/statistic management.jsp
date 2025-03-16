<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <a class="navbar-brand text-white" href="">
                    <i class="bi-box"></i>
                    Mini Finance
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
                                <c:if test="${sessionScope.account.role_id==6}">
                                    <small>${sessionScope.account.customer_id}</small>
                                    <small>${sessionScope.account.email}</small>
                                </c:if>
                                <c:if test="${sessionScope.account.role_id==1}">
                                    <small>${sessionScope.account.staff_id}</small>
                                    <small>${sessionScope.account.email}</small>
                                </c:if> 
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
                        <a class="nav-link " aria-current="page" href="staff_management?status=all&sort=full_name&type=&page=1&pageSize=2">
                            <i class="me-2"></i>
                            Staff Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="service_management?type=services">
                            <i class="me-2"></i>
                            Service Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="transaction_management">
                            <i class=" me-2"></i>
                            Transaction Management
                        </a>
                    </li>                   

                    <li class="nav-item">
                        <a class="nav-link active" href="statistic_management">
                            <i class="me-2"></i>
                            Statistic Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="serviceTermManagement?serviceName=all&sort=all&page=1&pageSize=4">
                            <i class="me-2"></i>
                            Qu?n lí d?ch v?
                        </a>
                    </li>
                    
                       <li class="nav-item">
                        <a class="nav-link " href="newsResponse?categoryId=0&sort=title&page=1&pageSize=4">
                            <i class="me-2"></i>
                            Ki?m duy?t tin t?c
                        </a>
                    </li>

                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Statistics Dashboard</h1>
            </div>

            <!-- Total Statistics Section -->
            <div class="row statistic-section">
                <div class="col-md-3">
                    <div class="statistic-card">
                        <div class="statistic-title">Total Customers</div>
                        <div class="statistic-value">${totalCustomers}</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="statistic-card">
                        <div class="statistic-title">Total Staff</div>
                        <div class="statistic-value">${totalStaff}</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="statistic-card">
                        <div class="statistic-title">Total Insurance</div>
                        <div class="statistic-value">${totalInsurance}</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="statistic-card">
                        <div class="statistic-title">Total Feedback</div>
                        <div class="statistic-value">${totalFeedback}</div>
                    </div>
                </div>
            </div>

            <!-- Active Counts Section -->
            <div class="row statistic-section">
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Active Customers</div>
                        <div class="statistic-value">${activeCustomers}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Active Staff</div>
                        <div class="statistic-value">${activeStaff}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Active Services</div>
                        <div class="statistic-value">${activeServices}</div>
                    </div>
                </div>
            </div>

            <!-- Distribution Statistics -->
            <div class="row statistic-section">
                <!-- Gender Distribution -->
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Gender Distribution</div>
                        <table class="table">
                            <tr>
                                <td>Male Customers:</td>
                                <td class="statistic-value">${maleCustomers}</td>
                            </tr>
                            <tr>
                                <td>Female Customers:</td>
                                <td class="statistic-value">${femaleCustomers}</td>
                            </tr>
                        </table>
                    </div>
                </div>

                <!-- Card Type Distribution -->
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Card Type Distribution</div>
                        <table class="table">
                            <tr>
                                <td>Credit Cards:</td>
                                <td class="statistic-value">${creditCards}</td>
                            </tr>
                            <tr>
                                <td>Debit Cards:</td>
                                <td class="statistic-value">${debitCards}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Request Status Distribution -->
            <div class="row statistic-section">
                <div class="col-12">
                    <div class="statistic-card">
                        <div class="statistic-title">Request Status Distribution</div>
                        <table class="table">
                            <tr>
                                <td>Pending Requests:</td>
                                <td class="statistic-value">${pendingRequests}</td>
                            </tr>
                            <tr>
                                <td>Approved Requests:</td>
                                <td class="statistic-value">${approvedRequests}</td>
                            </tr>
                            <tr>
                                <td>Rejected Requests:</td>
                                <td class="statistic-value">${rejectedRequests}</td>
                            </tr>
                        </table>
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