<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Mini Finance - Danh sách hợp đồng bảo hiểm</title>
        <!-- CSS FILES -->
        <style>
            /* Ngăn không cho nội dung trong các ô bảng xuống dòng */
            table.table-striped th, table.table-striped td {
                white-space: nowrap;
            }
        </style>

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
                            Đăng xuất
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
                                    <a class="nav-link active" href="list-insurance-contracts">
                                        <i class="bi-house-fill me-2"></i>
                                        Danh sách hợp đồng bảo hiểm
                                    </a>
                                </li>
                            </c:if>

                            <c:if test="${sessionScope.account.role_id==4}">
                                <li class="nav-item">
                                    <a class="nav-link" href="list-debt-customers">
                                        <i class="bi-wallet me-2"></i>
                                        Danh sách khách hàng nợ
                                    </a>
                                </li>
                            </c:if>

                            <c:if test="${sessionScope.account.role_id==4}">
                                <li class="nav-item">
                                    <a class="nav-link" href="list-transactions">
                                        <i class="bi-person me-2"></i>
                                        Danh sách giao dịch
                                    </a>
                                </li>
                            </c:if>

                            <c:if test="${sessionScope.account.role_id==4}">
                                <li class="nav-item">
                                    <a class="nav-link" href="list-insurance-transactions">
                                        <i class="bi-person me-2"></i>
                                        Danh sách giao dịch bảo hiểm
                                    </a>
                                </li>
                            </c:if>

                            <li class="nav-item">
                                <a class="nav-link" href="amounts">
                                    <i class="bi-person me-2"></i>
                                    Số lượng thống kê
                                </a>
                            </li>
                            <c:if test="${sessionScope.account.role_id==4}">
                                <li class="nav-item">
                                    <a class="nav-link" href="CustomerList_AServlet">
                                        <i class="bi-person me-2"></i>
                                        Kế Toán
                                    </a>
                                </li>                   
                            </c:if>
                            <c:if test="${sessionScope.account.role_id==4}">
                                <li class="nav-item">
                                    <a class="nav-link" href="calculateLoan">
                                        <i class="bi-person me-2"></i>
                                        Bảng tính lãi suất cho vay
                                    </a>
                                </li>                   
                            </c:if>
                            <c:if test="${sessionScope.account.role_id==4}">
                                <li class="nav-item">
                                    <a class="nav-link" href="calculateSaving">
                                        <i class="bi-person me-2"></i>
                                        Bảng tính lãi suất tiết kiệm
                                    </a>
                                </li>                   
                            </c:if>
                            <c:if test="${sessionScope.account.role_id==4}">
                                <li class="nav-item">
                                    <a class="nav-link" href="insuranceCalculator">
                                        <i class="bi-person me-2"></i>
                                        Bảng tính bảo hiểm
                                    </a>
                                </li>                   
                            </c:if> 
                        </ul>
                    </div>
                </nav>

                <!-- MAIN CONTENT -->
                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Danh sách hợp đồng bảo hiểm</h1>
                    </div>

                    <!-- Search Form -->
                    <div class="mb-4">
                        <form method="get" action="list-insurance-contracts" class="form-inline">
                            <div class="input-group">
                                <input type="text" name="search" class="form-control" placeholder="Search by Customer, Service or Policy" value="${search}" />
                                <div class="input-group-append">
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- Table Display -->
                    <div class="custom-block bg-white p-4">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        
                                        <th>Tên khách hàng</th>
                                        <th>Tên dịch vụ</th>
                                        <th>Tên điều khoản</th>
                                        <th>Ngày bắt đầu</th>
                                        <th>Ngày kết thúc</th>
                                        <th>Tần suất thanh toán</th>
                                        <th>Trạng thái</th>
                                        <th>Thời gian</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="contract" items="${contracts}">
                                        <tr>
                                            
                                            <td>${contract.customer_name}</td>
                                            <td>${contract.service_name}</td>
                                            <td>${contract.policy_name}</td>
                                            <td><fmt:formatDate value="${contract.start_date}" pattern="dd-MM-yyyy"/></td>
                                            <td><fmt:formatDate value="${contract.end_date}" pattern="dd-MM-yyyy"/></td>
                                            <td>${contract.payment_frequency}</td>
                                            <td>${contract.status}</td>
                                            <td><fmt:formatDate value="${contract.created_at}" pattern="dd-MM-yyyy"/></td>
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
                                    <a class="page-link" href="list-insurance-contracts?page=${currentPage - 1}&search=${search}">Previous</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="list-insurance-contracts?page=${i}&search=${search}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="list-insurance-contracts?page=${currentPage + 1}&search=${search}">Next</a>
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
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>
