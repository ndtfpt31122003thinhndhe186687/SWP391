USE [master]
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'AssignmentPRJ301')
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
    user_type NVARCHAR(20) CHECK (user_type IN ('customer', 'staff', 'admin')) NOT NULL,
    status NVARCHAR(20) CHECK (status IN ('active', 'inactive')) NOT NULL DEFAULT 'inactive',
    created_at DATETIME DEFAULT GETDATE(),
    gender NVARCHAR(20) CHECK (gender IN ('male', 'female', 'other')),
    date_of_birth DATE,
    profile_picture NVARCHAR(255) NULL
);

CREATE TABLE guest_requests (
    request_id INT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    phone_number NVARCHAR(20),
    request_date DATETIME DEFAULT GETDATE(),
    status NVARCHAR(20) CHECK (status IN ('pending', 'approved', 'rejected')) DEFAULT 'pending',
    user_id INT NULL,  -- Optional reference to users table if the guest becomes a registered user
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

CREATE TABLE services (
    service_id INT IDENTITY(1,1) PRIMARY KEY,
    service_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    service_type NVARCHAR(20) CHECK (service_type IN ('savings', 'loan', 'other')) NOT NULL,
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

CREATE TABLE loan_requests (
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
    FOREIGN KEY (loan_id) REFERENCES loan_requests(loan_id)
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


