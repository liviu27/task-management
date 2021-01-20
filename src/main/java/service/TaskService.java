package service;

import exceptions.technical.DatabaseConnectionException;
import models.Task;
import models.TaskCategory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static database.MySQLConnection.DATA_SOURCE;
import static repo.TaskRepository.TASK_REPOSITORY;

public enum TaskService {
    TASK_SERVICE;

    public void createTask(String taskTitle, String taskDescription, TaskCategory taskCategory, int projectID) {
        Task task = Task.builder()
                .title(taskTitle)
                .description(taskDescription)
                .category(taskCategory)
                .projectId(projectID)
                .build();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            TASK_REPOSITORY.createTask(connection, task);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public void assignTask(int taskId, int userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            TASK_REPOSITORY.assignTask(connection, taskId, userId);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public List<Task> getAllTasks() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return TASK_REPOSITORY.getAllTasks(connection);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public List<Task> getAllUnassignedTasks() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return TASK_REPOSITORY.getAllUnassignedTasks(connection);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public List<Task> getAllAssignedTasksByUser(int userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return TASK_REPOSITORY.getAllAssignedTasksByUser(connection, userId);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public List<Task> getAllAssignedTasksToUserByName(int userId, String taskTitle) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return TASK_REPOSITORY.getAllAssignedTasksToUserByName(connection, userId, taskTitle);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();

        }
    }

    public List<Task> getTasksByProjectId(int id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return TASK_REPOSITORY.getTasksByProjectId(connection, id);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public List<Task> gelAllInReviewTasks() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return TASK_REPOSITORY.getAllInReviewTasks(connection);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public void updateTimeWorkedOnTask(int taskId, int hours) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            TASK_REPOSITORY.updateTimeWorkedOnTask(connection, taskId, hours);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public void updateTaskDescription(String taskDescription, int taskId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            TASK_REPOSITORY.updateTaskDescription(connection, taskDescription, taskId);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }

    public void deleteTaskById(int id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            TASK_REPOSITORY.deleteTaskById(connection, id);
        } catch (SQLException exception) {
            throw new DatabaseConnectionException();
        }
    }
}
