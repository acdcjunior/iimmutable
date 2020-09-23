package io.github.acdcjunior.immutable;

import io.github.acdcjunior.immutable.fn.IFunction;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("unchecked")
public class IListTest {

    @Test
    public void map() {
        IList<Character> cs = IList.listOf(Arrays.asList('a', 'b').iterator()).map(new IFunction<Character, Character>() {
            @Override
            public Character apply(Character input) {
                return Character.toUpperCase(input);
            }
        });
        assertThat(cs).containsExactly('A', 'B');
    }


    @Test
    public void concat() {
        IList<Character> cs = IList.listOf('a', 'b').concat(IList.listOf('c', 'd'), IList.listOf('e', 'f'));
        assertThat(cs).containsExactly('a', 'b', 'c', 'd', 'e', 'f');
    }

    @Test
    public void distinct() {
        IList<Character> cs = IList.listOf('a', 'b', 'a', 'c').distinct();
        assertThat(cs).containsExactly('a', 'b', 'c');
    }

    @Test
    public void subtract_list() {
        IList<Character> cs = IList.listOf('a', 'b', 'a', 'c').subtract(IList.listOf('a', 'c'));
        assertThat(cs).containsExactly('b');
    }

    @Test
    public void subtract_elements() {
        IList<Character> cs = IList.listOf('a', 'b', 'd', 'a', 'c').subtract('c', 'd');
        assertThat(cs).containsExactly('a', 'b', 'a');
    }

    @Test
    public void plus_list() {
        IList<Character> cs = IList.listOf('a', 'b', 'a', 'c').plus(IList.listOf('x', 'y'));
        assertThat(cs).containsExactly('a', 'b', 'a', 'c', 'x', 'y');
    }

    @Test
    public void plus_elements() {
        IList<Character> cs = IList.listOf('a', 'b', 'd', 'a', 'c').plus('c', 'x');
        assertThat(cs).containsExactly('a', 'b', 'd', 'a', 'c', 'c', 'x');
    }

    @Test
    public void filterNonNull() {
        IList<Character> cs = IList.listOf(null, 'x', null).filterNonNull();
        assertThat(cs).containsExactly('x');
    }

    @Test
    public void toString_test() {
        String cs = IList.listOf("aw", "be").toString();
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
        List<Integer> fpl = IList.listOf(iterable).toList();
        assertThat(fpl).isEqualTo(is);
    }

    @Test
    public void equals() {
        String a = "a";
        String b = "b";
        IList<String> la = IList.listOf(a, b);
        IList<String> lb = IList.listOf(a, b);
        assertThat(la.equals(lb)).isTrue();
    }

    @Test
    public void isEmpty() {
        assertThat(IList.listOf("aw", "be").isEmpty()).isEqualTo(false);
        assertThat(IList.listOf().isEmpty()).isEqualTo(true);
    }

    @Test
    public void isNotEmpty() {
        assertThat(IList.listOf("aw", "be").isNotEmpty()).isEqualTo(true);
        assertThat(IList.listOf().isNotEmpty()).isEqualTo(false);
    }

    @Test
    public void first() {
        assertThat(IList.listOf("aw", "be").first().isDefined()).isEqualTo(true);
        assertThat(IList.listOf().first().isDefined()).isEqualTo(false);
    }

}