package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.io.EOFException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.google.common.base.Throwables;

class BufferedRichIterator<T> extends RichIterator<T> {
	protected final Deque<T>			buffer	= new LinkedList<>();
	private final LookAheadIterator<T>	it;
	
	public BufferedRichIterator(LookAheadIterator<T> it) { this.it = notNull(it); }

	@Override
	protected final T nextInternal() {
		if (!hasNext()) throw new NoSuchElementException();
		return !buffer.isEmpty() ? buffer.pop() : tryReadNext();
	}
	
	@Override
	protected final boolean hasNextInternal() {
		if (!buffer.isEmpty()) return true;
		try {
			buffer.addLast(tryReadNext());
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
