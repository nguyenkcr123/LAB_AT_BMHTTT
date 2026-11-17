package week_02;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.table.DefaultTableModel;

final class Database {
    private record Settings(String url, String user, char[] password) {}

    private static Settings settings;

    private Database() {}

    static void configure(String server, int port, String database, String user,
                          char[] password) throws SQLException {
        if (!server.matches("[A-Za-z0-9.-]+") || !database.matches("[A-Za-z0-9_]+")
                || port < 1 || port > 65535 || user.isBlank()) {
            throw new IllegalArgumentException("Kiểm tra lại Server, Port, Database và User.");
        }
        String url = "jdbc:sqlserver://" + server + ":" + port
                + ";databaseName=" + database
                + ";encrypt=true;trustServerCertificate=true;loginTimeout=5";

        Settings candidate = new Settings(url, user, password.clone());
        try (Connection connection = connect(candidate)) {
            settings = candidate;
        }
    }

    private static Connection connect(Settings config) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", config.user());
        properties.setProperty("password", new String(config.password()));
        return DriverManager.getConnection(config.url(), properties);
    }

    private static Connection open() throws SQLException {
        if (settings == null) {
            throw new SQLException("Hãy bấm Test Connection trên form kết nối SQL Server.");
        }
        return connect(settings);
    }

    static void register(String user, char[] password)
            throws SQLException, GeneralSecurityException {
        if (!user.matches("[A-Za-z0-9_.]{3,40}") || password.length < 8) {
            throw new IllegalArgumentException(
                    "Username từ 3 đến 40 ký tự. Password ít nhất 8 ký tự.");
        }

        byte[] salt = Crypto.random(16);
        byte[] hash = Crypto.derive(password, salt);
        String sql = "INSERT INTO dbo.app_users(username, salt, password_hash) VALUES (?, ?, ?)";
        try (Connection connection = open();
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

    static boolean login(String user, char[] password)
            throws SQLException, GeneralSecurityException {
        String sql = "SELECT salt, password_hash FROM dbo.app_users WHERE username = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() && MessageDigest.isEqual(
                        result.getBytes("password_hash"),
                        Crypto.derive(password, result.getBytes("salt")));
            }
        }
    }

    static String log(String algorithm, String operation, String user, int in, int out) {
        if (settings == null) {
            return "Chưa kết nối SQL Server.";
        }

        String sql = """
                INSERT INTO dbo.crypto_history
                    (algorithm, operation, app_username, input_length, output_length)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, algorithm);
            statement.setString(2, operation);
            statement.setString(3, user);
            statement.setInt(4, in);
            statement.setInt(5, out);
            statement.executeUpdate();
            return "Đã ghi lịch sử.";
        } catch (SQLException ex) {
            return "Lỗi ghi lịch sử: " + ex.getMessage();
        }
    }

    static DefaultTableModel history() throws SQLException {
        String sql = """
                SELECT TOP (50) history_id, created_at, algorithm, operation,
                    app_username, input_length, output_length
                FROM dbo.crypto_history
                ORDER BY history_id DESC
                """;
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Thời gian", "Thuật toán", "Thao tác", "User", "Vào", "Ra"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                model.addRow(new Object[]{
                        result.getLong(1), result.getTimestamp(2), result.getString(3),
                        result.getString(4), result.getString(5), result.getInt(6),
                        result.getInt(7)
                });
            }
        }
        return model;
    }
}