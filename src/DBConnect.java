import java.sql.*;

public class DBConnect {



    public Connection db_connect(){
        try {
            String host = "jdbc:mysql://localhost:3306/hotel?useTimezone=true&serverTimezone=UTC";
            String uName = "root";
            String uPass = "root";
            Connection con = DriverManager.getConnection(host, uName, uPass);
            return con;
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }


}
