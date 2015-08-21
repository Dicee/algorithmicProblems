package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.io.EOFException;
import java.util.NoSuchElementException;

import com.google.common.base.Throwables;

class BufferedRichIterator<T> extends RichIterator<T> {
	protected T buffer = null;
	private final LookAheadIterator<T>	it;
	
	public BufferedRichIterator(LookAheadIterator<T> it) { this.it = notNull(it); }

	@Override
	protected final T nextInternal() {
		if (buffer != null) {
			T res = buffer;
			buffer = null;
			return res;
		} 
		return tryReadNext();
	}
	
	@Override
	protected final boolean hasNextInternal() {
		if (buffer != null) return true;
		try {
			buffer = tryReadNext();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	private T tryReadNext() {
		try {
			T t = it.readNext();
			if (t == null) throw new EOFException();
			return t;
		} catch (EOFException e) {
			throw new NoSuchElementException();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
}
