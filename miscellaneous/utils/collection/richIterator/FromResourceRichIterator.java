package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;
import java.util.NoSuchElementException;

import com.google.common.base.Throwables;

public abstract class FromResourceRichIterator<X> extends RichIterator<X> {
	protected X peeked = null;

	/**
	 * Reads the next element of the iterator.
	 * @return the next value. A null value signals the end of the iterator.
	 * @throws EOFException potentially returned if the iterator has reached its end. Alternatively, the method will return null.
	 * @throws IOException
	 */
	protected abstract X readNext() throws EOFException, IOException;
	
	@Override
	protected final X nextInternal() {
		X res  = peeked;
		peeked = null;
		return res;
	}
	
	@Override
	protected final boolean hasNextInternal() {
		if (peeked != null) return true;
		try {
			peeked = tryReadNext();
			return peeked != null;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	private X tryReadNext() {
		try {
			return readNext();
		} catch (EOFException e) {
			return null;
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
}
