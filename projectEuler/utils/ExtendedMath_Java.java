package utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class ExtendedMath_Java {
	public static final boolean[] MILLION_SIEVE = ESieve(1_000_000);

	public static boolean isPrime(int n) {
		if (n <= 1_000_000)
			return !MILLION_SIEVE[n];

		int max = (int) Math.floor(Math.sqrt(n));
		for (int i=2 ; i<=max ; i++)
			if (n % i == 0)
				return false;
		return true;
	}
	
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
	
	public static int reverse(int n, int b) {
		int rev = 0;
		while (n > 0) {
			rev = (rev * b) + n % b;
			n  /= b;
		}
		return rev;
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
	
	public static void reverse(int[] arr, int min, int max) {
		while (min < max) swap(arr,min++,max--);
	}
	
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i]  = arr[j];
		arr[j]  = tmp;
	}
	
	public static void main(String[] args) {
//		long start = System.currentTimeMillis();
//		List<Integer> result = new ArrayList<>();
//		for (int i=1 ; i<10000000 ; i++)
//			if (isPrime(i))
//				result.add(i);
//		System.out.println(System.currentTimeMillis() - start);
//		start = System.currentTimeMillis();
//		basicESieve(10000000);
//		System.out.println(System.currentTimeMillis() - start);
	}
}
