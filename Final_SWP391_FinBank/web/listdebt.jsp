
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Mini Finance - Debt Management List</title>
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
                <a class="navbar-brand text-white" href="home">
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
                                <a class="nav-link " aria-current="page" href="customerList">
                                    <i class="bi-people me-2"></i>
                                    Danh sách người dùng
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link active" href="listdebt">
                                    <i class="bi-cash-coin me-2"></i>
                                    Quản lý nợ
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="customerrequest">
                                    <i class="bi-person-plus me-2"></i>
                                    Đơn vay
                                </a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link" href="requestsaving">
                                    <i class="bi-piggy-bank me-2"></i>
                                    Đơn tiết kiệm
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <!-- MAIN CONTENT -->
                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Danh sách quản lí nợ</h1>
                    </div>

                    <!-- Search Form -->
                    <div class="mb-4">
                        <form method="get" action="listdebt" class="form-inline">
                            <div class="input-group">
                                <input type="text" name="search" class="form-control" placeholder="Search by Debt ID or Customer Name" value="${search}" />
                                <div class="input-group-append">
                                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- Table Display -->
                    <div class="custom-block bg-white p-4">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tên người dùng</th>
                                    <th>Hoạt động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="debt" items="${listdebt}">
                                    <tr>
                                        <td>${debt.debt_id}</td>
                                        <td>${debt.customerName}</td>
                                        <td>
                                            <a href="updatedebtmanagement?id=${debt.debt_id}" class="btn btn-warning btn-sm">Update</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Pagination Controls -->
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="listdebt?page=${currentPage - 1}&search=${search}">Previous</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="listdebt?page=${i}&search=${search}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="listdebt?page=${currentPage + 1}&search=${search}">Next</a>
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
