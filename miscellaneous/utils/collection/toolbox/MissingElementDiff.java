package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;

import java.util.Objects;
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
	public int hashCode() { return missing.hashCode(); }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MissingElementDiff<?> that = (MissingElementDiff<?>) o;
		return Objects.equals(missing, that.missing);
	}
    
    @Override
    public String toString() {
        return "MissingElementDiff [" + missing + "]";
    }
}