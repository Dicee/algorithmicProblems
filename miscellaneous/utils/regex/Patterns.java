package miscellaneous.utils.regex;

public final class Patterns {
	public static final RichPattern	BLANKS	= RichPattern.compile("\\s+");
	public static final RichPattern	EMAIL	= RichPattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
	public static final RichPattern	URL		= RichPattern.compile("((https?:\\/\\/|www\\.)([\\w\\._-]+)\\.([a-z\\.]{2,6})|localhost)(:\\d+)?([\\/\\w\\-]*)*(\\?(([\\w-_]+=[\\w-_]*&)*[\\w-_]+=[\\w-_]*)?|\\/?)");

	private Patterns() { }
}	
