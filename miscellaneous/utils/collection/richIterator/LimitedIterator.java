package miscellaneous.utils.collection.richIterator;

public class LimitedIterator<X> extends ClassicRichIteratorDecorator<X,X> {
	private int limit;
	
	public LimitedIterator(RichIterator<X> it, int limit) {
		super(it);
		this.limit = limit;
	}

	@Override
	protected boolean hasNextInternal() throws Exception { return limit != 0 && it.hasNext(); }

	@Override
	protected X nextInternal() throws Exception {
		limit--;
		return it.next();
	}
}
