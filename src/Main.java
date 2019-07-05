import java.sql.*;
import java.time.LocalDate;
public class Main {



    public static void main(String[] args) {
        DBConnect conexao = new DBConnect();
        Connection con = conexao.db_connect();
        Menu menu = new Menu();
        menu.displayMenu(con);
    }
}
