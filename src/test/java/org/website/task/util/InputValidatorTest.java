package org.website.task.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputValidatorTest {

    @Test
    public void isUrlValidTest() {
        assertTrue(InputValidator.isUrlValid("http://www.example.com"));
        assertFalse(InputValidator.isUrlValid("javascript:alert('xss')"));
        assertFalse(InputValidator.isUrlValid(""));
        assertFalse(InputValidator.isUrlValid(null));
    }
}
