package codility.lessons.maximumSliceProblem;

/**
 * Level : painless
 */
public class MaxProfit {
	public int solution(int[] A) {
        int maxProfit = 0;
        int start = 0;
        for (int i=1 ; i<A.length ; i++) {
            int profit = A[i] - A[start];
            if (profit > 0)
                maxProfit = Math.max(profit,maxProfit);
            else 
                start = i;
        }
        return maxProfit;
    }
}
