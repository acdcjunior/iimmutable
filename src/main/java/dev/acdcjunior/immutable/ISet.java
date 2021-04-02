package dev.acdcjunior.immutable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static dev.acdcjunior.immutable.IList.listOf;


/**
 * An immutable set.
 *
 * @param <T> The type of the elements.
 * @since 1.0.0
 */
public class ISet<T> {

    private final HashSet<T> immutableBackingSet;

    /**
     * @since 1.0.0
     */
    @Contract(pure = true)
    public static <T> ISet<T> setOf(T... elements) {
        return new ISet<T>(listOf(elements));
    }

    private ISet(@NotNull IList<T> elements) {
        immutableBackingSet = new HashSet<T>(elements.toList());
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public Set<T> toSet() {
        return immutableBackingSet;
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
        return "ISet" + immutableBackingSet;
    }

}
