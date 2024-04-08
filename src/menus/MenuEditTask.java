package menus;

import controllers.TaskController;
import models.Task;
import utils.csv.CSVWriter;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class MenuEditTask {
    private Scanner scanner;
    private TaskController taskController;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");

    public MenuEditTask(TaskController taskController) {
        this.scanner = new Scanner(System.in);
        this.taskController = taskController;
    }

    public void execute() {
        Task taskToEdit = selectTaskToEdit();
        if (taskToEdit != null) {
            editTask(taskToEdit);
        }
    }

    private Task selectTaskToEdit() {
        UUID taskUUID = promptForUUID("Enter the UUID of the task you want to edit:");
        return taskController.getTaskList().stream()
                .filter(task -> task.getId().equals(taskUUID))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Task not found.");
                    return null;
                });
    }

    private UUID promptForUUID(String prompt) {
        System.out.println(prompt);
        try {
            return UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID.");
            return null;
        }
    }

    public void editTask(Task task) {
        String title = promptForInput("Enter the new task title (leave blank to keep the current one): ");
        if (!title.isEmpty()) {
            task.setTitle(title);
        }

        String description = promptForInput("Enter the new task description (leave blank to keep the current one): ");
        if (!description.isEmpty()) {
            task.setDescription(description);
        }

        LocalDate date = promptForDate("Enter the new expiration date (DD MM YYYY, leave blank to keep the current date): ", task.getExpirationDate());
        task.setExpirationDate(date);

        TaskPriority priority = promptForEnum(TaskPriority.class, "Enter the new priority (HIGH, MEDIUM, LOW, leave blank to keep the current one): ", task.getPriority());
        task.setPriority(priority);

        TaskStatus status = promptForEnum(TaskStatus.class, "Enter the new status (PENDING, DONE, OVERDUE, leave blank to keep the current): ", task.getStatus());
        task.setStatus(status);

        Set<String> tags = promptForTags("Enter the new tags (separated by comma, leave blank to keep the current ones): ", task.getTags());
        task.setTags(tags);

        taskController.updateTask(task); // Supondo que exista um método updateTask no TaskController
    }

    private String promptForInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private LocalDate promptForDate(String prompt, LocalDate current) {
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

    private <T extends Enum<T>> T promptForEnum(Class<T> enumType, String prompt, T current) {
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

    private Set<String> promptForTags(String prompt, Set<String> current) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return current;

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}
