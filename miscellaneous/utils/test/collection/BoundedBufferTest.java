package miscellaneous.utils.test.collection;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import miscellaneous.utils.collection.ArrayUtils;
import miscellaneous.utils.collection.BoundedBuffer;
import miscellaneous.utils.collection.BoundedBuffer.SizeExceededException;
import miscellaneous.utils.collection.BoundedBuffer.SizeExceededPolicy;

import org.junit.Before;
import org.junit.Test;

public class BoundedBufferTest {
	private static final int MAX_SIZE = 3;
	private BoundedBuffer<Integer>	buffer;
	
	@Before
	public void setUp() {
		this.buffer = new BoundedBuffer<>(MAX_SIZE, SizeExceededPolicy.ERROR);
		this.buffer.push(3);
		this.buffer.push(6);
	}
	
	@Test
	public void behavesLikeANormalDeque_1() {
		buffer.addLast(7);
		assertThat(buffer.peekLast(), equalTo(7));
		assertThat(buffer.pop(), equalTo(6));
		assertThat(buffer.pollLast(), equalTo(7));
		assertThat(buffer.pollFirst(), equalTo(3));
		assertThat(buffer.pollFirst(), equalTo(null));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void behavesLikeANormalDeque_2() {
		buffer.pop();
		buffer.pop();
		buffer.removeLast();
	}
	
	@Test(expected = BoundedBuffer.SizeExceededException.class)
	public void isBounded() {
		buffer.add(5);
		buffer.add(5);
	}
	
	@Test(expected = SizeExceededException.class)
	public void constructorFromCollectionIsBounded() {
		new BoundedBuffer<>(MAX_SIZE, asList(ArrayUtils.ofDim(Integer.class, MAX_SIZE + 3)), SizeExceededPolicy.ERROR);
	}
	
	@Test
	public void constructorFromCollectionIgnoresSizeExceededIfPolicySet() {
		BoundedBuffer<Integer> buffer = new BoundedBuffer<>(MAX_SIZE, asList(ArrayUtils.ofDim(Integer.class, MAX_SIZE + 3)), SizeExceededPolicy.IGNORE);
		assertThat(buffer.isFull(), is(true));
	}
	
	@Test
	public void ignoresSizeExceededIfPolicySet() {
		BoundedBuffer<Integer> buffer = new BoundedBuffer<>(1, SizeExceededPolicy.IGNORE);
		buffer.add(1);
		buffer.add(2);
		assertThat(buffer.isFull(), is(true));
	}
}
