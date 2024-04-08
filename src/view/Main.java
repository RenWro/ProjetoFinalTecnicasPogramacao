package view;

import controllers.TaskController;
import models.Task;
import utils.csv.CSVReader;
import utils.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        TaskController taskController = new TaskController();

        // reading tasks from csv
        CSVReader.readTasks(taskController);

        // adding a few tasks
        taskController.addNewTask("Cleaning", "Tidy up bedroom", LocalDate.now().plusDays(1), TaskPriority.LOW, TaskStatus.PENDING, "home");
        taskController.addNewTask("Exam", "Study for Physics exam", LocalDate.now().plusDays(5), TaskPriority.MEDIUM, TaskStatus.PENDING, "study");

        // printing all tasks
        System.out.println("ALL TASKS:");
        taskController.getTaskListAsString();

        // defining task to be edited
        Task taskToEdit = taskController.getTaskList().get(0);

        // editing title
        String oldTitle = taskToEdit.getTitle();
        taskController.editTitle(taskToEdit, "Spreadsheet");
        System.out.println("\nTitle edited from: " + oldTitle);
        System.out.println("To: " + taskToEdit.getTitle());

        // editing description
        String oldDescription = taskToEdit.getDescription();
        taskController.editDescription(taskToEdit, "Finish changes in spreadsheet");
        System.out.println("\nDescription edited from: " + oldDescription);
        System.out.println("To: " + taskToEdit.getDescription());

        // editing date (now - 1min)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        LocalDate oldDate = taskToEdit.getExpirationDate();
        taskController.editDate(taskToEdit, LocalDate.now().minusDays(1));
        System.out.println("\nExpiration date edited from: " + oldDate.format(formatter));
        System.out.println("To: " + taskToEdit.getExpirationDate().format(formatter));

        // editing priority
        TaskPriority oldPriority = taskToEdit.getPriority();
        taskController.editPriority(taskToEdit, TaskPriority.HIGH);
        System.out.println("\nPriority edited from: " + oldPriority);
        System.out.println("To: " + taskToEdit.getPriority());

        // printing task after changes
        System.out.println("\nTask \"" + taskToEdit.getTitle() + "\" after changes: ");
        System.out.println(taskToEdit);

        // defining second task to be edited
        Task taskToEdit2 = taskController.getTaskList().get(1);
        System.out.println("\nChanging task \"" + taskToEdit2.getTitle() + "\".");

        // changing status
        TaskStatus oldStatus = taskToEdit2.getStatus();
        taskController.alterTaskStatus(taskToEdit2);
        System.out.println("\nStatus altered from: " + oldStatus);
        System.out.println("To: " + taskToEdit2.getStatus());

        System.out.println("ALL TAGS BEFORE: ");
        System.out.println(taskController.getTaskTags());
        System.out.println("TASK TAGS BEFORE: ");
        System.out.println(taskToEdit2.getTags().stream().findFirst().orElse(null));

        String oldTag = taskToEdit2.getTags().stream().findFirst().orElse(null);
        taskController.editTag("SHOPPING", "GROCERIES");
        System.out.println("\nTag altered from: " + oldTag);
        System.out.println("To: " + taskToEdit2.getTags().stream().findFirst().orElse(null));

        System.out.println("ALL TAGS AFTER: ");
        System.out.println(taskController.getTaskTags());
        System.out.println("TASK TAGS AFTER: ");
        System.out.println(taskToEdit2.getTags().stream().findFirst().orElse(null));

        System.out.println("\nALL TASKS:");
        taskController.getTaskListAsString();

        // deleting
        taskController.deleteTask(taskToEdit2);
        System.out.println("\nTask \"" + taskToEdit2.getTitle() + "\" deleted.");

        System.out.println("\nTASKS AFTER REMOVAL:");
        taskController.getTaskListAsString();

        taskController.stopScheduler();

    }
}
