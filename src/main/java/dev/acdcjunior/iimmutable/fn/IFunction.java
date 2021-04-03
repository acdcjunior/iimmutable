package dev.acdcjunior.iimmutable.fn;


/**
 * @since 1.0.0
 */
public interface IFunction<P, R> {

    /**
     * @since 1.0.0
     */
    R apply(P input);

}