package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;

import miscellaneous.utils.check.Check;

public interface FromResourceRichIterator<T> {
	/**
	 * Reads the next element of the iterator.
	 * @return the next value. A null value signals the end of the iterator.
	 * @throws EOFException potentially returned if the iterator has reached its end. Alternatively, the method will return null.
	 * @throws IOException
	 */
	T readNext() throws EOFException, IOException;
	
	public static <T> FromResourceRichIterator<T> fromIterator(RichIterator<T> it) {
		Check.notNull(it);
		return () -> it.hasNext() ? it.next() : null;
	}
}
