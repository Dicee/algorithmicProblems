package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.isGreaterThan;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class UniformBatchRichIterator<X> extends ClassicRichIteratorDecorator<X, RichIterator<X>>{
	private final int batchSize;

	protected UniformBatchRichIterator(RichIterator<X> it, int batchSize) {
		super(it);
		isGreaterThan(batchSize, 0);
		this.batchSize = batchSize;
	}

	@Override
	protected RichIterator<X> nextInternal() throws Exception {
		Deque<X> buffer = new LinkedList<>();
		for (int i=0 ; i<batchSize && it.hasNext() ; i++) buffer.add(it.next());
		return RichIterators.fromCollection(buffer);
	}
	
	@Override
	protected boolean hasNextInternal() throws Exception { return it.hasNext(); }
	
	@Override
	protected void closeInternal() throws IOException { it.close(); }
}
