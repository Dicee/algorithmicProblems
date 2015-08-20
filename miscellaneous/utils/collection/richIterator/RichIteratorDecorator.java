package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.io.IOException;

abstract class RichIteratorDecorator<X,Y> extends RichIterator<Y> {
	protected final RichIterator<X> it;
	protected RichIteratorDecorator(RichIterator<X> it) { this.it = notNull(it); }
	@Override protected void closeInternal() throws IOException { it.close(); }
}
