package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.check.Check.notNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.util.Pair;
import miscellaneous.utils.exceptions.ExceptionUtils.ThrowingFunction;

public class PairRichIterator<K,V> extends RichIterator<Pair<K,V>> {
	private final RichIterator<Pair<K,V>> it;

	static <X,K,V> PairRichIterator<K,V> create(RichIterator<X> it, ThrowingFunction<X,K> keyFunction, ThrowingFunction<X,V> valueFunction) {
		notNull(keyFunction);
		notNull(valueFunction);
		return new PairRichIterator<>(it.map(x -> new Pair<>(keyFunction.apply(x),valueFunction.apply(x))));
	}
	
	PairRichIterator(RichIterator<Pair<K,V>> it) { this.it = notNull(it); }

	@Override
	protected boolean hasNextInternal() throws Exception { return it.hasNext(); }

	@Override
	protected Pair<K,V> nextInternal() throws Exception { return it.next(); }
	
	public <V1> PairRichIterator<K,V1> mapValues(ThrowingFunction<V,V1> mapper) {
		return mapToPair(Pair::getKey,pair -> mapper.apply(pair.getValue())); 
	}
	
	public <K1> PairRichIterator<K1,V> mapKeys(ThrowingFunction<K,K1> mapper) {
		return mapToPair(pair -> mapper.apply(pair.getKey()),Pair::getValue); 
	}
	
	public PairRichIterator<K,List<V>> groupByKey() {
		Map<K,List<V>> map = new HashMap<>();
		while (it.hasNext()) {
			Pair<K,V> pair = it.next();
			map.putIfAbsent(pair.getKey(),new LinkedList<>());
			map.get(pair.getKey()).add(pair.getValue());
		}
		return new PairRichIterator<>(RichIterators.wrap(map.entrySet().stream().map(entry ->  new Pair<>(entry.getKey(),entry.getValue())).iterator()));
	}
	
	public Map<K,V> toMap() { return stream().collect(Collectors.toMap(Pair::getKey,Pair::getValue)); }
}