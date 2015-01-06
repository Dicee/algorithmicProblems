import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		// a bit hacky but it enables to use a faster algorithm to
		// find the primes
		boolean[] sieve = new boolean[7920];
		sieve[0] = true;
		sieve[1] = true;

		int sum = 0;
		for (int i = 2; i < sieve.length; i++) {
			if (!sieve[i]) {
				for (int j = 2 * i; j < sieve.length; j += i)
					sieve[j] = true;
				sum += i;
			}
		}
		System.out.println(sum);
	}
}