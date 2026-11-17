USE master;
GO

IF SUSER_ID(N'lab2_user') IS NULL
    CREATE LOGIN lab2_user WITH PASSWORD = 'LabUser@2026', CHECK_POLICY = ON;
GO

USE LAB2_MaHoaHienDai;
GO

IF DATABASE_PRINCIPAL_ID(N'lab2_user') IS NULL
    CREATE USER lab2_user FOR LOGIN lab2_user;
GO

GRANT SELECT, INSERT ON dbo.app_users TO lab2_user;
GRANT SELECT, INSERT ON dbo.crypto_history TO lab2_user;
GO