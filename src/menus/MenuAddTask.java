package menus;

import controllers.TaskController;
import utils.csv.CSVReader;
import utils.csv.CSVWriter;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MenuAddTask {
    private Scanner scanner;
    private TaskController taskController;

    public MenuAddTask(TaskController taskController) {
        this.scanner = new Scanner(System.in);
        this.taskController = taskController;
    }

    private TaskPriority getValidPriority() {
        while (true) {
            System.out.print("Digite a prioridade (HIGH, MEDIUM, LOW): ");
            try {
                String input = scanner.nextLine().toUpperCase();
                return TaskPriority.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridade inválida, tente novamente.");
            }
        }
    }

    private TaskStatus getValidStatus() {
        while (true) {
            System.out.print("Digite o status (PENDING, DONE, OVERDUE): ");
            try {
                String input = scanner.nextLine().toUpperCase();
                return TaskStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Status inválido, tente novamente.");
            }
        }
    }

    public void adicionarTarefa() {
        System.out.print("Digite o título da tarefa: ");
        String title = scanner.nextLine();

        System.out.print("Digite a descrição da tarefa: ");
        String description = scanner.nextLine();

        LocalDate expirationDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy"); // Define o padrão para o formato de data
        while (expirationDate == null) {
            System.out.print("Digite a data de validade (formato DD MM AAAA): "); // Mudança na mensagem para corresponder ao formato
            String dateInput = scanner.nextLine();
            try {
                expirationDate = LocalDate.parse(dateInput, formatter); // Usa o formatter para analisar a data
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida, tente novamente.");
            }
        }

        TaskPriority priority = getValidPriority();
        TaskStatus status = getValidStatus();

        Set<String> tags = new HashSet<>();
        System.out.print("Digite as tags, separadas por vírgula: ");
        String tagsInput = scanner.nextLine();
        if (!tagsInput.isEmpty()) {
            String[] tagsArray = tagsInput.split(",");
            for (String tag : tagsArray) {
                tags.add(tag.trim());
            }
        }

        taskController.addNewTask(title, description, expirationDate, priority, status, tags.toArray(new String[0]));
        System.out.println("Nova tarefa adicionada com sucesso.");
    }
}
