package miscellaneous.utils.collection.richIterator;

import miscellaneous.utils.exceptions.ExceptionUtils.ThrowingFunction;

public class GroupedRichIterator<T> extends ClassicRichIteratorDecorator<RichIterator<T>, RichIterator<T>> {
	public static <T> GroupedRichIterator<T> create(RichIterator<RichIterator<T>> it) { return new GroupedRichIterator<>(it); }
	private GroupedRichIterator(RichIterator<RichIterator<T>> it) { super(it); }
	
	@Override
	protected boolean hasNextInternal() throws Exception { return it.hasNext(); }

	@Override
	protected RichIterator<T> nextInternal() throws Exception { return it.next(); }
	
	public RichIterator<T> flatten() { return flatMap(ThrowingFunction.identity()); }
	
	public <S> GroupedRichIterator<S> mapGroups(IteratorTransformation<T, S> transformation) { return create(map(transformation)); }
}
