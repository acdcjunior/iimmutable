package dev.acdcjunior.immutable.fn;


/**
 * @since 1.0.0
 */
public interface IBiFunction<A, B, R> {

    /**
     * @since 1.0.0
     */
    R apply(A a, B b);

}