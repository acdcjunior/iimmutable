package dev.acdcjunior.immutable.fn;


public interface IFunction<P, R> {

  R apply(P input);

}