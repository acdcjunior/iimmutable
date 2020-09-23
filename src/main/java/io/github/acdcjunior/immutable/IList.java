package io.github.acdcjunior.immutable;

import io.github.acdcjunior.immutable.fn.IFunction;
import io.github.acdcjunior.immutable.fn.IPredicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class IList<T> implements Iterable<T> {

    public static final int ARRAYLIST_DEFAULT_CAPACITY = 10;

    @NotNull
    public static <T> IList<T> listOf(@Nullable Iterator<T> iterator) {
        if (iterator == null) {
            return emptyList();
        }
        return new IList<T>(IListUtils.toArrayList(iterator));
    }

    @NotNull
    public static <T> IList<T> listOf(@Nullable Iterable<T> iterable) {
        return listOf(iterable == null ? null : iterable.iterator());
    }

    @NotNull
    public static <T> IList<T> listOf(@Nullable T... items) {
        if (items == null || items.length == 0) {
            return emptyList();
        }
        return new IList<T>(new ArrayList<T>(Arrays.asList(items)));
    }

    private static <T> IList<T> emptyList() {
        return new IList<T>(Collections.<T>emptyList());
    }

    @NotNull
    private final List<T> immutableBackingList;

    private IList(@NotNull List<T> immutableBackingList) {
        this.immutableBackingList = immutableBackingList;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IList) {
            return immutableBackingList.equals(((IList<?>) o).immutableBackingList);
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
    @Contract(pure = true)
    public List<T> toList() {
        return immutableBackingList;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return immutableBackingList.iterator();
    }

    @Contract(pure = true)
    public int size() {
        return immutableBackingList.size();
    }

    @NotNull
    @Contract(pure = true)
    public <R> IList<R> map(@NotNull IFunction<? super T, ? extends R> mapper) {
        List<R> rs = new ArrayList<R>(this.size());
        for (T item : this) {
            rs.add(mapper.apply(item));
        }
        return new IList<R>(rs);
    }

    @NotNull
    @Contract(pure = true)
    public IList<T> filter(@NotNull IPredicate<? super T> predicate) {
        List<T> rs = new ArrayList<T>(this.size());
        for (T item : this) {
            if (predicate.test(item)) {
                rs.add(item);
            }
        }
        return new IList<T>(rs);
    }

    @NotNull
    @Contract(pure = true)
    public final IList<T> concat(Iterable<? extends T>... iterables) {
        List<T> ls = new ArrayList<T>(immutableBackingList.size() + iterables.length * ARRAYLIST_DEFAULT_CAPACITY);
        ls.addAll(immutableBackingList);
        for (Iterable<? extends T> iterable : iterables) {
            for (T item : iterable) {
                ls.add(item);
            }
        }
        return new IList<T>(ls);
    }

    @NotNull
    @Contract(pure = true)
    public IList<T> filterNonNull() {
        return filter(new IPredicate<T>() {
            @Override
            public boolean test(@Nullable T t) {
                return t != null;
            }
        });
    }

    // https://stackoverflow.com/a/15603260/1850609
    @NotNull
    @Contract(pure = true)
    public IList<T> distinct() {
        return listOf(new LinkedHashSet<T>(immutableBackingList).iterator());
    }

    @NotNull
    @Contract(pure = true)
    public IList<T> subtract(@NotNull Iterable<T> other) {
        final List<T> otherLs = IListUtils.toArrayList(other);
        return filter(new IPredicate<T>() {
            @Override
            public boolean test(@Nullable T t) {
                return !otherLs.contains(t);
            }
        });
    }

    @NotNull
    @Contract(pure = true)
    public final IList<T> subtract(T... c) {
        return subtract(listOf(c));
    }

    @NotNull
    @Contract(pure = true)
    public final IList<T> plus(@NotNull Iterable<? extends T>... others) {
        return concat(others);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    @Contract(pure = true)
    public final IList<T> plus(T... c) {
        return plus(listOf(c));
    }

    @Contract(pure = true)
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Contract(pure = true)
    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    @Contract(pure = true)
    public IOption<T> first() {
        if (this.isEmpty()) {
            return IOption.none();
        }
        return IOption.some(immutableBackingList.get(0));
    }

}
