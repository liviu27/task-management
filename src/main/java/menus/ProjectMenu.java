package menus;

import models.Project;

import java.util.List;
import java.util.Scanner;

import static service.ProjectService.PROJECT_SERVICE;

public class ProjectMenu implements IMenu {

    private static final String PROJECT_MENU_TITLE = "\n---/ Project Menu \\---";
    private static final String PROJECT_MENU_OPTIONS
            = "\n1. Create new project"
            + "\n2. List all projects"
            + "\n3. Search project by ID"
            + "\n4. Search project by name"
            + "\n5. Search project by value"
            + "\n6. Delete project"
            + "\n------------------"
            + "\n0. Return to Main Menu";

    //Thread safe singleton - double-checked locking implementation
    private volatile static ProjectMenu instance;
    public static ProjectMenu getInstance() {
        if (instance == null) {
            synchronized (ProjectMenu.class) {
                if (instance == null) {
                    instance = new ProjectMenu();
                }
            }
        }
        return instance;
    }


    @Override
    public void displayMenu(Scanner scanner) {
        int option;
        System.out.println(PROJECT_MENU_TITLE);
        do {
            System.out.println(PROJECT_MENU_OPTIONS);
            option = scanner.nextInt();
            switch (option) {
                case INT_1 -> createProject(scanner);
                case INT_2 -> listAllProjects();
                case INT_3 -> listProjectByID(scanner);
                case INT_4 -> listProjectsByName(scanner);
                case INT_5 -> listProjectsWithinBudgetRange(scanner);
                case INT_6 -> deleteProject(scanner);
                case INT_0 -> MainMenu.getInstance().displayMenu(scanner);
                default -> System.out.println(INVALID_OPTION);
            }
        } while (true);
    }

    private void createProject(Scanner scanner) {
        System.out.println("Input project details:");
        scanner.nextLine();
        System.out.print("Project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Currency: ");
        String currency = scanner.nextLine();
        PROJECT_SERVICE.createProject(projectName, budget, currency);
    }

    private void listAllProjects() {
        List<Project> allProjects = PROJECT_SERVICE.getAllProjects();
        allProjects.forEach(System.out::println);
    }

    private void listProjectByID(Scanner scanner) {
        System.out.println("Input search parameters:"
                + "\nID = ");
        int id = scanner.nextInt();
        System.out.println(PROJECT_SERVICE.getProjectByID(id));
    }

    private void listProjectsByName(Scanner scanner) {
        System.out.println("Input search parameters:");
        scanner.nextLine();
        System.out.print("Project name or part of it = ");
        String projectName = scanner.nextLine();
        List<Project> projectsByName = PROJECT_SERVICE.getProjectsByName(projectName);
        projectsByName.forEach(System.out::println);
    }

    private void listProjectsWithinBudgetRange(Scanner scanner) {
        System.out.println("Input search parameters:");
        scanner.nextLine();
        System.out.print("Min. value = ");
        double minValue = scanner.nextDouble();
        System.out.print("Max. value = ");
        double maxValue = scanner.nextDouble();
        List<Project> projectsByValue = PROJECT_SERVICE.getProjectsWithinBudgetRange(minValue, maxValue);
        projectsByValue.forEach(System.out::println);
    }

    private void deleteProject(Scanner scanner) {
        System.out.println("Delete project by entering it's ID: ");
        listAllProjects();
        scanner.nextLine();
        System.out.print("ID = ");
        int id = scanner.nextInt();
        PROJECT_SERVICE.deleteProjectByID(id);
    }
}
