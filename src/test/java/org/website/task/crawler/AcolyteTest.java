package org.website.task.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class AcolyteTest {
    int expectedCount = 3;
    String url = "https://compscicenter.ru/";

    Connection connection = Jsoup.connect(url);


    private void crawlTest() throws IOException {
        Document htmlDocument = connection.get();

    }
}
