package menus;

import java.util.Scanner;

import static service.AccountService.ACCOUNT_SERVICE;

public class MainMenu implements IMenu {


    private static final String MAIN_MENU_TITLE = "\n*** Main Menu ***";
    private static final String MAIN_MENU_OPTIONS = "1. Account information" +
            "\n2. Projects" +
            "\n3. Tasks" +
            "\n---------------" +
            "\n0. Log out";

    private static MainMenu instance = null;

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
                case INT_1 -> AccountMenu.getInstance().displayMenu(scanner);
                case INT_2 -> ProjectMenu.getInstance().displayMenu(scanner);
                case INT_3 -> {
                    if (ACCOUNT_SERVICE.getCurrentLoggedAccount().getType().equals("admin")) {
                        AdminTaskMenu.getInstance().displayMenu(scanner);
                    } else {
                        UserTaskMenu.getInstance().displayMenu(scanner);
                    }
                }
                case INT_0 -> logOut(scanner);
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void logOut(Scanner scanner) {
        LoginMenu.getInstance().displayMenu(scanner);
    }
}
