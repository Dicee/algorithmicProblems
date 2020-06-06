package codility.lessons.countingElements;

import java.util.*;

/**
 * Level : painless
 */
class FrogRiverOne {
    public int solution(int X, int[] A) {
        Set<Integer> set = new HashSet<>();
        for (int i=0 ; i<A.length ; i++) {
            if (0 < A[i] && A[i] <= X) 
                set.add(A[i]);
            if (set.size() == X)
                return i;
        }
        return -1;
    }
}
