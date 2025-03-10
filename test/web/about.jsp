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
                                <a class="nav-link" href="home.jsp">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="about.jsp">About</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="Service">Services</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="team.html">Team</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="contact.html">Contact Us</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="news">News</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
            <!--search section-->

            <div class="search">
                <form action="">
                    <input type="text" placeholder="What do you need to search??"  name="searchvalue">
                    <button type="submit" class="site-btn">SEARCH</button>
                </form>
            </div>

            <!-- Login/logout -->
            <div class="login">
                <c:if test="${sessionScope.account != null}">
                    <c:if test="${sessionScope.account.role_id !=5}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Hello ${sessionScope.account.full_name}
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="userDropdown">
                                <li><a class="dropdown-item" href="changeInfor">View profile</a></li>
                                    <c:if test="${sessionScope.account.role_id==3}">
                                    <li><a class="dropdown-item" href="marketer/newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=0&status=all&sort=all&page=1&pageSize=4">Manage news</a></li>
                                    </c:if>
                                <li><a class="dropdown-item" href="logout">Logout</a></li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.account.role_id == 5}">
                        <li class="">
                            <a class="" href="changeInfor">Hello ${sessionScope.account.insurance_name}</a>

                        </li>
                        <li><a href="managerPolicy?insurance_id=${sessionScope.account.insurance_id}">Manage insurance policy</a></li>
                        <li><a class="dropdown-item" href="logout">Logout</a></li>
                        </c:if>
                    </c:if>    
                    <c:if test="${sessionScope.account == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="login">Login</a>
                    </li>
                </c:if>
            </div>
        </div>
        <!--header section end -->

       <!-- About Section Start -->
<div class="services_section layout_padding">
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <h1 class="services_taital">WELCOME TO FINANCIAL SERVICES</h1>
                <p class="services_text">At Financial Services, we are committed to providing secure, innovative, and customer-friendly financial solutions. Our mission is to empower individuals and businesses with the right financial tools for a brighter future.</p>
                <div class="moremore_bt"><a href="#">Learn More</a></div>
            </div>
            <div class="col-md-4">
                <img src="images/img-1.png" class="image_1" alt="Financial Services">
            </div>
        </div>

        <!-- Our Impact Section -->
        <div class="row text-center mt-4">
            <div class="col-md-4">
                <h3>📅 10+ Years</h3>
                <p>of Excellence</p>
            </div>
            <div class="col-md-4">
                <h3>💰 $1B+</h3>
                <p>in Transactions</p>
            </div>
            <div class="col-md-4">
                <h3>👥 500K+</h3>
                <p>Happy Customers</p>
            </div>
        </div>

        <!-- Interest Rates Section -->
        <div class="row mt-5">
            <div class="col-md-12">
                <h2 class="services_taital">📈 Interest Rates</h2>
                <p class="services_text">Stay updated with the latest interest rates for loans and savings to make informed financial decisions.</p>
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>Type</th>
                            <th>Download Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>🏦 Loan Interest Rate</td>
                            <td><button class="btn btn-primary" onclick="showPDF('loan')">📄 Download</button></td>
                        </tr>
                        <tr>
                            <td>💰 Saving Interest Rate</td>
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
