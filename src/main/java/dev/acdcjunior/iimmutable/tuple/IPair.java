package dev.acdcjunior.iimmutable.tuple;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @since 1.0.0
 */
public class IPair<A, B> {

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <A, B> IPair<A, B> pairOf(@Nullable A a, @Nullable B b) {
        return new IPair<A, B>(a, b);
    }

    @Nullable
    private final A a;
    @Nullable
    private final B b;

    private IPair(@Nullable A a, @Nullable B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * @since 1.0.0
     */
    @Override
    @Contract(pure = true)
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPair<?, ?> iPair = (IPair<?, ?>) o;
        if (a != null ? !a.equals(iPair.a) : iPair.a != null) return false;
        return b != null ? b.equals(iPair.b) : iPair.b == null;
    }

    /**
     * @since 1.0.0
     */
    @Override
    @Contract(pure = true)
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    /**
     * @since 1.0.0
     */
    @Override
    @NotNull
    @Contract(pure = true)
    public String toString() {
        return "(" + a + ", " + b + ')';
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public A getLeft() {
        return a;
    }

    /**
     * Alias to {@link #getLeft()}.
     *
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public A left() {
        return a;
    }

    /**
     * Alias to {@link #getLeft()}.
     *
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public A getA() {
        return a;
    }

    /**
     * Alias to {@link #getLeft()}.
     *
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public A getKey() {
        return a;
    }

    /**
     * Alias to {@link #getLeft()}.
     *
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public A key() {
        return a;
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public B getRight() {
        return b;
    }

    /**
     * Alias to {@link #getRight()}.
     *
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public B right() {
        return b;
    }

    /**
     * Alias to {@link #getRight()}.
     *
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public B getB() {
        return b;
    }

    /**
     * Alias to {@link #getRight()}.
     *
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public B getValue() {
        return b;
    }

    /**
     * Alias to {@link #getRight()}.
     *
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public B value() {
        return b;
    }

}
