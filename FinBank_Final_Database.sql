USE [master]
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'FinBank_SWP391')
BEGIN
	ALTER DATABASE FinBank_SWP391 SET OFFLINE WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE FinBank_SWP391 SET ONLINE;
	DROP DATABASE FinBank_SWP391;
END

GO

CREATE DATABASE FinBank_SWP391
GO

USE FinBank_SWP391
GO

/*******************************************************************************
	Drop tables if exists
*******************************************************************************/
DECLARE @sql nvarchar(MAX) 
SET @sql = N'' 

SELECT @sql = @sql + N'ALTER TABLE ' + QUOTENAME(KCU1.TABLE_SCHEMA) 
    + N'.' + QUOTENAME(KCU1.TABLE_NAME) 
    + N' DROP CONSTRAINT ' -- + QUOTENAME(rc.CONSTRAINT_SCHEMA)  + N'.'  -- not in MS-SQL
    + QUOTENAME(rc.CONSTRAINT_NAME) + N'; ' + CHAR(13) + CHAR(10) 
FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS AS RC 

INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KCU1 
    ON KCU1.CONSTRAINT_CATALOG = RC.CONSTRAINT_CATALOG  
    AND KCU1.CONSTRAINT_SCHEMA = RC.CONSTRAINT_SCHEMA 
    AND KCU1.CONSTRAINT_NAME = RC.CONSTRAINT_NAME 

EXECUTE(@sql) 

GO
DECLARE @sql2 NVARCHAR(max)=''

SELECT @sql2 += ' Drop table ' + QUOTENAME(TABLE_SCHEMA) + '.'+ QUOTENAME(TABLE_NAME) + '; '
FROM   INFORMATION_SCHEMA.TABLES
WHERE  TABLE_TYPE = 'BASE TABLE'

Exec Sp_executesql @sql2 
GO

CREATE TABLE role (
    role_id INT IDENTITY(1,1) PRIMARY KEY, 
    role_name NVARCHAR(50) NOT NULL UNIQUE, -- Tên vai trò (vd: admin, bank_teller, ...)
);

CREATE TABLE customer (
    customer_id INT IDENTITY(1,1) PRIMARY KEY,  
    full_name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
	username NVARCHAR(255) NOT NULL UNIQUE, -- Tên người dùng duy nhất
    password NVARCHAR(255) NOT NULL,
    phone_number NVARCHAR(20) UNIQUE,
    address NVARCHAR(MAX),
	card_type NVARCHAR(20) CHECK (card_type IN ('credit', 'debit')) NOT NULL, 
	amount DECIMAL(15, 2) DEFAULT 0.00,  -- Số dư tài khoản cho thẻ debit
    credit_limit DECIMAL(15, 2) DEFAULT 0.00,  -- Hạn mức tín dụng cho thẻ credit
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active',  
    gender NVARCHAR(20) CHECK (gender IN ('male', 'female')),
    date_of_birth DATE,
	role_id INT NOT NULL, -- Liên kết với bảng role
	created_at DATETIME DEFAULT GETDATE(),
    profile_picture NVARCHAR(255),
	credit_due_date INT default 0,
	FOREIGN KEY (role_id) REFERENCES role(role_id) -- Khóa ngoại tham chiếu bảng role
);
--them thuoc tinh no xau hay khong : đã có bảng riêng

CREATE TABLE staff (
    staff_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất cho mỗi nhân viên
    full_name NVARCHAR(255) NOT NULL, -- Họ và tên
    email NVARCHAR(255) NOT NULL UNIQUE, -- Email duy nhất
	username NVARCHAR(255) NOT NULL UNIQUE, -- Tên người dùng duy nhất
    password NVARCHAR(255) NOT NULL, -- Mật khẩu 
    phone_number NVARCHAR(20) UNIQUE, -- Số điện thoại
    gender NVARCHAR(20) CHECK (gender IN ('male', 'female')), -- Giới tính
    date_of_birth DATE, -- Ngày sinh (tùy chọn)
    address NVARCHAR(MAX), -- Địa chỉ
    role_id INT NOT NULL, -- Liên kết với bảng role
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo bản ghi
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active', -- Trạng thái nhân viên
    FOREIGN KEY (role_id) REFERENCES role(role_id) -- Khóa ngoại tham chiếu bảng role
);

CREATE TABLE news_category (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(255) NOT NULL
);

CREATE TABLE news (
    news_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của bài viết
	category_id INT NOT NULL, -- Liên kết với danh mục tin tức
    title NVARCHAR(255) NOT NULL, -- Tiêu đề bài viết
    content NVARCHAR(MAX) NOT NULL, -- Nội dung chi tiết của bài viết
    staff_id INT NOT NULL, -- ID nhân viên tạo bài viết
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo bài viết
    updated_at DATETIME DEFAULT GETDATE(), -- Ngày cập nhật bài viết
    picture NVARCHAR(255),
    status NVARCHAR(20) CHECK (status IN ('draft', 'pending', 'approved', 'rejected')) DEFAULT 'draft', -- Trạng thái bài viết
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id), -- Khóa ngoại tham chiếu bảng staff
	FOREIGN KEY (category_id) REFERENCES news_category(category_id)
);

CREATE TABLE news_views (
    id INT IDENTITY(1,1) PRIMARY KEY,
    news_id INT NOT NULL,
    view_date  DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (news_id) REFERENCES news(news_id)
);

CREATE TABLE term (
    term_id INT IDENTITY(1,1) PRIMARY KEY,
    term_name NVARCHAR(50) NOT NULL UNIQUE, -- Tên kỳ hạn (vd: "Monthly", "Quarterly", "Yearly")
    duration INT NOT NULL, -- Số tháng, quý, năm (vd: 6 tháng, 12 tháng, 24 tháng)
    term_type NVARCHAR(20) CHECK (term_type IN ('monthly', 'quarterly', 'annually')) NOT NULL, -- Loại kỳ hạn
	status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active'
	-- monthly: 1,2,3...
	-- quarterly: 3,6,9...
	-- annually : 12,24...
);

CREATE TABLE services (
    service_id INT IDENTITY(1,1) PRIMARY KEY,
    service_name NVARCHAR(255) NOT NULL UNIQUE,
    description NVARCHAR(MAX),
    service_type NVARCHAR(20) NOT NULL,
	-- 'savings', 'loan','deposit','withdrawal'
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active'
);

--tin tuc lai suat
CREATE TABLE service_terms (
    serviceTerm_id INT IDENTITY(1,1) PRIMARY KEY, 
	term_id int,
    service_id INT NOT NULL, -- Liên kết với bảng services
    term_name NVARCHAR(255) NOT NULL, -- Tên điều khoản (vd: "Điều khoản vay tín chấp", "Điều khoản gửi tiết kiệm")
    description NVARCHAR(MAX) NOT NULL, -- Mô tả ngắn về điều khoản
    contract_terms NVARCHAR(MAX) NOT NULL, -- Nội dung chi tiết điều khoản hợp đồng
	--both loan and saving
    early_payment_penalty DECIMAL(15, 2) DEFAULT 0.00, -- Phí trả trước hạn (nếu có của vay) (nếu khách rút tiền trước hạn, có thể bị mất lãi hoặc chịu phí)
	interest_rate DECIMAL(15, 2) DEFAULT 0.00,
	--loan
    min_payment DECIMAL(15, 2) DEFAULT 0.00, -- Số tiền tối thiểu cần trả mỗi kỳ (cho vay/nợ)
	--saving
	min_deposit DECIMAL(15, 2) DEFAULT 0.00, -- tiền gửi tối thiểu
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'inactive', -- Trạng thái của điều khoản
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo điều khoản+
	FOREIGN KEY (term_id) REFERENCES term(term_id), -- Khóa ngoại liên kết term
    FOREIGN KEY (service_id) REFERENCES services(service_id), -- Khóa ngoại liên kết dịch vụ
);
 

CREATE TABLE savings (
    savings_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
	serviceTerm_id INT NOT NULL,
    amount DECIMAL(15, 2),
    start_date DATETIME DEFAULT GETDATE(),
    end_date DATETIME NULL,
    [status] NVARCHAR(20) CHECK (status IN ('approved', 'pending','rejected','completed')) DEFAULT 'pending',   
	notes NVARCHAR(MAX), -- Ghi chú thêm
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (serviceTerm_id) REFERENCES service_terms(serviceTerm_id)
);

--Loan type(Neu ma la vay tin chap thi bat nhap luong, neu ma la vay the chap thi nhap tai san) : ok
CREATE TABLE loan (   
    loan_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
	serviceTerm_id INT NOT NULL,	
    amount DECIMAL(15, 2) NOT NULL,
	start_date DATETIME DEFAULT GETDATE(),
	end_date DATETIME NULL,
	image NVARCHAR(MAX) NOT NULL,
    notes NVARCHAR(MAX), -- Ghi chú thêm
	status NVARCHAR(20) CHECK (status IN ('pending', 'approved', 'rejected','complete')) DEFAULT 'pending',
	loan_type NVARCHAR(20) CHECK (loan_type IN ('secured', 'unsecured')) NOT NULL, -- Phân biệt thế chấp/tín chấp
	value_asset DECIMAL(15, 2) NOT NULL,
	payment_type NVARCHAR(20) CHECK (payment_type IN('reducing_balance', 'fixed_principal')) NOT NULL DEFAULT 'reducing_balance',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (serviceTerm_id) REFERENCES service_terms(serviceTerm_id)
);

--bang tra no
CREATE TABLE loan_payments (
    loan_payments_id INT IDENTITY(1,1) PRIMARY KEY,
    loan_id INT NOT NULL,
    payment_date DATETIME DEFAULT GETDATE(),
	payDate DATETIME, 
	payment_amount DECIMAL(15,2) NOT NULL,  -- Tổng số tiền trả 
	principal_amount DECIMAL(15,2) NOT NULL, -- Số tiền gốc trả trong kỳ
    interest_amount DECIMAL(15,2) NOT NULL, -- Số tiền lãi trả trong kỳ
    remaining_balance DECIMAL(15, 2) NOT NULL,
	penaltyfee DECIMAL(15, 2),
	payment_status NVARCHAR(20) CHECK(payment_status IN ('pending','complete','late')) DEFAULT 'pending',
    FOREIGN KEY (loan_id) REFERENCES loan(loan_id)
);

CREATE TABLE debt_management (
    debt_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL, -- ID của khách hàng
    loan_id INT, -- ID khoản vay 
    debt_status NVARCHAR(20) CHECK (debt_status IN ('good', 'bad')) DEFAULT 'good', -- Trạng thái nợ
	-- Điểm tín dụng : 1000 - ( tỉ lệ nợ thu nhập )x100  - ( overdue_days)*100    > 600 good
	-- tỉ lệ nợ thu nhập : tổng nợ / tổng thu nhập 10/40=0.25
    overdue_days INT DEFAULT 0, -- Số ngày quá hạn 3 
    notes NVARCHAR(MAX), -- Ghi chú về nợ xấu
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (loan_id) REFERENCES loan(loan_id)
);

CREATE TABLE feedback (
    feedback_id INT IDENTITY(1,1) PRIMARY KEY, -- Mã phản hồi duy nhất
    customer_id INT NOT NULL, -- ID của khách hàng (customer)
    service_id INT NULL, -- Dịch vụ liên quan đến phản hồi (nếu có)
    feedback_content NVARCHAR(MAX) NOT NULL, -- Nội dung phản hồi
	feedback_rate int NOT NULL,
    feedback_date DATETIME DEFAULT GETDATE(), -- Ngày gửi phản hồi
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id), 
    FOREIGN KEY (service_id) REFERENCES services(service_id) 
);

CREATE TABLE transactions (
    transaction_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
    amount DECIMAL(15, 2),
    transaction_date DATETIME DEFAULT GETDATE(),
    transaction_type NVARCHAR(20) CHECK (transaction_type IN ('deposit', 'withdrawal')) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

-- Bảng để quản lý bên thứ ba (insurance)
CREATE TABLE insurance (
    insurance_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của bên bảo hiểm
	role_id INT NOT NULL, -- Liên kết với bảng role
	username NVARCHAR(255) NOT NULL UNIQUE, -- Tên người dùng duy nhất
    password NVARCHAR(255) NOT NULL,
    insurance_name NVARCHAR(255) NOT NULL, -- Tên bên bảo hiểm
    email NVARCHAR(255) NOT NULL UNIQUE, -- Email liên lạc
    phone_number NVARCHAR(20)  UNIQUE, -- Số điện thoại
    address NVARCHAR(MAX), -- Địa chỉ
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active', -- Trạng thái
	FOREIGN KEY (role_id) REFERENCES role(role_id)
);

-- Bảng để quản lý các loại bảo hiểm của bên bảo hiểm

CREATE TABLE insurance_policy (
    policy_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của loại bảo hiểm
    insurance_id INT NOT NULL, -- ID bên bảo hiểm cung cấp
    policy_name NVARCHAR(255) NOT NULL, -- Tên loại bảo hiểm
    description NVARCHAR(MAX), -- Mô tả loại bảo hiểm
    coverage_amount DECIMAL(15, 2) NOT NULL, -- Số tiền được bảo hiểm
    premium_amount DECIMAL(15, 2) NOT NULL, -- Phí bảo hiểm
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active', -- Trạng thái
	image NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo loại bảo hiểm
    FOREIGN KEY (insurance_id) REFERENCES insurance(insurance_id)
);

-- Bảng để quản lý các điều khoản của bên bảo hiểm

CREATE TABLE insurance_terms (
    term_id INT IDENTITY(1,1) PRIMARY KEY,  -- ID duy nhất của điều khoản
    insurance_id INT NOT NULL,              -- Liên kết với bảng insurance
    policy_id INT NOT NULL,                           -- (Tùy chọn) Liên kết với bảng chính sách bảo hiểm
    term_name NVARCHAR(255) NOT NULL,       -- Tiêu đề điều khoản
    term_description NVARCHAR(MAX) NOT NULL, -- Nội dung chi tiết của điều khoản
    start_date DATE NOT NULL,            -- Ngày hiệu lực
    end_date DATE NOT NULL,                         -- Ngày hết hạn (nếu có)
	created_at  DATETIME DEFAULT GETDATE(),
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active', -- Trạng thái
    FOREIGN KEY (insurance_id) REFERENCES insurance(insurance_id),
    FOREIGN KEY (policy_id) REFERENCES insurance_policy(policy_id) 
);

-- Bảng để lưu thông tin hợp đồng bảo hiểm
CREATE TABLE insurance_contract (
    contract_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của hợp đồng
    contract_name NVARCHAR(MAX) NOT NULL,
	customer_id INT NOT NULL, -- ID khách hàng
	service_id INT NOT NULL, -- ID dich vu
	loan_id INT NOT NULL,
    policy_id INT NOT NULL, -- ID loại bảo hiểm
    start_date DATE NOT NULL, -- Ngày bắt đầu hiệu lực
    end_date DATE NOT NULL, -- Ngày kết thúc hiệu lực
	duration INT NOT NULL,
    payment_frequency NVARCHAR(50) CHECK (payment_frequency IN ('monthly', 'quarterly', 'annually')) 
	NOT NULL, -- Tần suất thanh toán : hàng tháng , hàng quý , hàng năm 
    status NVARCHAR(20) CHECK (status IN ('active', 'pending','expired', 'cancelled')) DEFAULT 'pending', -- Trạng thái
	image NVARCHAR(MAX) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo hợp đồng
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (service_id) REFERENCES services(service_id),
	FOREIGN KEY (loan_id) REFERENCES loan(loan_id),
    FOREIGN KEY (policy_id) REFERENCES insurance_policy(policy_id)
	
);
-- Bảng hợp đồng bảo hiểm thông tin chi tiết : M - M ( insurance vs contract)

CREATE TABLE insurance_contract_detail (
    contract_id INT NOT NULL,
    insurance_id INT NOT NULL,
    CoverageAmount DECIMAL(15, 2) NOT NULL, -- Số tiền bảo hiểm.
    PremiumAmount DECIMAL(15, 2) NOT NULL,  -- Phí bảo hiểm.
	PaidAmount Decimal(15,2) Not Null,      -- Số tiền đã trả.
    StartDate DATE NOT NULL,                -- Ngày bắt đầu bảo hiểm.
    EndDate DATE NOT NULL,                  -- Ngày kết thúc bảo hiểm.
    PRIMARY KEY (contract_id, insurance_id),  -- Khóa chính kết hợp.
    FOREIGN KEY (contract_id) REFERENCES insurance_contract(contract_id),        
    FOREIGN KEY (insurance_id) REFERENCES insurance(insurance_id)
);


CREATE TABLE insurance_transactions (
    transaction_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của giao dịch
    contract_id INT NOT NULL, -- ID hợp đồng bảo hiểm liên quan
    customer_id INT NOT NULL, -- ID khách hàng thực hiện giao dịch
    transaction_date DATETIME DEFAULT GETDATE(), -- Ngày giao dịch
    amount DECIMAL(15, 2) NOT NULL, -- Số tiền giao dịch
    transaction_type NVARCHAR(50) CHECK (transaction_type IN ('premium_payment', 'claim_payment')) NOT NULL, -- Loại giao dịch
    notes NVARCHAR(MAX), -- Ghi chú giao dịch
    FOREIGN KEY (contract_id) REFERENCES insurance_contract(contract_id), -- Khóa ngoại tới hợp đồng bảo hiểm
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) -- Khóa ngoại tới khách hàng
);

CREATE TABLE feedbackInsurance (
    feedback_id INT IDENTITY(1,1) PRIMARY KEY, -- Mã phản hồi duy nhất
    customer_id INT NOT NULL, -- ID của khách hàng (customer)
	insurance_id int NOT NULL,
    policy_id INT NULL, -- Dịch vụ liên quan đến phản hồi (nếu có)
    feedback_content NVARCHAR(MAX) NOT NULL, -- Nội dung phản hồi
    feedback_date DATETIME DEFAULT GETDATE(), -- Ngày gửi phản hồi
	feedback_rate int NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id), 
	FOREIGN KEY (insurance_id) REFERENCES insurance(insurance_id), 
    FOREIGN KEY (policy_id) REFERENCES insurance_policy(policy_id) 
);

CREATE TABLE serviceprovider (
    provider_id INT IDENTITY(1,1) PRIMARY KEY,
	role_id INT NOT NULL, -- Liên kết với bảng role
    Name NVARCHAR(255) NOT NULL,
	username NVARCHAR(255) NOT NULL UNIQUE, -- Tên người dùng duy nhất
    password NVARCHAR(255) NOT NULL,
    ServiceType NVARCHAR(50) CHECK (ServiceType IN ('Electricity', 'Water', 'Internet')),
    email NVARCHAR(255) NOT NULL UNIQUE, -- Email liên lạc
    phone_number NVARCHAR(20)  UNIQUE, -- Số điện thoại
    address NVARCHAR(MAX), -- Địa chỉ
	status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active', -- Trạng thái
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE electricityinvoice(
    invoice_id INT IDENTITY(1,1) PRIMARY KEY,
	provider_id INT NOT NULL,
	customer_id INT NOT NULL,
	ConsumptionKWh DECIMAL(10,2) NOT NULL, -- Chỉ số điện tiêu thụ
    Amount DECIMAL(18,2) NOT NULL,
	Duedate DATE NOT NULL,
	status NVARCHAR(50) CHECK (status IN ('pending','paid')) DEFAULT 'pending' ,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (provider_id) REFERENCES serviceprovider(provider_id)
);

CREATE TABLE waterinvoice(
    invoice_id INT IDENTITY(1,1) PRIMARY KEY,
	provider_id INT NOT NULL,
	customer_id INT NOT NULL,
	ConsumptionM3 DECIMAL(10,2) NOT NULL, -- Số mét khối nước tiêu thụ 
    Amount DECIMAL(18,2) NOT NULL,
	Duedate DATE NOT NULL,
	status NVARCHAR(50) CHECK (status IN ('pending','paid')) DEFAULT 'pending' ,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (provider_id) REFERENCES serviceprovider(provider_id)
);

CREATE TABLE internetinvoice(
    invoice_id INT IDENTITY(1,1) PRIMARY KEY,
	provider_id INT NOT NULL,
	customer_id INT NOT NULL,
	Package NVARCHAR(100) NOT NULL, -- Tên gói cước ( 100Mbps, 200Mbps)
    Amount DECIMAL(18,2) NOT NULL,
	Duedate DATE NOT NULL,
	status NVARCHAR(50) CHECK (status IN ('pending','paid')) DEFAULT 'pending' ,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (provider_id) REFERENCES serviceprovider(provider_id)
);

CREATE TABLE vip_term (
    vipTerm_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của điều khoản VIP
    term_id INT, -- Liên kết với bảng kỳ hạn (term)
    term_name NVARCHAR(255) NOT NULL, -- Tên điều khoản VIP (VD: "1 Month Membership Silver")
    description NVARCHAR(MAX) NOT NULL, -- Mô tả ngắn về điều khoản VIP
    contract_terms NVARCHAR(MAX) NOT NULL, -- Nội dung chi tiết điều khoản hợp đồng
	vip_type NVARCHAR(20) CHECK (vip_type IN ('silver', 'gold', 'diamond')) NOT NULL, -- Loại VIP
    price DECIMAL(15, 2) NOT NULL, -- Giá của gói VIP
	loan_rate DECIMAL(15, 2) DEFAULT 0.00,
	savings_rate DECIMAL(15, 2) DEFAULT 0.00,
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'inactive', -- Trạng thái điều khoản
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo điều khoản
    FOREIGN KEY (term_id) REFERENCES term(term_id), -- Khóa ngoại liên kết kỳ hạn
);

CREATE TABLE vip (
    vip_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của VIP
    customer_id INT NOT NULL, -- Khách hàng sở hữu VIP
    vipTerm_id INT NOT NULL, -- Điều khoản dịch vụ liên quan đến gói VIP
    start_date DATETIME DEFAULT GETDATE(), -- Ngày bắt đầu VIP
    end_date DATETIME NULL, -- Ngày kết thúc VIP
    status NVARCHAR(20) CHECK (status IN ('active', 'expired', 'cancelled')) DEFAULT 'active', -- Trạng thái VIP
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id), -- Liên kết với bảng khách hàng
    FOREIGN KEY (vipTerm_id) REFERENCES vip_term(vipTerm_id) -- Liên kết với bảng điều khoản dịch vụ
);

CREATE TABLE notifications (
    notification_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    reference_id INT NOT NULL, -- ID tham chiếu (savings_id, loan_id, transaction_id, ...)
    notification_type NVARCHAR(50) NOT NULL, -- Loại thông báo: deposit, withdraw, savings, loan
    message NVARCHAR(255) NOT NULL, -- Nội dung thông báo
    created_at DATETIME DEFAULT GETDATE(),
    is_read NVARCHAR(20) CHECK(is_read IN ('read','unread')) DEFAULT 'unread',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

--CREATE TABLE activity_log (
--    id INT PRIMARY KEY IDENTITY, 1 
--    staff_id INT, 2 
--    role NVARCHAR(50), 2
--    action NVARCHAR(255), chấp nhận đơn vay cho khách hàng id 4
--    timestamp DATETIME DEFAULT GETDATE(), 14/3/2025
--    FOREIGN KEY (staff_id) REFERENCES staff(id)
--);


-- mật khẩu cấp 2 



-- chương trình khuyến mãi : mở tài khoản tặng 50k , nạp ít nhất 500k  sẽ nhận 50k, thời gian từ 1/4/2025 -> 1/5/2025
-- giới thiệu bạn bè đăng ký và có giao dịch 1 triệu thì tặng người giới thiếu 100k , người dc giới thiệu 50k
-- gửi tiết kiệm : gửi nhiều lãi cao , 100 triệu trong 6 tháng thì 7,5% thay vì 7%  áp dụng cho khách hàng mới hoặc lần đầu gửi tiền trên 100 triệu 

--CREATE TABLE promotion (
--    id INT PRIMARY KEY IDENTITY,
--    name NVARCHAR(255),
--    description NVARCHAR(500),
--    start_date DATE,
--    end_date DATE,
--    discount_type NVARCHAR(50), -- Ví dụ: "Tặng tiền", "Bạn bè cùng vui ", "Tăng lãi suất"
--    discount_value DECIMAL(10,2),
--    conditions NVARCHAR(500)
--);

INSERT INTO role (role_name)
VALUES 
('admin'),
('banker'),
('marketer'),
('accountant'),
('insurance'),
('customer'),
('serviceprovider');

INSERT INTO customer (full_name, email, username, password, phone_number, address, card_type, amount, credit_limit, status, gender, date_of_birth, profile_picture,role_id)
VALUES 
(N'Nguyễn Đức Thịnh', 'thinhndhe186687@fpt.edu.vn', 'a', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456789', '123 Street, City', 'credit', 0.00, 5000000.00, 'active', 'male', '1990-01-01', 'profile_a.jpg',6),
(N'Lê Hồng Phong', 'hongphongbs7b@gmail.com', 'b', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0987654321', '456 Avenue, City', 'debit', 20000000, 0.00, 'active', 'female', '1992-02-02', 'profile_b.jpg',6),
(N'Triệu Hoàng Sơn', 'c@example.com', 'c', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456780', '789 Boulevard, City', 'credit', 0.00, 3000000.00, 'active', 'male', '1985-03-03', 'profile_c.jpg',6),
(N'Nguyễn Hoàng Vương', 'd@example.com', 'd', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0987654312', '321 Road, City', 'debit', 25000000, 0.00, 'active', 'female', '1995-04-04', 'profile_d.jpg',6),
(N'Vo Van E', 'e@example.com', 'e', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456790', '654 Lane, City', 'credit',0.00, 8000000.00, 'active', 'male', '1988-05-05', 'profile_e.jpg',6);


INSERT INTO staff (full_name, email,username,password, phone_number, gender, date_of_birth, address, role_id, status)
VALUES 
('Nguyen Van F', 'f@example.com', 'a', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456781', 'male', '1980-06-06', '111 Staff St, City', 1, 'active'),
('Tran Thi G', 'g@example.com', 'b', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0987654322', 'female', '1983-07-07', '222 Staff Ave, City', 2, 'active'),
('Le Van H', 'h@example.com', 'c', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456782', 'male', '1978-08-08', '333 Staff Blvd, City', 3, 'active'),
('Pham Thi I', 'i@example.com', 'd', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0987654323', 'female', '1990-09-09', '444 Staff Rd, City', 4, 'active'),
('Vo Van J', 'j@example.com', 'e', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456783', 'male', '1985-10-10', '555 Staff Lane, City', 2, 'active'),
('Nguyen duc thinh ','thinh@example.com', 'f', 'UJcZdUkab9YmW+57megoQi2mtU4=', '111111', 'female', '1983-07-07', '222 Staff Ave, City', 2, 'active'),
('Le hong phong', 'phong@example.com', 'g', 'UJcZdUkab9YmW+57megoQi2mtU4=', '222222', 'male', '1978-08-08', '333 Staff Blvd, City', 3, 'active'),
('Trinh minh hieu', 'hieu@example.com', 'h', 'UJcZdUkab9YmW+57megoQi2mtU4=', '33333', 'female', '1990-09-09', '444 Staff Rd, City', 4, 'active'),
('Dinh Thanh Tung', 'tung@example.com', 'i', 'UJcZdUkab9YmW+57megoQi2mtU4=', '444444', 'male', '1985-10-10', '555 Staff Lane, City', 2, 'active');

INSERT INTO news_category (category_name) VALUES 
(N'Tin tức về lãi suất'),
(N'Chính sách ngân hàng'),
(N'Ưu đãi cho vay'),
(N'Kế hoạch tiết kiệm');

INSERT INTO news (title, content, staff_id, category_id, status,picture) VALUES 
(N'Xu hướng lãi suất: Những điều mong đợi vào năm 2025',
N'Khi chúng ta tiến gần đến năm 2025, lãi suất dự kiến ​​sẽ dao động do nhiều yếu tố kinh tế khác nhau, bao gồm lạm phát, chính sách của ngân hàng trung ương và xu hướng tài chính toàn cầu. Trong vài năm qua, lãi suất đã bị ảnh hưởng bởi các nỗ lực phục hồi kinh tế sau đại dịch, những thay đổi về tỷ lệ việc làm và sự thay đổi trong thói quen chi tiêu của người tiêu dùng.
Các nhà phân tích tài chính dự đoán rằng năm 2025 sẽ là một năm then chốt, với những điều chỉnh lãi suất tiềm ẩn có thể tác động đến cả người tiết kiệm và người đi vay.
Đối với những cá nhân có tài khoản tiết kiệm, lãi suất tăng có thể mang đến cơ hội kiếm được lợi nhuận cao hơn từ tiền gửi của họ. Mặt khác, đối với những người tìm kiếm khoản vay, cho dù là mục đích cá nhân hay kinh doanh, lãi suất tăng có thể đồng nghĩa với chi phí đi vay cao hơn. Các ngân hàng trung ương trên khắp thế giới đang theo dõi chặt chẽ các chỉ số kinh tế để xác định phương án hành động tốt nhất.
Ngoài ra, căng thẳng địa chính trị, các hiệp định thương mại và những tiến bộ công nghệ trong lĩnh vực tài chính sẽ đóng vai trò quan trọng trong việc định hình xu hướng lãi suất. Một số chuyên gia tin rằng các cải tiến về ngân hàng kỹ thuật số và công nghệ tài chính sẽ giới thiệu các sản phẩm tài chính mới cung cấp lãi suất cạnh tranh, thách thức các tổ chức ngân hàng truyền thống.
Người mua nhà và người nắm giữ thế chấp cũng nên chú ý đến xu hướng lãi suất, vì những biến động có thể ảnh hưởng đến các khoản thanh toán hàng tháng và cơ hội tái cấp vốn. Các khoản thế chấp lãi suất cố định có thể mang lại sự ổn định, trong khi các khoản vay lãi suất thả nổi có thể trở nên rủi ro hơn trong môi trường lãi suất tăng.
Khi người tiêu dùng và doanh nghiệp điều hướng những thay đổi này, kiến ​​thức tài chính và lập kế hoạch chủ động sẽ rất cần thiết. Hiểu được xu hướng thị trường và tìm kiếm lời khuyên của chuyên gia có thể giúp các cá nhân và tổ chức đưa ra quyết định tài chính sáng suốt. Cho dù bạn đang tiết kiệm, đầu tư hay vay nợ, việc cập nhật xu hướng lãi suất vào năm 2025 sẽ là chìa khóa để tối ưu hóa chiến lược tài chính của bạn.',
3, 1, 'approved','news.png'),

(N'Hiểu tác động của thay đổi lãi suất',
N'Lãi suất đóng vai trò quan trọng trong nền kinh tế, ảnh hưởng đến mọi thứ từ chi tiêu của người tiêu dùng đến đầu tư kinh doanh. Khi lãi suất thay đổi, tác động lan tỏa đến nhiều lĩnh vực khác nhau, tác động đến cả cá nhân và doanh nghiệp. Nhưng chính xác thì những biến động này định hình các quyết định tài chính như thế nào?

Khi lãi suất tăng, chi phí vay tăng, khiến người tiêu dùng và doanh nghiệp phải trả nhiều tiền hơn khi vay vốn. Điều này có thể làm chậm tăng trưởng kinh tế vì các công ty có thể trì hoãn kế hoạch mở rộng và cá nhân có thể cân nhắc lại các khoản mua lớn như nhà hoặc ô tô. Lãi suất cao hơn cũng có thể dẫn đến tăng thanh toán thẻ tín dụng và thế chấp, làm giảm thu nhập khả dụng và có khả năng làm giảm chi tiêu của người tiêu dùng.

Ngược lại, khi lãi suất giảm, việc vay vốn trở nên dễ dàng hơn, khuyến khích cả doanh nghiệp và cá nhân vay vốn mới. Điều này có thể kích thích hoạt động kinh tế, dẫn đến tăng trưởng trong các lĩnh vực như bất động sản, bán ô tô và tinh thần kinh doanh. Lãi suất thấp hơn cũng có thể giảm bớt gánh nặng tài chính cho những người hiện đang có các khoản vay lãi suất thả nổi, giải phóng tiền để tiết kiệm hoặc đầu tư.

Tuy nhiên, những thay đổi về lãi suất không chỉ ảnh hưởng đến người đi vay. Người tiết kiệm và nhà đầu tư cũng phải thích ứng với bối cảnh tài chính đang thay đổi. Khi lãi suất tăng, các tài khoản tiết kiệm và đầu tư thu nhập cố định như trái phiếu có thể mang lại lợi nhuận cao hơn, khiến chúng hấp dẫn hơn đối với các nhà đầu tư thận trọng. Mặt khác, khi lãi suất giảm, lợi nhuận từ tiền tiết kiệm và trái phiếu có xu hướng giảm, thúc đẩy các nhà đầu tư hướng đến các tài sản rủi ro hơn như cổ phiếu hoặc bất động sản để tìm kiếm lợi nhuận tốt hơn.

Các ngân hàng trung ương, chẳng hạn như Cục Dự trữ Liên bang hoặc Ngân hàng Trung ương Châu Âu, đóng vai trò quan trọng trong việc thiết lập lãi suất. Họ điều chỉnh lãi suất dựa trên các điều kiện kinh tế, nhằm mục đích kiểm soát lạm phát, khuyến khích việc làm và duy trì sự ổn định tài chính. Các nhà hoạch định chính sách phân tích các yếu tố như tăng trưởng GDP, mức độ việc làm và xu hướng lạm phát trước khi đưa ra quyết định về lãi suất.

Đối với cả người tiêu dùng và doanh nghiệp, việc hiểu được cách thay đổi lãi suất tác động đến nền kinh tế có thể giúp đưa ra các quyết định tài chính sáng suốt. Cho dù bạn đang cân nhắc vay vốn, lập kế hoạch đầu tư hay quản lý nợ, việc cập nhật xu hướng lãi suất là điều cần thiết để thành công về mặt tài chính.',
3, 1, 'approved','news.png'),

(N'Giải thích về Quyết định lãi suất của Ngân hàng trung ương',
N'Các ngân hàng trung ương đóng vai trò quan trọng trong việc định hình nền kinh tế thông qua chính sách tiền tệ, trong đó quyết định lãi suất là một trong những công cụ mạnh mẽ nhất của họ. Những quyết định này ảnh hưởng đến lạm phát, việc làm và sự ổn định tài chính nói chung. Nhưng các ngân hàng trung ương xác định lãi suất như thế nào và những tác động của các lựa chọn của họ là gì?

Mục tiêu chính của các ngân hàng trung ương, chẳng hạn như Cục Dự trữ Liên bang (Fed), Ngân hàng Trung ương Châu Âu (ECB) hoặc Ngân hàng Nhật Bản (BoJ), là duy trì sự ổn định kinh tế bằng cách kiểm soát lạm phát và thúc đẩy tăng trưởng kinh tế. Khi lạm phát tăng quá nhanh, các ngân hàng trung ương có thể tăng lãi suất để làm chậm lại việc vay mượn và chi tiêu quá mức. Ngược lại, khi nền kinh tế trì trệ, việc hạ lãi suất có thể khuyến khích đầu tư và chi tiêu của người tiêu dùng.

Các quyết định về lãi suất dựa trên nhiều chỉ số kinh tế khác nhau, bao gồm:

Tỷ lệ lạm phát – Tỷ lệ lạm phát cao thường thúc đẩy các ngân hàng trung ương tăng lãi suất để giảm nguồn cung tiền và kiềm chế giá cả tăng cao.
Dữ liệu việc làm – Tỷ lệ thất nghiệp thấp có thể cho thấy nền kinh tế mạnh, trong khi tỷ lệ thất nghiệp cao có thể thúc đẩy các ngân hàng trung ương hạ lãi suất để kích thích tạo việc làm.

Tăng trưởng GDP – Một nền kinh tế đang phát triển có thể chịu được lãi suất cao hơn, trong khi một nền kinh tế đang suy thoái có thể cần lãi suất thấp hơn để khuyến khích đầu tư.
Xu hướng kinh tế toàn cầu – Lãi suất cũng chịu ảnh hưởng của thương mại quốc tế, căng thẳng địa chính trị và xu hướng kinh tế ở các nền kinh tế lớn như Hoa Kỳ hoặc Trung Quốc.
Khi một ngân hàng trung ương tăng lãi suất, chi phí vay tăng đối với các cá nhân và doanh nghiệp, khiến các khoản vay và thế chấp trở nên đắt đỏ hơn. Điều này có thể làm chậm hoạt động kinh tế nhưng giúp kiểm soát lạm phát. Ngược lại, khi lãi suất được hạ xuống, việc vay trở nên rẻ hơn, kích thích tăng trưởng kinh doanh và chi tiêu của người tiêu dùng.

Trong những năm gần đây, chính sách lãi suất đã trở thành chủ đề gây tranh cãi. Một số người cho rằng việc duy trì lãi suất quá thấp trong thời gian quá dài sẽ dẫn đến việc chấp nhận rủi ro quá mức trên thị trường tài chính, trong khi những người khác tin rằng việc tăng lãi suất mạnh có thể kìm hãm tăng trưởng kinh tế. Việc tìm ra sự cân bằng phù hợp là một thách thức mà các ngân hàng trung ương liên tục phải đối mặt.

Hiểu được cách các ngân hàng trung ương đưa ra những quyết định này có thể giúp người tiêu dùng và doanh nghiệp đưa ra những lựa chọn tài chính sáng suốt. Cho dù bạn đang có kế hoạch mua nhà, khởi nghiệp kinh doanh hay đầu tư vào thị trường chứng khoán, việc nắm bắt xu hướng lãi suất có thể giúp bạn có lợi thế trong việc điều hướng bối cảnh tài chính.',
3, 1, 'approved','news.png'),

(N'Tài khoản tiết kiệm và lãi suất: Hướng dẫn toàn diện',
N'Với lãi suất thay đổi, điều cần thiết là phải hiểu cách chúng ảnh hưởng đến tài khoản tiết kiệm. Lãi suất đóng vai trò quan trọng trong việc xác định mức lợi nhuận bạn kiếm được từ tiền gửi của mình. Khi các tổ chức tài chính điều chỉnh lãi suất dựa trên điều kiện kinh tế, người tiêu dùng phải luôn cập nhật thông tin về các lựa chọn tiết kiệm tốt nhất hiện có.

Một trong những yếu tố chính khi lựa chọn tài khoản tiết kiệm là hiểu được sự khác biệt giữa lãi suất cố định và lãi suất thả nổi. Lãi suất cố định mang lại sự ổn định, cho phép người tiết kiệm dự đoán thu nhập của mình theo thời gian. Tuy nhiên, lãi suất thả nổi dao động, nghĩa là lợi nhuận có thể tăng hoặc giảm tùy thuộc vào điều kiện thị trường.

Ngoài ra, các tổ chức tài chính cung cấp các loại tài khoản tiết kiệm khác nhau, chẳng hạn như tài khoản tiết kiệm lãi suất cao, tài khoản thị trường tiền tệ và chứng chỉ tiền gửi (CD). Mỗi lựa chọn này đều có những lợi ích và hạn chế riêng. Ví dụ, tài khoản lãi suất cao thường mang lại lợi nhuận tốt hơn tài khoản tiết kiệm tiêu chuẩn nhưng có thể có nhiều hạn chế hơn về việc rút tiền. Mặt khác, CD cung cấp lãi suất cao hơn nhưng yêu cầu bạn phải khóa tiền trong một khoảng thời gian cố định.

Một yếu tố quan trọng khác cần xem xét là lạm phát. Mặc dù một tài khoản tiết kiệm lãi suất cao có vẻ hấp dẫn, nhưng lạm phát có thể làm xói mòn giá trị thực của thu nhập của bạn. Đây là lý do tại sao việc cân bằng lãi suất với các yếu tố khác, chẳng hạn như phí và khả năng tiếp cận, là điều cần thiết để đảm bảo quyết định tài chính tốt nhất cho nhu cầu của bạn.

Để tối đa hóa khoản tiết kiệm của mình, hãy cân nhắc tự động hóa tiền gửi và tận dụng lãi suất khuyến mại do các ngân hàng cung cấp. Bằng cách cập nhật thông tin về xu hướng hiện tại và hiểu được các sắc thái của các lựa chọn tiết kiệm khác nhau, bạn có thể đảm bảo rằng tiền của mình đang hoạt động hiệu quả cho bạn.',
3, 1, 'approved','news.png'),

(N'Tương lai của các khoản vay và lãi suất',
N'Khi lãi suất thay đổi, các lựa chọn khả dụng cho người tiêu dùng tìm kiếm khoản vay cũng thay đổi. Cho dù bạn đang có kế hoạch mua nhà, khởi nghiệp kinh doanh hay tài trợ cho một khoản mua lớn, việc hiểu được xu hướng lãi suất trong tương lai là điều cần thiết.

Các nhà kinh tế dự đoán rằng lãi suất vào năm 2025 sẽ bị ảnh hưởng bởi các yếu tố như lạm phát, chính sách của ngân hàng trung ương và điều kiện kinh tế toàn cầu. Môi trường lãi suất tăng có thể khiến việc vay vốn trở nên đắt đỏ hơn, tăng các khoản thanh toán hàng tháng cho các khoản thế chấp, các khoản vay cá nhân và các khoản vay kinh doanh. Mặt khác, nếu lãi suất giảm, người đi vay có thể tìm thấy các điều khoản thuận lợi hơn và chi phí trả nợ thấp hơn.

Đối với người mua nhà, lãi suất thế chấp đóng vai trò quan trọng trong việc xác định khả năng chi trả. Những người đang cân nhắc mua nhà có thể cần đánh giá xem việc khóa thế chấp lãi suất cố định có phải là lựa chọn tốt hơn hay không so với việc lựa chọn thế chấp lãi suất thả nổi, vốn dao động tùy theo điều kiện thị trường.

Các chủ doanh nghiệp cũng nên theo dõi xu hướng lãi suất vì chúng có thể tác động đến các lựa chọn tài trợ cho việc mở rộng và chi phí hoạt động. Chi phí vay cao hơn có thể dẫn đến giảm đầu tư, trong khi lãi suất thấp hơn có thể thúc đẩy tăng trưởng kinh doanh.

Vai trò của các công ty công nghệ tài chính trong ngành cho vay dự kiến ​​sẽ mở rộng, cung cấp cho người tiêu dùng các lựa chọn vay thay thế ngoài các ngân hàng truyền thống. Các nền tảng cho vay trực tuyến, cho vay ngang hàng và các giải pháp tài chính phi tập trung (DeFi) đang trở nên nổi bật hơn, cung cấp lãi suất cạnh tranh và quy trình phê duyệt hợp lý.

Khi lãi suất tiếp tục biến động, người đi vay nên đánh giá cẩn thận các điều khoản cho vay, so sánh các bên cho vay và cân nhắc các cơ hội tái cấp vốn khi lãi suất thuận lợi. Việc luôn cập nhật thông tin về các xu hướng kinh tế sẽ giúp các cá nhân và doanh nghiệp đưa ra quyết định tài chính sáng suốt trong những năm tới.',
3, 1, 'approved','news.png'),

(N'Tác động của các chính sách ngân hàng mới',
N'Những thay đổi gần đây trong các chính sách ngân hàng đã gây ra một cuộc tranh luận đáng kể giữa các chuyên gia trong ngành. Nhiều người tin rằng các chính sách này sẽ dẫn đến một môi trường tài chính ổn định hơn, trong khi những người khác bày tỏ lo ngại về tác động tiềm tàng của chúng đối với các doanh nghiệp nhỏ. Khi các ngân hàng thích ứng với các quy định này, điều quan trọng là phải theo dõi tác động của chúng đối với các hoạt động cho vay và khả năng tiếp cận tín dụng của người tiêu dùng.

Một trong những mục tiêu chính của các chính sách mới là tăng cường tính minh bạch trong hoạt động ngân hàng. Các cơ quan quản lý đã đưa ra các hướng dẫn chặt chẽ hơn về phê duyệt khoản vay, công bố lãi suất và bảo vệ quyền lợi của người tiêu dùng. Sự thay đổi này nhằm mục đích xây dựng lòng tin giữa các tổ chức tài chính và khách hàng của họ bằng cách đảm bảo rằng các quy trình ngân hàng công bằng và có trách nhiệm.

Tuy nhiên, những thay đổi chính sách này cũng đặt ra những thách thức. Các doanh nghiệp nhỏ, vốn thường dựa vào nguồn tài chính dễ tiếp cận, có thể phải đối mặt với các yêu cầu cho vay chặt chẽ hơn, khiến họ khó đảm bảo được các khoản vay hơn. Để ứng phó, một số tổ chức tài chính đang khám phá các mô hình tài trợ thay thế, chẳng hạn như các chương trình tài chính vi mô và các sáng kiến ​​cho vay do chính phủ bảo lãnh, để hỗ trợ tăng trưởng cho các doanh nghiệp nhỏ.

Ngoài ra, các giải pháp ngân hàng kỹ thuật số và công nghệ tài chính đang đóng vai trò ngày càng quan trọng trong bối cảnh tài chính. Khi những thay đổi về quy định có hiệu lực, các ngân hàng đang đầu tư vào các giải pháp công nghệ để hợp lý hóa các quy trình tuân thủ đồng thời nâng cao dịch vụ khách hàng. Ngân hàng di động, tư vấn tài chính hỗ trợ AI và các biện pháp bảo mật dựa trên blockchain là một số cải tiến định hình tương lai của ngành ngân hàng theo các chính sách mới.

Cuối cùng, tác động lâu dài của những thay đổi về quy định này sẽ phụ thuộc vào mức độ thích ứng của các ngân hàng, doanh nghiệp và người tiêu dùng với môi trường tài chính đang thay đổi. Việc đánh giá và đối thoại liên tục giữa các nhà hoạch định chính sách và các bên liên quan trong ngành sẽ rất cần thiết trong việc định hình một hệ thống ngân hàng cân bằng giữa tính ổn định, tăng trưởng và khả năng tiếp cận.',
3, 2, 'approved','news.png'),

(N'Chính sách ngân hàng và bảo vệ người tiêu dùng',
N'Bảo vệ người tiêu dùng đã trở thành trọng tâm trong các chính sách ngân hàng mới nhất. Với các biện pháp mới nhằm bảo vệ quyền lợi của khách hàng, các ngân hàng hiện được yêu cầu phải minh bạch hơn về phí và lệ phí. Sự thay đổi này dự kiến ​​sẽ tăng cường lòng tin giữa người tiêu dùng và các tổ chức tài chính, cuối cùng mang lại lợi ích cho toàn bộ nền kinh tế.

Một trong những thay đổi đáng chú ý nhất liên quan đến việc điều chỉnh các khoản phí ẩn. Các ngân hàng hiện được yêu cầu phải công khai rõ ràng mọi khoản phí liên quan đến dịch vụ của mình, từ phí thấu chi đến chi phí rút tiền tại ATM. Sáng kiến ​​này trao quyền cho người tiêu dùng đưa ra quyết định tài chính sáng suốt và ngăn chặn các khoản phí bất ngờ ảnh hưởng tiêu cực đến tài chính của họ.

Một bước phát triển quan trọng khác là việc tăng cường các giao thức an ninh mạng. Với sự gia tăng của ngân hàng kỹ thuật số, các cơ quan quản lý đang áp dụng các yêu cầu bảo mật chặt chẽ hơn để bảo vệ người tiêu dùng khỏi gian lận và trộm cắp danh tính. Các ngân hàng hiện phải triển khai các phương pháp xác thực tiên tiến, chẳng hạn như xác minh sinh trắc học và giao dịch được mã hóa, để bảo vệ dữ liệu của khách hàng.

Ngoài ra, các tổ chức tài chính đang phải chịu trách nhiệm về các hoạt động cho vay công bằng. Cho vay phân biệt đối xử, lãi suất cắt cổ và quảng cáo gây hiểu lầm đang được giám sát tích cực để đảm bảo rằng người tiêu dùng nhận được sự đối xử công bằng khi nộp đơn xin vay hoặc dịch vụ tín dụng.

Nhờ những chính sách này, người tiêu dùng có thể mong đợi các nguồn lực về kiến ​​thức tài chính được cải thiện, khả năng tiếp cận các dịch vụ ngân hàng tốt hơn và các biện pháp bảo vệ pháp lý mạnh mẽ hơn trong trường hợp xảy ra tranh chấp. Bằng cách ưu tiên tính minh bạch và công bằng, những thay đổi về quy định này nhằm mục đích thúc đẩy một môi trường ngân hàng toàn diện và đáng tin cậy hơn.',
3, 2, 'approved','news.png'),

(N'Những thay đổi về quy định và ý nghĩa của chúng',
N'Việc đưa ra các khuôn khổ quy định mới có ý nghĩa sâu sắc đối với ngành ngân hàng. Những thay đổi này được thiết kế để tăng cường trách nhiệm giải trình và giảm thiểu rủi ro liên quan đến hoạt động cho vay. Tuy nhiên, các ngân hàng hiện đang phải đối mặt với thách thức cân bằng giữa việc tuân thủ với việc duy trì lợi nhuận, điều này có thể ảnh hưởng đến các chiến lược tương lai của họ.

Một trong những mục tiêu chính của những thay đổi về quy định này là đảm bảo sự ổn định tài chính. Các tiêu chuẩn cho vay chặt chẽ hơn đang được thực hiện để giảm thiểu rủi ro tích tụ nợ xấu, vốn là mối quan tâm lớn của các tổ chức tài chính trong quá khứ. Các biện pháp mới này yêu cầu các ngân hàng phải tiến hành đánh giá tín dụng kỹ lưỡng hơn, đảm bảo rằng người vay có khả năng tài chính để trả nợ.

Ngoài ra, các quy định hiện đang nhấn mạnh tầm quan trọng của việc bảo vệ khách hàng. Các ngân hàng phải cung cấp thông tin rõ ràng hơn về các điều khoản cho vay, lãi suất và các hình phạt tiềm ẩn, cho phép người tiêu dùng đưa ra quyết định tài chính sáng suốt hơn. Sự thay đổi này dự kiến ​​sẽ tạo ra một môi trường tài chính công bằng và minh bạch hơn.

Tuy nhiên, với những quy định chặt chẽ hơn này cũng đi kèm những thách thức. Các ngân hàng có thể thấy khó khăn hơn trong việc phê duyệt các khoản vay, có khả năng làm chậm lại các khoản đầu tư kinh doanh và hoạt động vay tiêu dùng. Để chống lại điều này, một số tổ chức tài chính đang khám phá các phương pháp đánh giá tín dụng sáng tạo, chẳng hạn như đánh giá rủi ro do AI cung cấp và các mô hình cho vay thay thế, để hợp lý hóa việc phê duyệt khoản vay trong khi vẫn duy trì tuân thủ quy định.

Khi ngành tài chính thích ứng với những thay đổi này, điều quan trọng là cả ngân hàng và người tiêu dùng đều phải cập nhật thông tin. Đối thoại liên tục giữa các cơ quan quản lý và các bên liên quan trong ngành sẽ đóng vai trò quan trọng trong việc định hình một cách tiếp cận cân bằng đảm bảo sự ổn định tài chính đồng thời hỗ trợ tăng trưởng kinh tế.',
3, 2, 'draft', 'news.png'),

(N'Tương lai của ngành ngân hàng trong bối cảnh thay đổi chính sách',
N'Khi bối cảnh ngân hàng thay đổi do các chính sách mới, các tổ chức tài chính phải nhanh chóng thích ứng để duy trì khả năng cạnh tranh. Những đổi mới về công nghệ và dịch vụ khách hàng sẽ rất cần thiết để điều hướng những thay đổi này. Tương lai của ngành ngân hàng phụ thuộc vào mức độ các ngân hàng có thể tích hợp các quy định mới trong khi vẫn tiếp tục đáp ứng nhu cầu của người tiêu dùng.

Một trong những thay đổi lớn trong ngành ngân hàng là việc áp dụng các chiến lược chuyển đổi số. Với các quy định chặt chẽ hơn, các ngân hàng đang tận dụng công nghệ để cải thiện hiệu quả và khả năng tuân thủ. Các công cụ đánh giá rủi ro tự động, giao dịch dựa trên blockchain và dịch vụ khách hàng do AI điều khiển đang trở nên phổ biến hơn khi các tổ chức tài chính nỗ lực tuân thủ các tiêu chuẩn quản lý mới.

Hơn nữa, sự chuyển dịch sang các dịch vụ ngân hàng lấy khách hàng làm trung tâm là điều hiển nhiên. Người tiêu dùng đang yêu cầu tính minh bạch cao hơn, xử lý giao dịch nhanh hơn và các biện pháp bảo mật được cải thiện. Để ứng phó, các ngân hàng đang cải thiện nền tảng ngân hàng di động của mình, cung cấp các công cụ lập kế hoạch tài chính được cá nhân hóa và triển khai các giao thức an ninh mạng chặt chẽ hơn để bảo vệ dữ liệu người dùng.

Một khía cạnh quan trọng khác của tương lai ngành ngân hàng là sự gia tăng của các quan hệ đối tác công nghệ tài chính. Khi các quy định được thắt chặt, các ngân hàng truyền thống đang hợp tác với các công ty công nghệ tài chính để cung cấp các giải pháp tài chính sáng tạo. Sự hợp tác này cho phép các ngân hàng duy trì khả năng cạnh tranh trong khi vẫn đảm bảo tuân thủ các khuôn khổ quy định đang thay đổi.

Nhìn chung, tương lai của ngành ngân hàng sẽ được xác định bởi khả năng nắm bắt sự đổi mới trong khi vẫn tuân thủ các thay đổi về chính sách. Bằng cách chủ động thích ứng với các quy định mới và tích hợp các tiến bộ công nghệ, các ngân hàng có thể tạo ra một hệ sinh thái tài chính an toàn hơn, hiệu quả hơn và thân thiện hơn với khách hàng.',
3, 2, 'approved','news.png'),

(N'Phản hồi của công chúng về những thay đổi chính sách ngân hàng',
N'Cảm nhận của công chúng về những thay đổi chính sách ngân hàng gần đây khá trái chiều. Trong khi một số người tiêu dùng đánh giá cao tính minh bạch gia tăng, những người khác lại lo ngại về khả năng tăng phí và tiêu chí vay khắt khe hơn. Đối thoại liên tục giữa người tiêu dùng và các nhà hoạch định chính sách sẽ đóng vai trò quan trọng trong việc định hình tương lai của các hoạt động ngân hàng.

Một trong những khía cạnh quan trọng nhất của những thay đổi chính sách này là nhấn mạnh vào việc bảo vệ quyền của người tiêu dùng. Các quy định mới yêu cầu các ngân hàng phải công bố rõ ràng hơn về phí và lệ phí, đảm bảo rằng khách hàng hiểu đầy đủ các điều khoản trong thỏa thuận tài chính của họ. Sự thay đổi này nhằm mục đích thúc đẩy sự tin tưởng lớn hơn giữa các ngân hàng và khách hàng của họ.

Tuy nhiên, không phải tất cả phản hồi đều tích cực. Một số người tiêu dùng lo ngại rằng các biện pháp quản lý chặt chẽ hơn có thể dẫn đến chi phí tăng cao, vì các ngân hàng điều chỉnh cơ cấu phí của mình để bù đắp cho các yêu cầu tuân thủ chặt chẽ hơn. Đặc biệt, các doanh nghiệp nhỏ lo ngại rằng việc tiếp cận tín dụng có thể trở nên khó khăn hơn do các đánh giá cho vay khắt khe hơn.

Mặt khác, các chuyên gia tài chính lập luận rằng các biện pháp quản lý này cuối cùng sẽ dẫn đến một môi trường ngân hàng ổn định hơn. Bằng cách giảm thiểu rủi ro liên quan đến hoạt động cho vay và tăng cường tính minh bạch tài chính, ngành này có thể thấy được những lợi ích lâu dài, bao gồm sự tự tin lớn hơn của người tiêu dùng và giảm các trường hợp gian lận tài chính.

Khi các nhà hoạch định chính sách và các tổ chức tài chính tiếp tục tinh chỉnh các quy định này, sự tham gia của công chúng vẫn là điều cần thiết. Các cuộc thảo luận cởi mở và phản hồi của người tiêu dùng sẽ giúp đảm bảo rằng các chính sách ngân hàng tạo ra sự cân bằng giữa sự ổn định tài chính và khả năng tiếp cận cho tất cả mọi người.',
3, 2, 'approved','news.png'),

(N'Các khoản vay mới dành cho doanh nghiệp nhỏ',
N'Để hỗ trợ các doanh nghiệp nhỏ, một số ngân hàng đã triển khai các khoản vay mới với các điều khoản thuận lợi. Các khoản vay này được thiết kế để cung cấp cho các doanh nhân nguồn vốn cần thiết để phát triển hoạt động của họ. Sáng kiến ​​này được nhiều người trong cộng đồng doanh nghiệp hoan nghênh, họ coi đây là phao cứu sinh trong thời kỳ kinh tế khó khăn.

Với những biến động kinh tế gần đây, các doanh nghiệp nhỏ đã gặp khó khăn trong việc đảm bảo nguồn vốn. Nhận ra vấn đề này, các ngân hàng đã đưa ra các chương trình cho vay chuyên biệt với lãi suất thấp hơn, lịch trình trả nợ linh hoạt và yêu cầu thế chấp thấp hơn. Các biện pháp này nhằm mục đích khuyến khích tăng trưởng kinh doanh và duy trì nền kinh tế địa phương.

Nhiều sản phẩm cho vay mới này dành riêng cho các công ty khởi nghiệp và doanh nghiệp siêu nhỏ, những lĩnh vực thường gặp khó khăn trong việc huy động vốn. Một số ngân hàng thậm chí còn hợp tác với các cơ quan chính phủ và tổ chức tài chính để cung cấp thêm các khoản bảo lãnh và trợ cấp, giúp giảm bớt gánh nặng cho chủ doanh nghiệp.

Ngoài ra, các nền tảng cho vay kỹ thuật số đã nổi lên như một nhân tố chính trong việc tài trợ cho doanh nghiệp nhỏ. Nhiều ngân hàng hiện cung cấp các quy trình đăng ký trực tuyến hợp lý, cho phép các doanh nhân đăng ký vay với ít giấy tờ và phê duyệt nhanh hơn. Sự chuyển dịch này hướng tới khả năng tiếp cận kỹ thuật số dự kiến ​​sẽ tăng cường sự hòa nhập tài chính và thúc đẩy hoạt động kinh tế.

Khi các doanh nghiệp nhỏ tận dụng các cơ hội vay mới này, các chuyên gia tài chính khuyến khích người đi vay đánh giá cẩn thận nhu cầu và khả năng trả nợ của họ. Bằng cách đưa ra quyết định vay sáng suốt, các doanh nghiệp có thể tối đa hóa lợi ích của các sản phẩm tài chính này đồng thời đảm bảo tính bền vững lâu dài.',
3, 3, 'approved','news.png'),

(N'Hiểu về các lựa chọn cho vay cá nhân',
N'Để hỗ trợ các doanh nghiệp nhỏ, một số ngân hàng đã triển khai các dịch vụ cho vay mới với các điều khoản thuận lợi. Các khoản vay này được thiết kế để cung cấp cho các doanh nhân nguồn vốn cần thiết để phát triển hoạt động của họ. Sáng kiến ​​này được nhiều người trong cộng đồng doanh nghiệp hoan nghênh, họ coi đây là phao cứu sinh trong thời kỳ kinh tế khó khăn.

Với những biến động kinh tế gần đây, các doanh nghiệp nhỏ đã gặp khó khăn trong việc đảm bảo nguồn vốn. Nhận ra vấn đề này, các ngân hàng đã giới thiệu các chương trình cho vay chuyên biệt với lãi suất thấp hơn, lịch trình trả nợ linh hoạt và yêu cầu thế chấp thấp hơn. Các biện pháp này nhằm mục đích khuyến khích tăng trưởng kinh doanh và duy trì nền kinh tế địa phương.

Nhiều sản phẩm cho vay mới này dành riêng cho các công ty khởi nghiệp và doanh nghiệp siêu nhỏ, những lĩnh vực thường gặp khó khăn trong việc huy động vốn. Một số ngân hàng thậm chí còn hợp tác với các cơ quan chính phủ và tổ chức tài chính để cung cấp thêm các khoản bảo lãnh và trợ cấp, giúp giảm bớt gánh nặng cho chủ doanh nghiệp.

Ngoài ra, các nền tảng cho vay kỹ thuật số đã nổi lên như một nhân tố chủ chốt trong việc tài trợ cho doanh nghiệp nhỏ. Nhiều ngân hàng hiện cung cấp các quy trình đăng ký trực tuyến hợp lý, cho phép các doanh nhân đăng ký vay với ít giấy tờ và phê duyệt nhanh hơn. Sự chuyển dịch sang khả năng tiếp cận kỹ thuật số này dự kiến ​​sẽ tăng cường hòa nhập tài chính và thúc đẩy hoạt động kinh tế.

Khi các doanh nghiệp nhỏ tận dụng các cơ hội vay vốn mới này, các chuyên gia tài chính khuyến khích người đi vay đánh giá cẩn thận nhu cầu và khả năng trả nợ của họ. Bằng cách đưa ra quyết định vay vốn sáng suốt, các doanh nghiệp có thể tối đa hóa lợi ích của các sản phẩm tài chính này đồng thời đảm bảo tính bền vững lâu dài.',
3, 3, 'approved','news.png'),

(N'Sự trỗi dậy của các nền tảng cho vay trực tuyến',
N'Sự xuất hiện của các nền tảng cho vay trực tuyến đã làm thay đổi bối cảnh vay vốn. Các nền tảng này cung cấp quyền truy cập nhanh chóng và dễ dàng vào các khoản tiền, thường ít yêu cầu hơn so với các ngân hàng truyền thống. Tuy nhiên, những người đi vay tiềm năng phải thận trọng và nghiên cứu kỹ lưỡng các lựa chọn này để tránh các hoạt động cho vay bóc lột.

Cuộc cách mạng kỹ thuật số đã thay đổi đáng kể cách mọi người tiếp cận tín dụng, với các nền tảng cho vay trực tuyến ngày càng phổ biến do sự tiện lợi và tốc độ của chúng. Không giống như các ngân hàng truyền thống yêu cầu nhiều thủ tục giấy tờ và các cuộc gặp trực tiếp, nhiều bên cho vay trực tuyến cho phép người dùng đăng ký vay ngay tại nhà. Khả năng tiếp cận này đã khiến việc vay vốn trở nên toàn diện hơn, đặc biệt là đối với những cá nhân có thể không đủ điều kiện để vay vốn ngân hàng thông thường.

Một trong những lợi thế chính của các nền tảng cho vay trực tuyến là quy trình đăng ký hợp lý của họ. Nhiều nền tảng sử dụng các hệ thống tự động để đánh giá khả năng tín dụng của người đi vay, cung cấp các quyết định phê duyệt trước ngay lập tức. Ngoài ra, việc sử dụng các phương pháp chấm điểm tín dụng thay thế cho phép những cá nhân có lịch sử tín dụng hạn chế tiếp cận các cơ hội tài trợ mà nếu không thì họ có thể không có.

Tuy nhiên, sự gia tăng của hoạt động cho vay trực tuyến cũng gây ra những lo ngại về tính minh bạch và các hoạt động cho vay có đạo đức. Một số bên cho vay áp dụng lãi suất cắt cổ và phí ẩn, khiến người vay mắc kẹt trong vòng xoáy nợ nần. Để tự bảo vệ mình, người tiêu dùng nên xem xét kỹ lưỡng các điều khoản cho vay, so sánh nhiều bên cho vay và kiểm tra xem có giấy phép và tuân thủ quy định phù hợp hay không.

Bất chấp những thách thức này, các nền tảng cho vay trực tuyến vẫn tiếp tục phát triển, cung cấp cho người vay các giải pháp tài chính linh hoạt và sáng tạo. Khi công nghệ phát triển, ngành này dự kiến ​​sẽ giới thiệu nhiều lựa chọn cho vay an toàn và thân thiện với người tiêu dùng hơn nữa, định hình lại bối cảnh tài chính.',
3, 3, 'draft','news.png'),

(N'Ưu đãi cho vay và phục hồi kinh tế',
N'Khi nền kinh tế bắt đầu phục hồi, các ngân hàng trở nên lạc quan hơn về hoạt động cho vay. Các ưu đãi cho vay mới đang được đưa ra để kích thích tăng trưởng và hỗ trợ người tiêu dùng muốn mua sắm đáng kể. Sự thay đổi này có thể báo hiệu xu hướng tích cực cho cả bên cho vay và bên đi vay trong những tháng tới.

Suy thoái kinh tế gần đây đã buộc nhiều tổ chức tài chính phải thắt chặt chính sách cho vay của họ. Tuy nhiên, khi thị trường ổn định và niềm tin của người tiêu dùng tăng lên, các ngân hàng một lần nữa mở cửa cho người đi vay. Sự hồi sinh trong hoạt động cho vay này đặc biệt có lợi cho những cá nhân tìm kiếm các khoản thế chấp, vay mua ô tô và các lựa chọn tài chính cá nhân.

Một trong những động lực thúc đẩy đằng sau các ưu đãi cho vay mới này là nhu cầu phục hồi hoạt động kinh tế. Chính phủ và ngân hàng trung ương đã khuyến khích cho vay thông qua các biện pháp chính sách, chẳng hạn như giảm lãi suất và bơm thanh khoản vào hệ thống ngân hàng. Do đó, các tổ chức tài chính đang đưa ra các gói cho vay hấp dẫn với lãi suất thấp hơn và thời hạn trả nợ được kéo dài.

Đối với người đi vay, đây là cơ hội tuyệt vời để đảm bảo các khoản vay với các điều kiện thuận lợi. Tuy nhiên, điều quan trọng vẫn là phải đánh giá cẩn thận tình hình tài chính của mình trước khi vay nợ. Việc vay có trách nhiệm đảm bảo rằng các cá nhân có thể đáp ứng các nghĩa vụ trả nợ mà không gặp khó khăn về tài chính, cuối cùng góp phần vào sự ổn định kinh tế lâu dài.',
3, 3, 'approved','news.png'),

(N'So sánh lãi suất cho vay: Những điều bạn cần biết',
N'Khi cân nhắc một khoản vay, việc so sánh lãi suất từ ​​các bên cho vay khác nhau là điều cần thiết. Bài viết này cung cấp các mẹo về cách so sánh hiệu quả các ưu đãi cho vay, bao gồm hiểu về APR, phí và các tùy chọn hoàn trả. Kiến thức là sức mạnh khi đưa ra quyết định tài chính.

Lãi suất thay đổi rất nhiều giữa các bên cho vay, khiến người vay phải tiến hành nghiên cứu kỹ lưỡng trước khi cam kết vay. Tỷ lệ phần trăm hàng năm (APR) là một yếu tố quan trọng trong quá trình so sánh này, vì nó bao gồm cả lãi suất và các khoản phí bổ sung liên quan đến khoản vay. APR thấp hơn thường cho thấy khoản vay dễ chi trả hơn trong thời gian dài.

Ngoài lãi suất, người vay cũng nên đánh giá các điều khoản cho vay, bao gồm thời hạn hoàn trả, hình phạt trả trước và tính linh hoạt trong lịch trình thanh toán. Một số bên cho vay cung cấp lãi suất cố định, đảm bảo các khoản thanh toán hàng tháng ổn định, trong khi những bên khác cung cấp lãi suất thay đổi có thể dao động tùy theo điều kiện thị trường.

Ngoài ra, nên cân nhắc đến uy tín của bên cho vay và chất lượng dịch vụ khách hàng. Các đánh giá trực tuyến và hồ sơ tuân thủ quy định có thể cung cấp những hiểu biết có giá trị về độ tin cậy của bên cho vay. Người vay cũng nên cảnh giác với các khoản phí ẩn có thể không thấy rõ ngay trong các tài liệu quảng cáo.

Dành thời gian để so sánh các ưu đãi cho vay có thể giúp tiết kiệm đáng kể và cải thiện kết quả tài chính. Với kế hoạch cẩn thận và quyết định sáng suốt, người vay có thể đảm bảo nguồn tài chính phù hợp với mục tiêu tài chính của mình đồng thời tránh được gánh nặng nợ nần không cần thiết.',
3, 3, 'approved','news.png'),

(N'Các kế hoạch tiết kiệm tốt nhất cho năm 2025',
N'Khi chúng ta tiến gần đến năm 2025, các tổ chức tài chính đang triển khai các kế hoạch tiết kiệm mới nhằm giúp người tiêu dùng đạt được mục tiêu tài chính của mình. Các kế hoạch này thường đi kèm với lãi suất cạnh tranh và các điều khoản linh hoạt. Bài viết này đánh giá các lựa chọn tốt nhất hiện có cho các nhu cầu tiết kiệm khác nhau, từ quỹ khẩn cấp đến đầu tư dài hạn.

Với điều kiện kinh tế thay đổi, người tiêu dùng phải đánh giá cẩn thận các kế hoạch tiết kiệm để đảm bảo họ tối đa hóa lợi nhuận trong khi vẫn dễ dàng tiếp cận tiền khi cần. Các ngân hàng và hợp tác tín dụng đang giới thiệu các tài khoản tiết kiệm chuyên biệt, chẳng hạn như tiết kiệm lãi suất cao, tiền gửi có kỳ hạn cố định và các tùy chọn đầu tư-tiết kiệm kết hợp phù hợp với các mục tiêu tài chính đa dạng.

Đối với những người muốn tăng dần tài sản của mình, các tài khoản tiết kiệm lãi suất cao cung cấp lãi suất hấp dẫn trong khi vẫn cho phép thanh khoản. Trong khi đó, tiền gửi có kỳ hạn mang lại lợi nhuận cao hơn cho những người sẵn sàng khóa tiền của mình trong một thời gian cố định. Ngoài ra, các kế hoạch tiết kiệm tập trung vào hưu trí, chẳng hạn như IRA hoặc tài khoản liên kết lương hưu, vẫn đóng vai trò quan trọng đối với an ninh tài chính dài hạn.

Các chuyên gia khuyên nên đa dạng hóa các chiến lược tiết kiệm, đảm bảo một phần tiền vẫn dễ tiếp cận trong khi các khoản tiết kiệm khác góp phần vào tăng trưởng tài chính dài hạn. Khi sự cạnh tranh giữa các tổ chức tài chính gia tăng, việc so sánh các điều khoản, phí và ưu đãi sẽ là chìa khóa để lựa chọn kế hoạch tiết kiệm tốt nhất vào năm 2025.

',3, 4, 'approved','news.png'),

(N'Tầm quan trọng của việc tiết kiệm sớm',
N'Tiết kiệm sớm có thể mang lại lợi ích tài chính đáng kể về lâu dài. Bài viết này thảo luận về những lợi ích của việc bắt đầu một kế hoạch tiết kiệm càng sớm càng tốt, bao gồm lãi kép và an ninh tài chính. Chúng tôi cũng cung cấp các chiến lược để những người trẻ tuổi bắt đầu hành trình tiết kiệm của mình.

Một trong những nguyên tắc tài chính mạnh mẽ nhất là lãi kép, cho phép tiền tiết kiệm tăng theo cấp số nhân theo thời gian. Một cá nhân bắt đầu tiết kiệm càng sớm thì lợi nhuận tích lũy do tái đầu tư thu nhập lãi suất càng lớn. Ngay cả những đóng góp nhỏ được thực hiện một cách nhất quán cũng có thể dẫn đến lợi nhuận tài chính đáng kể trong nhiều năm.

Ngoài tăng trưởng tài chính, tiết kiệm sớm còn tạo ra thói quen tài chính có kỷ luật, thúc đẩy quản lý tiền có trách nhiệm và giảm sự phụ thuộc vào nợ nần. Bằng cách xây dựng quỹ khẩn cấp sớm, cá nhân có thể tự bảo vệ mình trước những chi phí bất ngờ và khó khăn về tài chính.

Để bắt đầu hành trình tiết kiệm của mình, những người trẻ tuổi có thể khám phá các chương trình tiết kiệm do người sử dụng lao động tài trợ, chuyển tiền tiết kiệm tự động và các lựa chọn đầu tư thân thiện với ngân sách. Kiến thức tài chính cũng đóng vai trò quan trọng, giúp cá nhân đưa ra quyết định sáng suốt về các sản phẩm tiết kiệm và lập kế hoạch tài chính dài hạn.

Bắt đầu sớm không đòi hỏi số tiền lớn—điều quan trọng là tính nhất quán. Việc hình thành thói quen tiết kiệm sớm sẽ đặt nền tảng cho sự độc lập về tài chính và tích lũy của cải lâu dài.',
3, 4, 'approved','news.png'),

(N'Cách chọn đúng kế hoạch tiết kiệm',
N'Với nhiều kế hoạch tiết kiệm hiện có, việc chọn đúng kế hoạch có thể rất khó khăn. Hướng dẫn này phân tích các yếu tố chính cần cân nhắc, chẳng hạn như lãi suất, phí tài khoản và hạn mức rút tiền. Bằng cách hiểu các yếu tố này, người tiêu dùng có thể chọn một kế hoạch phù hợp với mục tiêu tài chính của mình.

Một kế hoạch tiết kiệm có cấu trúc tốt là điều cần thiết cho sự ổn định tài chính ngắn hạn và dài hạn. Các tài khoản tiết kiệm lãi suất cao mang lại lợi nhuận tốt hơn, trong khi tiền gửi có kỳ hạn cố định có thể cung cấp lãi suất cao hơn cho những người sẵn sàng cam kết tiền của mình. Mặt khác, các tài khoản tiết kiệm linh hoạt cho phép rút tiền mà không bị phạt, khiến chúng trở nên lý tưởng cho các quỹ khẩn cấp.

Khi lựa chọn một kế hoạch, người tiêu dùng nên so sánh lợi nhuận phần trăm hàng năm (APY), yêu cầu số dư tối thiểu và bất kỳ khoản phí ẩn nào có thể làm giảm thu nhập của họ. Một số kế hoạch cũng cung cấp các lợi ích về thuế, đặc biệt là các kế hoạch tiết kiệm tập trung vào hưu trí, có thể giúp tối đa hóa tích lũy tài sản theo thời gian.

Các chuyên gia khuyên bạn nên đa dạng hóa tiền tiết kiệm trên nhiều tài khoản để cân bằng thanh khoản và lợi nhuận. Bằng cách cân nhắc các mục tiêu tài chính, khả năng chịu rủi ro và chi phí dự kiến, cá nhân có thể điều chỉnh chiến lược tiết kiệm đảm bảo an ninh tài chính dài hạn.',
3, 4, 'draft','news.png'),

(N'Kế hoạch tiết kiệm cho thời gian nghỉ hưu',
N'Lên kế hoạch cho thời gian nghỉ hưu là rất quan trọng và các kế hoạch tiết kiệm đóng vai trò quan trọng trong quá trình này. Bài viết này khám phá các lựa chọn tiết kiệm khác nhau được thiết kế riêng cho thời gian nghỉ hưu, bao gồm các kế hoạch IRA và 401(k). Chúng tôi nhấn mạnh tầm quan trọng của việc bắt đầu sớm để đảm bảo thời gian nghỉ hưu thoải mái.

Các kế hoạch tiết kiệm hưu trí cung cấp các lợi thế về thuế giúp cá nhân tăng trưởng tài sản một cách hiệu quả. Các kế hoạch IRA và 401(k) truyền thống cho phép đóng góp trước thuế, giảm thu nhập chịu thuế trong khi cho phép các khoản đầu tư tăng trưởng được hoãn thuế. Trong khi đó, Roth IRA cung cấp các khoản rút tiền miễn thuế khi nghỉ hưu, khiến chúng trở thành một lựa chọn chiến lược cho kế hoạch tài chính dài hạn.

Việc lựa chọn kế hoạch nghỉ hưu phù hợp phụ thuộc vào các yếu tố như đóng góp của chủ lao động, thu nhập hàng năm và lối sống khi nghỉ hưu dự kiến. Nhiều chủ lao động cung cấp các chương trình phù hợp với 401(k), giúp tăng hiệu quả tiền tiết kiệm mà không cần thêm nỗ lực từ nhân viên. Đối với những cá nhân tự kinh doanh, SEP IRA và các kế hoạch Solo 401(k) là những lựa chọn thay thế tuyệt vời với hạn mức đóng góp cao.

Việc thường xuyên xem xét và điều chỉnh tiền tiết kiệm hưu trí đảm bảo rằng các cá nhân vẫn đi đúng hướng với các mục tiêu tài chính của mình. Khi hoàn cảnh sống thay đổi, việc phân bổ lại tiền giữa các khoản đầu tư bảo thủ và tập trung vào tăng trưởng có thể giúp tối ưu hóa lợi nhuận dài hạn trong khi giảm thiểu rủi ro.',
3, 4, 'approved','news.png'),

(N'Mẹo để Tối đa hóa Tiết kiệm của Bạn',
N'Tối đa hóa tiết kiệm đòi hỏi tính kỷ luật và chiến lược. Bài viết này đưa ra những mẹo thực tế về cách tăng tỷ lệ tiết kiệm của bạn, bao gồm các kỹ thuật lập ngân sách và tự động hóa các khoản đóng góp tiết kiệm. Những thay đổi nhỏ có thể dẫn đến tăng trưởng tài chính đáng kể theo thời gian.

Một trong những cách đơn giản nhất để tích lũy tiền tiết kiệm là thông qua tự động hóa. Thiết lập khoản tiền gửi trực tiếp vào tài khoản tiết kiệm đảm bảo đóng góp nhất quán mà không bị cám dỗ chi tiêu. Các cố vấn tài chính cũng khuyến nghị tuân theo quy tắc lập ngân sách 50/30/20—phân bổ 50% thu nhập cho các nhu cầu thiết yếu, 30% cho chi tiêu tùy ý và 20% cho tiết kiệm và trả nợ.

Giảm các chi phí không cần thiết, chẳng hạn như ăn uống bên ngoài thường xuyên hoặc các dịch vụ đăng ký, có thể giải phóng thêm tiền để tiết kiệm. Ngoài ra, sử dụng phần thưởng hoàn tiền và các chương trình tiết kiệm do người sử dụng lao động tài trợ có thể giúp cá nhân tăng tiền tiết kiệm của mình với nỗ lực tối thiểu.

Đầu tư vào các tài khoản lợi nhuận cao hoặc các lựa chọn đầu tư rủi ro thấp sẽ nâng cao hơn nữa tiềm năng tiết kiệm. Việc thường xuyên xem xét các mục tiêu tài chính và điều chỉnh các chiến lược cho phù hợp sẽ đảm bảo tiến triển liên tục hướng tới sự độc lập về tài chính.',
3, 4, 'approved','news.png');


INSERT INTO term (term_name, duration, term_type, status)
VALUES 
(N'Hàng tháng', 1, 'monthly', 'active'),
(N'Hai tháng một lần', 2, 'monthly', 'active'),
(N'Hàng quý', 3, 'quarterly', 'active'),
(N'Nửa năm', 6, 'quarterly', 'active'),
(N'Hàng năm', 12, 'annually', 'active');


INSERT INTO services (service_name, description, service_type, status)
VALUES 
(N'Tiết kiệm', N'Dịch vụ giúp bạn tiết kiệm tiền một cách an toàn và hiệu quả.', 'saving', 'active'),
(N'Khoản vay', N'Dịch vụ cho vay tiền với các điều khoản linh hoạt.', 'loan', 'active'),
(N'Gửi tiết kiệm', N'Tài khoản gửi tiết kiệm có lãi suất cố định, giúp bạn gia tăng tài sản.', 'deposit', 'active'),
(N'Rút tiền', N'Dịch vụ cho phép bạn rút tiền từ tài khoản của mình một cách dễ dàng.', 'withdrawal', 'active');
INSERT INTO service_terms (term_id, service_id, term_name, description, contract_terms, early_payment_penalty, interest_rate, min_deposit, status)
VALUES 
-- Gửi tiết kiệm 1 tháng
((SELECT term_id FROM term WHERE duration = 1), 1, 
 N'Tiết kiệm cố định 1 tháng', 
 N'Khách hàng phải gửi tiền trong 1 tháng để nhận lãi suất. Đây là lựa chọn lý tưởng cho những ai muốn có một khoản tiết kiệm ngắn hạn với lãi suất ổn định.', 
 N'Yêu cầu số tiền gửi tối thiểu. Rút tiền sớm sẽ bị mất lãi suất đã hứa.', 
 10.00, 0, 1000000.00, 'active'),

-- Gửi tiết kiệm 2 tháng
((SELECT term_id FROM term WHERE duration = 2), 1, 
 N'Tiết kiệm cố định 2 tháng', 
 N'Khách hàng phải gửi tiền trong 2 tháng để nhận lãi suất. Lựa chọn này phù hợp cho những ai muốn tích lũy một khoản tiền nhỏ trong thời gian ngắn.', 
 N'Yêu cầu số tiền gửi tối thiểu. Rút tiền sớm sẽ bị mất lãi suất đã hứa.', 
 20.00, 0, 2000000.00, 'active'),

-- Gửi tiết kiệm 3 tháng
((SELECT term_id FROM term WHERE duration = 3), 1, 
 N'Tiết kiệm cố định 3 tháng', 
 N'Khách hàng phải gửi tiền trong 3 tháng để nhận lãi suất. Đây là lựa chọn tốt cho những ai muốn có lãi suất cao hơn một chút trong thời gian ngắn.', 
 N'Yêu cầu số tiền gửi tối thiểu. Rút tiền sớm sẽ bị mất lãi suất đã hứa.', 
 30.00, 0, 3000000.00, 'active'),

-- Gửi tiết kiệm 6 tháng
((SELECT term_id FROM term WHERE duration = 6), 1, 
 N'Tiết kiệm cố định 6 tháng', 
 N'Khách hàng phải gửi tiền trong 6 tháng để nhận lãi suất. Lựa chọn này rất phù hợp cho những người muốn đầu tư một khoản tiền trong thời gian dài hơn.', 
 N'Yêu cầu số tiền gửi tối thiểu. Rút tiền sớm sẽ bị mất lãi suất đã hứa.', 
 50.00, 0, 5000000.00, 'active'),

-- Gửi tiết kiệm 12 tháng
((SELECT term_id FROM term WHERE duration = 12), 1, 
 N'Tiết kiệm cố định 12 tháng', 
 N'Khách hàng phải gửi tiền trong 12 tháng để nhận lãi suất. Đây là lựa chọn tối ưu cho những ai muốn có lãi suất cao nhất trong thời gian dài.', 
 N'Yêu cầu số tiền gửi tối thiểu. Rút tiền sớm sẽ bị mất lãi suất đã hứa.', 
 100.00, 0, 10000000.00, 'active');
 INSERT INTO service_terms (term_id, service_id, term_name, description, contract_terms, early_payment_penalty, interest_rate, min_payment, status)
VALUES 
-- Vay 1 tháng
((SELECT term_id FROM term WHERE duration = 1), 2, 
 N'Khoản vay cá nhân 1 tháng', 
 N'Người vay phải hoàn trả trong 1 tháng.', 
 N'Yêu cầu thanh toán hàng tháng cố định. Thanh toán trễ sẽ bị phạt.', 
 15.00, 5.0, 5000000, 'active'),

-- Vay 2 tháng
((SELECT term_id FROM term WHERE duration = 2), 2, 
 N'Khoản vay cá nhân 2 tháng', 
 N'Người vay phải hoàn trả trong 2 tháng.', 
 N'Yêu cầu thanh toán hàng tháng cố định. Thanh toán trễ sẽ bị phạt.', 
 25.00, 5.2, 8000000, 'active'),

-- Vay 3 tháng
((SELECT term_id FROM term WHERE duration = 3), 2, 
 N'Khoản vay cá nhân 3 tháng', 
 N'Người vay phải hoàn trả trong 3 tháng.', 
 N'Yêu cầu thanh toán hàng tháng cố định. Thanh toán trễ sẽ bị phạt.', 
 40.00, 5.5, 10000000, 'active'),

-- Vay 6 tháng
((SELECT term_id FROM term WHERE duration = 6), 2, 
 N'Khoản vay cá nhân 6 tháng', 
 N'Người vay phải hoàn trả trong 6 tháng.', 
 N'Yêu cầu thanh toán hàng tháng cố định. Thanh toán trễ sẽ bị phạt.', 
 60.00, 6.0, 25000000, 'active'),

-- Vay 12 tháng
((SELECT term_id FROM term WHERE duration = 12), 2, 
 N'Khoản vay cá nhân 12 tháng', 
 N'Người vay phải hoàn trả trong 12 tháng.', 
 N'Yêu cầu thanh toán hàng tháng cố định. Thanh toán trễ sẽ bị phạt.', 
 120.00, 6.5, 50000000, 'active');


INSERT INTO feedback (customer_id, service_id, feedback_content, feedback_rate)
VALUES 
(1, 1, N'Dịch vụ tuyệt vời!', 5),
(2, 2, N'Có thể cải thiện hơn.', 4),
(3, 1, N'Rất hài lòng.', 4),
(4, 3, N'Không hài lòng với trải nghiệm.', 2),
(5, 4, N'Hỗ trợ xuất sắc!', 4);

INSERT INTO transactions (customer_id, service_id, amount, transaction_type)
VALUES 
(1, 1, 500000.00, 'deposit'),
(2, 2, 300000.00, 'withdrawal'),
(3, 1, 2000000.00, 'deposit'),
(4, 3, 1000000.00, 'withdrawal'),
(5, 4, 150000.00, 'deposit'),
(1, 1, 50000.00, 'deposit'),
(2, 2, 30000.00, 'withdrawal'),
(3, 1, 20000.00, 'deposit'),
(4, 3, 10000000.00, 'withdrawal'),
(5, 4, 15000.00, 'deposit'),
(1, 1, 500000.00, 'deposit'),
(2, 2, 3000.00, 'withdrawal'),
(3, 1, 20000.00, 'deposit'),
(4, 3, 10000.00, 'withdrawal'),
(5, 4, 15000.00, 'deposit'),
(1, 1, 50000.00, 'deposit'),
(2, 2, 3000.00, 'withdrawal'),
(3, 1, 20000.00, 'deposit'),
(4, 3, 100000.00, 'withdrawal'),
(5, 4, 15000.00, 'deposit'),
(1, 1, 50000.00, 'deposit'),
(2, 2, 3000.00, 'withdrawal'),
(3, 1, 2000.00, 'deposit'),
(4, 3, 100000.00, 'withdrawal'),
(5, 4, 1500000.00, 'deposit'),
(1, 1, 500000.00, 'deposit'),
(2, 2, 300000.00, 'withdrawal'),
(3, 1, 200000.00, 'deposit'),
(4, 3, 100000.00, 'withdrawal'),
(5, 4, 150000.00, 'deposit');



INSERT INTO insurance (username, password, insurance_name, email, phone_number, address, status,role_id)
VALUES 
('a', 'UJcZdUkab9YmW+57megoQi2mtU4=', N'Bảo Hiểm A', 'insure_a@example.com', '0123456784', N'Thành Phố Hà Nội, Việt Nam', 'active',5),
('b', 'UJcZdUkab9YmW+57megoQi2mtU4=', N'Bảo Hiểm B', 'insure_b@example.com', '0987654325', N'Thành Phố Hồ Chí Minh, Việt Nam', 'active',5),
('c', 'UJcZdUkab9YmW+57megoQi2mtU4=', N'Bảo Hiểm C', 'insure_c@example.com', '0123456785', N'Thành Phố Lạng Sơn, Việt Nam', 'active',5),
('d', 'UJcZdUkab9YmW+57megoQi2mtU4=', N'Bảo Hiểm D', 'insure_d@example.com', '0987654326', N'Thành Phố Hải Phòng, Việt Nam', 'active',5),
('e', 'UJcZdUkab9YmW+57megoQi2mtU4=', N'Bảo Hiểm E', 'insure_e@example.com', '0123456786', N'Thành Phố Nam Định, Việt Nam', 'active',5);

INSERT INTO insurance_policy (insurance_id, policy_name, description, coverage_amount, premium_amount, status, image)
VALUES 
-- Policies cho insurance_id = 1
(1, N'Bảo hiểm khoản vay cá nhân', 
 N'Chương trình bảo hiểm khoản vay cá nhân giúp khách hàng an tâm hơn khi vay tiền để mua sắm hoặc kinh doanh. Gói bảo hiểm này sẽ chi trả khoản vay trong trường hợp không mong muốn như mất khả năng lao động hoặc tử vong.',
 50000000, 2000000, 'active', NULL),
(1, N'Bảo hiểm khoản vay mua nhà', 
 N'Bảo hiểm này hỗ trợ khách hàng thanh toán khoản vay mua nhà trong trường hợp gặp sự cố tài chính hoặc tai nạn nghiêm trọng, giúp bảo vệ gia đình khỏi nguy cơ mất nhà.',
 150000000, 5000000, 'active', NULL),
(1, N'Bảo hiểm khoản vay kinh doanh', 
 N'Gói bảo hiểm này bảo vệ khoản vay kinh doanh trước những rủi ro như thiên tai, hỏa hoạn hoặc mất khả năng thanh toán, đảm bảo doanh nghiệp có thể tiếp tục hoạt động.',
 100000000, 4000000, 'inactive', NULL),
(1, N'Bảo hiểm khoản vay mua xe', 
 N'Bảo hiểm này hỗ trợ thanh toán khoản vay mua xe trong trường hợp chủ sở hữu gặp tai nạn nghiêm trọng hoặc mất khả năng lao động.',
 70000000, 3000000, 'active', NULL),
(1, N'Bảo hiểm khoản vay tiêu dùng', 
 N'Gói bảo hiểm giúp khách hàng thanh toán khoản vay tiêu dùng khi gặp sự cố sức khỏe hoặc tài chính, giảm áp lực trả nợ.',
 30000000, 1500000, 'inactive', NULL),

-- Policies cho insurance_id = 2
(2, N'Bảo hiểm khoản vay cá nhân', 
 N'Bảo hiểm giúp bảo vệ tài chính của khách hàng trong trường hợp không thể trả khoản vay cá nhân vì lý do sức khỏe hoặc tử vong.',
 55000000, 2200000, 'active', NULL),
(2, N'Bảo hiểm khoản vay mua nhà', 
 N'Chương trình bảo hiểm này giúp bảo vệ khoản vay mua nhà, hỗ trợ tài chính khi người vay mất khả năng lao động hoặc tử vong.',
 160000000, 5200000, 'inactive', NULL),
(2, N'Bảo hiểm khoản vay kinh doanh', 
 N'Bảo hiểm dành cho doanh nghiệp vay vốn, giúp bảo vệ trước những rủi ro về tài chính, thiên tai và kinh tế.',
 120000000, 4500000, 'active', NULL),
(2, N'Bảo hiểm khoản vay mua xe', 
 N'Bảo hiểm giúp chi trả khoản vay mua xe trong trường hợp không may như tai nạn hoặc mất khả năng lao động.',
 80000000, 3500000, 'active', NULL),
(2, N'Bảo hiểm khoản vay tiêu dùng', 
 N'Khách hàng có thể an tâm hơn khi vay tiền tiêu dùng với bảo hiểm này, hỗ trợ chi trả khi gặp khó khăn tài chính.',
 35000000, 1700000, 'inactive', NULL),

-- Policies cho insurance_id = 3
(3, N'Bảo hiểm khoản vay cá nhân', 
 N'Gói bảo hiểm này giúp khách hàng giảm bớt gánh nặng tài chính nếu không may gặp tai nạn hoặc bệnh hiểm nghèo.',
 60000000, 2500000, 'active', NULL),
(3, N'Bảo hiểm khoản vay mua nhà', 
 N'Bảo hiểm này giúp bảo vệ khoản vay mua nhà, đảm bảo gia đình không phải chịu áp lực tài chính nếu người vay mất khả năng chi trả.',
 170000000, 5500000, 'inactive', NULL),
(3, N'Bảo hiểm khoản vay kinh doanh', 
 N'Gói bảo hiểm giúp doanh nghiệp duy trì hoạt động trong những tình huống rủi ro bất ngờ, như thiên tai hoặc suy thoái kinh tế.',
 130000000, 4700000, 'active', NULL),
(3, N'Bảo hiểm khoản vay mua xe', 
 N'Bảo hiểm này bảo vệ chủ xe khỏi rủi ro mất khả năng thanh toán khoản vay mua xe do tai nạn hoặc biến cố khác.',
 90000000, 3800000, 'active', NULL),
(3, N'Bảo hiểm khoản vay tiêu dùng', 
 N'Giúp khách hàng yên tâm hơn khi vay tiêu dùng nhờ chính sách bảo hiểm hỗ trợ trả nợ trong trường hợp không mong muốn.',
 40000000, 1800000, 'inactive', NULL),

-- Policies cho insurance_id = 4
(4, N'Bảo hiểm khoản vay cá nhân', 
 N'Bảo hiểm hỗ trợ khách hàng trả nợ khoản vay cá nhân trong các trường hợp rủi ro như thất nghiệp, tai nạn hoặc bệnh tật.',
 65000000, 2800000, 'active', NULL),
(4, N'Bảo hiểm khoản vay mua nhà', 
 N'Chính sách bảo hiểm này giúp bảo vệ khoản vay mua nhà, hỗ trợ tài chính khi người vay gặp khó khăn trong việc trả nợ.',
 180000000, 5800000, 'inactive', NULL),
(4, N'Bảo hiểm khoản vay kinh doanh', 
 N'Gói bảo hiểm giúp giảm thiểu rủi ro tài chính cho các doanh nghiệp đang vay vốn để phát triển hoạt động kinh doanh.',
 140000000, 4900000, 'active', NULL),
(4, N'Bảo hiểm khoản vay mua xe', 
 N'Bảo hiểm dành cho người vay mua xe, hỗ trợ tài chính khi không thể tiếp tục trả góp do rủi ro ngoài ý muốn.',
 95000000, 4000000, 'active', NULL),
(4, N'Bảo hiểm khoản vay tiêu dùng', 
 N'Gói bảo hiểm này giúp người vay yên tâm hơn khi vay tiêu dùng, hỗ trợ tài chính trong trường hợp khẩn cấp.',
 45000000, 2000000, 'inactive', NULL),

-- Policies cho insurance_id = 5
(5, N'Bảo hiểm khoản vay cá nhân', 
 N'Bảo hiểm giúp bảo vệ khách hàng khi vay tiền cá nhân, hỗ trợ tài chính trong trường hợp gặp rủi ro không mong muốn.',
 70000000, 3000000, 'active', NULL),
(5, N'Bảo hiểm khoản vay mua nhà', 
 N'Bảo hiểm này giúp đảm bảo rằng khoản vay mua nhà vẫn được thanh toán ngay cả khi khách hàng gặp khó khăn tài chính.',
 190000000, 6000000, 'inactive', NULL),
(5, N'Bảo hiểm khoản vay kinh doanh', 
 N'Gói bảo hiểm dành cho doanh nghiệp, giúp giảm thiểu rủi ro tài chính khi gặp biến động kinh tế.',
 150000000, 5000000, 'active', NULL),
(5, N'Bảo hiểm khoản vay mua xe', 
 N'Bảo hiểm này giúp chủ xe an tâm hơn khi vay mua xe, hỗ trợ tài chính trong trường hợp không mong muốn.',
 100000000, 4200000, 'active', NULL),
(5, N'Bảo hiểm khoản vay tiêu dùng', 
 N'Bảo hiểm hỗ trợ thanh toán khoản vay tiêu dùng khi khách hàng gặp khó khăn tài chính, giảm bớt áp lực trả nợ.',
 50000000, 2200000, 'inactive', NULL);

INSERT INTO insurance_terms (insurance_id, policy_id, term_name, term_description, start_date, end_date, status)
SELECT 
    p.insurance_id,
    p.policy_id,
    term_data.term_name,
    term_data.term_description,
    DATEADD(DAY, ABS(CHECKSUM(NEWID())) % 365, GETDATE()), -- Ngày bắt đầu ngẫu nhiên từ hôm nay
    DATEADD(YEAR, 3, GETDATE()), -- Ngày kết thúc sau 3 năm
    CASE WHEN RAND(CHECKSUM(NEWID())) > 0.5 THEN 'active' ELSE 'inactive' END
FROM insurance_policy p
CROSS JOIN (
    SELECT 
        N'Điều kiện bảo hiểm' AS term_name, 
        N'Bảo hiểm chỉ có hiệu lực khi khách hàng đáp ứng đầy đủ điều kiện trong hợp đồng.' AS term_description
    UNION ALL
    SELECT 
        N'Phạm vi bảo hiểm', 
        N'Chính sách bảo hiểm sẽ chi trả toàn bộ hoặc một phần thiệt hại tài chính tùy theo mức bảo hiểm.'
    UNION ALL
    SELECT 
        N'Quyền lợi bảo hiểm', 
        N'Khách hàng được hưởng đầy đủ các quyền lợi bảo hiểm nếu tuân thủ đúng quy định hợp đồng.'
    UNION ALL
    SELECT 
        N'Loại trừ bảo hiểm', 
        N'Bảo hiểm không áp dụng trong trường hợp khách hàng vi phạm pháp luật hoặc cố ý gây thiệt hại.'
    UNION ALL
    SELECT 
        N'Trường hợp bồi thường', 
        N'Công ty bảo hiểm sẽ xem xét bồi thường dựa trên mức độ thiệt hại thực tế và hợp đồng.'
) AS term_data
WHERE p.policy_id IS NOT NULL;




--INSERT INTO loan (customer_id, serviceTerm_id, amount, start_date, end_date, image, notes, status, loan_type, value_asset, payment_type) 
--VALUES 
--(1, 10, 5000000.00, GETDATE(), '2025-09-09', 'image1.jpg', N'Vay mua xe', 'approved', 'secured', 10000000.00, 'reducing_balance'),

--(1, 8, 2000000.00, GETDATE(), '2026-12-09', 'image2.jpg', N'Vay du lịch', 'pending', 'unsecured', 0.00, 'fixed_principal'),

--(1, 6, 10000000.00, GETDATE(), '2025-04-09', 'image3.jpg', N'Vay mua nhà', 'approved', 'secured', 20000000.00, 'reducing_balance');

INSERT INTO serviceprovider (role_id, Name, username, password, ServiceType, email, phone_number, address, status)  
VALUES  
(7, N'EVN HCMC', 'a', 'UJcZdUkab9YmW+57megoQi2mtU4=', 'Electricity', 'contact@evnhcmc.vn', '0909000111', N'Ho Chi Minh City', 'active'),  

(7, N'SAWACO', 'b', 'UJcZdUkab9YmW+57megoQi2mtU4=', 'Water', 'support@sawaco.com', '0909123456', N'District 1, Ho Chi Minh City', 'active'),  

(7, N'VNPT Internet', 'c', 'UJcZdUkab9YmW+57megoQi2mtU4=', 'Internet', 'vnpt_support@vnpt.vn', '0911222333', N'Hanoi', 'active'),  

(7, N'FPT Telecom', 'd', 'UJcZdUkab9YmW+57megoQi2mtU4=1', 'Internet', 'hotro@fpt.vn', '0988111222', N'Da Nang', 'inactive'),  

(7, N'THU DUC Water', 'e', 'UJcZdUkab9YmW+57megoQi2mtU4=', 'Water', 'contact@thuducwater.vn', '0977888999', N'Thu Duc, Ho Chi Minh City', 'active');  
INSERT INTO vip_term (term_id, term_name, description, contract_terms, vip_type, price, loan_rate, savings_rate, status, created_at)
VALUES
-- Thành viên Bạc
((SELECT term_id FROM term WHERE duration = 1), N'Thành viên Bạc 1 Tháng', N'Thành viên Bạc cơ bản trong 1 tháng', N'Quyền truy cập vào dịch vụ ngân hàng tiêu chuẩn với một số lợi ích nhỏ.', 'silver', 100.00, 1.5, 2.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), N'Thành viên Bạc 6 Tháng', N'Thành viên Bạc cơ bản trong 6 tháng', N'Lợi ích mở rộng trong nửa năm.', 'silver', 500.00, 2.0, 2.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), N'Thành viên Bạc 12 Tháng', N'Nhà thành viên Bạc cơ bản trong 12 tháng', N'Đăng ký hàng năm với các dịch vụ tài chính tốt hơn.', 'silver', 900.00, 2.5, 3.0, 'active', GETDATE()),

-- Thành viên Vàng
((SELECT term_id FROM term WHERE duration = 1), N'Thành viên Vàng 1 Tháng', N'Thành viên Vàng với các ưu đãi bổ sung trong 1 tháng', N'Dịch vụ khách hàng ưu tiên và lãi suất vay/tiết kiệm tốt hơn.', 'gold', 200.00, 2.0, 3.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), N'Thành viên Vàng 6 Tháng', N'Thành viên Vàng với các ưu đãi bổ sung trong 6 tháng', N'Nhiều lợi ích hơn trong thời gian dài hơn.', 'gold', 1000.00, 2.5, 3.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), N'Thành viên Vàng 12 Tháng', N'Thành viên Vàng với các ưu đãi bổ sung trong 12 tháng', N'Gói giá trị tốt nhất với hỗ trợ ngân hàng xuất sắc.', 'gold', 1800.00, 3.0, 4.0, 'active', GETDATE()),

-- Thành viên Kim cương
((SELECT term_id FROM term WHERE duration = 1), N'Thành viên Kim cương 1 Tháng', N'Thành viên Kim cương độc quyền trong 1 tháng', N'Dịch vụ ngân hàng cao cấp với lợi ích cao nhất.', 'diamond', 500.00, 3.0, 4.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), N'Thành viên Kim cương 6 Tháng', N'Thành viên Kim cương độc quyền trong 6 tháng', N'Nhiều quyền truy cập hơn vào các cố vấn tài chính và dịch vụ.', 'diamond', 2500.00, 3.5, 4.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), N'Thành viên Kim cương 12 Tháng', N'Thành viên Kim cương độc quyền trong 12 tháng', N'Dịch vụ tài chính hàng đầu với lợi ích tối đa.', 'diamond', 4500.00, 4.0, 5.0, 'active', GETDATE());

INSERT INTO vip_term (term_id, term_name, description, contract_terms, vip_type, price, loan_rate, savings_rate, status, created_at)
VALUES
-- Thành viên Bạc
((SELECT term_id FROM term WHERE duration = 1), N'Thành viên Bạc 1 Tháng', N'Thành viên Bạc cơ bản trong 1 tháng', N'Quyền truy cập vào dịch vụ ngân hàng tiêu chuẩn với một số lợi ích nhỏ.', 'silver', 100.00, 1.5, 2.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), N'Thành viên Bạc 6 Tháng', N'Thành viên Bạc cơ bản trong 6 tháng', N'Lợi ích mở rộng trong nửa năm.', 'silver', 500.00, 2.0, 2.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), N'Thành viên Bạc 12 Tháng', N'Nhà thành viên Bạc cơ bản trong 12 tháng', N'Đăng ký hàng năm với các dịch vụ tài chính tốt hơn.', 'silver', 900.00, 2.5, 3.0, 'active', GETDATE()),

-- Thành viên Vàng
((SELECT term_id FROM term WHERE duration = 1), N'Thành viên Vàng 1 Tháng', N'Thành viên Vàng với các ưu đãi bổ sung trong 1 tháng', N'Dịch vụ khách hàng ưu tiên và lãi suất vay/tiết kiệm tốt hơn.', 'gold', 200.00, 2.0, 3.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), N'Thành viên Vàng 6 Tháng', N'Thành viên Vàng với các ưu đãi bổ sung trong 6 tháng', N'Nhiều lợi ích hơn trong thời gian dài hơn.', 'gold', 1000.00, 2.5, 3.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), N'Thành viên Vàng 12 Tháng', N'Thành viên Vàng với các ưu đãi bổ sung trong 12 tháng', N'Gói giá trị tốt nhất với hỗ trợ ngân hàng xuất sắc.', 'gold', 1800.00, 3.0, 4.0, 'active', GETDATE()),

-- Thành viên Kim cương
((SELECT term_id FROM term WHERE duration = 1), N'Thành viên Kim cương 1 Tháng', N'Thành viên Kim cương độc quyền trong 1 tháng', N'Dịch vụ ngân hàng cao cấp với lợi ích cao nhất.', 'diamond', 500.00, 3.0, 4.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), N'Thành viên Kim cương 6 Tháng', N'Thành viên Kim cương độc quyền trong 6 tháng', N'Nhiều quyền truy cập hơn vào các cố vấn tài chính và dịch vụ.', 'diamond', 2500.00, 3.5, 4.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), N'Thành viên Kim cương 12 Tháng', N'Thành viên Kim cương độc quyền trong 12 tháng', N'Dịch vụ tài chính hàng đầu với lợi ích tối đa.', 'diamond', 4500.00, 4.0, 5.0, 'active', GETDATE());
 
select * from customer
select * from savings
select * from loan
select * from services
select * from transactions
select * from service_terms
--select * from staff 
select * from role
select * from insurance
select * from insurance_policy
select * from insurance_contract_detail
select * from insurance_transactions
select * from staff 
select * from news
select * from transactions
select * from feedback
select * from loan
select * from loan_payments   
select * from customer

select * from term 
select * from service_terms
select * from services
select * from loan
select * from vip
select * from vip_term
select * from serviceprovider
select * from notifications
select * from insurance
join insurance_policy on insurance.insurance_id = insurance_policy.insurance_id
where insurance.username = 'a' and insurance.password = 'UJcZdUkab9YmW+57megoQi2mtU4='

--delete from news where staff_id= 2;
--delete from request where staff_id=2;
--delete from staff where staff_id= 2;

SELECT COUNT(customer_id) AS total_customers FROM customer; -- tong so khach hang
SELECT COUNT(staff_id) AS total_staff FROM staff; -- tong so nhan vien
SELECT COUNT(insurance_id) AS total_insurance_accounts FROM insurance; -- tong so tai khoan bao hiem 
SELECT COUNT(customer_id) AS active_customers FROM customer WHERE status = 'active'; -- so khach hang dang hoat dong
SELECT COUNT(staff_id) AS active_staff FROM staff WHERE status = 'active'; -- so  nhan vien dang hoat dong
SELECT COUNT(service_id) as  active_service from services where status = 'active'; -- so dich vu dang hoat dong
SELECT gender, COUNT(customer_id) AS total FROM customer GROUP BY gender; -- phan bo khach hang theo gioi tinh 
SELECT card_type, COUNT(customer_id) AS total FROM customer GROUP BY card_type; -- so tai khoan theo loai the ( credit/debit)
SELECT COUNT(feedback_id) AS total_feedbacks -- so phan hoi tu khach hang
FROM feedback;

-- so luong khach hang moi dang ky theo tung thang /nam 
-- so luong khach hang su dung tung loai dich vu
-- Tổng số bài viết (news) được nhân viên tạo ra.


-- UJcZdUkab9YmW+57megoQi2mtU4=

--	Lãi suất mỗi kỳ = interest_rate / 12
--	Lãi kỳ đầu = amount * (interest_rate / 12)
--	Tiền gốc trả mỗi kỳ = amount / số kỳ 
--	Tiền còn lại = amount - tiền gốc trả mỗi kỳ 

select * from loan
select * from loan_payments

SELECT lp.loan_payments_id, lp.loan_id, l.customer_id, c.email, lp.payment_amount, lp.payment_date  
                       FROM loan_payments lp 
                       JOIN loan l ON lp.loan_id = l.loan_id 
                       JOIN customer c ON l.customer_id = c.customer_id 
                       WHERE lp.payment_date = CAST(GETDATE() AS DATE) AND lp.payment_status = 'pending'
				   

