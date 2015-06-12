package miscellaneous.utils.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class HashCounter<T> implements Iterable<Map.Entry<T,Integer>> {
	private final Map<T, Integer> map = new HashMap<>();
	
	public HashCounter<T> add(T t) {
		map.putIfAbsent(t,0);
		map.compute(t,(k,v) -> v + 1);
		return this;
	}
	
	@Override
	public String toString() {
		return map.toString();
	}

	@Override
	public Iterator<Map.Entry<T,Integer>> iterator() {
		return map.entrySet().iterator();
	}
	
	public Stream<Map.Entry<T,Integer>> stream() {
		return StreamSupport.stream(spliterator(),false);
	}
}
