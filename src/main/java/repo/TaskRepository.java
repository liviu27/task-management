package repo;

import models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum TaskRepository {
    TASK_REPOSITORY;

    private static final String WILDCARD = "%";
    private static final String CREATE_TASK = "INSERT INTO tasks (title, description, projectID) VALUES (?, ?, ?)";
    private static final String GET_ALL_TASKS = "SELECT * FROM tasks";
    private static final String GET_TASKS_BY_PROJECT_ID = "SELECT * FROM tasks WHERE projectID = ?";
    private static final String DELETE_TASK_BY_ID = "DELETE FROM tasks WHERE id = ?";
    private static final String ASSIGN_TASK_TO_USER = "UPDATE tasks SET userID = ? WHERE id = ?";
    private static final String GET_ALL_UNASSIGNED_TASKS = "SELECT * FROM tasks WHERE userID = NULL";
    private static final String GET_ALL_ASSIGNED_TASKS_BY_USER = "SELECT * FROM tasks WHERE userID = ?";
    private static final String GET_ALL_ASSIGNED_TASKS_BY_TITLE = "SELECT * FROM tasks WHERE userID = ? AND title LIKE ?";
    private static final String GET_WORKED_TIME = "SELECT worked_hours FROM tasks WHERE id = ?";
    private static final String UPDATE_WORKED_TIME = "UPDATE tasks SET worked_hours = ? WHERE id = ?";


    public void createTask(Connection connection, Task task) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TASK)) {
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setInt(3, task.getProjectId());
            preparedStatement.executeUpdate();
        }
    }

    public void assignTask(Connection connection, int taskId, int userId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN_TASK_TO_USER)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, taskId);
            preparedStatement.executeUpdate();
        }
    }

    public List<Task> getAllTasks(Connection connection) throws SQLException {
        List<Task> allTasks = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(GET_ALL_TASKS);
            while (resultSet.next()) {
                allTasks.add(mapRowToTask(resultSet));
            }
        }
        return allTasks;
    }

    public List<Task> getAllUnassignedTasks(Connection connection) throws SQLException {
        List<Task> allUnassignedTasks = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(GET_ALL_UNASSIGNED_TASKS);
            while (resultSet.next()) {
                allUnassignedTasks.add(mapRowToTask(resultSet));
            }
        }
        return allUnassignedTasks;
    }

    public List<Task> getAllAssignedTasksByUser(Connection connection, int userId) throws SQLException {
        List<Task> assignedTasks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ASSIGNED_TASKS_BY_USER)){
            preparedStatement.setInt(1, userId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                assignedTasks.add(mapRowToTask(resultSet));
            }
        }
        return assignedTasks;
    }

    public List<Task> getAllAssignedTasksToUserByName(Connection connection, int userId, String taskTitle) throws SQLException {
        List<Task> assignedTasksByName = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ASSIGNED_TASKS_BY_TITLE)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, WILDCARD + taskTitle + WILDCARD);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                assignedTasksByName.add(mapRowToTask(resultSet));
            }
        }
        return assignedTasksByName;
    }

    public List<Task> getTasksByProjectId(Connection connection, int id) throws SQLException {
        List<Task> tasksByProjectId = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_TASKS_BY_PROJECT_ID)) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasksByProjectId.add(mapRowToTask(resultSet));
            }
        }
        return tasksByProjectId;
    }

    public void updateTimeWorkedOnTask(Connection connection, int taskId, int hours) throws SQLException {
        int workedHours = getWorkedHoursByTaskId(connection, taskId);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WORKED_TIME)) {
            preparedStatement.setInt(1, workedHours + hours);
            preparedStatement.setInt(2, taskId);
            preparedStatement.executeUpdate();
        }
    }

    public int getWorkedHoursByTaskId(Connection connection, int taskId) throws SQLException {
        int workedHours = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_WORKED_TIME)) {
            preparedStatement.setInt(1, taskId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                workedHours = resultSet.getInt(1);
            }
        }
        return workedHours;
    }

    public void deleteTaskById(Connection connection, int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Task mapRowToTask(ResultSet resultSet) throws SQLException {
        final int taskId = resultSet.getInt(1);
        final String taskTitle = resultSet.getString(2);
        final String taskDescription = resultSet.getString(3);
        final String taskStatus = resultSet.getString(4);
        final int userId = resultSet.getInt(5);
        final int projectId = resultSet.getInt(6);
        final int workedHours = resultSet.getInt(7);
        return Task.builder()
                .id(taskId)
                .title(taskTitle)
                .description(taskDescription)
                .status(taskStatus)
                .userId(userId)
                .projectId(projectId)
                .workedHours(workedHours)
                .build();
    }

}
