package io.github.acdcjunior.immutable.fn;


public interface IFunction<P, R> {

  R apply(P input);

}