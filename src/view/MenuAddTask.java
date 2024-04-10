package view;

import controllers.TaskController;
import utils.csv.CSVReader;
import utils.csv.CSVWriter;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class MenuAddTask {
    private Scanner scanner;
    private TaskController taskController;

    public MenuAddTask(TaskController taskController) {
        this.scanner = new Scanner(System.in);
        this.taskController = taskController;
    }

    public void addTask() {
        String title = promptForInput("Enter the task title: ");
        String description = promptForInput("Enter the task description: ");
        LocalDate expirationDate = promptForDate();
        TaskPriority priority = getValidEnumValue(TaskPriority.class, "Enter the priority (HIGH, MEDIUM, LOW): ");
        TaskStatus status = getValidEnumValue(TaskStatus.class, "Enter status (PENDING, DONE, OVERDUE): ");

        taskController.addNewTask(title, description, expirationDate, priority, status);
        System.out.println("New task added successfully.");

    }

    private String promptForInput(String prompt) {
        String input = null;
        while (input == null || input.trim().isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
        return input;
    }

    private LocalDate promptForDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate date = null;
        while (date == null) {
            System.out.print("Enter the expiration date (DD MM YYYY format):");
            String dateInput = scanner.nextLine();
            try {
                LocalDate parsedDate = LocalDate.parse(dateInput, formatter);
                date = parsedDate;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date, please try again.");
                date = null;
            }
        }
        return date;
    }

    private <T extends Enum<T>> T getValidEnumValue(Class<T> enumClass, String prompt) {
        T result = null;
        while (result == null) {
            System.out.print(prompt);
            try {
                result = Enum.valueOf(enumClass, scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid value, please try again.");
            }
        }
        return result;
    }
}
