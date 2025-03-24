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
    customer_id INT NOT NULL, -- ID khách hàng
	service_id INT NOT NULL, -- ID dich vu
    policy_id INT NOT NULL, -- ID loại bảo hiểm
    start_date DATE NOT NULL, -- Ngày bắt đầu hiệu lực
    end_date DATE NOT NULL, -- Ngày kết thúc hiệu lực
	duration INT NOT NULL,
    payment_frequency NVARCHAR(50) CHECK (payment_frequency IN ('monthly', 'quarterly', 'annually')) 
	NOT NULL, -- Tần suất thanh toán : hàng tháng , hàng quý , hàng năm 
    status NVARCHAR(20) CHECK (status IN ('active', 'expired', 'cancelled')) DEFAULT 'active', -- Trạng thái
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo hợp đồng
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (service_id) REFERENCES services(service_id),
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
('Nguyen Van A', 'thinhndhe186687@fpt.edu.vn', 'a', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456789', '123 Street, City', 'credit', 0.00, 5000.00, 'active', 'male', '1990-01-01', 'profile_a.jpg',6),
('Tran Thi B', 'b@example.com', 'b', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0987654321', '456 Avenue, City', 'debit', 2000.00, 0.00, 'active', 'female', '1992-02-02', 'profile_b.jpg',6),
('Le Van C', 'c@example.com', 'c', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456780', '789 Boulevard, City', 'credit', 0.00, 3000.00, 'active', 'male', '1985-03-03', 'profile_c.jpg',6),
('Pham Thi D', 'd@example.com', 'd', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0987654312', '321 Road, City', 'debit', 2500.00, 0.00, 'active', 'female', '1995-04-04', 'profile_d.jpg',6),
('Vo Van E', 'e@example.com', 'e', 'UJcZdUkab9YmW+57megoQi2mtU4=', '0123456790', '654 Lane, City', 'credit',0.00, 8000.00, 'active', 'male', '1988-05-05', 'profile_e.jpg',6);


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
('Interest Rates News'),
('Banking Policies'),
('Loan Offers'),
('Savings Plans');

INSERT INTO news (title, content, staff_id, category_id, status,picture) VALUES 
('Interest Rate Trends: What to Expect in 2025', 
 'As we approach 2025, interest rates are expected to fluctuate due to various economic factors, including inflation, central bank policies, and global financial trends. Over the past few years, interest rates have been influenced by post-pandemic economic recovery efforts, changes in employment rates, and shifts in consumer spending habits. 
 Financial analysts predict that 2025 will be a pivotal year, with potential rate adjustments that could impact both savers and borrowers.
For individuals with savings accounts, rising interest rates may offer an opportunity to earn higher returns on their deposits. On the other hand, for those seeking loans, whether for personal or business purposes, increased rates could mean higher borrowing costs. Central banks around the world are closely monitoring economic indicators to determine the best course of action.
In addition, geopolitical tensions, trade agreements, and technological advancements in the financial sector will play crucial roles in shaping interest rate trends. Some experts believe that digital banking and fintech innovations will introduce new financial products that offer competitive interest rates, challenging traditional banking institutions.
Homebuyers and mortgage holders should also pay attention to interest rate trends, as fluctuations could affect monthly payments and refinancing opportunities. Fixed-rate mortgages may provide stability, while variable-rate loans could become riskier in a rising rate environment.
As consumers and businesses navigate these changes, financial literacy and proactive planning will be essential. Understanding market trends and seeking expert advice can help individuals and organizations make informed financial decisions. Whether you are saving, investing, or borrowing, staying updated on interest rate trends in 2025 will be key to optimizing your financial strategy.', 
 3, 1, 'approved','news.png'),

('Understanding the Impact of Interest Rate Changes', 
 'Interest rates play a crucial role in the economy, influencing everything from consumer spending to business investments. When interest rates change, the effects ripple through various sectors, impacting both individuals and corporations. But how exactly do these fluctuations shape financial decisions?

When interest rates rise, borrowing costs increase, making it more expensive for consumers and businesses to take out loans. This can slow down economic growth as companies may delay expansion plans and individuals may reconsider major purchases like homes or cars. Higher rates can also lead to increased credit card and mortgage payments, reducing disposable income and potentially lowering consumer spending.

Conversely, when interest rates drop, borrowing becomes more affordable, encouraging both businesses and individuals to take on new loans. This can stimulate economic activity, leading to growth in sectors such as real estate, automobile sales, and entrepreneurship. Lower rates can also ease the financial burden on those with existing variable-rate loans, freeing up funds for savings or investments.

However, changes in interest rates do not only affect borrowers. Savers and investors must also adapt to shifting financial landscapes. When rates increase, savings accounts and fixed-income investments such as bonds may offer higher returns, making them more attractive to cautious investors. On the other hand, when rates decline, returns on savings and bonds tend to decrease, pushing investors toward riskier assets like stocks or real estate in search of better gains.

Central banks, such as the Federal Reserve or the European Central Bank, play a key role in setting interest rates. They adjust rates based on economic conditions, aiming to control inflation, encourage employment, and maintain financial stability. Policymakers analyze factors such as GDP growth, employment levels, and inflation trends before making rate decisions.

For consumers and businesses alike, understanding how interest rate changes impact the economy can help in making informed financial decisions. Whether you are considering a loan, planning your investments, or managing debt, staying updated on interest rate trends is essential for financial success.', 
 3, 1, 'approved','news.png'),

('Central Bank Interest Rate Decisions Explained', 
 'Central banks play a critical role in shaping the economy through monetary policy, with interest rate decisions being one of their most powerful tools. These decisions influence inflation, employment, and overall financial stability. But how do central banks determine interest rates, and what are the implications of their choices?

The primary goal of central banks, such as the Federal Reserve (Fed), the European Central Bank (ECB), or the Bank of Japan (BoJ), is to maintain economic stability by controlling inflation and fostering economic growth. When inflation rises too quickly, central banks may increase interest rates to slow down excessive borrowing and spending. Conversely, when the economy is sluggish, lowering interest rates can encourage investment and consumer spending.

Interest rate decisions are based on various economic indicators, including:

Inflation Rate – A high inflation rate often prompts central banks to raise interest rates to reduce money supply and curb rising prices.
Employment Data – Low unemployment rates may indicate a strong economy, while high unemployment could push central banks to lower rates to stimulate job creation.
GDP Growth – A growing economy can withstand higher interest rates, whereas a contracting economy may require lower rates to encourage investment.
Global Economic Trends – Interest rates are also influenced by international trade, geopolitical tensions, and economic trends in major economies like the U.S. or China.
When a central bank raises interest rates, borrowing costs increase for individuals and businesses, making loans and mortgages more expensive. This can slow down economic activity but helps control inflation. Conversely, when rates are lowered, borrowing becomes cheaper, stimulating business growth and consumer spending.

In recent years, interest rate policies have been a topic of debate. Some argue that keeping rates too low for too long leads to excessive risk-taking in financial markets, while others believe aggressive rate hikes can stifle economic growth. Finding the right balance is a challenge that central banks continuously face.

Understanding how central banks make these decisions can help consumers and businesses make informed financial choices. Whether you are planning to buy a house, start a business, or invest in the stock market, staying aware of interest rate trends can give you an edge in navigating the financial landscape.', 
 3, 1, 'approved','news.png'),

('Savings Accounts and Interest Rates: A Comprehensive Guide', 
 'With changing interest rates, it’s essential to understand how they affect savings accounts. Interest rates play a significant role in determining how much return you earn on your deposits. As financial institutions adjust their rates based on economic conditions, consumers must stay informed about the best savings options available.

One of the key factors in choosing a savings account is understanding the difference between fixed and variable interest rates. Fixed rates offer stability, allowing savers to predict their earnings over time. However, variable rates fluctuate, meaning returns can increase or decrease depending on market conditions.

Additionally, financial institutions offer different types of savings accounts, such as high-yield savings accounts, money market accounts, and certificates of deposit (CDs). Each of these options comes with its own benefits and drawbacks. For instance, high-yield accounts generally offer better returns than standard savings accounts but may have more restrictions on withdrawals. CDs, on the other hand, provide higher interest rates but require you to lock in your funds for a fixed period.

Another critical factor to consider is inflation. While a high-interest savings account may seem attractive, inflation can erode the real value of your earnings. This is why it’s essential to balance interest rates with other factors, such as fees and accessibility, to ensure the best financial decision for your needs.

To maximize your savings, consider automating your deposits and taking advantage of promotional interest rates offered by banks. By staying informed about current trends and understanding the nuances of different savings options, you can ensure that your money is working for you effectively.', 
 3, 1, 'approved','news.png'),

('The Future of Loans and Interest Rates', 
 'As interest rates evolve, so do the options available for consumers seeking loans. Whether you’re planning to buy a home, start a business, or finance a major purchase, understanding future trends in interest rates is essential.

Economists predict that interest rates in 2025 will be influenced by factors such as inflation, central bank policies, and global economic conditions. A rising interest rate environment can make borrowing more expensive, increasing monthly payments for mortgages, personal loans, and business loans. On the other hand, if rates decrease, borrowers may find more favorable terms and lower repayment costs.

For homebuyers, mortgage rates play a crucial role in determining affordability. Those considering purchasing a home may need to evaluate whether locking in a fixed-rate mortgage is a better option than opting for an adjustable-rate mortgage, which fluctuates based on market conditions.

Business owners should also keep an eye on interest rate trends, as they can impact financing options for expansion and operational costs. Higher borrowing costs could lead to reduced investments, while lower rates might encourage business growth.

The role of fintech companies in the lending industry is expected to expand, providing consumers with alternative borrowing options beyond traditional banks. Online loan platforms, peer-to-peer lending, and decentralized finance (DeFi) solutions are becoming more prominent, offering competitive interest rates and streamlined approval processes.

As interest rates continue to fluctuate, borrowers should carefully assess loan terms, compare lenders, and consider refinancing opportunities when rates are favorable. Staying informed about economic trends will help individuals and businesses make well-informed financial decisions in the years ahead.', 
 3, 1, 'approved','news.png'),

('Impact of New Banking Policies', 
'The recent changes in banking policies have sparked a significant debate among industry experts. Many believe that these policies will lead to a more stable financial environment, while others express concern about their potential impact on small businesses. As banks adapt to these regulations, it is crucial to monitor their effects on lending practices and consumer access to credit.

One of the primary goals of the new policies is to enhance transparency in banking operations. Regulatory authorities have introduced stricter guidelines on loan approvals, interest rate disclosures, and consumer rights protection. This shift aims to build trust between financial institutions and their customers by ensuring that banking processes are fair and accountable.

However, these policy changes also present challenges. Small businesses, which often rely on accessible financing, may face stricter lending requirements, making it harder for them to secure loans. In response, some financial institutions are exploring alternative funding models, such as microfinance programs and government-backed loan initiatives, to support small business growth.

Additionally, digital banking and fintech solutions are playing an increasingly significant role in the financial landscape. As regulatory changes take effect, banks are investing in technology-driven solutions to streamline compliance processes while enhancing customer service. Mobile banking, AI-powered financial advising, and blockchain-based security measures are some of the innovations shaping the future of banking under the new policies.

Ultimately, the long-term impact of these regulatory changes will depend on how well banks, businesses, and consumers adapt to the evolving financial environment. Continuous evaluation and dialogue between policymakers and industry stakeholders will be essential in shaping a banking system that balances stability, growth, and accessibility.', 
3, 2, 'approved','news.png'),

('Banking Policies and Consumer Protection', 
'Consumer protection has become a focal point in the latest banking policies. With new measures aimed at safeguarding customer interests, banks are now required to be more transparent about fees and charges. This shift is expected to enhance trust between consumers and financial institutions, ultimately benefiting the economy as a whole.

One of the most notable changes involves the regulation of hidden fees. Banks are now mandated to clearly disclose all charges associated with their services, from overdraft fees to ATM withdrawal costs. This initiative empowers consumers to make informed financial decisions and prevents unexpected charges from negatively impacting their finances.

Another significant development is the enhancement of cybersecurity protocols. With the rise of digital banking, regulators are imposing stricter security requirements to protect consumers from fraud and identity theft. Banks must now implement advanced authentication methods, such as biometric verification and encrypted transactions, to safeguard customer data.

Additionally, financial institutions are being held accountable for fair lending practices. Discriminatory lending, predatory interest rates, and misleading advertising are being actively monitored to ensure that consumers receive equitable treatment when applying for loans or credit services.

As a result of these policies, consumers can expect improved financial literacy resources, greater accessibility to banking services, and stronger legal protections in the event of disputes. By prioritizing transparency and fairness, these regulatory changes aim to foster a more inclusive and trustworthy banking environment.', 
3, 2, 'approved','news.png'),

('Regulatory Changes and Their Implications', 
'The introduction of new regulatory frameworks has profound implications for the banking sector. These changes are designed to increase accountability and reduce risks associated with lending. However, banks are now facing the challenge of balancing compliance with maintaining profitability, which could affect their future strategies.

One of the primary goals of these regulatory changes is to ensure financial stability. Stricter lending standards are being implemented to reduce the risk of bad debt accumulation, which has been a major concern for financial institutions in the past. These new measures require banks to conduct more thorough credit assessments, ensuring that borrowers have the financial capacity to repay loans.

In addition, regulations are now emphasizing the importance of customer protection. Banks must provide clearer disclosures about loan terms, interest rates, and potential penalties, allowing consumers to make more informed financial decisions. This shift is expected to create a fairer and more transparent financial environment.

However, with these stricter rules come challenges. Banks may find it more difficult to approve loans, potentially slowing down business investments and consumer borrowing. To counteract this, some financial institutions are exploring innovative credit evaluation methods, such as AI-powered risk assessments and alternative lending models, to streamline loan approvals while maintaining regulatory compliance.

As the financial industry adapts to these changes, it is crucial for both banks and consumers to stay informed. Continuous dialogue between regulators and industry stakeholders will play a key role in shaping a balanced approach that ensures financial stability while supporting economic growth.', 
3, 2, 'draft','news.png'),

('Future of Banking Amidst Policy Changes', 
'As the banking landscape evolves due to new policies, financial institutions must adapt quickly to remain competitive. Innovations in technology and customer service will be essential in navigating these changes. The future of banking depends on how well banks can integrate new regulations while continuing to meet consumer needs.

One of the major shifts in the banking industry is the adoption of digital transformation strategies. With stricter regulations in place, banks are leveraging technology to improve efficiency and compliance. Automated risk assessment tools, blockchain-based transactions, and AI-driven customer service are becoming more prevalent as financial institutions strive to align with new regulatory standards.

Furthermore, the shift towards more customer-centric banking services is evident. Consumers are demanding greater transparency, faster transaction processing, and improved security measures. In response, banks are enhancing their mobile banking platforms, offering personalized financial planning tools, and implementing stricter cybersecurity protocols to protect user data.

Another key aspect of the future of banking is the rise of fintech partnerships. As regulations tighten, traditional banks are collaborating with fintech companies to provide innovative financial solutions. This synergy allows banks to remain competitive while ensuring compliance with evolving regulatory frameworks.

Overall, the future of banking will be defined by its ability to embrace innovation while adhering to policy changes. By proactively adapting to new regulations and integrating technological advancements, banks can create a more secure, efficient, and customer-friendly financial ecosystem.', 
3, 2, 'approved','news.png'),

('Public Response to Banking Policy Changes', 
'Public sentiment regarding the recent banking policy changes has been mixed. While some consumers appreciate the increased transparency, others are concerned about the potential for higher fees and stricter borrowing criteria. Ongoing dialogue between consumers and policymakers will be crucial in shaping the future of banking practices.

One of the most significant aspects of these policy changes is the emphasis on protecting consumer rights. New regulations require banks to disclose fees and charges more clearly, ensuring that customers fully understand the terms of their financial agreements. This shift is intended to foster greater trust between banks and their clients.

However, not all feedback has been positive. Some consumers worry that heightened regulatory measures may lead to increased costs, as banks adjust their fee structures to compensate for stricter compliance requirements. Small businesses, in particular, fear that accessing credit may become more challenging due to more rigorous lending assessments.

On the other hand, financial experts argue that these regulatory measures will ultimately lead to a more stable banking environment. By reducing risks associated with lending and enhancing financial transparency, the industry may see long-term benefits, including greater consumer confidence and reduced instances of financial fraud.

As policymakers and financial institutions continue to refine these regulations, public engagement remains essential. Open discussions and consumer feedback will help ensure that banking policies strike a balance between financial stability and accessibility for all.', 
3, 2, 'approved','news.png'),

('New Loan Offers for Small Businesses', 
'In a bid to support small businesses, several banks have rolled out new loan offerings with favorable terms. These loans are designed to provide entrepreneurs with the capital needed to grow their operations. The initiative has been welcomed by many in the business community, who see it as a lifeline during challenging economic times.

With the recent economic fluctuations, small businesses have faced difficulties in securing funding. Recognizing this issue, banks have introduced specialized loan programs that offer lower interest rates, flexible repayment schedules, and reduced collateral requirements. These measures aim to encourage business growth and sustain local economies.

Many of these new loan products cater specifically to startups and micro-enterprises, sectors that traditionally struggle to obtain financing. Some banks are even partnering with government agencies and financial institutions to provide additional guarantees and subsidies, further easing the burden on business owners.

Additionally, digital lending platforms have emerged as a key player in small business financing. Many banks now offer streamlined online application processes, allowing entrepreneurs to apply for loans with minimal paperwork and quicker approvals. This shift toward digital accessibility is expected to enhance financial inclusion and boost economic activity.

As small businesses take advantage of these new loan opportunities, financial experts encourage borrowers to carefully assess their needs and repayment capacities. By making informed borrowing decisions, businesses can maximize the benefits of these financial products while ensuring long-term sustainability.', 
3, 3, 'approved','news.png'),

('Understanding Personal Loan Options', 
'In a bid to support small businesses, several banks have rolled out new loan offerings with favorable terms. These loans are designed to provide entrepreneurs with the capital needed to grow their operations. The initiative has been welcomed by many in the business community, who see it as a lifeline during challenging economic times.

With the recent economic fluctuations, small businesses have faced difficulties in securing funding. Recognizing this issue, banks have introduced specialized loan programs that offer lower interest rates, flexible repayment schedules, and reduced collateral requirements. These measures aim to encourage business growth and sustain local economies.

Many of these new loan products cater specifically to startups and micro-enterprises, sectors that traditionally struggle to obtain financing. Some banks are even partnering with government agencies and financial institutions to provide additional guarantees and subsidies, further easing the burden on business owners.

Additionally, digital lending platforms have emerged as a key player in small business financing. Many banks now offer streamlined online application processes, allowing entrepreneurs to apply for loans with minimal paperwork and quicker approvals. This shift toward digital accessibility is expected to enhance financial inclusion and boost economic activity.

As small businesses take advantage of these new loan opportunities, financial experts encourage borrowers to carefully assess their needs and repayment capacities. By making informed borrowing decisions, businesses can maximize the benefits of these financial products while ensuring long-term sustainability.', 
3, 3, 'approved','news.png'),

('The Rise of Online Loan Platforms', 
'The emergence of online loan platforms has transformed the borrowing landscape. These platforms offer quick and easy access to funds, often with fewer requirements than traditional banks. However, potential borrowers must exercise caution and thoroughly research these options to avoid predatory lending practices.

The digital revolution has significantly changed the way people access credit, with online lending platforms growing in popularity due to their convenience and speed. Unlike traditional banks that require extensive paperwork and in-person visits, many online lenders allow users to apply for loans from the comfort of their homes. This accessibility has made borrowing more inclusive, especially for individuals who may not qualify for conventional bank loans.

One of the key advantages of online loan platforms is their streamlined application process. Many platforms use automated systems to assess borrowers creditworthiness, providing instant pre-approval decisions. Additionally, the use of alternative credit scoring methods enables individuals with limited credit history to access financing opportunities they might not otherwise have.

However, the rise of online lending has also brought concerns regarding transparency and ethical lending practices. Some lenders impose exorbitant interest rates and hidden fees, trapping borrowers in cycles of debt. To protect themselves, consumers should thoroughly review loan terms, compare multiple lenders, and check for proper licensing and regulatory compliance.

Despite these challenges, online loan platforms continue to grow, providing borrowers with flexible and innovative financing solutions. As technology advances, the industry is expected to introduce even more secure and consumer-friendly lending options, further reshaping the financial landscape.', 
3, 3, 'draft','news.png'),

('Loan Offers and Economic Recovery', 
'As the economy begins to recover, banks are becoming more optimistic about lending. New loan offers are being introduced to stimulate growth and support consumers looking to make significant purchases. This shift could signal a positive trend for both lenders and borrowers in the coming months.

The recent economic downturn forced many financial institutions to tighten their lending policies. However, as markets stabilize and consumer confidence grows, banks are once again opening their doors to borrowers. This resurgence in lending is particularly beneficial for individuals seeking mortgages, car loans, and personal financing options.

One of the driving forces behind these new loan offers is the need to revitalize economic activity. Governments and central banks have encouraged lending through policy measures, such as reduced interest rates and liquidity injections into the banking system. As a result, financial institutions are introducing attractive loan packages with lower interest rates and extended repayment terms.

For borrowers, this presents a prime opportunity to secure loans at favorable conditions. However, it remains crucial to assess one’s financial situation carefully before taking on debt. Responsible borrowing ensures that individuals can meet repayment obligations without financial strain, ultimately contributing to long-term economic stability.', 
3, 3, 'approved','news.png'),

('Comparing Loan Rates: What You Need to Know', 
'When considering a loan, comparing rates from different lenders is essential. This article provides tips on how to effectively compare loan offers, including understanding APR, fees, and repayment options. Knowledge is power when it comes to making financial decisions.

Interest rates vary widely across lenders, making it imperative for borrowers to conduct thorough research before committing to a loan. The Annual Percentage Rate (APR) is a critical factor in this comparison, as it encompasses both the interest rate and additional fees associated with the loan. A lower APR typically indicates a more affordable loan in the long run.

Beyond interest rates, borrowers should also evaluate loan terms, including repayment periods, prepayment penalties, and flexibility in payment schedules. Some lenders offer fixed interest rates, ensuring stable monthly payments, while others provide variable rates that may fluctuate based on market conditions.

Additionally, it is advisable to consider the lender’s reputation and customer service quality. Online reviews and regulatory compliance records can offer valuable insights into a lender’s reliability. Borrowers should also be wary of hidden fees that may not be immediately evident in promotional materials.

Taking the time to compare loan offers can lead to significant savings and better financial outcomes. With careful planning and informed decision-making, borrowers can secure financing that aligns with their financial goals while avoiding unnecessary debt burdens.', 
3, 3, 'approved','news.png'),

('Best Savings Plans for 2025', 
'As we approach 2025, financial institutions are rolling out new savings plans aimed at helping consumers achieve their financial goals. These plans often come with competitive interest rates and flexible terms. This article reviews the best options available for different savings needs, from emergency funds to long-term investments.

With economic conditions shifting, consumers must carefully evaluate savings plans to ensure they maximize returns while maintaining easy access to funds when needed. Banks and credit unions are introducing specialized savings accounts, such as high-yield savings, fixed-term deposits, and hybrid investment-savings options tailored to diverse financial goals.

For those looking to grow their wealth gradually, high-yield savings accounts offer attractive interest rates while allowing liquidity. Meanwhile, term deposits provide higher returns for those willing to lock in their funds for a fixed period. Additionally, retirement-focused savings plans, such as IRAs or pension-linked accounts, continue to be crucial for long-term financial security.

Experts recommend diversifying savings strategies, ensuring a portion of funds remains easily accessible while other savings contribute to long-term financial growth. As competition among financial institutions increases, comparing terms, fees, and incentives will be key to choosing the best savings plan in 2025.

', 
3, 4, 'approved','news.png'),

('The Importance of Saving Early', 
'Saving early can lead to significant financial benefits in the long run. This article discusses the advantages of starting a savings plan as soon as possible, including compound interest and financial security. We also provide strategies for young adults to begin their savings journey.

One of the most powerful financial principles is compound interest, which allows savings to grow exponentially over time. The earlier an individual starts saving, the greater the accumulated returns due to the reinvestment of interest earnings. Even small contributions made consistently can lead to substantial financial gains over the years.

Beyond financial growth, early saving instills disciplined financial habits, promoting responsible money management and reducing reliance on debt. By building an emergency fund early, individuals can safeguard themselves against unexpected expenses and financial hardships.

To kickstart their savings journey, young adults can explore employer-sponsored savings programs, automated savings transfers, and budget-friendly investment options. Financial literacy also plays a key role, enabling individuals to make informed decisions about savings products and long-term financial planning.

Starting early doesn’t require large sums—what matters is consistency. Establishing a habit of saving early lays the foundation for financial independence and long-term wealth accumulation.', 
3, 4, 'approved','news.png'),

('How to Choose the Right Savings Plan', 
'With numerous savings plans available, choosing the right one can be overwhelming. This guide breaks down key factors to consider, such as interest rates, account fees, and withdrawal limits. By understanding these elements, consumers can select a plan that aligns with their financial goals.

A well-structured savings plan is essential for both short-term and long-term financial stability. High-yield savings accounts provide better returns, while fixed-term deposits can offer higher interest rates for those willing to commit their funds. Flexible savings accounts, on the other hand, allow withdrawals without penalties, making them ideal for emergency funds.

When selecting a plan, consumers should compare annual percentage yields (APY), minimum balance requirements, and any hidden fees that might reduce their earnings. Some plans also offer tax benefits, particularly retirement-focused savings plans, which can help maximize wealth accumulation over time.

Experts recommend diversifying savings across multiple accounts to balance liquidity and returns. By considering financial goals, risk tolerance, and expected expenses, individuals can tailor a savings strategy that ensures long-term financial security.', 
3, 4, 'draft','news.png'),

('Savings Plans for Retirement', 
'Planning for retirement is crucial, and savings plans play a vital role in this process. This article explores different savings options specifically designed for retirement, including IRAs and 401(k) plans. We emphasize the importance of starting early to ensure a comfortable retirement.

Retirement savings plans offer tax advantages that help individuals grow their wealth efficiently. Traditional IRAs and 401(k) plans allow pre-tax contributions, reducing taxable income while enabling investments to grow tax-deferred. Roth IRAs, meanwhile, provide tax-free withdrawals in retirement, making them a strategic option for long-term financial planning.

Choosing the right retirement plan depends on factors such as employer contributions, annual income, and expected retirement lifestyle. Many employers offer 401(k) matching programs, which effectively increase savings without additional effort from employees. For self-employed individuals, SEP IRAs and Solo 401(k) plans provide excellent alternatives with high contribution limits.

Regularly reviewing and adjusting retirement savings ensures that individuals remain on track with their financial goals. As life circumstances change, reallocating funds between conservative and growth-focused investments can help optimize long-term returns while minimizing risk.', 
3, 4, 'approved','news.png'),

('Tips for Maximizing Your Savings', 
'Maximizing savings requires discipline and strategy. This article offers practical tips on how to increase your savings rate, including budgeting techniques and automating savings contributions. Small changes can lead to significant financial growth over time.

One of the simplest ways to build savings is through automation. Setting up direct deposits to a savings account ensures consistent contributions without the temptation to spend. Financial advisors also recommend following the 50/30/20 budgeting rule—allocating 50% of income to necessities, 30% to discretionary spending, and 20% to savings and debt repayment.

Reducing unnecessary expenses, such as dining out frequently or subscription services, can free up extra funds for savings. Additionally, using cashback rewards and employer-sponsored savings programs can help individuals grow their savings with minimal effort.

Investing in high-yield accounts or low-risk investment options further enhances savings potential. Regularly reviewing financial goals and adjusting strategies accordingly ensures continuous progress toward financial independence.', 
3, 4, 'approved','news.png');


INSERT INTO term (term_name, duration, term_type,status)
VALUES 
('Monthly', 1, 'monthly', 'active'),
('Bi-Monthly', 2, 'monthly', 'active'),
('Quarterly', 3, 'quarterly', 'active'),
('Semi-Annual', 6, 'quarterly', 'active'),
('Yearly', 12, 'annually', 'active');


INSERT INTO services (service_name, description, service_type, status)
VALUES 
('Savings ', 'saving money.', 'saving', 'active'),
('Loan', ' loan.', 'loan', 'active'),
('Deposit', 'Fixed deposit account.', 'deposit', 'active'),
('Withdrawal', 'Service for withdrawing funds.', 'withdrawal', 'active');

INSERT INTO service_terms (term_id, service_id, term_name, description, contract_terms, early_payment_penalty, interest_rate, min_deposit, status)
VALUES 
-- Gửi tiết kiệm 1 tháng
((SELECT term_id FROM term WHERE duration = 1), 1, 
 '1-Month Fixed Deposit Savings', 
 'Customers must deposit for 1 month to earn interest.', 
 'Minimum deposit required. Early withdrawal results in penalty.', 
 10.00, 3.0, 500.00, 'active'),

-- Gửi tiết kiệm 2 tháng
((SELECT term_id FROM term WHERE duration = 2), 1, 
 '2-Month Fixed Deposit Savings', 
 'Customers must deposit for 2 months to earn interest.', 
 'Minimum deposit required. Early withdrawal results in penalty.', 
 20.00, 3.2, 600.00, 'active'),

-- Gửi tiết kiệm 3 tháng
((SELECT term_id FROM term WHERE duration = 3), 1, 
 '3-Month Fixed Deposit Savings', 
 'Customers must deposit for 3 months to earn interest.', 
 'Minimum deposit required. Early withdrawal results in penalty.', 
 30.00, 3.5, 700.00, 'active'),

-- Gửi tiết kiệm 6 tháng
((SELECT term_id FROM term WHERE duration = 6), 1, 
 '6-Month Fixed Deposit Savings', 
 'Customers must deposit for 6 months to earn interest.', 
 'Minimum deposit required. Early withdrawal results in penalty.', 
 50.00, 4.0, 1000.00, 'active'),

-- Gửi tiết kiệm 12 tháng
((SELECT term_id FROM term WHERE duration = 12), 1, 
 '12-Month Fixed Deposit Savings', 
 'Customers must deposit for 12 months to earn interest.', 
 'Minimum deposit required. Early withdrawal results in penalty.', 
 100.00, 4.5, 1500.00, 'active');

 INSERT INTO service_terms (term_id, service_id, term_name, description, contract_terms, early_payment_penalty, interest_rate, min_payment, status)
VALUES 
-- Vay 1 tháng
((SELECT term_id FROM term WHERE duration = 1), 2, 
 '1-Month Personal Loan', 
 'Borrowers must repay in 1 month.', 
 'Fixed monthly repayment required. Late payment incurs a penalty.', 
 15.00, 5.0, 300.00, 'active'),

-- Vay 2 tháng
((SELECT term_id FROM term WHERE duration = 2), 2, 
 '2-Month Personal Loan', 
 'Borrowers must repay in 2 months.', 
 'Fixed monthly repayment required. Late payment incurs a penalty.', 
 25.00, 5.2, 400.00, 'active'),

-- Vay 3 tháng
((SELECT term_id FROM term WHERE duration = 3), 2, 
 '3-Month Personal Loan', 
 'Borrowers must repay in 3 months.', 
 'Fixed monthly repayment required. Late payment incurs a penalty.', 
 40.00, 5.5, 500.00, 'active'),

-- Vay 6 tháng
((SELECT term_id FROM term WHERE duration = 6), 2, 
 '6-Month Personal Loan', 
 'Borrowers must repay in 6 months.', 
 'Fixed monthly repayment required. Late payment incurs a penalty.', 
 60.00, 6.0, 600.00, 'active'),

-- Vay 12 tháng
((SELECT term_id FROM term WHERE duration = 12), 2, 
 '12-Month Personal Loan', 
 'Borrowers must repay in 12 months.', 
 'Fixed monthly repayment required. Late payment incurs a penalty.', 
 120.00, 6.5, 700.00, 'active');
 ALTER TABLE service_terms ALTER COLUMN term_id INT NULL;


INSERT INTO feedback (customer_id, service_id, feedback_content,feedback_rate)
VALUES 
(1, 1, 'Great service!',5),
(2, 2, 'Could be better.',4),
(3, 1, 'Very satisfied.',4),
(4, 3, 'Not happy with the experience.',2),
(5, 4, 'Excellent support!',4);

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

INSERT INTO insurance_contract (customer_id, service_id, policy_id, duration, start_date, end_date, payment_frequency, status)
VALUES 
(4, 2, 4, 12, '2023-04-01', '2024-04-01', 'monthly', 'active'),
(1, 2, 1, 12, '2023-01-01', '2024-01-01', 'monthly', 'active'),
(2, 2, 2, 12, '2023-02-01', '2024-02-01', 'annually', 'active'),
(3, 2, 3, 12, '2023-03-01', '2024-03-01', 'annually', 'active'),
(4, 2, 4, 12, '2023-04-01', '2024-04-01', 'annually', 'active'),
(4, 2, 5, 12, '2023-05-01', '2024-05-01', 'annually', 'active');


INSERT INTO insurance_contract_detail (contract_id, insurance_id, CoverageAmount, PremiumAmount, PaidAmount,StartDate, EndDate)
VALUES 
(1, 1, 100000.00, 5000.00,1000.00,'2023-01-01', '2024-01-01'),
(2, 2, 200000.00, 7000.00,0, '2023-02-01', '2024-02-01'),
(3, 3, 15000.00, 2500.00,0, '2023-03-01', '2024-03-01'),
(4, 4, 300000.00, 6000.00,0, '2023-04-01', '2024-04-01'),
(5, 5, 50000.00, 3000.00, 0,'2023-05-01', '2024-05-01'),
(3, 1, 100000.00, 5000.00,0, '2024-01-01', '2025-01-01'),
(2, 3, 15000.00, 2500.00,0, '2024-02-01', '2025-02-01'),
(3, 2, 200000.00, 7000.00,0, '2024-03-01', '2025-03-01'),
(4, 5, 50000.00, 3000.00,0, '2024-04-01', '2025-04-01');

INSERT INTO insurance_transactions (contract_id, customer_id, amount, transaction_type, notes)
VALUES
(1, 1, 5000.00, 'premium_payment', N'Thanh toán phí bảo hiểm hàng tháng.'),
(2, 2, 7000.00, 'premium_payment', N'Thanh toán phí bảo hiểm hàng quý.'),
(3, 3, 2500.00, 'claim_payment', N'Thanh toán bồi thường cho tai nạn.'),
(4, 4, 6000.00, 'premium_payment', N'Thanh toán phí bảo hiểm hàng tháng.'),
(5, 5, 3000.00, 'premium_payment', N'Thanh toán phí bảo hiểm hàng năm.');

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
-- Silver Membership
((SELECT term_id FROM term WHERE duration = 1), '1-Month Membership Silver', 'Basic Silver membership for 1 month', 'Access to standard banking services with minor benefits.', 'silver', 100.00, 1.5, 2.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), '6-Month Membership Silver', 'Basic Silver membership for 6 months', 'Extended benefits for half a year.', 'silver', 500.00, 2.0, 2.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), '12-Month Membership Silver', 'Basic Silver membership for 12 months', 'Annual subscription with better financial services.', 'silver', 900.00, 2.5, 3.0, 'active', GETDATE()),

-- Gold Membership
((SELECT term_id FROM term WHERE duration = 1), '1-Month Membership Gold', 'Gold membership with additional perks for 1 month', 'Priority customer service and better loan/savings rates.', 'gold', 200.00, 2.0, 3.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), '6-Month Membership Gold', 'Gold membership with additional perks for 6 months', 'More benefits for a longer period.', 'gold', 1000.00, 2.5, 3.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), '12-Month Membership Gold', 'Gold membership with additional perks for 12 months', 'Best value package with excellent banking support.', 'gold', 1800.00, 3.0, 4.0, 'active', GETDATE()),

-- Diamond Membership
((SELECT term_id FROM term WHERE duration = 1), '1-Month Membership Diamond', 'Exclusive Diamond membership for 1 month', 'Premium banking services with highest benefits.', 'diamond', 500.00, 3.0, 4.0, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 6), '6-Month Membership Diamond', 'Exclusive Diamond membership for 6 months', 'More access to financial advisors and services.', 'diamond', 2500.00, 3.5, 4.5, 'active', GETDATE()),
((SELECT term_id FROM term WHERE duration = 12), '12-Month Membership Diamond', 'Exclusive Diamond membership for 12 months', 'Best-in-class financial services with maximum benefits.', 'diamond', 4500.00, 4.0, 5.0, 'active', GETDATE())

 
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
				   

