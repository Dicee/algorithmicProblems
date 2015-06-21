package miscellaneous.utils.exceptions;

import java.io.Closeable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Throwables;

public class IgnoreCheckedExceptions {
	private IgnoreCheckedExceptions() { }
	
	public static interface ThrowingSupplier<OUTPUT> {
		public OUTPUT get() throws Exception;
	}
	
	public static interface ThrowingFunction<INPUT,OUTPUT> {
		public OUTPUT apply(INPUT input) throws Exception;
	}
	
	public static interface ThrowingBiFunction<INPUT1,INPUT2,OUTPUT> {
		public OUTPUT apply(INPUT1 input1, INPUT2 input2) throws Exception;
	}

	public static interface ThrowingConsumer<INPUT> {
		public void accept(INPUT input) throws Exception;
	}
	
	public static interface ThrowingRunnable {
		public void run() throws Exception;
	}
	
	public static <INPUT> Consumer<INPUT> ignoreCheckedExceptionConsumer(ThrowingConsumer<INPUT> consumer) {
		return input -> {
			try {
				consumer.accept(input);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
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
	
	public static <OUTPUT> Supplier<OUTPUT> ignoreCheckedExceptionsSupplier(ThrowingSupplier<OUTPUT> supplier) {
		return () -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
	
	public static <INPUT,OUTPUT> Function<INPUT,OUTPUT> ignoreCheckedExceptionsFunction(ThrowingFunction<INPUT,OUTPUT> function) {
		return input -> {
			try {
				return function.apply(input);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
	
	public static <OUTPUT> OUTPUT ignoreCheckedExceptions(ThrowingSupplier<OUTPUT> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	public static void ignoreCheckedExceptions(ThrowingRunnable runnable) {
		try {
			runnable.run();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	public static Runnable ignoreCheckedExceptionsRunnable(ThrowingRunnable runnable) {
		return () -> {
			try {
				runnable.run();
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}

	public static <INPUT1,INPUT2,OUTPUT> BiFunction<INPUT1,INPUT2,OUTPUT> ignoreCheckedExceptionsBiFunction(ThrowingBiFunction<INPUT1,INPUT2,OUTPUT> biFunction) {
		return (a,b) -> {
			try {
				return biFunction.apply(a,b);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		};
	}
}
