package org.website.task.util;

import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.website.task.exception.ProjectException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The class that is responsible for writing data to the DSC file,
 * contains one appropriate static method
 */
public class CsvFileWriter {
    private static final Logger logger = LogManager.getLogger();

    /**
     * The method writes data to the CSV file
     *
     * @param filePath The path to the file
     * @param data     List containing an array of data
     */
    public static void writeDataToCsvFile(String filePath, List<String[]> data) {
        File file = new File(filePath);
        try (FileWriter outputFile = new FileWriter(file)) {
            CSVWriter writer = new CSVWriter(outputFile, ' ',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(data);
        } catch (IOException e) {
            logger.error("Unable to find the specified path", e);
            throw new ProjectException(e);
        }
    }
}
