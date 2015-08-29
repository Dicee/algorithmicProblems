package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;
import static miscellaneous.utils.collection.richIterator.RichIterators.singleton;

import java.io.IOException;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

class GroupByComparatorRichIterator<X> extends ClassicRichIteratorDecorator<X, RichIterator<X>> {
	private X previous = null;
	private final Comparator<X>	cmp;
	
	protected GroupByComparatorRichIterator(RichIterator<X> it, Comparator<X> cmp) {
		super(it);
		this.cmp = notNull(cmp);
	}
	
	@Override
	protected boolean hasNextInternal() throws Exception { return previous != null || it.hasNext(); }

	@Override
	protected RichIterator<X> nextInternal() throws Exception {
		if (!it.hasNext() && previous != null) { 
			RichIterator<X> result = singleton(previous);
			previous = null;
			return result;
		}
		
		X current = it.next();
		if (previous == null) {
			previous = current;
			return it.hasNext() ? nextInternal() : singleton(current);
		}
		return RichIterators.fromCollection(takeGroup(cmp,it,current));
	}

	private Deque<X> takeGroup(Comparator<X> cmp, RichIterator<X> it, X current) {
		Deque<X> buffer = new LinkedList<>();
		buffer.addLast(previous);
		
		boolean eof = false;
		while (!eof && cmp.compare(previous, current) == 0) {
			buffer.addLast(current);
			if (!(eof = !it.hasNext())) current = it.next(); 
		}
		if (!eof && cmp.compare(previous, current) > 0) throw new IllegalStateException("RichIterator not sorted");
		previous = eof ? null : current;
		return buffer;
	}
	
	@Override
	protected void closeInternal() throws IOException { it.close(); }
}
