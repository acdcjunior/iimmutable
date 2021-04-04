package dev.acdcjunior.iimmutable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @since 1.0.0
 */
public class IListUtils {

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <T> List<T> toArrayList(Iterable<T> iterable) {
        return toArrayList(iterable.iterator());
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    public static <T> List<T> toArrayList(Iterator<T> iterator) {
        List<T> ls = new ArrayList<T>();
        while (iterator.hasNext()) {
            ls.add(iterator.next());
        }
        return ls;
    }

}
