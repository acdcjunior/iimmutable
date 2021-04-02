package dev.acdcjunior.immutable;

import dev.acdcjunior.immutable.fn.IConsumer;
import dev.acdcjunior.immutable.fn.IFunction;
import dev.acdcjunior.immutable.tuple.IPair;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;


public class IEitherTest {

    private static final String LEFT_ARG = "left";
    private static final String RIGHT_ARG = "right";

    private final IEither<String, Long> aLeft = IEither.left(LEFT_ARG);
    private final IEither<Long, String> aRight = IEither.right(RIGHT_ARG);

    @Test
    public void toString__hashCode() {
        assertThat(aLeft.toString()).isEqualTo("Left(" + LEFT_ARG + ")");
        assertThat(aRight.toString()).isEqualTo("Right(" + RIGHT_ARG + ")");

        assertThat(aLeft.hashCode()).isEqualTo(LEFT_ARG.hashCode());
        assertThat(aRight.hashCode()).isEqualTo(RIGHT_ARG.hashCode());
    }

    @Test
    public void left__builds_an_IEitherLeft() {
        assertThat(aLeft).isInstanceOf(IEither.Left.class);
        assertThat(((IEither.Left<String, Long>) aLeft).getValue()).isEqualTo(LEFT_ARG);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = IllegalArgumentException.class)
    public void left__errors_if_arg_is_null() {
        IEither.left(null);
    }

    @Test
    public void right__builds_an_IEitherRight() {
        assertThat(aRight).isInstanceOf(IEither.Right.class);
        assertThat(((IEither.Right<Long, String>) aRight).getValue()).isEqualTo(RIGHT_ARG);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = IllegalArgumentException.class)
    public void right__errors_if_arg_is_null() {
        IEither.right(null);
    }

    @Test
    public void toList_IEitherLeft___returns_emptyList() {
        IList<Long> ls = aLeft.toList();
        assertThat(ls).isEmpty();
    }

    @Test
    public void toList_IEitherRight___returns_singleElementList() {
        IList<String> rs = aRight.toList();
        assertThat(rs).containsExactly(RIGHT_ARG);
    }

    @Test
    public void left_IEitherLeft___returns_value() {
        assertThat(aLeft.left()).isEqualTo(LEFT_ARG);
    }

    @Test
    public void left_IEitherRight___returns_null() {
        assertThat(aLeft.right()).isNull();
    }

    @Test
    public void right_IEitherLeft___returns_null() {
        assertThat(aRight.left()).isNull();
    }

    @Test
    public void right_IEitherRight___returns_value() {
        assertThat(aRight.right()).isEqualTo(RIGHT_ARG);
    }

    @Test
    public void isLeft_IEitherLeft___returns_true() {
        assertThat(aLeft.isLeft()).isTrue();
    }

    @Test
    public void isDefined_IEitherLeft___returns_false() {
        assertThat(aLeft.isDefined()).isFalse();
        assertThat(aLeft.isRight()).isFalse();
    }

    @Test
    public void isLeft_IEitherRight___returns_false() {
        assertThat(aRight.isLeft()).isFalse();
    }

    @Test
    public void isDefined_IEitherRight___returns_true() {
        assertThat(aRight.isDefined()).isTrue();
        assertThat(aRight.isRight()).isTrue();
    }

    @Test
    public void ifLeft_IEitherLeft___executes() {
        final AtomicReference<String> a = new AtomicReference<String>("originalLeft");
        IEither<String, Long> r = aLeft.ifLeft(new IConsumer<String>() {
            @Override
            public void accept(String s) {
                a.set(a.get() + s);
            }
        });
        assertThat(a.get()).isEqualTo("originalLeft" + LEFT_ARG);
        assertThat(r).isSameAs(aLeft);
    }

    @Test
    public void ifLeft_IEitherRight___doesNothing() {
        final AtomicReference<String> a = new AtomicReference<String>("originalLeft2");
        IEither<String, Long> r = aLeft.ifRight(new IConsumer<Long>() {
            @Override
            public void accept(Long s) {
                a.set(a.get() + s);
            }
        });
        assertThat(a.get()).isEqualTo("originalLeft2");
        assertThat(r).isSameAs(aLeft);
    }

    @Test
    public void ifRight_IEitherLeft___doesNothing() {
        final AtomicReference<String> a = new AtomicReference<String>("originalRight");
        IEither<Long, String> r = aRight.ifLeft(new IConsumer<Long>() {
            @Override
            public void accept(Long s) {
                a.set(a.get() + s);
            }
        });
        assertThat(a.get()).isEqualTo("originalRight");
        assertThat(r).isSameAs(aRight);
    }

    @Test
    public void ifRight_IEitherRight___executes() {
        final AtomicReference<String> a = new AtomicReference<String>("originalRight2");
        IEither<Long, String> r = aRight.ifRight(new IConsumer<String>() {
            @Override
            public void accept(String s) {
                a.set(a.get() + s);
            }
        });
        assertThat(a.get()).isEqualTo("originalRight2" + RIGHT_ARG);
        assertThat(r).isSameAs(aRight);
    }

    @Test
    public void map_IEitherLeft___doesNothing() {
        IEither<String, String> mapped = aLeft.map(new IFunction<Long, String>() {
            @Override
            public String apply(Long input) {
                return "@@@";
            }
        });
        //noinspection AssertBetweenInconvertibleTypes
        assertThat(mapped).isEqualTo(aLeft);
    }

    @Test
    public void map_IEitherRight___executesAndConverts() {
        IEither<Long, IPair<String, String>> mapped = aRight.map(new IFunction<String, IPair<String, String>>() {
            @Override
            public IPair<String, String> apply(String input) {
                return IPair.pairOf(input + 1, input + 2);
            }
        });
        assertThat(mapped).isEqualTo(IEither.right(IPair.pairOf(RIGHT_ARG + 1, RIGHT_ARG + 2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void map_IEitherRight___nullReturnThrowsException() {
        aRight.map(new IFunction<String, IPair<String, String>>() {
            @Override
            public IPair<String, String> apply(String input) {
                return null;
            }
        });
    }

    @Test
    public void mapLeft_IEitherLeft___executesAndConverts() {
        IEither<IPair<String, String>, Long> mapped = aLeft.mapLeft(new IFunction<String, IPair<String, String>>() {
            @Override
            public IPair<String, String> apply(String input) {
                return IPair.pairOf(input + 1, input + 2);
            }
        });
        assertThat(mapped).isEqualTo(IEither.left(IPair.pairOf(LEFT_ARG + 1, LEFT_ARG + 2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void mapLeft_IEitherLeft___nullReturnThrowsException() {
        aLeft.mapLeft(new IFunction<String, Object>() {
            @Override
            public Object apply(String input) {
                return null;
            }
        });
    }

    @Test
    public void mapLeft_IEitherRight___doesNothing() {
        IEither<String, String> mapped = aRight.mapLeft(new IFunction<Long, String>() {
            @Override
            public String apply(Long input) {
                return "@@@";
            }
        });
        //noinspection AssertBetweenInconvertibleTypes
        assertThat(mapped).isEqualTo(aRight);
    }

    @Test
    public void equals_IEitherLeft() {
        assertThat(IEither.<String, Long>left(LEFT_ARG)).isEqualTo(IEither.<String, Long>left(LEFT_ARG));
        assertThat(IEither.<String, Long>left(LEFT_ARG)).isNotEqualTo(IEither.<String, Long>left(LEFT_ARG + 1));
    }

    @Test
    public void equals_IEitherRight() {
        assertThat(IEither.<Long, String>right(RIGHT_ARG)).isEqualTo(IEither.<Long, String>right(RIGHT_ARG));
        assertThat(IEither.<Long, String>right(RIGHT_ARG)).isNotEqualTo(IEither.<Long, String>right(RIGHT_ARG + 1));
    }

    @Test
    public void equals_not() {
        assertThat(IEither.<String, String>right(RIGHT_ARG)).isNotEqualTo(IEither.<String, String>left(RIGHT_ARG));
        assertThat(IEither.<String, String>left(LEFT_ARG)).isNotEqualTo(IEither.<String, String>right(LEFT_ARG));
    }

    @Test
    public void iterator_IEitherLeft() {
        assertThat(aLeft.iterator()).isEmpty();
    }

    @Test
    public void iterator_IEitherRight() {
        assertThat(aRight.iterator()).containsExactly(RIGHT_ARG);
    }

    @Test
    public void toOption_IEitherLeft___returns_none() {
        assertThat(aLeft.toOption()).isEqualTo(IOption.none());
    }

    @Test
    public void toOption_IEitherRight___returns_some() {
        assertThat(aRight.toOption()).isEqualTo(IOption.some(RIGHT_ARG));
    }

    @Test
    public void toOptionLeft_IEitherLeft___returns_some() {
        assertThat(aLeft.toOptionLeft()).isEqualTo(IOption.some(LEFT_ARG));
    }

    @Test
    public void toOptionLeft_IEitherRight___returns_none() {
        assertThat(aRight.toOptionLeft()).isEqualTo(IOption.none());
    }

    @Test
    public void flatMap_IEitherRight___returningRight() {
        IEither<Long, IPair<String, String>> mapped = aRight.flatMap(new IFunction<String, IEither<Long, IPair<String, String>>>() {
            @Override
            public IEither<Long, IPair<String, String>> apply(String input) {
                return IEither.right(IPair.pairOf(input + 1, input + 2));
            }
        });
        assertThat(mapped).isEqualTo(IEither.right(IPair.pairOf(RIGHT_ARG + 1, RIGHT_ARG + 2)));
    }

    @Test
    public void flatMap_IEitherRight___returningLeft() {
        IEither<Long, IPair<String, String>> mapped = aRight.flatMap(new IFunction<String, IEither<Long, IPair<String, String>>>() {
            @Override
            public IEither<Long, IPair<String, String>> apply(String input) {
                return IEither.left(112233L);
            }
        });
        assertThat(mapped).isEqualTo(IEither.left(112233L));
    }

    @Test
    public void flatMap_IEitherLeft___doesNothing() {
        IEither<String, IPair<String, String>> mapped = aLeft.flatMap(new IFunction<Long, IEither<String, IPair<String, String>>>() {
            @Override
            public IEither<String, IPair<String, String>> apply(Long input) {
                return null;
            }
        });
        //noinspection AssertBetweenInconvertibleTypes
        assertThat(mapped).isEqualTo(aLeft);
    }

    @Test
    public void getOrHandle() {
        assertThat(
            IEither.<String, Integer>right(12).getOrHandle(new IFunction<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return 17;
                }
            })
        ).isEqualTo(12);

        assertThat(
            IEither.<String, Integer>left("12").getOrHandle(new IFunction<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return Integer.parseInt(input) + 5;
                }
            })
        ).isEqualTo(17);
    }

    @Test
    public void swap() {
        assertThat(IEither.<String, Integer>right(123).swap()).isEqualTo(IEither.<Integer, String>left(123));
        assertThat(IEither.<String, Integer>left("abc").swap()).isEqualTo(IEither.<Integer, String>right("abc"));
    }


    @Test
    public void accept() {
        final AtomicReference<String> leftVal = new AtomicReference<String>("originalLeft");
        final AtomicReference<Long> rightVal = new AtomicReference<Long>(7777L);
        IConsumer<String> acceptLeftConsumer = new IConsumer<String>() {
            @Override
            public void accept(String s) {
                leftVal.set(leftVal.get() + s);
            }
        };
        IConsumer<Long> acceptRightConsumer = new IConsumer<Long>() {
            @Override
            public void accept(Long s) {
                rightVal.set(rightVal.get() + s);
            }
        };
        // when
        IEither.<String, Long>left("--left!").accept(acceptLeftConsumer, acceptRightConsumer);
        IEither.<String, Long>right(2222L).accept(acceptLeftConsumer, acceptRightConsumer);
        // then
        assertThat(leftVal.get()).isEqualTo("originalLeft" + "--left!");
        assertThat(rightVal.get()).isEqualTo(9999L);
    }

}
