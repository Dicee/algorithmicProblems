package miscellaneous.utils.test.collection.richIterator;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import miscellaneous.utils.collection.richIterator.RichIterators;

public class RichIteratorTestUtils {
	public static void assertIteratorsAreEqual(Iterator<?> actual, Iterator<?> expected) {
		assertThat(RichIterators.wrap(actual).toList(), equalTo(RichIterators.wrap(expected).toList()));
	}
}
