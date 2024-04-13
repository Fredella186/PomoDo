import java.sql.*;

public class Dbconnect {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String DB_NAME = "todolist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DB_NAME),
                    USERNAME, PASSWORD);
        } catch (SQLException e) {
            // Handle the exception (e.g., log or display an error message)
            e.printStackTrace();
        }
        return connection;
    }
}
