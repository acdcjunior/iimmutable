package dev.acdcjunior.immutable;

import dev.acdcjunior.immutable.fn.IFunction;
import dev.acdcjunior.immutable.fn.IPredicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class IList<T> implements Iterable<T> {

    public static final int ARRAYLIST_DEFAULT_CAPACITY = 10;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final IList EMPTY_ILIST = new IList(Collections.emptyList());

    @NotNull
    @Contract(pure = true)
    public static <T> IList<T> listOf(@Nullable Iterator<T> iterator) {
        if (iterator == null) {
            return emptyList();
        }
        return new IList<T>(IListUtils.toArrayList(iterator));
    }

    @NotNull
    @Contract(pure = true)
    public static <T> IList<T> listOf(@Nullable Iterable<T> iterable) {
        return listOf(iterable == null ? null : iterable.iterator());
    }

    private static <T> IList<T> listOfVarags(@Nullable T... items) {
        if (items == null || items.length == 0) {
            return emptyList();
        }
        return new IList<T>(new ArrayList<T>(Arrays.asList(items)));
    }

    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1) { return listOfVarags(t1); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2) { return listOfVarags(t1, t2); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3) { return listOfVarags(t1, t2, t3); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4) { return listOfVarags(t1, t2, t3, t4); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5) { return listOfVarags(t1, t2, t3, t4, t5); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6) { return listOfVarags(t1, t2, t3, t4, t5, t6); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7) { return listOfVarags(t1, t2, t3, t4, t5, t6, t7); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) { return listOfVarags(t1, t2, t3, t4, t5, t6, t7, t8); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) { return listOfVarags(t1, t2, t3, t4, t5, t6, t7, t8, t9); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) { return listOfVarags(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10); }
    @NotNull @Contract(pure = true) public static <T> IList<T> listOf(@Nullable T... items) { return listOfVarags(items); }

    @SuppressWarnings("unchecked")
    public static <T> IList<T> emptyList() {
        return (IList<T>) EMPTY_ILIST;
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

    private IList<T> concatVarargs(Iterable<? extends T>... iterables) {
        List<T> ls = new ArrayList<T>(immutableBackingList.size() + iterables.length * ARRAYLIST_DEFAULT_CAPACITY);
        ls.addAll(immutableBackingList);
        for (Iterable<? extends T> iterable : iterables) {
            for (T item : iterable) {
                ls.add(item);
            }
        }
        return new IList<T>(ls);
    }

    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1) { return concatVarargs(it1); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1, Iterable<? extends T> it2) { return concatVarargs(it1, it2); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1, Iterable<? extends T> it2, Iterable<? extends T> it3) { return concatVarargs(it1, it2, it3); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1, Iterable<? extends T> it2, Iterable<? extends T> it3, Iterable<? extends T> it4) { return concatVarargs(it1, it2, it3, it4); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1, Iterable<? extends T> it2, Iterable<? extends T> it3, Iterable<? extends T> it4, Iterable<? extends T> it5) { return concatVarargs(it1, it2, it3, it4, it5); }
    @NotNull @Contract(pure = true) public final IList<T> concat(Iterable<? extends T>... iterables) { return concatVarargs(iterables); }

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

    private IList<T> subtractVarags(T... c) {
        return subtract(listOf(c));
    }

    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1) { return subtractVarags(t1); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1, T t2) { return subtractVarags(t1, t2); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1, T t2, T t3) { return subtractVarags(t1, t2, t3); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1, T t2, T t3, T t4) { return subtractVarags(t1, t2, t3, t4); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1, T t2, T t3, T t4, T t5) { return subtractVarags(t1, t2, t3, t4, t5); }
    @NotNull @Contract(pure = true) public IList<T> subtract(T... c) { return subtractVarags(c); }

    private IList<T> plusVarargs(@NotNull Iterable<? extends T>... others) {
        return concat(others);
    }

    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1) { return plusVarargs(it1); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2) { return plusVarargs(it1, it2); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3) { return plusVarargs(it1, it2, it3); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3, @NotNull Iterable<? extends T> it4) { return plusVarargs(it1, it2, it3, it4); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3, @NotNull Iterable<? extends T> it4, @NotNull Iterable<? extends T> it5) { return plusVarargs(it1, it2, it3, it4, it5); }
    @NotNull @Contract(pure = true) public IList<T> plus(@NotNull Iterable<? extends T>... others) { return plusVarargs(others); }

    @SuppressWarnings("unchecked")
    private IList<T> plusVarargs(T... c) {
        return plusVarargs(listOf(c));
    }

    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1) { return plusVarargs(t1); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1, T t2) { return plusVarargs(t1, t2); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1, T t2, T t3) { return plusVarargs(t1, t2, t3); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1, T t2, T t3, T t4) { return plusVarargs(t1, t2, t3, t4); }
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1, T t2, T t3, T t4, T t5) { return plusVarargs(t1, t2, t3, t4, t5); }
    @NotNull @Contract(pure = true) public IList<T> plus(T... c) { return plusVarargs(c); }

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
