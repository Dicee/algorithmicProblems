package miscellaneous.utils.collection;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import miscellaneous.utils.check.Check;

public class BoundedBuffer<T> implements Deque<T> {
	private final int maxSize;
	private final Deque<T> buffer;
	
	public BoundedBuffer(int maxSize) {
		this.maxSize = Check.isPositive(maxSize);
		this.buffer  = new LinkedList<>();
	}
	
	public BoundedBuffer(int maxSize, Collection<T> buffer) {
		Check.isGreaterOrEqual(maxSize, buffer.size());
		this.maxSize = maxSize;
		this.buffer  = new LinkedList<>(Check.notNull(buffer));
	}

	@Override
	public boolean addAll(Collection<? extends T> c) { 
		ensureHasCpacity(c.size());
		return buffer.addAll(c);
	}

	@Override 
	public boolean add(T t) {
		ensureHasCpacity(1);
		return buffer.add(t);
	}

	@Override 
	public void addFirst(T t) {
		ensureHasCpacity(1);
		buffer.addFirst(t);
	}

	@Override
	public boolean offerFirst(T t) {
		ensureHasCpacity(1);
		return buffer.offerFirst(t);
	}
	@Override
	public boolean offerLast(T t) {
		ensureHasCpacity(1);
		return buffer.offerLast(t);
	}

	private void ensureHasCpacity(int toAdd) { if (toAdd + buffer.size() > maxSize) throw new SizeExceededException(); }
	
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
