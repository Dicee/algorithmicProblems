package projectEuler.utils;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExtendedMath {
//	public static final boolean[] MILLION_SIEVE = ESieve(1_000_000);
	
	public static boolean isPrime1(int n) {
//		if (n <= 1_000_000)
//			return !MILLION_SIEVE.contains(n);
		if (n % 2 == 0 && n != 2)
			return false;

		int max = (int) Math.floor(Math.sqrt(n));
		for (int i=3 ; i<=max ; i += 2)
			if (n % i == 0)
				return false;
		return true;
	}

	public static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;
	    if(n <= 1 || n % 2 == 0 || n % 3 == 0)
	        return false;
	    int x = (int) Math.sqrt(n);
	    for(int i=1 ; (6*i-1) <= x ; i++)
	        if(n % (6*i-1) == 0 || n % (6*i+1) == 0) 
	            return false;
	    return true;
	}
	
	public static boolean isPrime(long n) {
		if (n == 2 || n == 3)
			return true;
	    if(n <= 1 || n % 2 == 0 || n % 3 == 0)
	        return false;
	    int x = (int) Math.sqrt(n);
	    for(int i=1 ; (6*i-1) <= x ; i++)
	        if(n % (6*i-1) == 0 || n % (6*i+1) == 0) 
	            return false;
	    return true;
	}

//	public static boolean[] ESieve1(int n) {
//		//true means the value has been eliminated, not that is is a prime !
//		boolean[] sieve = new boolean[n + 1];
//		sieve[0] = true;
//		sieve[1] = true;
//		eliminate(2,sieve);
//		eliminate(3,sieve);
//		for (int i=1 ; 6*i - 1 < n ; i++) {
//			if (!sieve[6*i - 1]) eliminate(6*i - 1,sieve);
//			if (!sieve[6*i + 1]) eliminate(6*i + 1,sieve);
//		}
//		return sieve;
//	}
//	
//	private static void eliminate(int prime, boolean[] sieve) {
//		for (int i=2*prime ; i<sieve.length - 1 ; i += prime) sieve[i] = true;
//	}
	
	public static boolean[] ESieve(int n) {
		//true means the value has been eliminated, not that is is a prime !
		boolean[] sieve = new boolean[n + 1];
		sieve[0] = true;
		sieve[1] = true;
		for (int i=2 ; i<n ; i++) 
			if (!sieve[i]) 
				for (int k=2*i ; k<n ; k += i)
					sieve[k] = true;
		return sieve;
	}
	
	public static <T extends Collection<Integer>> T ESieve(int n, T fill, Predicate<Integer> pred) {
		boolean[] sieve = ESieve(n);
		for (int i=2 ; i<n ; i++)
			if (!sieve[i] && pred.test(i))
				fill.add(i);
		return fill;
	}
	
	public static <T extends Collection<Integer>> T ESieve(int n, T fill) {
		boolean[] sieve = ESieve(n);
		for (int i=2 ; i<n ; i++)
			if (!sieve[i])
				fill.add(i);
		return fill;
	}
	
	public static int gcd(int n, int m) {
		if (m > n)
			return gcd(m,n);
		int r;
		do {
			r = n % m;
			n = m;
			m = r;
		} while (r != 0);
		return n;
	}
	
	public static BigInteger reverse(BigInteger n) {
		return new BigInteger(new StringBuilder(n.toString()).reverse().toString());
	}
	
	public static long reverse(long n, int b) {
		long rev = 0;
		while (n > 0) {
			rev = (rev * b) + n % b;
			n  /= b;
		}
		return rev;
	}
	
	public static int reverse(int n, int b) {
		int rev = 0;
		while (n > 0) {
			rev = (rev * b) + n % b;
			n  /= b;
		}
		return rev;
	}
	
	public static boolean isPalindrome(int n, int b) {
		return reverse(n,b) == n;
	}
	
	public static boolean isPandigital(long n) {
		return isPandigital(n,9);
	}
	
	public static boolean isPandigital(long n, int nDigits) {
		int digits = 0;
		while (n > 0) {
			int l = 1 << (n % 10);
			if (n % 10 == 0 || (l & digits) != 0)
				return false;
			digits += l;
			n      /= 10;
		}
		digits /= 2;
		for (int i=0 ; i<nDigits ; i++) 
			if (digits % 2 == 0)
				return false;
			else
				digits /= 2;
		return true; 
	}
	
	public static void main(String[] args) {
//		long start = System.currentTimeMillis();
//		ESieve1(100_000_000);
//		System.out.println(System.currentTimeMillis() - start);
//		start = System.currentTimeMillis();
//		ESieve(100_000_000);
//		System.out.println(System.currentTimeMillis() - start);
	}
}
