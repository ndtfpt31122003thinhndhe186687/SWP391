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
            .filter-sort-bar {
                margin-bottom: 20px;
                display: flex;
                gap: 20px;
                align-items: center;
            }
            .filter-sort-bar label {
                font-weight: bold;
                color: #333;
            }
            .filter-dropdown {
                padding: 8px;
                border-radius: 5px;
                border: 1px solid #ddd;
                background-color: #fff;
                font-size: 14px;
            }
            .filter-dropdown:focus {
                border-color: #d32f2f;
                outline: none;
            }
            .filter-dropdown:hover {
                background-color: #f0f0f0;
            }
            .filter-dropdown option:hover {
                background-color: #d32f2f;
                color: white;
            }
            .table {
                border-collapse: collapse;
                width: 100%;
                background-color: #fff;
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }

            .table thead {
                background-color: #007bff;
                color: white;
            }

            .table thead th {
                padding: 12px;
                text-align: left;
                font-weight: bold;
            }

            .table tbody tr {
                transition: background 0.3s;
            }

            .table tbody tr:hover {
                background-color: #f1f1f1;
            }

            .table td, .table th {
                padding: 10px;
                border-bottom: 1px solid #ddd;
            }

            .badge {
                font-size: 14px;
                padding: 5px 10px;
                border-radius: 12px;
            }

            .bg-success {
                background-color: #28a745 !important;
            }

            .bg-danger {
                background-color: #dc3545 !important;
            }

            .btn {
                padding: 6px 12px;
                font-size: 14px;
                border-radius: 5px;
                transition: 0.3s;
            }

            .btn:hover {
                opacity: 0.8;
            }

            .pagination {
                margin-top: 20px;
                display: flex;
                justify-content: center;
                gap: 8px;
            }

            .pagination a {
                text-decoration: none;
                padding: 6px 12px;
                border: 1px solid #007bff;
                border-radius: 5px;
                color: #007bff;
                transition: 0.3s;
            }

            .pagination a:hover {
                background-color: #007bff;
                color: white;
            }

            .current-page {
                padding: 6px 12px;
                border-radius: 5px;
                background-color: #007bff;
                color: white;
                font-weight: bold;
            }
            .feedback-content img {
                max-width: 100%;
                height: auto;
                border-radius: 5px;
                margin-top: 10px;
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
                        <a class="nav-link" href="transaction_management">
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
                        <a class="nav-link  " href="serviceprovider_management">
                            <i class=" me-2"></i>
                            Quản lý nhà cung cấp dịch vụ
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="statistic_management">
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
                        <a class="nav-link active " href="feedback_management">
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
                <h1 class="h2 mb-0 text-danger">Quản lý phản hồi</h1>
            </div>

            <form action="sortFeedback" method="get">

                <label for="filterStatus">Hiển thị thông tin theo tên dịch vụ:</label>
                <select id="filterStatus" class="filter-dropdown" name="service_id"">
                    <option value="0" ${requestScope.service_id == '' ? 'selected' : ''}>Tất cả</option>
                    <c:forEach items="${listS}" var="S">
                        <option value="${S.service_id}" ${requestScope.service_id == S.service_id ? 'selected' : ''}>${S.service_name}</option>
                    </c:forEach>                
                </select>

                <label for="sortFeedback">Sắp xếp theo:</label>
                <select id="sortTransaction" class="filter-dropdown" name="sortFeedback">
                    <option value="feedback_date" ${requestScope.sort == 'full_name' ? 'selected' : ''}>Ngày phản hồi</option>
                    <option value="feedback_rate" ${requestScope.sort == 'service_name' ? 'selected' : ''}>Điểm phản hồi</option>

                </select>

                <br>
                <label>Chọn số lượng phản hồi: </label>
                <select class="filter-dropdown" name="quantity">                    
                    <option value="5" ${requestScope.quantity == '5' ? 'selected' : ''}>5</option>
                    <option value="10" ${requestScope.quantity == '10' ? 'selected' : ''}>10</option>
                    <option value="15" ${requestScope.quantity == '15' ? 'selected' : ''}>15</option>                  
                </select>

                <button type="submit">Tìm</button>
            </form>

            <div class="search-bar">
                <form action="searchFeedback">
                    <input type="text" placeholder="Search" name="searchName" >                  
                    <button style="background-color: #d32f2f; color: white; border: none; padding: 5px 10px;">Tìm</button>
                </form>
            </div>    

            <!-- View list transaction -->
            <div class="mt-3">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID Phản hồi</th>
                            <th>Họ và tên khách hàng</th>
                            <th>Tên dịch vụ</th>
                            <th>Điểm chính sách</th>
                            <th>Ngày phản hồi</th>


                        </tr>
                    </thead>
                    <c:forEach items="${listF}" var="F">
                        <tr>
                            <td><a href="#" class="text-danger" data-bs-toggle="modal" data-bs-target="#feedbackModal${F.feedback_id}">${F.feedback_id}</a></td>
                            <td>${F.full_name}</td>
                            <td>${F.service_name}</td>
                            <td>${F.feedback_rate}</td>
                            <td><fmt:formatDate value="${F.feedback_date}" pattern="dd-MM-yyyy" /></td>

                        </tr>
                        <div class="modal fade" id="feedbackModal${F.feedback_id}" tabindex="-1" aria-labelledby="feedbackModalLabel${F.feedback_id}" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="feedbackModalLabel${F.feedback_id}">Chi tiết phản hồi:</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body feedback-content">                                                              
                                        <p><strong>Nội dung phản hồi:</strong> ${F.feedback_content}</p>                                      
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </table>
                <c:forEach begin="1" end="${endP}" var="q">
                    <a href="sortFeedback?sortFeedback=${sortFeedback}&service_id=${service_id}&quantity=${quantity}&offset=${q}">${q}</a>
                </c:forEach>
        </main>        
    </div>
</div>

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>