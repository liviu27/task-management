package service;

import exceptions.business.ProjectNotFoundException;
import exceptions.technical.DatabaseConnectionException;
import models.Project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static database.MySQLConnection.DATA_SOURCE;
import static repo.ProjectRepository.PROJECT_REPOSITORY;

public enum ProjectService {
    PROJECT_SERVICE;

    public void createProject(String projectName, Double budget, String currency) {
        Project project = Project.builder()
                .name(projectName)
                .budget(budget)
                .currency(currency)
                .build();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            PROJECT_REPOSITORY.createProject(connection, project);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<Project> listAllProjects() {
        List<Project> allProjects;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            allProjects = PROJECT_REPOSITORY.listAllProjects(connection);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
        return allProjects;
    }

    public Project findProjectByID(int id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return PROJECT_REPOSITORY.findProjectByID(connection, id)
                    .orElseThrow(() -> new ProjectNotFoundException(id));
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
    }

    public List<Project> findProjectsByName(String projectName) {
        List<Project> projects;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            projects = PROJECT_REPOSITORY.findProjectsByName(connection, projectName);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
        return projects;
    }

    public List<Project> findProjectsByValue(double minValue, double maxValue) {
        List<Project> projects;
        try (Connection connection = DATA_SOURCE.getConnection()){
            projects = PROJECT_REPOSITORY.findProjectsByValue(connection, minValue, maxValue);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
        return projects;
    }


    public void deleteProjectByID(int id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            PROJECT_REPOSITORY.deleteProjectByID(connection, id);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException("Check that you are able to connect to database");
        }
    }
}
