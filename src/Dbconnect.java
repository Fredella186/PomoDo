import java.sql.*;

public class Dbconnect {

    private static final String HOST = "";
    private static final int PORT = 0;
    private static final String DB_NAME = "";
    private static final String USERNAME = "";
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