package org.website.task.crawler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Disposer class.
 */
public class DisposerTest {
    private static final int EXPECTED_LIST_SIZE = 20;
    private static final String TEST_URL = "https://compscicenter.ru/";
    private static final String TEST_WORD = "Computer";
    private static final Disposer disposer = new Disposer();

    /**
     * Search test.
     */
    @Test
    public void executeTest() {
        disposer.execute(TEST_URL, TEST_WORD);
        assertEquals(EXPECTED_LIST_SIZE, Disposer.data.size());
    }
}
