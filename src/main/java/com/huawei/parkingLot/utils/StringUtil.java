package com.huawei.parkingLot.utils;

public class StringUtil {

    private StringUtil() {
    }

    public static boolean isBlank(final String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    public static void validateNotBlank(final String str) {
        if (isBlank(str)) {
            throw new IllegalArgumentException("Metin Bos");
        }
    }

    public static boolean equals(final String str1, final String str2) {
        return (str1 == null && str2 == null) || str1.equals(str2);
    }

}
