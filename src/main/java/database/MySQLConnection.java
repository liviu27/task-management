package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public enum MySQLConnection {

    DATA_SOURCE;

    private static final String DB_RL = "jdbc:mysql://localhost:3306/task_management";
    private static final String DB_ADMIN = "root";
    private static final String DB_PASSWORD = "root";

    private final HikariDataSource hikariDataSource;


    MySQLConnection() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_RL);
        config.setUsername(DB_ADMIN);
        config.setPassword(DB_PASSWORD);
        hikariDataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public void rollbackConnection(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void closeConnection(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
