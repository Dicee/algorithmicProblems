package codility.lessons.prefixSum;

/**
 * Level : painless
 */
public class PassingCars {
	public int solution(int[] A) {
		int west = 0;
		for (int i = 1; i < A.length; i++)
			west += A[i];

		int count = 0;
		for (int i = 0; i < A.length - 1; i++) {
			if (A[i] == 0)
				count += west;
			else
				west--;
			if (count > 1_000_000_000)
				return -1;
		}
		return count;
	}
}
