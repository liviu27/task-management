import menus.LoginMenu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static database.MySQLConnection.DATA_SOURCE;

public class Main {
    public static void main(String[] args) throws SQLException {
        final Connection connection = DATA_SOURCE.getConnection();

        Scanner scanner = new Scanner(System.in);
        LoginMenu.getInstance().displayMenu(scanner);
        scanner.close();
    }
}
