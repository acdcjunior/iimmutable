package io.github.acdcjunior.java6fp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FPUtils {

    public static <T> List<T> toArrayList(Iterable<T> items) {
        List<T> ls = new ArrayList<T>();
        for (T item : items) {
            ls.add(item);
        }
        return ls;
    }

    public static <T> List<T> toArrayList(Iterator<T> iterator) {
        List<T> ls = new ArrayList<T>();
        while (iterator.hasNext()) {
            ls.add(iterator.next());
        }
        return ls;
    }

}
