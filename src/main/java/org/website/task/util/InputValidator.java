package org.website.task.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    public static boolean isUrlValid(String url) {
        if (url != null) {
            Pattern pattern = Pattern.compile("(\\b(https?|ftp|file)://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
            Matcher matcher = pattern.matcher(url);
            return matcher.matches();
        } else {
            return false;
        }
    }
}
