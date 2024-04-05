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
        taskController.addNewTask("Spreadsheeeeet", "Fisn altering spreadsheet", LocalDateTime.now().plusDays(1), TaskPriority.MEDIUM, TaskStatus.PENDING, "work", "urgent");
        taskController.addNewTask("Groceries", "Buy tomatoes and milk", LocalDateTime.now().plusHours(2), TaskPriority.MEDIUM, TaskStatus.PENDING, "home", "shopping");
        taskController.addNewTask("Article", "Do research for the article", LocalDateTime.now().plusDays(5), TaskPriority.LOW, TaskStatus.PENDING, "study");

        // printing all tasks
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

        // editing date (now - 1min)
        taskController.editDate(taskToEdit, LocalDateTime.now().minusMinutes(1));
        System.out.println("\nExpiration date edited: ");
        System.out.println(taskToEdit);

        // editing priority
        taskController.editPriority(taskToEdit, TaskPriority.HIGH);
        System.out.println("\nPriority edited: ");
        System.out.println(taskToEdit);

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
