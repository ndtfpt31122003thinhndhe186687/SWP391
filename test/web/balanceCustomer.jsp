<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="description" content="">
    <meta name="author" content="Tooplate">

    <title>Mini Finance Dashboard Template</title>

    <!-- CSS FILES -->      
    <link rel="preconnect" href="https://fonts.googleapis.com">

    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">

    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="css/bootstrap-icons.css" rel="stylesheet">

    <link href="css/apexcharts.css" rel="stylesheet">

    <link href="css/tooplate-mini-finance.css" rel="stylesheet">

</head>

<body> 
    <header class="navbar sticky-top flex-md-nowrap bg-danger">
        <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
            <a class="navbar-brand text-white" href="home">
                <i class="bi-box"></i>
                Finbank                </a>
        </div>

        <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="" method="get" role="form">
            <input class="form-control bg-white text-dark" name="search" type="text" placeholder="Search" aria-label="Search">
        </form>

        <div class="navbar-nav me-lg-2">
            <div class="nav-item text-nowrap d-flex align-items-center">
                <div class="dropdown ps-3">
                    <a class="nav-link dropdown-toggle text-center text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" id="navbarLightDropdownMenuLink">
                        <i class="bi-bell"></i>
                        <span class="position-absolute start-100 translate-middle p-1 bg-white border border-danger rounded-circle">
                            <span class="visually-hidden">New alerts</span>
                        </span>
                    </a>

                    <ul class="dropdown-menu dropdown-menu-lg-end notifications-block-wrap bg-white text-danger shadow" aria-labelledby="navbarLightDropdownMenuLink">
                        <small class="text-danger">Notifications</small>

                        <li class="notifications-block border-bottom border-danger pb-2 mb-2">
                            <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                <div class="notifications-icon-wrap bg-danger text-white">
                                    <i class="notifications-icon bi-check-circle-fill"></i>
                                </div>
                                <div>
                                    <span>Your account has been created successfully.</span>
                                    <p>12 days ago</p>
                                </div>
                            </a>
                        </li>

                        <li class="notifications-block border-bottom border-danger pb-2 mb-2">
                            <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                <div class="notifications-icon-wrap bg-danger text-white">
                                    <i class="notifications-icon bi-folder"></i>
                                </div>
                                <div>
                                    <span>Please check. We have sent a Daily report.</span>
                                    <p>10 days ago</p>
                                </div>
                            </a>
                        </li>

                        <li class="notifications-block">
                            <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                <div class="notifications-icon-wrap bg-danger text-white">
                                    <i class="notifications-icon bi-question-circle"></i>
                                </div>
                                <div>
                                    <span>Account verification failed.</span>
                                    <p>1 hour ago</p>
                                </div>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="dropdown px-3">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid" alt="">
            </a>
            <ul class="dropdown-menu bg-white shadow">
                <li>
                    <div class="dropdown-menu-profile-thumb d-flex">
                        <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid me-3" alt="">

                        <div class="d-flex flex-column">
                            <small>${sessionScope.account.full_name}</small>
                            <small>${sessionScope.account.email}</small>
                        </div>
                    </div>
                </li>
                <li>
                    <a class="dropdown-item" href="viewprofile">
                        <i class="bi-person me-2"></i>
                        Hồ sơ                       
                    </a>
                </li>
                <li>
                    <a class="dropdown-item" href="changeInfor">
                        <i class="bi-gear me-2"></i>
                        Cài đặt
                    </a>
                </li>               
                </li>
                <li class="border-top mt-3 pt-2 mx-4">
                    <a class="dropdown-item ms-0 me-0" href="logout">
                        <i class="bi-box-arrow-left me-2"></i>
                        Đăng xuất
                    </a>
                </li>
            </ul>
        </div>

    </div>
</div>
</header>

<div class="container-fluid">
    <div class="row">
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
                    <li class="nav-item">
                                <a class="nav-link" href="CustomerInsuranceList">
                                    <i class="bi-gear me-2"></i>
                                    Bảo hiểm
                                </a>
                            </li>

                    <li class="nav-item">
                        <a class="nav-link" href="accountTransaction">
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

         <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Tổng quan</h1>

                <small class="text-muted">Xin chào ${customer.full_name}, chào mừng quay trở lại!</small>
            </div>

            <div class="row my-4">
                <div class="col-lg-7 col-12">
                    <div class="custom-block custom-block-balance">
                        <small>Số dư tài khoản</small>

                        <h2 class="mt-2 mb-3">
                            <fmt:setLocale value="vi_VN" />
                            <fmt:formatNumber value="${customer.amount}" type="currency" currencySymbol="VND" groupingUsed="true" />
                        </h2>

                        <div class="custom-block-numbers d-flex align-items-center">
                            <span>****</span>
                            <span>****</span>
                            <span>****</span>
                            <p>2560</p>
                        </div>

                        <div class="d-flex">

                            <div class="ms-auto">
                                <small>Chủ thẻ</small>
                                <p>${customer.full_name}</p>
                            </div>
                        </div>
                    </div>

                    <div class="custom-block bg-white">
                        <h5 class="mb-4 text-danger">Lịch sử</h5>

                        <div id="pie-chart"></div>
                    </div>

                    <div class="custom-block bg-white">
                        <div id="chart"></div>
                    </div>

                    <div class="custom-block custom-block-exchange">
                        <h5 class="mb-4 text-danger">Exchange Rate</h5>

                        <div class="d-flex align-items-center border-bottom pb-3 mb-3">
                            <div class="d-flex align-items-center">
                                <img src="images/flag/united-states.png" class="exchange-image img-fluid" alt="">

                                <div>
                                    <p>USD</p>
                                    <h6>1 US Dollar</h6>
                                </div>
                            </div>

                            <div class="ms-auto me-4">
                                <small>Sell</small>
                                <h6>1.0931</h6>
                            </div>

                            <div>
                                <small>Buy</small>
                                <h6>1.0821</h6>
                            </div>
                        </div>

                        <div class="d-flex align-items-center border-bottom pb-3 mb-3">
                            <div class="d-flex align-items-center">
                                <img src="images/flag/singapore.png" class="exchange-image img-fluid" alt="">

                                <div>
                                    <p>SGD</p>
                                    <h6>1 Singapore Dollar</h6>
                                </div>
                            </div>

                            <div class="ms-auto me-4">
                                <small>Sell</small>
                                <h6>0.6901</h6>
                            </div>

                            <div>
                                <small>Buy</small>
                                <h6>0.6201</h6>
                            </div>
                        </div>

                        <div class="d-flex align-items-center border-bottom pb-3 mb-3">
                            <div class="d-flex align-items-center">
                                <img src="images/flag/united-kingdom.png" class="exchange-image img-fluid" alt="">

                                <div>
                                    <p>GPD</p>
                                    <h6>1 British Pound</h6>
                                </div>
                            </div>

                            <div class="ms-auto me-4">
                                <small>Sell</small>
                                <h6>1.1520</h6>
                            </div>

                            <div>
                                <small>Buy</small>
                                <h6>1.1412</h6>
                            </div>
                        </div>

                        <div class="d-flex align-items-center border-bottom pb-3 mb-3">
                            <div class="d-flex align-items-center">
                                <img src="images/flag/australia.png" class="exchange-image img-fluid" alt="">

                                <div>
                                    <p>AUD</p>
                                    <h6>1 Australian Dollar</h6>
                                </div>
                            </div>

                            <div class="ms-auto me-4">
                                <small>Sell</small>
                                <h6>0.6110</h6>
                            </div>

                            <div>
                                <small>Buy</small>
                                <h6>0.5110</h6>
                            </div>
                        </div>

                        <div class="d-flex align-items-center">
                            <div class="d-flex align-items-center">
                                <img src="images/flag/european-union.png" class="exchange-image img-fluid" alt="">

                                <div>
                                    <p>EUR</p>
                                    <h6>1 Euro</h6>
                                </div>
                            </div>

                            <div class="ms-auto me-4">
                                <small>Sell</small>
                                <h6>1.1020</h6>
                            </div>

                            <div>
                                <small>Buy</small>
                                <h6>1.1010</h6>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-5 col-12">
                    <div class="custom-block custom-block-profile-front custom-block-profile text-center bg-white">
                        <div class="custom-block-profile-image-wrap mb-4">
                            <img src="imageCustomer/${customer.profile_picture}" class="custom-block-profile-image img-fluid" alt="">

                            <a href="changeInfor" class="bi-pencil-square custom-block-edit-icon"></a>
                        </div>

                        <p class="d-flex flex-wrap mb-2">
                            <strong>Tên:</strong>

                            <span>${customer.full_name}</span>
                        </p>

                        <p class="d-flex flex-wrap mb-2">
                            <strong>Email:</strong>

                            <a href="#">
                                ${customer.email}
                            </a>
                        </p>

                        <p class="d-flex flex-wrap mb-0">
                            <strong>Phone:</strong>

                            <a href="#">
                                ${fn:substring(customer.phone_number, 0, 4)} ${fn:substring(customer.phone_number, 4, 7)} ${fn:substring(customer.phone_number, 7, 10)}
                            </a>

                        </p>
                    </div>

                    <div class="custom-block custom-block-bottom d-flex flex-wrap">
                        <div class="custom-block-bottom-item">
                            <a href="#" class="d-flex flex-column">
                                <i class="custom-block-icon bi-wallet"></i>

                                <small>Top up</small>
                            </a>
                        </div>

                        <div class="custom-block-bottom-item">
                            <a href="#" class="d-flex flex-column">
                                <i class="custom-block-icon bi-upc-scan"></i>

                                <small>Scan & Pay</small>
                            </a>
                        </div>

                        <div class="custom-block-bottom-item">
                            <a href="#" class="d-flex flex-column">
                                <i class="custom-block-icon bi-send"></i>

                                <small>Send</small>
                            </a>
                        </div>

                        <div class="custom-block-bottom-item">
                            <a href="#" class="d-flex flex-column">
                                <i class="custom-block-icon bi-arrow-down"></i>

                                <small>Request</small>
                            </a>
                        </div>
                    </div>

                    <div class="custom-block custom-block-transations">
                        <h5 class="mb-4 text-danger">Giao dịch gần đây</h5>
                        <c:forEach items="${requestScope.listT}" var="t" begin="0" end="2">
                            <div class="d-flex flex-wrap align-items-center">
                                <div class="d-flex align-items-center">
                                    <div>
                                        <p class="text-muted">
                                            <c:choose>
                                                <c:when test="${t.transaction_type eq 'withdrawal'}">
                                                    <i class="bi bi-arrow-right-circle text-danger"></i> Rút tiền
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="bi bi-arrow-down-circle text-success"></i> Chuyển tiền
                                                </c:otherwise>
                                            </c:choose></p>
                                    </div>
                                </div>

                                <div class="ms-auto">
                                    <small>
                                        <fmt:formatDate value="${t.transaction_date}" pattern="dd/MM/yyyy" />
                                    </small>
                                    <strong class="d-block">
                                        <c:choose>
                                            <c:when test="${t.transaction_type eq 'withdrawal'}">
                                                <span class="text-danger">
                                                    -<fmt:formatNumber value="${t.amount}" type="currency" currencySymbol="VND" groupingUsed="true" />
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-success">
                                                    +<fmt:formatNumber value="${t.amount}" type="currency" currencySymbol="VND" groupingUsed="true" />
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>

                                </div>
                            </div>
                        </c:forEach>
                        <div class="border-top pt-4 mt-4 text-center">
                            <a class="btn custom-btn" href="wallet">
                                Xem lịch sử giao dịch
                                <i class="bi-arrow-up-right-circle-fill ms-2"></i>
                            </a>
                        </div>
                    </div>



                </div>
            </div>

            <footer class="site-footer">
                <div class="container">
                    <div class="row">

                        <div class="col-lg-12 col-12">
                            <p class="copyright-text">Copyright © Mini Finance 2048 
                                - Design: <a rel="sponsored" href="https://www.tooplate.com" target="_blank">Tooplate</a></p>
                        </div>

                    </div>
                </div>
            </footer>
        </main>

    </div>
</div>

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/apexcharts.min.js"></script>
<script src="js/custom.js"></script>

<script type="text/javascript">
    var options = {
        series: [13, 43, 22],
        chart: {
            width: 380,
            type: 'pie',
        },
        labels: ['Balance', 'Expense', 'Credit Loan', ],
        responsive: [{
                breakpoint: 480,
                options: {
                    chart: {
                        width: 200
                    },
                    legend: {
                        position: 'bottom'
                    }
                }
            }]
    };

    var chart = new ApexCharts(document.querySelector("#pie-chart"), options);
    chart.render();
</script>

<script type="text/javascript">
    var options = {
        series: [{
                name: 'Income',
                data: [44, 55, 57, 56, 61, 58, 63, 60, 66]
            }, {
                name: 'Expense',
                data: [76, 85, 101, 98, 87, 105, 91, 114, 94]
            }, {
                name: 'Transfer',
                data: [35, 41, 36, 26, 45, 48, 52, 53, 41]
            }],
        chart: {
            type: 'bar',
            height: 350
        },
        plotOptions: {
            bar: {
                horizontal: false,
                columnWidth: '55%',
                endingShape: 'rounded'
            },
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            show: true,
            width: 2,
            colors: ['transparent']
        },
        xaxis: {
            categories: ['Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct'],
        },
        yaxis: {
            title: {
                text: '$ (thousands)'
            }
        },
        fill: {
            opacity: 1
        },
        tooltip: {
            y: {
                formatter: function (val) {
                    return "$ " + val + " thousands"
                }
            }
        }
    };

    var chart = new ApexCharts(document.querySelector("#chart"), options);
    chart.render();
</script>

</body>
</html>