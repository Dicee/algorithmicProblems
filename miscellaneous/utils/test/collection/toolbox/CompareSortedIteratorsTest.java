package miscellaneous.utils.test.collection.toolbox;

import static java.util.Arrays.asList;
import static miscellaneous.utils.collection.toolbox.Diff.diff;
import static miscellaneous.utils.collection.toolbox.Diff.missing;
import static miscellaneous.utils.collection.toolbox.Diff.unexpected;
import static miscellaneous.utils.strings.StringUtils.lastChar;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import miscellaneous.utils.collection.richIterator.RichIterators;
import miscellaneous.utils.collection.toolbox.CompareSortedIterators;
import miscellaneous.utils.collection.toolbox.Diff;
import miscellaneous.utils.collection.toolbox.DiffReport;

import org.junit.Before;
import org.junit.Test;

public class CompareSortedIteratorsTest {
	private Iterator<String>	expected;
	private TextComparison		compareSorted;

	@Before
	public void setUp() {
		this.compareSorted = new TextComparison();
		this.expected      = RichIterators.of("z", "a", "us", "xhtml");
	}
	
	@Test(expected = IllegalStateException.class)
	public void failsIfNotSorted() {
		Iterator<String> actual = RichIterators.of("z", "a", "xhtml", "us");
		compareSorted.compare(actual, expected);
	}
	
	@Test
	public void testSimpleComparison() {
		Iterator  <String> actual = RichIterators.of("z", "A", "ba", "xhtml");
		DiffReport<String> report = compareSorted.compare(actual, expected);
		assertReportEqualsTo(report, 1, 0, 0, 4, asList(diff("ba", "us")));
	}

	@Test
	public void testUnexpected() {
		Iterator  <String> actual = RichIterators.of("z", "a", "r", "r", "us", "is", "xhtml");
		DiffReport<String> report = compareSorted.compare(actual, expected);
		assertReportEqualsTo(report, 0, 0, 3, 7, asList(unexpected("r"), unexpected("r"), unexpected("is")));
	}

	@Test
	public void testMissing() {
		Iterator  <String> actual = RichIterators.of("z", "us");
		DiffReport<String> report = compareSorted.compare(actual, expected);
		assertReportEqualsTo(report, 0, 2, 0, 4, asList(missing("a"), missing("xhtml")));
	}

	@Test
	public void testVariousDiffs() {
		Iterator  <String> actual   = RichIterators.of("z", "b", "r", "t", "us", "is", "qa", "di", "xhtml", "emlfp");
		Iterator  <String> expected = RichIterators.of("Z", "a", "r", "T", "us", "is", "qi", "qi", "naa", "xhTml");
		DiffReport<String> report   = compareSorted.compare(actual, expected);
		assertReportEqualsTo(report, 2, 1, 1, 11, asList(diff("b", "a"), diff("qa", "qi"), missing("naa"), unexpected("emlfp")));
		assertThat(report.getEventCount(TextComparison.EQUALS_IGNORE_CASE), is(3L));
		assertThat(report.getEventCount(TextComparison.LAST_CHAR_EQUAL)   , is(1L));
	}
	
	private static void assertReportEqualsTo(DiffReport<String> report, int diffCount, int missingCount, int unexpectedCount, int totalCount, List<Diff<String>> diffs) {
		assertThat(report.getDiffCount      (), is(diffCount));
		assertThat(report.getMissingCount   (), is(missingCount));
		assertThat(report.getUnexpectedCount(), is(unexpectedCount));
		assertThat(report.getTotalCount     (), is(totalCount));
		assertThat(report.getDiffs          (), equalTo(diffs));
	}
	
	private static class TextComparison extends CompareSortedIterators<String> {
		private static final String EQUALS_IGNORE_CASE = "equals_ignore_case";
		private static final String LAST_CHAR_EQUAL    = "last_char_equal";
	
		public TextComparison() { super((s0, s1) -> Integer.compare(s0.length(), s1.length())); }
	
		@Override
		protected boolean deepCheckValidity(String actual, String expected, DiffReport<String> report) { 
			boolean isValid = false;
			if      (isValid = actual.equalsIgnoreCase(expected))      report.reportEvent(EQUALS_IGNORE_CASE);
			else if (isValid = lastChar(actual) == lastChar(expected)) report.reportEvent(LAST_CHAR_EQUAL);
			return isValid;
		}
	}
}