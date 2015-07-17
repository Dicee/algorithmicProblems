package miscellaneous.utils.collection.richIterator;

import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingFunction;

public abstract class GroupedRichIterator<T> extends RichIterator<RichIterator<T>> {
	public static <T> GroupedRichIterator<T> create(RichIterator<RichIterator<T>> it) {
		return new GroupedRichIterator<T>() {
			@Override
			protected boolean hasNextInternal() throws Exception { return it.hasNext(); }

			@Override
			protected RichIterator<T> nextInternal() throws Exception { return it.next(); }
		};
	}
	
	public RichIterator<T> flatten() { return flatMap(ThrowingFunction.identity()); }
}
