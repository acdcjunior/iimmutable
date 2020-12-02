package dev.acdcjunior.immutable;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class IListUtilsTest {

    private final List<Character> ab = Arrays.asList('a', 'b');

    @Test
    @SuppressWarnings("RedundantCast")
    public void toArrayList_Iterable() {
        assertThat(IListUtils.toArrayList(((Iterable<Character>) ab))).isEqualTo(ab);
    }

    @Test
    public void toArrayList_Iterator() {
        assertThat(IListUtils.toArrayList(ab.iterator())).isEqualTo(ab);
    }

}
