package miscellaneous.utils.test.collection.richIterator;

import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingFunction.identity;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;

import org.junit.Before;
import org.junit.Test;

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
}
