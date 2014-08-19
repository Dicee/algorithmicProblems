package projectEuler.pb_1_to_15;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problems_1_to_15_Java {

	/**
	 * If we list all the natural numbers below 10 that are multiples of 3 or 5,
	 * we get 3, 5, 6 and 9. The sum of these multiples is 23. Find the sum of
	 * all the multiples of 3 or 5 below 1000.
	 */
	public static int problem1() {
		int sum = 0;
		for (int i=0 ; i<1000 ; i++)
			if (i % 3 == 0 || i % 5 == 0)
				sum += i;
		return sum;
	}
	
	/**
	 * Each new term in the Fibonacci sequence is generated by adding the
	 * previous two terms. By starting with 1 and 2, the first 10 terms will be:
	 * 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
	 * By considering the terms in the Fibonacci sequence whose values do not
	 * exceed four million, find the sum of the even-valued terms.
	 */
	public static int problem2() {
		int a = 0, b = 1;
		int c = a + b;
		int sum = 0;
		while (c <= 4_000_000) {
			if (c % 2 == 0)
				sum += c;
			a = b;
			b = c;
			c = a + b;
		}
		return sum;
	}
	
	/**
	 * The prime factors of 13195 are 5, 7, 13 and 29.
	 * What is the largest prime factor of the number 600851475143 ?
	 */
	public static long problem3() {
		long n   = 600851475143L;
		long res = 0;
		for (long i=3 ; i <= n / i ; i++)
			while (n % i == 0) {
				n  /= i;
				res = i;
			}
		return n > 1 ? n : res;
	}

	/**
	 * A palindromic number reads the same both ways. The largest palindrome
	 * made from the product of two 2-digit numbers is 9009 = 91 × 99.
	 * Find the largest palindrome made from the product of two 3-digit numbers.
	 */
	public static int problem4() {
		int res = 0;
		for (int i=100 ; i<1000 ; i++)
			for (int j=i ; j<1000 ; j++) {
				int prod = i*j;
				if (isPalindroma((prod) + "")) 
					res = Math.max(res,prod);
			}
		return res;
	}

	public static boolean isPalindroma(String s) {
		int length = s.length();
		if (s.isEmpty() || length == 1) 
			return true;
		return s.charAt(0) != s.charAt(length - 1) ? false : isPalindroma(s.substring(1,length - 1));
	}

	/**
	 * 2520 is the smallest number that can be divided by each of the numbers
	 * from 1 to 10 without any remainder.
	 * What is the smallest positive number that is evenly divisible by all of
	 * the numbers from 1 to 20?
	 */
	public static int problem5() {
		//Little optimization : when examining i % dividers[j], we know that i % dividers[k] == 0, k < j
		//so some tests are redundant and can be avoided (example : 2 and 6 have been tested so 2*6 = 12 is
		//necessarily also a divider)
		int[] dividers = { 2,3,4,5,6,7,9,10,11,13,16,17,19 };
		int   res      = 0;
		for (int i=20 ; res == 0 ; i+=20) {
			boolean passed = true;
			for (int j=0 ; j<dividers.length && (passed = i % dividers[j] == 0) ; j++);
			if (passed) 
				res = i;
			if (i > Integer.MAX_VALUE - 20)
				res = -1;
		}
		return res;
	}
	
	/**
	 * The sum of the squares of the first ten natural numbers is, 1^2 + 2^2 + ...
	 * + 10^2 = 385
	 * 
	 * The square of the sum of the first ten natural numbers is, (1 + 2 + ... +
	 * 10)^2 = 552 = 3025
	 * 
	 * Hence the difference between the sum of the squares of the first ten
	 * natural numbers and the square of the sum is 3025 − 385 = 2640.
	 * 
	 * Find the difference between the sum of the squares of the first one
	 * hundred natural numbers and the square of the sum.
	 */
	public static int problem6() {
		int sqrSum = 0;
		int sum    = 0;
		for (int i=1 ; i<101 ; i++) {
			sqrSum += i*i;
			sum    += i;
		}
		return sum*sum - sqrSum;
	}

	/**
	 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can
	 * see that the 6th prime is 13.
	 * What is the 10 001st prime number?
	 */
	public static int problem7() {
		int count = 0;
		int i;
		for (i=2 ; count<10_001 ; i++) if (Problems_1_to_15_Scala.isPrime(i)) count++;
		return i - 1;
	}
	
	/**
	 * The four adjacent digits in the 1000-digit number that have the greatest
	 * product are 9 × 9 × 8 × 9 = 5832. Find the thirteen adjacent digits in
	 * the 1000-digit number that have the greatest product. What is the value
	 * of this product?
	 */
	public static long problem8() {
		String s =  "7316717653133062491922511967442657474235534919493496983"
				+ "520312774506326239578318016984801869478851843858615607891"
				+ "129494954595017379583319528532088055111254069874715852386"
				+ "305071569329096329522744304355766896648950445244523161731"
				+ "856403098711121722383113622298934233803081353362766142828"
				+ "064444866452387493035890729629049156044077239071381051585"
				+ "930796086670172427121883998797908792274921901699720888093"
				+ "776657273330010533678812202354218097512545405947522435258"
				+ "490771167055601360483958644670632441572215539753697817977"
				+ "846174064955149290862569321978468622482839722413756570560"
				+ "574902614079729686524145351004748216637048440319989000889"
				+ "524345065854122758866688116427171479924442928230863465674"
				+ "813919123162824586178664583591245665294765456828489128831"
				+ "426076900422421902267105562632111110937054421750694165896"
				+ "040807198403850962455444362981230987879927244284909188845"
				+ "801561660979191338754992005240636899125607176060588611646"
				+ "710940507754100225698315520005593572972571636269561882670"
				+ "428252483600823257530420752963450";
		int len    = s.length();
		int subLen = 13;
		long res    = 0;
		for (int i=0 ; i<len - subLen + 1 ; i++) {
			long prod = 1;
			for (int j=i ; j<i + subLen ; j++) prod *= (s.charAt(j) - '0');
			res = Math.max(res,prod);
		}
		return res;
	}
	
	/**
	 * A Pythagorean triplet is a set of three natural numbers, a < b < c, for
	 * which, a^2 + b^2 = c^2
	 * 
	 * For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2.
	 * 
	 * There exists exactly one Pythagorean triplet for which a + b + c = 1000.
	 * Find the product abc.
	 */
	public static int problem9() {
		int max = 1000;
		for (int a=1 ; a<max/3 ; a++)
			for (int b=a ; b<=(max - a)/2 ; b++) {
				int c = max - a - b;
				if (c*c == a*a + b*b) 
					return a*b*c;
			}
		return -1;
	}
	
	/**
	 * The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
	 * Find the sum of all the primes below two million.
	 */
	public static long problem10() {
		return Problems_1_to_15_Scala.problem10();
	}
	
	/**
	 * What is the greatest product of four adjacent numbers in the same
	 * direction (up, down, left, right, or diagonally) in the 20×20 grid?
	 */
	public static int problem11() {
		return Problems_1_to_15_Scala.problem11();
	}

	public static int problem11_bis() {
		String s =
			"08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08 " + 
			"49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00 " +
			"81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65 " + 
			"52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91 " +
			"22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80 " + 
			"24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50 " +
			"32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70 " + 
			"67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21 " + 
			"24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72 " +
			"21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95 " + 
			"78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92 " + 
			"16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57 " +
			"86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58 " +
			"19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40 " +
			"04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66 " +
			"88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69 " + 
			"04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36 " + 
			"20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16 " + 
			"20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54 " +
			"01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48 ";
		List<Integer> input = Arrays.asList(s.split("\\s+")).stream().map(Integer::parseInt).collect(Collectors.toList());
		int           n     = (int) Math.sqrt(input.size());
		
		int horizontal   = maxProdInRange(0,19,0,16,  1  ,input);
		int vertical     = maxProdInRange(0,16,0,18,  n  ,input);
		int diagonal     = maxProdInRange(0,16,0,16,n + 1,input);
		int antiDiagonal = maxProdInRange(3,19,0,16,1 - n,input);
		
		return Math.max(diagonal,Math.max(vertical,Math.max(antiDiagonal,horizontal)));
	}
	
	public static int prod(int index, int inc, List<Integer> input) {
		int res = 1;
		for (int i=0 ; i<4 ; res *= input.get(index), index += inc, i++);
		return res;
	}
	
	public static int maxProdInRange(int rowMin, int rowMax, int colMin, int colMax, int inc, List<Integer> input) {
		int n = (int) Math.sqrt(input.size()); 
		return IntStream.range(rowMin,rowMax)
			.flatMap(i -> IntStream.range(colMin,colMax).map(j -> prod(i*n + j,inc,input)))
			.max()
			.getAsInt();
	}
	
	/**
	 * The sequence of triangle numbers is generated by adding the natural
	 * numbers. So the 7th triangle number would be 1 + 2 + 3 + 4 + 5 + 6 + 7 =
	 * 28. The first ten terms would be:
	 * 
	 * 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, ...
	 * 
	 * Let us list the factors of the first seven triangle numbers:
	 * 
	 * 1: 1 3: 1,3 6: 1,2,3,6 10: 1,2,5,10 15: 1,3,5,15 21: 1,3,7,21 28:
	 * 1,2,4,7,14,28
	 * 
	 * We can see that 28 is the first triangle number to have over five
	 * divisors.
	 * 
	 * What is the value of the first triangle number to have over five hundred
	 * divisors?
	 */
	public static int problem12() {
		return Problems_1_to_15_Scala.problem12();
	}
	
	public static int problem12_bis() {
		int n   = 1;
		int sum = 0;
		int divisors;
		do  {
			divisors = 10;
			sum     += n;
			for (int i=1 ; i*i<=sum ; i++) if (sum % i == 0) divisors++;
			divisors = divisors*2 - (Math.sqrt(sum) % 1 == 0 ? 1 : 0);
			n++;
		} while (divisors <= 500);
		return sum;
	}
	
	/**
	 * Work out the first ten digits of the sum of the following one-hundred 50-digit numbers.
	 */
	public static long problem13() {
		return Problems_1_to_15_Scala.problem13();
	}
	
	/**
	 * The following iterative sequence is defined for the set of positive
	 * integers:
	 * 
	 * n → n/2 (n is even) n → 3n + 1 (n is odd)
	 * 
	 * Using the rule above and starting with 13, we generate the following
	 * sequence: 13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1
	 * 
	 * It can be seen that this sequence (starting at 13 and finishing at 1)
	 * contains 10 terms. Although it has not been proved yet (Collatz Problem),
	 * it is thought that all starting numbers finish at 1.
	 * 
	 * Which starting number, under one million, produces the longest chain?
	 * 
	 * NOTE: Once the chain starts the terms are allowed to go above one
	 * million.
	 */
	public static int problem14() {
		return Problems_1_to_15_Scala.problem14();
	}
	
	public static int problem14_bis() {
		int max = 0;
		int res = 0;
		for (int i=1 ; i<1000001 ; i++) {
			int count = 0;
			for (long n=i ; n != 1 ; count++, n = n % 2 == 0 ? n/2 : 3*n + 1);
			if (count > max) {
				res = i;
				max = count;
			}
		}
		return res;
	}
	
	/**
	 * Starting in the top left corner of a 2×2 grid, and only being able to
	 * move to the right and down, there are exactly 6 routes to the bottom
	 * right corner.
	 */
	public static long problem15() {
		return Problems_1_to_15_Scala.problem15();
	}
	
	public static long problem15_bis() {
		return binomialCoefficient(40,20);
	}

	public static long binomialCoefficient(int n, int k) {
		if (k > (n - k))
			k = n - k;
		long c = 1;
		for (int i=0 ; i<k ; i++) {
			c *= n - i;
			c /= i + 1;
		}
		return c;
	}
	
	public static long problem(String s) {
		switch (s) {
			case "1"      : return problem1     ();
			case "2"      : return problem2     ();
			case "3"      : return problem3     ();
			case "4"      : return problem4     ();
			case "5"      : return problem5     ();
			case "6"      : return problem6     ();
			case "7"      : return problem7     ();
			case "8"      : return problem8     ();
			case "9"      : return problem9     ();
			case "10"     : return problem10    ();
			case "11"     : return problem11    ();
			case "11_bis" : return problem11_bis();
			case "12"     : return problem12    ();
			case "12_bis" : return problem12_bis();
			case "13"     : return problem13    ();
			case "14"     : return problem14    ();
			case "14_bis" : return problem14_bis();
			case "15"     : return problem15    ();
			case "15_bis" : return problem15_bis();
		}
		throw new IllegalArgumentException(String.format("Problem %s doesn't exist",s));
	}
	
	public static void test(String pb, long result) {
		long start = System.nanoTime();
		try {
			assert(problem(pb) == result);
			System.out.println(String.format("Problem %s passed (execution time : %.2f ms)",pb,(System.nanoTime() - start)/1e6d));
		} catch (AssertionError ae) {
			System.err.println(String.format("Problem %s failed (execution time : %.2f ms)",pb,(System.nanoTime() - start)/1e6d));
		}
	}
	
	public static void main(String[] args) throws IOException {
		String[] tests   = { "1","2","3","4","5","6","7","8","9","10","11","11_bis","12","12_bis","13","14","14_bis","15","15_bis" };
		long[]   results = { 233168,4613732,6857,906609,232792560,25164150,104743,23514624000L,31875000,142913828922L,70600674,70600674,
				76576500,76576500,5537376230L,837799,837799,137846528820L,137846528820L };
		
//		for (int i=0 ; i<tests.length ; i++) 
//			test(tests[i],results[i]);
		test("9",results[8]);
	}
}