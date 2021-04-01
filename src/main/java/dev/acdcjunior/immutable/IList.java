package dev.acdcjunior.immutable;

import dev.acdcjunior.immutable.fn.IBiFunction;
import dev.acdcjunior.immutable.fn.IConsumer;
import dev.acdcjunior.immutable.fn.IFunction;
import dev.acdcjunior.immutable.fn.IPredicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


/**
 * An immutable list.
 *
 * @param <T> The type of the elements.
 *
 * @since 1.0.0
 */
public class IList<T> implements Iterable<T> {

    public static final int ARRAYLIST_DEFAULT_CAPACITY = 10;

    public static final int FLATMAP_MAPPER_FUNCTION_MEAN_EXPECTED_ELEMENTS = 2;

    public static final int JOIN_FUNCTION_MEAN_EXPECTED_ELEMENTS_STRING_SIZE = 5;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final IList EMPTY_ILIST = new IList(Collections.emptyList());

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <T> IList<T> listOf(@Nullable Iterator<T> iterator) {
        if (iterator == null) {
            return emptyList();
        }
        return new IList<T>(IListUtils.toArrayList(iterator));
    }

    /**
     * @since 1.0.0
     */
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

    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1) { return listOfVarags(t1); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2) { return listOfVarags(t1, t2); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3) { return listOfVarags(t1, t2, t3); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4) { return listOfVarags(t1, t2, t3, t4); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5) { return listOfVarags(t1, t2, t3, t4, t5); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6) { return listOfVarags(t1, t2, t3, t4, t5, t6); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7) { return listOfVarags(t1, t2, t3, t4, t5, t6, t7); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) { return listOfVarags(t1, t2, t3, t4, t5, t6, t7, t8); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) { return listOfVarags(t1, t2, t3, t4, t5, t6, t7, t8, t9); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public static <T> IList<T> listOf(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) { return listOfVarags(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) public static <T> IList<T> listOf(@Nullable T... items) { return listOfVarags(items); }

    /**
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T> IList<T> emptyList() {
        return (IList<T>) EMPTY_ILIST;
    }

    @NotNull
    private final List<T> immutableBackingList;

    private IList(@NotNull List<T> immutableBackingList) {
        this.immutableBackingList = immutableBackingList;
    }

    /**
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof IList) {
            return immutableBackingList.equals(((IList<?>) o).immutableBackingList);
        }
        return immutableBackingList.equals(o);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public int hashCode() {
        return immutableBackingList.hashCode();
    }

    /**
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return immutableBackingList.toString();
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public List<T> toList() {
        return immutableBackingList;
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return immutableBackingList.iterator();
    }

    /**
     * @since 1.0.0
     */
    @Contract(pure = true)
    public int size() {
        return immutableBackingList.size();
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public <R> IList<R> map(@NotNull IFunction<? super T, ? extends R> mapper) {
        List<R> rs = new ArrayList<R>(this.size());
        for (T item : this) {
            rs.add(mapper.apply(item));
        }
        return new IList<R>(rs);
    }

    /**
     * Returns a list of all elements yielded from results of mapper function being invoked on each element of the
     * original collection.
     *
     * @since 2.0.0
     */
    @NotNull
    @Contract(pure = true)
    public <R> IList<R> flatMap(@NotNull IFunction<? super T, Iterable<? extends R>> mapper) {
        List<R> rs = new ArrayList<R>(this.size() * FLATMAP_MAPPER_FUNCTION_MEAN_EXPECTED_ELEMENTS);
        for (T item : this) {
            Iterable<? extends R> frs = mapper.apply(item);
            for (R r : frs) {
                rs.add(r);
            }
        }
        return new IList<R>(rs);
    }

    /**
     * Returns the first element that matches the given predicate, or {@link IOption.None}. if no element matches it.
     *
     * @since 2.0.0
     */
    @NotNull
    @Contract(pure = true)
    public IOption<T> find(@NotNull IPredicate<? super T> predicate) {
        for (T item : this) {
            if (predicate.test(item)) {
                return IOption.some(item);
            }
        }
        return IOption.none();
    }

    /**
     * Tests if any of the elements match the given predicate.
     *
     * @since 2.0.0
     */
    @Contract(pure = true)
    public boolean any(@NotNull IPredicate<? super T> predicate) {
        return this.find(predicate).isDefined();
    }

    /**
     * Tests if no elements match the given predicate.
     *
     * @since 2.0.0
     */
    @Contract(pure = true)
    public boolean none(@NotNull IPredicate<? super T> predicate) {
        return !this.any(predicate);
    }

    /**
     * Tests if all the elements match the given predicate.
     *
     * @since 2.0.0
     */
    @Contract(pure = true)
    public boolean every(@NotNull IPredicate<? super T> predicate) {
        return this.filter(predicate).size() == this.size();
    }

    /**
     * Alias to {@link #every(IPredicate)}.
     *
     * @see #every(IPredicate)
     *
     * @since 2.0.0
     */
    @Contract(pure = true)
    public boolean all(@NotNull IPredicate<? super T> predicate) {
        return this.every(predicate);
    }

    /**
     * @since 1.0.0
     */
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

    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1) { return concatVarargs(it1); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1, Iterable<? extends T> it2) { return concatVarargs(it1, it2); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1, Iterable<? extends T> it2, Iterable<? extends T> it3) { return concatVarargs(it1, it2, it3); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1, Iterable<? extends T> it2, Iterable<? extends T> it3, Iterable<? extends T> it4) { return concatVarargs(it1, it2, it3, it4); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public final IList<T> concat(Iterable<? extends T> it1, Iterable<? extends T> it2, Iterable<? extends T> it3, Iterable<? extends T> it4, Iterable<? extends T> it5) { return concatVarargs(it1, it2, it3, it4, it5); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) public final IList<T> concat(Iterable<? extends T>... iterables) { return concatVarargs(iterables); }

    /**
     * @since 1.0.0
     */
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

    /**
     * Creates a new list containing all current elements minus the duplicates.
     * See: https://stackoverflow.com/a/15603260/1850609
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public IList<T> distinct() {
        return listOf(new LinkedHashSet<T>(immutableBackingList).iterator());
    }

    /**
     * @since 1.0.0
     */
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

    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1) { return subtractVarags(t1); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1, T t2) { return subtractVarags(t1, t2); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1, T t2, T t3) { return subtractVarags(t1, t2, t3); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1, T t2, T t3, T t4) { return subtractVarags(t1, t2, t3, t4); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> subtract(T t1, T t2, T t3, T t4, T t5) { return subtractVarags(t1, t2, t3, t4, t5); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) public IList<T> subtract(T... c) { return subtractVarags(c); }

    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> minus(T t1) { return subtractVarags(t1); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> minus(T t1, T t2) { return subtractVarags(t1, t2); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> minus(T t1, T t2, T t3) { return subtractVarags(t1, t2, t3); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> minus(T t1, T t2, T t3, T t4) { return subtractVarags(t1, t2, t3, t4); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> minus(T t1, T t2, T t3, T t4, T t5) { return subtractVarags(t1, t2, t3, t4, t5); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) public IList<T> minus(T... c) { return subtractVarags(c); }

    private IList<T> plusVarargs(@NotNull Iterable<? extends T>... others) {
        return concat(others);
    }

    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1) { return plusVarargs(it1); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2) { return plusVarargs(it1, it2); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3) { return plusVarargs(it1, it2, it3); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3, @NotNull Iterable<? extends T> it4) { return plusVarargs(it1, it2, it3, it4); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3, @NotNull Iterable<? extends T> it4, @NotNull Iterable<? extends T> it5) { return plusVarargs(it1, it2, it3, it4, it5); }
    /**
     * @since 1.0.0
     */
    @NotNull @Contract(pure = true) public IList<T> plus(@NotNull Iterable<? extends T>... others) { return plusVarargs(others); }

    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(@NotNull Iterable<? extends T> it1) { return plusVarargs(it1); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2) { return plusVarargs(it1, it2); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3) { return plusVarargs(it1, it2, it3); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3, @NotNull Iterable<? extends T> it4) { return plusVarargs(it1, it2, it3, it4); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(@NotNull Iterable<? extends T> it1, @NotNull Iterable<? extends T> it2, @NotNull Iterable<? extends T> it3, @NotNull Iterable<? extends T> it4, @NotNull Iterable<? extends T> it5) { return plusVarargs(it1, it2, it3, it4, it5); }
    /**
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) public IList<T> add(@NotNull Iterable<? extends T>... others) { return plusVarargs(others); }

    @SuppressWarnings("unchecked")
    private IList<T> plusVarargs(T... c) {
        return plusVarargs(listOf(c));
    }

    /**
     * Creates a new list containing all current elements plus the added one. Same as {@link #add(Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1) { return plusVarargs(t1); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #add(Object, Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1, T t2) { return plusVarargs(t1, t2); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #add(Object, Object, Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1, T t2, T t3) { return plusVarargs(t1, t2, t3); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #add(Object, Object, Object, Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1, T t2, T t3, T t4) { return plusVarargs(t1, t2, t3, t4); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #add(Object, Object, Object, Object, Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> plus(T t1, T t2, T t3, T t4, T t5) { return plusVarargs(t1, t2, t3, t4, t5); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #add(Object[])}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) public IList<T> plus(T... c) { return plusVarargs(c); }

    /**
     * Creates a new list containing all current elements plus the added one. Same as {@link #plus(Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(T t1) { return plusVarargs(t1); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #plus(Object, Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(T t1, T t2) { return plusVarargs(t1, t2); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #plus(Object, Object, Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(T t1, T t2, T t3) { return plusVarargs(t1, t2, t3); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #plus(Object, Object, Object, Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(T t1, T t2, T t3, T t4) { return plusVarargs(t1, t2, t3, t4); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #plus(Object, Object, Object, Object, Object)}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) @SuppressWarnings("unchecked") public IList<T> add(T t1, T t2, T t3, T t4, T t5) { return plusVarargs(t1, t2, t3, t4, t5); }
    /**
     * Creates a new list containing all current elements plus the added ones. Same as {@link #plus(Object[])}.
     * @since 2.0.0
     */
    @NotNull @Contract(pure = true) public IList<T> add(T... c) { return plusVarargs(c); }

    /**
     * @since 1.0.0
     */
    @Contract(pure = true)
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @since 1.0.0
     */
    @Contract(pure = true)
    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    /**
     * <p>Returns an {@link IOption} containing the first element of the list, if it exists and is not <code>null</code>.
     * If the list is empty <strong>or if the first element is <code>null</code></strong>, an {@link IOption.None} is returned.</p>
     *
     * <p>If the first element of your list may be <code>null</code>, use {@link #firstNullable()}.</p>
     *
     * @see #firstNullable()
     *
     * @return An {@link IOption.None} if the list is empty or the first element is <code>null</code>. Otherwise, an
     *  {@link IOption.Some} containing the first element of the list.
     *
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public IOption<T> first() {
        if (this.isEmpty()) {
            return IOption.none();
        }
        return IOption.ofNullable(immutableBackingList.get(0));
    }

    /**
     * Returns the first element of the list, or <code>null</code> if the list is empty.
     *
     * @see #first()
     *
     * @return <code>null</code> if the list is empty. Otherwise, returns its first element (which could be <code>null</code>).
     *
     * @since 2.0.0
     */
    @Nullable
    @Contract(pure = true)
    public T firstNullable() {
        if (this.isEmpty()) {
            return null;
        }
        return immutableBackingList.get(0);
    }

    /**
     * Returns a list of all elements yielded from results of mapper function being invoked on each element of the
     * original collection.
     *
     * @since 2.0.0
     */
    @NotNull
    @Contract(pure = true)
    public <R extends T> IOption<T> reduce(@NotNull IBiFunction<T, T, R> reducer) {
        Iterator<T> iterator = this.iterator();
        if (!iterator.hasNext()) return IOption.none();
        T accumulator = iterator.next();
        while (iterator.hasNext()) {
            accumulator = reducer.apply(accumulator, iterator.next());
        }
        return IOption.some(accumulator);
    }

    /**
     * Returns an array containing the elements of the list.
     *
     * @since 2.0.0
     */
    public T[] toArray(T[] a) {
        return immutableBackingList.toArray(a);
    }

    interface Reducer<T, R> {
        R reduce(R accumulator, T next);
    }

    /**
     * Returns a list of all elements yielded from results of mapper function being invoked on each element of the
     * original collection, accumulating to a value that starts with {@code initialValue}.
     *
     * @since 2.0.0
     */
    @NotNull
    @Contract(pure = true)
    public <R> R reduce(@NotNull Reducer<T, R> reducer, R initialValue) {
        Iterator<T> iterator = this.iterator();
        R accumulator = initialValue;
        while (iterator.hasNext()) {
            accumulator = reducer.reduce(accumulator, iterator.next());
        }
        return accumulator;
    }

    /**
     * @since 2.0.0
     */
    @NotNull
    public String join(String separator) {
        StringBuilder sb = new StringBuilder(immutableBackingList.size() * JOIN_FUNCTION_MEAN_EXPECTED_ELEMENTS_STRING_SIZE);
        for (int i = 0, backingListSize = immutableBackingList.size(); i < backingListSize; i++) {
            sb.append(immutableBackingList.get(i));
            if (i < backingListSize - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * @since 2.0.0
     */
    @NotNull
    public String join() {
        return this.join("");
    }

    /**
     * @since 2.0.0
     */
    @NotNull
    public <K> Map<K, T> associateBy(IFunction<T, K> keySelector) {
        Map<K, T> map = new HashMap<K, T>(immutableBackingList.size());
        for (T el : immutableBackingList) {
            map.put(keySelector.apply(el), el);
        }
        return map;
    }

    /**
     * @since 2.0.0
     */
    @NotNull
    public IList<T> peek(@NotNull IConsumer<? super T> consumer) {
        for (T item : this) {
            consumer.accept(item);
        }
        return this;
    }

    /**
     * @since 2.0.0
     */
    public void forEach(@NotNull IConsumer<? super T> consumer) {
        for (T item : this) {
            consumer.accept(item);
        }
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     *
     * @since 2.0.0
     */
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index has to be greater than zero and less than size(). Index: " + index + " Size: " + size());
        }
        return immutableBackingList.get(index);
    }

}
