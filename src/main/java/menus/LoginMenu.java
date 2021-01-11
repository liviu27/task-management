package menus;

import exceptions.business.WrongCredentialsException;

import java.util.Scanner;

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
        System.out.println("Type your credentials:");
        scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine().toLowerCase().strip();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        try {
            ACCOUNT_SERVICE.getVerifiedAccount(username, password);
            MainMenu.getInstance().displayMenu(scanner);
        } catch (WrongCredentialsException ex) {
            System.out.println(ex.getMessage());
            displayMenu(scanner);
        }
    }

    private void createNewUserAccount(Scanner scanner) {
        System.out.println("Insert information for the new account: ");
        scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine().toLowerCase().strip();
        System.out.print("Password: ");
        String password = scanner.nextLine();
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
