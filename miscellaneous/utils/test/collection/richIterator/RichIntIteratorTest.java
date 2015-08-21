package miscellaneous.utils.test.collection.richIterator;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import miscellaneous.utils.collection.richIterator.RichIntIterator;

import org.hamcrest.Matchers;
import org.junit.Test;

public class RichIntIteratorTest {
	@Test
	public void testRange() {
		assertThat(RichIntIterator.range(0,3).toList(), Matchers.equalTo(Arrays.asList(0,1,2)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRangeFailsIfEmptyRange() {
		assertThat(RichIntIterator.range(0,0).toList(), Matchers.equalTo(Arrays.asList(0,1,2)));
	}
	
	@Test
	public void testClosedRange() {
		assertThat(RichIntIterator.closedRange(0,3).toList(), Matchers.equalTo(Arrays.asList(0,1,2,3)));
	}
}
