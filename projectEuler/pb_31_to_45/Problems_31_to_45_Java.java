package projectEuler.pb_31_to_45;


import static miscellaneous.utils.math.MathUtils.isPalindrome;
import static miscellaneous.utils.math.MathUtils.isPandigital;
import static miscellaneous.utils.math.MathUtils.isPrime;
import static miscellaneous.utils.math.MathUtils.reverse;
import static miscellaneous.utils.math.Permutation.identity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import miscellaneous.utils.math.Permutation;
import miscellaneous.utils.math.PermutationGenerator;

public class Problems_31_to_45_Java {
	/**
	 * In England the currency is made up of pound, £, and pence, p, and there
	 * are eight coins in general circulation:
	 * 
	 * 1p, 2p, 5p, 10p, 20p, 50p, £1 (100p) and £2 (200p).
	 * 
	 * It is possible to make £2 in the following way:
	 * 
	 * 1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p
	 * 
	 * How many different ways can £2 be made using any number of coins?
	 */
	public static int problem31() {
		int count = 0;
		for (int i=200 ; i>=0 ; i -= 200) 
			for (int j=i ; j>=0 ; j -= 100)
				for (int k=j ; k>=0 ; k -= 50)
					for (int l=k ; l>=0 ; l -= 20)
						for (int m=l ; m>=0 ; m -= 10)
							for (int n=m ; n>=0 ; n -= 5)
								for (int o=n ; o>=0 ; o -= 2)
									count++;
		return count;
	}
	
	public static int problem31_bis() {
		int[] coins  = { 1,2,5,10,20,50,100,200 };
		int[] subRes = new int[201];
		subRes[0]    = 1;
		for (int c : coins) {
			for (int i=0 ; i + c <subRes.length ; i++)
				subRes[i + c] += subRes[i];
		}
		return subRes[subRes.length - 1];
	}
	
	/**
	 * We shall say that an n-digit number is pandigital if it makes use of all
	 * the digits 1 to n exactly once; for example, the 5-digit number, 15234,
	 * is 1 through 5 pandigital.
	 * 
	 * The product 7254 is unusual, as the identity, 39 × 186 = 7254, containing
	 * multiplicand, multiplier, and product is 1 through 9 pandigital.
	 * 
	 * Find the sum of all products whose multiplicand/multiplier/product
	 * identity can be written as a 1 through 9 pandigital. HINT: Some products
	 * can be obtained in more than one way so be sure to only include it once
	 * in your sum.
	 */
	public static int problem32() {
		Set<Long> prods = new HashSet<>();
		int res = 0;
		for (long i=2 ; i<100 ; i++) {
			long min = i < 10 ? 1234 : 123;
			long max = 10000/i + 1;
			for (long j=min ; j<max ; j++) {
				long prod = i*j;
				if (isPandigital(concat(concat(prod,j),i)) && !prods.contains(prod)) {
					res += prod;
					prods.add(prod);
				}
			}
		}
		return res;
	}
	
	public static long concat(long n, long m) {
		long k = m;
		while (k > 0) {
			k /= 10;
			n *= 10;
		}
		return n + m;
	}
	
	/**
	 The fraction 49/98 is a curious fraction, as an inexperienced
	 * mathematician in attempting to simplify it may incorrectly believe that
	 * 49/98 = 4/8, which is correct, is obtained by cancelling the 9s.
	 * 
	 * We shall consider fractions like, 30/50 = 3/5, to be trivial examples.
	 * 
	 * There are exactly four non-trivial examples of this type of fraction,
	 * less than one in value, and containing two digits in the numerator and
	 * denominator.
	 * 
	 * If the product of these four fractions is given in its lowest common
	 * terms, find the value of the denominator.
	 */
	public static int problem33() {
		return Problems_31_to_45_Scala.problem33();
	}

	/**
	 * 145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
	 * 
	 * Find the sum of all numbers which are equal to the sum of the factorial
	 * of their digits.
	 * 
	 * Note: as 1! = 1 and 2! = 2 are not sums they are not included.
	 */
	public static int problem34() {
		return Problems_31_to_45_Scala.problem34();
	}

	public static int problem34_bis() {
		int[] fact = new int[10];
		fact[0]    = 1;
		for (int i=1 ; i<fact.length ; i++) fact[i] = i*fact[i-1];
		int biggest, i;
		for (i=1 ; Math.pow(10,i) < i*fact[9] ; i++);
		biggest = i*fact[9];
		
		int res = 0;
		for (i=10 ; i<=biggest ; i++) {
			int n   = i;
			int sum = 0;
			while (n > 0) {
				sum += fact[n % 10];
				n /= 10;
			}
			if (sum == i) res += i;
		}
		return res;
	}
	
	/**
	 * The number, 197, is called a circular prime because all rotations of the
	 * digits: 197, 971, and 719, are themselves prime.
	 * 
	 * There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37,
	 * 71, 73, 79, and 97.
	 * 
	 * How many circular primes are there below one million?
	 */
	public static int problem35() {
		return Problems_31_to_45_Scala.problem35();
	}
	
	public static int problem35_bis() {
		Set<Integer> eligiblePrimes = new HashSet<>();
		eligiblePrimes.add(2);
		for (int i=3 ; i<999_999 ; i += 2) 
			if (eligible(i)) 
				eligiblePrimes.add(i);

		Set<Integer> res = new HashSet<>();
		for (int i : eligiblePrimes) {
			if (!res.contains(i)) {
				boolean       fail;
				List<Integer> cycle = new ArrayList<>();
				int pow;
				for (pow=1 ; 10*pow <= i ; pow *= 10);
				int next = i;
				do {
					next = (next % 10) * pow + next/10;
					fail = !eligiblePrimes.contains(next);
					cycle.add(next);
				} while (next != i && !fail);
				if (!fail)
					res.addAll(cycle);
			}
		}
		return res.size();
	}
	
	public static boolean eligible(int n) {
		if (n == 2 || n == 5) return true;
		int k = n;
		while (n > 10) {
			int l = n % 10;
			if (l % 2 == 0 || l == 5)
				return false;
			n /= 10;
		}
		return isPrime(k);
	}
	
	/**
	 * The decimal number, 585 = 10010010012 (binary), is palindromic in both
	 * bases.
	 * 
	 * Find the sum of all numbers, less than one million, which are palindromic
	 * in base 10 and base 2.
	 * 
	 * (Please note that the palindromic number, in either base, may not include
	 * leading zeros.)
	 */
	public static int problem36() {
		int res = 0;
		for (int k = 0 ; k<2 ; k++)
			for (int i=1 ; i<=999 ; i++) {
				int p = createPalindrome(i,10,k % 2 == 0);
					if (isPalindrome(p,2))
						res += p;
			}
		return res;
	}
	
	private static int createPalindrome(int n, int b, boolean odd) {
		int res = n;
		if (odd) n /= b;
		while (n > 0) {
			res = (res * b) + n % b;
			n  /= b;
		}
		return res;
	}
	
	/**
	 The number 3797 has an interesting property. Being prime itself, it is
	 * possible to continuously remove digits from left to right, and remain
	 * prime at each stage: 3797, 797, 97, and 7. Similarly we can work from
	 * right to left: 3797, 379, 37, and 3.
	 * 
	 * Find the sum of the only eleven primes that are both truncatable from
	 * left to right and right to left.
	 * 
	 * NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.
	 */
	public static long problem37() {
		long res = 0;
		int count = 0;
		for (int i=11 ; count<11 ; i += 2) {
			if (isTruncatableLeft(i) && isTruncatableRight(i)) {
				count++;
				res += i;
			}
		}
		return res;
	}
	
	public static boolean isTruncatableRight(int n) {
		int sub = 0;
		int i   = 1;
		while (n > 0) {
			sub += (n % 10)*i; 
			n   /= 10;
			i   *= 10;
			if (!isPrime(sub)) return false;
		}
		return true;
	}
	
	public static boolean isTruncatableLeft(int n) {
		while (n > 0) {
			if (!isPrime(n)) return false;
			n /= 10;
		}
		return true;
	}
	
	/**
	 * Take the number 192 and multiply it by each of 1, 2, and 3:
	 * 
	 * 192 × 1 = 192 192 × 2 = 384 192 × 3 = 576
	 * 
	 * By concatenating each product we get the 1 to 9 pandigital, 192384576. We
	 * will call 192384576 the concatenated product of 192 and (1,2,3)
	 * 
	 * The same can be achieved by starting with 9 and multiplying by 1, 2, 3,
	 * 4, and 5, giving the pandigital, 918273645, which is the concatenated
	 * product of 9 and (1,2,3,4,5).
	 * 
	 * What is the largest 1 to 9 pandigital 9-digit number that can be formed
	 * as the concatenated product of an integer with (1,2, ... , n) where n >
	 * 1?
	 */
	public static long problem38() {
		long res = 0;
		for (int i=9487 ; i>=9234 && res == 0; i--) {
			long n = concat(i,i*2);
			if (isPandigital(n)) res = n;
		}
		return res;
	}
	
	/**
	 If p is the perimeter of a right angle triangle with integral length
	 * sides, {a,b,c}, there are exactly three solutions for p = 120.
	 * 
	 * {20,48,52}, {24,45,51}, {30,40,50}
	 * 
	 * For which value of p ≤ 1000, is the number of solutions maximised?
	 */
	public static int problem39() {
		int res = 120;
		int max = 3;
		for (int p=4 ; p<=1000 ; p += 2) {
			int count = 0;
			for (int a=1 ; a<=p/3 ; a++)
				if ((p*(p - 2*a)) % (2*(p - a)) == 0) count++;
			if (count > max) {
				max = count;
				res = p;
			}
		}
		return res;
	}

	/**
	 * An irrational decimal fraction is created by concatenating the positive
	 * integers:
	 * 
	 * 0.123456789101112131415161718192021...
	 * 
	 * It can be seen that the 12th digit of the fractional part is 1.
	 * 
	 * If dn represents the nth digit of the fractional part, find the value of
	 * the following expression.
	 * 
	 * d_1 × d_10 × d_100 × d_1000 × d_10000 × d_100000 × d_1000000
	 */
	public static int problem40() {
		int res = 1;
		for (int d=1, k=0 ; k<6 ; k++, d *= 10) {
			int i = 9;
			int n = d;
			int digits = 1;
			while (n - i*digits >= 0) {
				n -= i * digits;
				i *= 10;
				digits++;
			}
			i /= 9;
			while (n - digits > 0) {
				i++;
				n -= digits;
			}
			res *= getDigit(i,n - 1);
		}
		return res;
	}
	
	public static int getDigit(int n, int index) {
		int rev = reverse(n,10);
		for (int i=0 ; i<index ; i++)
			rev /= 10;
		return rev % 10;
	}
	
	public static int problem40_bis() {
		int max = 7;
		int res = 1;

		int u = 1;
		int[] uSeq = new int[max];
		int pow = 1;
		uSeq[0] = 1;
		for (int i=1 ; i<max ; i++) {
			u += 9*i*pow;
			pow *= 10;
			uSeq[i] = u;
		}
		
		for (int d=1, k=0 ; k<6 ; k++, d *= 10) {
			int i;
			for (i = 0; i - 1 < uSeq.length && uSeq[i + 1] <= d; i++);
			int rank = (d - uSeq[i]) / (i + 1);
			res *= getDigit((int) Math.pow(10,i) + rank,rank % (i + 1));
		}
		return res;
	}
	
	public static int problem40_tris() {
		return Problems_31_to_45_Scala.problem40();
	}
	
	/**
	 * We shall say that an n-digit number is pandigital if it makes use of all
	 * the digits 1 to n exactly once. For example, 2143 is a 4-digit pandigital
	 * and is also prime.
	 * 
	 * What is the largest n-digit pandigital prime that exists?
	 */
	public static long problem41() {
		for (int i=9 ; i>0 ; i--) {
			int sum = 0;
			for (int j=1 ; j<=i ; j++) sum += j;
			//We eliminate the pandigital numbers that are divisible by 3
			if (sum % 3 != 0)
				for (Permutation perm : new PermutationGenerator(identity(i),true)) {
					int n = 0;
					for (int digit : perm) n = n*10 + digit + 1;
					if (isPrime(n)) 
						return n;
				}
		}
		return -1;
	}
	
	/**
	 * The nth term of the sequence of triangle numbers is given by, tn =
	 * 1/2*n(n+1); so the first ten triangle numbers are:
	 * 
	 * 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, ...
	 * 
	 * By converting each letter in a word to a number corresponding to its
	 * alphabetical position and adding these values we form a word value. For
	 * example, the word value for SKY is 19 + 11 + 25 = 55 = t10. If the word
	 * value is a triangle number then we shall call the word a triangle word.
	 * 
	 * Using words.txt (right click and 'Save Link/Target As...'), a 16K text
	 * file containing nearly two-thousand common English words, how many are
	 * triangle words?
	 */
	public static int problem42() {
		return Problems_31_to_45_Scala.problem42();
	}
	
	/**
	 * The number, 1406357289, is a 0 to 9 pandigital number because it is made
	 * up of each of the digits 0 to 9 in some order, but it also has a rather
	 * interesting sub-string divisibility property.
	 * 
	 * Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we
	 * note the following:
	 * 
	 * d_2d_3d_4=406 is divisible by 2 d_3d_4d_5=063 is divisible by 3 d_4d_5d_6=635 is
	 * divisible by 5 d_5d_6d_7=357 is divisible by 7 d_6d_7d_8=572 is divisible by 11
	 * d_7d_8d_9=728 is divisible by 13 d8d9d10=289 is divisible by 17
	 * 
	 * Find the sum of all 0 to 9 pandigital numbers with this property.
	 */
	public static Long problem43() {
		return Problems_31_to_45_Scala.problem43();
	}
	
	public static long problem43_bis() {
		long    res   = 0;
		int[][] conds = { { 4,7 },{ 5,11},{ 6,13 },{ 7,17 } };
		ext : for (Permutation perm : new PermutationGenerator(Permutation.fromDigits("1023546789"))) {
			if (perm.get(3) % 2 != 0)                       continue;
			if (perm.get(5) % 5 != 0)                       continue;
			if ((perm.get(2) + perm.get(3) + perm.get(4)) % 3 != 0) continue;
			for (int[] cond : conds) {
				int i = cond[0];
				if ((100*perm.get(i) + 10*perm.get(i)+ 1 + perm.get(i)+ 2) % cond[1] != 0)
					continue ext;
			}
			long n = 0;
			for (int i : perm) n = 10*n + i;
			res += n;
		}
		return res;
	}
	
	/**
	 * Pentagonal numbers are generated by the formula, Pn=n(3n−1)/2. The first
	 * ten pentagonal numbers are:
	 * 
	 * 1, 5, 12, 22, 35, 51, 70, 92, 117, 145, ...
	 * 
	 * It can be seen that P4 + P7 = 22 + 70 = 92 = P8. However, their
	 * difference, 70 − 22 = 48, is not pentagonal.
	 * 
	 * Find the pair of pentagonal numbers, Pj and Pk, for which their sum and
	 * difference are pentagonal and D = |Pk − Pj| is minimised; what is the
	 * value of D?
	 */
	public static int problem44() {
		int res = 0;
		for (int i=1 ; res == 0 ; i++) {
			int n = i*(3*i - 1)/2;
			for (int j=i-1 ; j>0 ; j--) {
				int m = j*(3*j - 1)/2;
				if (isPentagonal(n - m) && isPentagonal(n + m)) {
					res = n - m;
				}
			}
		}
		return res;
	}
	
	public static boolean isPentagonal(long n) {
		double d = (Math.sqrt(1 + 24*n) + 1)/6;
		return (long) d == d;
	}
	
	public static boolean isTriangle(int n) {
		double d = Math.sqrt(1 + 8*n);
		return (int) d == d;
	}
	
	/**
	 * Triangle, pentagonal, and hexagonal numbers are generated by the
	 * following formulae: Triangle Tn=n(n+1)/2 1, 3, 6, 10, 15, ... Pentagonal
	 * Pn=n(3n−1)/2 1, 5, 12, 22, 35, ... Hexagonal Hn=n(2n−1) 1, 6, 15, 28, 45,
	 * ...
	 * 
	 * It can be verified that T_285 = P_165 = H_143 = 40755.
	 * 
	 * Find the next triangle number that is also pentagonal and hexagonal.
	 */
	public static long problem45() {
		long res = 0;
		long i   = 1;
		while (2*i*i + 3*i + 1 <= 40755) i++;
		for ( ; res == 0 ; i++) {
			long n = 2*i*i + 3*i + 1;
			if (isPentagonal(n)) res = n;
		}
		return res;
	}
	
	public static long problem(String s) throws IOException {
		switch (s) {
			case "31"      : return problem31     ();
			case "32"      : return problem32     ();
			case "33"      : return problem33     ();
			case "31_bis"  : return problem31_bis ();
			case "34"      : return problem34     ();
			case "34_bis"  : return problem34_bis ();
			case "35"      : return problem35     ();
			case "35_bis"  : return problem35_bis ();
			case "36"      : return problem36     ();
			case "37"      : return problem37     ();
			case "38"      : return problem38     ();
			case "39"      : return problem39     ();
			case "40"      : return problem40     ();
			case "40_bis"  : return problem40_bis ();
			case "40_tris" : return problem40_tris();
			case "41"      : return problem41     ();
			case "42"      : return problem42     ();
			case "43"      : return problem43     ();
			case "43_bis"  : return problem43_bis ();
			case "44"      : return problem44     ();
			case "45"      : return problem45     ();
		}
		throw new IllegalArgumentException(String.format("Problem %s doesn't exist",s));
	}
	
	public static void test(String pb, long result) throws IOException {
		long start = System.nanoTime();
		long r     = problem(pb);
		try {
			assert(r == result);
			System.out.println(String.format("Problem %s passed (execution time : %.2f ms)",pb,(System.nanoTime() - start)/1e6d));
		} catch (AssertionError ae) {
			System.err.println(String.format("Problem %s failed (execution time : %.2f ms, returned %d)",pb,(System.nanoTime() - start)/1e6d,r));
		}
	}
	
	public static void main(String[] args) throws IOException {
		String[] tests   = { "31","32","33","34","34_bis","35","35_bis","36","37","38","39","40","40_bis","40_tris","41","42","43","43_bis","44","45" };
		long[]   results = { 73682,45228,100,40730,40730,55,55,872187,748317,932718654,840,210,210,210,7652413,162,16695334890L,16695334890L,5482660,1533776805 };
		
		for (int i = 0; i < tests.length; i++)
			test(tests[i],results[i]);
//		test("45",1533776805);
//		System.out.println(isPentagonal(536870913));
//		System.out.println(isTriangle(536870913));
	}
}
