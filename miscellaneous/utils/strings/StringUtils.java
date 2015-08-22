package miscellaneous.utils.strings;

import static java.util.stream.Collectors.joining;
import static miscellaneous.utils.check.Check.notBlank;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import miscellaneous.utils.check.Check;

public class StringUtils {
	private StringUtils() { }
	
	public static int count(String pattern, String input) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(input);
		
		int count;
		for (count = 0 ; m.find() ; count++);
		return count;
	}
	
	public static <T> String join(String first, String sep, String last, Collection<T> collection) { 
		return join(first,sep,last,Object::toString,collection);
	}
	
	public static <T> String join(String first, String sep, String last, Function<T,String> toString, Collection<T> collection) { 
		return collection.stream().map(toString).collect(joining(first,sep,last));
	}
	
	@SafeVarargs
	public static <T> String join(String sep, Function<T,String> toString, T... values) { 
		Check.notEmpty(values);
		return Stream.of(values).map(toString).collect(joining(sep));
	}
	
	@SafeVarargs
	public static <T> String join(String sep, T... values) { 
		return join(sep,T::toString,values);
	}
	
	public static <T> String join(String sep, Function<T,String> toString, Collection<T> collection) {
		return collection.stream().map(toString).collect(joining(sep));
	}
	
	public static <T> String join(String sep, Collection<T> collection) {
		return collection.stream().map(T::toString).collect(Collectors.joining(sep));
	}
	
	public static String blank(int length) { return repeat(length,' '); }

	public static String repeat(int length, char  ch) { return repeatInternal(length,sb -> sb.append(ch)); }
	public static String repeat(int length, String s) { return repeatInternal(length,sb -> sb.append(s )); }
	
	private static String repeatInternal(int length, Consumer<StringBuilder> update) {
		StringBuilder sb = new StringBuilder(length);
		for (int i=0 ; i<length ; i++) update.accept(sb);
		return sb.toString();
	}
	
	public static String fillWithBlanks(String toFill, int length) { return fillToLength(toFill,' ',length); }

	public static String fillToLength(String toFill, char filler, int length) {
		Check.isGreaterOrEqual(length,toFill.length());
		return toFill + repeat(length - toFill.length(),filler);
	}
	
	public static char lastChar(String s) { return notBlank(s).charAt(s.length() - 1); }
}
