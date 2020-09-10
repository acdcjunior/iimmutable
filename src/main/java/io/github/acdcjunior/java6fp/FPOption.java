package io.github.acdcjunior.java6fp;

import io.github.acdcjunior.java6fp.fn.FPCommand;
import io.github.acdcjunior.java6fp.fn.FPConsumer;
import io.github.acdcjunior.java6fp.fn.FPFunction;
import io.github.acdcjunior.java6fp.fn.FPSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public abstract class FPOption<T> implements Iterable<T> {

    @NotNull
    public abstract List<T> toList();

    /**
     * Returns null if the value is none().
     */
    @Nullable
    public abstract T orNull();

    @NotNull
    public abstract T getOrElse(@NotNull T orElse);

    @NotNull
    public abstract T getOrElse(@NotNull FPSupplier<? extends T> orElseFn);

    /**
     * Throws an exception if the value is none().
     */
    @NotNull
    public abstract T get();

    public abstract boolean isEmpty();

    public boolean isDefined() {
        return !isEmpty();
    }

    @NotNull
    public abstract FPOption<T> ifEmpty(@NotNull FPCommand fn);

    @NotNull
    public abstract FPOption<T> ifDefined(@NotNull FPConsumer<? super T> fn);

    @NotNull
    public abstract FPOption<T> orElse(@NotNull T orElse);

    @NotNull
    public abstract FPOption<T> orElse(@NotNull FPSupplier<? extends T> orElseFn);

    @NotNull
    public abstract FPOption<T> orElseFlat(@NotNull FPOption<T> orElse);

    @NotNull
    public abstract FPOption<T> orElseFlat(@NotNull FPSupplier<FPOption<T>> orElseFn);

    @NotNull
    public abstract FPOption<T> filter(@NotNull FPFunction<? super T, Boolean> fn);

    @NotNull
    public abstract <R> FPOption<R> map(@NotNull FPFunction<? super T, R> fn);

    @NotNull
    public abstract <R> FPOption<R> flatMap(@NotNull FPFunction<? super T, FPOption<R>> fn);

    public abstract void forEach(@NotNull FPConsumer<? super T> fn);

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return toList().iterator();
    }

    @NotNull
    @SuppressWarnings("ConstantConditions")
    public static <R> FPOption<R> some(@NotNull R value) {
        if (value == null) {
            throw new IllegalArgumentException("Argument of FPOption.Some cannot be null");
        }
        return new Some<R>(value);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <R> FPOption<R> none() {
        return (FPOption<R>) None.NONE;
    }

    @NotNull
    public static <R> FPOption<R> ofNullable(@Nullable R value) {
        return value == null ? FPOption.<R>none() : some(value);
    }

    public static final class Some<T> extends FPOption<T> {
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
        public T getOrElse(@NotNull FPSupplier<? extends T> orElseFn) {
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
        public FPOption<T> ifEmpty(@NotNull FPCommand fn) {
            return this;
        }

        @NotNull
        @Override
        public FPOption<T> ifDefined(@NotNull FPConsumer<? super T> fn) {
            fn.accept(orNull());
            return this;
        }

        @NotNull
        @Override
        public FPOption<T> orElse(@NotNull T orElse) {
            return this;
        }

        @NotNull
        @Override
        public FPOption<T> orElse(@NotNull FPSupplier<? extends T> orElseFn) {
            return this;
        }

        @NotNull
        @Override
        public FPOption<T> orElseFlat(@NotNull FPOption<T> orElse) {
            return this;
        }

        @NotNull
        @Override
        public FPOption<T> orElseFlat(@NotNull FPSupplier<FPOption<T>> orElseFn) {
            return this;
        }

        @NotNull
        @Override
        public FPOption<T> filter(@NotNull FPFunction<? super T, Boolean> fn) {
            return Boolean.TRUE.equals(this.map(fn).orNull()) ? this : FPOption.<T>none();
        }

        @NotNull
        @Override
        public <R> FPOption<R> map(@NotNull FPFunction<? super T, R> fn) {
            return ofNullable(fn.apply(this.orNull()));
        }

        @NotNull
        @Override
        public <R> FPOption<R> flatMap(@NotNull FPFunction<? super T, FPOption<R>> fn) {
            FPOption<R> r = fn.apply(this.orNull());
            if (r == null) return FPOption.none();
            return r;
        }

        @Override
        public void forEach(@NotNull FPConsumer<? super T> fn) {
            fn.accept(this.orNull());
        }

        @NotNull
        @Override
        public List<T> toList() {
            return Collections.singletonList(this.orNull());
        }
    }

    public static final class None<T> extends FPOption<T> {

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
            return FPOption.None.class.hashCode();
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
        public T getOrElse(@NotNull FPSupplier<? extends T> orElseFn) {
            return orElseFn.get();
        }

        @NotNull
        @Override
        public T get() {
            throw new IllegalStateException("FPOption.None has no value");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @NotNull
        @Override
        public FPOption<T> ifEmpty(@NotNull FPCommand fn) {
            fn.run();
            return this;
        }

        @NotNull
        @Override
        public FPOption<T> ifDefined(@NotNull FPConsumer<? super T> fn) {
            return this;
        }

        @NotNull
        @Override
        public FPOption<T> orElse(@NotNull T orElse) {
            return some(orElse);
        }

        @NotNull
        @Override
        public FPOption<T> orElse(@NotNull FPSupplier<? extends T> orElseFn) {
            return FPOption.ofNullable(orElseFn.get());
        }

        @NotNull
        @Override
        public FPOption<T> orElseFlat(@NotNull FPOption<T> orElse) {
            return orElse;
        }

        @NotNull
        @Override
        public FPOption<T> orElseFlat(@NotNull FPSupplier<FPOption<T>> orElseFn) {
            return orElseFn.get();
        }

        @NotNull
        @Override
        public FPOption<T> filter(@NotNull FPFunction<? super T, Boolean> fn) {
            return this;
        }

        @NotNull
        @Override
        public <R> FPOption<R> map(@NotNull FPFunction<? super T, R> fn) {
            return none();
        }

        @NotNull
        @Override
        public <R> FPOption<R> flatMap(@NotNull FPFunction<? super T, FPOption<R>> fn) {
            return none();
        }

        @Override
        public void forEach(@NotNull FPConsumer<? super T> fn) {
        }

        @NotNull
        @Override
        public List<T> toList() {
            return Collections.emptyList();
        }
    }

}