package miscellaneous.utils.math;

import static miscellaneous.utils.check.Check.notEmpty;
import static miscellaneous.utils.check.Check.notNull;

import java.util.Arrays;

import miscellaneous.utils.check.Check;
import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;

public final class Permutation implements Iterable<Integer>, Comparable<Permutation> {
	public static Permutation fromDigits     (String perm) { return new Permutation(toIntArray(perm)); }
	public static Permutation identity       (int n)       { return new Permutation(first(n, false )); } 
	public static Permutation reverseIdentity(int n)       { return new Permutation(first(n, true  )); } 
	
	/**
	 * Used as an internal to skip the sanity checks and go faster. Use with caution.
	 */
	static Permutation unsafePermutation(Integer[] perm) { return new Permutation(false, perm); }
	
	final Integer[]	perm;
	
	public  Permutation(Integer... perm)                        { this(true, perm); }
	private Permutation(boolean sanityChecked, Integer... perm) { this.perm = sanityChecked ? checkPerm(perm) : perm; }
	
	private Integer[] checkPerm(Integer[] perm) {
		boolean[] seen = new boolean[notEmpty(notNull(perm)).length];
		for (Integer i : perm) {
			Check.isFalse(i < 0 || i >= perm.length || seen[i], "Malformed permutation : " + Arrays.toString(perm));
			seen[i] = true;
		}
		return perm;
	}

	public int length() { return perm.length; }
	
	@Override
	public RichIterator<Integer> iterator() { return RichIterators.of(perm); }
	
	@Override
	public int compareTo(Permutation that) {
		int index = iterator().zip(that.iterator()).indexWhere(pair -> pair.getKey() != pair.getValue());
		return index >= 0 ? perm[index] - that.perm[index] : 0;
	}

	private static Integer[] first(int n, boolean reverse) {
		Check.isGreaterThan(n,0);
		Integer[] res = new Integer[n];
		for (int i=0 ; i<n ; i++) res[i] = reverse ? n - i - 1 : i;
		return res;
	}
	
	private static Integer[] toIntArray(String seed) {
		Check.notBlank(seed);
		char   [] digits = seed.toCharArray();
		Integer[] res    = new Integer[digits.length];
		for (int i=0 ; i<digits.length ; i++) Check.isBetween(0, res[i] = digits[i] - '0', 10);
		return res;
	}
	
	public int get(int index) { return perm[index]; }
	
	@Override
	public int hashCode() { return Arrays.hashCode(perm); }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Permutation that = (Permutation) obj;
		return Arrays.equals(perm, that.perm);
	}
	
	@Override
	public String toString() { return Arrays.toString(perm); }
}
