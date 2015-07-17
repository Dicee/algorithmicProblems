package miscellaneous.utils.math;

import static miscellaneous.utils.collection.ArrayUtils.reverse;
import static miscellaneous.utils.collection.ArrayUtils.swap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PermutationGenerator implements Iterable<Integer[]> {
	private Integer[] start;
	private Integer[] goal;
	private Comparator<Integer> cmp;
	
	public PermutationGenerator(int n) {
		this(n,false);
	}
	
	public PermutationGenerator(String seed) {
		this(seed,false);
	}
	
	public PermutationGenerator(String seed, String goal) {
		this(toIntArray(seed),toIntArray(goal));
	}
	
	public PermutationGenerator(int n, boolean reverse) {
		this(first(n,reverse),first(n,!reverse));
	}
	
	public PermutationGenerator(String seed, boolean reverse) {
		this(toIntArray(seed),first(seed.length(),!reverse));
	}
	
	private PermutationGenerator(Integer[] seed, Integer[] goal) {
		checkPerm(seed);
		checkPerm(goal);
		
		if (seed.length != goal.length)
			throw new IllegalArgumentException("Inconsistent permutation length");
		
		boolean reverse = false;
		for (int i=0 ; i < seed.length && !reverse ; i++)
			if (reverse = seed[i] > goal[i]);
			else if (seed[i] < goal[i]) break;

		this.cmp   = reverse ? Comparator.reverseOrder() : Integer::compare;
		this.goal  = goal;
		this.start = seed;
	}
	
	private void checkPerm(Integer[] seed) {
		boolean[] seen = new boolean[seed.length];
		for (Integer i : seed)
			if (i < 0 || i >= seed.length || seen[i]) 
				throw new IllegalArgumentException("Malformed permutation : " + Arrays.toString(seed));
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
	
	@Override
	public Iterator<Integer[]> iterator() {
		return new Iterator<Integer[]>() {
			private Integer[]	current = Arrays.copyOf(start,start.length);
			private boolean		init	= true;
			private int			index	= 0;
			private boolean		ended   = Arrays.equals(current,goal);
			
			@Override
			public boolean hasNext() {
				return !ended;
			}

			@Override
			public Integer[] next() {
				if (ended)
					throw new NoSuchElementException();
				
				if (init) {
					init = false;
					return current;
				}
				
				int n = current.length;
				Integer[] result;
				
				if (index != 0 && sortedRight(index)) {
					while (index > 0 && cmp.compare(current[index - 1],current[index]) > 0) index--;
					int k = index - 1;
					while (index < n - 1 && cmp.compare(current[k],current[index + 1]) < 0) index++;
					
					swap(current,index,k);
					reverse(current,k + 1,current.length-1);
					
					index  = k;
					result = current;
				} else {
					index++;
					result = next();
				}
				ended = Arrays.equals(result,goal);
				return result;
			}
			
			private boolean sortedRight(int index) {
				for (int i=index ; i<current.length - 1 ; i++)
					if (cmp.compare(current[i],current[i + 1]) < 0)
						return false;
				return true;
			}
		};
	}
	
	public static void main(String[] args) {
		PermutationGenerator generator = new PermutationGenerator("3201",true);
//		for (Integer[] arr : generator)
//			System.out.println(Arrays.toString(arr));
		Iterator<Integer[]> it = generator.iterator();
		while (it.hasNext())
			System.out.println(Arrays.toString(it.next()));
	}
}
