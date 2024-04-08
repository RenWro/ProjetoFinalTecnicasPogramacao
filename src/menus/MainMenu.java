package menus;

import controllers.TaskController;
import models.Task;
import utils.csv.CSVReader;

import java.util.Scanner;
import java.util.UUID;

public class MainMenu {
    private final Scanner scanner;
    private final TaskController taskController;

    public MainMenu(TaskController taskController) {
        this.scanner = new Scanner(System.in);
        this.taskController = taskController;
    }

    public void listTasks() {
        System.out.println("### Task List ###");
        taskController.getTaskListAsString();
    }

    public void displayMenu() {
        try {
            while (true) {
                listTasks();

                System.out.println("\nTodo List Application");
                System.out.println("1. Add Task");
                System.out.println("2. Edit Task");
                System.out.println("3. Delete Task");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        addTask();
                        break;
                    case "2":
                        editTask();
                        break;
                    case "3":
                        deleteTask();
                        break;
                    case "4":
                        System.out.println("Saindo...");
                        //taskController.shutdown();
                        return;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            }
        } finally {
            scanner.close();
            taskController.shutdown();
        }
    }

    private void addTask() {
        MenuAddTask menuAddTask = new MenuAddTask(taskController);
        menuAddTask.adicionarTarefa();
    }

    private void editTask() {
        MenuEditTask menuEditTask = new MenuEditTask(taskController);
        menuEditTask.execute();
    }

    private Task selectTaskToDelete() {
        System.out.println("Digite o UUID da tarefa que deseja deletar:");
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

    private void deleteTask() {
        Task taskToDelete = selectTaskToDelete();
        if (taskToDelete != null) {
            taskController.deleteTask(taskToDelete);
            System.out.println("Tarefa deletada com sucesso.");
        }
    }

    public static void main(String[] args) {
        TaskController taskController = new TaskController();
        CSVReader.readTasks(taskController);

        MainMenu menu = new MainMenu(taskController);
        menu.displayMenu();
    }

}
