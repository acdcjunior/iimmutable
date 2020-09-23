package io.github.acdcjunior.immutable.fn;


public interface IFunction<F, T> {

  T apply(F input);

}