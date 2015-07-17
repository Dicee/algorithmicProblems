package miscellaneous.utils.collection.richIterator;

import miscellaneous.utils.check.Check;

public abstract class RichIntIterator extends RichIterator<Integer> {
	private static RichIntIterator wrap(RichIterator<Integer> it) {
		return new RichIntIterator() {
			@Override protected Integer nextInternal   () throws Exception { return it.nextInternal   (); }
			@Override protected boolean hasNextInternal() throws Exception { return it.hasNextInternal(); }
		};
	}
	
	public static RichIntIterator counter() { return counter(0); }
	public static RichIntIterator counter(int init) {
		return new RichIntIterator() {
			private Integer count = init;
			
			@Override
			protected Integer nextInternal() throws Exception {
				if (count.equals(Integer.MAX_VALUE)) throw new IllegalStateException("Integer capacity exceeded");
				return count++;
			}
			
			@Override
			protected boolean hasNextInternal() throws Exception { return !count.equals(Integer.MAX_VALUE); }
		};
	}
	
	public static RichIntIterator range(int from, int to) {
		Check.isGreaterThan(to,from,"Empty range");
		return wrap(counter(from).takeWhile(i -> i < to));
	}

	public static RichIntIterator closedRange(int from, int until) {
		Check.isGreaterOrEqual(until,from);
		return wrap(counter(from).takeUntil(i -> i == until));
	}
}
