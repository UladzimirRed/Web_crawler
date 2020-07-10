package org.website.task.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.website.task.util.CsvFileWriter;
import org.website.task.util.HitsComparator;
import org.website.task.util.UrlNormalizer;

import java.util.*;
import java.util.stream.Collectors;


/**
 * A class that tracks pages that we already visited and does not view them,
 * sets a limit on the search depth, counts the total number of words used on each of the URLs
 */
public class Disposer {
    private static final Logger logger = LogManager.getLogger();
    private static final String CSV_FILE_PATH = "./src/main/resources/data/result.csv";
    private static final String CSV_TOP_TEN_FILE_PATH = "./src/main/resources/data/resultTop.csv";

    private static final int MAX_PAGES_TO_SEARCH = 100;
    private static final Set<String> PAGES_VISITED = new HashSet<>();
    private static final List<String> PAGES_TO_VISIT = new LinkedList<>();
    public static final List<String[]> overallResultData = new ArrayList<>();
    Contributor contributor = new Contributor();

    /**
     * The main search method, which controls the depth of the search,
     * calls the method for making a query, searching for a given word
     * and writing the result to a separate file.
     *
     * @param url   the url
     * @param words the words
     */
    public void execute(String url, String... words) {

        while (PAGES_VISITED.size() < MAX_PAGES_TO_SEARCH) {
            String currentUrl;
            if (PAGES_TO_VISIT.isEmpty()) {
                currentUrl = url;
                PAGES_VISITED.add(url);
            } else {
                currentUrl = this.nextUrl();
            }
            String normalizedUrl = UrlNormalizer.normalizeUrl(currentUrl);
            if (Contributor.crawl(normalizedUrl)) {
                List<String> partsOfResult = makePartsOfResult(normalizedUrl, words);
                overallResultData.add(partsOfResult.toArray(new String[0]));
                CsvFileWriter.writeDataToCsvFile(CSV_FILE_PATH, overallResultData);
                PAGES_TO_VISIT.addAll(contributor.getLinks());
            } else {
                logger.warn("Url: " + normalizedUrl + " is incorrect");
            }
        }
        List<String[]> topTenHits = makeSortedStrings();
        CsvFileWriter.writeDataToCsvFile(CSV_TOP_TEN_FILE_PATH, topTenHits);
        topTenHits.forEach(arr -> System.out.println(Arrays.toString(arr)));
        logger.info("Done! Visited " + PAGES_VISITED.size() + " web page(s)");
    }

    private List<String> makePartsOfResult(String currentUrl, String[] words) {
        List<String> outputValues = new ArrayList<>();
        List<Integer> numberOfUsesOfGivenWords = new ArrayList<>();
        outputValues.add(currentUrl);
        for (String word : words) {
            int numberOfUsage = contributor.searchForWord(word);
            numberOfUsesOfGivenWords.add(numberOfUsage);
            outputValues.add(String.valueOf(numberOfUsage));
        }
        outputValues.add(String.valueOf(countTotalUsage(numberOfUsesOfGivenWords.stream().mapToInt(i -> i).toArray())));
        return outputValues;
    }

    private List<String[]> makeSortedStrings() {
        return overallResultData.stream()
                .sorted(new HitsComparator().reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = PAGES_TO_VISIT.remove(0);
        } while (PAGES_VISITED.contains(nextUrl));
        PAGES_VISITED.add(nextUrl);
        return nextUrl;
    }

    private int countTotalUsage(int... usages) {
        return Arrays.stream(usages).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disposer disposer = (Disposer) o;

        return contributor != null ? contributor.equals(disposer.contributor) : disposer.contributor == null;
    }

    @Override
    public int hashCode() {
        return contributor != null ? contributor.hashCode() : 0;
    }
}


