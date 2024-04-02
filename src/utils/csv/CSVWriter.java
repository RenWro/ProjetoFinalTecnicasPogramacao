package utils.csv;

import models.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CSVWriter {

    private static final String FILE_PATH = "tasks.csv";

    public static void writeTasks(List<Task> tasks) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            tasks.stream()
                    .map(CSVWriter::taskToCSVLine)
                    .forEach(line -> {
                        try {
                            bw.write(line);
                            bw.newLine();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UncheckedIOException ue) {
            System.out.println("Failed to write file: " + ue.getCause().getMessage());
        }
    }

    private static String taskToCSVLine(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String expirationDateFormatted = task.expirationDate.format(formatter);
        String tags = String.join(";", task.tags);

        return String.join(",",
                String.valueOf(task.id),
                task.title.replace(",", ";"),
                task.description.replace(",", ";"),
                expirationDateFormatted,
                task.priority.toString(),
                task.status.toString(),
                "\"" + tags + "\""
        );

    }

}
