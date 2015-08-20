package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;

import miscellaneous.utils.exceptions.ExceptionUtils.ThrowingPredicate;

public class UntilRichIterator<X> extends BufferedRichIterator<X> {
	public UntilRichIterator(RichIterator<X> it, ThrowingPredicate<X> predicate) {
		super(new LookAheadIterator<X>() {
			private boolean found = false;
			
			@Override
			public X readNext() throws EOFException, IOException {
				if (found || !it.hasNext()) return null;
				X next = it.next();
				try {
					found = predicate.test(next);
					return next;
				} catch (Exception e) {
					throw new IOException(e);
				}
			}
		});
	}
}
