package dev.acdcjunior.immutable;

import dev.acdcjunior.immutable.fn.ICommand;
import dev.acdcjunior.immutable.fn.IConsumer;
import dev.acdcjunior.immutable.fn.IFunction;
import dev.acdcjunior.immutable.fn.ISupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * @since 1.0.0
 */
public abstract class IOption<T> implements Iterable<T> {

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract List<T> toList();

    /**
     * Returns the current option's value or <code>null</code> if this option is {@link None}.
     *
     * @return The value for this option, <code>null</code> if this option is {@link None}.
     *
     * @since 1.0.0
     */
    @Nullable
    public abstract T orNull();

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract T getOrElse(@NotNull T orElse);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract T getOrElse(@NotNull ISupplier<? extends T> orElseFn);

    /**
     * Throws an exception if this option is {@link None}.
     *
     * @see IOption#orNull() If you prefer to take a <code>null</code> instead of an exception when
     * the option is empty.
     *
     * @return The value for this option.
     * @throws IllegalStateException if this option is {@link None}.
     *
     * @since 1.0.0
     */
    @NotNull
    public abstract T get();

    /**
     * @since 1.0.0
     */
    public abstract boolean isEmpty();

    /**
     * @since 1.0.0
     */
    public boolean isDefined() {
        return !isEmpty();
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract IOption<T> ifEmpty(@NotNull ICommand fn);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract IOption<T> ifDefined(@NotNull IConsumer<? super T> fn);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract IOption<T> orElse(@NotNull T orElse);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract IOption<T> orElse(@NotNull ISupplier<? extends T> orElseFn);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract IOption<T> orElseFlat(@NotNull IOption<T> orElse);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract IOption<T> orElseFlat(@NotNull ISupplier<IOption<T>> orElseFn);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract IOption<T> filter(@NotNull IFunction<? super T, Boolean> fn);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract <R> IOption<R> map(@NotNull IFunction<? super T, R> fn);

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract <R> IOption<R> flatMap(@NotNull IFunction<? super T, IOption<R>> fn);

    /**
     * @since 1.0.0
     */
    public abstract void forEach(@NotNull IConsumer<? super T> fn);

    /**
     * @since 1.0.0
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return toList().iterator();
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    @SuppressWarnings("ConstantConditions")
    public static <R> IOption<R> some(@NotNull R value) {
        if (value == null) {
            throw new IllegalArgumentException("Argument of IOption.Some cannot be null");
        }
        return new Some<R>(value);
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    @SuppressWarnings("unchecked")
    public static <R> IOption<R> none() {
        return (IOption<R>) None.NONE;
    }

    /**
     * Alias to {@link #none()}.
     *
     * @since 1.0.0
     */
    @NotNull
    public static <R> IOption<R> empty() {
        return none();
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    public static <R> IOption<R> ofNullable(@Nullable R value) {
        return value == null ? IOption.<R>none() : some(value);
    }

    /**
     * @since 1.0.0
     */
    @NotNull
    public abstract <L> IEither<L, T> toEither(ISupplier<L> ifNone);

    /**
     * @since 1.0.0
     */
    public static final class Some<T> extends IOption<T> {

        @NotNull
        private final T value;

        private Some(@NotNull T value) {
            this.value = value;
        }

        /**
         * @since 1.0.0
         */
        @Override
        public String toString() {
            return String.format("Some(%s)", orNull());
        }

        /**
         * @since 1.0.0
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Some<?> some = (Some<?>) o;
            return value.equals(some.value);
        }

        /**
         * @since 1.0.0
         */
        @Override
        public int hashCode() {
            return value.hashCode();
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public T orNull() {
            return get();
        }

        /**
         * @since 1.0.0
         */
        @Override
        @NotNull
        public T getOrElse(@NotNull T orElse) {
            return get();
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public T getOrElse(@NotNull ISupplier<? extends T> orElseFn) {
            return get();
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public T get() {
            return value;
        }

        /**
         * @since 1.0.0
         */
        @Override
        public boolean isEmpty() {
            return false;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> ifEmpty(@NotNull ICommand fn) {
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> ifDefined(@NotNull IConsumer<? super T> fn) {
            fn.accept(orNull());
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> orElse(@NotNull T orElse) {
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> orElse(@NotNull ISupplier<? extends T> orElseFn) {
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> orElseFlat(@NotNull IOption<T> orElse) {
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> orElseFlat(@NotNull ISupplier<IOption<T>> orElseFn) {
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> filter(@NotNull IFunction<? super T, Boolean> fn) {
            return Boolean.TRUE.equals(this.map(fn).orNull()) ? this : IOption.<T>none();
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public <R> IOption<R> map(@NotNull IFunction<? super T, R> fn) {
            return ofNullable(fn.apply(this.orNull()));
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public <R> IOption<R> flatMap(@NotNull IFunction<? super T, IOption<R>> fn) {
            IOption<R> r = fn.apply(this.orNull());
            if (r == null) return IOption.none();
            return r;
        }

        /**
         * @since 1.0.0
         */
        @Override
        public void forEach(@NotNull IConsumer<? super T> fn) {
            fn.accept(this.orNull());
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public <L> IEither<L, T> toEither(ISupplier<L> ifNone) {
            return IEither.right(get());
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public List<T> toList() {
            return Collections.singletonList(this.orNull());
        }
    }

    /**
     * @since 1.0.0
     */
    public static final class None<T> extends IOption<T> {

        public static final None<?> NONE = new None<Object>();

        private None() { }

        /**
         * @since 1.0.0
         */
        @Override
        public String toString() {
            return "None";
        }

        /**
         * @since 1.0.0
         */
        @Override
        public boolean equals(Object o) {
            return this == o || (o != null && getClass() == o.getClass());
        }

        /**
         * @since 1.0.0
         */
        @Override
        public int hashCode() {
            return IOption.None.class.hashCode();
        }

        /**
         * @since 1.0.0
         */
        @Override
        public T orNull() {
            return null;
        }

        /**
         * @since 1.0.0
         */
        @Override
        @NotNull
        public T getOrElse(@NotNull T orElse) {
            return orElse;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public T getOrElse(@NotNull ISupplier<? extends T> orElseFn) {
            return orElseFn.get();
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public T get() {
            throw new IllegalStateException("IOption.None has no value. If null would be an acceptable value in this case, use .orNull() instead of .get()");
        }

        /**
         * @since 1.0.0
         */
        @Override
        public boolean isEmpty() {
            return true;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> ifEmpty(@NotNull ICommand fn) {
            fn.run();
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> ifDefined(@NotNull IConsumer<? super T> fn) {
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> orElse(@NotNull T orElse) {
            return some(orElse);
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> orElse(@NotNull ISupplier<? extends T> orElseFn) {
            return IOption.ofNullable(orElseFn.get());
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> orElseFlat(@NotNull IOption<T> orElse) {
            return orElse;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> orElseFlat(@NotNull ISupplier<IOption<T>> orElseFn) {
            return orElseFn.get();
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public IOption<T> filter(@NotNull IFunction<? super T, Boolean> fn) {
            return this;
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public <R> IOption<R> map(@NotNull IFunction<? super T, R> fn) {
            return none();
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public <R> IOption<R> flatMap(@NotNull IFunction<? super T, IOption<R>> fn) {
            return none();
        }

        /**
         * @since 1.0.0
         */
        @Override
        public void forEach(@NotNull IConsumer<? super T> fn) {
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public <L> IEither<L, T> toEither(ISupplier<L> ifNone) {
            return IEither.left(ifNone.get());
        }

        /**
         * @since 1.0.0
         */
        @NotNull
        @Override
        public List<T> toList() {
            return Collections.emptyList();
        }

    }

}
