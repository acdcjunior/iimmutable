package dev.acdcjunior.immutable.fn;


public interface IBiFunction<A, B, R> {

  R apply(A a, B b);

}