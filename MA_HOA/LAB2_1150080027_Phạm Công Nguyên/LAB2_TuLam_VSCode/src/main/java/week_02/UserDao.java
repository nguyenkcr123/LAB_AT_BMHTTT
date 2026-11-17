package week_02;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserDao {
    private UserDao() {}

    public static void register(String user, char[] password)
            throws SQLException, GeneralSecurityException {
        if (!user.matches("[A-Za-z0-9_.]{3,40}")) {
            throw new IllegalArgumentException("Username từ 3 đến 40 ký tự, gồm chữ, số, _ hoặc dấu chấm.");
        }
        if (password.length < 8) {
            throw new IllegalArgumentException("Password phải có ít nhất 8 ký tự.");
        }

        byte[] salt = CipherUtil.randomBytes(16);
        byte[] hash = CipherUtil.pbkdf2(password, salt);
        String sql = "INSERT INTO dbo.app_users(username, salt, password_hash) VALUES (?, ?, ?)";

        try (Connection connection = DbConfig.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user);
            statement.setBytes(2, salt);
            statement.setBytes(3, hash);
            statement.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 2627 || ex.getErrorCode() == 2601) {
                throw new IllegalArgumentException("Username đã tồn tại.", ex);
            }
            throw ex;
        }
    }

    public static boolean login(String user, char[] password)
            throws SQLException, GeneralSecurityException {
        String sql = "SELECT salt, password_hash FROM dbo.app_users WHERE username = ?";

        try (Connection connection = DbConfig.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    return false;
                }
                byte[] actual = CipherUtil.pbkdf2(password, result.getBytes("salt"));
                return MessageDigest.isEqual(result.getBytes("password_hash"), actual);
            }
        }
    }

    public static String newTicket() {
        return CipherUtil.hex(CipherUtil.randomBytes(16));
    }
}