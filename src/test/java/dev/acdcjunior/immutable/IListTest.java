package dev.acdcjunior.immutable;

import dev.acdcjunior.immutable.fn.IBiFunction;
import dev.acdcjunior.immutable.fn.IFunction;
import dev.acdcjunior.immutable.fn.IPredicate;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static dev.acdcjunior.immutable.Wrapper.w;
import static dev.acdcjunior.immutable.WrapperChild.wc;
import static org.assertj.core.api.Assertions.assertThat;


public class IListTest {

    public static final IPredicate<String> ALL_CAPS_PREDICATE = new IPredicate<String>() {
        @Override
        public boolean test(String s) {
            return s.matches("[A-Z]+");
        }
    };

    public static final IFunction<Object, String> TO_STRING_MAPPER = new IFunction<Object, String>() {
        @Override
        public String apply(Object input) {
            return input.toString();
        }
    };

    @Test
    public void empty__is_an_alias_to_none() {
        assertThat(IOption.empty()).isSameAs(IOption.none());
    }

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
    public void flatMap() {
        IList<Wrapper> ss = IList.listOf(w("a"), w("b")).flatMap(new IFunction<Wrapper, Iterable<? extends Wrapper>>() {
            @Override
            public Iterable<? extends Wrapper> apply(Wrapper input) {
                return IList.listOf(wc(input.w.toUpperCase()), wc(input.w + input.w.toUpperCase()));
            }
        });
        assertThat(ss.map(TO_STRING_MAPPER)).containsExactly("wc:W:A", "wc:w:aW:A", "wc:W:B", "wc:w:bW:B");
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
        List<Integer> asList = IList.listOf(iterable).toList();
        assertThat(asList).isEqualTo(is);
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
        assertThat(IList.listOf((Object) null).first().isDefined()).isEqualTo(false);
    }

    @Test
    public void firstNullable() {
        assertThat(IList.listOf("aw", "be").firstNullable()).isEqualTo("aw");
        assertThat(IList.listOf().firstNullable()).isNull();
        assertThat(IList.listOf((Object) null).firstNullable()).isNull();
    }

    @Test
    public void emptyList() {
        assertThat(IList.emptyList()).isEmpty();
        assertThat(IList.emptyList()).isSameAs(IList.emptyList());
    }

    @Test
    public void find() {
        assertThat(IList.listOf("aw", "be").find(ALL_CAPS_PREDICATE)).isEqualTo(IOption.none());
        assertThat(IList.listOf("aw", "BE").find(ALL_CAPS_PREDICATE)).isEqualTo(IOption.some("BE"));
        assertThat(IList.<String>listOf().find(ALL_CAPS_PREDICATE)).isEqualTo(IOption.none());
    }

    @Test
    public void any() {
        assertThat(IList.listOf("aw", "be").any(ALL_CAPS_PREDICATE)).isEqualTo(false);
        assertThat(IList.listOf("aw", "BE").any(ALL_CAPS_PREDICATE)).isEqualTo(true);
        assertThat(IList.<String>listOf().any(ALL_CAPS_PREDICATE)).isEqualTo(false);
    }

    @Test
    public void none() {
        assertThat(IList.listOf("aw", "be").none(ALL_CAPS_PREDICATE)).isEqualTo(true);
        assertThat(IList.listOf("aw", "BE").none(ALL_CAPS_PREDICATE)).isEqualTo(false);
        assertThat(IList.<String>listOf().none(ALL_CAPS_PREDICATE)).isEqualTo(true);
    }

    @Test
    public void every() {
        assertThat(IList.listOf("aw", "be").every(ALL_CAPS_PREDICATE)).isEqualTo(false);
        assertThat(IList.listOf("aw", "BE").every(ALL_CAPS_PREDICATE)).isEqualTo(false);
        assertThat(IList.listOf("AW", "BE").every(ALL_CAPS_PREDICATE)).isEqualTo(true);
        assertThat(IList.<String>listOf().none(ALL_CAPS_PREDICATE)).isEqualTo(true);
    }

    @Test
    public void all() {
        assertThat(IList.listOf("AW", "BE").all(ALL_CAPS_PREDICATE)).isEqualTo(IList.listOf("AW", "BE").every(ALL_CAPS_PREDICATE));
    }

    @Test
    public void reduce__noInitialValue____empty_list() {
        assertThat(IList.<Wrapper>listOf().reduce(new IBiFunction<Wrapper, Wrapper, Wrapper>() {
            @Override
            public Wrapper apply(Wrapper wrapper, Wrapper wrapper2) {
                return null;
            }
        })).isEqualTo(IOption.none());
    }

    @Test
    public void reduce__noInitialValue() {
        assertThat(IList.listOf(w("a"), w("b"), w("c")).reduce(new IBiFunction<Wrapper, Wrapper, Wrapper>() {
            @Override
            public Wrapper apply(Wrapper w1, Wrapper w2) {
                return w(w1.w + "/" + w2.w);
            }
        }).get().w).isEqualTo("w:w:w:a/w:b/w:c");
    }

    @Test
    public void reduce() {
        assertThat(IList.listOf(w("100"), w("10000"), w("1000000")).reduce(new IList.Reducer<Wrapper, Integer>() {
            @Override
            public Integer reduce(Integer accumulator, Wrapper next) {
                return accumulator + Integer.parseInt(next.w.split(":")[1]);
            }
        }, 1)).isEqualTo(1010101);
    }

    @Test
    public void toArray() {
        assertThat(IList.listOf("a", "b", "c").toArray(new String[0])).isEqualTo(new String[] { "a", "b", "c"});
    }

    @Test
    public void join_separator() {
        assertThat(IList.listOf("a", "b", "c").join("!")).isEqualTo("a!b!c");
        assertThat(IList.listOf().join("!")).isEqualTo("");
    }

    @Test
    public void join_noArg() {
        assertThat(IList.listOf("a", "b", "c").join()).isEqualTo("abc");
        assertThat(IList.listOf().join()).isEqualTo("");
    }

    @Test
    public void associateBy() {
        // TODO
    }

    @Test
    public void peek() {
        // TODO
    }

    @Test
    public void forEach() {
        // TODO
    }

    @Test
    public void get() {
        IList<String> ss = IList.listOf("a", "b", "c");
        assertThat(ss.get(0)).isEqualTo("a");
        assertThat(ss.get(2)).isEqualTo("c");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get__negative_index() {
        IList.listOf("a").get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get__index_greater_than_size() {
        IList.listOf("a", "b", "c").get(9);
    }

}

class Wrapper {
    static Wrapper w(String w) { return new Wrapper(w); }
    final String w;
    Wrapper(String w) { this.w = "w:" + w; }
    @Override public String toString() { return w; }
}
@SuppressWarnings("unused")
class WrapperChild extends Wrapper {
    static WrapperChild wc(String wc) { return new WrapperChild(wc); }
    final String wc;
    WrapperChild(String wc) { super("child:" + wc); this.wc = "wc:" + wc; }
    @Override public String toString() { return wc; }
}