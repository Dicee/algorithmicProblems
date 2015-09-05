package miscellaneous.utils.test.math;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import miscellaneous.utils.math.Permutation;
import miscellaneous.utils.math.PermutationGenerator;

import org.junit.Before;
import org.junit.Test;

public class PermutationGeneratorTest {
	private Permutation	perm;

	@Before
	public void setUp() { 
		this.perm = Permutation.fromDigits("3102");
	}
	
	@Test
	public void doesNotMutatePermutation() {
		new PermutationGenerator(perm).iterator().next();
		assertThat(perm, equalTo(Permutation.fromDigits("3102")));
	}
	
	@Test
	public void reachesTheEnd() {
		Iterator<Permutation> it = new PermutationGenerator(perm).iterator();
		assertThat(it.next(), equalTo(Permutation.fromDigits("3102")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("3120")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("3201")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("3210")));
		assertThat(it.hasNext(), is(false));
	}
	
	@Test
	public void goesBackward() {
		Iterator<Permutation> it = new PermutationGenerator(perm, true).iterator();
		assertThat(it.next(), equalTo(Permutation.fromDigits("3102")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("3021")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("3012")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("2310")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("2301")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("2130")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("2103")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("2031")));
		assertThat(it.next(), equalTo(Permutation.fromDigits("2013")));
	}
	
	@Test
	public void permutesData() {
		String[] data = { "0", "1", "2" };
		Iterator<String[]> it = new PermutationGenerator(Permutation.fromDigits("012")).generatePermutations(data);
		assertThat(it.next(), equalTo(new String[] { "0", "1", "2" }));
		assertThat(it.next(), equalTo(new String[] { "0", "2", "1" }));
		assertThat(it.next(), equalTo(new String[] { "1", "0", "2" }));
		assertThat(it.next(), equalTo(new String[] { "1", "2", "0" }));
		assertThat(it.next(), equalTo(new String[] { "2", "0", "1" }));
		assertThat(it.next(), equalTo(new String[] { "2", "1", "0" }));
	}
}
