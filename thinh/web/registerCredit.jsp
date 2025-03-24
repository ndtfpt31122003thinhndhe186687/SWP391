<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Đăng Ký Thẻ Tín Dụng</title>

        <!-- CSS FILES -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-icons.css" rel="stylesheet">
        <link href="css/tooplate-mini-finance.css" rel="stylesheet">

        <style>
            .container {
                margin-top: 30px;
            }
            .balance-info {
                margin-bottom: 20px;
            }
            .alert {
                margin-top: 20px;
            }
            .custom-card {
                border-radius: 10px;
                box-shadow: 0px 4px 6px rgba(0,0,0,0.1);
            }
        </style>

        <script>
            // Định dạng số tiền theo "000,000" (VNĐ)
            function formatCurrency(input) {
                let value = input.value.replace(/\D/g, ""); // Chỉ giữ lại số
                value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ","); // Thêm dấu phẩy sau mỗi 3 chữ số
                input.value = value;
            }

            // Lấy tháng và năm hiện tại, thiết lập tháng tiếp theo làm tháng thanh toán
            function setDefaultDate() {
                let today = new Date();
                let nextMonth = today.getMonth() + 2; // Tháng tiếp theo (JavaScript getMonth() bắt đầu từ 0)
                let year = today.getFullYear();
                if (nextMonth > 12) { // Nếu hiện tại là tháng 12, thì tháng sau là tháng 1 năm sau
                    nextMonth = 1;
                    year++;
                }

                document.getElementById("month").value = nextMonth;
                document.getElementById("year").value = year;
            }

            window.onload = setDefaultDate; // Gọi hàm khi trang tải xong
        </script>
    </head>

    <body>
        <!-- HEADER -->
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="home">
                    <i class="bi-box"></i> Mini Finance
                </a>
            </div>
        </header>

        <div class="container-fluid">
            <div class="row">
                <!-- SIDEBAR -->
                <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
                    <div class="position-sticky py-4 px-3 sidebar-sticky">
                        <ul class="nav flex-column h-100">
                             <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="balanceCustomer">
                            <i class="bi-house-fill me-2"></i>
                            Tổng quan
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="wallet">
                            <i class="bi-wallet me-2"></i>
                            Ví của tôi
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="viewprofile">
                            <i class="bi-person me-2"></i>
                            Hồ sơ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="savingList">
                            <i class="bi-person me-2"></i>
                            Sổ tiết kiệm 
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="loanList">
                            <i class="bi-person me-2"></i>
                            Vay 
                        </a>
                    </li>

                    <c:if test="${sessionScope.account.role_id==6}">
                        <c:if test="${sessionScope.account.card_type == 'credit' 
                                      && sessionScope.account.credit_limit == 0 }">
                              <li class="nav-item">                                             

                                  <a class="nav-link" href="registerCreditCard">
                                      <i class="bi-person me-2"></i>
                                      Đăng Ký Thẻ Tín Dụng
                                  </a>                          
                              </li>
                        </c:if>  
                    </c:if>  
                    <li class="nav-item">
                        <a class="nav-link" href="notificationsList">
                            <i class="bi-person me-2"></i>
                            Thông báo 
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="CustomerInsuranceList">
                            <i class="bi-gear me-2"></i>
                            Bảo hiểm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " href="changeInfor">
                            <i class="bi-gear me-2"></i>
                            Cài đặt

                        </a>
                    </li>
                    <li class="nav-item border-top mt-auto pt-2">
                        <a class="nav-link" href="logout">
                            <i class="bi-box-arrow-left me-2"></i>
                            Đăng xuất
                        </a>
                    </li>
                        </ul>
                    </div>
                </nav>

                <!-- MAIN CONTENT -->
                <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
                    <div class="title-group mb-3">
                        <h1 class="h2 mb-0 text-danger">Đăng Ký Thẻ Tín Dụng</h1>
                    </div>

                    <!-- FORM ĐĂNG KÝ THẺ TÍN DỤNG -->
                    <div class="custom-block bg-white p-4 mb-4">
                        <h5 class="mb-3 text-danger">Thông tin đăng ký</h5> <!-- Đổi từ text-primary (xanh) sang text-danger (đỏ) -->
                        <form action="registerCreditCard" method="post">
                            <!-- Nhập thu nhập hàng tháng (VNĐ) -->
                            <div class="mb-3">
                                <label for="salary" class="form-label">Thu nhập hàng tháng (VNĐ):</label>
                                <input type="text" name="salary" id="salary" class="form-control" placeholder="Nhập số tiền" required oninput="formatCurrency(this)">
                            </div>

                            <!-- Chọn ngày thanh toán hàng tháng -->
                            <div class="mb-3">
                                <label for="credit_due_date" class="form-label">Ngày thanh toán:</label>
                                <select name="credit_due_date" id="credit_due_date" class="form-control">
                                    <option value="1">Ngày 1</option>
                                    <option value="5">Ngày 5</option>
                                    <option value="10">Ngày 10</option>
                                    <option value="15">Ngày 15</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="month" class="form-label">Tháng thanh toán:</label>
                                <input type="text" id="month" class="form-control" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="year" class="form-label">Năm thanh toán:</label>
                                <input type="text" id="year" class="form-control" readonly>
                            </div>

                            <button type="submit" class="btn btn-danger">Đăng Ký</button>
                        </form>
                    </div>


                    <!-- THÔNG BÁO -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                </main>
            </div>
        </div>
    </body>
</html>
