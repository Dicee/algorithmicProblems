package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;

import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingPredicate;

public class WhileRichIterator<X> extends BufferedRichIterator<X> {
	public WhileRichIterator(RichIterator<X> it, ThrowingPredicate<X> predicate) {
		super(new LookAheadIterator<X>() {
			@Override
			public X readNext() throws EOFException, IOException {
				if (!it.hasNext()) return null;
				X next = it.next();
				try {
					if (!predicate.test(next)) return null;
					return next;
				} catch (Exception e) {
					throw new IOException(e);
				}
			}
		});
	}
}
