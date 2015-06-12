package miscellaneous.utils.regex;

import java.util.regex.Pattern;

public class RichPattern {
	public static RichPattern compile(String regex) { return new RichPattern(regex); }

	public final Pattern pattern;
	RichPattern(String regex) { this.pattern = Pattern.compile(regex); }
	
	public RichPattern or(RichPattern that) { return or(that.pattern)                                                       ; }
	public RichPattern or(Pattern p)        { return new RichPattern(String.format("(%s|%s)",pattern.pattern(),p.pattern())); }

	public String[] split  (String s)         { return pattern.split(s)              ; }
	public String   regex  ()                 { return pattern.pattern()             ; }
	public boolean  matches(CharSequence seq) { return pattern.matcher(seq).matches(); }
}
