package com.xinan.demo.util;

import org.reactivestreams.Publisher;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xinan
 * @date 2018/8/3
 */
public class Try {

    public static <T, R> Function<T, R> of(UncheckedFunction<T, R> mapper) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                return mapper.apply(t);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T> Supplier<T> of(UncheckedSupplier<T> mapper) {
        Objects.requireNonNull(mapper);
        return () -> {
            try {
                return mapper.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @FunctionalInterface
    public static interface UncheckedFunction<T, R> {

        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public static interface UncheckedSupplier<T> {
        T get() throws Exception;
    }
}
