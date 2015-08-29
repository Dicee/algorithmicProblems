package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;
import miscellaneous.utils.exceptions.ExceptionUtils.ThrowingPredicate;

public class WhileRichIterator<X> extends RichIteratorDecorator<X, X, LookAheadRichIterator<X>> {
	private final ThrowingPredicate<X> predicate;

	public WhileRichIterator(RichIterator<X> it, ThrowingPredicate<X> predicate) {
		super(new LookAheadRichIterator<X>(it));
		this.predicate = notNull(predicate);
	}

	@Override protected boolean hasNextInternal() throws Exception { return it.peek() != null && predicate.test(it.peek()); }
	@Override protected X       nextInternal   () throws Exception { return it.next(); }
}
