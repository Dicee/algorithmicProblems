package miscellaneous.utils.collection.richIterator;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class DistinctRichIterator<T> extends LookAheadRichIterator<T> {
	public DistinctRichIterator(RichIterator<T> it) {
		super(new FromResourceRichIterator<T>() {
			private final Set<T> elts = new HashSet<>();
			
			@Override
			public T readNext() throws EOFException, IOException {
				while (it.hasNext()) {
					T next = it.next();
					if (elts.add(next)) return next;
				}
				return null;
			}
		});
	}
	
	
}
	
