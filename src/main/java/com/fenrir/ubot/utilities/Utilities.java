package com.fenrir.ubot.utilities;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    public static <T> T randomElementFromList(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static <T> T randomElementFromArray(T[] array) {
        return array[new Random().nextInt(array.length)];
    }

    public static boolean isInteger(String arg) {
        return arg.matches("\\d+");
    }

    public static boolean isNumeric(String arg) {
        return arg.matches("-?\\d+(\\.\\d+)?");
    }

}
