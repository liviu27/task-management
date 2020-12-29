package menus;

import exceptions.business.CurrentUserNotAdminException;
import models.Account;

import java.util.List;
import java.util.Scanner;

import static repo.AccountRepository.ACCOUNT_REPOSITORY;
import static service.AccountService.ACCOUNT_SERVICE;

public class AccountMenu implements IMenu {
    private static AccountMenu instance = null;
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
//                case INT_6 -> deleteAccount(scanner);
                case INT_0 -> MainMenu.getInstance().displayMenu(scanner);
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void listAccountInformation() {
        System.out.println(ACCOUNT_SERVICE.listAccountInformation());
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

    private void listAllAccounts(Scanner scanner) {
        Account loggedAccount = ACCOUNT_REPOSITORY.getCurrentLoggedAccount();
        if(loggedAccount.getType().equals("admin")) {
            List<Account> accounts = ACCOUNT_SERVICE.listAllAccounts();
            accounts.forEach(System.out::println);
        } else {
            throw new CurrentUserNotAdminException(loggedAccount.getUsername());
        }
    }
}
