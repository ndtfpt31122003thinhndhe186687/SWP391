<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Mini Finance - Customer List</title>

        <!-- CSS FILES -->      
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="css/tooplate-mini-finance.css" rel="stylesheet">
    </head>
    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <!-- Header content (same as staff_management.jsp) -->
        </header>

        <div class="container-fluid">
            <div class="row">
                <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
                    <div class="position-sticky py-4 px-3 sidebar-sticky">
                        <ul class="nav flex-column h-100">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="customerList">
                                    <i class="bi-people me-2"></i>
                                    Customer List
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="listdebt">
                                    <i class="bi-cash-coin me-2"></i>
                                    Debt Management
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="customerrequest">
                                    <i class="bi-person-plus me-2"></i>
                                    Customer Loan Requests
                                </a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link" href="requestsaving">
                                    <i class="bi-piggy-bank me-2"></i>
                                    Customer Saving Requests
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Customer List</h1>
                    </div>

                    <div class="mt-3">
                        <form action="customerList" method="get" class="mb-3">
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <input type="text" name="search" class="form-control" placeholder="Search by name" value="${search}">
                                </div>
                                <div class="col-md-4">
                                    <select name="cardType" class="form-select">
                                        <option value="">All Card Types</option>
                                        <option value="debit" ${cardType == 'debit' ? 'selected' : ''}>Debit</option>
                                        <option value="credit" ${cardType == 'credit' ? 'selected' : ''}>Credit</option>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <button type="submit" class="btn btn-primary w-100">Search</button>
                                </div>
                            </div>
                        </form>

                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Card Type</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${customerList}" var="customer">
                                    <tr>
                                        <td>${customer.customer_id}</td>
                                        <td>${customer.full_name}</td>
                                        <td>${customer.card_type}</td>
                                        <td>
                                            <a href="customerDetails?id=${customer.customer_id}" class="btn btn-primary btn-sm">View Details</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <!-- Pagination Controls -->
                        <nav aria-label="Page navigation" class="mt-4">
                          <ul class="pagination justify-content-center">
                            <c:if test="${currentPage > 1}">
                              <li class="page-item">
                                <a class="page-link" href="customerList?page=${currentPage - 1}&search=${search}&cardType=${cardType}">Previous</a>
                              </li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                              <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="customerList?page=${i}&search=${search}&cardType=${cardType}">${i}</a>
                              </li>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                              <li class="page-item">
                                <a class="page-link" href="customerList?page=${currentPage + 1}&search=${search}&cardType=${cardType}">Next</a>
                              </li>
                            </c:if>
                          </ul>
                        </nav>

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

