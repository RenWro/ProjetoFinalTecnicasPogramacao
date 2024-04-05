package view;

import controllers.TaskController;
import models.Task;
import utils.enums.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskController taskController = new TaskController();

        // adding a few tasks
        List<String> tags1 = new ArrayList<>();
        tags1.add("work");
        tags1.add("urgent");
        taskController.addNewTask("Spreadsheeeeet", "Fisn altering spreadsheet", LocalDateTime.now().plusDays(1), TaskPriority.HIGH, TaskStatus.PENDING, tags1);

        List<String> tags2 = new ArrayList<>();
        tags2.add("home");
        tags2.add("shopping");
        taskController.addNewTask("Groceries", "Buy tomatoes and milk", LocalDateTime.now().plusHours(2), TaskPriority.MEDIUM, TaskStatus.PENDING, tags2);

        List<String> tags3 = new ArrayList<>();
        tags3.add("study");
        taskController.addNewTask("Article", "Do research for the article", LocalDateTime.now().plusDays(5), TaskPriority.LOW, TaskStatus.PENDING, tags3);

        // printing
        System.out.println("All tasks:");
        for (Task task : taskController.getTaskList()) {
            System.out.println(task);
        }

        // editing title
        Task taskToEdit = taskController.getTaskList().get(0);
        taskController.editTitle(taskToEdit, "Spreadsheet");
        System.out.println("\nTitle edited: ");
        System.out.println(taskToEdit);

        // editing description
        taskController.editDescription(taskToEdit, "Finish altering spreadsheet");
        System.out.println("\nDescription edited: ");
        System.out.println(taskToEdit);

        // editing date (now)
        taskController.editDate(taskToEdit, LocalDateTime.now().minusMinutes(1));
        System.out.println("\nExpiration date edited: ");
        System.out.println(taskToEdit);

        // editing priority
        taskController.editPriority(taskToEdit, TaskPriority.MEDIUM);
        System.out.println("\nPriority edited: ");
        System.out.println(taskToEdit);

        System.out.println("\nWaiting a bit to check for expired tasks...");
        try {
            Thread.sleep(40000); //wait 40 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // print all tasks again to check for LATE
        System.out.println("\nAll tasks after checking:");
        for (Task task : taskController.getTaskList()) {
            System.out.println(task);
        }

        // changing status
        taskController.alterTaskStatus(taskToEdit);
        System.out.println("\nStatus altered: ");
        System.out.println(taskToEdit);

        // deleting
        taskController.deleteTask(taskToEdit);
        System.out.println("\nTasks after removal:");
        for (Task task : taskController.getTaskList()) {
            System.out.println(task);
        }
    }
}
