package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;

import java.util.Objects;
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
	public int hashCode() { return unexpected.hashCode(); }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UnexpectedElementDiff<?> that = (UnexpectedElementDiff<?>) o;
		return Objects.equals(unexpected, that.unexpected);
	}
    
    @Override
    public String toString() {
        return "UnexpectedElementDiff [" + unexpected + "]";
    }
}