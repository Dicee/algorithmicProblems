package miscellaneous.utils.collection.richIterator;

import static java.util.stream.Collectors.joining;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptions;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptionsBinaryOperator;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptionsConsumer;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptionsSupplier;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptionsUnaryOperator;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.util.Pair;
import miscellaneous.utils.collection.StreamUtils;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingBinaryOperator;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingConsumer;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingFunction;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingPredicate;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingUnaryOperator;
/**
 * - sliding
 * - grouped
 * - max, min
 * - lastIndexWhere
 * - drop, dropWhile, dropUntil
 */
public abstract class RichIterator<X> implements Iterator<X>, Iterable<X>, Closeable, AutoCloseable {
	public static <X> RichIterator<X> iterate(X seed, ThrowingUnaryOperator<X> throwingOp) {
		UnaryOperator<X> op = ignoreCheckedExceptionsUnaryOperator(throwingOp);
		return RichIterators.wrap(new Iterator<X>() {
			private X current = seed;
			
			@Override
			public boolean hasNext() { return true; }

			@Override
			public X next() {
				X res = current;
				current = op.apply(current);
				return res;
			}
		});
	}
	
	private boolean			closed	= false;
	private boolean			used	= false;
	private long			count	= 0;
	private ThrowingConsumer<Long>	onClose;
	
	public Iterator<X> iterator() {
		ensureValidState();
		used = true;
		return this;
	}

	@Override
    // stupid Eclipse compiler cannot infer correctly
	public final boolean hasNext() { return !closed && ignoreCheckedExceptionsSupplier(this::hasNextInternal).get(); }

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
	
	protected abstract boolean hasNextInternal() throws Exception;
	protected abstract X nextInternal() throws Exception;
	protected void closeInternal() throws IOException { }
	
	public final RichIterator<X> onClose(ThrowingConsumer<Long> onClose) {
		this.onClose = onClose;
		return this;
	}
	
	public final <Y> RichIterator<Y> map(ThrowingFunction<X,Y> function) { 
		ensureValidState();
		return new MappedRichIterator<>(this,function); 
	}
	
	public final <Y> RichIterator<Y> flatMap(ThrowingFunction<X,? extends Iterable<Y>> function) { 
		ensureValidState();
		return new FlatMappedRichIterator<X,Y>(this,function); 
	}
	
	public final RichIterator<X> filter(ThrowingPredicate<X> predicate) { 
		ensureValidState();
		return new FilteredRichIterator<>(this,predicate); 
	}
	
	public final <K,V> PairRichIterator<K,V> mapToPair(ThrowingFunction<X,K> keyFunction, ThrowingFunction<X,V> valueFunction) {
		ensureValidState();
		return PairRichIterator.create(this,keyFunction,valueFunction);
	}
	
	public final <Y> PairRichIterator<X,Y> zip(RichIterator<Y> that) {
		ensureValidState();
		return new ZippedRichIterator<>(this,that);
	}
	
	public final PairRichIterator<Integer,X> zipWithIndex() {
		ensureValidState();
		return new ZippedRichIterator<>(RichIntIterator.counter(),this);
	}
	
	public final <K> PairRichIterator<K,List<X>> groupBy(ThrowingFunction<X,K> classifier) {
		ensureValidState();
		return mapToPair(classifier,ThrowingFunction.identity()).groupByKey();
	}

	public final GroupedRichIterator<X> grouped(int size) {
		ensureValidState();
		RichIterator<X> thizz = this;
		return GroupedRichIterator.create(new RichIterator<RichIterator<X>>() {
			@Override
			protected RichIterator<X> nextInternal() throws Exception {
				Deque<X> buffer = new LinkedList<>();
				for (int i=0 ; i<size && thizz.hasNext() ; i++) buffer.add(thizz.next());
				return RichIterators.fromCollection(buffer);
			}
			
			@Override
			protected boolean hasNextInternal() throws Exception { return thizz.hasNext(); }
			
			@Override
			protected void closeInternal() throws IOException { thizz.close(); }
		});
	}
	
	public final RichIterator<X> take(int n) {
		ensureValidState();
		return new LimitedIterator<>(this,n);
	}
	
	public final RichIterator<X> takeWhile(ThrowingPredicate<X> predicate) {
		ensureValidState();
		return new WhileRichIterator<>(this,predicate);
	}
	
	public final RichIterator<X> takeUntil(ThrowingPredicate<X> predicate) {
		ensureValidState();
		return new UntilRichIterator<>(this,predicate);
	}

	public final RichIterator<X> distinct() {
		ensureValidState();
		return new DistinctRichIterator<>(this);
	}
	
	public final RichIterator<X> sorted(Comparator<? super X> cmp) {
		ensureValidState();
		return new SortedRichIterator<>(this,cmp);
	}
	
	public final RichIterator<X> sorted() { return sorted(null); }
	
	public final <Y> Y fold(Y initialValue, BiFunction<X,Y,Y> combiner) {
		Y res = initialValue;
		while (hasNext()) res = combiner.apply(next(),res);
		return res;
	}
	
	public final Optional<X> reduce(ThrowingBinaryOperator<X> binaryOp) { return stream().reduce(ignoreCheckedExceptionsBinaryOperator(binaryOp)); }
	public final void foreach(ThrowingConsumer<X> consumer)             { stream().forEach(ignoreCheckedExceptionsConsumer(consumer)); }
	public final long count()                                           { return stream().count(); }
	
	public final String mkString()                                      { return mkString("");                                                        }
	public final String mkString(String sep)                            { return mkString("",sep,"");                                                 }
	public final String mkString(String first, String sep, String last) { return first + stream().map(Object::toString).collect(joining(sep)) + last; }
	
	public final void writeToFile(File f) throws IOException {
		try (BufferedWriter bw = Files.newBufferedWriter(f.toPath())) {
			map(Object::toString).foreach(bw::write);
		}
	}
	
	public final void writeToFile(File f, String sep) throws IOException {
		try (BufferedWriter bw = Files.newBufferedWriter(f.toPath())) {
			boolean first = true;
			for (String s : map(Object::toString)) {
				if (!first) {
					first = false;
					bw.write(sep);
				}
				bw.write(s);
			}
		}
	}

	/**
	 * Finds the first element of the RichIterator matching a predicate
	 * @note Important : this is NOT a terminal operation in the general case. It will only close the RichIterator if all
	 * 		 its elements have been consumed during the search. Thus, you can safely repeat
	 * @param predicate
	 * @return the first element of the RichIterator matching the predicate or Optional.empty() if no match was found
	 */
	public final Optional<X> findAny(ThrowingPredicate<X> predicate) {
		ensureValidState();
		return filter(predicate).stream().findAny();
	}
	
	public final Optional<X> findFirst(ThrowingPredicate<X> predicate) {
		ensureValidState();
		return filter(predicate).stream().findFirst();
	}
	
	public final int indexWhere(ThrowingPredicate<X> predicate) {
		ensureValidState();
		return zipWithIndex().findFirst(pair -> predicate.test(pair.getValue())).map(Pair::getKey).orElse(-1);
	}
	
	public final boolean forall(ThrowingPredicate<X> predicate) { return !exists(predicate.negate())    ; }
	public final boolean exists(ThrowingPredicate<X> predicate) { return  findAny(predicate).isPresent(); }
	
	public final List<X> toList() { return stream().collect(Collectors.toList()); }
	public final Set<X> toSet() { return stream().collect(Collectors.toSet()); }
	public final <K,V> Map<K,V> toMap(ThrowingFunction<X,K> keyFunction, ThrowingFunction<X,V> valueFunction) { return mapToPair(keyFunction,valueFunction).toMap(); }
	
	public final Stream<X> stream() { 
		ensureValidState();
		used = true;
		return StreamUtils.iteratorToStream(this);
	}

	private void ensureValidState() {
		ensureNotClosed();
		ensureNotAlreadyUsed();
	}
	
	private void ensureNotClosed     () { if (closed) throw new IllegalStateException("This iterator is already closed"      ); }
	private void ensureNotAlreadyUsed() { if (used  ) throw new IllegalStateException("This object can only be iterated once"); }

	public final boolean isClosed() { return closed; }
}
