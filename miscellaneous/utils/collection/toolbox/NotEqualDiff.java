package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;

import java.util.Objects;
import java.util.Optional;

import javafx.util.Pair;

public class NotEqualDiff<T> implements Diff<T> {
    private final T actual, expected;

    public NotEqualDiff(T actual, T expected) {
        this.actual   = notNull(actual);
        this.expected = notNull(expected);
    }

    @Override
    public Pair<Optional<T>, Optional<T>> showDiff() {
        return new Pair<>(Optional.of(actual), Optional.of(expected));
    }

	@Override
	public int hashCode() { return Objects.hash(actual, expected); }
    
    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NotEqualDiff<?> that = (NotEqualDiff<?>) o;
		return Objects.equals(actual, that.actual) && Objects.equals(expected, that.expected);
	}

    @Override
    public String toString() {
        return "NotEqualDiff [actual=" + actual + ", expected=" + expected + "]";
    }
}