package main.database;
import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import main.database.DatabaseConnection;


public class MySqlConnection implements DatabaseConnection {
    private HikariDataSource dataSource;

    public MySqlConnection() {
        initializeDataSource();
    }

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/cleancode");
        config.setUsername("root");
        config.setPassword("");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

      
        config.setMaximumPoolSize(30); 
        config.setMinimumIdle(5); 

      
        config.setConnectionTimeout(30000); 
        config.setIdleTimeout(600000); 
        config.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection connectDatabase() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("<!--------There is an issue connecting to the database!------------->");
            return null;
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close(); 
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the connection properly.");
        }
    }
} 