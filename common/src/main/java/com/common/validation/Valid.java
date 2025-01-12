package com.common.validation;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * An valid {@link Validation}.
 *
 * @param <T>
 *    Data type of the instance to validate.
 */
public final class Valid<T> extends Validation<T> implements Serializable {

    private final T value;

    /**
     * Construct a {@code Valid}
     *
     * @param value
     *    The value of this success.
     */
    private Valid(T value) {
        super();
        this.value = value;
    }


    /**
     * Returns an empty {@code Valid} instance. No value is present for this {@code Valid}.
     *
     * @return an empty {@code Valid}
     */
    public static <T> Valid<T> empty() {
        return new Valid(null);
    }


    /**
     * Returns an {@code Valid} adding the given non-{@code null} value.
     *
     * @param value
     *    The value to store, which must be non-{@code null}
     *
     * @return {@code Valid}
     *
     * @throws NullPointerException if value is {@code null}
     */
    public static <T> Valid<T> of(T value) {
        return new Valid(Objects.requireNonNull(value));
    }


    @Override
    public boolean isValid() {
        return true;
    }


    @Override
    public T get() {
        return value;
    }


    @Override
    public List<String> getErrors() {
        throw new NoSuchElementException("Is not possible to get errors of a 'valid' Validation");
    }


    @Override
    public boolean equals(Object obj) {
        return (obj == this) || (obj instanceof Valid && Objects.equals(value, ((Valid<?>) obj).value));
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }


    @Override
    public String toString() {
        return "Valid (" + value + ")";
    }

}
