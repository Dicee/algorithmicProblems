package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;

import miscellaneous.utils.exceptions.ExceptionUtils.ThrowingPredicate;

public class FilteredRichIterator<T> extends BufferedRichIterator<T> {
	public FilteredRichIterator(RichIterator<T> it, ThrowingPredicate<T> predicate) { 
		super(new FromResourceRichIterator<T>() {
			@Override
			public T readNext() throws EOFException, IOException {
				try {
					while (it.hasNext()) {
						T next = it.next();
						if (predicate.test(next)) return next;
					}
					return null;
				} catch (Exception e) {
					throw new IOException(e);
				}
			}
		});
	}
}
