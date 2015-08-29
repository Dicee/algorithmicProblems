package miscellaneous.utils.test.collection.richIterator;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Iterator;

import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIteratorDecorator;
import miscellaneous.utils.collection.richIterator.RichIterators;

public class RichIteratorTestUtils {
	public static void assertIteratorsAreEqual(Iterator<?> actual, Iterator<?> expected) {
		assertThat(RichIterators.wrap(actual).toList(), equalTo(RichIterators.wrap(expected).toList()));
	}
	
	public static <X> ObservableRichIterator<X> observable(RichIterator<X> it) { return new ObservableRichIterator<>(it); } 

	public static class ObservableRichIterator<X> extends RichIteratorDecorator<X, X, RichIterator<X>> {
		private int nextCalls, hasNextCalls, closeCalls;
		
		protected ObservableRichIterator(RichIterator<X> it) { super(it); }
		
		@Override protected boolean hasNextInternal() throws   Exception { hasNextCalls++; return it.hasNext(); }
		@Override protected X       nextInternal   () throws   Exception { nextCalls   ++; return it.next()   ; }
		@Override public void       closeInternal  () throws IOException { closeCalls  ++; }

		public int getNextCalls   () { return nextCalls   ; }
		public int getHasNextCalls() { return hasNextCalls; }
		public int getCloseCalls  () { return closeCalls  ; }
	}
}
