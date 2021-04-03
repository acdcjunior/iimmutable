package dev.acdcjunior.iimmutable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static dev.acdcjunior.iimmutable.IList.listOf;


/**
 * An immutable set.
 *
 * @param <T> The type of the elements.
 *
 * @since 1.0.0
 */
public class ISet<T> {

    private final Set<T> immutableBackingSet;

    /**
     * @since 1.0.0
     */
    @Contract(pure = true)
    public static <T> ISet<T> setOf(T... elements) {
        return new ISet<T>(listOf(elements));
    }

    private ISet(@NotNull IList<T> elements) {
        immutableBackingSet = Collections.unmodifiableSet(new HashSet<T>(elements.toList()));
    }

    /**
     * Returns an immutable {@link Set} instance.
     *
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public Set<T> toSet() {
        return immutableBackingSet;
    }

    /**
     * Returns an mutable {@link Set} instance.
     *
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public Set<T> toMutableSet() {
        return new HashSet<T>(immutableBackingSet);
    }

    /**
     * @since 1.0.0
     */
    @Override
    @Contract(pure = true)
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ISet<?> iSet = (ISet<?>) o;

        return immutableBackingSet.equals(iSet.immutableBackingSet);
    }

    /**
     * @since 1.0.0
     */
    @Override
    @Contract(pure = true)
    public int hashCode() {
        return immutableBackingSet.hashCode();
    }

    /**
     * @since 1.0.0
     */
    @Override
    @Contract(pure = true)
    public String toString() {
        return immutableBackingSet.toString();
    }

}
