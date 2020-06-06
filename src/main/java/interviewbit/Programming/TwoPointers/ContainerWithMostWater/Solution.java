package interviewbit.Programming.TwoPointers.ContainerWithMostWater;

import java.util.Arrays;
import java.util.List;

// Difficulty: I found it difficult. The code is extremely easy, bit I just didn't come up with the observation that
//             allowed to make it linear without looking at the hints.

// https://www.interviewbit.com/problems/container-with-most-water/
public class Solution {
    public static void main(String[] args) {
        System.out.println(maxArea(Arrays.asList(1, 5, 4, 3))); // 6
    }

    public static int maxArea(List<Integer> heights) {
        int max = 0;
        int i = 0, j = heights.size() - 1;

        while (i < j) {
            int hi = heights.get(i);
            int hj = heights.get(j);
            max = Math.max(max, (j - i) * Math.min(hi, hj));
            if (hi > hj) j--;
            else if (hi < hj) i++;
            else break;
        }

        return max;
    }
}
