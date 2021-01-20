package menus;

import jdk.swing.interop.SwingInterOpUtils;
import models.Account;
import models.Project;
import models.Task;
import models.TaskCategory;

import java.util.List;
import java.util.Scanner;

import static service.AccountService.ACCOUNT_SERVICE;
import static service.ProjectService.PROJECT_SERVICE;
import static service.TaskService.TASK_SERVICE;

public class AdminTaskMenu implements IMenu {

    private static final String ADMIN_TASK_MENU_TITLE = "\n---/ Tasks Menu - ADMIN\\---";
    private static final String ADMIN_TASK_MENU_OPTIONS
            = "\n1. Create task"
            + "\n2. Assign task"
            + "\n3. List all tasks"
            + "\n4. List in-review tasks"
            + "\n5. List all tasks by project"
            + "\n6. List all tasks by category"
            + "\n7. Update task description"
            + "\n8. Update task status"
            + "\n9. Delete task"
            + "\n-----------------------------"
            + "\n0. Return to Main Menu";

    //Thread safe singleton - double-checked locking implementation
    private volatile static AdminTaskMenu instance;

    public static AdminTaskMenu getInstance() {
        if (instance == null) {
            synchronized (AdminTaskMenu.class) {
                if (instance == null) {
                    instance = new AdminTaskMenu();
                }
            }
        }
        return instance;
    }


    @Override
    public void displayMenu(Scanner scanner) {
        int option;
        System.out.println(ADMIN_TASK_MENU_TITLE);
        do {
            System.out.println(ADMIN_TASK_MENU_OPTIONS);
            option = scanner.nextInt();
            switch (option) {
                case INT_1 -> createTask(scanner);
                case INT_2 -> assignTask(scanner);
                case INT_3 -> listAllTasks();
                case INT_4 -> listInReviewTasks(scanner);
                case INT_5 -> listAllTasksByProject(scanner);
//                case INT_6 -> listAllTasksByCategory(scanner);
                case INT_7 -> updateTaskDescription(scanner);
//                case INT_8 -> updateTaskStatus(scanner);
                case INT_9 -> deleteTask(scanner);
                case INT_0 -> MainMenu.getInstance().displayMenu(scanner);
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void listInReviewTasks(Scanner scanner) {
        System.out.println("All IN_REVIEW tasks: ");
        final List<Task> allInReviewTasks = TASK_SERVICE.gelAllInReviewTasks();
        if (allInReviewTasks.isEmpty()) {
            System.out.println("No IN_REVIEW tasks, be patient!");
        } else {
            allInReviewTasks.forEach(System.out::println);
        }
    }

    private void createTask(Scanner scanner) {
        System.out.println("Enter task information:");
        scanner.nextLine();

        System.out.print("Title: ");
        String taskTitle = scanner.nextLine();

        System.out.print("Description: ");
        String taskDescription = scanner.nextLine();

        TaskCategory taskCategory = null;
        boolean isGoodCategory = false;
        while (!isGoodCategory) {
            System.out.println("Choose category: ADMINISTRATIVE, TECHNICAL, FINANCIAL, BUSINESS, OTHER:");
            final String userInput = scanner.next();
            try {
                taskCategory = TaskCategory.valueOf(userInput.toUpperCase());
                isGoodCategory = true;
            } catch (IllegalArgumentException exception) {
                System.out.println("No such category. Returning to Task Menu");
            }
        }

        System.out.println("Assign task to project: ");
        List<Project> allProjects = PROJECT_SERVICE.getAllProjects();
        allProjects.forEach(System.out::println);
        System.out.print("Select project from above list by ID: ");
        int projectId = scanner.nextInt();

        TASK_SERVICE.createTask(taskTitle, taskDescription, taskCategory, projectId);
    }

    private void assignTask(Scanner scanner) {
        List<Task> taskList = TASK_SERVICE.getAllTasks();
        List<Account> accountList = ACCOUNT_SERVICE.getAllAccounts();
        if (taskList.isEmpty()) {
            System.out.println("No task to assign. Create a task first!");
        } else if (accountList.isEmpty()) {
            System.out.println("No registered users. Create a user first!");
        } else {
            taskList.forEach(System.out::println);
            System.out.print("Enter task ID: ");
            int taskId = scanner.nextInt();

            System.out.println("Select user to assign task by entering user ID: ");
            accountList.forEach(System.out::println);
            System.out.print("Enter user ID: ");
            int userId = scanner.nextInt();

            TASK_SERVICE.assignTask(taskId, userId);
        }
    }

    private void listAllTasks() {
        List<Task> allTasks = TASK_SERVICE.getAllTasks();
        if (allTasks.isEmpty()) {
            System.out.println("There are no registered tasks!");
        } else {
            allTasks.forEach(System.out::println);
        }
    }

    private void listAllTasksByProject(Scanner scanner) {
        System.out.println("Select project:");
        List<Project> allProjects = PROJECT_SERVICE.getAllProjects();
        allProjects.forEach(System.out::println);

        System.out.println("ID: ");
        int id = scanner.nextInt();

        List<Task> tasksByProjectId = TASK_SERVICE.getTasksByProjectId(id);
        if (!tasksByProjectId.isEmpty()) {
            tasksByProjectId.forEach(System.out::println);
        } else {
            System.out.println("Project " + id + " has no tasks. Try a different project!");
        }
    }

    private void updateTaskDescription(Scanner scanner) {
        listAllTasks();
        scanner.nextLine();
        System.out.print("Choose task from the above list to update it's description, or type 'cancel' to exit: ");
        final String userInput = scanner.nextLine();
        if (userInput.toLowerCase().equals(CANCEL_OPERATION)) {
            displayMenu(scanner);
        }
        final int taskId = Integer.parseInt(userInput);
        System.out.print("input new description: ");
        final String newDescription = scanner.nextLine();
        TASK_SERVICE.updateTaskDescription(newDescription, taskId);
    }

    private void deleteTask(Scanner scanner) {
        System.out.println("Delete task by entering it's ID:");
        listAllTasks();
        scanner.nextLine();
        System.out.print("Choose ID: ");
        int id = scanner.nextInt();
        TASK_SERVICE.deleteTaskById(id);
    }
}
