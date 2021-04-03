package dev.acdcjunior.iimmutable;

import dev.acdcjunior.iimmutable.fn.IBiFunction;
import dev.acdcjunior.iimmutable.fn.IConsumer;
import dev.acdcjunior.iimmutable.fn.IFunction;
import dev.acdcjunior.iimmutable.fn.IPredicate;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.*;

import static dev.acdcjunior.iimmutable.TestUtils.assertThatThrownBy;
import static dev.acdcjunior.iimmutable.Wrapper.w;
import static dev.acdcjunior.iimmutable.WrapperChild.wc;
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

    private final IList<Wrapper> iList = IList.listOf(w("a"), w("b"), w("c"));

    @Test(expected = UnsupportedOperationException.class)
    public void toList__returned_list_must_be_immutable___add() {
        List<Wrapper> list = iList.toList();
        list.add(w("new"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void toList__returned_list_must_be_immutable___set() {
        List<Wrapper> list = iList.toList();
        list.set(0, w("new"));
    }

    @Test
    public void toMutableList() {
        List<Wrapper> mutableList = iList.toMutableList();
        assertThat(mutableList).isEqualTo(iList.toList());
        mutableList.add(w("new"));
        assertThat(mutableList).isEqualTo(IList.listOf(w("a"), w("b"), w("c"), w("new")).toList());
    }

    @Test
    public void listOf__Iterator_null_returns_emptyList() {
        assertThat(IList.listOf((Iterator<Object>) null)).isEqualTo(IList.emptyList());
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
    public void mapIndexed() {
        IList<String> cs = IList.listOf(Arrays.asList('a', 'b').iterator()).mapIndexed(new IBiFunction<Integer, Character, String>() {
            @Override
            public String apply(Integer index, Character input) {
                return String.valueOf(index) + Character.toUpperCase(input);
            }
        });
        assertThat(cs).containsExactly("0A", "1B");
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
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equals__hashCode() {
        String a = "a";
        String b = "b";
        IList<String> la = IList.listOf(a, b);
        IList<String> lb = IList.listOf(a, b);
        assertThat(la.equals(lb)).isTrue();
        assertThat(la.hashCode()).isEqualTo(lb.hashCode());

        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(a, b));
        assertThat(la.equals(arrayList)).isTrue();
        assertThat(la.hashCode()).isEqualTo(arrayList.hashCode());
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
    public void firstOrNull() {
        assertThat(IList.listOf("aw", "be").firstOrNull()).isEqualTo("aw");
        assertThat(IList.listOf().firstOrNull()).isNull();
        assertThat(IList.listOf((Object) null).firstOrNull()).isNull();
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
    public void indexOf() {
        assertThat(iList.indexOf(w("a"))).isEqualTo(0);
        assertThat(iList.indexOf(w("b"))).isEqualTo(1);
        assertThat(iList.indexOf(w("@"))).isEqualTo(-1);

        assertThat(iList.indexOf(new IPredicate<Wrapper>() {
            @Override
            public boolean test(Wrapper input) {
                return input.w.equals("w:a");
            }
        })).isEqualTo(0);
        assertThat(iList.indexOf(new IPredicate<Wrapper>() {
            @Override
            public boolean test(Wrapper input) {
                return input.w.equals("w:b");
            }
        })).isEqualTo(1);
        assertThat(iList.indexOf(new IPredicate<Wrapper>() {
            @Override
            public boolean test(Wrapper input) {
                return input.w.equals("@");
            }
        })).isEqualTo(-1);
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
        Map<String, Wrapper> map = new HashMap<String, Wrapper>();
        map.put(iList.get(0).wKey(), iList.get(0));
        map.put(iList.get(1).wKey(), iList.get(1));
        map.put(iList.get(2).wKey(), iList.get(2));

        assertThat(iList.associateBy(new IFunction<Wrapper, String>() {
            @Override
            public String apply(Wrapper input) {
                return input.wKey();
            }
        })).isEqualTo(map);
    }

    @Test
    public void associateBy__valueTransform() {
        Map<String, Character> map = new HashMap<String, Character>();
        map.put(iList.get(0).wKey(), iList.get(0).toString().charAt(2));
        map.put(iList.get(1).wKey(), iList.get(1).toString().charAt(2));
        map.put(iList.get(2).wKey(), iList.get(2).toString().charAt(2));

        assertThat(iList.associateBy(new IFunction<Wrapper, String>() {
            @Override
            public String apply(Wrapper input) {
                return input.wKey();
            }
        }, new IFunction<Wrapper, Character>() {
            @Override
            public Character apply(Wrapper input) {
                return input.w.charAt(2);
            }
        })).isEqualTo(map);
    }

    @Test
    public void peek() {
        final ArrayList<Wrapper> ws = new ArrayList<Wrapper>();

        assertThat(iList.peek(new IConsumer<Wrapper>() {
            @Override
            public void accept(Wrapper wrapper) {
                ws.add(wrapper);
            }
        }).toString()).isEqualTo("[w:a, w:b, w:c]");

        assertThat(ws).isEqualTo(iList.toList());
    }

    @Test
    public void forEach() {
        final ArrayList<Wrapper> ws = new ArrayList<Wrapper>();

        iList.forEach(new IConsumer<Wrapper>() {
            @Override
            public void accept(Wrapper wrapper) {
                ws.add(wrapper);
            }
        });

        assertThat(ws).isEqualTo(iList.toList());
    }

    @Test
    public void get() {
        IList<String> ss = IList.listOf("a", "b", "c");
        assertThat(ss.get(0)).isEqualTo("a");
        assertThat(ss.get(2)).isEqualTo("c");
    }

    @Test
    public void get__rangeCheck_exceptions() {
        final IList<String> ls = IList.listOf("a", "b");
        assertThatThrownBy(new ThrowingRunnable() {
            @Override
            public void run() {
                ls.get(-1);
            }
        }).isInstanceOf(IndexOutOfBoundsException.class).hasMessageContaining(
                "Cannot `get(-1)`: Index (-1) must be equal to or greater than zero and less than size (2)"
        );
        assertThatThrownBy(new ThrowingRunnable() {
            @Override
            public void run() {
                ls.get(99);
            }
        }).isInstanceOf(IndexOutOfBoundsException.class).hasMessageContaining(
                "Cannot `get(99)`: Index (99) must be equal to or greater than zero and less than size (2)"
        );
    }

    @Test
    public void set() {
        assertThat(iList.set(0, w("@"))).isEqualTo(IList.listOf(w("@"), w("b"), w("c")));
        assertThat(iList.set(1, w("@"))).isEqualTo(IList.listOf(w("a"), w("@"), w("c")));
        assertThat(iList.set(2, w("@"))).isEqualTo(IList.listOf(w("a"), w("b"), w("@")));
    }

    @Test
    public void remove() {
        assertThat(iList.remove(0)).isEqualTo(IList.listOf(w("b"), w("c")));
        assertThat(iList.remove(1)).isEqualTo(IList.listOf(w("a"), w("c")));
        assertThat(iList.remove(2)).isEqualTo(IList.listOf(w("a"), w("b")));
    }

    @Test
    public void contains() {
        assertThat(iList.contains(w("a"))).isTrue();
        assertThat(iList.contains(w("@"))).isFalse();
    }

    @Test
    public void subList() {
        IList<String> ls = IList.listOf("a", "b", "c", "d", "e");
        IList<String> subLs = ls.subList(0, 2);
        assertThat(subLs).isEqualTo(IList.listOf("a", "b"));
    }

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void subList__rangeCheck_exceptions() {
        final IList<String> ls = IList.listOf("a", "b");

        assertThatThrownBy(new ThrowingRunnable() {
            @Override
            public void run() {
                ls.subList(-1, 1);
            }
        }).isInstanceOf(IndexOutOfBoundsException.class).hasMessageContaining(
                "Cannot `subList(-1, 1)`: fromIndex (-1) must be equal to or greater than zero and less than size (2)"
        );

        assertThatThrownBy(new ThrowingRunnable() {
            @Override
            public void run() {
                ls.subList(0, 3);
            }
        }).isInstanceOf(IndexOutOfBoundsException.class).hasMessageContaining(
                "Cannot `toIndex(0, 3)`: toIndex (3) must be equal to or less than size (2)"
        );

        assertThatThrownBy(new ThrowingRunnable() {
            @Override
            public void run() {
                ls.subList(1, 0);
            }
        }).isInstanceOf(IndexOutOfBoundsException.class).hasMessageContaining(
                "Cannot `toIndex(1, 0)`: fromIndex (1) must be equal to or less than toIndex (0)"
        );
    }

}

class Wrapper {
    static Wrapper w(String w) { return new Wrapper(w); }
    final String w;
    Wrapper(@NotNull String w) { this.w = "w:" + w; }
    public String wKey() { return "KEY("+w+")"; }
    @Override public String toString() { return w; }
    @Override public boolean equals(Object o) { return o instanceof Wrapper && ((Wrapper) o).w.equals(w); }
    @Override public int hashCode() { return w.hashCode(); }
}
@SuppressWarnings("unused")
class WrapperChild extends Wrapper {
    static WrapperChild wc(String wc) { return new WrapperChild(wc); }
    final String wc;
    WrapperChild(@NotNull String wc) { super("child:" + wc); this.wc = "wc:" + wc; }
    @Override public String toString() { return wc; }
    @Override public boolean equals(Object o) { return o instanceof WrapperChild && ((WrapperChild) o).w.equals(w) && ((WrapperChild) o).wc.equals(wc); }
    @Override public int hashCode() { return 31 * super.hashCode() + wc.hashCode(); }
}