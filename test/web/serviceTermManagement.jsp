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
            .news-table {
                width: 100%;
                border-collapse: collapse;
            }
            .news-table th, .news-table td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: left;
            }
            .news-table th {
                background-color: #d32f2f;
                color: white;
            }
            .news-content {
                max-width: 300px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .table-container {
                width: 100%;
                overflow-x: auto;
            }
            .news-table {
                width: max-content;
                min-width: 100%;
            }
            body {
                background-color: #ececec;
                min-width: 100vw;
                overflow-x: auto;
            }
            .test{
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
            }
            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 8px;
                padding: 10px;
            }

            .pagination a {
                display: inline-block;
                padding: 8px 12px;
                text-decoration: none;
                color: white;
                background-color: #d32f2f;
                border-radius: 5px;
                font-weight: bold;
                transition: background 0.3s;
            }

            .pagination a:hover {
                background-color: #b71c1c;
            }

            .current-page {
                display: inline-block;
                padding: 8px 12px;
                background-color: #ff5252;
                color: white;
                font-weight: bold;
                border-radius: 5px;
            }
        </style>


    </head>
    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger test">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="">
                    <i class="bi-box"></i>
                    Mini Finance
                </a>
            </div>

            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="searchServiceTerm" method="post" role="form">
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
                        <a class="nav-link" aria-current="page" href="staff_management">
                            <i class="me-2"></i>
                            Staff Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="service_management">
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
                        <a class="nav-link active " href="serviceTermManagement?serviceName=all&sort=all&page=1&pageSize=2">
                            <i class="me-2"></i>
                            Service Term Management
                        </a>
                    </li>

                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start" style="margin-top: 50px">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Service term Management</h1>
            </div>
            <div class="filter-sort-bar">
                <label for="filterServiceName">Filter by service name:</label> 
                <select id="filterServiceName" class="filter-dropdown" onchange="filterServiceName()">
                    <option value="all" ${requestScope.serviceName == 'all' ? 'selected' : ''}}>All </option>        
                    <c:forEach var="s" items="${requestScope.listS}">
                        <option value="${s.service_name}" ${requestScope.serviceName == s.service_name ? 'selected':''}>${s.service_name}</option>        
                    </c:forEach>
                </select>

                <label for="sort">Sort by:</label>
                <select id="sort" class="filter-dropdown" onchange="filterSort()">
                    <option value="all" ${requestScope.sort == 'all' ? 'selected' : ''}>All</option>        
                    <option value="term_name" ${requestScope.sort == 'term_name' ? 'selected' : ''}>Service term name</option>
                    <option value="max_term_months" ${requestScope.sort == 'max_term_months' ? 'selected' : ''}>Max Term Months</option>
                </select>

                <label for="pageSize">Items per page:</label>
                <select id="pageSize" class="filter-dropdown" onchange="changePageSize()">
                    <option value="2" ${requestScope.pageSize == 2 ? 'selected' : ''}>2</option>
                    <option value="3" ${requestScope.pageSize == 3 ? 'selected' : ''}>3</option>
                    <option value="4" ${requestScope.pageSize == 4 ? 'selected' : ''}>4</option>
                </select>
            </div>    
            <div class="mt-3">
                <a class="btn btn-success mb-2" href="addServiceTerm">Add New</a>
                <table class="news-table">
                    <thead>
                        <tr>
                            <th>Service name</th>
                            <th>Service term name</th>
                            <th>Description</th>
                            <th>Contract terms</th>
                            <th>Max term months</th>
                            <th>Early payment penalty</th>
                            <th>Interest rate</th>
                            <th>Min Payment</th>
                            <th>Min Deposit</th>                           
                            <th>Created at</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.listSt}" var="s">
                            <tr>
                                <td>${s.service_name}</td>
                                <td>${s.term_name}</td>
                                <td>${s.description}</td>
                                <td class="news-content">${s.contract_terms}</td>
                                <td>${s.max_term_months}</td>
                                <td>${s.early_payment_penalty}</td>
                                <td>${s.interest_rate}</td>
                                <td>${s.min_payment}</td>
                                <td>${s.min_deposit}</td>
                                <td>${s.created_at}</td>
                                <td>${s.status}</td>
                                <td>
                                    <a onclick="doDelete('${s.term_id}')" class="btn btn-danger">Delete</a>
                                    <a href="updateServiceTerm?term_id=${s.term_id}" class="btn btn-success">Update</a> 
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
                <div class="pagination">
                    <c:forEach begin="1" end="${totalPage}" var="i">
                        <c:choose>
                            <c:when test="${i == page}">
                                <span class="current-page">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="serviceTermManagement?serviceName=${requestScope.serviceName}&sort=${requestScope.sort}&page=${i}&pageSize=${pageSize}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                <script type="text/javascript">
                    function doDelete(id) {
                        if (confirm("Are you sure to delete this term ?")) {
                            window.location = "deleteServiceTerm?term_id=" + id;
                        }
                    }

                    function filterServiceName() {
                        var serviceName = document.getElementById("filterServiceName").value;
                        var sort = document.getElementById("sort").value;
                        var pageSize = document.getElementById("pageSize").value;
                        window.location.href = "serviceTermManagement?serviceName=" + serviceName + "&sort=" + sort + "&page=1"+"&pageSize="+pageSize;
                    }

                    function filterSort() {
                        var sort = document.getElementById("sort").value;
                        var serviceName = document.getElementById("filterServiceName").value;
                        var pageSize = document.getElementById("pageSize").value;
                        window.location.href = "serviceTermManagement?serviceName=" + serviceName + "&sort=" + sort + "&page=1"+"&pageSize="+pageSize;
                    }

                    function changePageSize() {
                        var pageSize = document.getElementById("pageSize").value;
                        var serviceName = document.getElementById("filterServiceName").value;
                        var sort = document.getElementById("sort").value;
                        window.location.href = "serviceTermManagement?serviceName=" + serviceName + "&sort=" + sort + "&page=1"+"&pageSize="+pageSize;
                    }
                </script>
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