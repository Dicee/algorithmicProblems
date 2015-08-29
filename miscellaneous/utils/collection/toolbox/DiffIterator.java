package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;
import static miscellaneous.utils.collection.BoundedBuffer.SizeExceededPolicy.ERROR;

import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;

import miscellaneous.utils.collection.BoundedBuffer;
import miscellaneous.utils.collection.richIterator.FromResourceRichIterator;
import miscellaneous.utils.collection.richIterator.LookAheadRichIterator;
import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;
import miscellaneous.utils.collection.toolbox.CompareSortedIterators.DeepValidator;

class DiffIterator<T> extends LookAheadRichIterator<Diff<T>> {
    DiffIterator(Iterator<T> actualIt, Iterator<T> expectedIt, DiffReport<T> report, Comparator<T> sortOrder, DeepValidator<T> deepValidator) {
        super(new DiffInternalIterator<>(actualIt, expectedIt, report, sortOrder, deepValidator));
    }

    private static class DiffInternalIterator<T> extends FromResourceRichIterator<Diff<T>> {
    	private final RichIterator <T>	actualIt;
    	private final RichIterator <T>	expectedIt;
    	private final DiffReport   <T>	report;
    	private final Comparator   <T>	sortOrder;
    	private final DeepValidator<T>	deepValidator;
    	private final Deque        <T>	actualBuffer;
    	private final Deque        <T>	expectedBuffer;
    	
    	public DiffInternalIterator(Iterator<T> actualIt, Iterator<T> expectedIt, DiffReport<T> report, Comparator<T> sortOrder, DeepValidator<T> deepValidator) {
        	this.actualIt       = RichIterators.wrap(notNull(actualIt));
            this.expectedIt     = RichIterators.wrap(notNull(expectedIt));
            this.report         = notNull(report);
            this.sortOrder      = notNull(sortOrder);
            this.deepValidator  = notNull(deepValidator);
            this.actualBuffer   = new BoundedBuffer<>(1, ERROR);
            this.expectedBuffer = new BoundedBuffer<>(1, ERROR);
        }
    	
        private boolean hasNextDiff() {
            boolean hasNextActual   = fillIfEmpty(actualIt  , actualBuffer  );
            boolean hasNextExpected = fillIfEmpty(expectedIt, expectedBuffer);
            return hasNextActual || hasNextExpected;
        }

        @Override
        public Diff<T> readNext() {
            while (hasNextDiff()) {
            	if (actualBuffer  .isEmpty()) return report.reportMissingElement   (expectedBuffer.pop());
            	if (expectedBuffer.isEmpty()) return report.reportUnexpectedElement(actualBuffer  .pop());

            	int comparison = sortOrder.compare(actualBuffer.peek(), expectedBuffer.peek());
                if (comparison == 0) {
                    T actual = actualBuffer.pop();
                    T expected = expectedBuffer.pop();
                    if (!deepValidator.test(actual, expected, report)) return report.reportDifference(actual, expected);
                    else                                               report.reportEqual();
                } else 
                	return comparison < 0 ? 
                			report.reportUnexpectedElement(actualBuffer  .pop()) :
                    		report.reportMissingElement   (expectedBuffer.pop());
            }
            return null;
        }

        private boolean fillIfEmpty(Iterator<T> actualIt, Deque<T> actualBuffer) {
            boolean hasNext = true;
            if (actualBuffer.isEmpty()) {
                if (actualIt.hasNext()) {
                    actualBuffer.push(actualIt.next());
                } else
                    hasNext = false;
            }
            return hasNext;
        }
    }
}