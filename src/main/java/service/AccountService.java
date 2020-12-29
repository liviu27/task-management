package service;

import exceptions.technical.DatabaseConnectionException;
import models.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static database.MySQLConnection.DATA_SOURCE;
import static repo.AccountRepository.ACCOUNT_REPOSITORY;

public enum AccountService {
    ACCOUNT_SERVICE;

    public void createAccount(String usename, String password, String name, String email) {
        Account account = Account.builder()
                .username(usename)
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

    public boolean loginPassed(String username, String password) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return ACCOUNT_REPOSITORY.loginPassed(connection, username, password);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
    }

    public List<Account> listAllAccounts() {
        List<Account> accounts;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            accounts = ACCOUNT_REPOSITORY.listAllAccounts(connection);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are connected to database!");
        }
        return accounts;
    }

    public Account listAccountInformation() {
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
