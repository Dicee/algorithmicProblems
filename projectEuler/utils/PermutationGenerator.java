package utils;

import java.util.Iterator;

public class PermutationGenerator implements Iterable<Integer[]> {
	private int n;
	private boolean reverse;
	
	public PermutationGenerator(int n) {
		this(n,false);
	}
	
	public PermutationGenerator(int n, boolean reverse) {
		this.n       = n;
		this.reverse = reverse;
	}

	@Override
	public Iterator<Integer[]> iterator() {
		return new Iterator<Integer[]>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Integer[] next() {
				return null;
			}
		};
	}
}
