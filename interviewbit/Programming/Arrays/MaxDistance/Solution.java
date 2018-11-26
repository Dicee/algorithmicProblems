package interviewbit.Programming.Arrays.MaxDistance;

import java.lang.*;
import java.util.*;

// Difficulty: easy. Accumulated aggregate functions over arrays is a common trick. It also required observing that what
//             we want is to select the last element (or first from the right) larger than the current element, which can
//             be done in logarithmic time using binary search in the accumulated (right to left) maximum of the array.

// https://www.interviewbit.com/problems/max-distance/
public class Solution {
    public static void main(String[] args) {
        System.out.println(maximumGap(Arrays.asList(3, 5, 4, 2)));
    }

    public static int maximumGap(List<Integer> values) {
        List<Integer> accMaxBuilder = new ArrayList<>();
        List<Integer> accMaxIndices = new ArrayList<>();

        for (int i = values.size() - 1; i >= 0; i--) {
            int value = values.get(i);
            if (accMaxBuilder.isEmpty() || accMaxBuilder.get(accMaxBuilder.size() - 1) < value) {
                accMaxBuilder.add(value);
                accMaxIndices.add(i);
            }
        }

        int[] accMax = new int[accMaxBuilder.size()];
        for (int i = 0; i < accMax.length; i++) accMax[i] = accMaxBuilder.get(i);

        int max = -1;
        for (int i = 0; i < values.size(); i++) {
            int j = Arrays.binarySearch(accMax, values.get(i));
            if (j < 0) j = -j - 1;
            if (j < accMax.length && values.get(i) <= accMax[j]) {
                max = Math.max(max, accMaxIndices.get(j) - i);
            }
        }

        return max;
    }
}