package miscellaneous.utils.collection.richIterator;

public class LookAheadRichIterator<X> extends BufferedRichIterator<X> {
	public LookAheadRichIterator(RichIterator<X> it) { super(it, 1); }
}
