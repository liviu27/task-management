package menus;

import java.sql.SQLException;
import java.util.Scanner;

public interface IMenu {
    String INVALID_OPTION = "Invalid option, please choose from available ones!";
    int INT_0 = 0;
    int INT_1 = 1;
    int INT_2 = 2;
    int INT_3 = 3;
    int INT_4 = 4;
    int INT_5 = 5;
    int INT_6 = 6;
    int INT_7 = 7;
    int INT_8 = 8;
    int INT_9 = 9;
    int INT_10 = 10;
    int INT_11 = 11;
    int INT_12 = 12;
    String EXIT = "Closing Application...";


    void displayMenu(Scanner scanner) throws SQLException;

}
