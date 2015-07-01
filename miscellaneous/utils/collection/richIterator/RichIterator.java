package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptionsSupplier;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptions;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptionsBinaryOperator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import miscellaneous.utils.collection.StreamUtils;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingBinaryOperator;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingConsumer;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingFunction;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingPredicate;

public abstract class RichIterator<X> implements Iterator<X>, Iterable<X>, Closeable, AutoCloseable {
	private boolean			closed	= false;
	private boolean			used	= false;
	private long			count	= 0;
	private ThrowingConsumer<Long>	onClose;
	
	public Iterator<X> iterator() {
		ensureNotClosed();
		ensureNotAlreadyUsed();
		used = true;
		return this;
	}

	@Override
        // stupid Eclipse compiler cannot infer correctly
	public boolean hasNext() {
		return !closed && ignoreCheckedExceptionsSupplier(this::hasNextInternal).get();
	}

	@Override
	public final X next() {
		ensureNotClosed();
		if (!hasNext()) throw new NoSuchElementException();
		X next = ignoreCheckedExceptions(this::nextInternal);
		if (!hasNext()) ignoreCheckedExceptions(this::close);
		count++;
		return next;
	}
	
	@Override
	public final void close() throws IOException {
		closed = true;
		try {
			closeInternal();
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			try {
				if (onClose != null) onClose.accept(count);
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
	}
	
	public final RichIterator<X> onClose(ThrowingConsumer<Long> onClose) {
		this.onClose = onClose;
		return this;
	}
	
	public final <Y> RichIterator<Y> map(ThrowingFunction<X,Y> function) { 
		ensureNotClosed();
		return new MappedRichIterator<>(this,function); 
	}
	
	public final RichIterator<X> filter(ThrowingPredicate<X> predicate) { 
		ensureNotClosed();
		return new FilteredRichIterator<>(this,predicate); 
	}
	
	public final <K,V> PairRichIterator<K,V> mapToPair(ThrowingFunction<X,K> keyFunction, ThrowingFunction<X,V> valueFunction) {
		ensureNotClosed();
		return PairRichIterator.create(this,keyFunction,valueFunction);
	}
	
	public final <Y> PairRichIterator<X,Y> zip(RichIterator<Y> that) {
		ensureNotClosed();
		return new ZippedRichIterator<>(this,that);
	}
	
	public final Optional<X> reduce(ThrowingBinaryOperator<X> binaryOp) {
		return stream().reduce(ignoreCheckedExceptionsBinaryOperator(binaryOp));
	}
	
	public final List<X> toList() { return stream().collect(Collectors.toList()); }
	
	public final <K,V> Map<K,V> toMap(ThrowingFunction<X,K> keyFunction, ThrowingFunction<X,V> valueFunction) { 
		return mapToPair(keyFunction,valueFunction).toMap();
	}
	
	public final Stream<X> stream() { 
		ensureNotClosed();
		ensureNotAlreadyUsed();
		used = true;
		return StreamUtils.iteratorToStream(this);
	}
	
	private void ensureNotClosed() {
		if (closed) throw new IllegalStateException("This iterator is already closed");
	}

	private void ensureNotAlreadyUsed() {
		if (used) throw new IllegalStateException("This object can only be iterated once");
	}

	protected abstract boolean hasNextInternal() throws Exception;
	protected abstract X nextInternal() throws Exception;
	protected void closeInternal() throws IOException { }
}
