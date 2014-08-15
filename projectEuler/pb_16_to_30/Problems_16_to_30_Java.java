package projectEuler.pb_16_to_30;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
				int k   = index - 1;
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
	
	public static void reverse(int[] arr, int min, int max) {
		while (min < max) swap(arr,min++,max--);
	}
	
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i]  = arr[j];
		arr[j]  = tmp;
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
		/* Mathematically, the solution consists in resolving phi^n/sqrt(5) > 10^999, where phi
		 * is the golden number which can be implemented like that : 
		 * 
		 * double phi = (Math.sqrt(5) + 1)/2;
		 * double a = 999 + Math.log10(5)/2;
		 * System.out.println((int) Math.ceil(a/Math.log10(phi)));
		 */
		return Problems_16_to_30_Scala.problem25();
	}
	
	public static int problem30() {
		return 0;
	}
	
	public static void main(String[] args) throws IOException {

	}
}

//long start = System.currentTimeMillis();
//assert(problem16     () == 1366       );
//assert(problem17     () == 21124      );
//assert(problem18     () == 1074         );
//assert(problem67     () == 7273       );
//assert(problem19     () == 171   );
//assert(problem19_bis    () == 171     );
//assert(problem20     () == 648      );
//assert(problem21     () == 31626   );
//assert(problem22    () == 871198282L);
//assert(problem23    () == 4179871     );
//assert(problem23_bis() == 4179871     );
//assert(problem24    () == 2783915460L   );
//assert(problem24_bis() == 2783915460L    );
//assert(problem25    () == 4782  );
//assert(problem14    () == 837799       );
//assert(problem14_bis() == 837799       );
//assert(problem15    () == 137846528820L);
//assert(problem15_bis() == 137846528820L);
//System.out.println(String.format("All 19 tests passed in %d ms",System.currentTimeMillis() - start));