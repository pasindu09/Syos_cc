package main.database;
import java.sql.Connection;

public interface DatabaseConnection {
    Connection connectDatabase();
    void closeConnection(Connection connection);
}
