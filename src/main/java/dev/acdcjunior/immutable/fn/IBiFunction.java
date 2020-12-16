package dev.acdcjunior.immutable.fn;


/**
 * @since 1.1.0
 */
public interface IBiFunction<A, B, R> {

  R apply(A a, B b);

}