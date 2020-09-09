package io.github.acdcjunior.java6fp.tuple;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FPPairTest {

    @Test
    public void pairOf() {
        FPPair<Integer, Integer> p = FPPair.pairOf(123, 456);
        assertThat(p.getA()).isEqualTo(123);
        assertThat(p.getB()).isEqualTo(456);
    }

}