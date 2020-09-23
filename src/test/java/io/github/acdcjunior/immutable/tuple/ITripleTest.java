package io.github.acdcjunior.immutable.tuple;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ITripleTest {

    @Test
    public void tripleOf() {
        ITriple<Integer, Integer, Integer> t = ITriple.tripleOf(111, 222, 333);
        assertThat(t.getA()).isEqualTo(111);
        assertThat(t.getB()).isEqualTo(222);
        assertThat(t.getC()).isEqualTo(333);
    }

    @Test
    @SuppressWarnings("EqualsWithItself")
    public void equals() {
        assertThat(ITriple.tripleOf(111, 222, 333).equals(ITriple.tripleOf(111, 222, 333))).isTrue();
        assertThat(ITriple.tripleOf(111, 222, 333).equals(ITriple.tripleOf(111, 333, 222))).isFalse();
        assertThat(ITriple.tripleOf(111, 222, 333).equals(ITriple.tripleOf(111, 222, 444))).isFalse();
        assertThat(ITriple.tripleOf(111, 222, 333).equals(ITriple.tripleOf(111, 444, 333))).isFalse();
        assertThat(ITriple.tripleOf(111, 222, 333).equals(ITriple.tripleOf(444, 222, 333))).isFalse();
    }

}