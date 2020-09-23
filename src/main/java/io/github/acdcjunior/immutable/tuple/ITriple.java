package io.github.acdcjunior.immutable.tuple;


public class ITriple<A, B, C> {

    public static <A, B, C> ITriple<A, B, C> tripleOf(A a, B b, C c) {
        return new ITriple<A, B, C>(a, b, c);
    }

    private final A a;
    private final B b;
    private final C c;

    public ITriple(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ITriple<?, ?, ?> iTriple = (ITriple<?, ?, ?>) o;
        if (a != null ? !a.equals(iTriple.a) : iTriple.a != null) return false;
        if (b != null ? !b.equals(iTriple.b) : iTriple.b != null) return false;
        return c != null ? c.equals(iTriple.c) : iTriple.c == null;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }

}