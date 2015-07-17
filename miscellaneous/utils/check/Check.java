package miscellaneous.utils.check;

public final class Check {
	private static final String	SHOULD_NOT_BE_NULL			= "This variable should not be null";
	private static final String	SHOULD_BE_NULL				= "This variable should be null";
	private static final String	SHOULD_BE_EQUAL				= "These objects should be equal";
	private static final String	SHOULD_NOT_BE_EQUAL			= "These objects should not be equal";
	private static final String	SHOULD_NOT_BE_EMPTY			= "This array should not be empty";
	private static final String	SHOULD_NOT_BE_BLANK			= "This string should not be blank";
	private static final String	SHOULD_BE_GREATER			= "The first parameter should be greater than the second one";
	private static final String	SHOULD_BE_GREATER_OR_EQUAL	= "The first parameter should be greater (or equal) than the second one";
	private static final String SHOULD_BE_POSITIVE			= "This variable should be positive";
	
	private Check() { }
	
	public static <T> void notEmpty(T[] arr) { notEmpty(arr,SHOULD_NOT_BE_EMPTY); }
	public static <T> void notEmpty(T[] arr, String msg) { check(arr.length != 0,msg); }
	
	public static <T> void isNull(T t) { isNull(SHOULD_BE_NULL); }
	public static <T> void isNull(T t, String msg) { check(t == null,msg); }
	
	public static <T> T notNull(T t) { return notNull(t,SHOULD_NOT_BE_NULL); }
	public static <T> T notNull(T t, String msg) {
		check(t != null,msg);
		return t;
	}
	
	public static int isPositive(int n) { check(n >= 0,SHOULD_BE_POSITIVE); return n; } 
	public static int isPositive(int n, String msg) { check(n >= 0,msg); return n; } 
	
	public static void isGreaterThan(long a, long b) { isGreaterThan(a,b,SHOULD_BE_GREATER); }
	public static void isGreaterThan(long a, long b, String msg) { check(a > b,msg); }

	public static void isGreaterOrEqual(long a, long b) { isGreaterOrEqual(a,b,SHOULD_BE_GREATER_OR_EQUAL); }
	public static void isGreaterOrEqual(long a, long b, String msg) { check(a >= b,msg); }
	public static void isGreaterOrEqual(int a, int b) { isGreaterOrEqual(a,b,SHOULD_BE_GREATER_OR_EQUAL); }
	public static void isGreaterOrEqual(int a, int b, String msg) { check(a >= b,msg); }
	
	public static void areEqual(Object o1, Object o2) { areEqual(o1,o2,SHOULD_BE_EQUAL); }
	public static void areEqual(Object o1, Object o2, String msg) { check(o1.equals(o2),msg); }
	
	public static void notEqual(Object o1, Object o2) { notEqual(o1,o2,SHOULD_NOT_BE_EQUAL); }
	public static void notEqual(Object o1, Object o2, String msg) { check(!o1.equals(o2),msg); }
 	
	public static String notBlank(String s) { return notBlank(s,SHOULD_NOT_BE_BLANK); }
	public static String notBlank(String s, String msg) { 
		check(s != null && !s.isEmpty(),msg);
		return s;
	}

	private static void check(boolean test, String msg) {
		if (!test) throw new CheckException(msg);
	}
	
	public static class CheckException extends RuntimeException {
		private static final long	serialVersionUID = 1L;
		public CheckException(String msg) { super(msg); }
		public CheckException() { super(); }
	}
}
