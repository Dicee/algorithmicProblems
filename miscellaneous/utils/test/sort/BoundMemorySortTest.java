package miscellaneous.utils.test.sort;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import miscellaneous.utils.sort.BoundMemorySort;

import org.junit.Test;

public class BoundMemorySortTest {
	@Test
	public void test() {
		BoundMemorySort<Integer> sorter = new BoundMemorySort<>(Integer.class,3);
		
		List<Integer> list = Arrays.asList(3,69,6,4,7,88,5,1,4,6,9);
		List<Integer> res = sorter.sort(list.iterator()).stream().collect(Collectors.toList());
		Collections.sort(list);
		assertThat(res,equalTo(list));
	}
}
