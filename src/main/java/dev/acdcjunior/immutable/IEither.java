package dev.acdcjunior.immutable;

import dev.acdcjunior.immutable.fn.IConsumer;
import dev.acdcjunior.immutable.fn.IFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

import static dev.acdcjunior.immutable.IList.listOf;


/**
 * Right-biased Either implementation.
 *
 * @param <L> type of left value
 * @param <R> type of right value
 *
 * @since 1.0.0
 */
public abstract class IEither<L, R> implements Iterable<R> {

    /**
     * @since 1.0.0
     */
    @NotNull
    public static <L, R> IEither<L, R> left(@NotNull L left) {
        return new Left<L, R>(left);
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    public static <L, R> IEither<L, R> right(@NotNull R right) {
        return new Right<L, R>(right);
    }

    @NotNull
    public abstract IList<R> toList();

    @Nullable
    public abstract L left();

    @Nullable
    public abstract R right();

    public abstract boolean isLeft();

    public boolean isDefined() {
        return !isLeft();
    }

    @NotNull
    @Override
    public Iterator<R> iterator() {
        return toList().iterator();
    }

    @NotNull
    public abstract IOption<R> toOption();

    @NotNull
    public abstract IOption<L> toOptionLeft();

    @NotNull
    public abstract IEither<L, R> ifLeft(@NotNull IConsumer<? super L> fn);

    @NotNull
    public abstract IEither<L, R> ifRight(@NotNull IConsumer<? super R> fn);

    @NotNull
    public abstract <R2> IEither<L, R2> map(@NotNull IFunction<? super R, R2> mapper);

    @NotNull
    public abstract <L2> IEither<L2, R> mapLeft(@NotNull IFunction<? super L, L2> mapper);

    @NotNull
    public abstract <R2> IEither<L, R2> flatMap(@NotNull IFunction<? super R, IEither<L, R2>> mapper);

    /**
     * <p>
     * Returns the result of the execution of {@code foldLeft} if the current instance is a {@link IEither.Left}
     * or of {@code foldRight} if this is a {@link IEither.Right}.
     * </p>
     *
     * Example:
     * <pre><code>
     * String result = (IEither&lt;A, B&gt; myEither).fold(
     *      (a) -&gt; "was an A: " + a,
     *      (b) -&gt; "was a B: " + b
     * )
     * </code></pre>
     *
     * @param foldLeft the function to apply when the current instance is a {@link IEither.Left}
     * @param foldRight the function to apply when the current instance is a {@link IEither.Right}
     * @return the results of applying the appropriate function
     * @since 2.0.0
     */
    @NotNull
    @SuppressWarnings("ConstantConditions")
    public <T> T fold(@NotNull IFunction<? super L, T> foldLeft, @NotNull IFunction<? super R, T> foldRight) {
        if (isLeft()) return mapLeft(foldLeft).left();
        return map(foldRight).right();
    }

    /**
     * If this is a {@link IEither.Right}, return its value. If a {@link IEither.Left}, execute {@code handleLeft}
     * in its value, so it can be transformed into a {@link IEither.Right}.
     *
     * Example:
     * <pre><code>
     * String result = (IEither&lt;Long, String&gt;.left(123).getOrHandle(i -> String.valueOf(i));
     * // result === "123"
     * </code></pre>
     * @since 2.0.0
     */
    public R getOrHandle(IFunction<L, R> handleLeft) {
        return fold(handleLeft, new IFunction<R, R>() {
            @Override
            public R apply(R input) {
                return input;
            }
        });
    }

    /**
     * Creates an {@link IEither} with the left value in its right and the right value in its left.
     *
     * @return An {@link IEither.Left} if this is an {@link IEither.Right} and vice-versa.
     * @since 2.0.0
     */
    @Contract(pure = true)
    public IEither<R, L> swap() {
        return fold(new IFunction<L, IEither<R, L>>() {
            @Override
            public IEither<R, L> apply(L input) {
                return IEither.right(input);
            }
        }, new IFunction<R, IEither<R, L>>() {
            @Override
            public IEither<R, L> apply(R input) {
                return IEither.left(input);
            }
        });
    }

    /**
     * @since 2.0.0
     */
    public void accept(@NotNull IConsumer<? super L> ifLeft, @NotNull IConsumer<? super R> ifRight) {
        ifLeft(ifLeft);
        ifRight(ifRight);
    }

    public static final class Left<L, R> extends IEither<L, R> {
        @NotNull
        private final L value;

        @SuppressWarnings("ConstantConditions")
        private Left(@NotNull L value) {
            if (value == null) {
                throw new IllegalArgumentException("Argument of IEither.Left cannot be null");
            }
            this.value = value;
        }

        @NotNull
        public L getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.format("Left(%s)", value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Left<?, ?> right = (Left<?, ?>) o;
            return value.equals(right.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @NotNull
        @Override
        public IList<R> toList() {
            return IList.emptyList();
        }

        @NotNull
        @Override
        public L left() {
            return getValue();
        }

        @Nullable
        @Override
        public R right() {
            return null;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @NotNull
        @Override
        public IOption<R> toOption() {
            return IOption.none();
        }

        @NotNull
        @Override
        public IOption<L> toOptionLeft() {
            return IOption.some(getValue());
        }

        @NotNull
        @Override
        public IEither<L, R> ifLeft(@NotNull IConsumer<? super L> fn) {
            fn.accept(getValue());
            return this;
        }

        @NotNull
        @Override
        public IEither<L, R> ifRight(@NotNull IConsumer<? super R> fn) {
            return this;
        }

        @NotNull
        @Override
        @SuppressWarnings("unchecked")
        public <R2> IEither<L, R2> map(@NotNull IFunction<? super R, R2> mapper) {
            return (IEither<L, R2>) this;
        }

        @NotNull
        @Override
        public <L2> IEither<L2, R> mapLeft(@NotNull IFunction<? super L, L2> mapper) {
            return new Left<L2, R>(mapper.apply(getValue()));
        }

        @NotNull
        @Override
        @SuppressWarnings("unchecked")
        public <R2> IEither<L, R2> flatMap(@NotNull IFunction<? super R, IEither<L, R2>> mapper) {
            return (IEither<L, R2>) this;
        }
    }


    public static final class Right<L, R> extends IEither<L, R> {
        @NotNull
        private final R value;

        @SuppressWarnings("ConstantConditions")
        private Right(@NotNull R value) {
            if (value == null) {
                throw new IllegalArgumentException("Argument of IEither.Right cannot be null");
            }
            this.value = value;
        }

        @NotNull
        public R getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.format("Right(%s)", value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Right<?, ?> right = (Right<?, ?>) o;
            return value.equals(right.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @NotNull
        @Override
        public IList<R> toList() {
            return listOf(this.value);
        }

        @Nullable
        @Override
        public L left() {
            return null;
        }

        @NotNull
        @Override
        public R right() {
            return getValue();
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @NotNull
        @Override
        public IOption<R> toOption() {
            return IOption.some(getValue());
        }

        @NotNull
        @Override
        public IOption<L> toOptionLeft() {
            return IOption.none();
        }

        @NotNull
        @Override
        public IEither<L, R> ifLeft(@NotNull IConsumer<? super L> fn) {
            return this;
        }

        @NotNull
        @Override
        public IEither<L, R> ifRight(@NotNull IConsumer<? super R> fn) {
            fn.accept(getValue());
            return this;
        }

        @NotNull
        @Override
        public <R2> IEither<L, R2> map(@NotNull IFunction<? super R, R2> mapper) {
            return new Right<L, R2>(mapper.apply(getValue()));
        }

        @NotNull
        @Override
        @SuppressWarnings("unchecked")
        public <L2> IEither<L2, R> mapLeft(@NotNull IFunction<? super L, L2> mapper) {
            return (IEither<L2, R>) this;
        }

        @NotNull
        @Override
        public <R2> IEither<L, R2> flatMap(@NotNull IFunction<? super R, IEither<L, R2>> mapper) {
            return mapper.apply(getValue());
        }
    }

}
