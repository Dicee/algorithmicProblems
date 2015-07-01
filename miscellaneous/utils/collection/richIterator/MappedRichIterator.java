package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingFunction;

class MappedRichIterator<INPUT,OUTPUT> extends RichIteratorDecorator<INPUT,OUTPUT> {
	private final ThrowingFunction<INPUT, OUTPUT> mapper;

	public MappedRichIterator(RichIterator<INPUT> it, ThrowingFunction<INPUT,OUTPUT> mapper) { 
		super(it);
		this.mapper = notNull(mapper);
	}

	@Override protected boolean hasNextInternal() throws Exception { return it.hasNext()           ; }
	@Override protected OUTPUT  nextInternal   () throws Exception { return mapper.apply(it.next()); }
}
