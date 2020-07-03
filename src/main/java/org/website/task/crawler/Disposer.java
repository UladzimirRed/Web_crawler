package org.website.task.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.website.task.util.CsvFileWriter;

import java.util.*;


/**
 * A class that tracks pages that we already visited and does not view them,
 * sets a limit on the search depth, counts the total number of words used on each of the URLs
 */
public class Disposer {
    private static Logger logger = LogManager.getLogger();
    private static final String CSV_FILE_PATH = "./src/main/resources/data/result.csv";

    private final int MAX_PAGES_TO_SEARCH = 20;
    private Set<String> pagesVisited = new HashSet<>();
    private List<String> pagesToVisit = new LinkedList<>();
    private List<String[]> data = new ArrayList<>();

    /**
     * The main search method, which controls the depth of the search,
     * calls the method for making a query, searching for a given word
     * and writing the result to a separate file.
     *
     * @param url        the url
     * @param firstWord  the first word
     * @param secondWord the second word
     */
    public void search(String url, String firstWord, String secondWord) {

        while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
            String currentUrl;
            Acolyte acolyte = new Acolyte();
            if (this.pagesToVisit.isEmpty()) {
                currentUrl = url;
                this.pagesVisited.add(url);
            } else {
                currentUrl = this.nextUrl();
            }
            acolyte.crawl(currentUrl);
            int firstWordUse = acolyte.searchForWord(firstWord);
            int secondWordUse = acolyte.searchForWord(secondWord);
            data.add(new String[]{currentUrl, String.valueOf(firstWordUse), String.valueOf(secondWordUse), String.valueOf(countTotalUsage(firstWordUse, secondWordUse))});
            System.out.println(currentUrl + "/: " + firstWordUse + " " + secondWordUse + " " + countTotalUsage(firstWordUse, secondWordUse));
            CsvFileWriter.writeDataToCsvFile(CSV_FILE_PATH, data);
            this.pagesToVisit.addAll(acolyte.getLinks());
        }
        logger.info("Done! Visited " + this.pagesVisited.size() + " web page(s)");
    }

    /**
     * Method that iterates over the list of pages to visit
     * and returns the next URL if it is in the set of visited pages.
     *
     * @return Next URL from pages to visit list
     */
    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }

    /**
     * The method counts the number of common uses of all words on the page.
     *
     * @param firstWordUsage  the first word that is involved in the search
     * @param secondWordUsage the second word that is involved in the search
     * @return Integer words common use
     */
    private int countTotalUsage(int firstWordUsage, int secondWordUsage) {
        return firstWordUsage + secondWordUsage;
    }
}
