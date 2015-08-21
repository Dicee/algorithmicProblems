package miscellaneous.utils.collection.richIterator;

import miscellaneous.utils.exceptions.ExceptionUtils.ThrowingFunction;

public interface IteratorTransformation<X, Y> extends ThrowingFunction<RichIterator<X>, RichIterator<Y>> { 
	public static <X> IteratorTransformation<X, X> identity() { return it -> it; };
}
