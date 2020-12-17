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


public abstract class IOption<T> implements Iterable<T> {

    @NotNull
    public abstract List<T> toList();

    /**
     * Returns the current option's value or <code>null</code> if this option is {@link None}.
     *
     * @return The value for this option, <code>null</code> if this option is {@link None}.
     */
    @Nullable
    public abstract T orNull();

    @NotNull
    public abstract T getOrElse(@NotNull T orElse);

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
     */
    @NotNull
    public abstract T get();

    public abstract boolean isEmpty();

    public boolean isDefined() {
        return !isEmpty();
    }

    @NotNull
    public abstract IOption<T> ifEmpty(@NotNull ICommand fn);

    @NotNull
    public abstract IOption<T> ifDefined(@NotNull IConsumer<? super T> fn);

    @NotNull
    public abstract IOption<T> orElse(@NotNull T orElse);

    @NotNull
    public abstract IOption<T> orElse(@NotNull ISupplier<? extends T> orElseFn);

    @NotNull
    public abstract IOption<T> orElseFlat(@NotNull IOption<T> orElse);

    @NotNull
    public abstract IOption<T> orElseFlat(@NotNull ISupplier<IOption<T>> orElseFn);

    @NotNull
    public abstract IOption<T> filter(@NotNull IFunction<? super T, Boolean> fn);

    @NotNull
    public abstract <R> IOption<R> map(@NotNull IFunction<? super T, R> fn);

    @NotNull
    public abstract <R> IOption<R> flatMap(@NotNull IFunction<? super T, IOption<R>> fn);

    public abstract void forEach(@NotNull IConsumer<? super T> fn);

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return toList().iterator();
    }

    @NotNull
    @SuppressWarnings("ConstantConditions")
    public static <R> IOption<R> some(@NotNull R value) {
        if (value == null) {
            throw new IllegalArgumentException("Argument of IOption.Some cannot be null");
        }
        return new Some<R>(value);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <R> IOption<R> none() {
        return (IOption<R>) None.NONE;
    }

    /**
     * @since 1.1.0
     */
    @NotNull
    public static <R> IOption<R> empty() {
        return none();
    }

    @NotNull
    public static <R> IOption<R> ofNullable(@Nullable R value) {
        return value == null ? IOption.<R>none() : some(value);
    }

    @NotNull
    public abstract <L> IEither<L, T> toEither(ISupplier<L> ifNone);

    public static final class Some<T> extends IOption<T> {
        @NotNull
        private final T value;

        private Some(@NotNull T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("Some(%s)", orNull());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Some<?> some = (Some<?>) o;
            return value.equals(some.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @NotNull
        @Override
        public T orNull() {
            return get();
        }

        @Override
        @NotNull
        public T getOrElse(@NotNull T orElse) {
            return get();
        }

        @NotNull
        @Override
        public T getOrElse(@NotNull ISupplier<? extends T> orElseFn) {
            return get();
        }

        @NotNull
        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NotNull
        @Override
        public IOption<T> ifEmpty(@NotNull ICommand fn) {
            return this;
        }

        @NotNull
        @Override
        public IOption<T> ifDefined(@NotNull IConsumer<? super T> fn) {
            fn.accept(orNull());
            return this;
        }

        @NotNull
        @Override
        public IOption<T> orElse(@NotNull T orElse) {
            return this;
        }

        @NotNull
        @Override
        public IOption<T> orElse(@NotNull ISupplier<? extends T> orElseFn) {
            return this;
        }

        @NotNull
        @Override
        public IOption<T> orElseFlat(@NotNull IOption<T> orElse) {
            return this;
        }

        @NotNull
        @Override
        public IOption<T> orElseFlat(@NotNull ISupplier<IOption<T>> orElseFn) {
            return this;
        }

        @NotNull
        @Override
        public IOption<T> filter(@NotNull IFunction<? super T, Boolean> fn) {
            return Boolean.TRUE.equals(this.map(fn).orNull()) ? this : IOption.<T>none();
        }

        @NotNull
        @Override
        public <R> IOption<R> map(@NotNull IFunction<? super T, R> fn) {
            return ofNullable(fn.apply(this.orNull()));
        }

        @NotNull
        @Override
        public <R> IOption<R> flatMap(@NotNull IFunction<? super T, IOption<R>> fn) {
            IOption<R> r = fn.apply(this.orNull());
            if (r == null) return IOption.none();
            return r;
        }

        @Override
        public void forEach(@NotNull IConsumer<? super T> fn) {
            fn.accept(this.orNull());
        }

        @NotNull
        @Override
        public <L> IEither<L, T> toEither(ISupplier<L> ifNone) {
            return IEither.right(get());
        }

        @NotNull
        @Override
        public List<T> toList() {
            return Collections.singletonList(this.orNull());
        }
    }

    public static final class None<T> extends IOption<T> {

        public static final None<?> NONE = new None<Object>();

        private None() { }

        @Override
        public String toString() {
            return "None";
        }

        @Override
        public boolean equals(Object o) {
            return this == o || (o != null && getClass() == o.getClass());
        }

        @Override
        public int hashCode() {
            return IOption.None.class.hashCode();
        }

        @Override
        public T orNull() {
            return null;
        }

        @Override
        @NotNull
        public T getOrElse(@NotNull T orElse) {
            return orElse;
        }

        @NotNull
        @Override
        public T getOrElse(@NotNull ISupplier<? extends T> orElseFn) {
            return orElseFn.get();
        }

        @NotNull
        @Override
        public T get() {
            throw new IllegalStateException("IOption.None has no value. If null would be an acceptable value in this case, use .orNull() instead of .get()");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @NotNull
        @Override
        public IOption<T> ifEmpty(@NotNull ICommand fn) {
            fn.run();
            return this;
        }

        @NotNull
        @Override
        public IOption<T> ifDefined(@NotNull IConsumer<? super T> fn) {
            return this;
        }

        @NotNull
        @Override
        public IOption<T> orElse(@NotNull T orElse) {
            return some(orElse);
        }

        @NotNull
        @Override
        public IOption<T> orElse(@NotNull ISupplier<? extends T> orElseFn) {
            return IOption.ofNullable(orElseFn.get());
        }

        @NotNull
        @Override
        public IOption<T> orElseFlat(@NotNull IOption<T> orElse) {
            return orElse;
        }

        @NotNull
        @Override
        public IOption<T> orElseFlat(@NotNull ISupplier<IOption<T>> orElseFn) {
            return orElseFn.get();
        }

        @NotNull
        @Override
        public IOption<T> filter(@NotNull IFunction<? super T, Boolean> fn) {
            return this;
        }

        @NotNull
        @Override
        public <R> IOption<R> map(@NotNull IFunction<? super T, R> fn) {
            return none();
        }

        @NotNull
        @Override
        public <R> IOption<R> flatMap(@NotNull IFunction<? super T, IOption<R>> fn) {
            return none();
        }

        @Override
        public void forEach(@NotNull IConsumer<? super T> fn) {
        }

        @NotNull
        @Override
        public <L> IEither<L, T> toEither(ISupplier<L> ifNone) {
            return IEither.left(ifNone.get());
        }

        @NotNull
        @Override
        public List<T> toList() {
            return Collections.emptyList();
        }
    }

}
