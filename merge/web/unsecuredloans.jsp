<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Vay Tiêu Dùng Tín Chấp - Ngân Hàng ABC</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                line-height: 1.6;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 80%;
                margin: auto;
                overflow: hidden;
            }
            header {
                background: #dc3545;
                color: white;
                padding-top: 30px;
                min-height: 70px;
                border-bottom: #e8491d 3px solid;
            }
            header h1 {
                margin: 0;
                text-align: center;
            }
            .highlight {
                color: #e8491d;
                font-weight: bold;
            }
            .button {
                display: inline-block;
                background: #e8491d;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 5px;
            }
            #showcase {
                min-height: 400px;
                background: url('https://via.placeholder.com/1500x400') no-repeat center;
                text-align: center;
                color: white;
            }
            #showcase h1 {
                margin-top: 100px;
                font-size: 55px;
                margin-bottom: 10px;
            }
            #showcase p {
                font-size: 20px;
            }
            #features {
                margin-top: 20px;
            }
            #features .feature {
                float: left;
                width: 30%;
                padding: 10px;
                text-align: center;
            }
            .flex-container {
            display: flex;
            justify-content: space-between;
            gap: 20px;
            margin: 20px auto;
            max-width: 1000px;
        }

        .container {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            width: 48%;
            text-align: center;
            background-color: white;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .container img {
            width: 100px;
            margin-bottom: 15px;
        }

        .benefits {
            text-align: left;
        }

        .benefits ul {
            list-style-type: disc;
            padding-left: 20px;
        }

        .benefits h3 {
            font-size: 18px;
            font-weight: bold;
        }
        .conditions-container {
            display: flex;
            justify-content: space-between;
            gap: 20px;
            margin-top: 40px;
        }

        .condition-item {
            width: 30%;
            text-align: center;
        }

        .condition-item img {
            width: 100%;
            border-radius: 10px;
        }

        .condition-info {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            margin-top: 10px;
        }

        .condition-number {
            background-color: black;
            color: white;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
        }

        .condition-text {
            font-size: 16px;
        }
 
        </style>
    </head>
    <body>
        <header>
            <div>
                <h1>FinBank</h1>
            </div>
        </header>

        <section id="showcase">
            <div class="container">
                <h1 style="color: black;">Unsecured loan</h1>
                <p style="color: black;">Quickly meet your spending needs.</p>
                <a href="SendLoanReq" class="button">Consulting registration</a>
            </div>
        </section>

        <section id="features">
            <h2 style="color: black; text-align: center;">Why choose a loan product at FinBank?</h2>
            <div class="flex-container">
        <!-- First Box -->
        <div class="container">
            <img src="your-first-image-url.png" alt="Loan Benefits Image">
            <div class="benefits">
                <h3>Lợi ích vượt trội</h3>
                <ul>
                    <li>Lãi suất vay chỉ từ 12.9%/năm trên dư nợ giảm dần</li>
                    <li>Thời gian vay linh hoạt từ 12 – 60 tháng</li>
                    <li>Hạn mức vay lên đến 1 tỷ đồng</li>
                </ul>
            </div>
        </div>

        <!-- Second Box -->
        <div class="container">
            <img src="your-second-image-url.png" alt="Simple Procedure Image">
            <div class="benefits">
                <h3>Thủ tục đơn giản</h3>
                <ul>
                    <li>Không cần tài sản đảm bảo</li>
                    <li>Phê duyệt nhanh chóng, giải ngân nhanh</li>
                </ul>
            </div>
        </div>
    </div>
        </section>
        <section id="showcase">
            <h2 style="color: black; text-align: center;">Conditions and registration steps</h2>
            <div class="conditions-container">
        <!-- First condition -->
        <div class="condition-item">
            <img src="your-condition-image-1-url.png" alt="Condition 1">
            <div class="condition-info">
                <div class="condition-number">1</div>
                <div class="condition-text" style="color: black">Quốc tịch Việt Nam.</div>
            </div>
        </div>

        <!-- Second condition -->
        <div class="condition-item">
            <img src="your-condition-image-2-url.png" alt="Condition 2">
            <div class="condition-info">
                <div class="condition-number">2</div>
                <div class="condition-text" style="color: black">Từ 20 đến 70 tuổi.</div>
            </div>
        </div>

        <!-- Third condition -->
        <div class="condition-item">
            <img src="your-condition-image-3-url.png" alt="Condition 3">
            <div class="condition-info">
                <div class="condition-number">3</div>
                <div class="condition-text" style="color: black">Thu nhập tối thiểu 10 triệu đồng/tháng.</div>
            </div>
        </div>
    </div>
        </section>
        <section>
            <h2 style="color: black; text-align: center;">Other</h2>
            
        </section>

        
        


    </body>
</html>