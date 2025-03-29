<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Loan" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Mini Finance - Danh sách khoản vay</title>

        <!-- CSS FILES -->      
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="css/tooplate-mini-finance.css" rel="stylesheet">

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
                color: black;
                cursor: pointer;
                text-decoration: none; /* Loại bỏ gạch chân */
            }

            .deposit-id:hover {
                text-decoration: underline; /* Chỉ hiện gạch chân khi di chuột vào */
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

            /* Hiển thị tất cả chi tiết khoản vay mà không cần nhấp vào */
            .savings-details {
                display: block !important;
                margin-top: 15px;
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

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="#" method="get" role="form">
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


        </header>

        <div class="container-fluid">
            <div class="row">

                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Danh sách khoản vay</h1>
                    </div>

                    <div class="custom-block custom-block-balance">
                        <%
                            List<Loan> loanList = (List<Loan>) request.getAttribute("loanList");
                            if (loanList == null || loanList.isEmpty()) {
                        %>
                        <p>Không có khoản vay nào.</p>
                        <%
                            } else {
                                // Tính tổng số tiền vay
                                double totalLoan = 0;
                                for (Loan loan : loanList) {
                                    totalLoan += loan.getAmount();
                                }
                        %>                      

                        <div class="savings-list">
                            <%
                                for (Loan loan : loanList) {
                            %>
                            <div class="savings-item">
                                <p><strong>Mã khoản vay:</strong> 
                                    <span class="deposit-id"><%= loan.getLoan_id() %></span>
                                </p>
                                <div id="<%= loan.getLoan_id() %>" class="savings-details">
                                    <div class="savings-table">
                                        <div class="row">
                                            <span class="label">Mã khách hàng:</span>
                                            <span class="value"><%= loan.getCustomer_id() %></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Số tiền:</span>
                                            <span class="value"><fmt:formatNumber value="<%= loan.getAmount() %>" pattern="#,##0.00" />VND</span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Ngày bắt đầu:</span>
                                            <span class="value"><fmt:formatDate value="<%= loan.getStart_date() %>" pattern="dd-MM-yyyy" /></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Ngày kết thúc:</span>
                                            <span class="value"><fmt:formatDate value="<%= loan.getEnd_date() %>" pattern="dd-MM-yyyy" /></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Loại khoản vay:</span>
                                            <span class="value"><%= loan.getLoan_type() %></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Giá trị tài sản:</span>
                                            <span class="value"><fmt:formatNumber value="<%= loan.getValue_asset() %>" pattern="#,##0.00" />VND</span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Trạng thái:</span>
                                            <span class="value"><%= loan.getStatus() %></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Ảnh tài sản:</span>
                                            <span class="value">
                                                <%
                                                    if (loan.getLoan_type().equals("secured")) {
                                                %>
                                                <img src="imageAsset/<%= loan.getAsset_image() %>" width="500px"/>
                                                <%
                                                    } else {
                                                %>
                                                <a href="uploads/<%= loan.getAsset_image() %>" target="_blank"><%= loan.getAsset_image() %></a>
                                                <%
                                                    }
                                                %>
                                            </span>

                                        </div>
                                        <div class="row">
                                            <span class="label">Ghi chú:</span>
                                            <span class="value"><%= loan.getNotes() != null ? loan.getNotes() : "Không có" %></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Loại thanh toán:</span>
                                            <span class="value"><%= loan.getTerms() %></span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <%
                                }
                            %>
                        </div>
                        <%
                            }
                        %>
                    </div>

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
                </main>
            </div>
        </div>

        <!-- JAVASCRIPT FILES -->
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>