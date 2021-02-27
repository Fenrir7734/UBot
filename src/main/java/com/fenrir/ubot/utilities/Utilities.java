package com.fenrir.ubot.utilities;

import java.util.List;
import java.util.Random;

public class Utilities {

    public static <T> T randomElementFromList(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static <T> T randomElementFromArray(T[] array) {
        return array[new Random().nextInt(array.length)];
    }

}
