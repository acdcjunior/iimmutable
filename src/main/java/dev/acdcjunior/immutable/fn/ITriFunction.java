package dev.acdcjunior.immutable.fn;


/**
 * @since 1.1.0
 */
public interface ITriFunction<A, B, C, R> {

  R apply(A a, B b, C c);

}