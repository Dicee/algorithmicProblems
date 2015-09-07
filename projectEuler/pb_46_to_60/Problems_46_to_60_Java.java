package projectEuler.pb_46_to_60;

import static com.dici.math.MathUtils.ESieve;
import static com.dici.math.MathUtils.isPrime;
import static com.dici.math.MathUtils.reverse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Problems_46_to_60_Java {

	/**
	 * It was proposed by Christian Goldbach that every odd composite number can
	 * be written as the sum of a prime and twice a square.
	 * 
	 * 9 = 7 + 2×1^2 15 = 7 + 2×2^2 21 = 3 + 2×3^2 25 = 7 + 2×3^2 27 = 19 + 2×2^2 33
	 * = 31 + 2×1^2
	 * 
	 * It turns out that the conjecture was false.
	 * 
	 * What is the smallest odd composite that cannot be written as the sum of a
	 * prime and twice a square?
	 */
	public static long problem46() {
		long res = 0;
		for (long i=3 ; res == 0 ; i += 2) {
			boolean found = false;
			for (long j=0 ; j*j<i ; j++)
				if (found = isPrime(i - 2*j*j)) break; 
			res = !found ? i : res;
		}
		return res;
	}
	
	/**
	 * The first two consecutive numbers to have two distinct prime factors are:
	 * 
	 * . 14 = 2 × 7 
	 * . 15 = 3 × 5
	 * 
	 * The first three consecutive numbers to have three distinct prime factors
	 * are:
	 * 
	 * . 644 = 2² × 7 × 23 
	 * . 645 = 3 × 5 × 43 
	 * . 646 = 2 × 17 × 19.
	 * 
	 * Find the first four consecutive integers to have four distinct prime
	 * factors. What is the first of these numbers?
	 */
	public static int problem47() {
		int n = 200000;
		List<Integer> primes = new ArrayList<>();
		ESieve(n,primes);
		int max = primes.get(primes.size() - 1);
		
		int res = 0;
		for (int i=2*3*5*7 ; i<max && res == 0 ; ) {
			int inc = 0;
			while (inc < 4 && countPrimeFactors(i + inc,primes) == 4) inc++;
			if (inc == 4) res = i;
			else          i += inc + 1;
		}
		return res;
	}
	
	public static int countPrimeFactors(int n, List<Integer> primes) {
		int count = 0;
		for (int i : primes) {
			if    (i*i    > n) return count + 1;
			if    (n     == 1) return count;  
			if    (n % i == 0) count++;
			while (n % i == 0) n /= i;
		}
		return count;
	}
	
	/**
	 * The series, 11 + 22 + 33 + ... + 1010 = 10405071317.
	 * Find the last ten digits of the series, 11 + 22 + 33 + ... + 10001000.
	 */
	public static long problem48() {
		return Problems_46_to_60_Scala.problem48();
	}
	
	public static long problem48_bis() {
		long res = 0;
		long m   = 10000000000L;
		for (long i=1 ; i<=1000 ; i++) 
			res = (res + powMod(i,i,m)) % m;
		return res;
	}
	
	public static long powMod(long n, long p, long m) {
		long res = 1;
		for (int i=0 ; i<p ; i++) 
			res = (res * n) % m;
		return res;
	}
	
	/**
	 * The arithmetic sequence, 1487, 4817, 8147, in which each of the terms
	 * increases by 3330, is unusual in two ways: (i) each of the three terms
	 * are prime, and, (ii) each of the 4-digit numbers are permutations of one
	 * another.
	 * 
	 * There are no arithmetic sequences made up of three 1-, 2-, or 3-digit
	 * primes, exhibiting this property, but there is one other 4-digit
	 * increasing sequence.
	 * 
	 * What 12-digit number do you form by concatenating the three terms in this
	 * sequence?
	 */
	public static long problem49() {
		List<Integer> primes = ESieve(3340,new ArrayList<>(),x -> x > 1000);
		long res = 0;
		for (int i : primes) 
			if (i != 1487) {
				int digits = digits(i);
				int k      = i + 3330;
				while (k < i + 9990 && digits(k) == digits && isPrime(k))
					k += 3330;
				if (k == i + 9990) {
					res = i;
					break;
				}
			}
		for (long i=0, n=res + 3330 ; i<2 ; i++, n += 3330)
			res = res*10000 + n;
		return res;
	}

	public static int digits(int n) {
		int res = 0;
		while (n > 0) {
			res += 1 << (n % 10);
			n   /= 10;
		}
		return res;
	}
	
	/**
	 * The prime 41, can be written as the sum of six consecutive primes: 41 = 2
	 * + 3 + 5 + 7 + 11 + 13
	 * 
	 * This is the longest sum of consecutive primes that adds to a prime below
	 * one-hundred.
	 * 
	 * The longest sum of consecutive primes below one-thousand that adds to a
	 * prime, contains 21 terms, and is equal to 953.
	 * 
	 * Which prime, below one-million, can be written as the sum of the most
	 * consecutive primes?
	 */
	public static int problem50() {
		List<Integer> primes = ESieve(1_000_000,new ArrayList<>());
		List<Integer> sums   = new ArrayList<>();
		sums.add(0);
		
		int size = primes.size();
		for (int i=0, sum=0 ; sum + primes.get(i) < primes.get(size - 1) ; i++) 
			sums.add(sum += primes.get(i));
		int n    = sums.size();

		int res = 0;
		int length = 0;
		for (int j=0 ; j<n ; j++) 
			for (int k=j-length ; k>=0 && sums.get(j) - sums.get(k) < 1_000_000 ; k--) {
				if (Collections.binarySearch(primes,sums.get(j) - sums.get(k)) >= 0) {
					res    = sums.get(j) - sums.get(k);
					length = j - k;
				}
			}
		return res;
	}
	
	/**
	 * It can be seen that the number, 125874, and its double, 251748, contain exactly the same digits, 
	 * but in a different order. Find the smallest positive integer, x, such that 2x, 3x, 4x, 5x, and 6x,
	 * contain the same digits.
	 */
	public static int problem52() {
		float lowerBound = 10*5/3f;
		float upperBound = 10*lowerBound;
		int res          = (int) lowerBound;
		while (!multiplesHaveSameDigits(res)) {
			if (res + 1 >= upperBound) {
				lowerBound  = upperBound;
				upperBound *= 10;
				res         = (int) lowerBound;
			} else 
				res++;
		}
		return res;
	}
	
	private static boolean multiplesHaveSameDigits(int n) {
		List<byte[]> multipleDigits = new LinkedList<>();
		for (int i=2 ; i<=6 ; multipleDigits.add(getDigits((i++)*n)));

		Iterator<byte[]> it   = multipleDigits.iterator();
		byte[]           prev = it.next();
		while (it.hasNext()) {
			byte[] next = it.next();
			if (!Arrays.equals(prev,next)) return false;
			prev = next;
		}
		return true;
	}
	
	private static byte[] getDigits(int n) {
		byte[] digits = new byte[10];
		while (n > 0) {
			digits[n % 10]++;
			n /= 10;
		}
		return digits;
	}
	
	/**
	 * There are exactly ten ways of selecting three from five, 12345: 123, 124, 125, 134, 135, 145, 234, 235, 245, and 345
	 * In combinatorics, we use the notation, 5_C3 = 10. In general, n_Cr = n!/(r!(n−r)!) ,where r ≤ n, n! = n×(n−1)×...×3×2×1, and 0! = 1.
	 * It is not until n = 23, that a value exceeds one-million: 23C10 = 1144066.
	 * How many, not necessarily distinct, values of nCr, for 1 ≤ n ≤ 100, are greater than one-million?
	 */
	public static int problem53() {
		int     count   = 0;
		Integer million = 1_000_000;
		
		Deque<Integer> coeffs = new LinkedList<>();
		coeffs.push(1);
		for (int i=2 ; i<=100 ; i++) {
			Deque<Integer>    tmp  = new LinkedList<>();
			Iterator<Integer> it   = coeffs.iterator();
			Integer           prev = it.next();     
			while (it.hasNext()) {
				Integer next = it.next();
				Integer toInsert = Math.min(million,prev + next);
				if (toInsert >= million) count += 2;
				tmp.addLast(toInsert);
				prev = next;
			}
			tmp.addLast(1);
			if (i % 2 == 0) { 
				Integer toInsert = Math.min(million,2*coeffs.peek());
				tmp.addFirst(toInsert);
				if (toInsert >= million) count++;
			}
			coeffs = tmp;
		}
		return count;
	}

	public static int problem53_bis() {
		return Problems_46_to_60_Scala.problem53(100);
	}
	
	public static int problem54() {
		return 0;
	}
	
	/**
	 * If we take 47, reverse and add, 47 + 74 = 121, which is palindromic.
	 * 
	 * Not all numbers produce palindromes so quickly. For example,
	 * 
	 * 349 + 943 = 1292, 1292 + 2921 = 4213 4213 + 3124 = 7337
	 * 
	 * That is, 349 took three iterations to arrive at a palindrome.
	 * 
	 * Although no one has proved it yet, it is thought that some numbers, like
	 * 196, never produce a palindrome. A number that never forms a palindrome
	 * through the reverse and add process is called a Lychrel number. Due to
	 * the theoretical nature of these numbers, and for the purpose of this
	 * problem, we shall assume that a number is Lychrel until proven otherwise.
	 * In addition you are given that for every number below ten-thousand, it
	 * will either (i) become a palindrome in less than fifty iterations, or,
	 * (ii) no one, with all the computing power that exists, has managed so far
	 * to map it to a palindrome. In fact, 10677 is the first number to be shown
	 * to require over fifty iterations before producing a palindrome:
	 * 4668731596684224866951378664 (53 iterations, 28-digits).
	 * 
	 * Surprisingly, there are palindromic numbers that are themselves Lychrel
	 * numbers; the first example is 4994.
	 * 
	 * How many Lychrel numbers are there below ten-thousand?
	 */
	public static int problem55() {
		Set<BigInteger> nonLychrelNumbers = new HashSet<>(100);
		
		int count = 0;
		
		for (int i=10 ; i<=10000 ; i++) {
			if (isLychrel(new BigInteger(String.valueOf(i)),nonLychrelNumbers)) count++;
		}
		return count;
	}

	private static boolean isLychrel(BigInteger n, Set<BigInteger> nonLychrelNumbers) {
		BigInteger nrev = reverse(n);
		int j = 0;
		List<BigInteger> serie = new LinkedList<>();
		
		boolean isCachedNonLychrel = false;
		do {
			serie.add(n);
			serie.add(nrev);
			
			n    = n.add(nrev);
			nrev = reverse(n);
			
			if (isCachedNonLychrel = (nonLychrelNumbers.contains(n) || nonLychrelNumbers.contains(nrev))) break;
			
			j++;
		} while (j <= 25 && !nrev.equals(n));
		
		if (isCachedNonLychrel || nrev.equals(n)) 
			nonLychrelNumbers.addAll(serie);
		
		return !isCachedNonLychrel && !nrev.equals(n);
	}
	
	/**
	 * By replacing the 1st digit of the 2-digit number *3, it turns out that
	 * six of the nine possible values: 13, 23, 43, 53, 73, and 83, are all
	 * prime.
	 * 
	 * By replacing the 3rd and 4th digits of 56**3 with the same digit, this
	 * 5-digit number is the first example having seven primes among the ten
	 * generated numbers, yielding the family: 56003, 56113, 56333, 56443,
	 * 56663, 56773, and 56993. Consequently 56003, being the first member of
	 * this family, is the smallest prime with this property.
	 * 
	 * Find the smallest prime which, by replacing part of the number (not
	 * necessarily adjacent digits) with the same digit, is part of an eight
	 * prime value family.
	 */
	public static long problem(String s) {
		switch (s) {
			case "46"      : return problem46     ();
			case "47"      : return problem47     ();
			case "48"      : return problem48     ();
			case "48_bis"  : return problem48_bis ();
			case "49"      : return problem49     ();
			case "50"      : return problem50     ();
			case "52"      : return problem52     ();
			case "53"      : return problem53     ();
			case "53_bis"  : return problem53_bis ();
			case "54"      : return problem54     ();
			case "55"      : return problem55     ();
		}
		throw new IllegalArgumentException(String.format("Problem %s doesn't exist",s));
	}
	
	public static void test(String pb, long expected) {
		long start  = System.nanoTime();
		long actual = problem(pb);
		try {
			assert(actual == expected);
			System.out.println(String.format("Problem %s passed (execution time : %.2f ms)",pb,(System.nanoTime() - start)/1e6d));
		} catch (AssertionError ae) {
			System.err.println(String.format("Problem %s failed (execution time : %.2f ms, returned %d)",pb,(System.nanoTime() - start)/1e6d,actual));
		}
	}
	
	public static void testAll() {
		String[] tests   = { "46","47","48","48_bis","49","50","52","53","53_bis","55" };
		long[]   results = { 5777,134043,9110846700L,9110846700L,296962999629L,997651,142857,4075,4075,249 };
		
		for (int i = 0; i < tests.length; i++)
			test(tests[i],results[i]);
	}
	
	public static void main(String[] args) { 
		testAll(); 
	}
}
