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
                        <a class="nav-link " aria-current="page" href="staff_management">
                            <i class="me-2"></i>
                            Staff Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link active" href="service_management">
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

                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger"> Service Management</h1>
            </div>

            <!-- Tabs choose  -->
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link ${requestScope.service != null ? 'active' : ''}" href="service_management?type=services">Services</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${requestScope.term != null ? 'active' : ''}" href="service_management?type=term">Term</a>
                </li>              
            </ul>
            <c:if test="${requestScope.service!=null}">    
                <div class="filter-sort-bar">
                    <label for="filterStatus">Filter by Status:</label>
                    <select id="filterStatus" class="filter-dropdown" onchange="filterService()">
                        <option value="all" ${requestScope.status == 'all' ? 'selected' : ''}>All</option>
                        <option value="active" ${requestScope.status == 'active' ? 'selected' : ''}>Active</option>
                        <option value="inactive" ${requestScope.status == 'inactive' ? 'selected' : ''}>Inactive</option>                  
                    </select>

                    <label for="sortService">Sort by:</label>
                    <select id="sortService" class="filter-dropdown">
                        <option value="service_name" ${requestScope.sort == 'service_name' ? 'selected' : ''}>Name</option>
                        <option value="service_type" ${requestScope.sort == 'service_type' ? 'selected' : ''}>Type</option>                   
                    </select>
                </div>       
            </c:if>  

            <c:if test="${requestScope.term!=null}">    
                <div class="filter-sort-bar">
                    <label for="filterStatus">Filter by Status:</label>
                    <select id="filterStatus" class="filter-dropdown" onchange="filterTerm()">
                        <option value="all" ${requestScope.status == 'all' ? 'selected' : ''}>All</option>
                        <option value="active" ${requestScope.status == 'active' ? 'selected' : ''}>Active</option>
                        <option value="inactive" ${requestScope.status == 'inactive' ? 'selected' : ''}>Inactive</option>                  
                    </select>

                    <label for="sortTerm">Sort by:</label>
                    <select id="sortTerm" class="filter-dropdown">
                        <option value="term_name" ${requestScope.sort == 'term_name' ? 'selected' : ''}>Name</option>
                        <option value="duration" ${requestScope.sort == 'duration' ? 'selected' : ''}>Duration</option>                   
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
                                <input type="text" class="form-control me-2" placeholder="Search by service name" name="searchName" value="${searchName}">
                                <button type="submit" class="btn btn-danger">Search</button>
                            </form>
                        </div>                       
                        <div class="col-md-2">
                            <a class="btn btn-success w-100" href="addService.jsp">Add New</a>
                        </div>
                    </div>
                    <div class="mb-3 text-end">
                    </div>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Type</th>
                                <th>Status</th>
                                <th>Actions</th>
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
                                            <td><span class="badge ${s.status == 'active' ? 'bg-success' : 'bg-danger'}">${s.status}</span></td>
                                            <td>
                                                <a onclick="doDeleteService('${s.service_id}')" href="#" class="btn btn-danger btn-sm">Delete</a>
                                                <a href="updateService?id=${s.service_id}" class="btn btn-success btn-sm">Update</a> 
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
                                            <td><span class="badge ${s.status == 'active' ? 'bg-success' : 'bg-danger'}">${s.status}</span></td>
                                            <td>
                                                <a onclick="doDeleteService('${s.service_id}')" href="#" class="btn btn-danger btn-sm">Delete</a>
                                                <a href="updateService?id=${s.service_id}" class="btn btn-success btn-sm">Update</a> 
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
                                <input type="text" class="form-control me-2" placeholder="Search by term name" name="searchName" value="${searchName}">
                                <button type="submit" class="btn btn-danger">Search</button>
                            </form>
                        </div>
                        <div class="col-md-2">
                            <a class="btn btn-success w-100" href="addTerm.jsp"">Add New</a>
                        </div>
                    </div>
                    <div class="mb-3 text-end">
                    </div>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Duration</th>
                                <th>Type</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <c:forEach items="${requestScope.term}" var="t">
                            <tr>
                                <td>${t.term_id}</td>
                                <td>${t.term_name}</td>
                                <td>${t.duration}</td>
                                <td>${t.term_type}</td>    
                                <td><span class="badge ${t.status == 'active' ? 'bg-success' : 'bg-danger'}">${t.status}</span></td>  
                                <td>
                                    <a onclick="doDeleteTerm('${t.term_id}')" href="#" class="btn btn-danger">Delete</a>
                                    <a href="updateTerm?id=${t.term_id}" class="btn btn-success">Update</a> 
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
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
            function filterService() {
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortService").value;
                var type = '${param.type}';
                window.location.href = "ServiceFilter?status=" + status + "&sort=" + sort + "&type=" + type;
            }

            function sortService() {
                var sort = document.getElementById("sortService").value;
                var status = document.getElementById("filterStatus").value;
                var type = '${param.type}';
                window.location.href = "ServiceFilter?status=" + status + "&sort=" + sort + "&type=" + type;
            }

            document.getElementById("sortService").onchange = sortService;


        </script>

        <script>
            function filterTerm() {
                var status = document.getElementById("filterStatus").value;
                var sort = document.getElementById("sortTerm").value;
                var type = '${param.type}';
                window.location.href = "TermFilter?status=" + status + "&sort=" + sort + "&type=" + type;
            }

            function sortTerm() {
                var sort = document.getElementById("sortTerm").value;
                var status = document.getElementById("filterStatus").value;
                var type = '${param.type}';
                window.location.href = "TermFilter?status=" + status + "&sort=" + sort + "&type=" + type;
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