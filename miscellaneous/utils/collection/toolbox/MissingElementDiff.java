package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;
import java.util.Optional;
import javafx.util.Pair;

public class MissingElementDiff<T> implements Diff<T> {
    private final T missing;

    public MissingElementDiff(T missing) {
        this.missing = notNull(missing);
    }

    @Override
    public Pair<Optional<T>, Optional<T>> showDiff() {
        return new Pair<>(Optional.empty(), Optional.of(missing));
    }

    @Override
    public String toString() {
        return "MissingElementDiff [" + missing + "]";
    }
}