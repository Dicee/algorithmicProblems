package miscellaneous.utils.collection;

import static miscellaneous.utils.strings.StringUtils.fillWithBlanks;

import java.lang.reflect.Array;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import miscellaneous.utils.check.Check;
import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;

public class ArrayUtils {
	private ArrayUtils() { }
	
	@SafeVarargs
	public static <T> T[] of(T... ts) {
		Check.notNull(ts);
		return ts;
	}
	
	public static <T> T[] ofDim(Class<T> clazz, int length) { return ofDim(clazz,length,null); }
	
	@SuppressWarnings("unchecked")
	public static <T> T[] ofDim(Class<T> clazz, int length, T defaultValue) {
		T[] arr = (T[]) Array.newInstance(clazz,length);
		for (int i=0 ; i<length ; i++) arr[i] = defaultValue;
		return arr;
	}
	
	public static <T> T[] reversed(T[] arr) {
		reverse(arr,0,arr.length);
		return arr;
	}
	
	public static <T> void reverse(T[] arr, int min, int max) {
		while (min < max) swap(arr,min++,max--);
	}
	
	public static <T> void swap(T[] arr, int i, int j) {
		T tmp  = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static void reverse(int[] arr, int min, int max) {
		while (min < max) swap(arr,min++,max--);
	}
	
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i]  = arr[j];
		arr[j]  = tmp;
	}
	
	public static <T> Optional<T> findFirst(T[] arr, Predicate<T> predicate) {
		return Stream.of(arr).filter(predicate).findFirst();
	}
	
	public static <T> T findAnyRequired(T[] arr, Predicate<T> predicate) {
		return Stream.of(arr).filter(predicate).findAny().orElseThrow(NoSuchElementException::new);
	}
	
	public static Integer[][] toWrapperTypeArray(int[][] intArr) {
		Integer[][] res = new Integer[intArr.length][intArr[0].length];
		for (int i=0 ; i<res.length ; i++)
			for (int j=0 ; j<res[0].length ; j++)
				res[i][j] = intArr[i][j];
		return res;
	}
	
	public static <T> String toPrettyString(T[][] arr) {
		long distinctColsLength = RichIterators.from2DArray(arr).map(RichIterator::count).distinct().count();
		Check.areEqual(1,distinctColsLength);

		List<String>  strings  = RichIterators.from2DArray(arr).flatMap(col -> (Iterable<String>) col.map(String::valueOf)).toList();
		int           maxWidth = 1 + RichIterators.fromCollection(strings).fold(0,(str,len) -> Math.max(str.length(),len));
		return RichIterators
			.fromCollection(strings) 
			.map(s -> fillWithBlanks(s,maxWidth))
			.grouped(arr[0].length)
			.map(RichIterator::mkString)
			.mkString("","\n","");
	}
}
