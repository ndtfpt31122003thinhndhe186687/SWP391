<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="en">
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
                                    <a class="nav-link" href="home.jsp">Trang chủ</a>
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
                                    <a class="nav-link" href="team.html">Đội ngũ</a>
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
                            <div class="more_bt"><a href="#">Đọc thêm </a></div>
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

                <!-- Login/logout -->
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
                                        <li><a class="dropdown-item" href="marketer/newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=0&status=all&sort=all&page=1&pageSize=4">Quản lí tin tức</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role_id==1}">
                                        <li><a class="dropdown-item" href="staff_management?status=all&sort=full_name&type=&page=1&pageSize=2">Quản lý</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role_id==2}">
                                        <li><a class="dropdown-item" href="customerList">Quản lý</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role_id==4}">
                                        <li><a class="dropdown-item" href="staff_management?status=all&sort=full_name&type=&page=1&pageSize=2">Quản lý</a></li>
                                        </c:if>
                                    <li><a class="dropdown-item" href="logout">Đăng xuất</a></li>
                                </ul>
                            </li>
                        </c:if>

                        <c:if test="${sessionScope.account.role_id == 5}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" >
                                    Xin chào ${sessionScope.account.insurance_name}
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="userDropdown">
                                    <li><a class="dropdown-item" href="managerPolicy?insurance_id=${sessionScope.account.insurance_id}">Quản lý bảo hiểm</a></li>
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
                            <h1 class="services_taital">WELCOME TO MY BANKING</h1>
                            <p class="services_text">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it </p>
                            <div class="moremore_bt"><a href="#">Read More </a></div>
                        </div>
                        <div class="col-md-4">
                            <div><img src="images/img-1.png" class="image_1"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!--about section end -->
            <!--services section start -->
            <div class="what_we_do_section layout_padding">
                <div class="container">
                    <h1 class="what_taital">WHAT WE DO</h1>
                    <p class="what_text">We provide seamless banking services to help you manage your finances efficiently and securely.</p>
                    <div class="what_we_do_section_2">
                        <div class="row">
                            <div class="col-lg-3 col-sm-6">
                                <div class="box_main">
                                    <div class="icon_1"><img src="images/icon-1.png"></div>
                                    <h3 class="accounting_text">Accounting</h3>
                                    <p class="lorem_text">Lorem Ipsum is simply dummy text of the printing and</p>
                                    <div class="moremore_bt_1"><a href="#">Read More </a></div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6">
                                <div class="box_main">
                                    <div class="icon_1"><img src="images/icon-2.png"></div>
                                    <h3 class="accounting_text">Advisor</h3>
                                    <p class="lorem_text">Lorem Ipsum is simply dummy text of the printing and</p>
                                    <div class="moremore_bt_1"><a href="#">Read More </a></div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6">
                                <div class="box_main">
                                    <div class="icon_1"><img src="images/icon-3.png"></div>
                                    <h3 class="accounting_text">Investment</h3>
                                    <p class="lorem_text">Lorem Ipsum is simply dummy text of the printing and</p>
                                    <div class="moremore_bt_1"><a href="#">Read More </a></div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6">
                                <div class="box_main">
                                    <div class="icon_1"><img src="images/icon-4.png"></div>
                                    <h3 class="accounting_text">Financial</h3>
                                    <p class="lorem_text">Lorem Ipsum is simply dummy text of the printing and</p>
                                    <div class="moremore_bt_1"><a href="#">Read More </a></div>
                                </div>
                            </div>
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