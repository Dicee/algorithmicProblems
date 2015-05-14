package miscellaneous;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {
	private CollectionUtils() { }

	public static <K,V,N> Map<K,N> mapValues(Map<K,V> map, Function<V,N> mapper) {
		return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,mapper.compose(Map.Entry::getValue)));
	}
	
	public static <K,V,N> Map<N,V> mapKeys(Map<K,V> map, Function<K,N> mapper) {
		return map.entrySet().stream().collect(Collectors.toMap(mapper.compose(Map.Entry::getKey),Map.Entry::getValue));
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
}
