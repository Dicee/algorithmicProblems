package utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import static utils.ExtendedMath.reverse;
import static utils.ExtendedMath.swap;

public class PermutationGenerator implements Iterable<Integer[]> {
	private int n, index;
	private boolean init = true;
	
	private Integer[] current;
	private Integer[] goal;
	private Comparator<Integer> cmp;
	
	public PermutationGenerator(int n) {
		this(n,false);
	}
	
	public PermutationGenerator(String seed) {
		this(seed,false);
	}
	
	public PermutationGenerator(int n, boolean reverse) {
		this(first(n,reverse),reverse);
	}
	
	public PermutationGenerator(String seed, boolean reverse) {
		this(toIntArray(seed),reverse);
	}
	
	private PermutationGenerator(Integer[] seed, boolean reverse) {
		checkSeed(seed);
		this.n       = seed.length;
		this.cmp     = reverse ? Comparator.reverseOrder() : Integer::compare;
		this.index   = 0;
		this.goal    = first(seed.length,!reverse);
		this.current = seed;
	}
	
	private void checkSeed(Integer[] seed) {
		boolean[] seen = new boolean[seed.length];
		for (Integer i : seed)
			if (i < 0 || i >= seed.length || seen[i]) 
				throw new IllegalArgumentException("Malformed seed : " + Arrays.toString(seed));
			else 
				seen[i] = true;
	}

	private static final Integer[] first(int n, boolean reverse) {
		Integer[] res = new Integer[n];
		for (int i=0 ; i<n ; i++) res[i] = reverse ? n-i-1 : i;
		return res;
	}
	
	private static final Integer[] toIntArray(String seed) {
		char   [] digits = seed.toCharArray();
		Integer[] res    = new Integer[digits.length];
		for (int i=0 ; i<digits.length ; i++) res[i] = digits[i] - '0';
		return res;
	}
	
	private boolean sortedRight(int index) {
		for (int i=index ; i<current.length - 1 ; i++)
			if (cmp.compare(current[i],current[i + 1]) < 0)
				return false;
		return true;
	}
	
	@Override
	public Iterator<Integer[]> iterator() {
		return new Iterator<Integer[]>() {
			@Override
			public boolean hasNext() {
				return !Arrays.equals(current,goal);
			}

			@Override
			public Integer[] next() {
				if (init) {
					init = false;
					return current;
				}
				if (index != 0 && sortedRight(index)) {
					while (index > 0 && cmp.compare(current[index - 1],current[index]) > 0) index--;
					int k = index - 1;
					while (index < n - 1 && cmp.compare(current[k],current[index + 1]) < 0) index++;
					swap(current,index,k);
					reverse(current,k + 1,current.length-1);
					index = k;
					return current;
				} else {
					index++;
					return next();
				}
			}
		};
	}
	
	public static void main(String[] args) {
		for (Integer[] arr : new PermutationGenerator("30154"))
			System.out.println(Arrays.toString(arr));
	}
}
