package codility.lessons.sorting;

import java.util.*;

/**
 * Level : painless
 */
class MaxProductOfThree {
    public int solution(int[] A) {
        int[] B = Arrays.copyOf(A,A.length);
        Arrays.sort(B);
        int max = B[0]*B[1]*B[B.length - 1];
        if (max > 0) 
            return Math.max(max,B[B.length - 3]*B[B.length - 2]*B[B.length - 1]);
        for (int i=2 ; i<B.length ; max = Math.max(max,B[i]*B[i-1]*B[i-2]), i++);
        return max;
    }
}


