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
                        <a class="nav-link " aria-current="page" href="staff_management?status=all&sort=full_name&type=bankers&page=1&pageSize=2">
                            <i class="me-2"></i>
                            Quản lý nhân viên
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link active" href="service_management?type=services">
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
                <h1 class="h2 mb-0 text-danger">Quản lý dịch vụ</h1>
            </div>

            <input type="hidden" name ="page" value="1"> 
            <!-- Tabs choose  -->
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link ${requestScope.service != null ? 'active' : ''}" href="service_management?type=services">Dịch vụ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${requestScope.term != null ? 'active' : ''}" href="service_management?type=term">Kỳ hạn</a>
                </li>              
            </ul>
            <c:if test="${requestScope.service!=null}">    
                <div class="filter-sort-bar">
                    <label for="filterStatus">Lọc theo trạng thái:</label>
                    <select id="filterStatus" class="filter-dropdown" onchange="filterService()">
                        <option value="all" ${requestScope.status == 'all' ? 'selected' : ''}>Tất cả</option>
                        <option value="active" ${requestScope.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                        <option value="inactive" ${requestScope.status == 'inactive' ? 'selected' : ''}>Không hoạt động</option>                  
                    </select>

                    <label for="sortService">Sắp xếp:</label>
                    <select id="sortService" class="filter-dropdown">
                        <option value="service_name" ${requestScope.sort == 'service_name' ? 'selected' : ''}>Tên</option>
                        <option value="service_type" ${requestScope.sort == 'service_type' ? 'selected' : ''}>Loại</option>                   
                    </select>

                    <label for="selectPageService">Hiện:</label>
                    <select id="selectPageService" class="filter-dropdown" onchange="selectPageService()">
                        <option value="2" ${requestScope.pageSize == '2' ? 'selected' : ''}>2</option>
                        <option value="5" ${requestScope.pageSize == '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${requestScope.pageSize == '10' ? 'selected' : ''}>10</option>
                    </select>
                </div>       
            </c:if>  

            <c:if test="${requestScope.term!=null}">    
                <div class="filter-sort-bar">
                    <label for="filterStatus">Lọc theo trạng thái:</label>
                    <select id="filterStatus" class="filter-dropdown" onchange="filterTerm()">
                        <option value="all" ${requestScope.status == 'all' ? 'selected' : ''}>Tất cả</option>
                        <option value="active" ${requestScope.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                        <option value="inactive" ${requestScope.status == 'inactive' ? 'selected' : ''}>Không hoạt động</option>                  
                    </select>

                    <label for="sortTerm">Sắp xếp:</label>
                    <select id="sortTerm" class="filter-dropdown">
                        <option value="term_name" ${requestScope.sort == 'term_name' ? 'selected' : ''}>Tên</option>
                        <option value="duration" ${requestScope.sort == 'duration' ? 'selected' : ''}>Thời gian</option>                   
                    </select>

                    <label for="selectPageTerm">Hiện:</label>
                    <select id="selectPageTerm" class="filter-dropdown" onchange="selectPageTerm()">
                        <option value="2" ${requestScope.pageSize == '2' ? 'selected' : ''}>2</option>
                        <option value="5" ${requestScope.pageSize == '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${requestScope.pageSize == '10' ? 'selected' : ''}>10</option>
                    </select>
                </div>       
            </c:if>      

            <!-- View list service -->
            <c:if test="${requestScope.service!=null}">
                <div class="mt-3">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <form action="searchService" class="d-flex">
                                <input type="hidden" name="type" value="services">
                                <input type="text" class="form-control me-2" placeholder="Tìm kiếm theo tên" name="searchName" >
                                <button type="submit" class="btn btn-danger">Tìm</button>
                            </form>
                        </div>                       
                        <div class="col-md-2">
                            <a class="btn btn-success w-100" href="addService.jsp">Thêm mới</a>
                        </div>
                    </div>
                    <div class="mb-3 text-end">
                    </div>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên</th>
                                <th>Mô tả</th>
                                <th>Loại </th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty ListByName}">
                                    <c:forEach items="${ListByName}" var="s">
                                        <tr>
                                            <td>${s.service_id}</td>
                                            <td>${s.service_name}</td>
                                            <td>${s.description}</td>
                                            <td>${s.service_type}</td>
                                            <td>
                                                <span class="badge ${s.status == 'active' ? 'bg-success' : 'bg-danger'}">
                                                    <c:if test="${s.status == 'active'}">
                                                        Hoạt động
                                                    </c:if>
                                                    <c:if test="${s.status == 'inactive'}">
                                                        Không hoạt động
                                                    </c:if>
                                                </span>
                                            </td>
                                            <td>
                                                <a onclick="doDeleteService('${s.service_id}')" href="#" class="btn btn-danger btn-sm">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                                <a href="updateService?id=${s.service_id}" class="btn btn-success btn-sm">
                                                    <i class="bi bi-pencil-square"></i>
                                                </a> 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${service}" var="s">
                                        <tr>
                                            <td>${s.service_id}</td>
                                            <td>${s.service_name}</td>
                                            <td>${s.description}</td>
                                            <td>${s.service_type}</td>
                                            <td>
                                                <span class="badge ${s.status == 'active' ? 'bg-success' : 'bg-danger'}">
                                                    <c:if test="${s.status == 'active'}">
                                                        Hoạt động
                                                    </c:if>
                                                    <c:if test="${s.status == 'inactive'}">
                                                        Không hoạt động
                                                    </c:if>
                                                </span>
                                            </td>
                                            <td>
                                                <a onclick="doDeleteService('${s.service_id}')" href="#" class="btn btn-danger btn-sm">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                                <a href="updateService?id=${s.service_id}" class="btn btn-success btn-sm">
                                                    <i class="bi bi-pencil-square"></i>
                                                </a> 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${empty ListByName and empty service}">
                                <tr>
                                    <td colspan="6" class="text-center">No services found.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>  
            </c:if>

            <!-- View list term -->
            <c:if test="${requestScope.term!=null}">
                <div class="mt-3">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <form action="searchTerm" class="d-flex">
                                <input type="hidden" name="type" value="term">
                                <input type="text" class="form-control me-2" placeholder="Tìm kiếm theo tên" name="searchName" >
                                <button type="submit" class="btn btn-danger">Tìm</button>
                            </form>
                        </div>
                        <div class="col-md-2">
                            <a class="btn btn-success w-100" href="addTerm.jsp"">Thêm mới</a>
                        </div>
                    </div>
                    <div class="mb-3 text-end">
                    </div>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên</th>
                                <th>Thời gian</th>
                                <th>Loại</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <c:forEach items="${requestScope.term}" var="t">
                            <tr>
                                <td>${t.term_id}</td>
                                <td>${t.term_name}</td>
                                <td>${t.duration}</td>
                                <td>${t.term_type}</td>    
                                <td>
                                    <span class="badge ${t.status == 'active' ? 'bg-success' : 'bg-danger'}">                                       
                                        <c:if test="${t.status == 'active'}">
                                            Hoạt động
                                        </c:if>
                                        <c:if test="${t.status == 'inactive'}">
                                            Không hoạt động
                                        </c:if>
                                    </span>
                                </td>  
                                <td>
                                    <a onclick="doDeleteTerm('${t.term_id}')" href="#" class="btn btn-danger">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                    <a href="updateTerm?id=${t.term_id}" class="btn btn-success">
                                        <i class="bi bi-pencil-square"></i>
                                    </a> 
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

            </c:if>
            <div class="pagination">
                <c:forEach begin="1" end="${totalPage}" var="i">
                    <c:choose>
                        <c:when test="${i == page}">
                            <span class="current-page">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="service_management?&status=${status}&sort=${sort}&type=${param.type}&page=${i}&pageSize=${pageSize}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

        </main>

        <script type="text/javascript">
            function doDeleteService(id) {
                if (confirm("Are you sure to delete ID '" + id + "'?")) {
                    window.location = "deleteService?id=" + id;
                }
            }
            function doDeleteTerm(id) {
                if (confirm("Are you sure to delete ID '" + id + "'?")) {
                    window.location = "deleteTerm?id=" + id;
                }
            }

            function selectPageService() {
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortService").value;
                var pageSize = document.getElementById("selectPageService").value;
                var type = '${param.type}';
                window.location.href = "service_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize;
            }

            function filterService() {
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortService").value;
                var pageSize = document.getElementById("selectPageService").value;
                var type = '${param.type}';
                window.location.href = "service_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize;
            }

            function sortService() {
                var sort = document.getElementById("sortService").value;
                var status = document.getElementById("filterStatus").value;
                var pageSize = document.getElementById("selectPageService").value;
                var type = '${param.type}';
                window.location.href = "service_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize;
            }

            document.getElementById("sortService").onchange = sortService;


        </script>

        <script>
            function selectPageTerm() {
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortTerm").value;
                var pageSize = document.getElementById("selectPageTerm").value;
                var type = '${param.type}';
                window.location.href = "service_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize;
            }

            function filterTerm() {
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortTerm").value;
                var pageSize = document.getElementById("selectPageTerm").value;
                var type = '${param.type}';
                window.location.href = "service_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize;
            }

            function sortTerm() {
                var sort = document.getElementById("sortTerm").value;
                var status = document.getElementById("filterStatus").value;
                var pageSize = document.getElementById("selectPageTerm").value;
                var type = '${param.type}';
                window.location.href = "service_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize;
            }

            document.getElementById("sortTerm").onchange = sortTerm;
        </script>


        </main>

    </div>
</div>

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>