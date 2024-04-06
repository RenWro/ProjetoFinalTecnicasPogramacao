package controllers;

import models.Task;
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
    private final ScheduledExecutorService scheduler;

    public TaskController() {
        this.taskList = new ArrayList<>();
        this.taskTags = new HashSet<>();
        this.scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(this::checkForExpiredTasks, 0, 15, TimeUnit.SECONDS);
    }

    public void checkForExpiredTasks() {
        taskList.stream()
                .filter(task -> task.getExpirationDate().isBefore(LocalDate.now()))
                .forEach(task -> task.setStatus(TaskStatus.OVERDUE));
    }

    //TODO instancia a task
    public void addNewTask(String title, String description, LocalDate date, TaskPriority priority, TaskStatus status, String... tagNames){
        Set<String> tags = Arrays.stream(tagNames)
                .map(String::toUpperCase)
                .filter(tagName -> !taskTags.contains(tagName))
                .peek(taskTags::add)
                .collect(Collectors.toCollection(HashSet::new));

        Task task = new Task(title, description, date, priority, status, tags);
        taskList.add(task);
    }

    //TODO exclui a task
    public void deleteTask(Task task){
        taskList.removeIf(t -> t.getId().equals(task.getId()));
    }

    //TODO edita o título
    public void editTitle(Task task, String newTitle) {
        task.setTitle(newTitle);
    }

    //TODO edita a descrição
    public void editDescription(Task task, String newDescription) {
        task.setDescription(newDescription);
    }

    //TODO edita a data
    public void editDate(Task task, LocalDate newDate) {
        task.setExpirationDate(newDate);
        checkForExpiredTasks();
    }

    //TODO edita a prioridade
    public void editPriority(Task task, TaskPriority newPriority) {
        task.setPriority(newPriority);
    }

    //TODO edita o status
    public void alterTaskStatus(Task task) {
        task.setStatus(task.getStatus() == TaskStatus.DONE ? TaskStatus.PENDING : TaskStatus.DONE);
    }


    // tag methods
    // TODO adiciona tag à lista de todas as tags
    public void addNewTagToTagList(String newTag){
        taskTags.add(newTag);
    }

    // TODO remove tag da lista de todas as tags
    public void deleteTagFromTagList(String tagToBeDeleted){
	    taskTags.removeIf(tag -> tag.equals(tagToBeDeleted));
    }

    // TODO edita tag na lista de todas as tags
    public void editTag(String oldTagName, String newTagName) {
        taskTags = taskTags.stream()
                .map(tag -> tag.equals(oldTagName) ? newTagName : tag)
                .collect(Collectors.toSet());
    }

    //TODO adiciona tag à lista de uma task específica
    public void addTagToTask(Task task, String newTag){
        if (!taskTags.contains(newTag)) {
            taskTags.add(newTag.toUpperCase());
	}
        task.getTags().add(newTag.toUpperCase());
    }

    //TODO remove tag da lista de uma task específica
    public void deleteTagFromTask(Task task, String tagToDelete) {
        task.getTags().removeIf(tag -> tag.equals(tagToDelete));
    }


    // getters
    public void getTaskListAsString() {
        taskList.stream()
                .map(Task::toString)
                .forEach(System.out::println);
    }

    //TODO lista de tarefas
    public List<Task> getTaskList() {
        return taskList;
    }

    //TODO lista de tags
    public Set<String> getTaskTags() {
        return taskTags;
    }
}
