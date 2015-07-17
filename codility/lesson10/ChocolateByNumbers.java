package codility.lesson10;

/**
 * Level : painless
 */
public class ChocolateByNumbers {
	public int solution(int N, int M) {
		int gcd = M;
		int n   = N;
		int r   = n % gcd;
		
		while (r != 0) {
			n   = gcd;
			gcd = r;
			r   = n % gcd;
		}
		return N / gcd;
	}
}
