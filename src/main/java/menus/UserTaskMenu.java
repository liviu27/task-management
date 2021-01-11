package menus;

import exceptions.technical.WrongInputException;
import models.Task;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.Scanner;

import static service.AccountService.ACCOUNT_SERVICE;
import static service.TaskService.TASK_SERVICE;

public class UserTaskMenu implements IMenu {

    private static final String USER_TASK_MENU_TITLE = "\n---/ Tasks Menu - USER\\---";
    private static final String USER_TASK_MENU_OPTIONS
            = "\n1. Accept task"
            + "\n2. List accepted tasks"
            + "\n3. List all accepted task by name"
            + "\n4. List all accepted task by project"
            + "\n5. List all accepted task by status"
            + "\n6. List all accepted task by category"
            + "\n7. Update task status"
            + "\n8. Register worked time"
            + "\n-------------------------------------"
            + "\n0. Return to previous menu";

    //Thread safe singleton - double-checked locking implementation
    private volatile static UserTaskMenu instance;

    public static UserTaskMenu getInstance() {
        if (instance == null) {
            synchronized (UserTaskMenu.class) {
                if (instance == null) {
                    instance = new UserTaskMenu();
                }
            }
        }
        return instance;
    }


    @Override
    public void displayMenu(Scanner scanner) {
        int option;
        System.out.println(USER_TASK_MENU_TITLE);
        do {
            System.out.println(USER_TASK_MENU_OPTIONS);
            option = scanner.nextInt();
            switch (option) {
                case INT_1 -> acceptTask(scanner);
                case INT_2 -> listAssignedTasks();
                case INT_3 -> listAssignedTasksByName(scanner);
//                case INT_4 -> listAcceptedTasksByProjectId(scanner);
//                case INT_5 -> listAcceptedTasksByStatus(scanner);
//                case INT_6 -> listAcceptedTasksByCategory(scanner);
//                case INT_7 -> updateTaskStatus(scanner);
                case INT_8 -> registerWorkedTime(scanner);
                case INT_0 -> MainMenu.getInstance().displayMenu(scanner);
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void acceptTask(Scanner scanner) {
        int userId = ACCOUNT_SERVICE.getCurrentLoggedAccount().getId();
        List<Task> unassignedTasks = TASK_SERVICE.getAllUnassignedTasks();
        if (unassignedTasks.isEmpty()) {
            System.out.println("No available task to assign");
        } else {
            System.out.println("Available task to accept:");
            unassignedTasks.forEach(System.out::println);
            System.out.print("Choose task to accept (enter ID): ");
            final int taskId = scanner.nextInt();
            TASK_SERVICE.assignTask(taskId, userId);
        }
    }

    private void listAssignedTasks() {
        int userId = ACCOUNT_SERVICE.getCurrentLoggedAccount().getId();
        List<Task> assignedTasks = TASK_SERVICE.getAllAssignedTasksByUser(userId);
        if (assignedTasks.isEmpty()) {
            System.out.println("\nNo assigned tasks at the moment.");
        } else {
            System.out.println("Assigned tasks:");
            assignedTasks.forEach(System.out::println);
        }
    }

    private void listAssignedTasksByName(Scanner scanner) {
        int userId = ACCOUNT_SERVICE.getCurrentLoggedAccount().getId();
        System.out.println("Input search parameters:");
        scanner.nextLine();
        System.out.print("Task name/title or part of it = ");
        String taskTitle = scanner.nextLine();
        List<Task> assignedTasksByName = TASK_SERVICE.getAllAssignedTasksToUserByName(userId, taskTitle);
        if (assignedTasksByName.isEmpty()) {
            System.out.println("\nNo task meets your search parameters. Please try again!");
        } else {
            System.out.println("Tasks accepted that meets your search parameters:");
            assignedTasksByName.forEach(System.out::println);
        }

    }

    private void registerWorkedTime(Scanner scanner) {
        System.out.println("\nChoose task to update Worked_Time from the list below:");
        listAssignedTasks();
        System.out.print("Task ID = ");
        final int taskId = scanner.nextInt();
        System.out.print("Enter the number of hours to be added to Worked_Time = ");
        final int hours = scanner.nextInt();
        TASK_SERVICE.updateTimeWorkedOnTask(taskId, hours);
    }
}
