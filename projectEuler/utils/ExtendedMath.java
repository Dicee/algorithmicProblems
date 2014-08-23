package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class ExtendedMath {
//	public static final Set<Integer> MILLION_SIEVE = new HashSet<>(400_000);
//	static {
//		boolean[] sieve = ESieve(300_000);
//		for (int i=0 ; i<sieve.length ; i++)
//			if (!sieve[i])
//				MILLION_SIEVE.add(i);
//	}
	
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
	
	public static <T> void reverse(T[] arr, int min, int max) {
		while (min < max) swap(arr,min++,max--);
	}
	
	public static <T> void swap(T[] arr, int i, int j) {
		T tmp  = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static void reverse(int[] arr, int min, int max) {
		while (min < max) swap(arr,min++,max--);
	}
	
	public static void swap(int[] arr, int i, int j) {
		int tmp  = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
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
