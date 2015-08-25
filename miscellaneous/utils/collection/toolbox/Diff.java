package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.check.Check.notNull;

import java.util.Objects;
import java.util.Optional;

import javafx.util.Pair;

public abstract class Diff<T> {
	public static <T> Diff<T> diff      (T actual, T expected) { return new BasicDiff<T>            (actual, expected); }
	public static <T> Diff<T> missing   (T expected)           { return new MissingElementDiff<T>   (expected)        ; }
	public static <T> Diff<T> unexpected(T actual)             { return new UnexpectedElementDiff<T>(actual)          ; }
	
	public abstract Pair<Optional<T>, Optional<T>> showDiff();
	
	private static class BasicDiff<T> extends Diff<T> {
		private final T actual, expected;

		public BasicDiff(T actual, T expected) {
			this.actual   = notNull(actual);
			this.expected = notNull(expected);
		}
		
		@Override
		public Pair<Optional<T>, Optional<T>> showDiff() { return new Pair<>(Optional.of(actual), Optional.of(expected)); }

		@Override
		public int hashCode() { return Objects.hash(actual, expected); }

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			BasicDiff<?> that = (BasicDiff<?>) o;
			return Objects.equals(actual, that.actual) && Objects.equals(expected, that.expected);
		}

		@Override
		public String toString() {
			return "BasicDiff [" + actual + ", " + expected + "]";
		}
	}
	
	private static class MissingElementDiff<T> extends Diff<T> {
		private final T missing;
		public MissingElementDiff(T missing) { this.missing = notNull(missing); }
		
		@Override
		public Pair<Optional<T>, Optional<T>> showDiff() { return new Pair<>(Optional.empty(), Optional.of(missing)); }
		
		@Override
		public int hashCode() { return missing.hashCode(); }

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			MissingElementDiff<?> that = (MissingElementDiff<?>) o;
			return Objects.equals(missing, that.missing);
		}
		
		@Override
		public String toString() {
			return "MissingElementtDiff [" + missing + "]";
		}
	}
	
	private static class UnexpectedElementDiff<T> extends Diff<T> {
		private final T unexpected;
		public UnexpectedElementDiff(T unexpected) { this.unexpected = notNull(unexpected); }
		
		@Override
		public Pair<Optional<T>, Optional<T>> showDiff() { return new Pair<>(Optional.of(unexpected), Optional.empty()); }
		
		@Override
		public int hashCode() { return unexpected.hashCode(); }

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			UnexpectedElementDiff<?> that = (UnexpectedElementDiff<?>) o;
			return Objects.equals(unexpected, that.unexpected);
		}
		
		@Override
		public String toString() {
			return "UnexpectedElementDiff [" + unexpected + "]";
		}
	}
}
