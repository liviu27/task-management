package service;

import exceptions.business.ProjectNotFoundException;
import exceptions.technical.DatabaseConnectionException;
import models.Project;

import java.sql.Connection;
import java.sql.SQLException;
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

    public List<Project> getAllProjects() {
        List<Project> allProjects;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            allProjects = PROJECT_REPOSITORY.getAllProjects(connection);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
        return allProjects;
    }

    public Project getProjectByID(int id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return PROJECT_REPOSITORY.getProjectByID(connection, id)
                    .orElseThrow(() -> new ProjectNotFoundException(id));
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public List<Project> getProjectsByName(String projectName) {
        List<Project> projects;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            projects = PROJECT_REPOSITORY.getProjectsByName(connection, projectName);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
        return projects;
    }

    public List<Project> getProjectsWithinBudgetRange(double minValue, double maxValue) {
        List<Project> projects;
        try (Connection connection = DATA_SOURCE.getConnection()){
            projects = PROJECT_REPOSITORY.getProjectsWithinBudgetRange(connection, minValue, maxValue);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
        return projects;
    }

    public void deleteProjectByID(int id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            PROJECT_REPOSITORY.deleteProjectById(connection, id);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }
}
