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

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="searchInsuranceTransaction" method="post" role="form">
                <input class="form-control bg-white text-dark" name="search_customer_name" type="text" placeholder="Search" aria-label="Search">
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
                <h1 class="h2 mb-0 text-danger">Insurance Transactions Management</h1>
            </div>

            <!-- Tabs choose staff -->


            <!-- View list staff -->
            <div class="mt-3">
                <form action="sortInsuranceTransaction" method="get">
                    <label>Sort by :</label>
                    <select class="filter-dropdown" name="sortInsuranceTransaction">
                        <option value="none" ${requestScope.sort == '' ? 'selected' : ''}>None</option>
                        <option value="transaction_date" ${requestScope.sort == 'transaction_date' ? 'selected' : ''}>Transaction Date</option>
                        <option value="amount" ${requestScope.sort == 'amount' ? 'selected' : ''}>Amount</option>
                    </select>
                    <label>Filter by Transaction Type</label>
                    <select class="filter-dropdown" name="transaction_type">                    
                        <option value="all" ${requestScope.transaction_type == '' ? 'selected' : ''}>All</option>
                        <option value="premium_payment" ${requestScope.transaction_type == 'premium_payment' ? 'selected' : ''}>Premium Payment</option>
                        <option value="claim_payment" ${requestScope.transaction_type == 'claim_payment' ? 'selected' : ''}>Claim Payment</option>

                    </select>
                    <button type="submit">Find</button>
                </form>
                <form action="paginationInsuranceTransaction" method="get">
                    <label>Select quantity transaction: </label>
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
                            <th>Transaction ID</th>
                            <th>Contract ID</th>
                            <th>Customer Name</th>
                            <th>Transaction Date</th>
                            <th>amount</th>
                            <th>Transaction Type</th>
                            <th>Notes</th>


                        </tr>
                    </thead>
                    <c:forEach items="${listT}" var="T">
                        <tr>
                            <td>${T.transaction_id}</td>
                            <td>${T.contract_id}</td>
                            <td>${T.full_name}</td>
                            <td>${T.transaction_date}</td>
                            <td class="format-number">${T.amount}</td>
                            <td>${T.transaction_type}</td> 
                            <td>${T.notes}</td> 


                        </tr>
                    </c:forEach>
                </table>
                <c:forEach begin="1" end="${endP}" var="q">
                    <a href="paginationInsuranceTransaction?offset=${q}&quantity=${quantity}">${q}</a>
                </c:forEach>
            </div>
        </main>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let formatNumbers = document.querySelectorAll(".format-number");
                formatNumbers.forEach(function (el) {
                    let num = parseFloat(el.innerText);
                    if (!isNaN(num)) {
                        el.innerText = num.toLocaleString("en-US");
                    }
                });
            });
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