package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.io.EOFException;
import java.util.NoSuchElementException;

import com.google.common.base.Throwables;

public class BufferedRichIterator<T> extends RichIterator<T> {
	protected T peeked = null;
	private final FromResourceRichIterator<T>	it;
	
	public BufferedRichIterator(FromResourceRichIterator<T> it) { this.it = notNull(it); }

	@Override
	protected final T nextInternal() {
		if (peeked != null) {
			T res  = peeked;
			peeked = null;
			return res;
		} 
		return tryReadNext();
	}
	
	@Override
	protected final boolean hasNextInternal() {
		if (peeked != null) return true;
		try {
			peeked = tryReadNext();
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
