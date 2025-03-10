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
                max-width: 900px; /* Kích th??c modal t??ng t? */
            }

            .modal-content {
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            }

            .modal-header {
                background-color: #dc3545;
                color: white;
                border-bottom: none;
            }

            .modal-title {
                font-size: 20px;
                font-weight: bold;
            }

            .modal-body label {
                font-weight: bold;
                font-size: 16px;
                margin-top: 10px;
            }

            .modal-body input,
            .modal-body select {
                font-size: 16px;
                padding: 10px;
                height: 50px;
                border-radius: 5px;
                border: 1px solid #ccc;
            }

            .modal-footer {
                border-top: none;
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

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="searchByPolicyName" method="post" role="form">
                <input class="form-control bg-white text-dark" name="search_policy_name" type="text" placeholder="Search" aria-label="Search">
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
                        <a class="nav-link " href="sortInsurancePolicy?sortInsurancePolicy=none&status=all&quantity=5&offset=1">
                            <i class="me-2"></i>
                            Quản lý chính sách bảo hiểm
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="sortInsuranceTerm?sortInsuranceTerm=none&status=all&quantity=5&offset=1">
                            <i class="me-2"></i>
                            Quản lý điều khoản bảo hiểm
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="filterInsuranceCustomer?gender=all&quantity=5&offset=1">
                            <i class="me-2"></i>
                            Quản lý khách hàng đã mua bảo hiểm
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="sortInsuranceContract?sortInsuranceContract=none&status=all&quantity=5&offset=1">
                            <i class=" me-2"></i>
                            Quản lý hợp đồng bảo hiểm
                        </a>
                    </li>                   

                    <li class="nav-item">
                        <a class="nav-link " href="sortInsuranceTransaction?sortInsuranceTransaction=none&transaction_type=all&quantity=5&offset=1">
                            <i class="me-2"></i>
                            Quản lý giao dịch bảo hiểm
                        </a>
                    </li>




                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Quản lý chính sách bảo hiểm</h1>
            </div>

            <!-- Tabs choose staff -->


            <!-- View list staff -->
            <div class="mt-3">
                <button class="btn btn-success mb-2" data-bs-toggle="modal" data-bs-target="#addPolicyModal">Thêm chính sách mới</button>
                <form action="sortInsurancePolicy" method="get">
                    <label>Sắp xếp theo :</label>
                    <select class="filter-dropdown" name="sortInsurancePolicy">
                        <option value="none" ${requestScope.sortInsurancePolicy == '' ? 'selected' : ''}>Không</option>    
                        <option value="created_at" ${requestScope.sortInsurancePolicy == 'created_at' ? 'selected' : ''}>Ngày tạo</option>
                        <option value="coverage_amount" ${requestScope.sortInsurancePolicy == 'coverage_amount' ? 'selected' : ''}>Số tiền được nhận</option>
                    </select>
                    <label>Hiện thông tin theo trạng thái:</label>
                    <select class="filter-dropdown" name="status">                    
                        <option value="all" ${requestScope.status == '' ? 'selected' : ''}>Tất cả</option>
                        <option value="active" ${requestScope.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                        <option value="inactive" ${requestScope.status == 'inactive' ? 'selected' : ''}>Ngừng hoạt động</option>

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
                            <th>ID chính sách</th>
                            <th>Tên chính sách</th>                          
                            <th>Trạng thái</th>
                            <th>Ngày tạo</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <c:forEach items="${listPolicy}" var="P">
                        <tr>
                            <td>${P.policy_id}</td>
                            <td><a href="#" class="text-dark" data-bs-toggle="modal" data-bs-target="#policyModal${P.policy_id}">${P.policy_name}</a></td>
                            <td>${P.status}</td> 
                            <td>${P.created_at}</td>
                            <td>
                                <a href="#" onclick="doDelete('${P.policy_id}')" class="btn btn-danger">Xoá</a>
                                <a href="updatePolicy?policy_id=${P.policy_id}" class="btn btn-success">Sửa</a> 
                            </td>
                        </tr>

                        <div class="modal fade" id="policyModal${P.policy_id}" tabindex="-1" aria-labelledby="policyModalLabel${P.policy_id}" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="policyModalLabel${P.policy_id}">Chi tiết chính sách: ${P.policy_name}</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p><strong>ID Chính sách:</strong> ${P.policy_id}</p>
                                        <p><strong>Mô tả:</strong> ${P.description}</p>
                                        <p><strong>Số tiền được nhận:</strong> <span class="format-number">${P.coverage_amount}</span></p>
                                        <p><strong>Số tiền cần đóng:</strong> <span class="format-number">${P.premium_amount}</span></p>
                                        <p><strong>Trạng thái:</strong> ${P.status}</p>
                                        <p><strong>Ngày tạo:</strong> ${P.created_at}</p>
                                        <p><strong>Ảnh:</strong></p>
                                        <img src="InsurancePolicy/${P.image}" alt="Ảnh chính sách" width="100%" style="object-fit: cover; border-radius: 5px;">
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
                    <a href="sortInsurancePolicy?sortInsurancePolicy=${sortInsurancePolicy}&status=${status}&quantity=${quantity}&offset=${q}">${q}</a>
                </c:forEach>
            </div>

            <div class="modal fade" id="addPolicyModal" tabindex="-1" aria-labelledby="addPolicyModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addPolicyModalLabel">Thêm chính sách mới</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <% String error = (String) request.getAttribute("error"); %>
                            <% if (error != null) { %>
                            <div class="alert alert-danger"><%= error %></div>
                            <% } %>
                            <form id="addPolicyForm" action="addPolicy" method="post" enctype="multipart/form-data">
                                <label>Nhập tên</label>
                                <textarea name="policy_name"  required class="form-control"></textarea>

                                <label>Nhập mô tả</label>
                                <textarea name="description" id="editor1" required class="form-control"></textarea>


                                <label>Nhập số tiền được nhận</label>
                                <input type="text" name="coverage_amount" required class="form-control" />

                                <label>Nhập số tiền cần đóng</label>
                                <input type="text" name="premium_amount" required class="form-control" />

                                <label>Trạng thái</label>
                                <select class="form-control" name="status">
                                    <option value="active">Hoạt động</option>
                                    <option value="inactive">Ngừng hoạt động</option>
                                </select>
                                <div class="form-group">
                                    <label for="file">Ảnh</label>
                                    <input style="margin-bottom: 5px;margin-top: 5px;" type="file" name="file" id="file" accept="image/png, image/jpg, image/jpeg">
                                </div>
                                <button type="submit" class="btn btn-danger mt-3">Thêm chính sách</button>
                            </form>
                        </div>
                    </div>
                </div>

            </div>



        </main>

        <script type="text/javascript">
            function doDelete(id) {
                if (confirm("Bạn có chắc chắn muốn xoá '" + id + "'?")) {
                    window.location = "deletePolicy?policy_id=" + id;
                }
            }

        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var hasError = "<%= (error != null) ? "true" : "false" %>";
                if (hasError === "true") {
                    var modal = new bootstrap.Modal(document.getElementById("addPolicyModal"));
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
                        <h5 class="modal-title" id="successModalLabel">Thành công</h5>
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
            sessionSuccess.removeAttribute("showSuccessModal");
            sessionSuccess.removeAttribute("successMessage");
            }
        %>

        <script src="ckfinder/ckfinder.js"></script>
        <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>

        <script>
            CKEDITOR.replace('editor1');
        </script>




        <script>
            document.addEventListener("DOMContentLoaded", function () {
                function formatNumberInput(input) {
                    input.addEventListener("input", function () {
                        let rawValue = input.value.replace(/,/g, "").replace(/[^0-9.]/g, '');
                        let cursorPosition = input.selectionStart; // Lưu vị trí con trỏ
                        let beforeCursor = input.value.slice(0, cursorPosition).replace(/,/g, ""); // Phần trước con trỏ

                        if (!isNaN(rawValue) && rawValue !== "") {
                            let formattedValue = Number(rawValue).toLocaleString("en-US", {
                                minimumFractionDigits: 2,
                                maximumFractionDigits: 2
                            });

                            // Tính lại vị trí con trỏ
                            let newCursorPos = 0;
                            let countDigits = 0;
                            for (let i = 0; i < formattedValue.length; i++) {
                                if (formattedValue[i].match(/[0-9]/))
                                    countDigits++;
                                if (countDigits >= beforeCursor.length) {
                                    newCursorPos = i + 1;
                                    break;
                                }
                            }

                            input.value = formattedValue;

                            setTimeout(() => {
                                input.selectionStart = input.selectionEnd = newCursorPos;
                            }, 0);
                        }
                    });

                    // Format khi load trang
                    input.value = Number(input.value.replace(/,/g, "") || 0).toLocaleString("en-US", {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    });
                }

                let coverageAmountInput = document.querySelector("input[name='coverage_amount']");
                let premiumAmountInput = document.querySelector("input[name='premium_amount']");

                if (coverageAmountInput)
                    formatNumberInput(coverageAmountInput);
                if (premiumAmountInput)
                    formatNumberInput(premiumAmountInput);

                // 📌 Định dạng hiển thị số có VND (Không ảnh hưởng đến input)
                let formatNumbers = document.querySelectorAll(".format-number");
                formatNumbers.forEach(function (el) {
                    let num = parseFloat(el.innerText.replace(/,/g, ""));
                    if (!isNaN(num)) {
                        el.innerText = num.toLocaleString("en-US", {
                            minimumFractionDigits: 2,
                            maximumFractionDigits: 2
                        }) + " VND";
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