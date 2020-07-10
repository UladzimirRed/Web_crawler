package org.website.task.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Url Normalizing Class
 * <p>
 * - Normalize the path (done by java.net.URI)
 * - Remove trailing slash.
 */
public class UrlNormalizer {
    private static final Logger logger = LogManager.getLogger();
    /**
     * Normalize url string.
     *
     * @param url the url
     * @return the string
     */
    public static String normalizeUrl(String url) {
        try {
            if (url == null) {
                return null;
            }
            URI uri = new URI(url);
            uri = uri.normalize();
            String path = uri.getPath();
            if (path != null) {
                path = path.replaceAll("//*/", "/");
                if (path.length() > 0 && path.charAt(path.length() - 1) == '/') {
                    path = path.substring(0, path.length() - 1);
                }
            }
            return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                    path, uri.getQuery(), uri.getFragment()).toString();

        } catch (URISyntaxException e) {
            logger.error(e);
            return null;
        }
    }
}
