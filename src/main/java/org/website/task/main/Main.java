package org.website.task.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.website.task.crawler.Disposer;
import org.website.task.util.InputValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Main class, that contains the fields with the initial URL and the given words for the search,
 * and the main method for starting the program.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Disposer disposer = new Disposer();

        try (InputStream input = new FileInputStream("src/main/resources/configuration.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            if (InputValidator.isUrlValid(prop.getProperty("url"))) {
                logger.info("Root url is valid. Start process...");
                disposer.execute(prop.getProperty("url"), prop.getProperty("first.word"), prop.getProperty("second.word"));
            } else {
                logger.warn("Url is not valid. Check our data");
            }
        } catch (IOException ex) {
            logger.error("File not found");
        }
    }
}