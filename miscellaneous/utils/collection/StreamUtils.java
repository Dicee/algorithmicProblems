package miscellaneous.utils.collection;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {
	private StreamUtils() { }
	
	public static <T> Stream<T> iteratorToStream(Iterator<T> it) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(it,0),false);
	}
}
