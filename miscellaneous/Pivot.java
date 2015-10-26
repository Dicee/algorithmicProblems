package miscellaneous;

import java.util.Deque;
import java.util.LinkedList;

import com.dici.check.Check;

/**
 * For a given array arr of integers, return an index i such that all the elements at the left of the element 
 * i are smaller than arr[i] (arr[0...i-1]), and all the elements at the right of i (arr[i...n-1]) are larger than arr[i].
 *
 * If such an index does not exist, return -1.
 */
public class Pivot {
    public static int solution(int[] arr ) {
        Deque<Integer> maxs = new LinkedList<>();
        for (int i = 0; i < arr.length ; i++) 
            if (maxs.peek() == null || arr[maxs.peek()] < arr[i]) maxs.push(i);
        
        for (int i = arr.length - 1, min = arr[i]; i >= 0 && !maxs.isEmpty(); min = Math.min(min, arr[--i]))
            if (min >= arr[maxs.pop()]) return i;
        return -1;
    }
    
    public static void main(String[] args) {
        Check.areEqual(solution(new int[] { 2,3,1 }), -1);
        Check.areEqual(solution(new int[] { 2,1,5,6,1,5,8,3,1 }), -1);
        Check.areEqual(solution(new int[] { 1,2,3,4,5,6 }), 5);
        Check.areEqual(solution(new int[] { 1,2,3,4,5,6,2,3,8,9,10,11,10 }), 11);
        Check.areEqual(solution(new int[] { 3,2,1 }), -1);
    }
}
