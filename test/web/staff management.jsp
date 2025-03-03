<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <a class="nav-link active" aria-current="page" href="staff_management?status=all&sort=full_name&type=&page=1&pageSize=2">
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
                        <a class="nav-link " href="statistic_management">
                            <i class="me-2"></i>
                            Statistic Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="serviceTermManagement">
                            <i class="me-2"></i>
                            Service Term Management
                        </a>
                    </li>

                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Staff Management</h1>
            </div>

            <input type="hidden" name ="page" value="1"> 

            <!-- Tabs choose staff -->
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link ${param.type == null || param.type == 'bankers' ? 'active' : ''}" href="staff_management?status=all&sort=full_name&type=bankers&page=1&pageSize=2">Bankers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.type == 'marketers' ? 'active' : ''}" href="staff_management?status=all&sort=full_name&type=marketers&page=1&pageSize=2">Marketers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.type == 'accountants' ? 'active' : ''}" href="staff_management?status=all&sort=full_name&type=accountants&page=1&pageSize=2">Accountants</a>
                </li>
            </ul>

            <div class="filter-sort-bar">
                <label for="filterStatus">Filter by Status:</label>
                <select id="filterStatus" class="filter-dropdown" onchange="filterStaff()">
                    <option value="all" ${requestScope.status == 'all' ? 'selected' : ''}>All</option>
                    <option value="active" ${requestScope.status == 'active' ? 'selected' : ''}>Active</option>
                    <option value="inactive" ${requestScope.status == 'inactive' ? 'selected' : ''}>Inactive</option>                  
                </select>

                <label for="sortStaff">Sort by:</label>
                <select id="sortStaff" class="filter-dropdown">
                    <option value="full_name" ${requestScope.sort == 'full_name' ? 'selected' : ''}>Name</option>
                    <option value="gender" ${requestScope.sort == 'gender' ? 'selected' : ''}>Gender</option>
                    <option value="date_of_birth" ${requestScope.sort == 'date_of_birth' ? 'selected' : ''}>DOB</option>
                </select>

                <label for="selectPage">Show:</label>
                <select id="selectPage" class="filter-dropdown" onchange="selectPage()">
                    <option value="2" ${requestScope.pageSize == '2' ? 'selected' : ''}>2</option>
                    <option value="5" ${requestScope.pageSize == '5' ? 'selected' : ''}>5</option>
                    <option value="10" ${requestScope.pageSize == '10' ? 'selected' : ''}>10</option>
                </select>
            </div>    





            <!-- View list staff -->
            <div class="mt-3">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <form id="searchForm" class="d-flex">             
                            <input type="text" class="form-control me-2" 
                                   placeholder="Search by name" name="searchName" value="${param.searchName}" >
                            <button type="submit" class="btn btn-danger">Search</button>
                        </form> 
                    </div>
                    <div class="col-md-2">
                        <a class="btn btn-success mb-2" href="addStaff.jsp">Add New</a>
                    </div>
                </div>
                <!-- Display search results if available -->
                <c:if test="${not empty ListByName or not empty ListByPhone}">
                    <div class="search-results mb-4">
                        <h4>Search Results</h4>

                        <!-- Results by Name -->
                        <c:if test="${not empty ListByName}">
                            <h5 class="mt-3">Found by Name:</h5>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>                                       
                                        <th>Role</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${ListByName}" var="b">
                                    <tr>
                                        <td>${b.staff_id}</td>
                                        <td>${b.full_name}</td>
                                        <td>${b.email}</td>
                                        <td>${b.phone_number}</td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${b.role_id == 2}">Banker</c:when>
                                                <c:when test="${b.role_id == 3}">Marketer</c:when>
                                                <c:when test="${b.role_id == 4}">Accountant</c:when>
                                            </c:choose>
                                        </td>
                                        <td><span class="badge ${b.status == 'active' ? 'bg-success' : 'bg-danger'}">${b.status}</span></td>
                                        <td>
                                            <a onclick="doDelete('${b.staff_id}')" href="#" class="btn btn-danger btn-sm">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                            <a href="updateStaff?id=${b.staff_id}" class="btn btn-success btn-sm">
                                                <i class="bi bi-pencil-square"></i>
                                            </a> 
                                        </td>

                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>

                        <!-- Results by Phone -->
                        <c:if test="${not empty ListByPhone}">
                            <h5 class="mt-3">Found by Phone:</h5>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>                                       
                                        <th>Role</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${ListByPhone}" var="b">
                                    <tr>
                                        <td>${b.staff_id}</td>
                                        <td>${b.full_name}</td>
                                        <td>${b.email}</td>
                                        <td>${b.phone_number}</td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${b.role_id == 2}">Banker</c:when>
                                                <c:when test="${b.role_id == 3}">Marketer</c:when>
                                                <c:when test="${b.role_id == 4}">Accountant</c:when>
                                            </c:choose>
                                        </td>
                                        <td><span class="badge ${b.status == 'active' ? 'bg-success' : 'bg-danger'}">${b.status}</span></td>
                                        <td>
                                            <a onclick="doDelete('${b.staff_id}')" href="#" class="btn btn-danger btn-sm">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                            <a href="updateStaff?id=${b.staff_id}" class="btn btn-success btn-sm">
                                                <i class="bi bi-pencil-square"></i>
                                            </a> 
                                        </td>

                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>

                        <!-- No results found message -->
                        <c:if test="${empty ListByName and empty ListByPhone}">
                            <div class="alert alert-info mt-3">
                                No staff members found matching your search.
                            </div>
                        </c:if>
                    </div>
                </c:if>

                <!-- Original staff list table -->
                <c:if test="${empty ListByName and empty ListByPhone}">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>                               
                                <th>Role</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <c:forEach items="${requestScope.data}" var="b">
                            <tr>
                                <td>${b.staff_id}</td>
                                <td>${b.full_name}</td>
                                <td>${b.email}</td>
                                <td>${b.phone_number}</td>                                                                                          
                                <td>
                                    <c:choose>
                                        <c:when test="${b.role_id == 2}">Banker</c:when>
                                        <c:when test="${b.role_id == 3}">Marketer</c:when>
                                        <c:when test="${b.role_id == 4}">Accountant</c:when>
                                    </c:choose>
                                </td>
                                <td><span class="badge ${b.status == 'active' ? 'bg-success' : 'bg-danger'}">${b.status}</span></td>
                                <td>
                                    <a onclick="doDelete('${b.staff_id}')" href="#" class="btn btn-danger btn-sm">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                    <a href="updateStaff?id=${b.staff_id}" class="btn btn-success btn-sm">
                                        <i class="bi bi-pencil-square"></i>
                                    </a> 
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>

            <div class="pagination">
                <c:forEach begin="1" end="${totalPage}" var="i">
                    <c:choose>
                        <c:when test="${i == page}">
                            <span class="current-page">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="staff_management?&status=${status}&sort=${sort}&type=${param.type}&page=${i}&pageSize=${pageSize}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

        </main>

        <script type="text/javascript">
            function doDelete(id) {
                if (confirm("Are you sure to delete ID '" + id + "'?")) {
                    window.location = "deleteStaff?id=" + id;
                }
            }

            document.getElementById("searchForm").addEventListener("submit", function (event) {
                event.preventDefault();

                var searchName = document.querySelector("input[name='searchName']").value;
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortStaff").value;
                var pageSize = document.getElementById("selectPage").value;
                var type = '${param.type}';

                window.location.href = "staff_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize + "&searchName=" + encodeURIComponent(searchName);
            });

            function selectPage() {
                var searchName = document.querySelector("input[name='searchName']").value;
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortStaff").value;
                var pageSize = document.getElementById("selectPage").value;
                var type = '${param.type}';

                var search = searchName ? searchName.value.trim().replace(/\s+/g, " ") : "";
                if (search !== "") {
                    window.location.href = "staff_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize + "&searchName=" + encodeURIComponent(searchName);
                } else {
                    window.location.href = "staff_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize;
                }



            }

            function filterStaff() {
                var searchName = document.querySelector("input[name='searchName']").value;
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortStaff").value;
                var pageSize = document.getElementById("selectPage").value;
                var type = '${param.type}';
                window.location.href = "staff_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize + "&searchName=" + encodeURIComponent(searchName);
                ;
            }

            function sortStaff() {
                var searchName = document.querySelector("input[name='searchName']").value;
                var sort = document.getElementById("sortStaff").value;
                var status = document.getElementById("filterStatus").value;
                var pageSize = document.getElementById("selectPage").value;
                var type = '${param.type}';
                window.location.href = "staff_management?status=" + status + "&sort=" + sort + "&type=" + type + "&page=1" + "&pageSize=" + pageSize + "&searchName=" + encodeURIComponent(searchName);
                ;
            }

            document.getElementById("sortStaff").onchange = sortStaff;

        </script>       



    </div>
</div>

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>