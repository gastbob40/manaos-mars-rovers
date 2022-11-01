package com.gastbob40.utils;

import java.util.Objects;

public class Pair<LEFT_T, RIGHT_T> {
    public final LEFT_T left;
    public final RIGHT_T right;

    public Pair(final LEFT_T left,
                final RIGHT_T right) {
        this.left = left;
        this.right = right;
    }

    public static <LEFT_T, RIGHT_T> Pair<LEFT_T, RIGHT_T> pair(final LEFT_T left, final RIGHT_T right) {
        return new Pair<>(left, right);
    }

    public LEFT_T getLeft() {
        return left;
    }

    public RIGHT_T getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) &&
               Objects.equals(right, pair.right);
    }

    @Override
    public String toString() {
        return "Pair{" +
               "left=" + left +
               ", right=" + right +
               '}';
    }
}

