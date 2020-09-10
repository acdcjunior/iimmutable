package io.github.acdcjunior.java6fp.tuple;


public class FPPair<A, B> {

    public static <A, B> FPPair<A, B> pairOf(A a, B b) {
        return new FPPair<A, B>(a, b);
    }

    private final A a;
    private final B b;

    public FPPair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FPPair<?, ?> fpPair = (FPPair<?, ?>) o;
        if (a != null ? !a.equals(fpPair.a) : fpPair.a != null) return false;
        return b != null ? b.equals(fpPair.b) : fpPair.b == null;
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