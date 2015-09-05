package miscellaneous.utils.math;

import java.math.BigInteger;
import java.util.Collection;
import java.util.function.Predicate;

public class MathUtils {
	public static final double	EPSILON			= 0.00001d;
	public static final float 	FLOAT_PRECISION = 0.00001f;
	
	public static boolean isPrime1(int n) {
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
	
	public static BigInteger reverse(BigInteger n) { return new BigInteger(new StringBuilder(n.toString()).reverse().toString()); }
	
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
	
	public static boolean isPalindrome(int n, int b) { return reverse(n,b) == n; }
	public static boolean isPandigital(long n) { return isPandigital(n,9); }
	
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
	
	public static <T extends Comparable<T>> boolean greaterThan   (T t0, T t1) { return !lowerOrEqual    (t0,t1); }
	public static <T extends Comparable<T>> boolean lowerThan     (T t0, T t1) { return !greaterOrEqual  (t0,t1); }
	public static <T extends Comparable<T>> boolean greaterOrEqual(T t0, T t1) { return 0 <= t0.compareTo(t1)   ; }
	public static <T extends Comparable<T>> boolean lowerOrEqual  (T t0, T t1) { return t0.compareTo(t1) <= 0   ; }
	
	public static int signum(double d) { return d < 0 ? -1 : d == 0 ? 0 : 1; }
	
	public static int epsilonCompare(double x, double y) {
		double d = Math.abs(x - y);
		return d < EPSILON ? 0 : Double.compare(x,y);
	}
	
	public static boolean isZero(float f) { return epsilonCompare(f,0f) == 0; }
	public static int epsilonCompare(float f0, float f1) { return Math.abs(f0 - f1) < FLOAT_PRECISION ? 0 : Float.compare(f0,f1); }
	public static boolean isBetween(int low, int mid, int high) { return low <= mid && mid < high; }
	public static boolean isBetweenClosed(int low, int mid, int high) { return low <= mid && mid <= high; }
	public static boolean isZero(double x) { return epsilonCompare(x,0) == 0; }
}
