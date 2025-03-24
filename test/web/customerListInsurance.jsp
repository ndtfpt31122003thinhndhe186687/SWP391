<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta name="description" content="">
        <meta name="author" content="">

        <title>Mini Finance - Profile Page</title>

        <!-- CSS FILES -->      
        <link rel="preconnect" href="https://fonts.googleapis.com">

        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">

        <link href="css/bootstrap.min.css" rel="stylesheet">

        <link href="css/bootstrap-icons.css" rel="stylesheet">

        <link href="css/tooplate-mini-finance.css" rel="stylesheet">
        <!--
        
        Tooplate 2135 Mini Finance
        
        https://www.tooplate.com/view/2135-mini-finance
        
        Bootstrap 5 Dashboard Admin Template
        
        -->
        <style>
            .savings-item {
                background: #fff;
                padding: 15px;
                border-radius: 8px;
                box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.1);
                margin-bottom: 10px;
                text-align: center; /* Căn giữa nội dung */
            }

            .deposit-id {
                color: #007bff;
                cursor: pointer;
                text-decoration: underline;
            }

            .savings-table {
                display: flex;
                flex-direction: column;
                align-items: center; /* Căn giữa nội dung */
                gap: 10px;
            }

            .row {
                display: flex;
                justify-content: space-between; /* Căn cách tiêu đề và giá trị */
                width: 100%; /* Giới hạn chiều rộng */
                padding: 5px 0;
                border-bottom: 1px solid #ddd;
            }

            .label {
                font-weight: bold;
                flex: 1;
                text-align: left;
            }

            .value {
                flex: 1;
                text-align: right;
            }

            .accrued-interest {
                font-size: 1.2rem;
                font-weight: bold;
                color: #28a745;
            }
            .deposit-id {
                color: black;
                cursor: pointer;
                text-decoration: none; /* Loại bỏ gạch chân */
            }

            .deposit-id:hover {
                text-decoration: underline; /* Chỉ hiện gạch chân khi di chuột vào */
            }

            .hidden {
                display: none;
            }
                /* Tổng thể */
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        margin: 20px;
    }

    /* Tiêu đề */
    .title-group h1 {
        color: #dc3545;
        font-weight: bold;
        text-align: center;
        margin-bottom: 20px;
    }

    /* Bảng */
    .table {
        width: 100%;
        border-collapse: collapse;
        background-color: white;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        overflow: hidden;
    }

    .table thead {
        background-color: #dc3545;
        color: white;
        text-align: center;
    }

    .table thead th {
        padding: 12px;
        font-size: 16px;
    }

    .table tbody tr {
        text-align: center;
        border-bottom: 1px solid #ddd;
    }

    .table tbody tr:hover {
        background-color: #f8d7da;
        transition: 0.3s;
    }

    .table td {
        padding: 10px;
        font-size: 14px;
        color: #333;
    }

    /* Định dạng số tiền */
    .format-number {
        font-weight: bold;
        color: #dc3545;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .table {
            font-size: 12px;
        }

        .table thead th, .table td {
            padding: 8px;
        }
    }


        </style>
    </head>

    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="index.html">
                    <i class="bi-box"></i>
                    Mini Finance
                </a>
            </div>

            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="searchCustomerInsurance" method="get" role="form">
                <input class="form-control bg-white text-dark" name="search_contract_name" type="text" placeholder="Search" aria-label="Search">
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
                                <small>${sessionScope.account.full_name}</small>
                                <small>${sessionScope.account.email}</small>
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
        </header>

        <div class="container-fluid">
            <div class="row">
                <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
                    <div class="position-sticky py-4 px-3 sidebar-sticky">
                        <ul class="nav flex-column h-100">
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="index.html">
                                    <i class="bi-house-fill me-2"></i>
                                    Tổng quan                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="wallet.html">
                                    <i class="bi-wallet me-2"></i>
                                    Ví của tôi
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="viewprofile">
                                    <i class="bi-person me-2"></i>
                                    Hồ sơ
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link " href="savingList">
                                    <i class="bi-person me-2"></i>
                                    Sổ tiết kiệm 
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link active" href="loanList">
                                    <i class="bi-person me-2"></i>
                                    Vay 
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="CustomerInsuranceList">
                                    <i class="bi-gear me-2"></i>
                                    Bảo hiểm
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="changeInfor">
                                    <i class="bi-gear me-2"></i>
                                    Cài đặt
                                </a>
                            </li>

                            <li class="nav-item border-top mt-auto pt-2">
                                <a class="nav-link" href="logout">
                                    <i class="bi-box-arrow-left me-2"></i>
                                    Đăng xuất
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Danh sách bảo hiểm đã mua</h1>    
                    </div>
                    <div class="mt-3">
                <form action="sortCustomerInsuranceList" method="get">
                    <label>Sắp xếp theo :</label>
                    <select class="filter-dropdown" name="sortCustomerInsurance">
                        <option value="none" ${requestScope.sortCustomerInsurance == '' ? 'selected' : ''}>Không</option>    
                        <option value="duration" ${requestScope.sortCustomerInsurance == 'duration' ? 'selected' : ''}>Thời hạn hợp đồng</option>
                       
                    </select>
                            <label>Hiện thông tin theo khoản vay:</label>
                    <select class="filter-dropdown" name="loan_id">                    
                        <option value="0" ${requestScope.loan_id == '' ? 'selected' : ''}>Tất cả</option>
                        <c:forEach items="${listL}" var="L">
                        <option value="${L.loan_id}" ${requestScope.loan_id == L.loan_id ? 'selected' : ''}>${L.notes}</option>
                        </c:forEach>                      
                    </select>
                            <label>Hiện thông tin theo tên bảo hiểm: </label>
                    <select class="filter-dropdown" name="insurance_id">                    
                        <option value="0" ${requestScope.insurance_id == '' ? 'selected' : ''}>Tất cả</option>
                        <c:forEach items="${listI}" var="I">
                        <option value="${I.insurance_id}" ${requestScope.insurance_id == I.insurance_id ? 'selected' : ''}>${I.insurance_name}</option>
                        </c:forEach>
                    </select>
                        
                    
                    <br>
                    <label>Chọn số lượng chính sách: </label>
                    <select class="filter-dropdown" name="quantity">                    
                        <option value="5" ${requestScope.quantity == '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${requestScope.quantity == '10' ? 'selected' : ''}>10</option>
                        <option value="15" ${requestScope.quantity == '15' ? 'selected' : ''}>15</option>                  
                    </select>
                    <button type="submit">Tìm</button>
                </form>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID hợp đồng</th>
                                <th>Tên hợp đồng</th>
                                <th>Tên bảo hiểm</th>
                                <th>Chính sách</th>
                                <th>Tên khoản vay</th>
                                <th>Thời hạn</th>
                                <th>Ngày bắt đầu</th>
                                <th>Ngày kết thúc</th>
                                <th>Chi tiết</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${listC}">
                                <tr>
                                    <td>${c.contract_id}</td>
                                    <td>${c.contract_name}</td>
                                    <td>${c.insurance_name}</td>
                                    <td>${c.policy_name}</td>    
                                    <td>${c.notes}</td> 
                                    <td>${c.duration} tháng</td>
                                    <td><fmt:formatDate value="${c.start_date}" pattern="dd-MM-yyyy" /></td>
                                    <td><fmt:formatDate value="${c.end_date}" pattern="dd-MM-yyyy" /></td>
                                    <td>
                                        <a href="#" onclick="toggleDetails('${c.contract_id}', '${c.insurance_id}', '${c.policy_id}')">Xem</a>


                                    </td>
                                </tr>
                                <tr id="details-${c.contract_id}-${c.insurance_id}-${c.policy_id}" class="hidden">





                                    <td colspan="7">
                                        <strong>Tần suất thanh toán:</strong> ${c.payment_frequency == 'monthly' ? 'Tháng' : (c.payment_frequency == 'quarterly' ? 'Quý' : 'Năm')}<br>
                                        <strong>Số tiền bảo hiểm:</strong> <fmt:formatNumber value="${c.coverageAmount}" pattern="#,##0.00" /> VND<br>
                                        <strong>Phí bảo hiểm:</strong> <fmt:formatNumber value="${c.premiumAmount}" pattern="#,##0.00" /> VND <br>
                                        <strong>Số tiền đã thanh toán:</strong> <fmt:formatNumber value="${c.paidAmount}" pattern="#,##0.00" /> VND<br>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                     <c:forEach begin="1" end="${endP}" var="q">
                    <a href="sortCustomerInsuranceList?sortCustomerInsurance=${sortCustomerInsurance}&loan_id=${loan_id}&insurance_id=${insurance_id}&quantity=${quantity}&offset=${q}">${q}</a>
                </c:forEach>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <script>
                        function toggleDetails(contract_id, insurance_id, policy_id) {
                            console.log("Clicked with:", contract_id, insurance_id, policy_id);
                            let id = "details-" + contract_id + "-" + insurance_id + "-" + policy_id;
                            console.log("Looking for element with ID:", id);

                            let detailsRow = document.getElementById(id);

                            if (detailsRow) {
                                console.log("Element found, toggling class");
                                detailsRow.classList.toggle('hidden');
                            } else {
                                console.error("Element NOT FOUND:", id);
                            }
                        }
                    </script>


                    <footer class="site-footer">
                        <div class="container">
                            <div class="row">

                                <div class="col-lg-12 col-12">
                                    <p class="copyright-text">Copyright © Mini Finance 2048 
                                        - Design: <a rel="sponsored" href="https://www.tooplate.com" target="_blank">Tooplate</a></p>
                                </div>

                            </div>
                        </div>
                    </footer>
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