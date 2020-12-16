package dev.acdcjunior.immutable.fn;


public interface ITriFunction<A, B, C, R> {

  R apply(A a, B b, C c);

}