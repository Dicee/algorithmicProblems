import java.util.*;

/**
 * Level : hard
 * This code obtained a 83% score (100% functionnality, 75% scalability) and a silver award.
 * https://codility.com/cert/view/certRRGNH9-6FTC4MP9TBPSUVZD
 */
class Solution {
    public int solution(int[] A, int[] B, int[] C) {
        int[] weights = new int[B.length];
        
		// This solution has a worst-case complexity of O(N^2), the worst case is when the net
		// of ropes consists in a chain.
        for (int i=0 ; i<A.length ; i++) {
            int id = i;
            while (id != -1 && weights[id] + B[i] <= A[id]) {
                weights[id] += B[i];
                id           = C[id];
            }
            if (id != -1)
                return i;
        }
        return A.length;
    }
}