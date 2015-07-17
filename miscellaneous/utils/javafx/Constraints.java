package miscellaneous.utils.javafx;

import javafx.beans.property.StringProperty;

public class Constraints {
	private static final double eps = 0.01d;
	
	public static class UpperBound extends Constraint<Double> {
		private final double max;
		
		public UpperBound(StringProperty sp, double max) {
			super(sp);
			this.max    = max;
		}
		
		@Override
		public boolean test(Double d) {
			return d <= max + eps;
		}
	}
	
	public static class LowerBound extends Constraint<Double> {
		private final double min;
		
		public LowerBound(StringProperty sp, double min) {
			super(sp);
			this.min = min;
		}
		
		@Override
		public boolean test(Double d) {
			return d >= min - eps;
		}
	}
	
	public static class Boundaries extends Constraint<Double> {
		private final double min, max;
		
		public Boundaries(StringProperty sp, double min, double max) {
			super(sp);
			this.min = min;
			this.max = max;
		}
		
		@Override
		public boolean test(Double d) {
			return d >= min - eps && d <= max + eps;
		}
	}
}
