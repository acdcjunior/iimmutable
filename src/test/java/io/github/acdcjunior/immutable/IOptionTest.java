package io.github.acdcjunior.immutable;

import io.github.acdcjunior.immutable.fn.ICommand;
import io.github.acdcjunior.immutable.fn.IConsumer;
import io.github.acdcjunior.immutable.fn.IFunction;
import io.github.acdcjunior.immutable.fn.ISupplier;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


public class IOptionTest {

    private static class Stuff {
        private final char val;

        public Stuff(char val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Stuff[" + val + "]";
        }
    }

    public static final Stuff SA = new Stuff('a');
    public static final Stuff SB = new Stuff('b');
    public static final ISupplier<Stuff> SB_SUPPLIER = new ISupplier<Stuff>() {
        @Override
        public Stuff get() {
            return SB;
        }
    };

    @Test
    public void some() {
        assertThat(IOption.some(SA)).isEqualTo(IOption.some(SA));
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void call() {
                IOption.some(null);
            }
        }).hasMessage("Argument of FPOption.Some cannot be null");
    }

    @Test
    public void none() {
        assertThat(IOption.none()).isEqualTo(IOption.none());
    }

    @Test
    public void ofNullable() {
        assertThat(IOption.ofNullable(SA)).isEqualTo(IOption.some(SA));
        assertThat(IOption.ofNullable(null)).isEqualTo(IOption.none());
    }

    @Test
    public void toList() {
        assertThat(IOption.some(SA).toList()).containsExactly(SA);
        assertThat(IOption.none().toList()).isEmpty();
    }

    @Test
    public void orNull() {
        assertThat(IOption.some(SA).orNull()).isSameAs(SA);
        assertThat(IOption.none().orNull()).isNull();
    }

    @Test
    public void getOrElse() {
        assertThat(IOption.some(SA).getOrElse(SB)).isSameAs(SA);
        assertThat(IOption.none().getOrElse(SB)).isSameAs(SB);
    }

    @Test
    public void getOrElse_fn() {
        assertThat(IOption.some(SA).getOrElse(SB_SUPPLIER)).isSameAs(SA);
        assertThat(IOption.<Stuff>none().getOrElse(SB_SUPPLIER)).isSameAs(SB);
    }

    @Test
    public void get() {
        assertThat(IOption.some(SA).get()).isSameAs(SA);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                IOption.none().get();
            }
        }).hasMessage("FPOption.None has no value");
    }

    @Test
    public void isEmpty() {
        assertThat(IOption.some(SA).isEmpty()).isFalse();
        assertThat(IOption.none().isEmpty()).isTrue();
    }

    @Test
    public void isDefined() {
        assertThat(IOption.some(SA).isDefined()).isTrue();
        assertThat(IOption.none().isDefined()).isFalse();
    }

    @Test
    public void ifEmpty() {
        final List<Stuff> ls = new ArrayList<Stuff>();
        assertThat(IOption.some(SA).ifEmpty(new ICommand() {
            @Override
            public void run() {
                ls.add(SA);
            }
        })).isEqualTo(IOption.some(SA));
        assertThat(IOption.<Stuff>none().ifEmpty(new ICommand() {
            @Override
            public void run() {
                ls.add(SB);
            }
        })).isEqualTo(IOption.<Stuff>none());

        assertThat(ls).containsExactly(SB);
    }

    @Test
    public void ifDefined() {
        final List<Stuff> ls = new ArrayList<Stuff>();
        assertThat(IOption.some(SA).ifDefined(new IConsumer<Stuff>() {
            @Override
            public void accept(Stuff stuff) {
                ls.add(stuff);
            }
        })).isEqualTo(IOption.some(SA));
        assertThat(IOption.<Stuff>none().ifDefined(new IConsumer<Stuff>() {
            @Override
            public void accept(Stuff stuff) {
                ls.add(stuff);
            }
        })).isEqualTo(IOption.<Stuff>none());

        assertThat(ls).containsExactly(SA);
    }

    @Test
    public void orElse() {
        assertThat(IOption.some(SA).orElse(SB)).isEqualTo(IOption.some(SA));
        assertThat(IOption.<Stuff>none().orElse(SB)).isEqualTo(IOption.some(SB));
    }

    @Test
    public void orElse_supply() {
        assertThat(IOption.some(SA).orElse(new ISupplier<Stuff>() {
            @Override
            public Stuff get() {
                return SB;
            }
        })).isEqualTo(IOption.some(SA));
        assertThat(IOption.<Stuff>none().orElse(new ISupplier<Stuff>() {
            @Override
            public Stuff get() {
                return SB;
            }
        })).isEqualTo(IOption.some(SB));
    }

    @Test
    public void orElseFlat() {
        assertThat(IOption.some(SA).orElseFlat(IOption.some(SB))).isEqualTo(IOption.some(SA));
        assertThat(IOption.<Stuff>none().orElseFlat(IOption.some(SB))).isEqualTo(IOption.some(SB));
    }

    @Test
    public void orElseFlat_supply() {
        assertThat(IOption.some(SA).orElseFlat(new ISupplier<IOption<Stuff>>() {
            @Override
            public IOption<Stuff> get() {
                return IOption.some(SB);
            }
        })).isEqualTo(IOption.some(SA));
        assertThat(IOption.<Stuff>none().orElseFlat(new ISupplier<IOption<Stuff>>() {
            @Override
            public IOption<Stuff> get() {
                return IOption.some(SB);
            }
        })).isEqualTo(IOption.some(SB));
    }

    @Test
    public void filter() {
        assertThat(IOption.some(SA).filter(new IFunction<Stuff, Boolean>() {
            @Override
            public Boolean apply(Stuff input) {
                return true;
            }
        })).isEqualTo(IOption.some(SA));
        assertThat(IOption.some(SA).filter(new IFunction<Stuff, Boolean>() {
            @Override
            public Boolean apply(Stuff input) {
                return false;
            }
        })).isEqualTo(IOption.none());
        assertThat(IOption.<Stuff>none().filter(new IFunction<Stuff, Boolean>() {
            @Override
            public Boolean apply(Stuff input) {
                return null;
            }
        })).isEqualTo(IOption.none());
    }

    @Test
    public void map() {
        assertThat(IOption.some(SA).map(new IFunction<Stuff, Stuff>() {
            @Override
            public Stuff apply(Stuff input) {
                return SB;
            }
        })).isEqualTo(IOption.some(SB));
        assertThat(IOption.some(SA).map(new IFunction<Stuff, Stuff>() {
            @Override
            public Stuff apply(Stuff input) {
                return null;
            }
        })).isEqualTo(IOption.none());
        assertThat(IOption.<Stuff>none().map(new IFunction<Stuff, Stuff>() {
            @Override
            public Stuff apply(Stuff input) {
                return SA;
            }
        })).isEqualTo(IOption.none());
    }

    @Test
    public void flatMap() {
        assertThat(IOption.some(SA).flatMap(new IFunction<Stuff, IOption<Stuff>>() {
            @Override
            public IOption<Stuff> apply(Stuff input) {
                return IOption.some(SB);
            }
        })).isEqualTo(IOption.some(SB));
        assertThat(IOption.some(SA).flatMap(new IFunction<Stuff, IOption<Stuff>>() {
            @Override
            public IOption<Stuff> apply(Stuff input) {
                return null;
            }
        })).isEqualTo(IOption.none());
        assertThat(IOption.<Stuff>none().flatMap(new IFunction<Stuff, IOption<Stuff>>() {
            @Override
            public IOption<Stuff> apply(Stuff input) {
                return IOption.some(SA);
            }
        })).isEqualTo(IOption.none());
    }

    @Test
    public void forEach() {
        final List<Stuff> ls = new ArrayList<Stuff>();
        IOption.some(SA).forEach(new IConsumer<Stuff>() {
            @Override
            public void accept(Stuff stuff) {
                ls.add(stuff);
            }
        });
        IOption.<Stuff>none().forEach(new IConsumer<Stuff>() {
            @Override
            public void accept(Stuff stuff) {
                ls.add(SB);
            }
        });

        assertThat(ls).containsExactly(SA);
    }

    @Test
    public void iterator() {
        List<Stuff> ls = new ArrayList<Stuff>();
        for (Stuff stuff : IOption.some(SA)) {
            ls.add(stuff);
        }
        for (Stuff ignored : IOption.<Stuff>none()) {
            ls.add(SB);
        }

        assertThat(ls).containsExactly(SA);
    }

}