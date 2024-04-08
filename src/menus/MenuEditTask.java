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
            // Prossiga com a edição da tarefa
            editTask(taskToEdit);
        }
    }

    private Task selectTaskToEdit() {
        System.out.println("Digite o UUID da tarefa que deseja editar:");
        String uuidInput = scanner.nextLine();
        try {
            UUID taskUUID = UUID.fromString(uuidInput);
            for (Task task : taskController.getTaskList()) {
                if (task.getId().equals(taskUUID)) {
                    return task;
                }
            }
            System.out.println("Tarefa não encontrada.");
        } catch (IllegalArgumentException e) {
            System.out.println("UUID inválido.");
        }
        return null;
    }

    public void editTask(Task task) {
        System.out.println("Editando tarefa: " + task.getTitle());

        System.out.print("Digite o novo título da tarefa (deixe em branco para manter o atual): ");
        String title = scanner.nextLine();
        if (!title.isBlank()) {
            task.setTitle(title);
        }

        System.out.print("Digite a nova descrição da tarefa (deixe em branco para manter a atual): ");
        String description = scanner.nextLine();
        if (!description.isBlank()) {
            task.setDescription(description);
        }

        System.out.print("Digite a nova data de validade (DD MM AAAA, deixe em branco para manter a atual): ");
        String dateInput = scanner.nextLine();
        if (!dateInput.isBlank()) {
            try {
                LocalDate newDate = LocalDate.parse(dateInput, dateFormatter);
                task.setExpirationDate(newDate);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Mantendo a data anterior.");
            }
        }

        System.out.print("Digite a nova prioridade (HIGH, MEDIUM, LOW, deixe em branco para manter a atual): ");
        String priorityInput = scanner.nextLine();
        if (!priorityInput.isBlank()) {
            try {
                TaskPriority newPriority = TaskPriority.valueOf(priorityInput.toUpperCase());
                task.setPriority(newPriority);
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridade inválida. Mantendo a prioridade anterior.");
            }
        }

        System.out.print("Digite o novo status (PENDING, DONE, OVERDUE, deixe em branco para manter o atual): ");
        String statusInput = scanner.nextLine();
        if (!statusInput.isBlank()) {
            try {
                TaskStatus newStatus = TaskStatus.valueOf(statusInput.toUpperCase());
                task.setStatus(newStatus);
            } catch (IllegalArgumentException e) {
                System.out.println("Status inválido. Mantendo o status anterior.");
            }
        }

        System.out.print("Digite as novas tags (separadas por vírgula, deixe em branco para manter as atuais): ");
        String tagsInput = scanner.nextLine();
        if (!tagsInput.isBlank()) {
            Set<String> newTags = new HashSet<>(Arrays.asList(tagsInput.split(",")));
            task.setTags(newTags.stream().map(String::trim).collect(Collectors.toSet()));
        }
        CSVWriter.writeTasks(taskController.getTaskList());
    }
}
