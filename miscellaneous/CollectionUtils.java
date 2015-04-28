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
}
