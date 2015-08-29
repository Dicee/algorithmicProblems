package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.NoSuchElementException;

import miscellaneous.utils.io.IOUtils;

import com.google.common.base.Throwables;

public abstract class FromResourceRichIterator<X> extends RichIterator<X> {
	protected X					peeked;
	private final Closeable[]	resources;

	public FromResourceRichIterator(Closeable... resources) { this.resources = notNull(resources); }
	
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
	
	@Override
	protected final void closeInternal() throws IOException { IOUtils.closeQuietly(resources); }
	
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
