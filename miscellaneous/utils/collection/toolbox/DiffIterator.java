package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;

import java.util.Comparator;
import java.util.Iterator;

import miscellaneous.utils.collection.richIterator.LookAheadRichIterator;
import miscellaneous.utils.collection.richIterator.NullableRichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;
import miscellaneous.utils.collection.toolbox.CompareSortedIterators.DeepValidator;

class DiffIterator<T> extends NullableRichIterator<Diff<T>> {
	private final LookAheadRichIterator <T>	actualIt;
	private final LookAheadRichIterator <T>	expectedIt;
	private final DiffReport            <T>	report;
	private final Comparator            <T>	sortOrder;
	private final DeepValidator         <T>	deepValidator;

	DiffIterator(Iterator<T> actualIt, Iterator<T> expectedIt, DiffReport<T> report, Comparator<T> sortOrder, DeepValidator<T> deepValidator) {
    	this.actualIt       = new LookAheadRichIterator<>(RichIterators.wrap(notNull(actualIt)));
    	this.expectedIt     = new LookAheadRichIterator<>(RichIterators.wrap(notNull(expectedIt)));
    	this.report         = notNull(report);
    	this.sortOrder      = notNull(sortOrder);
    	this.deepValidator  = notNull(deepValidator);
    }

	@Override
	protected Diff<T> nextOrNull() throws Exception {
        while (actualIt.hasNext() || expectedIt.hasNext()) {
        	if (!actualIt  .hasNext()) return report.reportMissingElement   (expectedIt.next());
        	if (!expectedIt.hasNext()) return report.reportUnexpectedElement(actualIt  .next());

        	int comparison = sortOrder.compare(actualIt.peek(), expectedIt.peek());
            if (comparison == 0) {
                T actual   = actualIt.next();
                T expected = expectedIt.next();
                if (!deepValidator.test(actual, expected, report)) return report.reportDifference(actual, expected);
                else                                               report.reportEqual();
            } else 
            	return comparison < 0 ? 
            			report.reportUnexpectedElement(actualIt  .next()) :
        				report.reportMissingElement   (expectedIt.next());
        }
        return null;
	}
}