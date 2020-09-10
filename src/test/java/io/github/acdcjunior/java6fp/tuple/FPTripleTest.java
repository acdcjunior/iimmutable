package io.github.acdcjunior.java6fp.tuple;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FPTripleTest {

    @Test
    public void tripleOf() {
        FPTriple<Integer, Integer, Integer> t = FPTriple.tripleOf(111, 222, 333);
        assertThat(t.getA()).isEqualTo(111);
        assertThat(t.getB()).isEqualTo(222);
        assertThat(t.getC()).isEqualTo(333);
    }

    @Test
    @SuppressWarnings("EqualsWithItself")
    public void equals() {
        assertThat(FPTriple.tripleOf(111, 222, 333).equals(FPTriple.tripleOf(111, 222, 333))).isTrue();
        assertThat(FPTriple.tripleOf(111, 222, 333).equals(FPTriple.tripleOf(111, 333, 222))).isFalse();
        assertThat(FPTriple.tripleOf(111, 222, 333).equals(FPTriple.tripleOf(111, 222, 444))).isFalse();
        assertThat(FPTriple.tripleOf(111, 222, 333).equals(FPTriple.tripleOf(111, 444, 333))).isFalse();
        assertThat(FPTriple.tripleOf(111, 222, 333).equals(FPTriple.tripleOf(444, 222, 333))).isFalse();
    }

}