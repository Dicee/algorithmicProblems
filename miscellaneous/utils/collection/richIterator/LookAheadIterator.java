package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;

import miscellaneous.utils.check.Check;

public interface LookAheadIterator<T> {
	/**
	 * Reads the next element of the iterator.
	 * @return the next value. A null value signals the end of the iterator.
	 * @throws EOFException potentially returned if the iterator has reached its end. Alternatively, the method will return null.
	 * @throws IOException
	 */
	public T readNext() throws EOFException, IOException;
	
	public static <T> LookAheadIterator<T> fromIterator(RichIterator<T> it) {
		Check.notNull(it);
		return () -> {
			if (!it.hasNext()) throw new EOFException();
			return it.next();
		};
	}
}
