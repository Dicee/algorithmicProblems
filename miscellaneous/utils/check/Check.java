package miscellaneous.utils.check;

public final class Check {
	private static final String SHOULD_NOT_BE_NULL = "This variable should not be null";
	private static final String SHOULD_BE_NULL = "This variable should be null";
	
	private Check() { }
	
	public static <T> boolean isNull(T t) {
		return isNull(SHOULD_BE_NULL);
	}
	
	public static <T> boolean isNull(T t, String msg) {
		if (t != null) throw new CheckException(msg);
		return true;
	}
	
	public static <T> T notNull(T t) {
		return notNull(t,SHOULD_NOT_BE_NULL);
	}
	
	public static <T> T notNull(T t, String msg) {
		if (t == null) throw new CheckException(msg);
		return t;
	}
	
	public static class CheckException extends RuntimeException {
		private static final long	serialVersionUID	= 1L;
		public CheckException(String msg) { super(msg); }
		public CheckException() { super(); }
	}
}
