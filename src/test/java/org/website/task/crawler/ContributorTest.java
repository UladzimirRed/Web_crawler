package org.website.task.crawler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for Contributor class.
 */
public class ContributorTest {
    private static final String TEST_URL = "https://compscicenter.ru/";
    private static final Contributor CONTRIBUTOR = new Contributor();

    /**
     * Crawl test.
     */
    @Test
    public void crawlTest() {
        assertTrue(CONTRIBUTOR.crawl(TEST_URL));
    }

    /**
     * Crawl expected exception test.
     */
    @Test
    public void crawlExpectedExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> CONTRIBUTOR.crawl("text"));
    }

    /**
     * Gets links test.
     */
    @Test
    public void getLinks() {
        assertNotNull(CONTRIBUTOR.getLinks());
    }
}
