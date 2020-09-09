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