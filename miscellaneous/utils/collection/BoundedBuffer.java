package miscellaneous.utils.collection;

import static miscellaneous.utils.check.Check.notNull;
import static miscellaneous.utils.exceptions.UnknownEnumValueException.unknownEnumValue;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import miscellaneous.utils.check.Check;

public class BoundedBuffer<T> implements Deque<T> {
	public static enum SizeExceededPolicy { ERROR, IGNORE }
	
	private final int					maxSize;
	private final Deque<T>				buffer;
	private final SizeExceededPolicy	sizeExceededPolicy;
	
	public BoundedBuffer(int maxSize, SizeExceededPolicy sizeExceededPolicy) {
		this.maxSize            = Check.isPositive(maxSize);
		this.buffer             = new LinkedList<>();
		this.sizeExceededPolicy = sizeExceededPolicy;
	}
	
	public BoundedBuffer(int maxSize, Collection<T> buffer, SizeExceededPolicy sizeExceededPolicy) {
		this(maxSize, sizeExceededPolicy);
		addAll(notNull(buffer));
	}

	public boolean isFull      ()    { return maxSize == buffer.size(); }
	public boolean addIfNotFull(T t) { return !isFull() && add(t)     ; }
	
	@Override public boolean addAll(Collection<? extends T> c) { 
		switch (sizeExceededPolicy) {
			case ERROR : return ensureHasCpacity(c.size()) && buffer.addAll(c); 
			case IGNORE: 
				boolean added = !isFull() || c.size() == 0;
				Iterator<? extends T> it = c.iterator();
				while (!isFull() && it.hasNext()) added = add(it.next()) || added;
				return added;
			default: throw unknownEnumValue(SizeExceededPolicy.class, sizeExceededPolicy);
		}
	}
	
	@Override public boolean add       (T t) { return ensureHasCpacity(1) && buffer.add(t)       ; }
	@Override public boolean offerFirst(T t) { return ensureHasCpacity(1) && buffer.offerFirst(t); }
	@Override public boolean offerLast (T t) { return ensureHasCpacity(1) && buffer.offerLast(t) ; }
	@Override public void    addFirst  (T t) { if (ensureHasCpacity(1))      buffer.addFirst(t)  ; }

	private boolean ensureHasCpacity(int toAdd) { 
		if (toAdd + buffer.size() > maxSize)
			switch (sizeExceededPolicy) {
				case IGNORE: return false;
				case ERROR : throw new SizeExceededException();
				default    : throw unknownEnumValue(SizeExceededPolicy.class, sizeExceededPolicy);
			}
		return true;
	}
	
	@Override public void        clear()                         {        buffer.clear()                 ; }
	@Override public boolean     containsAll(Collection<?> c)    { return buffer.containsAll(c)          ; }
	@Override public boolean     isEmpty()                       { return buffer.isEmpty()               ; }
	@Override public boolean     removeAll(Collection<?> c)      { return buffer.removeAll(c)            ; }
	@Override public boolean     retainAll(Collection<?> c)      { return buffer.retainAll(c)            ; }
	@Override public Object[]    toArray()                       { return buffer.toArray()               ; }
	@Override public <X> X[]     toArray(X[] a)                  { return buffer.toArray(a)              ; }
	@Override public boolean     contains(Object o)              { return buffer.contains(o)             ; }
	@Override public Iterator<T> descendingIterator()            { return buffer.descendingIterator()    ; }
	@Override public T           element()                       { return buffer.element()               ; }
	@Override public T           getFirst()                      { return buffer.getFirst()              ; }
	@Override public T           getLast()                       { return buffer.getLast()               ; }
	@Override public Iterator<T> iterator()                      { return buffer.iterator()              ; }
	@Override public T           peek()                          { return buffer.peek()                  ; }
	@Override public T           peekFirst()                     { return buffer.peekFirst()             ; }
	@Override public T           peekLast()                      { return buffer.peekLast()              ; }
	@Override public T           pollFirst()                     { return buffer.pollFirst()             ; }
    @Override public T           pollLast()                      { return buffer.pollLast()              ; }
	@Override public boolean     remove(Object o)                { return buffer.remove(o)               ; }
	@Override public T           removeFirst()                   { return buffer.removeFirst()           ; }
	@Override public T           removeLast()                    { return buffer.removeLast()            ; }
	@Override public boolean     removeLastOccurrence(Object o)  { return buffer.removeLastOccurrence(o) ; }
	@Override public int         size()                          { return buffer.size()                  ; }
	@Override public boolean     removeFirstOccurrence(Object o) { return buffer.removeFirstOccurrence(o); }
	@Override public T           remove()                        { return buffer.remove()                ; }
	@Override public T           pop()                           { return buffer.pop()                   ; }
	@Override public T           poll()                          { return buffer.poll()                  ; }
	@Override public String      toString()                      { return buffer.toString()              ; }
	@Override public boolean     offer(T t)                      { return offerLast(t)                   ; }
	@Override public void        addLast(T t)                    {        offerLast(t)                   ; }
	@Override public void        push(T t)                       {        addFirst(t)                    ; }
	
	public static class SizeExceededException extends RuntimeException { private static final long	serialVersionUID = 1L; }
}
