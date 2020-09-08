package io.github.acdcjunior.java6fp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class FPList<T> implements Iterable<T> {

    public static final int ARRAYLIST_DEFAULT_CAPACITY = 10;

    @NotNull
    public static <T> FPList<T> listOf(@Nullable Iterator<T> iterator) {
        if (iterator == null) {
            return emptyList();
        }
        return new FPList<T>(FPUtils.toArrayList(iterator));
    }

    @NotNull
    public static <T> FPList<T> listOf(@Nullable Iterable<T> items) {
        if (items == null) {
            return emptyList();
        }
        return new FPList<T>(FPUtils.toArrayList(items));
    }

    @NotNull
    public static <T> FPList<T> listOf(@Nullable T... items) {
        if (items == null || items.length == 0) {
            return emptyList();
        }
        return new FPList<T>(new ArrayList<T>(Arrays.asList(items)));
    }

    private static <T> FPList<T> emptyList() {
        return new FPList<T>(Collections.<T>emptyList());
    }

    @NotNull
    private final List<T> immutableBackingList;

    private FPList(@NotNull List<T> immutableBackingList) {
        this.immutableBackingList = immutableBackingList;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FPList) {
            return immutableBackingList.equals(((FPList<?>) o).immutableBackingList);
        }
        return immutableBackingList.equals(o);
    }

    @Override
    public int hashCode() {
        return immutableBackingList.hashCode();
    }

    @Override
    public String toString() {
        return immutableBackingList.toString();
    }

    @NotNull
    public List<T> toList() {
        return immutableBackingList;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return immutableBackingList.iterator();
    }

    public int size() {
        return immutableBackingList.size();
    }

    @NotNull
    public <R> FPList<R> map(@NotNull FPFunction<? super T, ? extends R> mapper) {
        List<R> rs = new ArrayList<R>(this.size());
        for (T item : this) {
            rs.add(mapper.apply(item));
        }
        return new FPList<R>(rs);
    }

    @NotNull
    public FPList<T> filter(@NotNull FPPredicate<? super T> predicate) {
        List<T> rs = new ArrayList<T>(this.size());
        for (T item : this) {
            if (predicate.test(item)) {
                rs.add(item);
            }
        }
        return new FPList<T>(rs);
    }

    @NotNull
    public final FPList<T> concat(Iterable<? extends T>... iterables) {
        List<T> ls = new ArrayList<T>(immutableBackingList.size() + iterables.length * ARRAYLIST_DEFAULT_CAPACITY);
        ls.addAll(immutableBackingList);
        for (Iterable<? extends T> iterable : iterables) {
            for (T item : iterable) {
                ls.add(item);
            }
        }
        return new FPList<T>(ls);
    }

    @NotNull
    public FPList<T> filterNonNull() {
        return filter(new FPPredicate<T>() {
            @Override
            public boolean test(@Nullable T t) {
                return t != null;
            }
        });
    }

    // https://stackoverflow.com/a/15603260/1850609
    @NotNull
    public FPList<T> distinct() {
        return listOf(new LinkedHashSet<T>(immutableBackingList).iterator());
    }

    @NotNull
    public FPList<T> subtract(@NotNull Iterable<T> other) {
        final List<T> otherLs = FPUtils.toArrayList(other);
        return filter(new FPPredicate<T>() {
            @Override
            public boolean test(@Nullable T t) {
                return !otherLs.contains(t);
            }
        });
    }

    @NotNull
    public final FPList<T> subtract(T... c) {
        return subtract(listOf(c));
    }

    @NotNull
    public final FPList<T> plus(@NotNull Iterable<? extends T>... others) {
        return concat(others);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public final FPList<T> plus(T... c) {
        return plus(listOf(c));
    }

}
