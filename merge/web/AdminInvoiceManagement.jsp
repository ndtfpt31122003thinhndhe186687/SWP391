<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.ElectricityInvoice, model.WaterInvoice, model.InternetInvoice, controlller_Accountant.InvoiceDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quản Lý Hóa Đơn</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2 class="text-center mt-4">Quản Lý Hóa Đơn</h2>

        <!-- Nút gửi hóa đơn tự động từ ngày 1-5 -->
        <form action="AutoInvoiceServlet" method="post" class="mb-3">
            <input type="hidden" name="action" value="sendInvoices">
            <button type="submit" class="btn btn-primary">Gửi Hóa Đơn Đến Khách Hàng</button>
        </form>

        <!-- Tabs -->
        <ul class="nav nav-tabs">
            <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#electricity">Hóa đơn Điện</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#water">Hóa đơn Nước</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#internet">Hóa đơn Internet</a></li>
        </ul>

        <div class="tab-content mt-3">
            <!-- Hóa đơn Điện -->
            <div id="electricity" class="tab-pane fade show active">
                <h4>Hóa Đơn Điện</h4>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Khách Hàng</th>
                            <th>Số KWh</th>
                            <th>Số Tiền</th>
                            <th>Ngày Hạn</th>
                            <th>Trạng Thái</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            InvoiceDAO dao = new InvoiceDAO();
                            List<ElectricityInvoice> electricityInvoices = dao.getAllElectricityInvoices();

                            for (ElectricityInvoice invoice : electricityInvoices) {
                        %>
                        <tr>
                            <td><%= invoice.getInvoiceId() %></td>
                            <td><%= invoice.getCustomerId() %></td>
                            <td><%= invoice.getConsumptionKWh() %> kWh</td>
                            <td><%= invoice.getAmount() %> VNĐ</td>
                            <td><%= invoice.getDueDate() %></td>
                            <td><%= invoice.getStatus() %></td>
                            <td>
                                <form action="AdminInvoiceServlet" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="table" value="electricityinvoice">
                                    <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceId() %>">
                                    <button type="submit" class="btn btn-warning btn-sm">Sửa</button>
                                </form>
                                <form action="AdminInvoiceServlet" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="table" value="electricityinvoice">
                                    <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceId() %>">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <!-- Hóa đơn Nước -->
            <div id="water" class="tab-pane fade">
                <h4>Hóa Đơn Nước</h4>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Khách Hàng</th>
                            <th>Số M³</th>
                            <th>Số Tiền</th>
                            <th>Ngày Hạn</th>
                            <th>Trạng Thái</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<WaterInvoice> waterInvoices = dao.getAllWaterInvoices();
                            for (WaterInvoice invoice : waterInvoices) {
                        %>
                        <tr>
                            <td><%= invoice.getInvoiceId() %></td>
                            <td><%= invoice.getCustomerId() %></td>
                            <td><%= invoice.getConsumptionM3() %> m³</td>
                            <td><%= invoice.getAmount() %> VNĐ</td>
                            <td><%= invoice.getDueDate() %></td>
                            <td><%= invoice.getStatus() %></td>
                            <td>
                                <form action="AdminInvoiceServlet" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="table" value="waterinvoice">
                                    <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceId() %>">
                                    <button type="submit" class="btn btn-warning btn-sm">Sửa</button>
                                </form>
                                <form action="AdminInvoiceServlet" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="table" value="waterinvoice">
                                    <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceId() %>">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <!-- Hóa đơn Internet -->
            <div id="internet" class="tab-pane fade">
                <h4>Hóa Đơn Internet</h4>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Khách Hàng</th>
                            <th>Gói Cước</th>
                            <th>Số Tiền</th>
                            <th>Ngày Hạn</th>
                            <th>Trạng Thái</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<InternetInvoice> internetInvoices = dao.getAllInternetInvoices();
                            for (InternetInvoice invoice : internetInvoices) {
                        %>
                        <tr>
                            <td><%= invoice.getInvoiceId() %></td>
                            <td><%= invoice.getCustomerId() %></td>
                            <td><%= invoice.getPackage() %></td>
                            <td><%= invoice.getAmount() %> VNĐ</td>
                            <td><%= invoice.getDueDate() %></td>
                            <td><%= invoice.getStatus() %></td>
                            <td>
                                <form action="AdminInvoiceServlet" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="table" value="internetinvoice">
                                    <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceId() %>">
                                    <button type="submit" class="btn btn-warning btn-sm">Sửa</button>
                                </form>
                                <form action="AdminInvoiceServlet" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="table" value="internetinvoice">
                                    <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceId() %>">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
