package codility.lessons.primeAndCompositeNumbers;

/**
 * Level : painless
 */
public class CountFactors {
	public int solution(int N) {
        double sqrt = Math.sqrt(N);
        int result = 0;
        for (int i=1 ; i<=sqrt ; i++) 
            if (N % i == 0) result++;
        return 2*result - (sqrt % 1 == 0 ? 1 : 0);
    }
}
