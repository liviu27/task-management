package menus;

import java.util.Scanner;

import static repo.AccountRepository.ACCOUNT_REPOSITORY;

public class MainMenu implements IMenu {

    private static MainMenu instance = null;

    private static final String MAIN_MENU_TITLE = "\n*** Main Menu ***";
    private static final String MAIN_MENU_OPTIONS = "1. Account information" +
            "\n2. Projects" +
            "\n3. Tasks" +
            "\n---------------" +
            "\n0. Log out";


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
                case INT_3 -> TaskMenu.getInstance().displayMenu(scanner);
                case INT_0 -> logOut(scanner);
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void logOut(Scanner scanner) {
//        ACCOUNT_REPOSITORY.setCurrentLoggedAccount("");
        LoginMenu.getInstance().displayMenu(scanner);
    }
}
