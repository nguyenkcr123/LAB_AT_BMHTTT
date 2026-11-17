USE LAB2_MaHoaHienDai;
GO

SELECT TOP (20) user_id, username, created_at
FROM dbo.app_users
ORDER BY user_id DESC;

SELECT TOP (50) history_id, created_at, algorithm, operation,
    app_username, input_length, output_length
FROM dbo.crypto_history
ORDER BY history_id DESC;
GO