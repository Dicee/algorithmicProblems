package miscellaneous.utils.collection.richIterator;

import java.util.NoSuchElementException;

public abstract class NullableRichIterator<X> extends RichIterator<X> {
	protected X	peeked;
	
	/**
	 * Reads the next element of the iterator.
	 * @return the next value. A null value signals the end of the iterator.
	 */
	protected abstract X nextOrNull() throws Exception;
	
	@Override
	protected final X nextInternal() {
		X res  = peeked;
		peeked = null;
		return res;
	}
	
	@Override
	protected boolean hasNextInternal() throws Exception {
		if (peeked != null) return true;
		try {
			peeked = nextOrNull();
			return peeked != null;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
