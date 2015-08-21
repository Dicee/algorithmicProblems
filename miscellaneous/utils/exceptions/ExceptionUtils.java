package miscellaneous.utils.exceptions;

import java.io.Closeable;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.google.common.base.Throwables;

public class ExceptionUtils {
	private ExceptionUtils() { }
	
	public static interface ThrowingSupplier<OUTPUT> {
		public OUTPUT get() throws Exception;
	}
	
	public static interface ThrowingFunction<INPUT,OUTPUT> {
		public OUTPUT apply(INPUT input) throws Exception;
		
		public static <X> ThrowingFunction<X,X> identity() { return x -> x; }
	}
	
	public static interface ThrowingBiFunction<INPUT1,INPUT2,OUTPUT> {
		public OUTPUT apply(INPUT1 input1, INPUT2 input2) throws Exception;
	}
	
	public static interface ThrowingBinaryOperator<X> extends ThrowingBiFunction<X,X,X> { }
	
	public static interface ThrowingUnaryOperator<X> extends ThrowingFunction<X,X> { }

	public static interface ThrowingConsumer<INPUT> {
		public void accept(INPUT input) throws Exception;
	}
	
	public static interface ThrowingRunnable {
		public void run() throws Exception;
	}
	
	public static interface ThrowingPredicate<INPUT> {
		public boolean test(INPUT input) throws Exception;
		public default ThrowingPredicate<INPUT> negate() { return input -> !test(input); }
	}
	
	public static <RESOURCE extends Closeable,OUTPUT> OUTPUT withCloseableResource(ThrowingSupplier<RESOURCE> resourceSupplier, //
			ThrowingFunction<RESOURCE,OUTPUT> function) {
		try (RESOURCE resource = resourceSupplier.get()) {
			return function.apply(resource);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	public static <RESOURCE extends AutoCloseable,OUTPUT> OUTPUT withAutoCloseableResource(ThrowingSupplier<RESOURCE> resourceSupplier, //
			ThrowingFunction<RESOURCE,OUTPUT> function) {
		try (RESOURCE resource = resourceSupplier.get()) {
			return function.apply(resource);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	public static <INPUT> Consumer<INPUT> uncheckedConsumer(ThrowingConsumer<INPUT> consumer) {
		return input -> {
			try {
				consumer.accept(input);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
	
	public static <OUTPUT> Supplier<OUTPUT> uncheckedSupplier(ThrowingSupplier<OUTPUT> supplier) {
		return () -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
	
	public static <INPUT,OUTPUT> Function<INPUT,OUTPUT> uncheckedFunction(ThrowingFunction<INPUT,OUTPUT> function) {
		return input -> {
			try {
				return function.apply(input);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
	
	public static <OUTPUT> OUTPUT uncheckExceptionsAndGet(ThrowingSupplier<OUTPUT> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	public static void uncheckExceptions(ThrowingRunnable runnable) {
		try {
			runnable.run();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	public static Runnable uncheckedRunnable(ThrowingRunnable runnable) {
		return () -> {
			try {
				runnable.run();
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}

	public static <INPUT1,INPUT2,OUTPUT> BiFunction<INPUT1,INPUT2,OUTPUT> uncheckedBiFunction(ThrowingBiFunction<INPUT1,INPUT2,OUTPUT> biFunction) {
		return (a,b) -> {
			try {
				return biFunction.apply(a,b);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
	
	public static <X> BinaryOperator<X> uncheckedBinaryOperator(ThrowingBinaryOperator<X> binaryOp) {
		return (a,b) -> {
			try {
				return binaryOp.apply(a,b);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
	
	public static <X> UnaryOperator<X> uncheckedUnaryOperator(ThrowingUnaryOperator<X> unaryOp) {
		return a -> {
			try {
				return unaryOp.apply(a);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
}