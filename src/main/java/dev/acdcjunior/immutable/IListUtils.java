package dev.acdcjunior.immutable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class IListUtils {

    public static <T> List<T> toArrayList(Iterable<T> iterable) {
        return toArrayList(iterable.iterator());
    }

    public static <T> List<T> toArrayList(Iterator<T> iterator) {
        List<T> ls = new ArrayList<T>();
        while (iterator.hasNext()) {
            ls.add(iterator.next());
        }
        return ls;
    }

}
