package miscellaneous.utils.collection.richIterator;

public abstract class LookAheadIterator<T> {
	/**
	 * Reads the next element of the iterator, but does not consume it.
	 * @return the next value. A null value signals the end of the iterator.
	 */
	public abstract T peek();
}
