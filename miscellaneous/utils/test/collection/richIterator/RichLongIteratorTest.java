package miscellaneous.utils.test.collection.richIterator;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import miscellaneous.utils.collection.richIterator.RichLongIterator;

import org.hamcrest.Matchers;
import org.junit.Test;

public class RichLongIteratorTest {
	@Test
	public void testRange() {
		assertThat(RichLongIterator.range(0,3).toList(), Matchers.equalTo(Arrays.asList(0L,1L,2L)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRangeFailsIfEmptyRange() {
		RichLongIterator.range(0,0);
	}
	
	@Test
	public void testClosedRange() {
		assertThat(RichLongIterator.closedRange(0,3).toList(), Matchers.equalTo(Arrays.asList(0L,1L,2L,3L)));
	}
}
