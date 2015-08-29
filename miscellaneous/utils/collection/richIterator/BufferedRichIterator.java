package miscellaneous.utils.collection.richIterator;

import miscellaneous.utils.check.Check;
import miscellaneous.utils.collection.BoundedBuffer;
import miscellaneous.utils.collection.BoundedBuffer.SizeExceededPolicy;

public class BufferedRichIterator<X> extends ClassicRichIteratorDecorator<X, X> implements LookAheadIterator<X> {
	private final BoundedBuffer<X> buffer;
	
	public BufferedRichIterator(RichIterator<X> it, int size) { 
		super(it);
		Check.isGreaterThan(size, 0);
		this.buffer = new BoundedBuffer<>(size, SizeExceededPolicy.ERROR); 
	}

	@Override
	protected final boolean hasNextInternal() { return !buffer.isEmpty() || it.hasNext(); }

	@Override
	protected final X nextInternal() {
		fillBufferIfNecessary();
		return buffer.pop();
	}

	private void fillBufferIfNecessary() {
		if (buffer.isEmpty())
			while (it.hasNext() && !buffer.isFull()) buffer.addLast(it.next());
	}
	
	@Override
	public X peek() { 
		fillBufferIfNecessary();
		return buffer.peek(); 
	}
}
