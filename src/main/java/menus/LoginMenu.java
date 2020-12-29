package menus;

import java.util.Locale;
import java.util.Scanner;

import static repo.AccountRepository.ACCOUNT_REPOSITORY;
import static service.AccountService.ACCOUNT_SERVICE;

public class LoginMenu implements IMenu {
    private static LoginMenu instance = null;
    private static final String TITLE = "\n=== TASK MANAGEMENT 2.0 ===";
    private static final String MAIN_MENU_OPTIONS
            = "\n1. Log in"
            + "\n2. Sign up"
            + "\n---------------"
            + "\n0. Exit";


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
                case INT_1 -> login(scanner);
                case INT_2 -> createNewUserAccount(scanner);
                case INT_0 -> closeApp();
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void login(Scanner scanner) {
        /*
        1. check for username - OK
        2. check password with DB (AES encryption) - TODO
        3. store username for future reference - OK
        4. if passed, show main menu - OK
         */
        scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine().toLowerCase().strip();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        boolean loginPassed = ACCOUNT_SERVICE.loginPassed(username, password);
        if (loginPassed == true) {
            MainMenu.getInstance().displayMenu(scanner);
        } else {
            System.out.println("Incorrect username or password. Try again! ");
        }
    }


    private void createNewUserAccount(Scanner scanner) {
        System.out.println("Insert information for the new account: ");
        scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine().toLowerCase().strip();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        // password encryption TODO
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("email: ");
        String email = scanner.nextLine();
        ACCOUNT_SERVICE.createAccount(username, password, name, email);

    }

    private void closeApp() {
        System.out.println(EXIT);
        System.exit(0);
    }
}
