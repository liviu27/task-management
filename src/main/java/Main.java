import menus.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginMenu.getInstance().displayMenu(scanner);
        scanner.close();
    }
}
