<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>News Detail</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 50px;
            background-color: #cc0000;
            color: white;
            font-size: 24px;
            font-weight: bold;
        }
        .logo {
            font-size: 28px;
            font-weight: bold;
            letter-spacing: 2px;
        }
        .nav {
            display: flex;
            gap: 20px;
        }
        .nav a {
            color: white;
            text-decoration: none;
            font-size: 16px;
        }
        .article-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background: #f8f8f8;
            border-radius: 10px;
        }
        .article-title {
            font-size: 32px;
            font-weight: bold;
            color: #cc0000;
        }
        .article-content {
            font-size: 18px;
            line-height: 1.6;
            margin-top: 20px;
        }
        .article-image img {
            width: 100%;
            border-radius: 10px;
            margin-top: 20px;
        }
        .related-articles {
            margin-top: 40px;
        }
        .related-articles h3 {
            color: #cc0000;
        }
        .related-item {
            font-size: 16px;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="logo">Cial Bank</div>
        <div class="nav">
            <a href="#">Home</a>
            <a href="#">About</a>
            <a href="#">Services</a>
            <a href="#">Contact</a>
        </div>
    </div>
    <div class="article-container">
        <div class="article-title">${newsDetail.title}</div>
        <div class="article-content">
           ${newsDetail.content}
        </div>
        <div class="article-image">
            <img src="https://via.placeholder.com/800x400" alt="Bank News">
        </div>
        <div class="related-articles">
            <h3>Related Articles</h3>
            <div class="related-item"><a href="#">System Maintenance Announcement on February 10, 2025</a></div>
            <div class="related-item"><a href="#">Special Offer for New Account Openings</a></div>
            <div class="related-item"><a href="#">New Loan Policies for Small Businesses</a></div>
        </div>
    </div>
</body>
</html>
