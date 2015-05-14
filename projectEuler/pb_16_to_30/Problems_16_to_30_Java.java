package projectEuler.pb_16_to_30;

import static projectEuler.pb_1_to_15.Problems_1_to_15_Scala.isPrime;
import static miscellaneous.CollectionUtils.reverse;
import static miscellaneous.CollectionUtils.swap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import projectEuler.utils.ExtendedMath;

public class Problems_16_to_30_Java {

	/**
	 *2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
	 * What is the sum of the digits of the number 2^1000?
	 */
	public static int problem16() {
		String[] digits = (new BigInteger("2").pow(1000)).toString().split("");
		return Arrays.asList(digits).stream().mapToInt(Integer::parseInt).sum();
	}
	
	/**
	 * If the numbers 1 to 5 are written out in words: one, two, three, four,
	 * five, then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.
	 * 
	 * If all the numbers from 1 to 1000 (one thousand) inclusive were written
	 * out in words, how many letters would be used?
	 * 
	 * NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and
	 * forty-two) contains 23 letters and 115 (one hundred and fifteen) contains
	 * 20 letters. The use of "and" when writing out numbers is in compliance
	 * with British usage.
	 */
	public static int problem17() {
		return Problems_16_to_30_Scala.problem17();
	}
	
	/**
	 By starting at the top of the triangle below and moving to adjacent
	 * numbers on the row below, the maximum total from top to bottom is 23.
	 * 
	 * Find the maximum total from top to bottom of the triangle below:
	 * 
	 * ...
	 * 
	 * NOTE: As there are only 16384 routes, it is possible to solve this
	 * problem by trying every route. However, Problem 67, is the same challenge
	 * with a triangle containing one-hundred rows; it cannot be solved by brute
	 * force, and requires a clever method! ;o)
	 */
	public static int problem18() throws IOException {
		return trianglePath(18);
	}
	
	public static int problem67() throws IOException {
		return trianglePath(67);
	}
	
	private static int trianglePath(int pb) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(Problems_16_to_30_Java.class.getResourceAsStream("data/problem" + pb + ".txt")));
		String line;
		List<List<Integer>> field = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			List<Integer> values = Arrays.asList(line.split("\\s+")).stream().map(Integer::parseInt).collect(Collectors.toList());
			field.add(values);
		}
		br.close();
		
		for (int i=field.size() - 2 ; i>=0 ; i--) {
			List<Integer> up     = field.get(i);
			List<Integer> bottom = field.get(i + 1);
			for (int j=0 ; j<=i ; j++) 
				up.set(j,up.get(j) + Math.max(bottom.get(j),bottom.get(j + 1)));
		}
		return field.get(0).get(0);
	}

	/**
	 * You are given the following information, but you may prefer to do some
	 * research for yourself.
	 * 
	 * 1 Jan 1900 was a Monday. Thirty days has September, April, June and
	 * November. All the rest have thirty-one, Saving February alone, Which has
	 * twenty-eight, rain or shine. And on leap years, twenty-nine. A leap year
	 * occurs on any year evenly divisible by 4, but not on a century unless it
	 * is divisible by 400.
	 * 
	 * How many Sundays fell on the first of the month during the twentieth
	 * century (1 Jan 1901 to 31 Dec 2000)?
	 */
	public static int problem19() {
		return Problems_16_to_30_Scala.problem19();
	}
	
	public static int problem19_bis() {
		int[] months = { 31,28,31,30,31,30,31,31,30,31,30,31 };
		int   fm     = (isLeapYear(1900) ? 366 : 365) % 7;
		int   count  = fm == 6 ? 1 : 0;
		for (int y=1901 ; y<=2000 ; y++) {
			for (int m=0 ; m<12 ; m++) {
				fm = (fm + months[m] + (isLeapYear(y) && m == 1 ? 1 : 0)) % 7;
				if (fm == 6) count++;
			}
		}
		return count;
	}
	
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}
	
	/**
	 * n! means n × (n − 1) × ... × 3 × 2 × 1
	 * 
	 * For example, 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800, and the sum of the
	 * digits in the number 10! is 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.
	 * 
	 * Find the sum of the digits in the number 100!
	 */
	public static int problem20() {
		return Problems_16_to_30_Scala.problem20();
	}
	
	/**
	 Let d(n) be defined as the sum of proper divisors of n (numbers less than
	 * n which divide evenly into n). If d(a) = b and d(b) = a, where a ≠ b,
	 * then a and b are an amicable pair and each of a and b are called amicable
	 * numbers.
	 * 
	 * For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22,
	 * 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1,
	 * 2, 4, 71 and 142; so d(284) = 220.
	 * 
	 * Evaluate the sum of all the amicable numbers under 10000.
	 */
	public static int problem21() {
		return Problems_16_to_30_Scala.problem21();
	}
	
	/**
	 Using names.txt (right click and 'Save Link/Target As...'), a 46K text
	 * file containing over five-thousand first names, begin by sorting it into
	 * alphabetical order. Then working out the alphabetical value for each
	 * name, multiply this value by its alphabetical position in the list to
	 * obtain a name score.
	 * 
	 * For example, when the list is sorted into alphabetical order, COLIN,
	 * which is worth 3 + 15 + 12 + 9 + 14 = 53, is the 938th name in the list.
	 * So, COLIN would obtain a score of 938 × 53 = 49714.
	 * 
	 * What is the total of all the name scores in the file?
	 */
	public static long problem22() {
		return Problems_16_to_30_Scala.problem22();
	}
	
	/**
	 A perfect number is a number for which the sum of its proper divisors is
	 * exactly equal to the number. For example, the sum of the proper divisors
	 * of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which means that 28 is a perfect
	 * number.
	 * 
	 * A number n is called deficient if the sum of its proper divisors is less
	 * than n and it is called abundant if this sum exceeds n.
	 * 
	 * As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the
	 * smallest number that can be written as the sum of two abundant numbers is
	 * 24. By mathematical analysis, it can be shown that all integers greater
	 * than 28123 can be written as the sum of two abundant numbers. However,
	 * this upper limit cannot be reduced any further by analysis even though it
	 * is known that the greatest number that cannot be expressed as the sum of
	 * two abundant numbers is less than this limit.
	 * 
	 * Find the sum of all the positive integers which cannot be written as the
	 * sum of two abundant numbers.
	 */
	public static int problem23() {
		return Problems_16_to_30_Scala.problem23();
	}
	
	public static long problem23_bis() {
		int[] abundants = IntStream.range(1,28124).filter(n -> IntStream.range(1,n/2 + 1).filter(p -> n % p == 0).sum() > n).toArray();
		long  res       = 0;
		Set<Integer> diff = new HashSet<>();
		for (int j=0 ; j<abundants.length ; j++)
			for (int k=0 ; k<abundants.length && abundants[j] + abundants[k] <= 28123 ; k++)
				diff.add(abundants[k] + abundants[j]);
		for (int i=0 ; i<28124 ; i++)
			if (!diff.contains(i)) 
				res += i;
		return res;
	}
	
	/**
	 * A permutation is an ordered arrangement of objects. For example, 3124 is
	 * one possible permutation of the digits 1, 2, 3 and 4. If all of the
	 * permutations are listed numerically or alphabetically, we call it
	 * lexicographic order. The lexicographic permutations of 0, 1 and 2 are:
	 * 
	 * 012 021 102 120 201 210
	 * 
	 * What is the millionth lexicographic permutation of the digits 0, 1, 2, 3,
	 * 4, 5, 6, 7, 8 and 9?
	 */
	public static long problem24() {
		int count = 0;
		int n = 10;
		int[] perm = new int[n];
		int[] goal = new int[n];
		for (int i=0 ; i<n ; i++) {
			perm[i]     = i;
			goal[n-i-1] = i;
		}
		int index = n - 1;
//		System.out.println(Arrays.toString(perm));
		while (!Arrays.equals(perm,goal) && count < 999_999) {
			if (index != 0 && sortedRight(perm,index)) {
				while (index > 0 && perm[index - 1] > perm[index]) index--;
				int k = index - 1;
				while (index < n - 1 && perm[k] < perm[index + 1]) index++;
				swap(perm,index,k);
				reverse(perm,k + 1,perm.length-1);
				index = k;
				count++;
//				System.out.println(Arrays.toString(perm));
			} else 
				index++;
		}
		String res = "";
		for (int i=0 ; i<n ; i++) res += perm[i];
		return Long.parseLong(res);
	}
	
	public static long problem24_bis() {
		int[] fact = new int[10];
		fact[0]    = 1;
		List<Integer> unused = new ArrayList<>();
		for (int i=0 ; i<10 ; i++) unused.add(i);
		for (int i=1 ; i<10 ; i++) fact[i] = i*fact[i-1];

		String res = "";
		int    n   = 999_999;
		for (int i=0 ; i<10 ; i++) {
			int j = n / fact[10 - i - 1];
			res += unused.get(j);
			n   -= j * fact[10 - i - 1];
			unused.remove(j);
		}
		return Long.parseLong(res);
	}
	
	public static boolean sortedRight(int[] arr, int index) {
		for (int i=index ; i<arr.length - 1 ; i++)
			if (arr[i] <= arr[i + 1])
				return false;
		return true;
	}
	
	/**
	 The Fibonacci sequence is defined by the recurrence relation:
	 * Fn = Fn−1 + Fn−2, where F1 = 1 and F2 = 1.
	 * 
	 * Hence the first 12 terms will be:
	 * F1 = 1 F2 = 1 F3 = 2 F4 = 3 F5 = 5 F6 = 8 F7 = 13 F8 = 21 F9 = 34 F10 =
	 * 55 F11 = 89 F12 = 144
	 * 
	 * The 12th term, F12, is the first term to contain three digits.
	 * What is the first term in the Fibonacci sequence to contain 1000 digits?
	 */
	public static int problem25() {
		/* Mathematically, the solution consists in solving phi^n/sqrt(5) > 10^999, where phi
		 * is the golden number which can be implemented like that : 
		 * 
		 * double phi = (Math.sqrt(5) + 1)/2;
		 * double a = 999 + Math.log10(5)/2;
		 * System.out.println((int) Math.ceil(a/Math.log10(phi)));
		 */
		return Problems_16_to_30_Scala.problem25();
	}
	
	/**
	 A unit fraction contains 1 in the numerator. The decimal representation
	 * of the unit fractions with denominators 2 to 10 are given:
	 * 
	 * 1/2 = 0.5 1/3 = 0.(3) 1/4 = 0.25 1/5 = 0.2 1/6 = 0.1(6) 1/7 = 0.(142857)
	 * 1/8 = 0.125 1/9 = 0.(1) 1/10 = 0.1
	 * 
	 * Where 0.1(6) means 0.166666..., and has a 1-digit recurring cycle. It can
	 * be seen that 1/7 has a 6-digit recurring cycle.
	 * 
	 * Find the value of d < 1000 for which 1/d contains the longest recurring
	 * cycle in its decimal fraction part.
	 */
	public static int problem26() {
		int res = 0;
		int max = 0;
		for (int i=1000 ; i>max && i>=2 ; i--) {
			int r = 1;
			Set<Integer> remainders = new HashSet<>();
			while (remainders.add(r)) r = (10*r) % i;
			int size = remainders.size();
			if (size > max) {
				res = i;
				max = size;
			}
		}
		return res;
	}

	/**
	 * Euler discovered the remarkable quadratic formula: n² + n + 41
	 * 
	 * It turns out that the formula will produce 40 primes for the consecutive
	 * values n = 0 to 39. However, when n = 40, 402 + 40 + 41 = 40(40 + 1) + 41
	 * is divisible by 41, and certainly when n = 41, 41² + 41 + 41 is clearly
	 * divisible by 41.
	 * 
	 * The incredible formula n² − 79n + 1601 was discovered, which produces 80
	 * primes for the consecutive values n = 0 to 79. The product of the
	 * coefficients, −79 and 1601, is −126479.
	 * 
	 * Considering quadratics of the form:
	 * 
	 * n² + an + b, where |a| < 1000 and |b| < 1000
	 * 
	 * where |n| is the modulus/absolute value of n e.g. |11| = 11 and |−4| = 4
	 * 
	 * Find the product of the coefficients, a and b, for the quadratic
	 * expression that produces the maximum number of primes for consecutive
	 * values of n, starting with n = 0.
	 */
	public static int problem27() {
		return Problems_16_to_30_Scala.problem27();
	}
	
	public static int problem27_bis() {
		int res = 0;
		int max = 0;
		for (int a=-999 ; a<999 ; a++) 
			for (int b=-999 ; b<999 ; b++) {
				if (Math.abs(a % 2) == Math.abs(b % 2) && 39*39 + a*39 + b > 1 && isPrime(b)) {
					int i;
					for (i =0 ; isPrime(i*i + a*i + b) ; i++);
					if (i > max) {
						max = i;
						res = a*b;
					}
				}
			}
		return res;
	}
	
	/**
	 * Starting with the number 1 and moving to the right in a clockwise
	 * direction a 5 by 5 spiral is formed as follows:
	 * 
	 * 21 22 23 24 25 
	 * 20 07 08 09 10 
	 * 19 06 01 02 11 
	 * 18 05 04 03 12 
	 * 17 16 15 14 13
	 * 
	 * It can be verified that the sum of the numbers on the diagonals is 101.
	 * 
	 * What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral
	 * formed in the same way?
	 */
	public static int problem28() {
		int res  = 1;
		int index = 1;
		for (int edge=2 ; edge<=1001 ; edge += 2) 
			for (int i=0 ; i<4 ; i++) 
				res += (index += edge);
		return res;
	}
	
	/**
	 * Consider all integer combinations of ab for 2 ≤ a ≤ 5 and 2 ≤ b ≤ 5:
	 * 
	 * 2^2=4, 2^3=8, 2^4=16, 2^5=32 3^2=9, 3^3=27, 3^4=81, 3^5=243 4^2=16, 4^3=64, 4^4=256,
	 * 4^5=1024 5^2=25, 5^3=125, 5^4=625, 5^5=3125
	 * 
	 * If they are then placed in numerical order, with any repeats removed, we
	 * get the following sequence of 15 distinct terms:
	 * 
	 * 4, 8, 9, 16, 25, 27, 32, 64, 81, 125, 243, 256, 625, 1024, 3125
	 * 
	 * How many distinct terms are in the sequence generated by a^b for 2 ≤ a ≤
	 * 100 and 2 ≤ b ≤ 100?
	 */
	public static int problem29() {
		Set<Double> res = new HashSet<>();
		for (int a=2 ; a<=100 ; a++) {
			for (int b=2 ; b<=100 ; b++) 
				res.add(Math.pow(a,b));
		}
		return res.size();
	}
	
	/**
	 * Surprisingly there are only three numbers that can be written as the sum
	 * of fourth powers of their digits:
	 * 
	 * 1634 = 1^4 + 6^4 + 3^4 + 4^4
	 * 8208 = 8^4 + 2^4 + 0^4 + 8^4
	 * 9474 = 9^4 + 4^4 + 7^4 + 4^4
	 * 
	 * As 1 = 1^4 is not a sum it is not included.
	 * 
	 * The sum of these numbers is 1634 + 8208 + 9474 = 19316.
	 * 
	 * Find the sum of all the numbers that can be written as the sum of fifth
	 * powers of their digits.
	 */
	public static int problem30() {
		return Problems_16_to_30_Scala.problem30();
	}
	
	public static long problem(String s) throws IOException {
		switch (s) {
			case "16"     : return problem16     ();
			case "17"     : return problem17     ();
			case "18"     : return problem18     ();
			case "67"     : return problem67     ();
			case "19"     : return problem19     ();
			case "19_bis" : return problem19_bis ();
			case "20"     : return problem20     ();
			case "21"     : return problem21     ();
			case "22"     : return problem22     ();
			case "23"     : return problem23     ();
			case "23_bis" : return problem23_bis ();
			case "24"     : return problem24     ();
			case "24_bis" : return problem24_bis ();
			case "25"     : return problem25     ();
			case "26"     : return problem26     ();
			case "27"     : return problem27     ();
			case "27_bis" : return problem27_bis ();
			case "28"     : return problem28     ();
			case "29"     : return problem29     ();
			case "30"     : return problem30     ();
		}
		throw new IllegalArgumentException(String.format("Problem %s doesn't exist",s));
	}
	
	public static void test(String pb, long result) throws IOException {
		long start = System.nanoTime();
		try {
			assert(problem(pb) == result);
			System.out.println(String.format("Problem %s passed (execution time : %.2f ms)",pb,(System.nanoTime() - start)/1e6d));
		} catch (AssertionError ae) {
			System.err.println(String.format("Problem %s failed (execution time : %.2f ms)",pb,(System.nanoTime() - start)/1e6d));
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		String[] tests   = { "16","17","18","67","19","19_bis","20","21","22","23","23_bis","24","24_bis","25","26","27","27_bis","28","29","30" };
		long[]   results = { 1366,21124,1074,7273,171,171,648,31626,871198282L,4179871,4179871,2783915460L,2783915460L,4782,983,-59231,-59231,
				669171001,9183,443839 };
		
		for (int i = 0; i < tests.length; i++)
			test(tests[i],results[i]);
	}
}
