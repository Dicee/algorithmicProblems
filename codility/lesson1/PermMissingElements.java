package codility.lesson1;

/**
 * Level : painless
 */
 class PermMissingElements {
	public int solution(int[] A) {
        	long sum = 0;
        	long n = A.length;
        	for (int i=0 ; i<A.length ; sum += A[i++]);   
        	return (int) ((n + 1)*(n + 2)/2 - sum);
    	}
}


