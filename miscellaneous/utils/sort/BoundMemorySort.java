package miscellaneous.utils.sort;

import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckedConsumer;
import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckExceptionsAndGet;
import static miscellaneous.utils.math.MathUtils.lowerOrEqual;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import miscellaneous.utils.collection.ArrayUtils;
import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;

import com.google.common.base.Throwables;

public class BoundMemorySort<T extends Comparable<T> & Serializable> {
	private final Class<T>	clazz;
	private final T[]		buffer;
	private int				filledTo;
	private File			merged;

	public BoundMemorySort(Class<T> clazz, int bufferSize) {
		this.buffer = ArrayUtils.ofDim(clazz,bufferSize);
		this.clazz  = clazz;
		this.merged = tempMergeFile();
	}
	
	public RichIterator<T> sort(Iterable<T> iterable) { return sort(iterable.iterator()); }
	
	public RichIterator<T> sort(Iterator<T> source) {
		fillBuffer(source);
		T[] sorted = sortBuffer();
		merge(sorted);
		return source.hasNext() ? sort(source) : RichIterators.fromSerializedRecords(merged,clazz);
	}

	private T[] sortBuffer() {
		T[] toSort = filledTo < buffer.length ? Arrays.copyOfRange(buffer,0,filledTo) : buffer;
		Arrays.sort(toSort);
		return toSort;
	}

	private void merge(T[] toSort) {
		File newMerged = tempMergeFile();
		try (FileOutputStream fos = new FileOutputStream(newMerged) ; ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			Iterator<T> it0 = RichIterators.fromSerializedRecords(merged,clazz);
			Iterator<T> it1 = Arrays.asList(toSort).iterator();
			Optional<T> next0 = Optional.empty(), next1 = Optional.empty();
			
			while (hasNext(it0,next0) && hasNext(it1,next1)) {
				T elt0 = next0.orElseGet(it0::next);
				T elt1 = next1.orElseGet(it1::next);
				if (lowerOrEqual(elt0,elt1)) {
					oos.writeObject(elt0);
					next0 = Optional.empty();
					next1 = Optional.of(elt1);
				} else {
					oos.writeObject(elt1);
					next0 = Optional.of(elt0);
					next1 = Optional.empty();
				}
			}
			
			RichIterator<T> remaining =    hasNext(it0,next0) ? 
					remaining(it0,next0) : hasNext(it1,next1) ? 
					remaining(it1,next1) : RichIterators.emptyIterator();
			remaining.forEach(uncheckedConsumer(oos::writeObject));
			remaining.close();
			
			merged = newMerged;
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	private RichIterator<T> remaining(Iterator<T> it, Optional<T> head) {
		 return head.isPresent() ? RichIterators.prepend(head.get(),it) : RichIterators.wrap(it);
	}

	private File tempMergeFile() {
		return uncheckExceptionsAndGet(() -> File.createTempFile("merge",null));
	}

	private boolean hasNext(Iterator<T> it0, Optional<T> next0) {
		return next0.isPresent() || it0.hasNext();
	}

	private void fillBuffer(Iterator<T> source) {
		filledTo = 0;
		for (int i=0 ; i<buffer.length && source.hasNext() ; buffer[i++] = source.next(), filledTo++);
	}
}
