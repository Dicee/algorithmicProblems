package projectEuler.pb_46_to_60;

import static projectEuler.utils.ExtendedMath.isPrime;
import static projectEuler.utils.ExtendedMath.ESieve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	public static long problem(String s) {
		switch (s) {
			case "46"      : return problem46     ();
			case "47"      : return problem47     ();
			case "48"      : return problem48     ();
			case "48_bis"  : return problem48_bis ();
			case "49"      : return problem49     ();
			case "50"      : return problem50     ();
		}
		throw new IllegalArgumentException(String.format("Problem %s doesn't exist",s));
	}
	
	public static void test(String pb, long result) {
		long start = System.nanoTime();
		long r     = problem(pb);
		try {
			assert(r == result);
			System.out.println(String.format("Problem %s passed (execution time : %.2f ms)",pb,(System.nanoTime() - start)/1e6d));
		} catch (AssertionError ae) {
			System.err.println(String.format("Problem %s failed (execution time : %.2f ms, returned %d)",pb,(System.nanoTime() - start)/1e6d,r));
		}
	}
	
	public static void main(String[] args) {
		String[] tests   = { "46","47","48","48_bis","49","50" };
		long[]   results = { 5777,134043,9110846700L,9110846700L,296962999629L,997651 };
		
//		for (int i = 0; i < tests.length; i++)
//			test(tests[i],results[i]);
		test("50",997651);
//		long n = 1000000000L;
//		long p = 16;
//		System.out.println(new BigInteger("16").modPow(new BigInteger(p + ""),new BigInteger(n + "")));
//		System.out.println(powMod(16,p,n));
//		System.out.println(powMod1(16,p,n));
	}

}
