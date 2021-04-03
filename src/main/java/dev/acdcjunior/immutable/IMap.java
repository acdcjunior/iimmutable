package dev.acdcjunior.immutable;

import dev.acdcjunior.immutable.fn.IFunction;
import dev.acdcjunior.immutable.fn.IPredicate;
import dev.acdcjunior.immutable.fn.ISupplier;
import dev.acdcjunior.immutable.tuple.IPair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * An immutable map.
 *
 * @param <K> The type of the keys.
 * @param <V> The type of the values.
 *
 * @since 1.0.0
 */
public class IMap<K, V> {

    /**
     * @param entry1 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])}.
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <K, V> IMap<K, V> mapOf(@NotNull IPair<K, V> entry1) {
        return mapOf(IList.listOf(entry1));
    }

    /**
     * @param entry1 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])}.
     * @param entry2 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])}.
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <K, V> IMap<K, V> mapOf(@NotNull IPair<K, V> entry1, @NotNull IPair<K, V> entry2) {
        return mapOf(IList.listOf(entry1, entry2));
    }

    /**
     * @param entry1 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])}.
     * @param entry2 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])}.
     * @param entry3 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])}.
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <K, V> IMap<K, V> mapOf(@NotNull IPair<K, V> entry1, @NotNull IPair<K, V> entry2, @NotNull IPair<K, V> entry3) {
        return mapOf(IList.listOf(entry1, entry2, entry3));
    }

    /**
     * @param entry1 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @param entry2 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @param entry3 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @param entry4 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <K, V> IMap<K, V> mapOf(@NotNull IPair<K, V> entry1, @NotNull IPair<K, V> entry2, @NotNull IPair<K, V> entry3, @NotNull IPair<K, V> entry4) {
        return mapOf(IList.listOf(entry1, entry2, entry3, entry4));
    }

    /**
     * @param entry1 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @param entry2 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @param entry3 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @param entry4 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @param entry5 Entry for the map. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <K, V> IMap<K, V> mapOf(@NotNull IPair<K, V> entry1, @NotNull IPair<K, V> entry2, @NotNull IPair<K, V> entry3, @NotNull IPair<K, V> entry4, @NotNull IPair<K, V> entry5) {
        return mapOf(IList.listOf(entry1, entry2, entry3, entry4, entry5));
    }

    /**
     * @param entries List of entries. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <K, V> IMap<K, V> mapOf(@NotNull IPair<K, V>... entries) {
        return mapOf(IList.listOf(entries));
    }

    /**
     * @param entryList List of entries. Cannot be {@code null}. (If you wish to pass {@code null}, see {@link #mapOfNonNull(IPair[])} .
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <K, V> IMap<K, V> mapOf(IList<IPair<K, V>> entryList) {
        int indexOfFirstNullElement = entryList.indexOf(new IPredicate<IPair<K, V>>() {
            @Override
            public boolean test(IPair<K, V> input) {
                return input == null;
            }
        });
        if (indexOfFirstNullElement != -1) {
            throw new NullPointerException("Entry on index " + indexOfFirstNullElement + " passed to mapOf() is null. If this is intentional, use #mapOfNonNull() instead");
        }
        return new IMap<K, V>(entryList.associateBy(new IFunction<IPair<K, V>, K>() {
            @Override
            public K apply(IPair<K, V> input) {
                return input.getKey();
            }
        }, new IFunction<IPair<K,V>, V>() {
            @Override
            public V apply(IPair<K, V> input) {
                return input.getValue();
            }
        }));
    }

    /**
     * @param entries List of entries. {@code null} values are skipped silently.
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <K, V> IMap<K, V> mapOfNonNull(IPair<K, V>... entries) {
        return mapOf(IList.listOf(entries).filterNonNull());
    }

    @NotNull
    private final Map<K, V> immutableBackingMap;

    private IMap(@NotNull Map<K, V> immutableBackingMap) {
        this.immutableBackingMap = Collections.unmodifiableMap(immutableBackingMap);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IMap<?, ?> iMap = (IMap<?, ?>) o;
        return immutableBackingMap.equals(iMap.immutableBackingMap);
    }

    /**
     * @since 1.0.0
     */
    @Override
    public int hashCode() {
        return immutableBackingMap.hashCode();
    }

    /**
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return immutableBackingMap.toString();
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public Map<K, V> toMap() {
        return immutableBackingMap;
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public Map<K, V> toMutableMap() {
        return new LinkedHashMap<K, V>(immutableBackingMap);
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public <L extends K, U extends V> IMap<K, V> put(L key, U value) {
        Map<K, V> copy = toMutableMap();
        copy.put(key, value);
        return new IMap<K, V>(copy);
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public V get(K key) {
        return immutableBackingMap.get(key);
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public V getOrDefault(K key, @Nullable final V defaultValue) {
        return getOrDefault(key, new ISupplier<V>() {
            @Override
            public V get() {
                return defaultValue;
            }
        });
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public V getOrDefault(K key, @NotNull ISupplier<V> defaultValueSupplier) {
        Check.notNull(defaultValueSupplier, "defaultValueSupplier cannot be null");
        if (immutableBackingMap.containsKey(key)) {
            return immutableBackingMap.get(key);
        }
        return defaultValueSupplier.get();
    }

}