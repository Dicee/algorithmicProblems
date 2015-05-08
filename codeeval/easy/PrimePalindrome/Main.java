package codeeval.easy.PrimePalindrome;

public class Main {
	public static void main(String[] args) {
		// a bit hacky but it enables to use a faster algorithm to
		// find the primes
		boolean[] sieve = new boolean[7920];
		sieve[0] = true;
		sieve[1] = true;

		int max = 0;
		for (int i = 2; i < sieve.length; i++) {
			if (!sieve[i]) {
				for (int j = 2 * i; j < sieve.length; j += i)
					sieve[j] = true;
				if (isPalindrome(i))
					max = i;
			}
		}
		System.out.println(max);
	}

	private static boolean isPalindrome(int i) {
		int j = i, reversed = 0;
		
		while (j > 0) {
			reversed = reversed*10 + j % 10;
			j       /= 10;
		}
		return reversed == i;
	}
}
