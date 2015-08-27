package miscellaneous.utils.collection.toolbox;

import java.util.Optional;
import javafx.util.Pair;

public interface Diff<T> {
	Pair<Optional<T>, Optional<T>> showDiff();
}
