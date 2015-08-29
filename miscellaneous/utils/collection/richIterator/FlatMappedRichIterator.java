package miscellaneous.utils.collection.richIterator;

import java.util.Collections;
import java.util.Iterator;

import miscellaneous.utils.check.Check;
import miscellaneous.utils.exceptions.ExceptionUtils.ThrowingFunction;

class FlatMappedRichIterator<X,Y> extends ClassicRichIteratorDecorator<X,Y> {
	private final ThrowingFunction<X,? extends Iterable<Y>> mapper;
	private Iterator<Y> current = Collections.emptyIterator();
	
	public FlatMappedRichIterator(RichIterator<X> it, ThrowingFunction<X,? extends Iterable<Y>> mapper) {
		super(it);
		this.mapper = Check.notNull(mapper);
	}
	
	@Override
	protected boolean hasNextInternal() throws Exception {
		if (!current.hasNext() && !it.hasNext()) return false; 
		if (current.hasNext()) return true;
		current = mapper.apply(it.next()).iterator();
		return hasNextInternal();
	}

	@Override
	protected Y nextInternal() throws Exception {
		// super.next(), which calls into nextInternal(), ensures that hasNext() is true if we get called, and hasNextInternal() always
		// provides a non-empty iterator (current) before returning true
		return current.next();
	}
}
