package interviewbit.Programming.Arrays.MaxNonNegativeSubArray;

import java.util.Arrays;
import java.util.List;

/**
 * Forgot the integer overflow edge-case, otherwise very classic (variant of Kadane's algorithm)
 */
public class Solution5 {
    public static List<Integer> maxset(List<Integer> arr) {
        int start = 0, end = 0;
        long sum = 0;

        int maxStart = 0, maxEnd = 0;
        long maxSum = 0;

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) >= 0) {
                end++;
                sum += arr.get(i);
            }

            if (arr.get(i) < 0 || i == arr.size() - 1) {
                if (sum > maxSum || (sum == maxSum && end - start > maxEnd - maxStart)) {
                    maxSum = sum;
                    maxStart = start;
                    maxEnd = end;

                }
                start = end = i + 1;
                sum = 0;
            }
        }

        return arr.subList(maxStart, maxEnd);
    }
}
