package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;
import static miscellaneous.utils.collection.richIterator.RichIterators.fromCollection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;

import miscellaneous.utils.collection.BoundedBuffer;
import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;

public abstract class CompareSortedIterators<X> {
	private Comparator<X> cmp;

	public CompareSortedIterators(Comparator<X> cmp) { this.cmp = notNull(cmp); }
	
	protected RichIterator<X> applyStrictOrderWithinGroup(RichIterator<X> group) {
		// default implementation does nothing. Override to change this behaviour.
		return group;
	}
	
	private final boolean checkValidity(X actual, X expected, DiffReport<X> report) { 
		if (actual.equals(expected)) return true;
		return deepCheckValidity(actual, expected, report); 
	}
	
	// default implementation always returns false. Override to change this behaviour.
	protected boolean deepCheckValidity(X actual, X expected, DiffReport<X> report) { return false; }
	
	public final DiffReport<X> compare(Iterator<X> actual, Iterator<X> expected) { 
		return compare(RichIterators.wrap(actual), RichIterators.wrap(expected));
	}

	public final DiffReport<X> compare(RichIterator<X> actual, RichIterator<X> expected) { 
		return compareIterators(prepare(actual), prepare(expected));
	}
	
	private RichIterator<X> prepare(RichIterator<X> it) {
		return it.grouped(cmp).mapGroups(this::applyStrictOrderWithinGroup).flatten();
	}
	
	private DiffReport<X> compareIterators(RichIterator<X> actualIt, RichIterator<X> expectedIt) {
		final BoundedBuffer<X> actualBuffer = new BoundedBuffer<>(1), expectedBuffer = new BoundedBuffer<>(1);
		
		DiffReport<X> report = new DiffReport<>();
		while ((!actualBuffer  .isEmpty() || actualIt  .hasNext()) && 
			   (!expectedBuffer.isEmpty() || expectedIt.hasNext())){
			
			if (actualBuffer  .isEmpty()) actualBuffer  .push(actualIt  .next());
			if (expectedBuffer.isEmpty()) expectedBuffer.push(expectedIt.next());
			
			int comparison = cmp.compare(actualBuffer  .peek(), expectedBuffer.peek());
			if (comparison == 0) {
				X actual   = actualBuffer  .pop();
				X expected = expectedBuffer.pop(); 
				if (!checkValidity(actual, expected, report)) report.reportDifference(actual, expected);
				else                                          report.reportNewRecord();
			} else if (comparison < 0) {
				report.reportUnexpectedElement(actualBuffer.pop()); 
			} else {
				report.reportMissingElement(expectedBuffer.pop()); 
			}
		}
		
		consumeIterator(fromCollection(actualBuffer  ).concat(actualIt  ), report::reportUnexpectedElement);
		consumeIterator(fromCollection(expectedBuffer).concat(expectedIt), report::reportMissingElement   );
		return report;
	}

	private void consumeIterator(RichIterator<X> it, Consumer<X> updateReport) {
		while (it.hasNext()) updateReport.accept(it.next());
	}
}
