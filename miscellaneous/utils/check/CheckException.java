package miscellaneous.utils.check;

public class CheckException extends RuntimeException {
	private static final long	serialVersionUID	= 1L;
	public CheckException(String msg) { super(msg); }
	public CheckException() { super(); }
}
