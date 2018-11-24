package interviewbit.Programming.Hashing.DiffkLi;

import java.util.*;

// Difficulty: easy, partly because it is a classic.

// https://www.interviewbit.com/problems/diffk-ii/
public class Solution {
    public static int diffPossible(final int[] arr, int target) {
        Set<Integer> values = new HashSet<>();
        for (int i: arr) {
            if (values.contains(target + i)) return 1;
            if (values.contains(i - target)) return 1;
            values.add(i);
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(diffPossible(new int[]{1, 5, 3}, 2)); // 1
        System.out.println(diffPossible(new int[]{1, 3, 5}, 2)); // 1
        System.out.println(diffPossible(new int[]{1, 3, 5}, 4)); // 1
        System.out.println(diffPossible(new int[]{1, 3, 5}, 0)); // 0
        System.out.println(diffPossible(new int[]{1, 3, 5, 9, 0, 3, 6}, 0)); // 1
        System.out.println(diffPossible(new int[]{1, -3, 5, 9}, -2)); // 0
        System.out.println(diffPossible(new int[]{1, -3, 5, 9}, -4)); // 1
        System.out.println(diffPossible(new int[]{
                        34, 63, 64, 38, 65, 83, 50, 44, 18, 34, 71, 80, 22, 28, 20, 96, 33, 70, 0, 25, 64, 96, 18, 2, 53, 100, 24, 47,
                        98, 69, 60, 55, 8, 38, 72, 94, 18, 68, 0, 53, 18, 30, 86, 55, 13, 93, 15, 43, 73, 68, 29},
                97)); // 0
    }
}
