<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mini Finance - List of Transactions</title>
    <!-- CSS FILES -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-icons.css" rel="stylesheet">
    <link href="css/tooplate-mini-finance.css" rel="stylesheet">
    <style>
      .amount-positive { color: green; }
      .amount-negative { color: red; }
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
              <li class="nav-item">
                <a class="nav-link active" href="list-transactions">
                  <i class="bi-currency-dollar me-2"></i>
                  List of Transactions
                </a>
              </li>
            </ul>
          </div>
        </nav>
        
        <!-- MAIN CONTENT -->
        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
          <div class="title-group mb-3">
            <h1 class="h2 mb-0 text-danger">List of Transactions</h1>
          </div>
          
          <!-- Search Form -->
          <div class="mb-4">
            <form method="get" action="list-transactions" class="form-inline">
              <div class="input-group">
                <input type="text" name="search" class="form-control" placeholder="Search by Customer or Transaction Type" value="${search}" />
                <div class="input-group-append">
                  <button type="submit" class="btn btn-primary">Search</button>
                </div>
              </div>
            </form>
          </div>
          
          <!-- Table Display -->
          <div class="custom-block bg-white p-4">
            <div class="table-responsive">
              <table class="table table-striped table-hover">
                <thead class="thead-dark">
                  <tr>
                    <th>ID</th>
                    <th>Customer Name</th>
                    <th>Service Name</th>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Type</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="transaction" items="${transactions}">
                    <tr>
                      <td>${transaction.transaction_id}</td>
                      <td>${transaction.customer_name}</td>
                      <td>${transaction.service_name}</td>
                      <td class="${transaction.amount >= 0 ? 'amount-positive' : 'amount-negative'}">
                        <fmt:formatNumber value="${transaction.amount}" type="currency" currencySymbol="$"/>
                      </td>
                      <td>
                        <fmt:formatDate value="${transaction.transaction_date}" pattern="dd-MM-yyyy HH:mm:ss"/>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${transaction.transaction_type eq 'premium_payment'}">
                            Premium Payment
                          </c:when>
                          <c:when test="${transaction.transaction_type eq 'claim_payment'}">
                            Claim Payment
                          </c:when>
                          <c:otherwise>
                            ${transaction.transaction_type}
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          
          <!-- Pagination Controls -->
          <nav aria-label="Page navigation" class="mt-4">
            <ul class="pagination justify-content-center">
              <c:if test="${currentPage > 1}">
                <li class="page-item">
                  <a class="page-link" href="list-transactions?page=${currentPage - 1}&search=${search}">Previous</a>
                </li>
              </c:if>
              <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                  <a class="page-link" href="list-transactions?page=${i}&search=${search}">${i}</a>
                </li>
              </c:forEach>
              <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                  <a class="page-link" href="list-transactions?page=${currentPage + 1}&search=${search}">Next</a>
                </li>
              </c:if>
            </ul>
          </nav>
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
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  </body>
</html>
