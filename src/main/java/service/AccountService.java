package service;

import exceptions.business.WrongCredentialsException;
import exceptions.technical.DatabaseConnectionException;
import models.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static database.MySQLConnection.DATA_SOURCE;
import static repo.AccountRepository.ACCOUNT_REPOSITORY;

public enum AccountService {
    ACCOUNT_SERVICE;

    private Account currentLoggedAccount;

    public Account getCurrentLoggedAccount() {
        return currentLoggedAccount;
    }

    public void createAccount(String username, String password, String name, String email) {
        Account account = Account.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .build();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            ACCOUNT_REPOSITORY.createAccount(connection, account);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void login(String username, String password) throws WrongCredentialsException {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            final Optional<Account> optionalAccount = ACCOUNT_REPOSITORY.getVerifiedAccount(connection, username, password);
            currentLoggedAccount = optionalAccount.orElseThrow();
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        } catch (NoSuchElementException exception) {
            throw new WrongCredentialsException();
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            accounts = ACCOUNT_REPOSITORY.listAllAccounts(connection);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are connected to database!");
        }
        return accounts;
    }

    public Account getAccountInformation() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return ACCOUNT_REPOSITORY.listAccountInformation(connection);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
    }

    public void updateAccountInformation(String name, String email) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            ACCOUNT_REPOSITORY.updateAccountInformation(connection, name, email);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
    }

    public void updateAccountPassword(String newPassword) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            ACCOUNT_REPOSITORY.updateAccountPassword(connection, newPassword);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
    }

    public void deleteAccount(String username) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            ACCOUNT_REPOSITORY.deleteAccount(connection, username);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
    }
}
