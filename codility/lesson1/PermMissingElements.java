/**
 * Level : painless
 */
class PermMissingElements {
    public int solution(int[] A) {
        long sum         = 0; 
		long completeSum = 0;
        for (int i=0 ; i<A.length ; i++) {
            sum         += A[i];
			completeSum += i + 1;
		}
        return (int) (completeSum - sum);
    }
}

