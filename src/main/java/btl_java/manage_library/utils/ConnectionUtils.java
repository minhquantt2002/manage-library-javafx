package btl_java.manage_library.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    public Connection connection;

    public void connectDB() {
        try {
            System.out.println("Connecting database ...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Connection : " + ex.getMessage());
            this.connection = null;
        }
    }
}
