package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import miscellaneous.utils.collection.richIterator.IteratorTransformation;
import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;

public class CompareSortedIterators<T> {
    @FunctionalInterface
    public static interface DeepValidator<T> {
        public static <T> DeepValidator<T> noDeepValidation() { return (x, y, report) -> false; }
        boolean test(T actual, T expected, DiffReport<T> report);
    }

    private final Comparator<T> sortOrder;
    private final IteratorTransformation<T, T> applyStrictOrderWithinGroup;
    private final DeepValidator<T> deepValidator;

    public CompareSortedIterators(Comparator<T> sortOrder, DeepValidator<T> deepValidator) {
        this(sortOrder, IteratorTransformation.identity(), deepValidator);
    }

    public CompareSortedIterators(Comparator<T> sortOrder, IteratorTransformation<T, T> applyStrictOrderWithinGroup, DeepValidator<T> deepValidator) {
        this.sortOrder                   = notNull(sortOrder);
        this.applyStrictOrderWithinGroup = notNull(applyStrictOrderWithinGroup);
        this.deepValidator               = notNull(deepValidator);
    }

    public final List<Diff<T>> compareFully(Iterator<T> actual, Iterator<T> expected, DiffReport<T> report) {
        RichIterator<Diff<T>> diffStream = compareIterators(prepare(actual), prepare(expected), report);
        return diffStream.toList();
    }

    private RichIterator<T> prepare(Iterator<T> it) {
        return RichIterators.wrap(it).grouped(sortOrder).mapGroups(applyStrictOrderWithinGroup).flatten();
    }

    public RichIterator<Diff<T>> compareIterators(Iterator<T> actualIt, Iterator<T> expectedIt, DiffReport<T> report) {
        return new DiffIterator<>(actualIt, expectedIt, report, sortOrder, (DeepValidator<T>) this::checkValidity);
    }
    
    private boolean checkValidity(T actual, T expected, DiffReport<T> report) {
        return actual.equals(expected) || deepValidator.test(actual, expected, report);
    }
}