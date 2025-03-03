<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
            .table thead {
                background-color: #dc3545;
                color: white;
            }


            .table {
                border-color: #dc3545;
            }

            .table tbody tr {
                color: #333;
            }


            .table tbody tr:hover {
                background-color: #f1b0b7;
                transition: 0.3s;
            }

            .btn-danger {
                background-color: #8b0000 !important;
                border-color: #8b0000 !important;
            }

            .btn-success {
                background-color: #b02a37 !important;
                border-color: #b02a37 !important;
            }
            .modal-dialog {
                max-width: 600px; /* Điều chỉnh kích thước modal */
            }

            .modal-content {
                padding: 20px;
                border-radius: 10px; /* Bo tròn góc modal */
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2); /* Hiệu ứng bóng */
            }

            .modal-header {
                background-color: #dc3545;
                color: white;
                border-top-left-radius: 10px;
                border-top-right-radius: 10px;
                text-align: center;
            }

            .modal-body {
                text-align: center;
            }

            .modal-body label {
                font-weight: bold;
                font-size: 16px;
                display: block;
                margin-top: 10px;
                text-align: left;
            }

            .modal-body input,
            .modal-body select {
                width: 100%;
                font-size: 16px;
                padding: 10px;
                height: 45px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .modal-footer {
                display: flex;
                justify-content: center;
            }

            .modal-footer .btn {
                padding: 10px 20px;
                font-size: 16px;
                border-radius: 5px;
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

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="searchInsuranceTermByTermName" method="post" role="form">
                <input class="form-control bg-white text-dark" name="search_term_name" type="text" placeholder="Search" aria-label="Search">
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
                        <a class="nav-link " href="paginationInsurancePolicy?offset=1&quantity=5">
                            <i class="me-2"></i>
                            Insurance Policy Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="paginationInsuranceCustomer?offset=1&quantity=5">
                            <i class="me-2"></i>
                            Insurance Customer Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="paginationInsuranceContract?offset=1&quantity=5">
                            <i class=" me-2"></i>
                            Insurance Contact Management
                        </a>
                    </li>                   

                    <li class="nav-item">
                        <a class="nav-link " href="paginationInsuranceTransaction?offset=1&quantity=5">
                            <i class="me-2"></i>
                            Insurance Transactions Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="managerInsuranceTerm?insurance_id=${account.insurance_id}">
                            <i class="me-2"></i>
                            Insurance Term Management
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Insurance Term Management</h1>
            </div>

            <!-- Tabs choose staff -->


            <!-- View list staff -->
            <div class="mt-3">
                <button class="btn btn-success mb-2" data-bs-toggle="modal" data-bs-target="#addInsuranceTermModal">Add New Term</button>
                <form action="sortInsuranceTerm" method="get">
                    <label>Sort by :</label>
                    <select class="filter-dropdown" name="sortInsuranceTerm">
                        <option value="none" ${requestScope.sortInsuranceTerm == '' ? 'selected' : ''}>None</option>    
                        <option value="created_at" ${requestScope.sortInsuranceTerm == 'created_at' ? 'selected' : ''}>Created At</option>
                        <option value="start_date" ${requestScope.sortInsuranceTerm == 'start_date' ? 'selected' : ''}>Start Date</option>
                    </select>
                    <label>Filter by Status:</label>
                    <select class="filter-dropdown" name="status">                    
                        <option value="all" ${requestScope.status == '' ? 'selected' : ''}>All</option>
                        <option value="active" ${requestScope.status == 'active' ? 'selected' : ''}>Active</option>
                        <option value="inactive" ${requestScope.status == 'inactive' ? 'selected' : ''}>Inactive</option>

                    </select>
                    <button type="submit">Find</button>
                </form>
                <form action="paginationInsuranceTerm" method="get">
                    <label>Select quantity term: </label>
                    <select class="filter-dropdown" name="quantity">                    
                        <option value="5" ${requestScope.quantity == '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${requestScope.quantity == '10' ? 'selected' : ''}>10</option>
                        <option value="15" ${requestScope.quantity == '15' ? 'selected' : ''}>15</option>                  
                    </select>
                    <button type="submit">Find</button>
                </form>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Term ID</th>
                            <th>Term Name</th>
                            <th>Term Description</th>
                            <th>Policy Name</th>
                            <th>Status</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Created At</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <c:forEach items="${listTerm}" var="T">
                        <tr>
                            <td>${T.term_id}</td>
                            <td>${T.term_name}</td>
                            <td>${T.term_description}</td>
                            <td>${T.policy_name}</td>
                            <td>${T.status}</td> 
                            <td>${T.start_date}</td> 
                            <td>${T.end_date}</td>
                            <td>${T.created_at}</td>
                            <td>
                                <a href="#" onclick="doDelete('${T.term_id}')" class="btn btn-danger">Delete</a>
                                <a href="updateInsuranceTerm?term_id=${T.term_id}" class="btn btn-success">Update</a> 
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:forEach begin="1" end="${endP}" var="q">
                    <a href="paginationInsuranceTerm?offset=${q}&quantity=${quantity}">${q}</a>
                </c:forEach>
            </div>

            <div class="modal fade" id="addInsuranceTermModal" tabindex="-1" aria-labelledby="addInsuranceTermModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addInsuranceTermModallLabel">Add a New Insurance Term</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <% String error = (String) request.getAttribute("error"); %>
                            <% if (error != null) { %>
                            <div class="alert alert-danger"><%= error %></div>
                            <% } %>
                            <form id="addInsuranceTermModalForm" action="addInsuranceTerm" method="post">
                                <label>Enter Policy ID</label> <br>
                                <select class="filter-dropdown" name="policy_id"  >
                                    <c:if test ="${not empty listPolicy}">
                                        <c:forEach var="p" items="${requestScope.listPolicy}">
                                            <option value="${p.policy_id}">${p.policy_name}</option>  
                                        </c:forEach>                
                                    </c:if>

                                </select>

                                <br>
                                <label>Enter Term Name</label>                                
                                <textarea name="term_name" id="editor1" required></textarea><br>
                                <label>Enter Term Description</label>
                                <textarea name="term_description" id="editor2"  required></textarea><br>
                                <label>Enter Start Date</label>
                                <input type="text" name="start_date" placeholder="yyyy-MM-d" required/>
                                <label>Enter End Date</label>
                                <input type="text" name="end_date" placeholder="yyyy-MM-d" required/>
                                <label>Enter Status</label><br>
                                <select class="filter-dropdown" name="status" >
                                    <option value="active">Active</option>
                                    <option value="inactive">Inactive</option>
                                </select>
                                <br>
                                <button type="submit" class="btn btn-danger mt-3">Add Insurance Term</button>
                            </form>
                        </div>

                    </div>
                </div>

            </div>
        </main>

        <script type="text/javascript">
            function doDelete(id) {
                if (confirm("Are you sure to delete ID '" + id + "'?")) {
                    window.location = "deleteInsuranceTerm?term_id=" + id;
                }
            }
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var hasError = "<%= (error != null) ? "true" : "false" %>";
                if (hasError === "true") {
                    var modal = new bootstrap.Modal(document.getElementById("addInsuranceTermModal"));
                    modal.show();
                }
            });
        </script>
        <%
            HttpSession sessionSuccess = request.getSession();
            Boolean showModal = (Boolean) sessionSuccess.getAttribute("showSuccessModal");
            String successMessage = (String) sessionSuccess.getAttribute("successMessage");

            if (showModal != null && showModal) {
        %>
        <!-- Modal thông báo -->
        <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="successModalLabel">Success</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <%= successMessage %>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">OK</button>
                    </div>
                </div>
            </div>
        </div>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var successModal = new bootstrap.Modal(document.getElementById('successModal'));
                successModal.show();
            });
        </script>
        <%
            // Xóa session ?? modal không xu?t hi?n l?i khi reload trang
            sessionSuccess.removeAttribute("showSuccessModal");
            sessionSuccess.removeAttribute("successMessage");
            }
        %>
        <script src="https://cdn.ckeditor.com/4.21.0/standard/ckeditor.js"></script>
        <script>
            CKEDITOR.replace('editor1');
            CKEDITOR.replace('editor2');
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