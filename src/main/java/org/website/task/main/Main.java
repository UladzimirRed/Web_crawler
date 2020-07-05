package org.website.task.main;

import org.website.task.crawler.Disposer;

/**
 * The Main class, that contains the fields with the initial URL and the given words for the search,
 * and the main method for starting the program.
 */
public class Main {
    private static final String URL = "https://compscicenter.ru/";
    private static final String FIRST_WORD = "Computer";
    private static final String SECOND_WORD = "Center";
    private static final String THIRD_WORD = "Science";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Disposer disposer = new Disposer();
        disposer.execute(URL, FIRST_WORD, SECOND_WORD);
    }
}