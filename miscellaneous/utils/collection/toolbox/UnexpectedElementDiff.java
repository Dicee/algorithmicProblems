package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;
import java.util.Optional;
import javafx.util.Pair;

public class UnexpectedElementDiff<T> implements Diff<T> {
    private final T unexpected;

    public UnexpectedElementDiff(T unexpected) {
        this.unexpected = notNull(unexpected);
    }

    @Override
    public Pair<Optional<T>, Optional<T>> showDiff() {
        return new Pair<>(Optional.of(unexpected), Optional.empty());
    }

    @Override
    public String toString() {
        return "UnexpectedElementDiff [" + unexpected + "]";
    }
}