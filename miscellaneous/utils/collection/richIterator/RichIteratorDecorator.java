package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.io.IOException;

public abstract class RichIteratorDecorator<X, Y, ITERATOR extends RichIterator<X>> extends RichIterator<Y> {
	protected final ITERATOR it;
	protected RichIteratorDecorator(ITERATOR it) { this.it = notNull(it); }
	@Override protected void closeInternal() throws IOException { it.close(); }
}
