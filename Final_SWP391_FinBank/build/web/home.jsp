<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="vi">
    <head>
        <!-- basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- mobile metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <!-- site metas -->
        <title>Fin Bank</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content=""> 
        <!-- bootstrap css -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <!-- style css -->
        <link rel="stylesheet" type="text/css" href="css/stylehome.css">
        <!-- Responsive-->
        <link rel="stylesheet" href="css/responsive.css">
        <!-- fevicon -->
        <link rel="icon" href="images/fevicon.png" type="image/gif" />
        <!-- Scrollbar Custom CSS -->
        <link rel="stylesheet" href="css/jquery.mCustomScrollbar.min.css">
        <!-- Tweaks for older IEs-->
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
        <!-- owl stylesheets --> 
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/owl.theme.default.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
    </head>
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
        }
        .fin {
            color: red;
        }

        .bank {
            color: black;
        }

        /* Chỉnh kích thước Navbar */
        .navbar {
            padding: 5px 10px; /* Giảm padding để navbar nhỏ hơn */
            font-size: 20px; /* Giảm kích thước chữ */
        }

        .navbar .navbar-nav .nav-item {
            margin: 0 5px; /* Giảm khoảng cách giữa các mục */
        }

        .navbar .navbar-nav .nav-link {
            padding: 8px 10px;
            font-size: 15px; /* Giảm kích thước chữ */
            text-transform: none; /* Không in hoa để nhìn nhỏ gọn hơn */
        }

        .search{
            margin-right: 400px;
            margin-top: 16px;
        }

        /* Nút tìm kiếm nhỏ hơn */
        .search input {
            width: 220px;
            padding: 5px ;
            font-size: 13px;
            margin-top: 25px;
        }

        .search button {
            padding: 5px 10px;
            font-size: 13px;
        }

        /* Chuông thông báo */
        .notification-bell {
            position: relative;
            cursor: pointer;
            font-size: 24px;
            margin-right: 20px;
        }

        /* Badge số lượng thông báo */
        .notification-bell .badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background: red;
            color: white;
            font-size: 12px;
            padding: 3px 6px;
            border-radius: 50%;
        }

        /* Hộp thông báo */
        #alert-container {
            position: absolute;
            top: 40px;
            right: 20px;
            width: 250px;
            background: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            padding: 10px;
            display: none;
        }

        .alert {
            padding: 8px;
            margin: 5px 0;
            border-radius: 5px;
            font-size: 14px;
        }

        /* Màu sắc từng loại thông báo */
        .alert-info {
            background: #d9edf7;
            color: #31708f;
        }
        .alert-warning {
            background: #fcf8e3;
            color: #8a6d3b;
        }
        .alert-success {
            background: #dff0d8;
            color: #3c763d;
        }

        /* Hiển thị khi mở */
        .hidden {
            display: none;
        }
        .user-section {
            display: flex;
            align-items: center;
            gap: 15px;
            float:right;
            margin-top: 30px;
        }

        /* Dropdown Menu */
        .notification-menu {
            min-width: 280px;
            border-radius: 10px;
            box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1);
            padding: 8px 0;
            overflow: hidden;
        }

        .unread {
            font-weight: bold;
            background-color: #f8d7da; /* Màu nền nhạt để nổi bật */
            color: #721c24; /* Màu chữ đậm hơn */
            border-left: 5px solid #dc3545; /* Viền trái để dễ nhận biết */
        }

    </style>

    <body>
        <c:if test="${not empty sessionScope.successMessage}">
            <div id="success-message" style="background-color: #d4edda; color: #155724; padding: 10px;
                 border-radius: 5px; margin-bottom: 15px; text-align: center;">
                ${sessionScope.successMessage}
            </div>
            <script>
                setTimeout(function () {
                    document.getElementById("success-message").style.display = "none";
                }, 3000);
            </script>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        <!--header section start -->
        <div class="header_section">
            <div class="header_left">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <div class="logo" "><a href="index.html"><img src="images/logobank.png"></a></div>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="home">Trang chủ</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="about.jsp">Giới thiệu</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="Service">Dịch vụ</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="Insurance">Bảo Hiểm</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="news">Tin tức</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="membership">Hội viên</a>
                            </li>
                        </ul>
                    </div>
                </nav>
                <div class="banner_main">
                    <h1 class="banner_taital">
                        <span class="fin">FIN</span> <br>
                        <span class="bank">BANK</span>
                    </h1>

                    <p class="banner_text">
                        Chào mừng đến với FIN Bank, đối tác đáng tin cậy của bạn về mặt cá nhân.
                        Chúng tôi cung cấp nhiều dịch vụ, bao gồm tiết kiệm,
                        cho vay và đầu tư, được thiết kế để giúp bạn đạt được
                        các mục tiêu tài chính của mình một cách dễ dàng và an toàn.
                    </p>
                    <div class="btn_main">
                        <div class="more_bt"><a href="about.jsp">Đọc thêm </a></div>
                        <div class="contact_bt"><a href="#">Liên hệ</a></div>
                    </div>
                </div>
            </div>


            <!--search section-->
            <div class="search">
                <form action="">
                    <input type="text" placeholder="What do you need to search??"  name="searchvalue">
                    <button type="submit" class="site-btn">Tìm kiếm</button>
                </form>
            </div>


            <!-- Gộp đăng nhập và thông báo vào một khối -->
            <div class="user-section">
                <div class="login">
                    <c:if test="${sessionScope.account != null}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Xin chào 
                                <c:choose>
                                    <c:when test="${sessionScope.account.role_id == 5}">
                                        ${sessionScope.account.insurance_name}
                                    </c:when>
                                    <c:otherwise>
                                        ${sessionScope.account.full_name}
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="userDropdown">
                                <c:choose>
                                    <c:when test="${sessionScope.account.role_id == 6}">
                                        <li><a class="dropdown-item" href="balanceCustomer">Xem hồ sơ</a></li>
                                        </c:when>
                                        <c:otherwise>
                                        <li><a class="dropdown-item" href="viewInforStaff">Xem hồ sơ</a></li>
                                        </c:otherwise>
                                    </c:choose>

                                <c:if test="${sessionScope.account.role_id == 3}">
                                    <li><a class="dropdown-item" href="marketer/newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=0&status=all&sort=all&page=1&pageSize=4">Quản lí tin tức</a></li>
                                    </c:if>
                                    <c:if test="${sessionScope.account.role_id == 1}">
                                    <li><a class="dropdown-item" href="staff_management?status=all&sort=full_name&type=bankers&page=1&pageSize=2">Quản lý</a></li>
                                    </c:if>
                                    <c:if test="${sessionScope.account.role_id == 2}">
                                    <li><a class="dropdown-item" href="customerList">Quản lý</a></li>
                                    </c:if>
                                    <c:if test="${sessionScope.account.role_id == 4}">
                                    <li><a class="dropdown-item" href="list-insurance-contracts">Quản lí (Accountant)</a></li>                                    </c:if>

                                <c:if test="${sessionScope.account.role_id == 5}">
                                    <li><a class="dropdown-item" href="sortInsurancePolicy?sortInsurancePolicy=none&status=all&quantity=5&offset=1">Quản lý bảo hiểm</a></li>
                                    </c:if>

                                <li><a class="dropdown-item" href="logout">Đăng xuất</a></li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.account == null}">
                        <li class="nav-item">
                            <a class="nav-link" href="login">Đăng nhập</a>
                        </li>
                    </c:if>
                </div>


                <div class="dropdown notification-dropdown" style="margin-left: 15px">
                    <button class="btn btn-light dropdown-toggle" type="button" id="notificationDropdown" data-bs-toggle="dropdown" aria-expanded="false" onclick="checkLogin()">
                        🔔 Thông báo
                        <span class="badge bg-danger notification-count">${requestScope.countNotify}</span>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end notification-menu" aria-labelledby="notificationDropdown">
                        <li class="dropdown-header">📌 Thông báo gần đây</li>
                            <c:choose>
                                <c:when test="${not empty requestScope.listNotify}">
                                    <c:forEach items="${requestScope.listNotify}" var="n" begin="0" end="2">
                                    <li>
                                        <a class="dropdown-item ${n.is_read=='unread' ? 'unread' : ''}" href="markAsRead?id=${n.notification_id}">${n.message}</a>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <li class="dropdown-item text-center text-muted">Không có thông báo nào</li>
                                </c:otherwise>
                            </c:choose>
                        <li class="dropdown-footer text-center"><a href="notificationsList">Xem tất cả</a></li>
                    </ul>
                </div>
            </div>

            <div class="header_right">
                <img src="images/banner-img.png" class="banner_img">
            </div>
        </div>
        <!--header section end -->


        <!--about section start -->
        <div class="services_section layout_padding">
            <div class="container">
                <div class="row">
                    <div class="col-md-8">
                        <h1 class="services_taital">CHÀO MỪNG ĐẾN VỚI NGÂN HÀNG CỦA CHÚNG TÔI!</h1>
                        <p class="services_text">
                            💰 Trải nghiệm tài chính hiện đại và an toàn! <br><br>
                            Chắc hẳn ai cũng từng gặp phải tình trạng bị phân tâm bởi bố cục của một trang web khi đọc nội dung trên đó. 
                            Chính vì vậy, chúng tôi luôn chú trọng đến việc thiết kế giao diện thân thiện, giúp bạn dễ dàng tiếp cận 
                            các thông tin quan trọng. <br><br>
                            Với hệ thống ngân hàng số tiên tiến, bạn có thể thực hiện giao dịch một cách nhanh chóng, bảo mật và thuận tiện. 
                            Chúng tôi cam kết mang lại cho bạn những dịch vụ tài chính tối ưu, đáp ứng mọi nhu cầu của bạn.
                        </p>
                        <div class="moremore_bt"><a href="#">Đọc thêm</a></div>
                    </div>
                    <div class="col-md-4">
                        <div><img src="images/img-1.png" class="image_1"></div>
                    </div>
                </div>
            </div>
        </div>

        <!--about section end -->

        <script type="text/javascript">
            function checkLogin() {
                var acc =${sessionScope.account!=null};
                if (!acc) {
                    alert("Bạn cần đăng nhập để xem thông báo");
                    window.location.href = "login";
                }
            }
        </script>


        <!--services section start -->
        <div class="what_we_do_section layout_padding">
            <div class="container">
                <h1 class="what_taital">CHÚNG TÔI LÀM GÌ?</h1>
                <p class="what_text">Chúng tôi cung cấp các dịch vụ ngân hàng hiện đại giúp bạn quản lý tài chính một cách hiệu quả và an toàn.</p>
                <div class="what_we_do_section_2">
                    <div class="row">
                        <c:forEach items="${requestScope.listServices}" var="s">
                            <div class="col-lg-3 col-sm-6">
                                <div class="box_main">
                                    <div class="icon_1">
                                        <c:choose>
                                            <c:when test="${s.service_type == 'saving'}">
                                                <img src="images/icon-1.png">
                                            </c:when>
                                            <c:when test="${s.service_type == 'loan'}">
                                                <img src="images/icon-2.png">
                                            </c:when>
                                            <c:when test="${s.service_type == 'deposit'}">
                                                <img src="images/icon-3.png">
                                            </c:when>
                                            <c:when test="${s.service_type == 'withdrawal'}">
                                                <img src="images/icon-4.png">
                                            </c:when>
                                        </c:choose>
                                    </div>
                                    <h3 class="accounting_text">${s.service_name}</h3>
                                    <p class="lorem_text">${s.description}</p>
                                    <div class="moremore_bt_1"><a href="#">Xem thêm</a></div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <!--services section end -->

        <div class="project_section_2 layout_padding">
            <div class="container">
                <div class="row">
                    <div class="col-lg-3 col-sm-6">
                        <div class="icon_1"><img src="images/icon-3.png"></div>
                        <h3 class="accounting_text_1">1000+</h3>
                        <p class="yers_text">Years of Business</p>
                    </div>
                    <div class="col-lg-3 col-sm-6">
                        <div class="icon_1"><img src="images/icon-4.png"></div>
                        <h3 class="accounting_text_1">20000+</h3>
                        <p class="yers_text">Projects Delivered</p>
                    </div>
                    <div class="col-lg-3 col-sm-6">
                        <div class="icon_1"><img src="images/icon-2.png"></div>
                        <h3 class="accounting_text_1">10000+</h3>
                        <p class="yers_text">Satisfied Customers</p>
                    </div>
                    <div class="col-lg-3 col-sm-6">
                        <div class="icon_1"><img src="images/icon-1.png"></div>
                        <h3 class="accounting_text_1">1500+</h3>
                        <p class="yers_text">Services</p>
                    </div>
                </div>
            </div>
        </div>
        <!--project section end -->

        <!--client section start -->
        <div class="client_section layout_padding">
            <div class="container">
                <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <h1 class="what_taital">what is syas our clients</h1>
                            <div class="client_section_2 layout_padding">
                                <ul>
                                    <li><img src="images/round-1.png" class="round_1"></li>
                                    <li><img src="images/img-11.png" class="image_11"></li>
                                    <li><img src="images/round-2.png" class="round_2"></li>
                                </ul>
                                <p class="dummy_text">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem</p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <h1 class="what_taital">what is syas our clients</h1>
                            <div class="client_section_2 layout_padding">
                                <ul>
                                    <li><img src="images/round-1.png" class="round_1"></li>
                                    <li><img src="images/img-11.png" class="image_11"></li>
                                    <li><img src="images/round-2.png" class="round_2"></li>
                                </ul>
                                <p class="dummy_text">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem</p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <h1 class="what_taital">what is syas our clients</h1>
                            <div class="client_section_2 layout_padding">
                                <ul>
                                    <li><img src="images/round-1.png" class="round_1"></li>
                                    <li><img src="images/img-11.png" class="image_11"></li>
                                    <li><img src="images/round-2.png" class="round_2"></li>
                                </ul>
                                <p class="dummy_text">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--client section end -->
        <!--footer section start -->
        <div class="footer_section layout_padding">
            <div class="container">
                <div class="row">
                    <div class="col-lg-4 col-sm-6">
                        <h4 class="about_text">About Banking</h4>
                        <div class="location_text"><img src="images/map-icon.png"><span class="padding_left_15">Locations</span></div>
                        <div class="location_text"><img src="images/call-icon.png"><span class="padding_left_15">+01 9876543210</span></div>
                        <div class="location_text"><img src="images/mail-icon.png"><span class="padding_left_15">demo@gmail.com</span></div>
                    </div>
                    <div class="col-lg-4 col-sm-6">
                        <h4 class="about_text">About Banking</h4>
                        <p class="dolor_text">VIE Bank accompanies every customer segment to help Vietnamese people have a better financial life.</p>
                    </div>
                    <div class="col-lg-4 col-sm-6">
                        <h4 class="about_text">Connect here</h4>
                        <div class="footer_social_icon">
                            <ul>
                                <li><a href="#"><img src="images/fb-icon1.png"></a></li>
                                <li><a href="#"><img src="images/twitter-icon1.png"></a></li>
                                <li><a href="#"><img src="images/linkedin-icon1.png"></a></li>
                                <li><a href="#"><img src="images/youtub-icon1.png"></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- copyright section start -->
                <div class="copyright_section">
                    <div class="copyright_text">Copyright © Vietnam Technological
                    </div>
                </div>
                <!-- copyright section end -->
            </div>
        </div>
        <!--footer section end -->
        <!-- Javascript files-->
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/jquery-3.0.0.min.js"></script>
        <script src="js/plugin.js"></script>
        <!-- sidebar -->
        <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="js/custom.js"></script>
        <!-- javascript --> 
        <script src="js/owl.carousel.js"></script>
        <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script> 
    </body>
</html>