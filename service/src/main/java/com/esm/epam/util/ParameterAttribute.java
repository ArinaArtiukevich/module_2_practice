package com.esm.epam.util;

import java.util.Arrays;
import java.util.List;

public class ParameterAttribute {
    public final static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS",
            TAG = "tag",
            NAME = "name",
            DESCRIPTION = "description",
            SORT = "sort";

    public final static List<String> SORT_KEYS = Arrays.asList(TAG, NAME, DESCRIPTION, SORT);
}
