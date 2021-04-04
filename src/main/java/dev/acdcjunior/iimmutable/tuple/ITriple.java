package dev.acdcjunior.iimmutable.tuple;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @since 1.0.0
 */
public class ITriple<A, B, C> {

    /**
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static <A, B, C> ITriple<A, B, C> tripleOf(@Nullable A a, @Nullable B b, @Nullable C c) {
        return new ITriple<A, B, C>(a, b, c);
    }

    @Nullable
    private final A a;
    @Nullable
    private final B b;
    @Nullable
    private final C c;

    private ITriple(@Nullable A a, @Nullable B b, @Nullable C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * @since 1.0.0
     */
    @Override
    @Contract(pure = true)
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ITriple<?, ?, ?> iTriple = (ITriple<?, ?, ?>) o;
        if (a != null ? !a.equals(iTriple.a) : iTriple.a != null) return false;
        if (b != null ? !b.equals(iTriple.b) : iTriple.b != null) return false;
        return c != null ? c.equals(iTriple.c) : iTriple.c == null;
    }

    /**
     * @since 1.0.0
     */
    @Override
    @Contract(pure = true)
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public A getA() {
        return a;
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public B getB() {
        return b;
    }

    /**
     * @since 1.0.0
     */
    @Nullable
    @Contract(pure = true)
    public C getC() {
        return c;
    }

}
