package miscellaneous.utils.collection.richIterator;

import javafx.util.Pair;

class ZippedRichIterator<X,Y> extends PairRichIterator<X,Y> {
	public ZippedRichIterator(RichIterator<X> left, RichIterator<Y> right) {
		super(new RichIterator<Pair<X,Y>>() {
			@Override
			protected boolean hasNextInternal() throws Exception { return left.hasNext() && right.hasNext(); }

			@Override
			protected Pair<X,Y> nextInternal() throws Exception { return new Pair<>(left.next(),right.next()); }
		});
	}
}
