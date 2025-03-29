<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Customer List</title>
    
    <!-- CSS FILES -->
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-icons.css" rel="stylesheet">
    <link href="css/tooplate-mini-finance.css" rel="stylesheet">
</head>
<body>
    <header class="navbar sticky-top flex-md-nowrap bg-danger">
        <div class="col-md-3 col-lg-3 px-3 fs-6">
            <a class="navbar-brand text-white" href="home">
                <i class="bi-box"></i>
                Finbank
            </a>
        </div>
    </header>

    <div class="container py-5">
        <h2 class="text-danger mb-4">Danh sách khách hàng</h2>
        
        <form action="CustomerList_AServlet" method="get" class="d-flex mb-4">
            <input class="form-control me-2" type="text" name="search" placeholder="Tìm kiếm khách hàng" required>
            <button class="btn btn-danger" type="submit">Tìm kiếm</button>
        </form>
        
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead class="table-danger">
                    <tr>
                        <th>ID</th>
                        <th>Tên khách hàng</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Customer> customers = (List<Customer>) request.getAttribute("customer");
                        if (customers != null) {
                            for (Customer customer : customers) {
                    %>
                        <tr>
                            <td><%= customer.getCustomer_id() %></td>
                            <td>
                                <a href="CustomerDetails_AServlet?id=<%= customer.getCustomer_id() %>" class="text-danger text-decoration-none">
                                    <%= customer.getFull_name() %>
                                </a>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="2" class="text-center">Không tìm thấy khách hàng.</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
    
    <footer class="site-footer bg-light py-3 text-center">
        <p class="mb-0">Copyright © Mini Finance 2048</p>
    </footer>

    <!-- JAVASCRIPT FILES -->