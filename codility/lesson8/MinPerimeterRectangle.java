package codility.lesson8;

/**
 * Level : painless
 */
public class MinPerimeterRectangle {
	public int solution(int N) {
		double sqrt = Math.sqrt(N);
		int min = Integer.MAX_VALUE;
		for (int i = 1; i <= sqrt; i++)
			if (N % i == 0)
				min = Math.min(min,2 * (N / i + i));
		return min;
	}
}
