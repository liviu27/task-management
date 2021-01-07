package menus;

import exceptions.business.CurrentUserNotAdminException;
import models.Account;

import java.util.List;
import java.util.Scanner;

import static service.AccountService.ACCOUNT_SERVICE;

public class AccountMenu implements IMenu {
    private static final String ACCOUNT_MENU_TITLE = "\n---/ Account information \\---";
    private static final String ACCOUNT_MENU_OPTIONS
            = "\n1. List account information"
            + "\n2. Update account information (name & email)"
            + "\n3. Update password"
            + "\n----------------- (admin only)"
            + "\n4. List all user accounts"
            + "\n5. List all user accounts by project"
            + "\n6. Delete user account"
            + "\n-----------------"
            + "\n0. Return to Main Menu";

    //Lazy singleton implementation
    private static AccountMenu instance = null;

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
                case INT_1 -> listAccountInformation();
                case INT_2 -> updateAccount(scanner);
                case INT_3 -> updatePassword(scanner);
                case INT_4 -> listAllAccounts(scanner);
//                case INT_5 -> listAllAccountsByProject(scanner);
                case INT_6 -> deleteAccount(scanner);
                case INT_0 -> MainMenu.getInstance().displayMenu(scanner);
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void listAccountInformation() {
        System.out.println(ACCOUNT_SERVICE.getAccountInformation());
    }

    private void updateAccount(Scanner scanner) {
        System.out.println("Enter new information:");
        scanner.nextLine();
        System.out.print("Name: ");
        String updatedName = scanner.nextLine();
        System.out.print("e-mail: ");
        String updatedEmail = scanner.nextLine();
        ACCOUNT_SERVICE.updateAccountInformation(updatedName, updatedEmail);
    }

    private void updatePassword(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter new password:");
        String newPassword = scanner.nextLine();
        ACCOUNT_SERVICE.updateAccountPassword(newPassword);
    }

    private void listAllAccounts(Scanner scanner) {
        Account loggedAccount = ACCOUNT_SERVICE.getCurrentLoggedAccount();
        if (loggedAccount.getType().equals("admin")) {
            List<Account> accounts = ACCOUNT_SERVICE.getAllAccounts();
            accounts.forEach(System.out::println);
        } else {
           try {
               throw new CurrentUserNotAdminException(loggedAccount.getUsername());
           } catch (CurrentUserNotAdminException ex) {
               System.out.println(ex.getMessage());
               displayMenu(scanner);
           }
        }
    }

    private void deleteAccount(Scanner scanner) {
        Account loggedAccount = ACCOUNT_SERVICE.getCurrentLoggedAccount();
        if (loggedAccount.getType().equals("admin")) {
            scanner.nextLine();
            System.out.print("Enter account username: ");
            String username = scanner.nextLine();
            System.out.println("Are you sure? (yes/no)");
            String answer = scanner.nextLine();
            if (answer.equals("yes")) {
                ACCOUNT_SERVICE.deleteAccount(username);
            } else {
                displayMenu(scanner);
            }
        } else {
            try {
                throw new CurrentUserNotAdminException(loggedAccount.getUsername());
            } catch (CurrentUserNotAdminException ex) {
                System.out.println(ex.getMessage());
                displayMenu(scanner);
            }
        }
    }
}
