package dev.acdcjunior.immutable.fn;


/**
 * @since 1.1.0
 */
public interface IBiPredicate<A, B> {

  boolean test(A a, B b);

}