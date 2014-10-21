package lesson13;

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
		int splitIndex  = Math.abs(Arrays.binarySearch(A,0));
		int[] B = A;       

		if (splitIndex > 0) {
			B         = new int[A.length];
			int[] neg = Arrays.copyOf(A,splitIndex);
			int[] pos = Arrays.copyOfRange(A,splitIndex,A.length); 
			for (int min=0, max=splitIndex-1 ; min <= max ; min++, max--) {
				int tmp = Math.abs(neg[min]);
				neg[min]  = Math.abs(neg[max]);
				neg[max]  = tmp;
			}
			int x = 0, y = 0;
			int i = 0;
			while (x < neg.length || y < pos.length)
				B[i++] = x >= neg.length ? pos[y++] : y >= pos.length ? 
					neg[x++] : neg[x] < pos[y] ? neg[x++] : pos[y++]; 
		} 
		int count = 1;
		for (int i=1 ; i<B.length ; i++)
			if (B[i] != B[i - 1]) count++;
		return count;
    }
}
