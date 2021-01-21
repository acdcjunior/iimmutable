package dev.acdcjunior.immutable.tuple;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class IPairTest {

    @Test
    public void pairOf() {
        IPair<Integer, Integer> p = IPair.pairOf(123, 456);
        assertThat(p.getA()).isEqualTo(123);
        assertThat(p.getB()).isEqualTo(456);
    }

    @Test
    @SuppressWarnings("EqualsWithItself")
    public void equals() {
        assertThat(IPair.pairOf(123, 456).equals(IPair.pairOf(123, 456))).isTrue();
        assertThat(IPair.pairOf(123, 456).equals(IPair.pairOf(456, 123))).isFalse();
    }

    @Test
    public void __toString() {
        assertThat(IPair.pairOf(123, "456").toString()).isEqualTo("(123, 456)");
    }

    @Test
    public void getLeft_getRight_getA_getB_left_right() {
        IPair<Integer, Integer> p = IPair.pairOf(123, 456);
        assertThat(p.getLeft()).isEqualTo(123);
        assertThat(p.getA()).isEqualTo(123);
        assertThat(p.left()).isEqualTo(123);
        assertThat(p.getRight()).isEqualTo(456);
        assertThat(p.getB()).isEqualTo(456);
        assertThat(p.right()).isEqualTo(456);
    }

}
