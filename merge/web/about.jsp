<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- mobile metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <!-- site metas -->
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
        <title>Interest Rates</title>
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
        </style>

    </head>

    <body>
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
                                <a class="nav-link" href="contact.html">Liên hệ</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="news">Tin tức</a>
                            </li>
                        </ul>
                    </div>
                </nav>
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
                        <c:if test="${sessionScope.account.role_id !=5}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Xin chào ${sessionScope.account.full_name}
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="userDropdown">
                                    <li><a class="dropdown-item" href="balanceCustomer">Xem hồ sơ</a></li>
                                        <c:if test="${sessionScope.account.role_id==3}">
                                        <li><a class="dropdown-item" href="marketer/newsManage">Quản lí tin tức</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role_id==1}">
                                        <li><a class="dropdown-item" href="staff_management">Quản lý</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role_id==2}">
                                        <li><a class="dropdown-item" href="customerList">Quản lý</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role_id==4}">
                                        <li><a class="dropdown-item" href="staff_management">Quản lý</a></li>
                                        </c:if>
                                    <li><a class="dropdown-item" href="logout">Đăng xuất</a></li>
                                </ul>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.account.role_id == 5}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Xin chào ${sessionScope.account.insurance_name}
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="userDropdown">
                                    <li><a class="dropdown-item" href="managerPolicy">Quản lý bảo hiểm</a></li>
                                    <li><a class="dropdown-item" href="logout">Đăng xuất</a></li>
                                </ul>
                            </li>                          
                        </c:if>
                    </c:if>    
                    <c:if test="${sessionScope.account == null}">
                        <li class="nav-item">
                            <a class="nav-link" href="login">Đăng nhập</a>
                        </li>
                    </c:if>
                </div>

                <div class="dropdown notification-dropdown" style="margin-left: 15px">
                    <button class="btn btn-light dropdown-toggle" type="button" id="notificationDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                        🔔 Thông báo
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end notification-menu" aria-labelledby="notificationDropdown">
                        <li class="dropdown-header">📌 Thông báo gần đây</li>
                            <c:choose>
                                <c:when test="${not empty requestScope.listNotify}">
                                    <c:forEach items="${requestScope.listNotify}" var="n" begin="0" end="2">
                                    <li><a class="dropdown-item" href="#">${n.message}</a></li>
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


        </div>
        <!--header section end -->

        <!-- About Section Start -->
        <div class="services_section layout_padding">
            <div class="container">
                <div class="row">
                    <div class="col-md-8">
                        <h1 class="services_taital">CHÀO MỪNG BẠN ĐẾN VỚI FINBANK</h1>
                        <p class="services_text">
                            Tại Dịch Vụ Tài Chính, chúng tôi cam kết mang đến những giải pháp tài chính an toàn, hiện đại và thân thiện với khách hàng. Với sứ mệnh hỗ trợ cá nhân và doanh nghiệp tiếp cận các công cụ tài chính tối ưu, chúng tôi không ngừng đổi mới để giúp bạn xây dựng một tương lai vững chắc.
                            Ngân hàng chúng tôi cung cấp đa dạng các dịch vụ như gửi tiết kiệm, cho vay, đầu tư, bảo hiểm và thanh toán trực tuyến. Chúng tôi luôn đặt lợi ích của khách hàng lên hàng đầu, đảm bảo các giao dịch diễn ra minh bạch, nhanh chóng và an toàn.
                            Với đội ngũ chuyên gia tài chính giàu kinh nghiệm cùng hệ thống công nghệ hiện đại, chúng tôi cam kết mang lại những trải nghiệm tài chính tốt nhất, giúp bạn quản lý tài chính cá nhân và doanh nghiệp hiệu quả hơn.
                            Hãy cùng chúng tôi khám phá những giải pháp tài chính phù hợp với nhu cầu của bạn!
                        </p>
                    </div>
                    <div class="col-md-4">
                        <img src="images/img-1.png" class="image_1" alt="Financial Services">
                    </div>
                </div>

                <!-- Our Impact Section -->
                <div class="row text-center mt-4">
                    <div class="col-md-4">
                        <h3>📅 10+ Years</h3>
                        <p>Kinh nghiệm</p>  
                    </div>
                    <div class="col-md-4">
                        <h3>💰 $1B+</h3>
                        <p>Trong Giao dịch</p>
                    </div>
                    <div class="col-md-4">
                        <h3>👥 500K+</h3>
                        <p>Khách hàng hài lòng</p>
                    </div>
                </div>

                <!-- Interest Rates Section -->
                <div class="row mt-5">
                    <div class="col-md-12">
                        <h2 class="services_taital">📈 Các gói dịch vụ</h2>
                        <p class="services_text">Cập nhật thông tin mới nhất về lãi suất cho vay và tiết kiệm để đưa ra quyết định tài chính sáng suốt.</p>
                        <table class="table table-striped">
                            <thead class="table-dark">
                                <tr>
                                    <th>Loại</th>
                                    <th>Xem chi tiết</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>🏦 Lãi suất vay</td>
                                    <td><button class="btn btn-primary" onclick="showPDF('loan')">📄 Download</button></td>
                                </tr>
                                <tr>
                                    <td>💰 Lãi suất gửi tiết kiệm</td>
                                    <td><button class="btn btn-success" onclick="showPDF('saving')">📄 Download</button></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Testimonials Section -->
                <div class="testimonial-section mt-5">
                    <h2 class="services_taital text-center">💬 What Our Clients Say</h2>
                    <div class="testimonial text-center">
                        <p>"Financial Services made managing my savings and loans effortless. Their customer support is excellent!"</p>
                        <h4>— Emily Carter</h4>
                    </div>
                </div>

                <!-- Call-To-Action -->
                <div class="cta-section text-center mt-5">
                    <h2>Ready to take control of your finances?</h2>
                    <a href="login" class="btn btn-lg btn-warning">Get Started Now</a>
                </div>
            </div>
        </div>
        <!-- About Section End -->



        <script>
            function showPDF(type) {
                var pdfFrame = document.getElementById("pdfFrame");
                pdfFrame.src = "downloadPDF?type=" + type; // Load file PDF
                $("#pdfModal").modal("show"); // Hi?n th? modal
            }
        </script>
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

        <!-- Modal hi?n th? PDF -->
        <div class="modal fade" id="pdfModal" tabindex="-1" aria-labelledby="pdfModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl"> 
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="pdfModalLabel">PDF Preview</h5>
                    </div>
                    <div class="modal-body">
                        <iframe id="pdfFrame" width="100%" height="600px" style="border: none;"></iframe>
                    </div>
                </div>
            </div>
        </div>

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
