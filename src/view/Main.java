package view;

import controllers.TaskController;
import models.Task;
import utils.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        TaskController taskController = new TaskController();

        // adding a few tasks
        taskController.addNewTask("Spreadsheeeeet", "Fisn chngs spreadsheet", LocalDateTime.now().plusDays(1), TaskPriority.MEDIUM, TaskStatus.PENDING, "work", "urgent");
        taskController.addNewTask("Groceries", "Buy tomatoes and milk", LocalDateTime.now().plusHours(2), TaskPriority.MEDIUM, TaskStatus.PENDING, "home", "shopping");
        taskController.addNewTask("Article", "Do research for the article", LocalDateTime.now().plusDays(5), TaskPriority.LOW, TaskStatus.PENDING, "study");

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm:ss");
        LocalDateTime oldDate = taskToEdit.getExpirationDate();
        taskController.editDate(taskToEdit, LocalDateTime.now().minusMinutes(1));
        System.out.println("\nExpiration date edited from: " + oldDate.format(formatter));
        System.out.println("To: " + taskToEdit.getExpirationDate().format(formatter));

        // editing priority
        TaskPriority oldPriority = taskToEdit.getPriority();
        taskController.editPriority(taskToEdit, TaskPriority.HIGH);
        System.out.println("\nPriority edited from: " + oldPriority);
        System.out.println("To: " + taskToEdit.getPriority());

        // printing task after changes
        System.out.println("\nTask \"" + taskToEdit.getTitle() + "\" after changes.");
        System.out.println(taskToEdit);

        // defining second task to be edited
        Task taskToEdit2 = taskController.getTaskList().get(1);

        // changing status
        TaskStatus oldStatus = taskToEdit2.getStatus();
        taskController.alterTaskStatus(taskToEdit2);
        System.out.println("\nStatus altered from: " + oldStatus);
        System.out.println("To: " + taskToEdit2.getStatus());

        System.out.println("\nALL TASKS:");
        taskController.getTaskListAsString();

        // deleting
        taskController.deleteTask(taskToEdit2);
        System.out.println("\nTask \"" + taskToEdit2.getTitle() + "\" deleted.");

        System.out.println("\nTASKS AFTER REMOVAL:");
        taskController.getTaskListAsString();
    }
}
