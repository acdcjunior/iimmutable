package io.github.acdcjunior.java6fp;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class FPUtilsTest {

    private final List<Character> ab = Arrays.asList('a', 'b');

    @Test
    @SuppressWarnings("RedundantCast")
    public void toArrayList_Iterable() {
        assertThat(FPUtils.toArrayList(((Iterable<Character>) ab))).isEqualTo(ab);
    }

    @Test
    public void toArrayList_Iterator() {
        assertThat(FPUtils.toArrayList(ab.iterator())).isEqualTo(ab);
    }

}