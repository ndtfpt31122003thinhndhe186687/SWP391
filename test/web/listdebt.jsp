<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

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
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <!-- Header content (same as staff_management.jsp) -->
        </header>

        <div class="container-fluid">
            <div class="row">
                <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
                    <div class="position-sticky py-4 px-3 sidebar-sticky">
                        <ul class="nav flex-column h-100">
                            <li class="nav-item">
                                <a class="nav-link" href="customerList">
                                    <i class="bi-people me-2"></i>
                                    Customer List
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="listdebt">
                                    <i class="bi-cash-coin me-2"></i>
                                    Debt Management
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="customerrequest">
                                    <i class="bi-person-plus me-2"></i>
                                    Customer Requests
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Debt Management List</h1>
                    </div>

                    <div class="mt-3">
                        <form method="get" action="listdebt" class="mb-3">
                            <div class="input-group">
                                <input type="text" name="search" class="form-control" value="${search}" placeholder="Search by Debt ID or Customer Name">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>

                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Debt ID</th>
                                    <th>Customer Name</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listdebt}" var="debt">
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
                </main>
            </div>
        </div>

        <!-- JAVASCRIPT FILES -->
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>

