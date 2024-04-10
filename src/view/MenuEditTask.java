package view;

import controllers.TaskController;
import models.Task;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class MenuEditTask {
    private Scanner scanner;
    private TaskController taskController;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

    public MenuEditTask(TaskController taskController) {
        this.scanner = new Scanner(System.in);
        this.taskController = taskController;
    }

    public void execute() {
        Task taskToEdit = selectTaskToEdit();
        if (taskToEdit != null) {
            displayMenu(taskToEdit);
        }
    }

    private Task selectTaskToEdit() {
        UUID taskUUID = promptForUUID();
        return taskController.getTaskList().stream()
                .filter(task -> task.getId().equals(taskUUID))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Task not found.");
                    return null;
                });
    }

    private UUID promptForUUID() {
        System.out.print("Enter the ID of the task you want to edit: ");
        try {
            return UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID.");
            return null;
        }
    }

    public void displayMenu(Task task) {
        while (true) {
            System.out.println(task);
            System.out.println("\nTASK EDITION MENU");
            System.out.println("1. Edit title");
            System.out.println("2. Edit description");
            System.out.println("3. Edit expiration date");
            System.out.println("4. Edit priority");
            System.out.println("5. Edit status");
            System.out.println("6. Exit to main menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    editTitle(task);
                    break;
                case "2":
                    editDescription(task);
                    break;
                case "3":
                    editDate(task);
                    break;
                case "4":
                    editPriority(task);
                    break;
                case "5":
                    editStatus(task);
                    break;
                case "6":
                    MainMenu mainMenu = new MainMenu(taskController);
                    mainMenu.displayMenu();
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void editTitle(Task task){
        String oldTitle = task.getTitle();
        taskController.editTitle(task, promptForInput(
                "Enter the new task title (leave blank to keep the current one): ", oldTitle));
        System.out.println("\nTitle edited from: " + oldTitle);
        System.out.println("To: " + task.getTitle());
    }

    private void editDescription(Task task){
        String oldDescription = task.getDescription();
        taskController.editDescription(task, promptForInput(
                "Enter the new task description (leave blank to keep the current one): ", oldDescription));
        System.out.println("\nDescription edited from: " + oldDescription);
        System.out.println("To: " + task.getDescription());
    }

    private void editDate(Task task) {
        LocalDate oldDate = task.getExpirationDate();
        taskController.editDate(task, promptForDate(
                "Enter the new expiration date (DD MM YYYY, leave blank to keep the current date): ", oldDate));
        System.out.println("\nDate changed from: " + oldDate.format(dateFormatter));
        System.out.println("To: " + task.getExpirationDate().format(dateFormatter));
    }

    private void editPriority(Task task) {
        TaskPriority oldPriority = task.getPriority();
        taskController.editPriority(task, promptForEnum(TaskPriority.class,
                "Enter the new priority (HIGH, MEDIUM, LOW, leave blank to keep the current one): ", oldPriority));
        System.out.println("Priority changed from " + oldPriority);
        System.out.println("To: " + task.getPriority());
    }

    private void editStatus(Task task){
        TaskStatus oldStatus = task.getStatus();
        taskController.alterTaskStatus(task);
        System.out.println("Status changed from " + oldStatus);
        System.out.println("To: " + task.getStatus());
    }

    public String promptForInput(String prompt, String currentValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()){
            return currentValue;
        }

        return input;
    }

    public LocalDate promptForDate(String prompt, LocalDate current) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return current;

        try {
            return LocalDate.parse(input, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Keeping the old one.");
            return current;
        }
    }

    public <T extends Enum<T>> T promptForEnum(Class<T> enumType, String prompt, T current) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.isEmpty()) return current;

        try {
            return Enum.valueOf(enumType, input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input. Maintaining previous " + enumType.getSimpleName() + ".");
            return current;
        }
    }
}
