<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Thống kê</title>

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
                    <i class="bi-bank"></i>
                    Finbank
                </a>
            </div>

            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

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
                            Hồ sơ
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item" href="setting.html">
                            <i class="bi-gear me-2"></i>
                            Cài đặt
                        </a>
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
                        <a class="nav-link " aria-current="page" href="staff_management?status=all&sort=full_name&type=bankers&page=1&pageSize=2">
                            <i class="me-2"></i>
                            Quản lý nhân viên
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="service_management?type=services">
                            <i class="me-2"></i>
                            Quản lý dịch vụ
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="transaction_management">
                            <i class=" me-2"></i>
                            Quản lý giao dịch
                        </a>
                    </li>    

                    <li class="nav-item">
                        <a class="nav-link" href="insurance_management">
                            <i class=" me-2"></i>
                            Quản lý bảo hiểm
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="serviceprovider_management">
                            <i class=" me-2"></i>
                            Quản lý nhà cung cấp dịch vụ
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link active " href="statistic_management">
                            <i class="me-2"></i>
                            Thống kê
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="serviceTermManagement?serviceName=all&sort=all&page=1&pageSize=4">
                            <i class="me-2"></i>
                            Quản lý điều khoản
                        </a>
                    </li>
                    
                    <li class="nav-item">
                        <a class="nav-link " href="feedback_management">
                            <i class="me-2"></i>
                            Quản lý phản hồi
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="newsResponse?categoryId=0&sort=title&page=1&pageSize=4">
                            <i class="me-2"></i>
                            Kiểm duyệt tin tức 
                        </a>
                    </li>    
                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Bảng thống kê</h1>
            </div>

            <!-- Total Statistics Section -->
            <div class="row statistic-section">
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số khách hàng</div>
                        <div class="statistic-value">${totalCustomers}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số nhân viên</div>
                        <div class="statistic-value">${totalStaff}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Tổng số bên bảo hiểm </div>
                        <div class="statistic-value">${totalInsurance}</div>
                    </div>
                </div>              
            </div>

            <!-- Active Counts Section -->
            <div class="row statistic-section">
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Số khách hàng hoạt động</div>
                        <div class="statistic-value">${activeCustomers}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Số nhân viên hoạt động</div>
                        <div class="statistic-value">${activeStaff}</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="statistic-card">
                        <div class="statistic-title">Số dich vụ hoạt động </div>
                        <div class="statistic-value">${activeServices}</div>
                    </div>
                </div>
            </div>

            <!-- Distribution Statistics -->
            <div class="row statistic-section">
                <!-- Gender Distribution -->
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Phân bố giới tính</div>
                        <table class="table">
                            <tr>
                                <td>Khách hàng nam:</td>
                                <td class="statistic-value">${maleCustomers}</td>
                            </tr>
                            <tr>
                                <td>Khách hàng nữ:</td>
                                <td class="statistic-value">${femaleCustomers}</td>
                            </tr>
                        </table>
                    </div>
                </div>

                <!-- Card Type Distribution -->
                <div class="col-md-6">
                    <div class="statistic-card">
                        <div class="statistic-title">Loại thẻ</div>
                        <table class="table">
                            <tr>
                                <td>Thẻ tín dụng:</td>
                                <td class="statistic-value">${creditCards}</td>
                            </tr>
                            <tr>
                                <td>Thẻ ghi nợ:</td>
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
                        <div class="statistic-title">Trạng thái yêu cầu:</div>
                        <table class="table">
                            <tr>
                                <td>Yêu cầu đang chờ xử lý:</td>
                                <td class="statistic-value">${pendingRequests}</td>
                            </tr>
                            <tr>
                                <td>Yêu cầu đồng ý:</td>
                                <td class="statistic-value">${approvedRequests}</td>
                            </tr>
                            <tr>
                                <td>Yêu cầu từ chối:</td>
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