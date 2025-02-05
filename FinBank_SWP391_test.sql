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
	FOREIGN KEY (role_id) REFERENCES role(role_id) -- Khóa ngoại tham chiếu bảng role
);
--them thuoc tinh no xau hay khong : đã có bảng riêng

CREATE TABLE asset (
	asset_id INT IDENTITY(1,1) PRIMARY KEY,  
	description NVARCHAR(MAX),
	Value DECIMAL(15, 2) NOT NULL,
	customer_id INT NOT NULL,
	[status] NVARCHAR(20) CHECK ([status] IN ('verified', 'unverified')) DEFAULT 'unverified',
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
);

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

CREATE TABLE savings (
    savings_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
	term_id INT NOT NULL,
    amount DECIMAL(15, 2),
    interest_rate DECIMAL(5, 2),
    start_date DATETIME DEFAULT GETDATE(),
    end_date DATETIME NULL,
	terms NVARCHAR(MAX), -- Điều khoản hợp đồng
    notes NVARCHAR(MAX), -- Ghi chú thêm
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id),
	FOREIGN KEY (term_id) REFERENCES term(term_id)
);

--Loan type(Neu ma la vay tin chap thi bat nhap luong, neu ma la vay the chap thi nhap tai san) : ok
CREATE TABLE loan (
    loan_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
	term_id INT NOT NULL,
    amount DECIMAL(15, 2),
    interest_rate DECIMAL(5, 2),
	start_date DATETIME DEFAULT GETDATE(),
	terms NVARCHAR(MAX), -- Điều khoản hợp đồng
    notes NVARCHAR(MAX), -- Ghi chú thêm
	loan_type NVARCHAR(20) CHECK (loan_type IN ('secured', 'unsecured')) NOT NULL, -- Phân biệt thế chấp/tín chấp
    asset_id INT NOT NULL, -- Tham chiếu đến bảng asset (nếu là thế chấp)
	FOREIGN KEY (asset_id) REFERENCES asset(asset_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id),
	FOREIGN KEY (term_id) REFERENCES term(term_id)
);
--bang tra no
CREATE TABLE loan_disbursements (
    disbursement_id INT IDENTITY(1,1) PRIMARY KEY,
    loan_id INT NOT NULL,
    disbursement_date DATETIME DEFAULT GETDATE(),
    amount DECIMAL(15, 2) NOT NULL,
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


CREATE TABLE request (
    customer_id INT NOT NULL,
    staff_id INT NOT NULL,
    service_id INT NOT NULL,  
    request_date DATETIME DEFAULT GETDATE(),
	status NVARCHAR(20) CHECK (status IN ('pending', 'approved', 'rejected')) DEFAULT 'pending',
	PRIMARY KEY(customer_id,staff_id,service_id),
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
    transaction_type NVARCHAR(20) CHECK (transaction_type IN ('deposit', 'withdrawal')) NOT NULL,
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

INSERT INTO role (role_name)
VALUES 
('admin'),
('banker'),
('marketer'),
('accountant'),
('insurance'),
('customer'); 

INSERT INTO customer (full_name, email, username, password, phone_number, address, card_type, amount, credit_limit, status, gender, date_of_birth, profile_picture,role_id)
VALUES 
('Nguyen Van A', 'a@example.com', 'a', '123', '0123456789', '123 Street, City', 'credit', 1000.00, 5000.00, 'active', 'male', '1990-01-01', 'profile_a.jpg',6),
('Tran Thi B', 'b@example.com', 'b', '123', '0987654321', '456 Avenue, City', 'debit', 2000.00, 0.00, 'active', 'female', '1992-02-02', 'profile_b.jpg',6),
('Le Van C', 'c@example.com', 'c', '123', '0123456780', '789 Boulevard, City', 'credit', 1500.00, 3000.00, 'active', 'male', '1985-03-03', 'profile_c.jpg',6),
('Pham Thi D', 'd@example.com', 'd', '123', '0987654312', '321 Road, City', 'debit', 2500.00, 0.00, 'active', 'female', '1995-04-04', 'profile_d.jpg',6),
('Vo Van E', 'e@example.com', 'e', '123', '0123456790', '654 Lane, City', 'credit', 3000.00, 8000.00, 'active', 'male', '1988-05-05', 'profile_e.jpg',6);

INSERT INTO asset (description, Value, customer_id, [status])
VALUES 
('Car', 20000.00, 1, 'verified'),
('House', 150000.00, 2, 'unverified'),
('Laptop', 1500.00, 3, 'verified'),
('Smartphone', 800.00, 4, 'unverified'),
('Boat', 30000.00, 5, 'verified');

INSERT INTO staff (full_name, email,username,password, phone_number, gender, date_of_birth, address, role_id, status)
VALUES 
('Nguyen Van F', 'f@example.com', 'a', '123', '0123456781', 'male', '1980-06-06', '111 Staff St, City', 1, 'active'),
('Tran Thi G', 'g@example.com', 'b', '123', '0987654322', 'female', '1983-07-07', '222 Staff Ave, City', 2, 'active'),
('Le Van H', 'h@example.com', 'c', '123', '0123456782', 'male', '1978-08-08', '333 Staff Blvd, City', 3, 'active'),
('Pham Thi I', 'i@example.com', 'd', '123', '0987654323', 'female', '1990-09-09', '444 Staff Rd, City', 4, 'active'),
('Vo Van J', 'j@example.com', 'e', '123', '0123456783', 'male', '1985-10-10', '555 Staff Lane, City', 2, 'active');

INSERT INTO news (title, content, staff_id, status)
VALUES 
('Tin tức 1', 'Nội dung tin tức 1', 1, 'approved'),
('Tin tức 2', 'Nội dung tin tức 2', 2, 'pending'),
('Tin tức 3', 'Nội dung tin tức 3', 3, 'draft'),
('Tin tức 4', 'Nội dung tin tức 4', 4, 'approved'),
('Tin tức 5', 'Nội dung tin tức 5', 5, 'rejected');

INSERT INTO term (term_name, duration, term_type,status)
VALUES 
('Monthly', 1, 'monthly', 'active'),
('Quarterly', 3, 'quarterly', 'active'),
('Yearly', 12, 'annually', 'active'),
('Semi-Annual', 6, 'quarterly', 'active'),
('Bi-Monthly', 2, 'monthly', 'active');

INSERT INTO services (service_name, description, service_type, status)
VALUES 
('Savings ', 'saving money.', 'savings', 'active'),
('Loan', ' loan.', 'loan', 'active'),
('Deposit', 'Fixed deposit account.', 'deposit', 'active'),
('Withdrawal e', 'Service for withdrawing funds.', 'withdrawal', 'active');

INSERT INTO savings (customer_id, service_id, term_id, amount, interest_rate, terms, notes)
VALUES 
(1, 1, 1, 5000.00, 2.5, '1 year', 'No special notes.'),
(2, 1, 2, 10000.00, 3.0, '2 years', 'No special notes.'),
(3, 1, 3, 7500.00, 4.0, '3 years', 'No special notes.'),
(4, 1, 4, 3000.00, 2.0, '6 months', 'No special notes.'),
(5, 1, 1, 2000.00, 1.5, '1 year', 'No special notes.');

INSERT INTO loan (customer_id, service_id, term_id, amount, interest_rate, loan_type, asset_id, terms, notes)
VALUES 
(1, 2, 1, 10000.00, 5.0, 'unsecured', 1, 'No special terms.', 'No special notes.'),
(2, 3, 2, 50000.00, 4.5, 'secured', 2, 'Secured by house.', 'No special notes.'),
(3, 2, 3, 15000.00, 6.0, 'unsecured', 3, 'No special terms.', 'No special notes.'),
(4, 3, 4, 80000.00, 3.5, 'secured', 4, 'Secured by car.', 'No special notes.'),
(5, 2, 1, 20000.00, 5.5, 'unsecured', 5, 'No special terms.', 'No special notes.');

INSERT INTO loan_disbursements (loan_id, amount)
VALUES 
(1, 1000.00),
(2, 5000.00),
(3, 1500.00),
(4, 8000.00),
(5, 2000.00);

INSERT INTO debt_management (customer_id, loan_id, debt_status, overdue_days, notes)
VALUES 
(1, 1, 'good', 0, 'No issues.'),
(2, 2, 'bad', 10, 'Late payment.'),
(3, NULL, 'good', 0, 'No debts.'),
(4, 4, 'bad', 5, 'Payment overdue.'),
(5, 5, 'good', 0, 'No issues.');

INSERT INTO request (customer_id, staff_id, service_id, status)
VALUES 
(1, 1, 1, 'approved'),
(2, 2, 2, 'pending'),
(3, 3, 1, 'approved'),
(4, 4, 3, 'rejected'),
(5, 5, 4, 'approved');

INSERT INTO feedback (customer_id, service_id, feedback_content)
VALUES 
(1, 1, 'Great service!'),
(2, 2, 'Could be better.'),
(3, 1, 'Very satisfied.'),
(4, 3, 'Not happy with the experience.'),
(5, 4, 'Excellent support!');

INSERT INTO transactions (customer_id, service_id, amount, transaction_type)
VALUES 
(1, 1, 500.00, 'deposit'),
(2, 2, 300.00, 'withdrawal'),
(3, 1, 200.00, 'deposit'),
(4, 3, 1000.00, 'withdrawal'),
(5, 4, 1500.00, 'deposit');

INSERT INTO customer_services (customer_id, service_id)
VALUES 
(1, 1),
(2, 2),
(3, 1),
(4, 3),
(5, 4);

INSERT INTO insurance (username, password, insurance_name, email, phone_number, address, status,role_id)
VALUES 
('insure_a', '123', 'Insurance Co A', 'insure_a@example.com', '0123456784', 'Insurance St, City', 'active',5),
('insure_b', '123', 'Insurance Co B', 'insure_b@example.com', '0987654325', 'Insurance Ave, City', 'active',5),
('insure_c', '123', 'Insurance Co C', 'insure_c@example.com', '0123456785', 'Insurance Blvd, City', 'active',5),
('insure_d', '123', 'Insurance Co D', 'insure_d@example.com', '0987654326', 'Insurance Rd, City', 'active',5),
('insure_e', '123', 'Insurance Co E', 'insure_e@example.com', '0123456786', 'Insurance Lane, City', 'active',5);

INSERT INTO insurance_policy (insurance_id, policy_name, description, coverage_amount, premium_amount, status)
VALUES 
(1, 'Health Insurance', 'Comprehensive health insurance.', 100000.00, 5000.00, 'active'),
(2, 'Life Insurance', 'Life insurance policy.', 200000.00, 7000.00, 'active'),
(3, 'Car Insurance', 'Insurance for vehicles.', 15000.00, 2500.00, 'active'),
(4, 'Home Insurance', 'Insurance for homes.', 300000.00, 6000.00, 'active'),
(5, 'Travel Insurance', 'Insurance for travelers.', 50000.00, 3000.00, 'active');

INSERT INTO insurance_contract (customer_id, service_id, policy_id, start_date, end_date, payment_frequency, status)
VALUES 
(1, 1, 1, '2023-01-01', '2024-01-01', 'monthly', 'active'),
(2, 2, 2, '2023-02-01', '2024-02-01', 'quarterly', 'active'),
(3, 1, 3, '2023-03-01', '2024-03-01', 'annually', 'active'),
(4, 3, 4, '2023-04-01', '2024-04-01', 'monthly', 'active'),
(5, 4, 5, '2023-05-01', '2024-05-01', 'annually', 'active');

INSERT INTO insurance_contract_detail (contract_id, insurance_id, CoverageAmount, PremiumAmount, StartDate, EndDate)
VALUES 
(1, 1, 100000.00, 5000.00, '2023-01-01', '2024-01-01'),
(2, 2, 200000.00, 7000.00, '2023-02-01', '2024-02-01'),
(3, 3, 15000.00, 2500.00, '2023-03-01', '2024-03-01'),
(4, 4, 300000.00, 6000.00, '2023-04-01', '2024-04-01'),
(5, 5, 50000.00, 3000.00, '2023-05-01', '2024-05-01');

INSERT INTO insurance_transactions (contract_id, customer_id, amount, transaction_type, notes)
VALUES 
(1, 1, 5000.00, 'premium_payment', 'Monthly premium payment.'),
(2, 2, 7000.00, 'premium_payment', 'Quarterly premium payment.'),
(3, 3, 2500.00, 'claim_payment', 'Claim payment for accident.'),
(4, 4, 6000.00, 'premium_payment', 'Monthly premium payment.'),
(5, 5, 3000.00, 'premium_payment', 'Annual premium payment.');

--select * from customer
--select * from staff 
select * from role
--select * from insurance
select * from staff 
select * from news
select * from request
select * from services
select * from term 
select * from transactions
--delete from news where staff_id= 2;
--delete from request where staff_id=2;
--delete from staff where staff_id= 2;
select d.debt_id, c.full_name, c.email,d.loan_id,d.debt_status,d.overdue_days,d.notes,d.updated_at from customer c JOIN debt_management d
ON c.customer_id=d.customer_id