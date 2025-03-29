<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Savings" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Mini Finance - Danh sách sổ tiết kiệm</title>

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
                text-align: center;
            }

            .deposit-id {
                color: black;
                cursor: pointer;
                text-decoration: none;
            }

            .deposit-id:hover {
                text-decoration: underline;
            }

            .savings-table {
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 10px;
            }

            .row {
                display: flex;
                justify-content: space-between;
                width: 100%;
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
        </header>

        <div class="container-fluid">
            <div class="row">

                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Danh sách sổ tiết kiệm</h1>
                    </div>

                    <div class="custom-block custom-block-balance">
                        <%
                            List<Savings> savingsList = (List<Savings>) request.getAttribute("savingsList");
                            if (savingsList == null || savingsList.isEmpty()) {
                        %>
                        <p>Không có sổ tiết kiệm nào.</p>
                        <%
                            } else {
                                // Tính tổng số tiền tiết kiệm
                                double totalSavings = 0;
                                for (Savings savings : savingsList) {
                                    totalSavings += savings.getAmount();
                                }
                        %>                      

                        <div class="savings-list">
                            <%
                                for (Savings savings : savingsList) {
                            %>
                            <div class="savings-item">
                                <p><strong>Mã sổ tiết kiệm:</strong> 
                                    <span class="deposit-id"><%= savings.getSavings_id() %></span>
                                </p>
                                <div id="<%= savings.getSavings_id() %>" class="savings-details">
                                    <div class="savings-table">
                                        <div class="row">
                                            <span class="label">Mã khách hàng:</span>
                                            <span class="value"><%= savings.getCustomer_id() %></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Số tiền:</span>
                                            <span class="value"><fmt:formatNumber value="<%= savings.getAmount() %>" pattern="#,##0.00" />VND</span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Ngày bắt đầu:</span>
                                            <span class="value"><fmt:formatDate value="<%= savings.getStart_date() %>" pattern="dd-MM-yyyy" /></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Ngày kết thúc:</span>
                                            <span class="value"><fmt:formatDate value="<%= savings.getEnd_date() %>" pattern="dd-MM-yyyy" /></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Trạng thái:</span>
                                            <span class="value"><%= savings.getStatus() %></span>
                                        </div>
                                        <div class="row">
                                            <span class="label">Ghi chú:</span>
                                            <span class="value"><%= savings.getNotes() != null ? savings.getNotes() : "Không có" %></span>
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
