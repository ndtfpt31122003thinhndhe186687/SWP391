<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>News</title>
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
        .news-container {
            display: flex;
            flex-direction: column;
            gap: 20px;
            padding: 20px;
        }

        .news-card {
            display: flex;
            justify-content: center;
            align-items: center;
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
            min-height: 120px;
            margin-bottom: 13px;
        }

        .news-content {
            text-align: center;
        }

        .news-category {
            font-size: 14px;
            color: red;
            font-weight: bold;
            text-transform: uppercase;
        }

        .news-date {
            font-size: 14px;
            color: gray;
        }

        .news-title {
            font-size: 18px;
            font-weight: bold;
            margin: 10px 0;
        }

        .news-link {
            font-size: 16px;
            color: red;
            text-decoration: none;
            font-weight: bold;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .news-link span {
            font-size: 20px;
            margin-left: 5px;
        }


    </style>
</head>
<body>
    <!--header section start -->
    <div class="header_section">
        <div class="header_left">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="logo"><a href="index.html"><img src="images/logo.png"></a></div>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="index.html">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="about.html">About</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="services.html">Services</a>
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

        <!-- Login/logout-->
        <div clas="login">
            <c:if test="${sessionScope.account != null}">
                <li class="nav-item">
                    <a class="nav-link" href="#">Hello ${sessionScope.account.full_name}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout">Logout</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="changeInfor">View profile</a>
                </li>

            </c:if>
            <c:if test="${sessionScope.account == null}">
                <li class="nav-item">
                    <a class="nav-link" href="login">Login</a>
                </li>
            </c:if>
        </div>    
    </div>
    <!--header section end -->

    <c:forEach items="${requestScope.listNews}" var="news">
        <div class="news-card">
            <div class="news-content">
                <span class="news-category">News </span> | 
                <span class="news-date">${news.created_at}</span>
                <h3 class="news-title">${news.title}</h3>
                <a href="newsDetail?news_id=${news.news_id}" class="news-link">View detail<span>&#8250;</span></a>
            </div>
        </div>
    </c:forEach>



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
</body>
</html>
