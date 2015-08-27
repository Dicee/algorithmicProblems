package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;
import java.util.Optional;
import javafx.util.Pair;

public class NotEqualDiff<T> implements Diff<T> {
    private final T actual, expected;

    public NotEqualDiff(T actual, T expected) {
        this.actual = notNull(actual);
        this.expected = notNull(expected);
    }

    @Override
    public Pair<Optional<T>, Optional<T>> showDiff() {
        return new Pair<>(Optional.of(actual), Optional.of(expected));
    }

    @Override
    public String toString() {
        return "BasicDiff [actual=" + actual + ", expected=" + expected + "]";
    }
}