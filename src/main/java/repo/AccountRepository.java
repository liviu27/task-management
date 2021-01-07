package repo;

import models.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static service.AccountService.ACCOUNT_SERVICE;

public enum AccountRepository {
    ACCOUNT_REPOSITORY;

    private static final String CREATE_ACCOUNT = "INSERT INTO accounts (username, password, name, email) "
            + "VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_ACCOUNTS = "SELECT * FROM accounts";
    private static final String GET_ACCOUNT_INFO = "SELECT * FROM accounts WHERE username= ?";
    private static final String GET_VERIFIED_ACCOUNT = "SELECT * FROM accounts WHERE username= ? AND password = ?";
    private static final String UPDATE_ACCOUNT_INFO = "UPDATE accounts SET name = ?, email = ? WHERE username = ?";
    private static final String UPDATE_PASSWORD = "UPDATE accounts SET password = ? WHERE username = ?";
    private static final String DELETE_ACCOUNT = "DELETE FROM accounts WHERE username = ?";

    public void createAccount(Connection connection, Account account) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ACCOUNT)) {
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getName());
            preparedStatement.setString(4, account.getEmail());
            preparedStatement.executeUpdate();
        }
    }

    public Optional<Account> getVerifiedAccount(Connection connection, String username, String password) throws SQLException {
        Account verifiedAccount = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_VERIFIED_ACCOUNT)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                verifiedAccount = mapRowToAccount(resultSet);
            }
        }
        return Optional.ofNullable(verifiedAccount);
    }

    public List<Account> listAllAccounts(Connection connection) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(GET_ALL_ACCOUNTS);
            while (resultSet.next()) {
                accounts.add(mapRowToAccount(resultSet));
            }
        }
        return accounts;
    }

    public Account listAccountInformation(Connection connection) throws SQLException {
        Account account = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT_INFO)) {
            preparedStatement.setString(1, ACCOUNT_SERVICE.getCurrentLoggedAccount().getUsername());
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = mapRowToAccount(resultSet);
            }
        }
        return account;
    }

    public void updateAccountInformation(Connection connection, String name, String email) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_INFO)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, ACCOUNT_SERVICE.getCurrentLoggedAccount().getUsername());
            preparedStatement.executeUpdate();
        }
    }

    public void updateAccountPassword(Connection connection, String newPassword) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, ACCOUNT_SERVICE.getCurrentLoggedAccount().getUsername());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteAccount(Connection connection, String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        }
    }

    private Account mapRowToAccount(ResultSet resultSet) throws SQLException {
        final int accountID = resultSet.getInt(1);
        final String accountUsername = resultSet.getString(2);
        final String accountName = resultSet.getString(4);
        final String accountEmail = resultSet.getString(5);
        final String accountType = resultSet.getString(6);
        return Account.builder()
                .id(accountID)
                .username(accountUsername)
                .password("************")
                .name(accountName)
                .email(accountEmail)
                .type(accountType)
                .build();
    }


}
