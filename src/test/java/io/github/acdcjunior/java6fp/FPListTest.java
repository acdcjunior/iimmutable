package io.github.acdcjunior.java6fp;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;


@SuppressWarnings("unchecked")
public class FPListTest {

    @Test
    public void map() {
        FPList<Character> cs = FPList.listOf(Arrays.asList('a', 'b').iterator()).map(new FPFunction<Character, Character>() {
            @Override
            public Character apply(Character input) {
                return Character.toUpperCase(input);
            }
        });
        assertThat(cs).containsExactly('A', 'B');
    }


    @Test
    public void concat() {
        FPList<Character> cs = FPList.listOf('a', 'b').concat(FPList.listOf('c', 'd'), FPList.listOf('e', 'f'));
        assertThat(cs).containsExactly('a', 'b', 'c', 'd', 'e', 'f');
    }

    @Test
    public void distinct() {
        FPList<Character> cs = FPList.listOf('a', 'b', 'a', 'c').distinct();
        assertThat(cs).containsExactly('a', 'b', 'c');
    }

    @Test
    public void subtract_list() {
        FPList<Character> cs = FPList.listOf('a', 'b', 'a', 'c').subtract(FPList.listOf('a', 'c'));
        assertThat(cs).containsExactly('b');
    }

    @Test
    public void subtract_elements() {
        FPList<Character> cs = FPList.listOf('a', 'b', 'd', 'a', 'c').subtract('c', 'd');
        assertThat(cs).containsExactly('a', 'b', 'a');
    }

    @Test
    public void plus_list() {
        FPList<Character> cs = FPList.listOf('a', 'b', 'a', 'c').plus(FPList.listOf('x', 'y'));
        assertThat(cs).containsExactly('a', 'b', 'a', 'c', 'x', 'y');
    }

    @Test
    public void plus_elements() {
        FPList<Character> cs = FPList.listOf('a', 'b', 'd', 'a', 'c').plus('c', 'x');
        assertThat(cs).containsExactly('a', 'b', 'd', 'a', 'c', 'c', 'x');
    }

    @Test
    public void filterNonNull() {
        FPList<Character> cs = FPList.listOf(null, 'x', null).filterNonNull();
        assertThat(cs).containsExactly('x');
    }

    @Test
    public void toString_test() {
        String cs = FPList.listOf("aw", "be").toString();
        assertThat(cs).isEqualTo("[aw, be]");
    }

    @Test
    public void iterable__and__toList() {
        final List<Integer> is = Arrays.asList(3, 5, 7);
        Iterable<Integer> iterable = new Iterable<Integer>() {
            @NotNull
            @Override
            public Iterator<Integer> iterator() {
                return is.iterator();
            }
        };
        List<Integer> fpl = FPList.listOf(iterable).toList();
        assertThat(fpl).isEqualTo(is);
    }

    @Test
    public void equals() {
        String a = "a";
        String b = "b";
        FPList<String> la = FPList.listOf(a, b);
        FPList<String> lb = FPList.listOf(a, b);
        assertThat(la.equals(lb)).isTrue();
    }

}