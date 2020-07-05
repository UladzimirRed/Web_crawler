package org.website.task.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.website.task.exception.ProjectException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * The class that makes the HTTP request and parses the HTML-page,
 * searches for the given word on this page
 * and counts how many times this word is repeated on this page
 */
public class Contributor {
    private static final Logger logger = LogManager.getLogger();
    private static final List<String> links = new LinkedList<>();
    private static final int HTTP_STATUS_OK = 200;
    private Document htmlDocument;

    /**
     * This method makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     *
     * @param url - The URL to visit
     * @return whether or not the crawl was successful
     */
    public boolean crawl(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            htmlDocument = connection.get();

            if (connection.response().statusCode() == HTTP_STATUS_OK) {
                logger.info("Visiting.. Received web page at " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                logger.warn("Failure.. Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            logger.info("Found " + linksOnPage.size() + " links");
            for (Element link : linksOnPage) {
                links.add(link.absUrl("href"));
            }
            return true;
        } catch (IOException e) {
            logger.warn("We were not successful in our HTTP request", e);
            return false;
        }
    }

    /**
     * Performs a search on the body of on the HTML document that is retrieved. This method should
     * only be called after a successful crawl.
     *
     * @param searchWord - The word or string to look for
     * @return How many times was the word found
     */
    public int searchForWord(String searchWord) {
        try {
            int count = 0;
            if (htmlDocument == null) {
                logger.error("Call crawl() before performing analysis on the document");
            }
            logger.info("Searching for the word " + searchWord + "...");
            String bodyText = htmlDocument.body().text();
            for (int index = -1; (index = bodyText.indexOf(searchWord, index + 1)) != -1; count++) ;
            logger.info("Used " + count + " times");
            return count;
        } catch (Exception ex) {
            logger.fatal("Html document is not exist on this web page. Check preset URL.");
            throw new ProjectException("Html document is not exist on this web page. Check preset URL.");
        }
    }

    /**
     * Gets links.
     *
     * @return the links
     */
    public List<String> getLinks() {
        return links;
    }
}

