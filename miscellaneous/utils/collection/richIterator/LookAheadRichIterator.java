package miscellaneous.utils.collection.richIterator;

public class LookAheadRichIterator<X> extends BufferedRichIterator<X> {
	protected LookAheadRichIterator(RichIterator<X> it) { super(it, 1); }
}
