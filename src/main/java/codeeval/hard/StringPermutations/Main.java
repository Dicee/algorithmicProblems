package codeeval.hard.StringPermutations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

// I reused a utility class that I have made specifically for that kind of problem
// I actually realise this class is not generic enough and I will rewrite it in Scala
// with more features
public class Main<T> implements Iterable<T[]> {
	// static method used to generate the identity integer permutation as a facility to be used in the constructor
	// when T is Integer
	public static final Integer[] identity(int n) {
		return first(n,false);
	}
	
	private Integer[] start;
	private Integer[] goal;
	private T      [] toPermute; 
	private Comparator<Integer> cmp;
	
	public Main(int n, T[] toPermute) {
		this(n,false,toPermute);
	}
	
	public Main(int n, boolean reverse, T[] toPermute) {
		this(first(n,reverse),first(n,!reverse),toPermute);
	}
	
	private Main(Integer[] seed, Integer[] goal, T[] toPermute) {
		checkPerm(seed);
		checkPerm(goal);
		
		if (seed.length != goal.length || toPermute.length != goal.length)
			throw new IllegalArgumentException("Inconsistent permutation length");
		
		boolean reverse = false;
		for (int i=0 ; i < seed.length && !reverse ; i++)
			if (reverse = seed[i] > goal[i]);
			else if (seed[i] < goal[i]) break;

		final boolean rev = reverse;
		this.goal         = goal;
		this.start        = seed;
		this.toPermute    = toPermute;
		this.cmp          = new Comparator<Integer>() {
			public int compare(Integer t0, Integer t1) {
				return rev ? t1.compareTo(t0) : t0.compareTo(t1);
			}
		};
		
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
	
	@Override
	public Iterator<T[]> iterator() {
		return new Iterator<T[]>() {
			private Integer[]	current = Arrays.copyOf(start,start.length);
			private T      []   toPerm  = Arrays.copyOf(toPermute,toPermute.length);
			private boolean		init	= true;
			private int			index	= 0;
			private boolean		ended   = Arrays.equals(current,goal);
			
			@Override
			public boolean hasNext() {
				return !ended;
			}

			@Override
			public T[] next() {
				if (ended)
					throw new NoSuchElementException();
				
				if (init) {
					init = false;
					return toPerm;
				}
				
				int n = current.length;
				T[] result;
				
				if (index != 0 && sortedRight(index)) {
					while (index > 0 && cmp.compare(current[index - 1],current[index]) > 0) index--;
					int k = index - 1;
					while (index < n - 1 && cmp.compare(current[k],current[index + 1]) < 0) index++;
					
					swap(current,index,k);
					reverse(current,k + 1,current.length-1);
					
					swap(toPerm,index,k);
					reverse(toPerm,k + 1,toPerm.length-1);
					
					index  = k;
					result = toPerm;
				} else {
					index++;
					result = next();
				}
				ended = Arrays.equals(current,goal);
				return result;
			}
			
			private boolean sortedRight(int index) {
				for (int i=index ; i<current.length - 1 ; i++)
					if (cmp.compare(current[i],current[i + 1]) < 0)
						return false;
				return true;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static <T> void reverse(T[] arr, int min, int max) {
		while (min < max) swap(arr,min++,max--);
	}
	
	public static <T> void swap(T[] arr, int i, int j) {
		T tmp  = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static void reverse(int[] arr, int min, int max) {
		while (min < max) swap(arr,min++,max--);
	}
	
	public static void swap(int[] arr, int i, int j) {
		int tmp  = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static void main(String[] args) throws IOException {
		StringBuilder  sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(new File(args[0])));
		
		for (String line = br.readLine() ; line != null ; line = br.readLine()) {
			String[] arr = line.split("(?<!^)");
			Arrays.sort(arr);
			Main<String> generator = new Main<>(line.length(),arr);
			// the test cases involve tiny strings (length == 3 or 4) so we can generate the whole sequence each time
			// and store it into a StringBuilder
			for (String[] perm : generator) {
				for (String  s : perm) sb.append(s);
				sb.append(",");
			}
			String s = sb.toString();
			System.out.println(s.substring(0,s.length() - 1));
			sb.setLength(0);
		}
		br.close();
	}
}
