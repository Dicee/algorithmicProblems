package miscellaneous.utils.collection;

import static miscellaneous.utils.check.Check.notNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import miscellaneous.utils.check.Check;

public class CollectionUtils {
	private CollectionUtils() { }

	public static <K,V,N> Map<K,N> mapValues(Map<K,V> map, Function<V,N> mapper) {
		return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,mapper.compose(Map.Entry::getValue)));
	}
	
	public static <K,V,N> Map<N,V> mapKeys(Map<K,V> map, Function<K,N> mapper) {
		return map.entrySet().stream().collect(Collectors.toMap(mapper.compose(Map.Entry::getKey),Map.Entry::getValue));
	}
	
	public static <T> List<T> reverse(List<T> list) {
		Collections.reverse(list);
		return list;
	}
	
	@SafeVarargs
	public static <T> Set<T> setOf(T... elts) { 
		Check.notNull(elts,"This array should contain at least one element");
		HashSet<T> res = new HashSet<>(elts.length);
		for (T elt : elts) res.add(elt);
		return Collections.unmodifiableSet(res);
	}

	@SafeVarargs
	public static <T> List<T> listOf(T... elts) {
		notNull(elts,"This array should contain at least one element");
		return Collections.unmodifiableList(Arrays.asList(elts));
	}

	public static <T> Collector<T, List<T>, List<T>> collectList() { return Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList); }
}
