<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>News Detail</title>
    <!-- mobile metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <!-- site metas -->
    <title>News</title>
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
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');

        body {
            font-family: 'Poppins', sans-serif;
            margin: 0;
            padding: 0;
            background: #f9f9f9;
        }

        .header, .footer {
            background-color: #cc0000;
            color: white;
            text-align: center;
            padding: 15px 0;
            font-size: 18px;
            font-weight: bold;
        }

        .article-container {
            max-width: 900px;
            margin: 50px auto;
            padding: 20px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        .article-title {
            font-size: 28px;
            font-weight: bold;
            color: #cc0000;
            text-align: center;
            margin-bottom: 10px;
        }

        .article-date {
            font-size: 14px;
            color: #777;
            text-align: center;
            margin-bottom: 20px;
        }

        .article-image img {
            width: 100%;
            border-radius: 10px;
            margin-bottom: 20px;
        }

        .article-content {
            font-size: 16px;
            line-height: 1.6;
            color: #444;
            text-align: justify;
        }

        .related-articles {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 2px solid #ddd;
        }

        .related-articles h3 {
            color: #cc0000;
            font-size: 20px;
        }

        .related-item {
            font-size: 16px;
            margin: 10px 0;
        }

        .related-item a {
            color: #007bff;
            text-decoration: none;
            font-weight: 600;
        }

        .related-item a:hover {
            text-decoration: underline;
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
                            <a class="nav-link" href="home.jsp">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="about.html">About</a>
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
                                <li><a class="dropdown-item" href="newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=0&status=all&sort=all&page=1&pageSize=4">Manage news</a></li>
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
    <div class="article-container">
        <div class="article-title">${newsDetail.title}</div>
        <div class="article-date">
            <fmt:formatDate value="${newsDetail.created_at}" pattern="dd-MM-yyyy" />
        </div>
        <div class="article-content">
            ${firstPart}
        </div>
        <div class="article-image">
            <img src="imageNews/${newsDetail.picture}" alt="News Image">
        </div>
        <div class="article-content">
            ${secondPart}
        </div>
        <div class="related-articles">
            <h3>Related Articles</h3>
            <c:forEach items="${requestScope.listRelatedNews}" var="related">
            <div class="related-item"><a href="newsDetail?news_id=${related.news_id}">${related.title}</a></div>
            </c:forEach>
        </div>
    </div>
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
