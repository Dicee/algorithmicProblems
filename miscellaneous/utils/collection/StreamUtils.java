package miscellaneous.utils.collection;

import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckedRunnable;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import miscellaneous.utils.collection.richIterator.RichIterator;

public class StreamUtils {
	private StreamUtils() { }
	
	public static <T> Stream<T> iteratorToStream(Iterator<T> it) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(it,0),false);
	}
	
	public static <T> Stream<T> iteratorToStream(RichIterator<T> it) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(it,0),false).onClose(uncheckedRunnable(it::close));
	}
}
