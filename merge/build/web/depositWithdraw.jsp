<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="vi_VN"/>
<%@ page session="true" %>
<%@ page import="model.Customer" %>

<c:if test="${empty account}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mini Finance - Nạp tiền / Rút tiền</title>

    <!-- CSS FILES -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-icons.css" rel="stylesheet">
    <link href="css/tooplate-mini-finance.css" rel="stylesheet">
    
    <style>
        .container { margin-top: 30px; }
        .balance-info { margin-bottom: 20px; }
        .alert { margin-top: 20px; }
        .custom-card { border-radius: 10px; box-shadow: 0px 4px 6px rgba(0,0,0,0.1); }
    </style>
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
                                <a class="nav-link " aria-current="page" href="balanceCustomer">
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

                            <li class="nav-item">
                                <a class="nav-link active" href="accountTransaction">
                                    <i class="bi-person me-2"></i>
                                    Nạp tiền/ Rút tiền
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
                    <h1 class="h2 mb-0 text-danger">Nạp tiền / Rút tiền</h1>
                </div>

                <!-- HIỂN THỊ THÔNG TIN TÀI KHOẢN -->
                <div class="custom-block bg-white p-4 mb-4">
                    <h5 class="mb-3 text-danger">Thông tin tài khoản</h5>
                    <p><strong>Loại tài khoản:</strong> ${account.card_type}</p>

                    <div class="balance-info">
                        <p><strong>Số dư (Debit):</strong> 
                            <fmt:formatNumber value="${account.amount}" type="currency" currencySymbol="VND" pattern="#,##0.000"/> 
                        </p>
                        <p><strong>Hạn mức tín dụng (Credit):</strong> 
                            <fmt:formatNumber value="${account.credit_limit}" type="currency" currencySymbol="VND" pattern="#,##0.000"/> 
                        </p>
                    </div>
                </div>

                <!-- FORM NẠP TIỀN -->
                <c:if test="${account.card_type eq 'debit'}">
                    <div class="custom-block bg-white p-4 mb-4">
                        <h5 class="mb-3 text-success">Nạp tiền</h5>
                        <form action="accountTransaction" method="post">
                            <input type="hidden" name="actionType" value="deposit">
                            <div class="mb-3">
                                <label for="depositAmount" class="form-label">Số tiền nạp (VNĐ):</label>
                                <input type="text" name="amount" id="depositAmount" class="form-control" placeholder="Nhập số tiền cần nạp" required>
                            </div>
                            <button type="submit" class="btn btn-success">Nạp tiền</button>
                        </form>
                    </div>
                </c:if>

                <!-- FORM RÚT TIỀN -->
                <div class="custom-block bg-white p-4 mb-4">
                    <h5 class="mb-3 text-danger">Rút tiền</h5>
                    <form action="accountTransaction" method="post">
                        <input type="hidden" name="actionType" value="withdraw">
                        <div class="mb-3">
                            <label for="withdrawAmount" class="form-label">Số tiền rút (VNĐ):</label>
                            <input type="text" name="amount" id="withdrawAmount" class="form-control" placeholder="Nhập số tiền cần rút" required>
                        </div>
                        <button type="submit" class="btn btn-danger">Rút tiền</button>
                    </form>
                </div>

                <!-- THÔNG BÁO -->
                <c:if test="${not empty message}">
                    <div class="alert alert-info">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
            </main>
        </div>
    </div>
</body>
</html>
