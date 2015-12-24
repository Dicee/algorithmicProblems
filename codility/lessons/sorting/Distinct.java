package codility.lessons.sorting;

import java.util.*;

/**
 * Level : painless
 */
class Distinct {
    public int solution(int[] A) {
        Set<Integer> set = new HashSet<>(A.length);
        for (int i=0 ; i<A.length ; set.add(A[i++]));
        return set.size();
    }
}
