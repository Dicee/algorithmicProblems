package codility.lesson4;

import java.util.*;

/**
 * Level : painless
 */
class Triangle {
    public int solution(int[] A) {
        int[] B = Arrays.copyOf(A,A.length);
        Arrays.sort(B);
        for (int i=2 ; i<A.length ; i++) {
            long p = B[i], q = B[i - 1], r = B[i - 2];
            if (p + q > r && p + r > q && q + r > p) return 1;
        }
        return 0;
    }
}
