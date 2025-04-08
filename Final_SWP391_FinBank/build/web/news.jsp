<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
            font-family: Arial, Helvetica, sans-serif;
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

        .category-filter {
            margin-bottom: 20px;
            padding: 10px;
            background: #f8f8f8;
            border-radius: 8px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .category-filter label {
            font-weight: bold;
            color: #333;
        }
        .category-list {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        .category-list button {
            padding: 8px 12px;
            border: 1px solid #ccc;
            background-color: white;
            cursor: pointer;
            border-radius: 5px;
            transition: 0.3s;
        }
        .category-list button:hover {
            background-color: #f0f0f0;
        }
        .category-list button.active {
            background-color: red;
            color: white;
            border-color: red;
        }

        .category-filter select {
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .news-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            max-width: 1100px;
            margin: 20px auto;
            padding: 10px;
            justify-content: center;
        }

        .news-item {
            display: flex;
            flex-direction: column;
            width: 32%;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            transition: transform 0.3s ease-in-out;
            text-align: center;
            padding: 10px;
        }

        .news-item:hover {
            transform: scale(1.03);
        }

        .news-item img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 10px 10px 0 0;
        }

        .news-content {
            padding: 15px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            flex: 1;
        }

        .news-content h2 {
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin: 0 0 5px 0;
        }

        .news-date {
            font-size: 14px;
            color: #777;
            margin-bottom: 5px;
        }

        .news-content-1 {
            font-size: 14px;
            color: #555;
            line-height: 1.4;
            overflow: hidden;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            margin-bottom: 10px;
        }

        .read-more {
            display: inline-block;
            color: #007bff;
            font-weight: bold;
            text-decoration: none;
            margin-top: auto;
        }

        .read-more:hover {
            text-decoration: underline;
        }
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 20px;
            padding: 10px;
            width: 100%;
        }

        .pagination a, .pagination span {
            padding: 8px 12px;
            margin: 0 5px;
            text-decoration: none;
            border: 1px solid #ddd;
            color: #007bff;
            background-color: #fff;
            border-radius: 5px;
            display: inline-block;
            text-align: center;
        }

        .pagination a:hover {
            background-color: red;
            color: white;
        }

        .pagination .current-page {
            background-color: red;
            color: white;
            font-weight: bold;
            border-radius: 5px;
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
                                <a class="nav-link" href="news">Tin tức</a>
                            </li>
                              <li class="nav-item">
                                <a class="nav-link" href="membership">Hội viên</a>
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
    <div>
        <div class="category-filter">
            <label>Chọn thể loại:</label>
            <div class="category-list">
                <button onclick="filterCategory(0)" class="${category_id == 0 ? 'active' : ''}">All</button>
                <c:forEach var="n" items="${requestScope.listNc}">
                    <button onclick="filterCategory(${n.category_id})" class="${n.category_id == category_id ? 'active' : ''}">
                        ${n.category_name}
                    </button>        
                </c:forEach>
            </div>
        </div>

        <script>
            function filterCategory(categoryId) {
                window.location.href = "news?category=" + categoryId + "&page=1";
            }
        </script>


        <div id="news-container" class="news-container">
            <c:forEach items="${requestScope.listNews}" var="news">
                <article class="news-item">
                    <img src="imageNews/${news.picture}" alt="">
                    <div class="news-content">
                        <h2>${news.title}</h2>
                        <span class="news-date">                             
                            <fmt:formatDate value="${news.created_at}" pattern="dd-MM-yyyy" />
                        </span>
                        <p class="news-content-1">${news.content}</p>
                        <a href="newsDetail?news_id=${news.news_id}" class="read-more">View detail</a>
                    </div>
                </article>
            </c:forEach>

        </div>
        <div class="pagination">
            <c:forEach begin="1" end="${totalPage}" var="i">
                <c:choose>
                    <c:when test="${i == page}">
                        <span class="current-page">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="news?category=${category_id}&page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
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
