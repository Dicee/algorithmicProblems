package interviewbit.Programming.TwoPointers.RemoveDuplicatesFromSortedArray;

import java.util.*;

// Difficulty; relatively easy. Just need to think about shifting the elements to avoid a copy of the array.

// https://www.interviewbit.com/problems/remove-duplicates-from-sorted-array/
public class Solution {
	public static int removeDuplicates(List<Integer> sortedValues) {
	    int n = sortedValues.size();
	    int j = 0;

	    for (int i = 1; i < n; i++) {
	        if (!sortedValues.get(i).equals(sortedValues.get(j))) {
	        	sortedValues.set(++j, sortedValues.get(i));
			}
	    }

	    // removal of the last index is constant time for an array-backed list
	    for (int i = n - 1; i > j; i--) sortedValues.remove(i);

	    return sortedValues.size();
	}
}