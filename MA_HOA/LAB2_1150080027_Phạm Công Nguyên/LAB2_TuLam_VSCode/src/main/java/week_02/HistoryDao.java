package week_02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public final class HistoryDao {
    private HistoryDao() {}

    public static String tryInsert(String algorithm, String operation, String user,
                                   int inputLength, int outputLength) {
        if (!DbConfig.isReady()) {
            return "Chưa kết nối SQL, chưa ghi lịch sử.";
        }

        String sql = """
                INSERT INTO dbo.crypto_history
                    (algorithm, operation, app_username, input_length, output_length)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection connection = DbConfig.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, algorithm);
            statement.setString(2, operation);
            statement.setString(3, user);
            statement.setInt(4, inputLength);
            statement.setInt(5, outputLength);
            statement.executeUpdate();
            return "Đã ghi lịch sử.";
        } catch (SQLException ex) {
            return "Lỗi ghi lịch sử: " + ex.getMessage();
        }
    }

    public static DefaultTableModel emptyModel() {
        return new DefaultTableModel(
                new String[]{"ID", "Thời gian", "Thuật toán", "Thao tác",
                    "User", "Độ dài vào", "Độ dài ra"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static DefaultTableModel loadLatest() throws SQLException {
        String sql = """
                SELECT TOP (50) history_id, created_at, algorithm, operation,
                    app_username, input_length, output_length
                FROM dbo.crypto_history
                ORDER BY history_id DESC
                """;
        DefaultTableModel model = emptyModel();
        try (Connection connection = DbConfig.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                model.addRow(new Object[]{
                    result.getLong(1), result.getTimestamp(2), result.getString(3),
                    result.getString(4), result.getString(5),
                    result.getInt(6), result.getInt(7)
                });
            }
        }
        return model;
    }
}