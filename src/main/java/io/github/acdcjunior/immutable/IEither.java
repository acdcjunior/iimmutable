package io.github.acdcjunior.immutable;

import io.github.acdcjunior.immutable.fn.IConsumer;
import io.github.acdcjunior.immutable.fn.IFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

import static io.github.acdcjunior.immutable.IList.listOf;


/**
 * Right-biased Either implementation.
 *
 * @param <L> type of left value
 * @param <R> type of right value
 */
public abstract class IEither<L, R> implements Iterable<R> {

    @NotNull
    public static <L, R> IEither<L, R> left(@NotNull L left) {
        return new Left<L, R>(left);
    }

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
    public abstract IEither<L, R> ifLeft(@NotNull IConsumer<L> fn);

    @NotNull
    public abstract IEither<L, R> ifRight(@NotNull IConsumer<R> fn);

    @NotNull
    public abstract <R2> IEither<L, R2> map(@NotNull IFunction<? super R, R2> mapper);

    @NotNull
    public abstract <L2> IEither<L2, R> mapLeft(@NotNull IFunction<? super L, L2> mapper);

    @NotNull
    public abstract <R2> IEither<L, R2> flatMap(@NotNull IFunction<? super R, IEither<L, R2>> mapper);

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
        public IEither<L, R> ifLeft(@NotNull IConsumer<L> fn) {
            fn.accept(getValue());
            return this;
        }

        @NotNull
        @Override
        public IEither<L, R> ifRight(@NotNull IConsumer<R> fn) {
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
        public IEither<L, R> ifLeft(@NotNull IConsumer<L> fn) {
            return this;
        }

        @NotNull
        @Override
        public IEither<L, R> ifRight(@NotNull IConsumer<R> fn) {
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

