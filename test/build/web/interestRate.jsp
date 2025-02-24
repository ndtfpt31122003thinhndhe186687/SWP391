<!DOCTYPE html>
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
            .container {
                max-width: 800px;
                margin: auto;
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .tabs {
                display: flex;
                border-bottom: 2px solid #ddd;
            }
            .tab {
                padding: 10px 20px;
                cursor: pointer;
            }
            .tab.active {
                border-bottom: 2px solid red;
                font-weight: bold;
            }
            .rate-list {
                list-style: none;
                padding: 0;
            }
            .rate-list li {
                display: flex;
                justify-content: space-between;
                padding: 10px;
                border-bottom: 1px solid #ddd;
            }
            .download {
                color: red;
                cursor: pointer;
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
                                <a class="nav-link" href="home.jsp">Home</a>
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
                                    <li><a class="dropdown-item" href="newsManage?staff_id=${sessionScope.account.staff_id}&status=all&sort=created_at&page=1&pageSize=2">Manage news</a></li>
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
        <div class="container">
            <ul class="rate-list">
                <li>
                    <span>Loan Interest Rate</span>
                    <button onclick="showPDF('loan')">Download</button>
                </li>
                <li>
                    <span>Saving Interest Rate</span>
                    <button onclick="showPDF('saving')">Download</button>
                </li>
            </ul>

            <div class="pdf-container" id="pdfContainer">
                <iframe id="pdfFrame" width="100%" height="600px"></iframe>
            </div>
        </div>

        <script>
            function showPDF(type) {
                var pdfContainer = document.getElementById("pdfContainer");
                var pdfFrame = document.getElementById("pdfFrame");
                pdfFrame.src = "downloadPDF?type=" + type; 
                pdfContainer.style.display = "block"; 
            }
        </script>

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
