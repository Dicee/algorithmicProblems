package miscellaneous.utils.collection;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import miscellaneous.utils.check.Check;

public class ArrayUtils {
	private ArrayUtils() { }
	
	public static <T> T[] ofDim(Class<T> clazz, int length) {
		return ofDim(clazz,length,null);
	}
	
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
		int tmp  = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static <T> Optional<T> findFirst(T[] arr, Predicate<T> predicate) {
		return Stream.of(arr).filter(predicate).findFirst();
	}
	
	public static <T> T findAnyRequired(T[] arr, Predicate<T> predicate) {
		return Stream.of(arr).filter(predicate).findAny().orElseThrow(NoSuchElementException::new);
	}
}
