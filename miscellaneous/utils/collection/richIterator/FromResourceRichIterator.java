package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;

import miscellaneous.utils.io.IOUtils;

public abstract class FromResourceRichIterator<X> extends NullableRichIterator<X> {
	private final Closeable[] resources;

	public FromResourceRichIterator(Closeable... resources) { this.resources = notNull(resources); }
	
	/**
	 * Reads the next element of the iterator.
	 * @return the next value. A null value signals the end of the iterator.
	 * @throws EOFException potentially returned if the iterator has reached its end. Alternatively, the method will return null.
	 * @throws IOException
	 */
	protected abstract X tryReadNext() throws EOFException, IOException;

	@Override 
	protected X nextOrNull() throws Exception {
		try {
			return tryReadNext();
		} catch (EOFException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override 
	protected final void closeInternal() throws IOException { IOUtils.closeQuietly(resources); }
}
