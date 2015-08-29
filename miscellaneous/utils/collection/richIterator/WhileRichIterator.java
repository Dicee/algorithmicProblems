package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;

import miscellaneous.utils.exceptions.ExceptionUtils.ThrowingPredicate;

public class WhileRichIterator<X> extends LookAheadRichIterator<X> {
	public WhileRichIterator(RichIterator<X> it, ThrowingPredicate<X> predicate) {
		super(new FromResourceRichIterator<X>() {
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
