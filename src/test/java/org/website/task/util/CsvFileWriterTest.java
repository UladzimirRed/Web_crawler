package org.website.task.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * The type Csv file writer test.
 */
class CsvFileWriterTest {
    /**
     * Creates csv file test.
     *
     * @param path the path
     */
    @Test
    void createsCsvFile(@TempDir final Path path) {
        final var csv = path.resolve("test.csv");
        CsvFileWriter.writeDataToCsvFile(
                csv.toString(),
                List.of(
                        new String[]{"1", "abc"},
                        new String[]{"2", "def"}
                )
        );
        Assertions.assertTrue(csv.toFile().exists());
    }

    /**
     * Writes data to csv test.
     *
     * @param temp the temp
     * @throws IOException the io exception
     */
    @Test
    void writesDataToCsv(@TempDir final Path temp) throws IOException {
        final var csv = temp.resolve("test.csv");
        CsvFileWriter.writeDataToCsvFile(
                csv.toString(),
                List.of(
                        new String[]{"1", "abc"},
                        new String[]{"2", "def"}
                )
        );
        Assertions.assertEquals(
                Files.readAllLines(csv),
                List.of("1 abc", "2 def")
        );
    }
}
