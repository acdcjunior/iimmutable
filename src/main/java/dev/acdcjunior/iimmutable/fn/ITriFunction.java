package dev.acdcjunior.iimmutable.fn;


/**
 * @since 1.0.0
 */
public interface ITriFunction<A, B, C, R> {

    /**
     * @since 1.0.0
     */
    R apply(A a, B b, C c);

}