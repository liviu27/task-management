package repo;

import models.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ProjectRepository {
    PROJECT_REPOSITORY;

    private static final String WILDCARD = "%";
    private static final String CREATE_PROJECT = "INSERT into projects (name, budget, budget_currency) VALUES (?, ?, ?)";
    private static final String LIST_ALL_PROJECTS = "SELECT * FROM projects";
    private static final String LIST_PROJECT_BY_ID = "SELECT * FROM projects WHERE id = ?";
    private static final String LIST_PROJECTS_BY_NAME = "SELECT * FROM projects WHERE name LIKE ?";
    private static final String DELETE_PROJECT_BY_ID = "DELETE FROM projects WHERE id = ?";
    private static final String LIST_PROJECTS_BY_VALUE = "SELECT * FROM projects WHERE budget BETWEEN ? AND ?";

    public void createProject(Connection connection, Project project) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PROJECT)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setDouble(2, project.getBudget());
            preparedStatement.setString(3, project.getCurrency());
            preparedStatement.executeUpdate();
        }

    }

    public List<Project> listAllProjects(Connection connection) throws SQLException {
        List<Project> allProjects = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(LIST_ALL_PROJECTS);
            while (resultSet.next()) {
                allProjects.add(mapRowToProject(resultSet));
            }
        }
        return allProjects;
    }

    public Optional<Project> findProjectByID(Connection connection, int id) throws SQLException {
        Project project = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_PROJECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                project = mapRowToProject(resultSet);
            }
        }
        return Optional.ofNullable(project);
    }

    public List<Project> findProjectsByName(Connection connection, String projectName) throws SQLException {
        List<Project> projects = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_PROJECTS_BY_NAME)) {
            preparedStatement.setString(1, WILDCARD + projectName + WILDCARD);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(mapRowToProject(resultSet));
            }
        }
        return projects;
    }

    public List<Project> findProjectsByValue(Connection connection, double minValue, double maxValue) throws SQLException {
        List<Project> projects = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_PROJECTS_BY_VALUE)) {
            preparedStatement.setDouble(1, minValue);
            preparedStatement.setDouble(2, maxValue);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(mapRowToProject(resultSet));
            }
            return projects;
        }
    }

    public void deleteProjectByID(Connection connection, int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }


    private Project mapRowToProject(ResultSet resultSet) throws SQLException {
        final int projectID = resultSet.getInt(1);
        final String projectName = resultSet.getString(2);
        final double projectBudget = resultSet.getDouble(3);
        final String projectCurrency = resultSet.getString(4);
        return Project.builder()
                .id(projectID)
                .name(projectName)
                .budget(projectBudget)
                .currency(projectCurrency)
                .build();


    }


}
