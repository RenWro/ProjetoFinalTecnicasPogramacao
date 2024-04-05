package utils.csv;

import controllers.TaskController;
import models.Task;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CSVReader {

    private static final String FILE_PATH = "tasks.csv";

    public static List<Task> readTasks() {
        List<Task> tasks = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            tasks = Files.lines(Paths.get(FILE_PATH))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(data -> new Task(
                            UUID.fromString(data[0].trim()),
                            data[1].trim(),
                            data[2].trim(),
                            LocalDateTime.parse(data[3].trim(), formatter),
                            TaskPriority.valueOf(data[4].trim().toUpperCase()),
                            TaskStatus.valueOf(data[5].trim().toUpperCase()),
                            Set.of(data[6].split(";"))
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
