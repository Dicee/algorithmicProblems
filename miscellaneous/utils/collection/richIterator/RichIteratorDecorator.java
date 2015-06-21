package miscellaneous.utils.collection.richIterator;

abstract class RichIteratorDecorator<X,Y> extends RichIterator<Y> {
	protected final RichIterator<X> it;
	public RichIteratorDecorator(RichIterator<X> it) { this.it = it; }
}
