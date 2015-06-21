package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptions;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import miscellaneous.utils.collection.StreamUtils;

public abstract class RichIterator<T> implements Iterator<T>, Iterable<T>, Closeable, AutoCloseable {
	private boolean			closed	= false;
	private boolean			used	= false;
	private long			count	= 0;
	private Consumer<Long>	onClose;
	
	public Iterator<T> iterator() {
		if (closed) throw new IllegalStateException("Cannot iterate a closed iterator");
		if (used  ) throw new IllegalStateException("This object can only be iterated once");
		used = true;
		return this;
	}

	@Override
	public boolean hasNext() {
		return !closed && ignoreCheckedExceptions(this::hasNextInternal);
	}

	@Override
	public final T next() {
		if (!hasNext()) throw new NoSuchElementException();
		T next = ignoreCheckedExceptions(this::nextInternal);
		if (!hasNext()) ignoreCheckedExceptions(this::close);
		count++;
		return next;
	}
	
	@Override
	public final void close() throws IOException {
		closeInternal();
		if (onClose != null) onClose.accept(count);
		closed = true;
	}
	
	public final RichIterator<T> onClose(Consumer<Long> onClose) {
		this.onClose = onClose;
		return this;
	}
	
	public final Stream<T> stream() { return StreamUtils.iteratorToStream(this); }

	protected abstract boolean hasNextInternal() throws Exception;
	protected abstract T nextInternal() throws Exception;
	protected void closeInternal() throws IOException { }
}
