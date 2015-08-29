package miscellaneous.utils.test.collection.richIterator;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static miscellaneous.utils.exceptions.ExceptionUtils.ThrowingFunction.identity;
import static miscellaneous.utils.test.collection.richIterator.RichIteratorTestUtils.observable;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;
import miscellaneous.utils.collection.richIterator.RichIntIterator;
import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;
import miscellaneous.utils.test.collection.richIterator.RichIteratorTestUtils.ObservableRichIterator;

public class RichIteratorTest {
	private RichIterator<Integer> it;

	@Before
	public void setUp() {
		this.it = RichIterators.fromCollection(Arrays.asList(3,8,5,6,7,9,1,15));
	}
	
	@Test
	public void testMap() {
		assertThat(it.map(x -> 2*x).toList(),equalTo(Arrays.asList(6,16,10,12,14,18,2,30)));
	}
	
	@Test
	public void testFilter() {
		assertThat(it.filter(x -> x % 2 == 0 || x % 5 == 0).toList(),equalTo(Arrays.asList(8,5,6,15)));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testIterableOnce() {
		assertThat(it.filter(x -> x % 2 == 0).toList(),equalTo(Arrays.asList(8,6)));
		assertThat(it.filter(x -> x % 2 == 0 || x % 5 == 0).toList(),equalTo(Arrays.asList(8,5,6,15)));
	}
	
	@Test
	public void testChaining() {
		assertThat(it.filter(x -> x > 5).map(x -> -x).toList(),equalTo(Arrays.asList(-8,-6,-7,-9,-15)));
	}
	
	@Test
	public void testMapValue() {
		assertThat(it.filter(x -> x % 2 == 0).mapToPair(x -> "Number " + x,identity()).mapValues(x -> x - 1).toList(),
				equalTo(Arrays.asList(new Pair<>("Number 8",7),new Pair<>("Number 6",5))));
	}
	
	@Test
	public void testGroupByKey() {
		Map<String,List<Integer>> map = new HashMap<>();
		map.put("Even",Arrays.asList(8,6));
		map.put("Odd",Arrays.asList(3,5,7,9,1,15));
		assertThat(it.mapToPair(x -> x % 2 == 0 ? "Even" : "Odd",identity()).groupByKey().toMap(),equalTo(map));
	}
	
	@Test
	public void testReduce() {
		assertThat(it.filter(x -> x > 5).map(x -> -x).reduce((x,y) -> x + y).get(),equalTo(-45));
	}
	
	@Test
	public void testZip() {
		assertThat(it.zip(RichIterators.fromCollection(Arrays.asList("coucou","non"))).toList(),equalTo(Arrays.asList(new Pair<>(3,"coucou"),new Pair<>(8,"non"))));
	}
	
	@Test
	public void testTakeWhile() {
		assertThat(it.takeWhile(x -> x >= 3).toList(),equalTo(Arrays.asList(3,8,5,6,7,9)));
	}
	
	@Test
	public void testTakeUntil() {
		assertThat(it.takeUntil(x -> x == 7).toList(),equalTo(Arrays.asList(3,8,5,6,7)));
	}
	
	@Test 
	public void testDistinct() {
		assertThat(it.map(i -> i % 3).distinct().toList(),equalTo(Arrays.asList(0,2,1)));
	}
	
	@Test(expected=ClassCastException.class) 
	public void testSorted_throwsExceptionIfNotComparable() {
		RichIterators.fromCollection(Arrays.asList(1,"hey",new Object(),2)).sorted().toList();
	}
	
	@Test 
	public void testSorted_isLazy() {
		CustomComparable c0 = new CustomComparable(1);
		CustomComparable c1 = new CustomComparable(2);
		RichIterator<CustomComparable> it = RichIterators.fromCollection(Arrays.asList(c0,c1)).sorted();
		assertThat(!c0.hasBeenCompared() && !c1.hasBeenCompared(),is(true));
		it.toList();
		assertThat(c0.hasBeenCompared() && c1.hasBeenCompared(),is(true));
	}

	@Test 
	public void testSorted_sortsCorrectly() {
		assertThat(it.sorted().toList(),equalTo(Arrays.asList(1,3,5,6,7,8,9,15)));
	}
	
	@Test 
	public void testFind() {
		assertThat(it.findFirst(i -> i > 6),equalTo(Optional.of(8)));
		assertThat(it.findFirst(i -> i > 150),equalTo(Optional.empty()));
	}
	
	@Test 
	public void testFindIndex() {
		assertThat(it.indexWhere(i -> i > 6),equalTo(1));
		assertThat(it.indexWhere(i -> i > 150),equalTo(-1));
	}
	
	@Test
	public void testForall_1() {
		assertThat(it.forall(i -> i > 0),is(true));
	}
	
	@Test
	public void testForall_2() {
		assertThat(it.forall(i -> i > 2),is(false));
	}
	
	@Test
	public void testExists_1() {
		assertThat(it.exists(i -> i > 0),is(true));
	}
	
	@Test
	public void testExists_2() {
		assertThat(it.exists(i -> i < 0),is(false));
	}
	
	@Test
	public void testFlatMap() {
		assertThat(it.flatMap(i -> RichIntIterator.counter(1).takeWhile(j -> 3*j < i)).toList(),equalTo(Arrays.asList(1,2,1,1,1,2,1,2,1,2,3,4)));
	}
	
	@Test
	public void testFlatMap_handlesEmptyIterators() {
		assertThat(it.flatMap(i -> RichIterators.emptyIterator()).hasNext(),is(false));
	}
	
	private static class CustomComparable implements Comparable<CustomComparable> {
		private final AtomicBoolean	hasBeenCompared	= new AtomicBoolean(false);
		private final int			value;
		
		public CustomComparable(int value) { this.value = value; }
		
		@Override
		public int compareTo(CustomComparable that) {
			hasBeenCompared.set(true);
			that.hasBeenCompared.set(true);
			return Integer.compare(value,that.value);
		}
		
		public boolean hasBeenCompared() { return hasBeenCompared.get(); }
	}
	
	@Test
	public void testGroupedByComparator_correctness() {
		List<RichIterator<String>> iterators = RichIterators.of("a", "a", "b", "d", "e", "E").grouped(String.CASE_INSENSITIVE_ORDER).toList();
		assertThat(iterators.stream().map(RichIterator::toList).collect(toList()), 
				equalTo(asList(asList("a", "a"), asList("b"), asList("d"), asList("e", "E"))));
	}
	
	@Test
	public void testGroupedByComparator_handlesLastElement() {
		List<RichIterator<String>> iterators = RichIterators.of("a", "a", "b", "d", "e", "E", "x").grouped(String.CASE_INSENSITIVE_ORDER).toList();
		assertThat(iterators.stream().map(RichIterator::toList).collect(toList()), 
				equalTo(asList(asList("a", "a"), asList("b"), asList("d"), asList("e", "E"), asList("x"))));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testGroupedByComparator_failsIfNotSorted() {
		RichIterators.of("b", "a", "c").grouped(String.CASE_INSENSITIVE_ORDER).toList();
	}
	
	@Test
	public void testBuffering() {
		ObservableRichIterator<Integer> it         = observable(RichIterators.of(1, 2, 3, 4));
		RichIterator          <Integer> bufferedIt = it.buffered(2);
		assertThat(bufferedIt.next() , is(1));
		assertThat(it.getNextCalls() , is(2));
		                             
		assertThat(bufferedIt.next() , is(2));
		assertThat(it.getNextCalls() , is(2));
		                             
		assertThat(bufferedIt.next() , is(3));
		assertThat(it.getNextCalls() , is(4));
		                             
		assertThat(bufferedIt.next() , is(4));
		assertThat(it.getNextCalls() , is(4));
		assertThat(it.getCloseCalls(), is(1));
	}
}
