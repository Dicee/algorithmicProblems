package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;

import miscellaneous.utils.check.Check;

public interface LookAheadIterator<T> {
	public T readNext() throws EOFException, IOException;
	
	public static <T> LookAheadIterator<T> fromIterator(RichIterator<T> it) {
		Check.notNull(it);
		return () -> {
			if (!it.hasNext()) throw new EOFException();
			return it.next();
		};
	}
}
