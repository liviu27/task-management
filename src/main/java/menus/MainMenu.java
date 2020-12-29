package menus;

import java.util.Scanner;

public class MainMenu implements IMenu {

    private static MainMenu instance = null;

    private static final String MAIN_MENU_TITLE = "\n*** Main Menu ***";
    private static final String MAIN_MENU_OPTIONS = "1. Account information" +
            "\n2. Projects" +
            "\n3. Tasks" +
            "\n4. Log out";


    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    @Override
    public void displayMenu(Scanner scanner) {
        int option;
        System.out.println(MAIN_MENU_TITLE);

        do {
            System.out.println(MAIN_MENU_OPTIONS);
            option = scanner.nextInt();
            switch (option) {
                case 1 -> AccountMenu.getInstance().displayMenu(scanner);
                case 2 -> ProjectMenu.getInstance().displayMenu(scanner);
                case 3 -> TaskMenu.getInstance().displayMenu(scanner);
                case 4 -> logOut();
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void logOut() {
        /* TODO
        1. check account type
        2. update isAdmin
        3. return to log in menu
         */

    }
}
