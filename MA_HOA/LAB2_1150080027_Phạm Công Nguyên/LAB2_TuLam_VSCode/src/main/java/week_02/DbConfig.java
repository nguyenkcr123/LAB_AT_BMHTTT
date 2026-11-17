package week_02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DbConfig {
    private record Settings(String url, String user, char[] password) {}

    private static Settings settings;

    private DbConfig() {}

    public static void configure(String server, int port, String database,
                                 String user, char[] password) throws SQLException {
        if (!server.matches("[A-Za-z0-9.-]+") || !database.matches("[A-Za-z0-9_]+")
                || user.isBlank() || port < 1 || port > 65535) {
            throw new IllegalArgumentException("Kiểm tra Server, Port, Database và User.");
        }

        String url = "jdbc:sqlserver://" + server + ":" + port
                + ";databaseName=" + database
                + ";encrypt=true;trustServerCertificate=true;loginTimeout=5";
        Settings candidate = new Settings(url, user, password.clone());

        try (Connection connection = connect(candidate)) {
            settings = candidate;
        }
    }

    public static boolean isReady() {
        return settings != null;
    }

    public static Connection open() throws SQLException {
        if (!isReady()) {
            throw new SQLException("Hãy bấm Test Connection trong cửa sổ kết nối SQL.");
        }
        return connect(settings);
    }

    private static Connection connect(Settings config) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", config.user());
        properties.setProperty("password", new String(config.password()));
        return DriverManager.getConnection(config.url(), properties);
    }
}