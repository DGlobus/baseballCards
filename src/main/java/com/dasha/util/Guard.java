package com.dasha.util;

import com.dasha.exceptions.exception.ItemNotFoundException;

public class Guard {
    public static void check(boolean condition, String error) {
        if (!condition)
            throw new RuntimeException(error);
    }

    public static void checkAndThrowItemNotFoundException(boolean condition, String error) {
        if (!condition)
            throw new ItemNotFoundException(error);
    }
}
