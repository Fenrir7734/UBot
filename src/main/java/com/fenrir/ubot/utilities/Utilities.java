package com.fenrir.ubot.utilities;

import java.awt.*;
import java.util.List;
import java.util.Random;

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

    public static String colorToHex(Color color) {
        return Integer.toHexString(color.getRGB()).substring(2);
    }

}
