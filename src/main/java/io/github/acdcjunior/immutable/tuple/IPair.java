package io.github.acdcjunior.immutable.tuple;


public class IPair<A, B> {

    public static <A, B> IPair<A, B> pairOf(A a, B b) {
        return new IPair<A, B>(a, b);
    }

    private final A a;
    private final B b;

    public IPair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPair<?, ?> iPair = (IPair<?, ?>) o;
        if (a != null ? !a.equals(iPair.a) : iPair.a != null) return false;
        return b != null ? b.equals(iPair.b) : iPair.b == null;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

}