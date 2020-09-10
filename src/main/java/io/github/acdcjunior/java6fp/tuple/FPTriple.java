package io.github.acdcjunior.java6fp.tuple;


public class FPTriple<A, B, C> {

    public static <A, B, C> FPTriple<A, B, C> tripleOf(A a, B b, C c) {
        return new FPTriple<A, B, C>(a, b, c);
    }

    private final A a;
    private final B b;
    private final C c;

    public FPTriple(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FPTriple<?, ?, ?> fpTriple = (FPTriple<?, ?, ?>) o;
        if (a != null ? !a.equals(fpTriple.a) : fpTriple.a != null) return false;
        if (b != null ? !b.equals(fpTriple.b) : fpTriple.b != null) return false;
        return c != null ? c.equals(fpTriple.c) : fpTriple.c == null;
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