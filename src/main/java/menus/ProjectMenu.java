package menus;

import models.Project;

import java.util.List;
import java.util.Scanner;

import static service.ProjectService.PROJECT_SERVICE;

public class ProjectMenu implements IMenu {

    private static ProjectMenu instance = null;
    private static final String PROJECT_MENU_TITLE = "\n---/ Project Menu '\'---";
    private static final String PROJECT_MENU_OPTIONS
            = "\n1. Create new project"
            + "\n2. List all projects"
            + "\n3. Search project by ID"
            + "\n4. Search project by name"
            + "\n5. Search project by value"
            + "\n6. Delete project"
            + "\n------------------"
            + "\n0. Return to Main Menu";

    public static ProjectMenu getInstance() {
        if (instance == null) {
            instance = new ProjectMenu();
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
                case 1 -> createProject(scanner);
                case 2 -> listProjects();
                case 3 -> listProjectByID(scanner);
                case 4 -> listProjectsByName(scanner);
                case 5 -> listProjectsByValue(scanner);
                case 6 -> deleteProject(scanner);
                case 0 -> MainMenu.getInstance().displayMenu(scanner);
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

    private void listProjects() {
        List<Project> allProjects = PROJECT_SERVICE.listAllProjects();
        allProjects.forEach(System.out::println);
    }

    private void listProjectByID(Scanner scanner) {
        System.out.println("Input search parameters:"
                + "\nID = ");
        int id = scanner.nextInt();
        System.out.println(PROJECT_SERVICE.findProjectByID(id));
    }

    private void listProjectsByName(Scanner scanner) {
        System.out.println("Input search parameters:");
        scanner.nextLine();
        System.out.print("Project name or part of it = ");
        String projectName = scanner.nextLine();
        List<Project> projectsByName = PROJECT_SERVICE.findProjectsByName(projectName);
        projectsByName.forEach(System.out::println);
    }

    private void listProjectsByValue(Scanner scanner) {
        System.out.println("Input search parameters:");
        scanner.nextLine();
        System.out.print("Min. value = ");
        double minValue = scanner.nextDouble();
        System.out.print("Max. value = ");
        double maxValue = scanner.nextDouble();
        List<Project> projectsByValue = PROJECT_SERVICE.findProjectsByValue(minValue, maxValue);
        projectsByValue.forEach(System.out::println);
    }

    private void deleteProject(Scanner scanner) {
        System.out.println("Delete project by entering it's ID: ");
        listProjects();
        scanner.nextLine();
        System.out.print("ID = ");
        int id = scanner.nextInt();
        PROJECT_SERVICE.deleteProjectByID(id);
    }

}
