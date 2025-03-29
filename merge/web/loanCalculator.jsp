<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Mini Finance - Loan Calculator</title>
        <!-- CSS FILES -->      
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="css/tooplate-mini-finance.css" rel="stylesheet">
    </head>
    <body>
        <!-- HEADER -->
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="home.jsp">
                    <i class="bi-box"></i>
                    Mini Finance
                </a>
            </div>
            <div class="dropdown px-3">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid" alt="">
                </a>
                <ul class="dropdown-menu bg-white shadow">
                    <li>
                        <a class="dropdown-item" href="logout">
                            <i class="bi-box-arrow-left me-2"></i>
                            Logout
                        </a>
                    </li>
                </ul>
            </div>
        </header>

        <!-- CONTAINER -->
        <div class="container-fluid">
            <div class="row">
                <!-- SIDEBAR -->
                <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
                    <div class="position-sticky py-4 px-3 sidebar-sticky">
                        <ul class="nav flex-column">
                            <c:if test="${sessionScope.account.role_id==4}">
                        <li class="nav-item">
                        <a class="nav-link" href="list-insurance-contracts">
                            <i class="bi-house-fill me-2"></i>
                            List Insurance Contracts
                        </a>
                    </li>
                    </c:if>
                    
                    <c:if test="${sessionScope.account.role_id==4}">
                        <li class="nav-item">
                        <a class="nav-link" href="list-debt-customers">
                            <i class="bi-wallet me-2"></i>
                            List Debt Customer
                        </a>
                    </li>
                    </c:if>
                    
                    <c:if test="${sessionScope.account.role_id==4}">
                        <li class="nav-item">
                        <a class="nav-link" href="list-transactions">
                            <i class="bi-person me-2"></i>
                            List Transactions
                        </a>
                    </li>
                    </c:if>
                    
                    <c:if test="${sessionScope.account.role_id==4}">
                        <li class="nav-item">
                        <a class="nav-link" href="list-insurance-transactions">
                            <i class="bi-person me-2"></i>
                            List Insurance Transactions
                        </a>
                    </li>
                    </c:if>
                    
                    <li class="nav-item">
                        <a class="nav-link" href="amounts">
                            <i class="bi-person me-2"></i>
                            Amount Statistics
                        </a>
                    </li>
                    <c:if test="${sessionScope.account.role_id==4}">
                    <li class="nav-item">
                        <a class="nav-link" href="CustomerList_AServlet">
                            <i class="bi-person me-2"></i>
                            Accountant
                        </a>
                    </li>                   
                    </c:if>
                    <c:if test="${sessionScope.account.role_id==4}">
                    <li class="nav-item">
                        <a class="nav-link active" href="calculateLoan">
                            <i class="bi-person me-2"></i>
                            Loan Interest Calculator
                        </a>
                    </li>                   
                    </c:if>
                    <c:if test="${sessionScope.account.role_id==4}">
                    <li class="nav-item">
                        <a class="nav-link" href="calculateSaving">
                            <i class="bi-person me-2"></i>
                            Savings Interest Calculator
                        </a>
                    </li>                   
                    </c:if>
                    <c:if test="${sessionScope.account.role_id==4}">
                    <li class="nav-item">
                        <a class="nav-link" href="insuranceCalculator">
                            <i class="bi-person me-2"></i>
                            Insurance Calculator
                        </a>
                    </li>                   
                    </c:if> 
                        </ul>
                    </div>
                </nav>

                <!-- MAIN CONTENT -->
                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Loan Interest Calculator</h1>
                    </div>

                    <div class="custom-block bg-white p-4">
                        <form action="calculateLoan" method="post">
                            <div class="mb-3">
                                <label for="loanTermId" class="form-label">Choose Loan Terms:</label>
                                <select name="loanTermId" id="loanTermId" class="form-select">
                                    <c:forEach var="term" items="${loanTerms}">
                                        <option value="${term.term_id}"
                                                data-max-term="${term.duration}"
                                                data-min-payment="${term.min_payment}">
                                            ${term.term_name} - Interest rate: ${term.interest_rate}% - Min payment:VNĐ 
                                            <fmt:formatNumber value="${term.min_payment}" pattern="#,##0.000"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="loanAmount" class="form-label">Loan Amount (VNĐ):</label>
                                <input type="text" name="loanAmount" id="loanAmount" class="form-control" placeholder="Enter loan amount" required>
                            </div>

                            <script>
                                function formatLoanAmount(input) {
                                    // Lấy giá trị và loại bỏ ký tự không phải số hoặc dấu chấm
                                    let value = input.value.replace(/[^\d.]/g, '');
                                    // Chỉ cho phép một dấu chấm
                                    const parts = value.split('.');
                                    if (parts.length > 2) {
                                        value = parts[0] + '.' + parts.slice(1).join('');
                                    }
                                    // Tách phần nguyên và phần thập phân
                                    let [integerPart, decimalPart] = value.split('.');
                                    // Loại bỏ các dấu phẩy cũ
                                    integerPart = integerPart.replace(/,/g, '');
                                    // Định dạng phần nguyên với dấu phẩy
                                    integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                                    // Giới hạn phần thập phân tối đa 3 chữ số
                                    if (decimalPart) {
                                        decimalPart = decimalPart.substring(0, 3);
                                        input.value = integerPart + '.' + decimalPart;
                                    } else {
                                        input.value = integerPart;
                                    }
                                }

                                document.getElementById("loanAmount").addEventListener("input", function () {
                                    formatLoanAmount(this);
                                });
                            </script>
                            <div class="mb-3">
                                <label for="loanDuration" class="form-label">Loan Term (months):</label>
                                <input type="number" name="loanDuration" id="loanDuration" class="form-control" placeholder="Auto-filled" readonly>
                            </div>
                            <button type="submit" name="action" value="calculateLoan" class="btn btn-danger">Calculate Loan Interest</button>
                        </form>
                        <c:if test="${not empty loanError}">
                            <div class="alert alert-danger mt-3">${loanError}</div>
                        </c:if>
                        <c:if test="${not empty loanMonthlyPayment}">
                            <div class="alert alert-info mt-3">
                                <p>Monthly Payment: <strong>
                                        <fmt:formatNumber value="${loanMonthlyPayment}" pattern="#,##0.000"/>
                                    </strong> VNĐ</p>
                                <p>Total Amount Payable: <strong>
                                        <fmt:formatNumber value="${loanTotalPayment}" pattern="#,##0.000"/>
                                    </strong> VNĐ</p>
                                <p>Total Interest Payable: <strong>
                                        <fmt:formatNumber value="${loanTotalInterest}" pattern="#,##0.000"/>
                                    </strong> VNĐ</p>
                            </div>
                        </c:if>
                    </div>
                </main>
            </div>
        </div>

        <!-- FOOTER -->
        <footer class="site-footer">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 col-12">
                        <p class="copyright-text">
                            Copyright © Mini Finance 2048 
                            - Design: <a rel="sponsored" href="https://www.tooplate.com" target="_blank">Tooplate</a>
                        </p>
                    </div>
                </div>
            </div>
        </footer>

        <!-- JAVASCRIPT FILES -->
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/custom.js"></script>
        <script>
                                const loanTermSelect = document.getElementById("loanTermId");
                                const loanDurationInput = document.getElementById("loanDuration");
                                const loanAmountInput = document.getElementById("loanAmount");

                                loanTermSelect.addEventListener("change", function () {
                                    const selectedOption = this.options[this.selectedIndex];
                                    const maxTerm = selectedOption.getAttribute("data-max-term");
                                    const minPayment = selectedOption.getAttribute("data-min-payment");
                                    if (maxTerm) {
                                        loanDurationInput.value = maxTerm;
                                    } else {
                                        loanDurationInput.value = "";
                                    }
                                    if (minPayment) {
                                        loanAmountInput.min = minPayment;
                                        // Cập nhật placeholder để hiển thị min payment
                                    } else {
                                        loanAmountInput.removeAttribute("min");
                                        loanAmountInput.placeholder = "Enter loan amount";
                                    }
                                });
                                // Trigger change on page load
                                loanTermSelect.dispatchEvent(new Event("change"));

                                // Kiểm tra trước khi submit form
                                const form = document.querySelector("form");
                                form.addEventListener("submit", function (e) {
                                    const loanAmount = parseFloat(loanAmountInput.value);
                                    const minPayment = parseFloat(loanAmountInput.min);
                                    if (!isNaN(minPayment) && loanAmount < minPayment) {
                                        alert("Loan amount must be at least " + minPayment);
                                        e.preventDefault();
                                    }
                                });
        </script>
    </body>
</html>
