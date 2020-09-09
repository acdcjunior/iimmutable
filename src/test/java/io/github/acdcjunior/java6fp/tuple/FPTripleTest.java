package io.github.acdcjunior.java6fp.tuple;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class FPTripleTest {

    @Test
    public void tripleOf() {
        FPTriple<Integer, Integer, Integer> t = FPTriple.tripleOf(111, 222, 333);
        Assertions.assertThat(t.getA()).isEqualTo(111);
        Assertions.assertThat(t.getB()).isEqualTo(222);
        Assertions.assertThat(t.getC()).isEqualTo(333);
    }

}