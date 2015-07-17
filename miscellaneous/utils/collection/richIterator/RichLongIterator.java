package miscellaneous.utils.collection.richIterator;

import miscellaneous.utils.check.Check;

public abstract class RichLongIterator extends RichIterator<Long> {
	private static RichLongIterator wrap(RichIterator<Long> it) {
		return new RichLongIterator() {
			@Override protected Long    nextInternal   () throws Exception { return it.nextInternal   (); }
			@Override protected boolean hasNextInternal() throws Exception { return it.hasNextInternal(); }
		};
	}
	
	public static RichLongIterator counter() { return counter(0); }
	public static RichLongIterator counter(long init) {
		return new RichLongIterator() {
			private Long count = init;
			
			@Override
			protected Long nextInternal() throws Exception {
				if (count.equals(Long.MAX_VALUE)) throw new IllegalStateException("Long capacity exceeded");
				return count++;
			}
			
			@Override
			protected boolean hasNextInternal() throws Exception { return !count.equals(Long.MAX_VALUE); }
		};
	}
	
	public static RichLongIterator range(long from, long to) {
		Check.isGreaterThan(to,from,"Empty range");
		return wrap(counter(from).takeWhile(i -> i < to));
	}

	public static RichLongIterator closedRange(long from, long until) {
		Check.isGreaterOrEqual(until,from);
		return wrap(counter(from).takeUntil(i -> i == until));
	}
}
