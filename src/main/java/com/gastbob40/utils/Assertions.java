package com.gastbob40.utils;

import io.smallrye.common.constraint.NotNull;
import lombok.SneakyThrows;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.gastbob40.utils.Seq.seq;

/**
 * Utility class for inline assertions.
 */
public enum Assertions {
    ;

    /**
     * Assert that the given value is true.
     *
     * @param expression
     *         The value to test.
     *
     * @return A boolean assertion.
     */
    public static <TYPE_T> Assertion<TYPE_T> assertThat(final TYPE_T source, final Predicate<TYPE_T> expression) {
        return new Assertion<TYPE_T>(source, src -> expression.test(source));
    }

    /**
     * Assert that the given value is true.
     *
     * @param expression
     *         The value to test.
     *
     * @return A boolean assertion.
     */
    public static Assertion<Boolean> assertThat(final boolean expression) {
        return new Assertion<>(expression, assertion -> assertion);
    }

    /**
     * Assert that the given value is false.
     *
     * @param expression
     *         The value to test.
     *
     * @return A boolean assertion.
     */
    public static Assertion<Boolean> assertNot(final boolean expression) {
        return new Assertion<>(!expression, assertion -> assertion);
    }

    /**
     * Assert that the given value is not null.
     *
     * @param value
     *         The value to test.
     *
     * @return A valued assertion.
     */
    public static <IN_T> Assertion<IN_T> assertNotNull(final IN_T value) {
        return new Assertion<>(value, Objects::nonNull);
    }

    /**
     * Assert that all of the given values are true.
     *
     * @param values
     *         The values to test.
     *
     * @return An array valued assertion.
     */
    public static <IN_T> Assertion<IN_T[]> requireNoNulls(final IN_T[] values) {
        return new Assertion<>(values, array -> seq(array).noneMatch(Objects::isNull));
    }

    /**
     * Assert that all of the given values are true.
     *
     * @param values
     *         The values to test.
     *
     * @return An array valued assertion.
     */
    public static <IN_T> Assertion<Collection<IN_T>> requireNoNulls(final Collection<IN_T> values) {
        return new Assertion<>(values, array -> seq(array).noneMatch(Objects::isNull));
    }

    /**
     * Assert that a String is not blank.
     *
     * @param value
     *         The value to test.
     *
     * @return A valued assertion.
     */
    public static <IN_T extends String> Assertion<IN_T> assertNotBlank(final IN_T value) {
        return new Assertion<>(value, s -> s != null && !s.isBlank());
    }

    /**
     * Assert that a String is empty.
     *
     * @param value
     *         The value to test.
     *
     * @return A valued assertion.
     */
    public static <IN_T extends String> Assertion<IN_T> assertEmpty(final IN_T value) {
        return new Assertion<>(value, s -> s != null && s.isEmpty());
    }

    /**
     * Assert that a String is not empty.
     *
     * @param value
     *         The value to test.
     *
     * @return A valued assertion.
     */
    public static <IN_T extends String> Assertion<IN_T> assertNotEmpty(final IN_T value) {
        return new Assertion<>(value, s -> s != null && !s.isEmpty());
    }

    /**
     * Assert that a collection is empty.
     *
     * @param value
     *         The value to test.
     *
     * @return A valued assertion.
     */
    public static <IN_T> Assertion<Collection<IN_T>> assertEmpty(final Collection<IN_T> value) {
        return new Assertion<>(value, array -> seq(array).toList().isEmpty());
    }

    /**
     * Assert that a collection is not empty.
     *
     * @param value
     *         The value to test.
     *
     * @return A valued assertion.
     */
    public static <IN_T> Assertion<Collection<IN_T>> assertNotEmpty(final Collection<IN_T> value) {
        return new Assertion<>(value, array -> !seq(array).toList().isEmpty());
    }

    /**
     * Assert that two objects are equal.
     *
     * @param value1
     *         The first value to test.
     * @param value2
     *         The value to test against.
     *
     * @return A valued assertion.
     */
    public static <IN_T> Assertion<IN_T> assertEquals(final IN_T value1, final IN_T value2) {
        return new Assertion<>(value1, (v1) -> Objects.equals(v1, value2));
    }

    /**
     * Assert that two objects are not equal.
     *
     * @param value1
     *         The first value to test.
     * @param value2
     *         The value to test against.
     *
     * @return A valued assertion.
     */
    public static <IN_T> Assertion<IN_T> assertNotEquals(final IN_T value1, final IN_T value2) {
        return new Assertion<>(value1, (v1) -> !Objects.equals(v1, value2));
    }

    /**
     * Assert that the given value is null.
     *
     * @param value
     *         The value to test.
     *
     * @return A valued assertion.
     */
    public static <IN_T> Assertion<IN_T> assertNull(final IN_T value) {
        return new Assertion<>(value, Objects::isNull);
    }

    /**
     * Assertion class.
     *
     * @param <ASSERT_T>
     *         The inner value.
     */
    @Value public static class Assertion<ASSERT_T> {
        public ASSERT_T value;
        public @NotNull Function<ASSERT_T, Boolean> validationFunction;
        public Logger logger = LoggerFactory.getLogger(Assertions.class);

        public ASSERT_T orElse(final ASSERT_T value) {
            return orElseGet(() -> value);
        }

        @SneakyThrows
        public void orElseDo(final @NotNull Runnable exceptionSupplier, Object... parameters) {
            if (!validationFunction.apply(value)) {exceptionSupplier.run();}
        }

        public ASSERT_T orElseGet(final @NotNull Supplier<ASSERT_T> supplier) {
            return validationFunction.apply(value) ? value : supplier.get();
        }

        public @NotNull ASSERT_T orElseThrow(final @NotNull Function<Object[], ? extends Exception> exceptionSupplier, Object... parameters) {
            if (!validationFunction.apply(value)) {
                try {
                    throw exceptionSupplier.apply(parameters);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }

            return value;
        }

        public @NotNull ASSERT_T orElseThrow(final @NotNull Supplier<? extends Exception> exceptionSupplier) {
            if (!validationFunction.apply(value)) {
                try {
                    throw exceptionSupplier.get();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }

            return value;
        }

        public void orElseWarn(final @NotNull Function<Object[], ? extends Exception> exceptionSupplier, Object... parameters) {
            if (!validationFunction.apply(value)) {
                final var exception = exceptionSupplier.apply(parameters);
                final var message = exception.getMessage();
                logger.warn(message);
            }
        }

        public void orElseWarn(final String message) {
            if (!validationFunction.apply(value)) {
                logger.warn(message);
            }
        }
    }
}
