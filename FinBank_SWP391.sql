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
	created_at DATETIME DEFAULT GETDATE(),
    profile_picture NVARCHAR(255) 
);

CREATE TABLE asset (
	asset_id INT IDENTITY(1,1) PRIMARY KEY,  
	description NVARCHAR(MAX), 
	Value DECIMAL(15, 2) NOT NULL,
	customer_id INT NOT NULL,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
);


CREATE TABLE role (
    role_id INT IDENTITY(1,1) PRIMARY KEY, 
    role_name NVARCHAR(50) NOT NULL UNIQUE, -- Tên vai trò (vd: admin, bank_teller, ...)
);


CREATE TABLE staff (
    staff_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất cho mỗi nhân viên
    full_name NVARCHAR(255) NOT NULL, -- Họ và tên
    email NVARCHAR(255) NOT NULL UNIQUE, -- Email duy nhất
    password NVARCHAR(255) NOT NULL, -- Mật khẩu
    username NVARCHAR(255) NOT NULL UNIQUE, -- Tên người dùng duy nhất
    phone_number NVARCHAR(20) UNIQUE, -- Số điện thoại
    gender NVARCHAR(20) CHECK (gender IN ('male', 'female')), -- Giới tính
    date_of_birth DATE, -- Ngày sinh (tùy chọn)
    address NVARCHAR(MAX), -- Địa chỉ
    role_id INT NOT NULL, -- Liên kết với bảng role
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo bản ghi
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active', -- Trạng thái nhân viên
    FOREIGN KEY (role_id) REFERENCES role(role_id) -- Khóa ngoại tham chiếu bảng role
);

CREATE TABLE news (
    news_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của bài viết
    title NVARCHAR(255) NOT NULL, -- Tiêu đề bài viết
    content NVARCHAR(MAX) NOT NULL, -- Nội dung chi tiết của bài viết
    staff_id INT NOT NULL, -- ID nhân viên tạo bài viết
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo bài viết
    updated_at DATETIME DEFAULT GETDATE(), -- Ngày cập nhật bài viết
    status NVARCHAR(20) CHECK (status IN ('draft', 'pending', 'approved', 'rejected')) DEFAULT 'draft', -- Trạng thái bài viết
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id), -- Khóa ngoại tham chiếu bảng staff
);


CREATE TABLE services (
    service_id INT IDENTITY(1,1) PRIMARY KEY,
    service_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    service_type NVARCHAR(20) CHECK (service_type IN ('savings', 'loan')) NOT NULL,
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active'
);

CREATE TABLE savings (
    savings_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
    amount DECIMAL(15, 2),
    interest_rate DECIMAL(5, 2),
    start_date DATETIME DEFAULT GETDATE(),
    end_date DATETIME NULL,  -- Nullable to accommodate ongoing savings accounts
    status NVARCHAR(20) CHECK (status IN ('active', 'completed', 'closed')) DEFAULT 'active',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE service_contract (
    contract_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của hợp đồng
    customer_id INT NOT NULL, -- ID khách hàng
    service_id INT NOT NULL, -- ID dịch vụ (loan hoặc savings)
    contract_type NVARCHAR(20) CHECK (contract_type IN ('loan', 'savings')) NOT NULL, -- Loại hợp đồng
    start_date DATE NOT NULL, -- Ngày bắt đầu hiệu lực
    end_date DATE NULL, -- Ngày kết thúc hiệu lực (nullable để xử lý các hợp đồng đang hoạt động)
    terms NVARCHAR(MAX) NOT NULL, -- Điều khoản hợp đồng
    amount DECIMAL(15, 2) NOT NULL, -- Số tiền trong hợp đồng
    interest_rate DECIMAL(5, 2), -- Lãi suất áp dụng (nếu có)
    payment_schedule NVARCHAR(MAX), -- Lịch trình thanh toán
    status NVARCHAR(20) CHECK (status IN ('active', 'completed', 'terminated')) DEFAULT 'active', -- Trạng thái
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo hợp đồng
    updated_at DATETIME DEFAULT GETDATE(), -- Ngày cập nhật cuối cùng
    notes NVARCHAR(MAX), -- Ghi chú thêm
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE loan (
    loan_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
    amount DECIMAL(15, 2),
    interest_rate DECIMAL(5, 2),
    loan_term INT,
    request_date DATETIME DEFAULT GETDATE(),
    status NVARCHAR(20) CHECK (status IN ('pending', 'approved', 'rejected', 'disbursed')) DEFAULT 'pending',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE loan_disbursements (
    disbursement_id INT IDENTITY(1,1) PRIMARY KEY,
    loan_id INT NOT NULL,
    disbursement_date DATETIME DEFAULT GETDATE(),
    amount DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES loan(loan_id)
);

CREATE TABLE request (
    customer_id INT NOT NULL,
    staff_id INT NOT NULL,
    service_id INT NOT NULL,  
    request_date DATETIME DEFAULT GETDATE(),
	status NVARCHAR(20) CHECK (status IN ('pending', 'approved', 'rejected')) DEFAULT 'pending',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (staff_id) REFERENCES staff(staff_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id),

);

CREATE TABLE feedback (
    feedback_id INT IDENTITY(1,1) PRIMARY KEY, -- Mã phản hồi duy nhất
    customer_id INT NOT NULL, -- ID của khách hàng (customer)
    service_id INT NULL, -- Dịch vụ liên quan đến phản hồi (nếu có)
    feedback_content NVARCHAR(MAX) NOT NULL, -- Nội dung phản hồi
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
    transaction_type NVARCHAR(20) CHECK (transaction_type IN ('deposit', 'withdrawal', 'payment')) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE customer_services (
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
    PRIMARY KEY (customer_id, service_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

-- Bảng để quản lý bên thứ ba (insurance)
CREATE TABLE insurance (
    insurance_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của bên bảo hiểm
	username NVARCHAR(255) NOT NULL UNIQUE, -- Tên người dùng duy nhất
    password NVARCHAR(255) NOT NULL,
    insurance_name NVARCHAR(255) NOT NULL, -- Tên bên bảo hiểm
    email NVARCHAR(255) NOT NULL UNIQUE, -- Email liên lạc
    phone_number NVARCHAR(20)  UNIQUE, -- Số điện thoại
    address NVARCHAR(MAX), -- Địa chỉ
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active', -- Trạng thái
    created_at DATETIME DEFAULT GETDATE() -- Thời điểm thêm vào hệ thống
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
    created_at DATETIME DEFAULT GETDATE(), -- Ngày tạo loại bảo hiểm
    FOREIGN KEY (insurance_id) REFERENCES insurance(insurance_id)
);

-- Bảng để lưu thông tin hợp đồng bảo hiểm
CREATE TABLE insurance_contract (
    contract_id INT IDENTITY(1,1) PRIMARY KEY, -- ID duy nhất của hợp đồng
    customer_id INT NOT NULL, -- ID khách hàng
	service_id INT NOT NULL, -- ID dich vu
    policy_id INT NOT NULL, -- ID loại bảo hiểm
    start_date DATE NOT NULL, -- Ngày bắt đầu hiệu lực
    end_date DATE NOT NULL, -- Ngày kết thúc hiệu lực
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


-- asset , news , transaction_insurance


--CREATE TRIGGER trg_set_card_limits
--ON customer
--AFTER INSERT, UPDATE
--AS
--BEGIN
--    -- Nếu loại thẻ là debit, set credit_limit = 0 và amount có giá trị
--    UPDATE customer
--    SET credit_limit = 0
--    WHERE card_type = 'debit' AND customer_id IN (SELECT customer_id FROM inserted);
    
--    -- Nếu loại thẻ là credit, set amount = 0 và credit_limit có giá trị
--    UPDATE customer
--    SET amount = 0
--    WHERE card_type = 'credit' AND customer_id IN (SELECT customer_id FROM inserted);
--END;

--INSERT INTO customer (full_name, email, username, password, phone_number, address, card_type, amount, credit_limit, status, gender, date_of_birth, profile_picture)  
--VALUES 
--('Nguyen Van A', 'nguyenvana@example.com', 'nguyenvana', 'password123', '0987654321', N'123 Đường ABC, Hà Nội', 'debit', 5000000.00, 0.00, 'active', 'male', '1990-05-15', 'profile_a.jpg'),  
--('Tran Thi B', 'tranthib@example.com', 'tranthib', 'securepass', '0912345678', N'456 Đường XYZ, Hồ Chí Minh', 'credit', 0.00, 20000000.00, 'active', 'female', '1995-09-22', 'profile_b.jpg');  
--select * from customer
