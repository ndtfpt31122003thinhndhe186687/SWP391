<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mini Finance - Wallet</title>

    <!-- CSS FILES -->      
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-icons.css" rel="stylesheet">
    <link href="css/tooplate-mini-finance.css" rel="stylesheet">

    <style>
        .transaction-date {
            font-size: 1.25rem;
            font-weight: bold;
            padding: 1rem 0;
            border-bottom: 1px solid #dee2e6;
            margin-bottom: 1rem;
        }

        .transaction-item {
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 0.5rem;
            background-color: #fff;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }

        .transaction-time {
            color: #6c757d;
            font-size: 0.9rem;
        }

        .transaction-amount {
            font-weight: bold;
            font-size: 1.1rem;
        }

        .transaction-description {
            color: #6c757d;
            margin-top: 0.5rem;
        }

        .transaction-amount.deposit {
            color: #28a745 !important;
        }

        .transaction-amount.withdrawal {
            color: #dc3545 !important;
        }
        .pagination {
            display: flex;
            justify-content: center;
            list-style: none;
            padding: 0;
        }
        .pagination li {
            margin: 0 5px;
        }
        .pagination a {
            color: #dc3545;
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #dc3545;
            border-radius: 5px;
        }
        .pagination .active a {
            background-color: #dc3545;
            color: white;
        }
    </style>
</head>
<body>
    <header class="navbar sticky-top flex-md-nowrap bg-danger">
        <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
            <a class="navbar-brand text-white" href="home.jsp">
                <i class="bi-box"></i>
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
                            <a class="nav-link" href="index.html">
                                <i class="bi-house-fill me-2"></i>
                                Overview
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="wallet">
                                <i class="bi-wallet me-2"></i>
                                My Wallet
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                <div class="title-group mb-3">
                    <h1 class="h2 mb-0 text-danger">My Wallet</h1>
                </div>

                <div class="row my-4">
                    <div class="col-lg-12 col-12">
                        <div class="custom-block bg-white">
                            <h6 class="mb-4 text-danger">Customer Information</h6>
                            <div class="custom-block-profile mb-5">
                                <p><strong>ID :</strong> ${customer.customer_id}</p>
                                <p><strong>Name Customer:</strong> ${customer.full_name}</p>
                            </div>

                            <h6 class="mb-4 text-danger">Transaction History</h6>
                            <div class="custom-block-profile bg-light p-3 mb-4">
                                <form action="wallet" method="get" class="row g-3">
                                    <div class="col-md-4">
                                        <label for="startDate" class="form-label">From Date:</label>
                                        <input type="text" class="form-control" id="startDate" name="startDate" value="${param.startDate}" required>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="endDate" class="form-label">To Date:</label>
                                        <input type="text" class="form-control" id="endDate" name="endDate" value="${param.endDate}" required>
                                    </div>
                                    <div class="col-md-4 d-flex align-items-end">
                                        <button type="submit" class="btn btn-danger">Filter</button>
                                    </div>
                                    <div>
                                        <c:if test="${not empty err}">
                                            <span class="text-danger">${requestScope.err}</span>
                                        </c:if>
                                    </div>
                                </form>
                            </div>

                            <!-- Transactions display -->
                            <c:if test="${not empty transactions}">
                                <div class="transactions-container">
                                    <c:set var="currentDate" value="" />
                                    <c:forEach var="transaction" items="${transactions}">
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
                                                            <c:when test="${transaction.transaction_type eq 'withdrawal'}">
                                                                <i class="bi bi-arrow-right-circle text-danger"></i> Withdrawal
                                                            </c:when>
                                                            <c:otherwise>
                                                                <i class="bi bi-arrow-down-circle text-success"></i> Deposit
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div>CUSTOMER ${customer.full_name}</div>
                                                    <div class="transaction-description">
                                                        Transaction - Transaction ID/ Trace ${transaction.transaction_id}
                                                    </div>
                                                </div>
                                                <div class="text-end">
                                                    <div class="transaction-amount ${transaction.transaction_type}">
                                                        <c:choose>
                                                            <c:when test="${transaction.transaction_type eq 'withdrawal'}">
                                                                -${Math.abs(transaction.amount)} $
                                                            </c:when>
                                                            <c:otherwise>
                                                                +${Math.abs(transaction.amount)} $
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="transaction-time">
                                                        <fmt:formatDate value="${transaction.transaction_date}" pattern="HH:mm" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Nút Xuất PDF -->
                                <div class="text-center mt-3">
                                    <form action="wallet" method="post">
                                        <input type="hidden" name="startDate" value="${param.startDate}">
                                        <input type="hidden" name="endDate" value="${param.endDate}">
                                        <input type="hidden" name="action" value="exportPDF">
                                        <button type="submit" class="btn btn-primary">Xuất PDF</button>
                                    </form>
                                </div>

                                <div class="pagination">
                                    <c:if test="${currentPage > 1}">
                                        <li><a href="wallet?page=${currentPage - 1}&startDate=${param.startDate}&endDate=${param.endDate}">« Previous</a></li>
                                    </c:if>
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <li class="${i == currentPage ? 'active' : ''}">
                                            <a href="wallet?page=${i}&startDate=${param.startDate}&endDate=${param.endDate}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <c:if test="${currentPage < totalPages}">
                                        <li><a href="wallet?page=${currentPage + 1}&startDate=${param.startDate}&endDate=${param.endDate}">Next »</a></li>
                                    </c:if>
                                </div>
                            </c:if>
                        </div>
                    </div>
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

        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/custom.js"></script>
</body>
</html>