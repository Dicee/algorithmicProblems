package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;
import static miscellaneous.utils.collection.CollectionUtils.collectList;
import static miscellaneous.utils.collection.toolbox.DiffReport.NO_LIMIT;

import java.util.List;

import miscellaneous.utils.check.Check;
import miscellaneous.utils.collection.richIterator.RichIterator;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class SortedIteratorEqualMatcher<T> extends TypeSafeMatcher<RichIterator<T>> {
	public static <T> SortedIteratorEqualMatcher<T> sortedIteratorEqual(RichIterator<T> expected, CompareSortedIterators<T> compareSortedIterators) {
		return new SortedIteratorEqualMatcher<>(expected, compareSortedIterators);
	}

	public static <T> SortedIteratorEqualMatcher<T> sortedIteratorEqual(RichIterator<T> expected, CompareSortedIterators<T> compareSortedIterators, int limit) {
		return new SortedIteratorEqualMatcher<>(expected, compareSortedIterators,limit);
	}

	private final RichIterator          <T>	expected;
	private final CompareSortedIterators<T>	compareSortedIterators;
	private final DiffReport            <T>	report;
	private final int						limit;

	public SortedIteratorEqualMatcher(RichIterator<T> expected, CompareSortedIterators<T> compareSortedIterators) {
		this(expected,compareSortedIterators, NO_LIMIT);
	}

	public SortedIteratorEqualMatcher(RichIterator<T> expected, CompareSortedIterators<T> compareSortedIterators, int limit) {
		Check.isTrue(limit == NO_LIMIT || limit > 0);
		this.compareSortedIterators = notNull(compareSortedIterators);
		this.expected               = notNull(expected);
		this.limit                  = limit;
		this.report                 = new DiffReport<>();
	}

	@Override
	public void describeTo(Description description) { description.appendValue(expected); }

	@Override
	public void describeMismatchSafely(RichIterator<T> item, Description description) {
		if (!report.getDiffs().isEmpty()) description.appendText("\n" + report.getDiffString());
	}

	@Override
	protected boolean matchesSafely(RichIterator<T> actual) {
		List<Diff<T>> diffs = limit == NO_LIMIT ? 
				compareSortedIterators.compareFully(actual, expected, report): 
				compareSortedIterators.compareIterators(actual, expected, report).stream().limit(limit).collect(collectList());
		return diffs.isEmpty();
	}
}