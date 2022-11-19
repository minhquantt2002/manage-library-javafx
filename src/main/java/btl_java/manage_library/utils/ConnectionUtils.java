package btl_java.manage_library.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    public Connection connectDB() {
        Connection connection = null;
        try {
//            System.out.println("Connecting database ...");
            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://quan-test-1-instance-1.c5libzzugtfw.ap-southeast-1.rds.amazonaws.com/library", "quannguyen02", "Minhquan223");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Connection : " + ex.getMessage());
        }
        return connection;
    }
}
