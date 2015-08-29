package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.util.HashSet;
import java.util.Set;

class DistinctRichIterator<X> extends NullableRichIterator<X> {
	private final Set<X>			elts	= new HashSet<>();
	private final RichIterator<X>	it;

	public DistinctRichIterator(RichIterator<X> it) { this.it = notNull(it); }

	@Override
	protected X nextOrNull() throws Exception {
		while (it.hasNext()) {
			X next = it.next();
			if (elts.add(next)) return next;
		}
		return null;
	}
}
