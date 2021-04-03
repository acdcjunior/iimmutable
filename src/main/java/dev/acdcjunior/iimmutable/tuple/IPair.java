package dev.acdcjunior.iimmutable.tuple;


/**
 * @since 1.0.0
 */
public class IPair<A, B> {

    /**
     * @since 1.0.0
     */
    public static <A, B> IPair<A, B> pairOf(A a, B b) {
        return new IPair<A, B>(a, b);
    }

    private final A a;
    private final B b;

    private IPair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * @since 1.0.0
     */
    @Override
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
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    /**
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "(" + a + ", " + b + ')';
    }

    /**
     * @since 1.0.0
     */
    public A getLeft() {
        return a;
    }

    /**
     * Alias to {@link #getLeft()}.
     *
     * @since 1.0.0
     */
    public A left() {
        return a;
    }

    /**
     * Alias to {@link #getLeft()}.
     *
     * @since 1.0.0
     */
    public A getA() {
        return a;
    }

    /**
     * Alias to {@link #getLeft()}.
     *
     * @since 1.0.0
     */
    public A getKey() {
        return a;
    }

    /**
     * Alias to {@link #getLeft()}.
     *
     * @since 1.0.0
     */
    public A key() {
        return a;
    }

    /**
     * @since 1.0.0
     */
    public B getRight() {
        return b;
    }

    /**
     * Alias to {@link #getRight()}.
     *
     * @since 1.0.0
     */
    public B right() {
        return b;
    }

    /**
     * Alias to {@link #getRight()}.
     *
     * @since 1.0.0
     */
    public B getB() {
        return b;
    }

    /**
     * Alias to {@link #getRight()}.
     *
     * @since 1.0.0
     */
    public B getValue() {
        return b;
    }

    /**
     * Alias to {@link #getRight()}.
     *
     * @since 1.0.0
     */
    public B value() {
        return b;
    }

}
