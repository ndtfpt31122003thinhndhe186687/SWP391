<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Giao dịch</title>

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
        </style>
    </head>
    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="">
                    <i class="bi-bank"></i>
                    FinBank
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
                        <a class="nav-link" aria-current="page" href="staff_management?status=all&sort=full_name&type=bankers&page=1&pageSize=2">
                            <i class="me-2"></i>
                            Quản lý nhân viên
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="service_management?type=services">
                            <i class="me-2"></i>
                            Quản lý dịch vụ
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link active" href="transaction_management">
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
                <h1 class="h2 mb-0 text-danger">Quản lý giao dịch</h1>
            </div>

            <input type="hidden" name ="page" value="1"> 

            <div class="filter-sort-bar">
                <label for="filterStatus">Lọc theo trạng thái:</label>
                <select id="filterStatus" class="filter-dropdown" onchange="filterTransaction()">
                    <option value="all" ${requestScope.status == 'all' ? 'selected' : ''}>Tất cả</option>
                    <option value="deposit" ${requestScope.status == 'deposit' ? 'selected' : ''}>Nạp</option>
                    <option value="withdrawal" ${requestScope.status == 'withdrawal' ? 'selected' : ''}>Rút</option>                  
                </select>

                <label for="sortTransaction">Sắp xếp:</label>
                <select id="sortTransaction" class="filter-dropdown">
                    <option value="full_name" ${requestScope.sort == 'full_name' ? 'selected' : ''}>Tên khách hàng</option>
                    <option value="service_name" ${requestScope.sort == 'service_name' ? 'selected' : ''}>Tên dịch vụ</option>
                    <option value="amount" ${requestScope.sort == 'amount' ? 'selected' : ''}>Số tiền</option>
                    <option value="transaction_date" ${requestScope.sort == 'transaction_date' ? 'selected' : ''}>Ngày</option>
                </select>

                <label for="selectPage">Hiện:</label>
                <select id="selectPage" class="filter-dropdown" onchange="selectPage()">
                    <option value="5" ${requestScope.pageSize == '5' ? 'selected' : ''}>5</option>
                    <option value="10" ${requestScope.pageSize == '10' ? 'selected' : ''}>10</option>
                    <option value="20" ${requestScope.pageSize == '20' ? 'selected' : ''}>20</option>
                </select>
            </div>  
           
            <div class="col-md-6 search-bar">
                <form action="searchTransaction" class="d-flex">
                    <input type="text" class="form-control me-2" placeholder="Tìm kiếm theo tên khách hàng" name="searchName" >
                    <button type="submit" class="btn btn-danger">Tìm</button>
                </form>
            </div>     

            <!-- View list transaction -->
            <div class="mt-3">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên khách hàng</th>
                            <th>Tên dịch vụ</th>
                            <th>Số tiền</th>
                            <th>Ngày giao dịch</th>                          
                        </tr>
                    </thead>
                    <c:forEach items="${requestScope.data}" var="b">
                        <tr>
                            <td>${b.transaction_id}</td>
                            <td>${b.customer_name}</td>
                            <td>${b.service_name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${b.transaction_type == 'deposit'}">
                                        <span style="color: green;">
                                            +<fmt:formatNumber value="${b.amount}" type="number" groupingUsed="true" />đ
                                        </span>
                                    </c:when>
                                    <c:when test="${b.transaction_type == 'withdrawal'}">
                                        <span style="color: red;">
                                            -<fmt:formatNumber value="${b.amount}" type="number" groupingUsed="true" />đ
                                        </span>
                                    </c:when>                                   
                                </c:choose>
                            </td>
                            <td>
                                <fmt:formatDate value="${b.transaction_date}" pattern="dd/MM/yyyy"/>
                            </td>                                                   
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="pagination">
                <c:forEach begin="1" end="${totalPage}" var="i">
                    <c:choose>
                        <c:when test="${i == page}">
                            <span class="current-page">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="transaction_management?&status=${status}&sort=${sort}&page=${i}&pageSize=${pageSize}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </main>

        <script type="text/javascript">
            function selectPage() {
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortTransaction").value;
                var pageSize = document.getElementById("selectPage").value;
                window.location.href = "transaction_management?status=" + status + "&sort=" + sort + "&page=1" + "&pageSize=" + pageSize;
            }

            function filterTransaction() {
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortTransaction").value;
                var pageSize = document.getElementById("selectPage").value;
                window.location.href = "transaction_management?status=" + status + "&sort=" + sort + "&page=1" + "&pageSize=" + pageSize;
            }

            function sortTransaction() {
                var sort = document.getElementById("sortTransaction").value;
                var status = document.getElementById("filterStatus").value;
                var pageSize = document.getElementById("selectPage").value;
                window.location.href = "transaction_management?status=" + status + "&sort=" + sort + "&page=1" + "&pageSize=" + pageSize;
            }

            document.getElementById("sortTransaction").onchange = sortTransaction;
        </script>            





    </div>
</div>

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>