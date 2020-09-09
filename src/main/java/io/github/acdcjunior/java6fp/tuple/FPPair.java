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

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

}