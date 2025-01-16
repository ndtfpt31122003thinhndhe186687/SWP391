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
CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,  
    full_name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    phone_number NVARCHAR(20),
    address NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    gender NVARCHAR(20) CHECK (gender IN ('male', 'female')),
    date_of_birth DATE,
    profile_picture NVARCHAR(255) 
);

CREATE TABLE guest_requests (
    request_id INT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL,
	password NVARCHAR(255) NOT NULL,
    phone_number NVARCHAR(20),
	address NVARCHAR(MAX),
	gender NVARCHAR(20) CHECK (gender IN ('male', 'female')),
    request_date DATETIME DEFAULT GETDATE(),
	date_of_birth DATE,
    status NVARCHAR(20) CHECK (status IN ('pending', 'approved', 'rejected')) DEFAULT 'pending',
	user_id INT NULL,  -- Optional reference to users table if the guest becomes a registered user
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL

);

CREATE TABLE customer ( 
    customer_id INT,  
    card_type NVARCHAR(20) CHECK (card_type IN ('credit', 'debit')) NOT NULL, 
    user_type NVARCHAR(20) CHECK (user_type IN ('customer')) NOT NULL,
    amount DECIMAL(15, 2) DEFAULT 0.00,  -- Số dư tài khoản cho thẻ debit
    credit_limit DECIMAL(15, 2) DEFAULT 0.00,  -- Hạn mức tín dụng cho thẻ credit
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active',  
    FOREIGN KEY (customer_id) REFERENCES users(user_id),  
    PRIMARY KEY (customer_id)
);


CREATE TABLE admin (
    admin_id INT, 
    user_type NVARCHAR(20) CHECK (user_type IN ('admin')) NOT NULL,
	FOREIGN KEY (admin_id) REFERENCES users(user_id),
    PRIMARY KEY (admin_id)
);

CREATE TABLE bank_teller (
    bankteller_id INT, 
	user_type NVARCHAR(20) CHECK (user_type IN ('staff')) NOT NULL,
	FOREIGN KEY (bankteller_id) REFERENCES users(user_id),
    PRIMARY KEY (bankteller_id)
);

CREATE TABLE marketer (
    marketer_id INT,
    user_type NVARCHAR(20) CHECK (user_type IN ('marketer')) NOT NULL,   
    FOREIGN KEY (marketer_id) REFERENCES users(user_id),
	PRIMARY KEY (marketer_id)
);

CREATE TABLE accountant (
    accountant_id INT,
    user_type NVARCHAR(20) CHECK (user_type IN ('accountant')) NOT NULL,    
    FOREIGN KEY (accountant_id) REFERENCES users(user_id),
	PRIMARY KEY (accountant_id)
);


-- Bảng service_providers
CREATE TABLE service_providers (
    provider_id INT IDENTITY(1,1) PRIMARY KEY, 
    provider_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    email NVARCHAR(255) NOT NULL UNIQUE,
    phone_number NVARCHAR(20),
    address NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) DEFAULT 'active',
	username NVARCHAR(255) NOT NULL UNIQUE,  -- Tên người dùng cho service provider
    password NVARCHAR(255) NOT NULL -- Mật khẩu của service provider
);

-- Bảng provider_services
CREATE TABLE provider_services (
    provider_service_id INT IDENTITY(1,1) PRIMARY KEY,
    provider_id INT NOT NULL,
    price DECIMAL(15, 2) NOT NULL,
    description NVARCHAR(MAX),
    FOREIGN KEY (provider_id) REFERENCES service_providers(provider_id) ON DELETE CASCADE, 
);

-- Bảng bills
CREATE TABLE bills (
    bill_id INT IDENTITY(1,1) PRIMARY KEY,
    provider_service_id INT NOT NULL,
    customer_id INT NOT NULL,
    bill_date DATETIME DEFAULT GETDATE(),
    due_date DATETIME NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    status NVARCHAR(20) CHECK (status IN ('pending', 'paid', 'overdue', 'canceled')) DEFAULT 'pending',
    FOREIGN KEY (provider_service_id) REFERENCES provider_services(provider_service_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Bảng provider_transactions
CREATE TABLE provider_transactions (
    transaction_id INT IDENTITY(1,1) PRIMARY KEY,
    bill_id INT not null,
    customer_id INT not null,
    transaction_date DATETIME DEFAULT GETDATE(),
    amount DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id) ON DELETE NO ACTION, -- Thay đổi thành NO ACTION
    FOREIGN KEY (customer_id) REFERENCES users(user_id) ON DELETE NO ACTION -- Thay đổi thành NO ACTION
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
    user_id INT,
    service_id INT,
    amount DECIMAL(15, 2),
    interest_rate DECIMAL(5, 2),
    start_date DATETIME DEFAULT GETDATE(),
    end_date DATETIME NULL,  -- Nullable to accommodate ongoing savings accounts
    status NVARCHAR(20) CHECK (status IN ('active', 'completed', 'closed')) DEFAULT 'active',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE loan (
    loan_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    service_id INT,
    amount DECIMAL(15, 2),
    interest_rate DECIMAL(5, 2),
    loan_term INT,
    request_date DATETIME DEFAULT GETDATE(),
    status NVARCHAR(20) CHECK (status IN ('pending', 'approved', 'rejected', 'disbursed')) DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
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
    customer_id INT,
    bankteller_id INT,
    service_id INT,  
    request_date DATETIME DEFAULT GETDATE(),
	status NVARCHAR(20) CHECK (status IN ('pending', 'approved', 'rejected')) DEFAULT 'pending',
    FOREIGN KEY (customer_id) REFERENCES users(user_id),
	FOREIGN KEY (bankteller_id) REFERENCES users(user_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id),

);

CREATE TABLE feedback (
    feedback_id INT IDENTITY(1,1) PRIMARY KEY, -- Mã phản hồi duy nhất
    customer_id INT NOT NULL, -- ID của khách hàng (customer)
    service_id INT NULL, -- Dịch vụ liên quan đến phản hồi (nếu có)
    feedback_content NVARCHAR(MAX) NOT NULL, -- Nội dung phản hồi
    feedback_date DATETIME DEFAULT GETDATE(), -- Ngày gửi phản hồi
    FOREIGN KEY (customer_id) REFERENCES users(user_id), -- Khóa ngoại đến bảng users
    FOREIGN KEY (service_id) REFERENCES services(service_id) -- Khóa ngoại đến bảng services
);


CREATE TABLE transactions (
    transaction_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    service_id INT,
    amount DECIMAL(15, 2),
    transaction_date DATETIME DEFAULT GETDATE(),
    transaction_type NVARCHAR(20) CHECK (transaction_type IN ('deposit', 'withdrawal', 'payment')) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE user_services (
    user_id INT,
    service_id INT,
    PRIMARY KEY (user_id, service_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

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

