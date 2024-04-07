package utils.csv;

import controllers.TaskController;
import models.Task;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CSVReader {
    private static final String FILE_PATH = "src/db/tasks.csv";

    public static void readTasks(TaskController taskController) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");

        try {
            List<Task> tasks =  Files.lines(Paths.get(FILE_PATH))
                    .map(line -> line.split(","))
                    .map(data -> new Task(
                            UUID.fromString(data[0].trim()),
                            data[1].trim(),
                            data[2].trim(),
                            LocalDate.parse(data[3].trim(), formatter),
                            TaskPriority.valueOf(data[4].trim().toUpperCase()),
                            TaskStatus.valueOf(data[5].trim().toUpperCase()),
                            Set.of(data[6].split(";"))
                    ))
                    .collect(Collectors.toList());

            taskController.getTaskList().addAll(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
