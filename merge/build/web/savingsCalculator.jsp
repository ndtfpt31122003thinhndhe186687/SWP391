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
    <title>Mini Finance - Savings Calculator</title>
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
          <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid" alt="Profile">
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
                        <a class="nav-link" href="calculateLoan">
                            <i class="bi-person me-2"></i>
                            Loan Interest Calculator
                        </a>
                    </li>                   
                    </c:if>
                    <c:if test="${sessionScope.account.role_id==4}">
                    <li class="nav-item">
                        <a class="nav-link active" href="calculateSaving">
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
            <h1 class="h2 mb-0 text-danger">Savings Interest Calculator</h1>
          </div>
          
          <div class="custom-block bg-white p-4">
            <form action="calculateSaving" method="post">
              <div class="mb-3">
                <label for="savingTermId" class="form-label">Choose Savings Terms:</label>
                <select name="savingTermId" id="savingTermId" class="form-select">
                  <c:forEach var="term" items="${savingTerms}">
                    <option value="${term.term_id}"
                            data-max-term="${term.duration}"
                            data-min-deposit="${term.min_deposit}"
                            data-term-name="${term.term_name}">
                      ${term.term_name} - Interest rate: ${term.interest_rate}% - Min deposit: $<fmt:formatNumber value="${term.min_deposit}" pattern="#,##0.000"/>
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="mb-3">
                <label for="savingAmount" class="form-label">Deposit Amount (VNĐ):</label>
                <input type="text" name="savingAmount" id="savingAmount" class="form-control" placeholder="Enter deposit amount" required>
                <div id="savingAmountError" class="text-danger mt-1"></div>
              </div>
              <div class="mb-3">
                <label for="savingDuration" class="form-label">Deposit Period (months):</label>
                <input type="number" name="savingDuration" id="savingDuration" class="form-control" placeholder="Auto-filled or free" readonly>
              </div>
              <button type="submit" name="action" value="calculateSaving" class="btn btn-danger">Calculate Savings Interest</button>
            </form>
            <c:if test="${not empty savingError}">
              <div class="alert alert-danger mt-3">${savingError}</div>
            </c:if>
            <c:if test="${not empty savingInterestEarned}">
  <div class="alert alert-info mt-3">
    <p>Interest Received: <strong><fmt:formatNumber value="${savingInterestEarned}" pattern="#,##0.000"/> VNĐ</strong></p>
    <p>Ending Amount: <strong><fmt:formatNumber value="${savingFinalAmount}" pattern="#,##0.000"/> VNĐ</strong></p>
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
      const savingTermSelect = document.getElementById("savingTermId");
      const savingDurationInput = document.getElementById("savingDuration");
      const savingAmountInput = document.getElementById("savingAmount");
      const savingAmountError = document.getElementById("savingAmountError");

      savingTermSelect.addEventListener("change", function(){
          const selectedOption = this.options[this.selectedIndex];
          const maxTerm = selectedOption.getAttribute("data-max-term");
          const minDeposit = selectedOption.getAttribute("data-min-deposit");
          const termName = selectedOption.getAttribute("data-term-name");
          
          if(termName && termName.toLowerCase() !== "flexible saving" && maxTerm) {
              savingDurationInput.value = maxTerm;
              savingDurationInput.readOnly = true;
          } else {
              savingDurationInput.value = "";
              savingDurationInput.readOnly = false;
          }
          if(minDeposit) {
              savingAmountInput.min = minDeposit;
              savingAmountInput.placeholder = "Enter deposit amount (min VNĐ " +
                  new Intl.NumberFormat('en-US', { minimumFractionDigits: 3, maximumFractionDigits: 3 }).format(minDeposit) + ")";
          } else {
              savingAmountInput.removeAttribute("min");
              savingAmountInput.placeholder = "Enter deposit amount";
          }
      });
      savingTermSelect.dispatchEvent(new Event("change"));

      // Format input deposit amount as user types
      function formatSavingAmount(input) {
          let value = input.value.replace(/[^\d.]/g, '');
          const parts = value.split('.');
          if (parts.length > 2) {
              value = parts[0] + '.' + parts.slice(1).join('');
          }
          let [integerPart, decimalPart] = value.split('.');
          integerPart = integerPart.replace(/,/g, '');
          integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
          if(decimalPart) {
              decimalPart = decimalPart.substring(0, 3);
              input.value = integerPart + '.' + decimalPart;
          } else {
              input.value = integerPart;
          }
      }
      savingAmountInput.addEventListener("input", function () {
          formatSavingAmount(this);
      });

      // Kiểm tra trước khi submit form: số tiền không được nhỏ hơn min deposit
      const form = document.querySelector("form");
      form.addEventListener("submit", function(e) {
          const savingAmount = parseFloat(savingAmountInput.value.replace(/,/g, ""));
          const minDeposit = parseFloat(savingAmountInput.min);
          if (!isNaN(minDeposit) && savingAmount < minDeposit) {
              savingAmountError.textContent = "Deposit amount must be at least VNĐ" +
                  new Intl.NumberFormat('en-US', { minimumFractionDigits: 3, maximumFractionDigits: 3 }).format(minDeposit);
              e.preventDefault();
          } else {
              savingAmountError.textContent = "";
          }
      });
    </script>
  </body>
</html>
