<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mini Finance - Insurance Premium Calculator</title>
    <!-- CSS FILES -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-icons.css" rel="stylesheet">
    <link href="css/tooplate-mini-finance.css" rel="stylesheet">
    <style>
      .calculator {
        max-width: 600px;
        margin: 20px auto;
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
      }
      .calculator h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #d32f2f;
      }
      .result {
        margin-top: 20px;
        text-align: center;
      }
    </style>
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
            <ul class="nav flex-column h-100">
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
                        <a class="nav-link" href="calculateSaving">
                            <i class="bi-person me-2"></i>
                            Savings Interest Calculator
                        </a>
                    </li>                   
                    </c:if>
                    <c:if test="${sessionScope.account.role_id==4}">
                    <li class="nav-item">
                        <a class="nav-link active" href="insuranceCalculator">
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
          <div class="calculator">
            <h2>Insurance Premium Calculator</h2>
            <form action="insuranceCalculator" method="post">
              <div class="mb-3">
                <label for="loanAmount" class="form-label">Loan Amount (VNĐ):</label>
                <input type="number" step="0.01" class="form-control" id="loanAmount" name="loanAmount" required>
              </div>
              <!-- Nếu cho phép nhập thời gian vay, mặc định là 12 tháng (1 năm) -->
              <div class="mb-3">
                <label for="loanTerm" class="form-label">Loan Term (in months):</label>
                <input type="number" class="form-control" id="loanTerm" name="loanTerm" value="12" required>
              </div>
              <div class="mb-3">
                <label for="policyId" class="form-label">Insurance Policy ID:</label>
                <input type="number" class="form-control" id="policyId" name="policyId" required>
              </div>
              <!-- Nếu cần nhập feeRate bổ sung, bạn có thể để trống nếu không sử dụng trong tính toán -->
              <div class="mb-3">
                <label for="feeRate" class="form-label">Insurance Fee Rate (%):</label>
                <input type="number" step="0.01" class="form-control" id="feeRate" name="feeRate">
              </div>
              <button type="submit" class="btn btn-danger w-100">Calculate Premium</button>
            </form>
            
            <c:if test="${not empty premium}">
              <div class="result">
                <h3>Calculated Premium: ${premium} VNĐ</h3>
              </div>
            </c:if>
            
            <c:if test="${not empty error}">
              <div class="alert alert-danger mt-3" role="alert">
                ${error}
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
  </body>
</html>
