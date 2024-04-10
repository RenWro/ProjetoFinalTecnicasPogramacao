package menus;

import controllers.TaskController;
import models.Task;
import utils.csv.CSVReader;

import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class MainMenu {
    private final Scanner scanner;
    private final TaskController taskController;

    public MainMenu(TaskController taskController) {
        this.scanner = new Scanner(System.in);
        this.taskController = taskController;
    }

    public static void main(String[] args) {
        TaskController taskController = new TaskController();
        CSVReader.readTasks(taskController);
        MainMenu menu = new MainMenu(taskController);
        menu.displayMenu();
    }

    public void listTasks() {
        System.out.println("### Task List ###");
        taskController.getTaskListAsString();
    }

    public void displayMenu() {
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
                    System.out.println("Exiting...");
                    taskController.stopScheduler();
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
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

    private void deleteTask(){
        System.out.print("Enter the UUID of the task you want to delete: ");
        UUID taskUUID = UUID.fromString(scanner.nextLine());

        taskController.getTaskList().stream()
                .filter(task -> Objects.equals(task.getId(), taskUUID))
                .findAny()
                .ifPresentOrElse(task -> {
                    taskController.deleteTask(task);
                    System.out.println("Task deleted successfully.");
                }, () -> System.out.println("Task not found."));

    }
}
