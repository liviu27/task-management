package menus;

import java.util.Scanner;

public class LoginMenu implements IMenu {
    private static LoginMenu instance = null;
    private static final String TITLE = "\n=== TASK MANAGEMENT 2.0 ===";
    private static final String MAIN_MENU_OPTIONS
            = "\n1. Log in"
            + "\n2. Sign up"
            + "\n3. Exit";


    public static LoginMenu getInstance() {
        if (instance == null) {
            instance = new LoginMenu();
        }
        return instance;
    }

    @Override
    public void displayMenu(Scanner scanner) {
        int option;
        System.out.println(TITLE);
        do {
            System.out.println(MAIN_MENU_OPTIONS);
            option = scanner.nextInt();
            switch (option) {
                case 1 -> login(scanner);
                case 2 -> createNewUserAccount(scanner);
                case 3 -> closeApp();
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void login(Scanner scanner) {
        /*
        1. check for username TODO
        2. check password with DB (AES encryption)
        3. store username for future reference
        4. update isAdmin variable
        5. if passed, show main menu
         */
        MainMenu.getInstance().displayMenu(scanner);
    }


    private void createNewUserAccount(Scanner scanner) {
        /*
        - Create user account with encrypted password
        - use AES(CBC) encryption
         */
    }

    private void closeApp() {
        System.out.println(EXIT);
        System.exit(0);
    }
}
