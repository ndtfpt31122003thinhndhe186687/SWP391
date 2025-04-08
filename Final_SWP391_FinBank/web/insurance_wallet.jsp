<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Mini Finance - Giao dịch bảo hiểm</title>

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="css/tooplate-mini-finance.css" rel="stylesheet">
    </head>
    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="home.jsp">
                    <i class="bi-shield-check"></i>
                    Mini Finance
                </a>
            </div>
        </header>

        <div class="container-fluid">
            <div class="row">
                <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
                    <div class="position-sticky py-4 px-3 sidebar-sticky">
                        <ul class="nav flex-column h-100">
                            <li class="nav-item">
                                <a class="nav-link " aria-current="page" href="balanceCustomer">
                                    <i class="bi-house-fill me-2"></i> Tổng quan
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="wallet">
                                    <i class="bi-wallet me-2"></i> Ví của tôi
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="insuranceWallet">
                                    <i class="bi-shield-check me-2"></i> Lịch sử bảo hiểm
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="transferAmount">
                                    <i class="bi-arrow-left-right me-2"></i> Chuyển tiền
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " href="viewprofile">
                                    <i class="bi-person me-2"></i> Hồ sơ
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="savingList">
                                    <i class="bi-piggy-bank me-2"></i> Sổ tiết kiệm
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="loanList">
                                    <i class="bi-bank me-2"></i> Vay
                                </a>
                            </li>
                            <c:if test="${sessionScope.account.role_id==6}">
                                <c:if test="${sessionScope.account.card_type == 'credit' && sessionScope.account.credit_limit == 0 }">
                                    <li class="nav-item">
                                        <a class="nav-link" href="registerCreditCard">
                                            <i class="bi-credit-card me-2"></i> Đăng Ký Thẻ Tín Dụng
                                        </a>
                                    </li>
                                </c:if>
                            </c:if>
                            <li class="nav-item">
                                <a class="nav-link" href="notificationsList">
                                    <i class="bi-bell me-2"></i> Thông báo
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="CustomerInsuranceList">
                                    <i class="bi-shield-lock me-2"></i> Bảo hiểm
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="changeInfor">
                                    <i class="bi-gear me-2"></i> Cài đặt
                                </a>
                            </li>
                            <li class="nav-item border-top mt-auto pt-2">
                                <a class="nav-link" href="logout">
                                    <i class="bi-box-arrow-left me-2"></i> Đăng xuất
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Insurance Wallet</h1>
                    </div>

                    <div class="row my-4">
                        <div class="col-lg-12 col-12">
                            <div class="custom-block bg-white">
                                <h6 class="mb-4 text-danger">Người dùng</h6>
                                <div class="custom-block-profile mb-5">
                                    <p><strong>Mã người dùng :</strong> ${customer.customer_id}</p>
                                    <p><strong>Tên Người Dùng:</strong> ${customer.full_name}</p>
                                </div>

                                <h6 class="mb-4 text-danger">Lịch sử giao dịch bảo hiểm</h6>
                                <div class="custom-block-profile bg-light p-3 mb-4">
                                    <form action="insuranceWallet" method="get" class="row g-3">
                                        <div class="col-md-4">
                                            <label for="startDate" class="form-label">Từ ngày:</label>
                                            <input type="text" class="form-control" id="startDate" name="startDate" value="${param.startDate}" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="endDate" class="form-label">Đến ngày:</label>
                                            <input type="text" class="form-control" id="endDate" name="endDate" value="${param.endDate}" required>
                                        </div>
                                        <div class="col-md-4 d-flex align-items-end">
                                            <button type="submit" class="btn btn-danger">Lọc</button>
                                        </div>
                                        <div>
                                            <c:if test="${not empty err}">
                                                <span class="text-danger">${requestScope.err}</span>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>

                                <c:if test="${not empty insuranceTransactions}">
                                    <div class="transactions-container">
                                        <c:set var="currentDate" value="" />
                                        <c:forEach var="transaction" items="${insuranceTransactions}">
                                            <fmt:formatDate value="${transaction.transaction_date}" pattern="dd/MM/yyyy" var="transactionDate" />
                                            <c:if test="${currentDate ne transactionDate}">
                                                <div class="transaction-date">${transactionDate}</div>
                                                <c:set var="currentDate" value="${transactionDate}" />
                                            </c:if>

                                            <div class="transaction-item">
                                                <div class="d-flex justify-content-between align-items-start">
                                                    <div>
                                                        <div class="transaction-description">
                                                            <c:choose>
                                                                <c:when test="${transaction.transaction_type eq 'premium_payment'}">
                                                                    <i class="bi bi-arrow-up-circle text-danger"></i> Đóng phí bảo hiểm
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <i class="bi bi-arrow-down-circle text-success"></i> Nhận bồi thường
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div>Hợp đồng: #${transaction.contract_id}</div>
                                                        <div class="transaction-description">${transaction.notes}</div>
                                                    </div>
                                                    <div class="text-end">
                                                        <fmt:setLocale value="vi_VN" />
                                                        <div class="transaction-amount ${transaction.transaction_type}">
                                                            <c:choose>
                                                                <c:when test="${transaction.transaction_type eq 'premium_payment'}">
                                                                    - <fmt:formatNumber value="${transaction.amount}" type="number" groupingUsed="true" /> VND
                                                                </c:when>
                                                                <c:otherwise>
                                                                    + <fmt:formatNumber value="${transaction.amount}" type="number" groupingUsed="true" /> VND
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div class="transaction-time">
                                                            <fmt:formatDate value="${transaction.transaction_date}" pattern="HH:mm:ss"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <div class="text-center mt-3">
                                        <form action="insuranceWallet" method="post">
                                            <input type="hidden" name="startDate" value="${param.startDate}">
                                            <input type="hidden" name="endDate" value="${param.endDate}">
                                            <input type="hidden" name="action" value="exportPDF">
                                            <button type="submit" class="btn btn-primary">Xuất PDF</button>
                                        </form>
                                    </div>

                                    <div class="pagination mt-4">
                                        <c:if test="${currentPage > 1}">
                                            <li><a href="insuranceWallet?page=${currentPage - 1}&startDate=${param.startDate}&endDate=${param.endDate}">« Trước</a></li>
                                            </c:if>
                                            <c:forEach var="i" begin="1" end="${totalPages}">
                                            <li class="${i == currentPage ? 'active' : ''}">
                                                <a href="insuranceWallet?page=${i}&startDate=${param.startDate}&endDate=${param.endDate}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${currentPage < totalPages}">
                                            <li><a href="insuranceWallet?page=${currentPage + 1}&startDate=${param.startDate}&endDate=${param.endDate}">Tiếp »</a></li>
                                            </c:if>
                                    </div>
                                </c:if>

                                <c:if test="${empty insuranceTransactions}">
                                    <div class="alert alert-info">Không có giao dịch nào.</div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <footer class="site-footer">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-12 col-12">
                                    <p class="copyright-text">Copyright © Mini Finance 2048 - Design: <a rel="sponsored" href="https://www.tooplate.com" target="_blank">Tooplate</a></p>
                                </div>
                            </div>
                        </div>
                    </footer>
                </main>
            </div>
        </div>
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>
