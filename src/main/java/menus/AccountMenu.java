package menus;

import java.util.Scanner;

public class AccountMenu implements IMenu {
    private static AccountMenu instance = null;
    private static final String ACCOUNT_MENU_TITLE = "---/ Account information '\'---";
    private static final String ACCOUNT_MENU_OPTIONS = "1. List account information"
            + "\n2. Update account information (name & email)"
            + "\n3. Update password"
            + "\n4. Return to Main Menu";

    public static AccountMenu getInstance() {
        if (instance == null) {
            instance = new AccountMenu();
        }
        return instance;
    }

    @Override
    public void displayMenu(Scanner scanner) {
        int option;
        System.out.println(ACCOUNT_MENU_TITLE);
        do {
            System.out.println(ACCOUNT_MENU_OPTIONS);
            option = scanner.nextInt();
            switch (option) {
                case 1 -> listAccountInformation();
                case 2 -> updateAccount(scanner);
                case 3 -> updatePassword(scanner);
                case 4 -> MainMenu.getInstance().displayMenu(scanner);
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void listAccountInformation() {
        /* TODO
        1. check for loggedAccount (from repo)
        2. print information for logged in account
        3. can be an utility method (used for admin and normal account)
         */
    }

    private void updateAccount(Scanner scanner) {
        /* TODO
        1. check for loggedAccount (from repo)
        2. print information for logged in account
        3. can be an utility method (used for admin and normal account)
         */
    }

    private void updatePassword(Scanner scanner) {
        /* TODO
        1. check for loggedAccount (from repo)
        2. print information for logged in account
        3. can be an utility method (used for admin and normal account)
         */
    }
}
