package controllers;

import models.Task;
import utils.csv.CSVReader;
import utils.csv.CSVWriter;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TaskController {

    private List<Task> taskList;
    private Set<String> taskTags;
    public ScheduledExecutorService schedulerCheckExpired;
    public ScheduledExecutorService schedulerWriteCSV;

    public TaskController() {
        this.taskList = new ArrayList<>();
        this.taskTags = new HashSet<>();
        this.schedulerCheckExpired = Executors.newScheduledThreadPool(1);
        this.schedulerWriteCSV = Executors.newScheduledThreadPool(1);

        schedulerCheckExpired.scheduleAtFixedRate(this::checkForExpiredTasks, 1, 15, TimeUnit.SECONDS);
        schedulerWriteCSV.scheduleAtFixedRate(() -> CSVWriter.writeTasks(taskList), 1, 16, TimeUnit.SECONDS);
    }

    public void checkForExpiredTasks() {
        taskList.stream()
                .filter(task -> task.getExpirationDate().isBefore(LocalDate.now()))
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



    public void addNewTask(String title, String description, LocalDate date, TaskPriority priority, TaskStatus status, String... tagNames){
        Set<String> tags = Arrays.stream(tagNames)
                .map(String::toUpperCase)
                .filter(tagName -> !taskTags.contains(tagName))
                .peek(taskTags::add)
                .collect(Collectors.toCollection(HashSet::new));

        Task task = new Task(title, description, date, priority, status, tags);
        taskList.add(task);
        CSVWriter.writeTasks(taskList);
    }

    public void deleteTask(Task task){
        taskList.removeIf(t -> t.getId().equals(task.getId()));
        CSVWriter.writeTasks(taskList);
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


    // tag methods
    public void addNewTagToTagList(String newTag){
        taskTags.add(newTag);
    }

    public void deleteTagFromTagList(String tagToBeDeleted){
	    taskTags.removeIf(tag -> tag.equals(tagToBeDeleted));
    }

    public void editTag(String oldTagName, String newTagName) {
        taskTags = taskTags.stream()
                .map(tag -> tag.equals(oldTagName) ? newTagName : tag)
                .collect(Collectors.toSet());
        taskList = taskList.stream()
                .map(task -> task.getTags().contains(oldTagName) ? setTagValue(task, newTagName) : task)
                .collect(Collectors.toList());

    }

    private Task setTagValue(Task task, String newTagName){
        task.getTags().add(newTagName);
        return task;
    }

    public void addTagToTask(Task task, String newTag){
        if (!taskTags.contains(newTag)) {
            taskTags.add(newTag.toUpperCase());
	}
        task.getTags().add(newTag.toUpperCase());
    }

    public void deleteTagFromTask(Task task, String tagToDelete) {
        task.getTags().removeIf(tag -> tag.equals(tagToDelete));
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

    public Set<String> getTaskTags() {
        return taskTags;
    }

    // setters

    public void setTaskTags(Set<String> taskTags) {
        this.taskTags = taskTags;
    }

    public void updateTask(Task updatedTask) {
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.getId().equals(updatedTask.getId())) {
                taskList.set(i, updatedTask);
                CSVWriter.writeTasks(taskList);
                break;
            }
        }
    }

    public void shutdown() {
        shutdownExecutorService(schedulerCheckExpired);
        shutdownExecutorService(schedulerWriteCSV);
    }

    private void shutdownExecutorService(ScheduledExecutorService executor) {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        System.err.println("Executor service did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

}
