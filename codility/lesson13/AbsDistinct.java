package codility.lesson13;

import java.util.Arrays;

/**
 * Level : painless
 */		
public class AbsDistinct {
	public int solution(int[] A) {
	// we could use an HashSet to crack this...        
        /* Set<Integer> set = new HashSet<>();
         * for (int i : A) set.add(Math.abs(i));
         *return set.size();
         */
         
         // ... but it would not be fun
	int splitIndex = Arrays.binarySearch(A,0);
	splitIndex     = splitIndex < 0 ? - splitIndex - 1 : splitIndex;
	splitIndex     = Math.min(splitIndex,A.length - 1);
	 long[] B       = new long[A.length];  
		
	int neg = A[splitIndex] >= 0 ? splitIndex - 1 : splitIndex;
	int pos = A[splitIndex] >= 0 ? splitIndex     : splitIndex + 1;
		
	int i     = 0;
	int count = 0;
		
	while (pos < A.length || neg >= 0) {
		if (pos >= A.length)
			B[i] = - (long) A[neg--];
		else if (neg < 0 || A[pos] < - A[neg])
		        B[i] = A[pos++];
		else 
		        B[i] = - (long) A[neg--];
		  
		if (i == 0 || B[i] != B[i-1]) 
		        count++;
		i++;
	}
	return count;
    }
}
