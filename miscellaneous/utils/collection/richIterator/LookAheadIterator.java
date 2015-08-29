package miscellaneous.utils.collection.richIterator;

import java.io.Closeable;
import java.util.Iterator;

interface LookAheadIterator<X> extends Iterator<X>, Iterable<X>, Closeable, AutoCloseable {
	/**
	 * Reads the next element of the iterator, but does not consume it.
	 * @return the next value. A null value signals the end of the iterator.
	 */
	X peek();
}
