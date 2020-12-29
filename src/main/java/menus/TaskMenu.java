package menus;

import java.util.Scanner;

public class TaskMenu implements IMenu {

    private static TaskMenu instance = null;

    public static TaskMenu getInstance() {
        if (instance == null) {
            instance = new TaskMenu();
        }
        return instance;
    }

    @Override
    public void displayMenu(Scanner scanner) {

    }
}
