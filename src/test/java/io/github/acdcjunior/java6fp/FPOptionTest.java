package io.github.acdcjunior.java6fp;

import io.github.acdcjunior.java6fp.fn.FPCommand;
import io.github.acdcjunior.java6fp.fn.FPConsumer;
import io.github.acdcjunior.java6fp.fn.FPFunction;
import io.github.acdcjunior.java6fp.fn.FPSupplier;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


public class FPOptionTest {

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
    public static final FPSupplier<Stuff> SB_SUPPLIER = new FPSupplier<Stuff>() {
        @Override
        public Stuff get() {
            return SB;
        }
    };

    @Test
    public void some() {
        assertThat(FPOption.some(SA)).isEqualTo(FPOption.some(SA));
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void call() {
                FPOption.some(null);
            }
        }).hasMessage("Argument of FPOption.Some cannot be null");
    }

    @Test
    public void none() {
        assertThat(FPOption.none()).isEqualTo(FPOption.none());
    }

    @Test
    public void ofNullable() {
        assertThat(FPOption.ofNullable(SA)).isEqualTo(FPOption.some(SA));
        assertThat(FPOption.ofNullable(null)).isEqualTo(FPOption.none());
    }

    @Test
    public void toList() {
        assertThat(FPOption.some(SA).toList()).containsExactly(SA);
        assertThat(FPOption.none().toList()).isEmpty();
    }

    @Test
    public void orNull() {
        assertThat(FPOption.some(SA).orNull()).isSameAs(SA);
        assertThat(FPOption.none().orNull()).isNull();
    }

    @Test
    public void getOrElse() {
        assertThat(FPOption.some(SA).getOrElse(SB)).isSameAs(SA);
        assertThat(FPOption.none().getOrElse(SB)).isSameAs(SB);
    }

    @Test
    public void getOrElse_fn() {
        assertThat(FPOption.some(SA).getOrElse(SB_SUPPLIER)).isSameAs(SA);
        assertThat(FPOption.<Stuff>none().getOrElse(SB_SUPPLIER)).isSameAs(SB);
    }

    @Test
    public void get() {
        assertThat(FPOption.some(SA).get()).isSameAs(SA);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                FPOption.none().get();
            }
        }).hasMessage("FPOption.None has no value");
    }

    @Test
    public void isEmpty() {
        assertThat(FPOption.some(SA).isEmpty()).isFalse();
        assertThat(FPOption.none().isEmpty()).isTrue();
    }

    @Test
    public void isDefined() {
        assertThat(FPOption.some(SA).isDefined()).isTrue();
        assertThat(FPOption.none().isDefined()).isFalse();
    }

    @Test
    public void ifEmpty() {
        final List<Stuff> ls = new ArrayList<Stuff>();
        assertThat(FPOption.some(SA).ifEmpty(new FPCommand() {
            @Override
            public void run() {
                ls.add(SA);
            }
        })).isEqualTo(FPOption.some(SA));
        assertThat(FPOption.<Stuff>none().ifEmpty(new FPCommand() {
            @Override
            public void run() {
                ls.add(SB);
            }
        })).isEqualTo(FPOption.<Stuff>none());

        assertThat(ls).containsExactly(SB);
    }

    @Test
    public void ifDefined() {
        final List<Stuff> ls = new ArrayList<Stuff>();
        assertThat(FPOption.some(SA).ifDefined(new FPConsumer<Stuff>() {
            @Override
            public void accept(Stuff stuff) {
                ls.add(stuff);
            }
        })).isEqualTo(FPOption.some(SA));
        assertThat(FPOption.<Stuff>none().ifDefined(new FPConsumer<Stuff>() {
            @Override
            public void accept(Stuff stuff) {
                ls.add(stuff);
            }
        })).isEqualTo(FPOption.<Stuff>none());

        assertThat(ls).containsExactly(SA);
    }

    @Test
    public void orElse() {
        assertThat(FPOption.some(SA).orElse(SB)).isEqualTo(FPOption.some(SA));
        assertThat(FPOption.<Stuff>none().orElse(SB)).isEqualTo(FPOption.some(SB));
    }

    @Test
    public void orElse_supply() {
        assertThat(FPOption.some(SA).orElse(new FPSupplier<Stuff>() {
            @Override
            public Stuff get() {
                return SB;
            }
        })).isEqualTo(FPOption.some(SA));
        assertThat(FPOption.<Stuff>none().orElse(new FPSupplier<Stuff>() {
            @Override
            public Stuff get() {
                return SB;
            }
        })).isEqualTo(FPOption.some(SB));
    }

    @Test
    public void orElseFlat() {
        assertThat(FPOption.some(SA).orElseFlat(FPOption.some(SB))).isEqualTo(FPOption.some(SA));
        assertThat(FPOption.<Stuff>none().orElseFlat(FPOption.some(SB))).isEqualTo(FPOption.some(SB));
    }

    @Test
    public void orElseFlat_supply() {
        assertThat(FPOption.some(SA).orElseFlat(new FPSupplier<FPOption<Stuff>>() {
            @Override
            public FPOption<Stuff> get() {
                return FPOption.some(SB);
            }
        })).isEqualTo(FPOption.some(SA));
        assertThat(FPOption.<Stuff>none().orElseFlat(new FPSupplier<FPOption<Stuff>>() {
            @Override
            public FPOption<Stuff> get() {
                return FPOption.some(SB);
            }
        })).isEqualTo(FPOption.some(SB));
    }

    @Test
    public void filter() {
        assertThat(FPOption.some(SA).filter(new FPFunction<Stuff, Boolean>() {
            @Override
            public Boolean apply(Stuff input) {
                return true;
            }
        })).isEqualTo(FPOption.some(SA));
        assertThat(FPOption.some(SA).filter(new FPFunction<Stuff, Boolean>() {
            @Override
            public Boolean apply(Stuff input) {
                return false;
            }
        })).isEqualTo(FPOption.none());
        assertThat(FPOption.<Stuff>none().filter(new FPFunction<Stuff, Boolean>() {
            @Override
            public Boolean apply(Stuff input) {
                return null;
            }
        })).isEqualTo(FPOption.none());
    }

    @Test
    public void map() {
        assertThat(FPOption.some(SA).map(new FPFunction<Stuff, Stuff>() {
            @Override
            public Stuff apply(Stuff input) {
                return SB;
            }
        })).isEqualTo(FPOption.some(SB));
        assertThat(FPOption.some(SA).map(new FPFunction<Stuff, Stuff>() {
            @Override
            public Stuff apply(Stuff input) {
                return null;
            }
        })).isEqualTo(FPOption.none());
        assertThat(FPOption.<Stuff>none().map(new FPFunction<Stuff, Stuff>() {
            @Override
            public Stuff apply(Stuff input) {
                return SA;
            }
        })).isEqualTo(FPOption.none());
    }

    @Test
    public void flatMap() {
        assertThat(FPOption.some(SA).flatMap(new FPFunction<Stuff, FPOption<Stuff>>() {
            @Override
            public FPOption<Stuff> apply(Stuff input) {
                return FPOption.some(SB);
            }
        })).isEqualTo(FPOption.some(SB));
        assertThat(FPOption.some(SA).flatMap(new FPFunction<Stuff, FPOption<Stuff>>() {
            @Override
            public FPOption<Stuff> apply(Stuff input) {
                return null;
            }
        })).isEqualTo(FPOption.none());
        assertThat(FPOption.<Stuff>none().flatMap(new FPFunction<Stuff, FPOption<Stuff>>() {
            @Override
            public FPOption<Stuff> apply(Stuff input) {
                return FPOption.some(SA);
            }
        })).isEqualTo(FPOption.none());
    }

    @Test
    public void forEach() {
        final List<Stuff> ls = new ArrayList<Stuff>();
        FPOption.some(SA).forEach(new FPConsumer<Stuff>() {
            @Override
            public void accept(Stuff stuff) {
                ls.add(stuff);
            }
        });
        FPOption.<Stuff>none().forEach(new FPConsumer<Stuff>() {
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
        for (Stuff stuff : FPOption.some(SA)) {
            ls.add(stuff);
        }
        for (Stuff ignored : FPOption.<Stuff>none()) {
            ls.add(SB);
        }

        assertThat(ls).containsExactly(SA);
    }

}