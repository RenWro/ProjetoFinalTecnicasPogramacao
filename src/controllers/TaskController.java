package controllers;

import models.Task;
import utils.csv.CSVWriter;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskController {

    private List<Task> taskList;
    public ScheduledExecutorService schedulerCheckExpired;
    public ScheduledExecutorService schedulerWriteCSV;

    public TaskController() {
        this.taskList = new ArrayList<>();
        this.schedulerCheckExpired = Executors.newScheduledThreadPool(1);
        this.schedulerWriteCSV = Executors.newScheduledThreadPool(1);

        schedulerCheckExpired.scheduleAtFixedRate(this::checkForExpiredTasks, 1, 15, TimeUnit.SECONDS);
        schedulerWriteCSV.scheduleAtFixedRate(() -> CSVWriter.writeTasks(taskList), 1, 16, TimeUnit.SECONDS);
    }

    public void checkForExpiredTasks() {
        taskList.stream()
                .filter(task -> !task.getStatus().equals(TaskStatus.DONE) && task.getExpirationDate().isBefore(LocalDate.now()))
                .forEach(task -> task.setStatus(TaskStatus.OVERDUE));
    }

    public void stopScheduler() {

        schedulerCheckExpired.shutdown();
        schedulerWriteCSV.shutdown();

        try {

            if (!schedulerCheckExpired.awaitTermination(60, TimeUnit.SECONDS)) {
                schedulerCheckExpired.shutdownNow();
            }

            if (!schedulerWriteCSV.awaitTermination(60, TimeUnit.SECONDS)) {
                schedulerWriteCSV.shutdownNow();
            }
        } catch (InterruptedException e) {

            schedulerCheckExpired.shutdownNow();
            schedulerWriteCSV.shutdownNow();

            Thread.currentThread().interrupt();
        }
    }


    public void addNewTask(String title, String description, LocalDate date, TaskPriority priority, TaskStatus status){
        Task task = new Task(title, description, date, priority, status);
        taskList.add(task);
    }

    public void deleteTask(Task task){
        taskList.removeIf(t -> t.getId().equals(task.getId()));;
    }

    public void editTitle(Task task, String newTitle) {
        task.setTitle(newTitle);
    }

    public void editDescription(Task task, String newDescription) {
        task.setDescription(newDescription);
    }

    public void editDate(Task task, LocalDate newDate) {
        task.setExpirationDate(newDate);
        checkForExpiredTasks();
    }

    public void editPriority(Task task, TaskPriority newPriority) {
        task.setPriority(newPriority);
    }

    public void alterTaskStatus(Task task) {
        task.setStatus(task.getStatus() == TaskStatus.DONE ? TaskStatus.PENDING : TaskStatus.DONE);
    }


    // getters
    public void getTaskListAsString() {
        taskList.stream()
                .map(Task::toString)
                .forEach(System.out::println);
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}
